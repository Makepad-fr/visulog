/*
	22015094 - Idil Saglam
*/
package up.visulog.analyzer;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.IsoFields;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import up.visulog.git.CommitExtractor;
import up.visulog.pluginmanager.VisulogPlugin;

public abstract class AnalyzerPlugin<T> extends VisulogPlugin {
    private final AnalyzerPluginType type;
    protected final T param;
    protected final boolean countCommitsPerUser, countMergeCommitsPerUser;
    protected final GraphType[] graphTypes;
    protected Map<PersonIdent, Set<RevCommit>> commitsPerUser = new HashMap<>();
    protected final GroupBy groupBy;
    /**
     * Create a new analyzer plugin with given parameters
     *
     * @param type The type of the analyzer plugin
     * @param param the required param to create the analyzer plugin
     * @param countCommitsPerUser A boolean indicating that if graphs will contain the count of
     *     commits per user
     * @param countMergeCommitsPerUser A boolean indicating that if graphs will contain the count of
     *     merge commits per user
     * @param groupBy The type of groups used for analyzing
     * @param graphType The type of the graph to generate
     */
    protected AnalyzerPlugin(
            AnalyzerPluginType type,
            T param,
            boolean countCommitsPerUser,
            boolean countMergeCommitsPerUser,
            GroupBy groupBy,
            GraphType[] graphType) {
        this.type = type;
        this.param = param;
        this.countCommitsPerUser = countCommitsPerUser;
        this.countMergeCommitsPerUser = countMergeCommitsPerUser;
        this.graphTypes = graphType;
        this.groupBy = groupBy;
    }

    /**
     * Initializes the hashmap of commits per user.
     */
    protected void initUserCommits() {
        for (RevCommit commit: super.commits) {
            PersonIdent author = commit.getAuthorIdent();
            // If the commits per user does not contains any entry for the current commit author, initialize an empty hashset for this author
            this.commitsPerUser.computeIfAbsent(author, k -> new HashSet<>());
            // Add the commit to the hashset of the current user's commits
            this.commitsPerUser.get(author).add(commit);
        }
    }

    /**
     * Return a map contains number of commits of each user
        * @return A map containing the number of commit per user
     */
    protected Map<PersonIdent, Number> getCommitsPerUserCount () {
        Map<PersonIdent, Number> result = new HashMap<>();
        for (Entry<PersonIdent, Set<RevCommit>> commitPerUserEntry: this.commitsPerUser.entrySet()) {
            result.put(commitPerUserEntry.getKey(), commitPerUserEntry.getValue().size());
        }
        return result;
    }

    /**
     * Get the number of merge commits per user
     * @return A map contains the number of merge commits per user
     */
    protected Map<PersonIdent, Number> getMergeCommitPerUser() {
        Map<PersonIdent, Number> result = new HashMap<>();
        for (Entry<PersonIdent, Set<RevCommit>> commitsPerUserEntry: this.commitsPerUser.entrySet()) {
            result.put(commitsPerUserEntry.getKey(), commitsPerUserEntry.getValue().stream().filter(
                CommitExtractor::isMergeCommit).count());
        }
        return result;
    }


    /**
     * Get grouped commits following the groupBy type
     * @return A map containing grouped commits per each date commits by user
     */
    protected Map<ZonedDateTime, Map<PersonIdent, Set<RevCommit>>> getGroupedCommits() {
        Map<ZonedDateTime, Map<PersonIdent, Set<RevCommit>>> result = new TreeMap<>();
         // Per each commit
        for (RevCommit commit: super.commits) {
            final ZonedDateTime commitZdt = ZonedDateTime.ofInstant(commit.getAuthorIdent().getWhen().toInstant(), ZoneId.systemDefault());
            // Verify if there's a key in the result map to fit in
            Set<ZonedDateTime> targetKeys = result.keySet().stream().filter((ZonedDateTime zdt) -> AnalyzerPlugin.same(
                    zdt,
                    commitZdt,
                    this.groupBy
                )
            ).collect(Collectors.toSet());
            if (targetKeys.size() == 0) {
                // If there's not target key initialize with an empty hashmap
                result.put(commitZdt, this.initAnalyzeResult());
            }
            // Put the commit in the hashmap
            result.get(commitZdt).get(commit.getAuthorIdent()).add(commit);
        }
        return result;
    }

    /**
     * Verify if two dates are same day. Two dates are same day, if and only if they are same year and same month on the same timezone
     * @param date1 First date
     * @param date2 Second date
     * @param group The type of group to verify
     * @return True if date1 and date2 are same day, false if not
     */
    public static boolean same(ZonedDateTime date1, ZonedDateTime date2, GroupBy group) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        return switch (group) {
            case Hour -> date1.getHour() == date2.getHour() && same(date1, date2, GroupBy.Day);
            case Day -> date1.getDayOfMonth() == date2.getDayOfMonth() && same(date1, date2, GroupBy.Week);
            case Week ->
                (date1.get(weekFields.weekOfWeekBasedYear()) == date2.get(weekFields.weekOfWeekBasedYear())) && same(date1, date2, GroupBy.Month);
            case Month -> date1.getMonth() == date2.getMonth() && same(date1,date2,GroupBy.Quarter);
            case Quarter -> date1.get(IsoFields.QUARTER_OF_YEAR) == date2.get(IsoFields.QUARTER_OF_YEAR) && same(date1, date2, GroupBy.Year);
            case Year -> date1.getYear() == date2.getYear();
        };
    }


    /**
     * Initialize a hashmap with all existing authors and with empty sets
     * @return The hashmap generated with all existing authors and empty sets for each
     */
    private Map<PersonIdent, Set<RevCommit>> initAnalyzeResult() {
        Map<PersonIdent, Set<RevCommit>> result = new HashMap<>();
        for (PersonIdent author: this.commitsPerUser.keySet()) {
            result.put(author, new HashSet<>());
        }
        return result;
    }
}
