package up.visulog.analyzer;

import up.visulog.pluginmanager.VisulogPlugin;

public abstract class AnalyzerPlugin<T> extends VisulogPlugin {
    private final AnalyzerPluginType type;
    protected final T param;
    protected final boolean countCommitsPerUser, countMergeCommitsPerUser;
    protected final GraphType[] graphTypes;

  /**
   * Create a new analyzer plugin with given parameters
   * @param type The type of the analyzer plugin
   * @param param the required param to create the analyzer plugin
   * @param countCommitsPerUser A boolean indicating that if graphs will contain the count of commits per user
   * @param countMergeCommitsPerUser A boolean indicating that if graphs will contain the count of merge commits per user
   * @param graphType The type of the graph to generate
   */
  protected AnalyzerPlugin(AnalyzerPluginType type, T param, boolean countCommitsPerUser, boolean countMergeCommitsPerUser, GraphType[] graphType) {
      this.type = type;
      this.param = param;

      this.countCommitsPerUser = countCommitsPerUser;
      this.countMergeCommitsPerUser = countMergeCommitsPerUser;
      this.graphTypes = graphType;
    }
}
