package xyz.damt.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import xyz.damt.Practice;

public class MongoSaveTask extends BukkitRunnable {

    private final Practice practice;

    public MongoSaveTask(Practice practice) {
        this.practice = practice;
    }

    @Override
    public void run() {
        practice.getArenaHandler().getArenas().forEach(arena -> arena.save(true));
        practice.getKitHandler().getKits().forEach(kit -> kit.save(true));
    }
}
