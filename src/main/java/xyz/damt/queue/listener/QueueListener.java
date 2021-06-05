package xyz.damt.queue.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.damt.Practice;
import xyz.damt.events.QueueJoinEvent;
import xyz.damt.events.QueueLeaveEvent;
import xyz.damt.match.Match;
import xyz.damt.queue.Queue;

public class QueueListener implements Listener {

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        Queue queue = Practice.getInstance().getQueueHandler().getQueue(e.getPlayer().getUniqueId());
        if (queue != null) queue.remove(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQueueEvent(QueueJoinEvent e) {
        Practice.getInstance().getServerHandler().giveQueueItems(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQueueLeave(QueueLeaveEvent e) {
        if (e.getPlayer() != null) Practice.getInstance().getServerHandler().giveSpawnItems(e.getPlayer());
    }

}
