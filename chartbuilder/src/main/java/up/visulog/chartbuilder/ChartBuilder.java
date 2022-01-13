/*
	22015094 - Idil Saglam
*/
package up.visulog.chartbuilder;

import up.visulog.analyzer.AnalyzerPlugin;
import up.visulog.analyzer.AnalyzerPluginType;
import up.visulog.analyzer.GraphType;
import up.visulog.analyzer.GroupBy;

public class ChartBuilder extends AnalyzerPlugin<String> {

    /**
     * Creates a new chart builder plugin with given parameters
     *
     * @param outputPath The output path to save the charts
     * @param countCommitsPerUser A boolean indicating that if commits per user will be counted
     * @param countMergeCommitsPerUser A boolean indicating that merge commits per user will be
     *     counted
     * @param groupType The type of groups to use when analyzing
     * @param graphTypes The type of graphs to generate
     */
    public ChartBuilder(
            String outputPath,
            boolean countCommitsPerUser,
            boolean countMergeCommitsPerUser,
            GroupBy groupType,
            GraphType[] graphTypes) {
        super(
                AnalyzerPluginType.chartGenerator,
                outputPath,
                countCommitsPerUser,
                countMergeCommitsPerUser,
                groupType,
                graphTypes);
    }

    @Override
    public void run() {
        BarChartSample.main(null);
    }
}
