package xyz.damt.arena;

import lombok.Getter;
import org.bson.Document;
import xyz.damt.Practice;
import xyz.damt.kit.Kit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ArenaHandler {

    @Getter private final HashMap<String, Arena> arenaHashMap;
    private final Practice practice;

    public ArenaHandler(Practice practice) {
        this.practice = practice;
        this.arenaHashMap = new HashMap<>();

        this.load();
    }

    public void load() {
        practice.getServer().getScheduler().runTaskAsynchronously(practice, () -> {
           practice.getMongoHandler().getArena().find().forEach((Consumer<? super Document>) document ->
                   new Arena(document.getString("_id")));
        });
    }

    public Collection<Arena> getArenas(){
        return arenaHashMap.values();
    }

    public Set<Arena> getArenasOfKit(Kit kit) {
        return getArenas().stream().filter(arena -> arena.getKits().contains(kit)).collect(Collectors.toSet());
    }

    public Arena getArenaOfKit(Kit kit) {
        return getArenas().stream().filter(arena -> !arena.isBusy() && arena.getKits().contains(kit)).findFirst().orElse(null);
    }

    public Arena getArena(String name) {
        return arenaHashMap.get(name);
    }

}
