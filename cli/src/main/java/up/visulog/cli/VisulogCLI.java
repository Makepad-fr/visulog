/*
	22015094 - Idil Saglam
*/
package up.visulog.cli;

import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import up.visulog.analyzer.AnalyzerPluginType;
import up.visulog.analyzer.GraphType;
import up.visulog.analyzer.GroupBy;
import up.visulog.config.Configuration;
import up.visulog.config.Runner;

@Command(
        name = "visulog",
        mixinStandardHelpOptions = true,
        version = "1.0.0",
        description = "Analyse commits of a git repository")
public class VisulogCLI implements Callable<Integer> {

    @Option(
            names = {"-a", "--author"},
            description = "Extractor author to filter with",
            required = false)
    private String[] authors = new String[0];

    @Option(
            names = {"--from"},
            description = "The start date to analyze commits",
            type = Date.class,
            required = false)
    private Date startDate;

    @Option(
            names = {"--to"},
            description = "End date to analyze commits",
            type = Date.class,
            required = false)
    private Date endDate = new Date();

    @Option(
            names = {"--branch"},
            description = "The name of the branch to get the commits with",
            required = false)
    private String branchName;

    @Option(
            names = {"-i", "--input"},
            description = "Input repository path",
            type = String.class,
            required = false)
    private String inputPath = Paths.get("").toAbsolutePath().toString();

    @Option(
            names = {"--countCommit"},
            description = "Flag indicating that commits per user will be counted",
            required = false,
            negatable = false)
    private boolean countCommitsPerUser;

    @Option(
            names = {"--countMergeCommits"},
            description = "Flag indicating that merge commits per user will be counted",
            required = false,
            negatable = false)
    private boolean countMergeCommitsPerUser;

    @Option(
            names = {"--graphType"},
            description =
                    "The type of the graphs to create. Valid values: ${COMPLETION-CANDIDATES}",
            required = false)
    private GraphType[] graphTypes = GraphType.values();

    @Option(
            names = {"-o", "--output"},
            description = "The output path to save the generated graphs and tables",
            required = false)
    private String outputPath;

    @Option(
            names = {"--port", "-p"},
            description = "The port of the webserver to create")
    private String portNumber;

    @Option(
            names = {"-c", "--config"},
            description = "The path of the configuration file to load from",
            required = false)
    private String configurationPath;

    @Option(
            names = {"--saveConfig", "--saveConfiguration"},
            description = "Saves the configuration to the given path.",
            required = false)
    private String configurationFilePathToSave;

    @Option(
            names = {"--groupBy"},
            description =
                    "Group logs with given type of group. Valid values: ${COMPLETION-CANDIDATES}")
    private GroupBy groupBy;

    @Override
    public Integer call() throws Exception {
        Configuration configuration;
        if (configurationPath == null) {
            configuration =
                    new Configuration(
                            this.authors,
                            this.startDate,
                            this.endDate,
                            this.inputPath,
                            this.branchName);
            if (portNumber != null) {
                configuration.addAnalyzerPlugin(
                        AnalyzerPluginType.web,
                        portNumber,
                        countCommitsPerUser,
                        countMergeCommitsPerUser,
                        groupBy,
                        graphTypes);
            }
            if (outputPath != null) {
                configuration.addAnalyzerPlugin(
                        AnalyzerPluginType.chartGenerator,
                        outputPath,
                        countCommitsPerUser,
                        countMergeCommitsPerUser,
                        groupBy,
                        graphTypes);
            }
            // TODO: Add GUI plugin here
            Runner runner = new Runner(configuration);
            runner.run();
        }
        // TODO: Load from configuration path
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new VisulogCLI()).execute(args);
        System.exit(exitCode);
    }
}
