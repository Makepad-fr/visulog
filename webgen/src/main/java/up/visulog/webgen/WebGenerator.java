/*
	22015094 - Idil Saglam
*/
package up.visulog.webgen;

import up.visulog.analyzer.AnalyzerPlugin;
import up.visulog.analyzer.AnalyzerPluginType;
import up.visulog.analyzer.GraphType;
import up.visulog.analyzer.GroupBy;

public class WebGenerator extends AnalyzerPlugin<String> {

    /**
     * Creates a web generator plugin with given parameters
     *
     * @param portNumber The port number for the webserver to create
     * @param countCommitsPerUser A boolean indicating that if we will count commits per user
     * @param countMergeCommitsPerUser A boolean indicating that if merge commits per user will be
     *     counted
     * @param groupBy The type of groups to use while analyzing
     * @param graphTypes The type of the graphs to generate
     */
    public WebGenerator(
            String portNumber,
            boolean countCommitsPerUser,
            boolean countMergeCommitsPerUser,
            GroupBy groupBy,
            GraphType[] graphTypes) {
        super(
                AnalyzerPluginType.web,
                portNumber,
                countCommitsPerUser,
                countMergeCommitsPerUser,
            groupBy,
                graphTypes);
    }

    @Override
    public void run() {
        // TODO: Complete with the HTML generation and web server to serve generated HTML
    }
}
