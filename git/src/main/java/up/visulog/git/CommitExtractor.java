/*
	22015094 - Idil Saglam
*/
package up.visulog.git;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;

public class CommitExtractor {
    private final String[] authors;
    private final Date startDate, endDate;
    private final Ref head;

    private final Repository repository;
    private final Git git;

    /**
     * Creates a new commit extractor instance with the given paramaeters
     *
     * @param authors An array of authors to extract commits with
     * @param startDate The start extract commits starting from
     * @param endDate The end date to extract commits end at
     * @param path The path of the git repository
     * @param branchName The name of the git branch
     */
    public CommitExtractor(
            String[] authors, Date startDate, Date endDate, String path, String branchName)
            throws IOException {
        this.repository = new FileRepository(path);
        this.git = new Git(this.repository);
        this.authors = authors;
        this.startDate = startDate;
        this.endDate = endDate;
        this.head = this.repository.exactRef(String.format("refs/heads/%s", branchName));
    }

    /**
     * Get the set of the extracted commits
     *
     * @return The set of extracted commits matching the parameters
     */
    public Set<RevCommit> extract() throws IOException {
        RevWalk walk = new RevWalk(this.repository);
        RevCommit startsWith = walk.parseCommit(this.head.getObjectId());
        walk.markStart(startsWith);
        walk.setRevFilter(new VisulogRevFilter());
        Set<RevCommit> result = new HashSet<>();
        for (RevCommit rev : walk) {
            result.add(rev);
        }
        walk.dispose();
        return result;
    }

    private class VisulogRevFilter extends RevFilter {

        /**
         * Determine if the supplied commit should be included in results.
         *
         * @param walker the active walker this filter is being invoked from within.
         * @param commit the commit currently being tested. The commit has been parsed and its body
         *     is available for inspection only if the filter returns true from {@link
         *     #requiresCommitBody()}.
         * @return true to include this commit in the results; false to have this commit be omitted
         *     entirely from the results.
         * @throws StopWalkException the filter knows for certain that no additional commits can
         *     ever match, and the current commit doesn't match either. The walk is halted and no
         *     more results are provided.
         * @throws MissingObjectException an object the filter needs to consult to determine its
         *     answer does not exist in the Git repository the walker is operating on. Filtering
         *     this commit is impossible without the object.
         * @throws IncorrectObjectTypeException an object the filter needed to consult was not of
         *     the expected object type. This usually indicates a corrupt repository, as an object
         *     link is referencing the wrong type.
         * @throws IOException a loose object or pack file could not be read to obtain data
         *     necessary for the filter to make its decision.
         */
        @Override
        public boolean include(RevWalk walker, RevCommit commit)
                throws StopWalkException, MissingObjectException, IncorrectObjectTypeException,
                        IOException {
            if ((CommitExtractor.this.startDate != null)
                    && (commit.getAuthorIdent().getWhen().before(CommitExtractor.this.startDate))) {
                // If the current commit authored before the start date it won't be included
                return false;
                // throw StopWalkException.INSTANCE;
            }
            if (CommitExtractor.this.endDate != null
                    && (commit.getAuthorIdent().getWhen().after(CommitExtractor.this.endDate))) {
                // If the current commit authored after the start date it won't be included
                return false;
            }

            if (Arrays.stream(CommitExtractor.this.authors)
                    .noneMatch(
                            (String author) -> {
                                PersonIdent commitAuthor = commit.getAuthorIdent();
                                return author.equals(commitAuthor.getEmailAddress())
                                        || author.equals(commitAuthor.getName());
                            })) {
                // If the current commit author's email or name does not match with authors array,
                // do not include the current commit
                return false;
            }
            return true;
        }

        /**
         * {@inheritDoc}
         *
         * <p>Clone this revision filter, including its parameters.
         *
         * <p>This is a deep clone. If this filter embeds objects or other filters it must also
         * clone those, to ensure the instances do not share mutable data.
         */
        @Override
        public RevFilter clone() {
            return new VisulogRevFilter();
        }
    }
}
