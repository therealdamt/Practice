package xyz.damt.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.damt.queue.Queue;

public class QueueLeaveEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final Player player;
    private final Queue queue;

    public QueueLeaveEvent(Queue queue, Player player) {
        this.player = player;
        this.queue = queue;
    }

    public Player getPlayer() {
        return player;
    }

    public Queue getQueue() {
        return queue;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
