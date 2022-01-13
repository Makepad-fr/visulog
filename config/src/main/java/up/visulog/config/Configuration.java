package up.visulog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.Map.Entry;
import java.util.stream.Stream;
import up.visulog.analyzer.AnalyzerPluginType;
import up.visulog.analyzer.GraphType;
import up.visulog.git.CommitExtractor;
import up.visulog.pluginmanager.VisulogPlugin;

public class Configuration {
    private final Map<String, VisulogPlugin> plugins;
    private final CommitExtractor extractor;
    // private final boolean createWebServer, countTotalCommits, countCommitsPerUser, countsMergeCommitsPerUser, countsMergeCommits;

    /**
     * Creates a configuration file from given parameters
     * @param authors An array of authors, it can be either username or email address
     * @param startDate The start date to filter commits to analyze
     * @param endDate The end date to filter commits to analyze
     * @param path The path of the git repository
     * @param branchName The name of the branch to use
     */
    @SafeVarargs
    public Configuration(
        String[] authors,
        Date startDate,
        Date endDate,
        String path,
        String branchName,
        Entry<String, VisulogPlugin>... plugins) {
        this.extractor = new CommitExtractor(authors, startDate, endDate, path, branchName);
        this.plugins = new HashMap<>();
        for (Entry<String, VisulogPlugin> p: plugins) {
            p.getValue().setCommits(extractor.extract());
            this.plugins.put(p.getKey(), p.getValue());
        }
    }

    /**
     * Creates a new configuration from given configuration file path
     * @param configurationFilePath The configuration file path
     */
    // TODO: This needs attention as VisulogPlugins can have different parameters
    /*public Configuration(Path configurationFilePath) {
        // TODO: Read the yaml file
    }*/

    /**
     * Registers a plugin to the current configuration
     * @param pluginName The name of the plugin to register
     * @param plugin The plugin to register
     */
    public void registerPlugin(String pluginName, VisulogPlugin plugin) {
        this.plugins.put(pluginName, plugin);
    }

    /**
     * Return the map of the available plugins on the current configuration
     * @return The map of the current plugins available on the configuration
     */
    public Map<String, VisulogPlugin> getAvailablePlugins() {
        return this.plugins;
    }

    /**
     * Get available plugins mathing the given String pattern
     * @param pattern The string pattern to match the plugin name with
     * @return The Stream of map entries matching the given string pattern
     */
    public Stream<Entry<String, VisulogPlugin>> getAvailablePlugins(String pattern) {
        return this.plugins.entrySet().stream().filter((Entry<String, VisulogPlugin> pluginEntry) -> {
            return pluginEntry.getKey().matches(pattern);
        });
    }

    /**
     * Saves the current configuration as a yaml file
     * @param configurationOutputPath The path of the configuration file to save
     * @return True if the configuration file saved successfully, false if not
     */
    public boolean save(String configurationOutputPath) {
        // FIXME: This will probably won't work
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(Feature.WRITE_DOC_START_MARKER));
        try {
            mapper.writeValue(new File(configurationOutputPath), this);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Adds a new analyzer plugin to the current configuration
     * @param analyzerPluginType The type of the analyzer plugin
     * @param param The parameter will be passed to the analyzer plugin
     * @param countsCommitsPerUser  A boolean indicating that if commits per user will be counted
     * @param countMergeCommitsPerUser A boolean indicating that if merge commits per user will be counted
     * @param graphTypes The type of the graph to generate
     */
    public <T> void addAnalyzerPlugin(AnalyzerPluginType analyzerPluginType, T param, boolean countsCommitsPerUser, boolean countMergeCommitsPerUser, GraphType[] graphTypes) {

    }
}
