package up.visulog.webgen;

import up.visulog.analyzer.AnalyzerPlugin;
import up.visulog.analyzer.AnalyzerPluginType;
import up.visulog.analyzer.GraphType;

public class WebGenerator extends AnalyzerPlugin<String> {

  /**
   * Creates a web generator plugin with given parameters
   * @param portNumber The port number for the webserver to create
   * @param countCommitsPerUser A boolean indicating that if we will count commits per user
   * @param countMergeCommitsPerUser A boolean indicating that if merge commits per user will be counted
   * @param graphTypes The type of the graphs to generate
   */
  public WebGenerator(String portNumber, boolean countCommitsPerUser, boolean countMergeCommitsPerUser, GraphType[] graphTypes) {
    super(AnalyzerPluginType.web,portNumber, countCommitsPerUser, countMergeCommitsPerUser, graphTypes);
  }

  @Override
  public void run() {
    // TODO: Complete with the HTML generation and web server to serve generated HTML
  }
}
