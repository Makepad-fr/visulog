package up.visulog.analyzer;

public enum AnalyzerPluginType {
  web,
  chartGenerator;

  /**
   * Returns the string representation of the current analyzer plugin type
   * @return The string representation of the current analyzer plugin type
   */
  public String toString() {
    return switch (this) {
      case web -> "web";
      case chartGenerator -> "chart-generator";
    };
  }
}
