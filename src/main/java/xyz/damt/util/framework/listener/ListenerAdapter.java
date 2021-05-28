package xyz.damt.util.framework.listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.Practice;

public abstract class ListenerAdapter implements Listener {

    public final Practice practice = Practice.getInstance();

    public void register(JavaPlugin javaPlugin) {
        javaPlugin.getServer().getPluginManager().registerEvents(this, javaPlugin);
    }

}
