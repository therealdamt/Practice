package xyz.damt.arena;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import xyz.damt.Practice;
import xyz.damt.match.Match;

public class ArenaListener implements Listener {

    @EventHandler
    public void onPlayerBuildEvent(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Match match = Practice.getInstance().getMatchHandler().getMatch(player.getUniqueId());

        if (match == null) return;

        Arena arena = match.getArena();

        if (arena == null) return;

        arena.getBlocksBuilt().add(e.getBlock());
    }

}
