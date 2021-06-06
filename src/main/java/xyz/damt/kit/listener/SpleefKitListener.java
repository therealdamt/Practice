package xyz.damt.kit.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.damt.Practice;
import xyz.damt.match.Match;

public class SpleefKitListener implements Listener {

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
        if (!e.getPlayer().getLocation().getBlock().isLiquid()) return;
        Player player = e.getPlayer();

        Bukkit.getScheduler().runTaskAsynchronously(Practice.getInstance(), () -> {
            Match match = Practice.getInstance().getMatchHandler().getMatch(player.getUniqueId());
            if (match == null || !match.getKit().getName().replace("Elo", "").equalsIgnoreCase("spleef")) return;
        });

        Match match = Practice.getInstance().getMatchHandler().getMatch(player.getUniqueId());
        if (match != null) match.stop(match.getOpponent(player).getUniqueId(), 3);
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;

        Player player = (Player) e.getEntity();
        Match match = Practice.getInstance().getMatchHandler().getMatch(player.getUniqueId());

        if (match == null || !match.getKit().getName().replace("Elo", "").equalsIgnoreCase("spleef")) return;

        e.setDamage(0);
    }

}
