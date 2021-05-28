package xyz.damt.kit;

import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import xyz.damt.Practice;
import xyz.damt.handler.MongoHandler;
import xyz.damt.queue.Queue;
import xyz.damt.queue.QueueType;
import xyz.damt.util.Serializer;

import java.io.IOException;

@Getter
@Setter
public class Kit {

    private String name;

    private ItemStack[] contents, armorContents;
    private KitType kitType;
    private boolean elo;

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

            try {
                this.armorContents = Serializer.itemStackArrayFromBase64(document.getString("armor"));
                this.contents = Serializer.itemStackArrayFromBase64(document.getString("contents"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public Document toBson() {
        return new Document("_id", name)
                .append("contents", Serializer.itemStackArrayToBase64(contents))
                .append("armor", Serializer.itemStackArrayToBase64(armorContents))
                .append("type", kitType.toString())
                .append("elo", elo);
    }


}
