package xyz.damt.kit;

import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.damt.Practice;
import xyz.damt.handler.MongoHandler;
import xyz.damt.queue.Queue;
import xyz.damt.queue.QueueType;
import xyz.damt.util.CC;
import xyz.damt.util.ItemBuilder;
import xyz.damt.util.Serializer;

import java.io.IOException;

@Getter
@Setter
public class Kit {

    private String name;

    private ItemStack[] contents, armorContents = new ItemStack[]{};
    private KitType kitType = KitType.NO_BUILD;
    private boolean elo;

    private Material icon = Material.GOLD_SWORD;
    private String color = "&b&l";

    private final Queue queue;
    private final MongoHandler mongoHandler = Practice.getInstance().getMongoHandler();

    public Kit(String name) {
        this.name = name;

        this.load();
        Practice.getInstance().getKitHandler().getKitHashMap().put(name.toLowerCase(), this);

        this.queue = new Queue(this, elo ? QueueType.ELO : QueueType.NORMAL);
    }

    public void save(boolean async) {

        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(Practice.getInstance(), () -> save(false));
            return;
        }

        Document document = mongoHandler.getKits().find(new Document("_id", name)).first();

        if (document == null) {
            mongoHandler.getKits().insertOne(toBson());
            return;
        }

        mongoHandler.getKits().replaceOne(document, toBson(), new ReplaceOptions().upsert(true));
    }

    public void load() {
        Bukkit.getScheduler().runTaskAsynchronously(Practice.getInstance(), () -> {
            Document document = mongoHandler.getKits().find(new Document("_id", name)).first();

            if (document == null) return;

            this.kitType = KitType.valueOf(document.getString("type").toUpperCase());
            this.elo = document.getBoolean("elo");
            this.color = document.getString("color");
            this.icon = Material.valueOf(document.getString("icon").toUpperCase());

            try {
                this.armorContents = Serializer.itemStackArrayFromBase64(document.getString("armor"));
                this.contents = Serializer.itemStackArrayFromBase64(document.getString("contents"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void remove(boolean async) {

        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(Practice.getInstance(), () -> remove(false));
            return;
        }

        Practice.getInstance().getKitHandler().getKitHashMap().remove(name);
        Practice.getInstance().getArenaHandler().getArenasOfKit(this).forEach(arena -> arena.getKits().remove(this));
        mongoHandler.getKits().deleteOne(new Document("_id", name));
    }

    public Document toBson() {
        return new Document("_id", name)
                .append("contents", Serializer.itemStackArrayToBase64(contents))
                .append("icon", icon.toString())
                .append("color", color)
                .append("armor", Serializer.itemStackArrayToBase64(armorContents))
                .append("type", kitType.toString())
                .append("elo", elo);
    }

    public ItemStack getItem() {
        return new ItemBuilder(icon).name(elo ? CC.translate(color + name + "&7&l(Elo)") : CC.translate(color + name))
                .lore(color.replace("&l", "") + "Players In Queue&7: " + queue.getPlayersInQueue().size()).build();
    }

}
