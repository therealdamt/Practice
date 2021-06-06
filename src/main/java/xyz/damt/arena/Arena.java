package xyz.damt.arena;

import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import xyz.damt.Practice;
import xyz.damt.handler.MongoHandler;
import xyz.damt.kit.Kit;
import xyz.damt.util.LocationUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Arena {

    private String name;

    private Location positionOne,
            positionTwo, center,
            cornerOne, cornerTwo;

    private List<Kit> kits;
    private List<Block> blocksBuilt;
    private HashMap<Block, Material> blockBroken;
    private boolean isBusy;

    private final MongoHandler mongoHandler = Practice.getInstance().getMongoHandler();

    public Arena(String name) {
        this.name = name;

        this.kits = new ArrayList<>();
        this.blocksBuilt = new ArrayList<>();
        this.blockBroken = new HashMap<>();
        this.isBusy = false;

        this.load();
        Practice.getInstance().getArenaHandler().getArenaHashMap().put(name.toLowerCase(), this);
    }

    public void load() {
        Bukkit.getScheduler().runTaskAsynchronously(Practice.getInstance(), () -> {
            Document document = mongoHandler.getArena().find(new Document("_id", name)).first();

            if (document == null) return;

            this.positionOne = LocationUtil.stringToLocation(document.getString("pos1"));
            this.positionTwo = LocationUtil.stringToLocation(document.getString("pos2"));

            this.center = LocationUtil.stringToLocation(document.getString("center"));

            this.cornerOne = LocationUtil.stringToLocation(document.getString("corner1"));
            this.cornerTwo = LocationUtil.stringToLocation(document.getString("corner2"));

            this.kits = document.getList("kits", String.class).stream().map(string ->
                    Practice.getInstance().getKitHandler().getKit(string)).collect(Collectors.toList());
        });
    }

    public void save(boolean async) {
        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(Practice.getInstance(), () -> save(false));
            return;
        }

        Document document = mongoHandler.getArena().find(new Document("_id", name)).first();

        if (document == null) {
            mongoHandler.getArena().insertOne(toBson());
            return;
        }

        mongoHandler.getArena().replaceOne(document, toBson(), new ReplaceOptions().upsert(true));
    }

    public void rollback() {
        blocksBuilt.forEach(block -> {
            if (block != null) block.setType(null);
        });

        blockBroken.keySet().forEach(block -> {
            block.setType(blockBroken.get(block));
        });

        blockBroken.clear();
        blocksBuilt.clear();
    }

    public void remove(boolean async) {

        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(Practice.getInstance(), () -> remove(false));
            return;
        }

        this.rollback();
        mongoHandler.getArena().deleteOne(new Document("_id", name));
        Practice.getInstance().getArenaHandler().getArenaHashMap().remove(name);
    }

    public Document toBson() {
        return new Document("_id", name)
                .append("pos1", LocationUtil.locationToString(positionOne))
                .append("pos2", LocationUtil.locationToString(positionTwo))
                .append("center", LocationUtil.locationToString(center))
                .append("corner1", LocationUtil.locationToString(cornerOne))
                .append("corner2", LocationUtil.locationToString(cornerTwo))
                .append("kits", kits.stream().map(Kit::getName).collect(Collectors.toList()));
    }

}
