package xyz.damt.queue.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import xyz.damt.queue.Queue;
import xyz.damt.queue.QueueHandler;

public class QueueTask extends BukkitRunnable {

    private final QueueHandler queueHandler;

    public QueueTask(QueueHandler queueHandler) {
        this.queueHandler = queueHandler;
    }

    @Override
    public void run() {
        queueHandler.getQueues().forEach(Queue::move);
    }
}
