/*
	22015094 - Idil Saglam
*/
package up.visulog.pluginmanager;

import java.io.Serializable;

@FunctionalInterface
public interface Plugin extends Runnable, Serializable {

    void run();
}
