package xyz.damt.queue;

import lombok.Getter;
import org.bukkit.entity.Player;
import xyz.damt.Practice;
import xyz.damt.kit.Kit;
import xyz.damt.queue.tasks.QueueTask;

import java.util.Collection;
import java.util.HashMap;

public class QueueHandler {

    private final Practice practice;
    @Getter private final HashMap<Kit, Queue> kitQueueHashMap;

    public QueueHandler(Practice practice) {
        this.practice = practice;
        this.kitQueueHashMap = new HashMap<>();

        new QueueTask(this).runTaskTimerAsynchronously(practice, 20L, 20L);
    }

    public Collection<Queue> getQueues() {
        return this.kitQueueHashMap.values();
    }

    public Queue getQueue(Kit kit) {
        return kitQueueHashMap.get(kit);
    }

    public Queue getQueueOfPlayer(Player player) {
        return getQueues().stream().filter(queue -> queue.getPlayersInQueue().contains(player)).findFirst().orElse(null);
    }

    public int getPlayersInQueueSize() {
        int i = 0;

        for (Queue queue : getQueues()) {
            i += queue.getPlayersInQueue().size();
        }

        return i;
    }

}
