package up.visulog.pluginmanager;

import java.io.Serializable;

@FunctionalInterface
public interface Plugin extends Runnable, Serializable {

  void run();
}
