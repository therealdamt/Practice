package xyz.damt.queue.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.damt.Practice;
import xyz.damt.queue.Queue;

public class QueueListener implements Listener {

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        Queue queue = Practice.getInstance().getQueueHandler().getQueue(e.getPlayer().getUniqueId());
        if (queue != null) queue.remove(e.getPlayer());
    }

}
