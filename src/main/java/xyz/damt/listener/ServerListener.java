package xyz.damt.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.damt.Practice;
import xyz.damt.match.Match;

public class ServerListener implements Listener {

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();

        Match match = Practice.getInstance().getMatchHandler().getMatch(player.getUniqueId());
        if (match == null) e.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Practice.getInstance().getConfigHandler().getOtherHandler().JOIN_MESSAGE.forEach(e.getPlayer()::sendMessage);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }

}
