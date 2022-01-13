/*
	22015094 - Idil Saglam
*/
package up.visulog.config;

import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import up.visulog.pluginmanager.Plugin;
import up.visulog.pluginmanager.VisulogPlugin;

public class Runner {
    private final Configuration configuration;
    private final int timeout;
    private final TimeUnit timeoutUnit;

    /**
     * Create Runner instance with the given configuration
     *
     * @param configuration The configuration to pass to the runner
     * @param timeout The timeout value of the execution
     * @param timeoutUnit The unit of the timeout of the execution
     */
    public Runner(Configuration configuration, int timeout, TimeUnit timeoutUnit) {
        this.configuration = configuration;
        this.timeout = timeout;
        this.timeoutUnit = timeoutUnit;
    }

    /**
     * Create a new Runner instance with default time out of 1 hour
     *
     * @param configuration The configuration to pass in the runner instance
     */
    public Runner(Configuration configuration) {
        this(configuration, 1, TimeUnit.HOURS);
    }

    /**
     * Run all plugins using given number of threads
     *
     * @param nbThreads The number of threads to run plugins with
     */
    public void run(int nbThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(nbThreads);
        for (Plugin plugin : this.configuration.getAvailablePlugins().values()) {
            // Add plugins to the current thread pool
            executor.execute(plugin);
        }
        // This will make the executor accept no new threads
        // and finish all existing threads in the queue
        executor.shutdown();
        // Wait until all threads are finish
        try {
            executor.awaitTermination(this.timeout, this.timeoutUnit);
        } catch (InterruptedException ignore) {
            executor.shutdownNow();
        }
    }

    /** Run all plugins with the maximum available threads */
    public void run() {
        this.run(Thread.activeCount());
    }

    /**
     * Run plugins matching the given pattern in given number of threads
     *
     * @param pattern The string pattern to match
     */
    public void run(String pattern, int nbThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(nbThreads);
        this.configuration
                .getAvailablePlugins(pattern)
                .forEach(
                        (Entry<String, VisulogPlugin> pluginEntry) -> {
                            executor.execute(pluginEntry.getValue());
                        });
        // This will make the executor accept no new threads
        // and finish all existing threads in the queue
        executor.shutdown();
        // Wait until all threads are finish
        try {
            executor.awaitTermination(this.timeout, this.timeoutUnit);
        } catch (InterruptedException ignore) {
            executor.shutdownNow();
        }
    }

    /**
     * Run all plugins matching with the given pattern with the maximum number of threads
     *
     * @param pattern The string pattern to match
     */
    public void run(String pattern) {
        run(pattern, Thread.activeCount());
    }

    /** Run all plugins synchronously */
    public void runSync() {
        this.configuration.getAvailablePlugins().values().forEach(Plugin::run);
    }

    /**
     * Run plugins matching the given string pattern synchronously
     *
     * @param pattern The string pattern to match plugin name with
     */
    public void runSync(String pattern) {
        this.configuration
                .getAvailablePlugins(pattern)
                .forEach(
                        (Entry<String, VisulogPlugin> pluginEntry) -> {
                            pluginEntry.getValue().run();
                        });
    }
}
