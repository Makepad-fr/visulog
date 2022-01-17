/*
	22015094 - Idil Saglam
*/
package up.visulog.tablebuilder;

import up.visulog.analyzer.AnalyzerPlugin;
import up.visulog.analyzer.AnalyzerPluginType;
import up.visulog.analyzer.GroupBy;

public class TableBuilder extends AnalyzerPlugin<Void> {

    /**
     * Create a new analyzer plugin with given parameters
     *
     * @param countCommitsPerUser A boolean indicating that if graphs will contain the count of
     *     commits per user
     * @param countMergeCommitsPerUser A boolean indicating that if graphs will contain the count of
     *     merge commits per user
     * @param groupBy The type of groups used for analyzing
     */
    public TableBuilder(
            boolean countCommitsPerUser, boolean countMergeCommitsPerUser, GroupBy groupBy) {
        super(
                AnalyzerPluginType.stdout,
                null,
                countCommitsPerUser,
                countMergeCommitsPerUser,
                groupBy,
                null);
    }

    @Override
    public void run() {
        TableGenerator generator = new TableGenerator(super.groupedCommits, super.groupBy);

        generator.generate();
    }
}
