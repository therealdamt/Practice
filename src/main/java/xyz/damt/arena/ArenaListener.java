package xyz.damt.arena;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import xyz.damt.match.Match;
import xyz.damt.util.framework.listener.ListenerAdapter;

public class ArenaListener extends ListenerAdapter {

    @EventHandler
    public void onPlayerBuildEvent(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Match match = practice.getMatchHandler().getMatch(player.getUniqueId());

        if (match == null) return;

        Arena arena = match.getArena();

        if (arena == null) return;

        arena.getBlocksBuilt().add(e.getBlock());
    }

}
