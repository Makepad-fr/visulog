/*
	22015094 - Idil Saglam
*/
package up.visulog.analyzer;

public enum AnalyzerPluginType {
    web,
    chartGenerator,
    stdout,
    csvGenerator;

    /**
     * Returns the string representation of the current analyzer plugin type
     *
     * @return The string representation of the current analyzer plugin type
     */
    public String toString() {
        return switch (this) {
            case web -> "web";
            case chartGenerator -> "chart-generator";
            case stdout -> "stdout";
            case csvGenerator -> "csv-generator";
        };
    }
}
