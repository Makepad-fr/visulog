/*
	22015094 - Idil Saglam
*/
package up.visulog.tablebuilder;

import up.visulog.analyzer.AnalyzerPlugin;
import up.visulog.analyzer.AnalyzerPluginType;
import up.visulog.analyzer.GraphType;
import up.visulog.analyzer.GroupBy;

public class TableBuilder extends AnalyzerPlugin<Void> {

    /**
     * Create a new analyzer plugin with given parameters
     *
     * @param type The type of the analyzer plugin
     * @param countCommitsPerUser A boolean indicating that if graphs will contain the count of
     *     commits per user
     * @param countMergeCommitsPerUser A boolean indicating that if graphs will contain the count of
     *     merge commits per user
     * @param groupBy The type of groups used for analyzing
     * @param graphType The type of the graph to generate
     */
    protected TableBuilder(
            AnalyzerPluginType type,
            boolean countCommitsPerUser,
            boolean countMergeCommitsPerUser,
            GroupBy groupBy,
            GraphType[] graphType) {
        super(type, null, countCommitsPerUser, countMergeCommitsPerUser, groupBy, graphType);
    }

    @Override
    public void run() {}
}
