package xyz.damt.profile;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.damt.util.framework.listener.ListenerAdapter;

public class ProfileListener extends ListenerAdapter {

    @EventHandler
    public void onAsyncPlayerJoin(AsyncPlayerPreLoginEvent e) {
        if (!practice.getProfileHandler().hasProfile(e.getUniqueId())) {
            new Profile(e.getUniqueId()).save(true);
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        practice.getProfileHandler().getProfile(e.getPlayer().getUniqueId()).save(true);
    }

}
