package xyz.damt.profile;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.damt.Practice;

public class ProfileListener implements Listener {

    @EventHandler
    public void onAsyncPlayerJoin(AsyncPlayerPreLoginEvent e) {
        if (!Practice.getInstance().getProfileHandler().hasProfile(e.getUniqueId())) {
            new Profile(e.getUniqueId()).save(true);
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        Practice.getInstance().getProfileHandler().getProfile(e.getPlayer().getUniqueId()).save(true);
    }

}
