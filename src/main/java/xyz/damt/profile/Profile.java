package xyz.damt.profile;

import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import xyz.damt.Practice;
import xyz.damt.handler.MongoHandler;
import xyz.damt.kit.Kit;
import xyz.damt.party.Party;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Profile {

    private final UUID uuid;
    private boolean build;
    private int kills, deaths, gamesPlayed, wins, loses, elo, coins;

    private ItemStack[] lastInventoryContents, lastArmorContents;

    private List<PotionEffect> lastPotionEffects = new ArrayList<>();
    private List<Party> partyInvites = new ArrayList<>();

    private double lastHealth, lastFood;

    private final MongoHandler mongoHandler = Practice.getInstance().getMongoHandler();
    private final HashMap<UUID, Kit> playersSentDuel = new HashMap<>();

    public Profile(UUID uuid) {
        this.uuid = uuid;

        this.load();
        Practice.getInstance().getProfileHandler().getProfileHashMap().put(uuid, this);
    }

    public void load() {
        Bukkit.getScheduler().runTaskAsynchronously(Practice.getInstance(), () -> {
            Document document = mongoHandler.getProfiles().find(new Document("_id", uuid.toString())).first();

            if (document == null) return;

            this.kills = document.getInteger("kills");
            this.deaths = document.getInteger("deaths");
            this.loses = document.getInteger("loses");
            this.gamesPlayed = document.getInteger("games");
            this.wins = document.getInteger("wins");
            this.elo = document.getInteger("elo");
            this.coins = document.getInteger("coins");
        });
    }

    public void save(boolean async) {

        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(Practice.getInstance(), () -> save(false));
        }

        Document document = mongoHandler.getProfiles().find(new Document("_id", uuid.toString())).first();

        if (document == null) {
            mongoHandler.getProfiles().insertOne(toBson());
            return;
        }

        mongoHandler.getProfiles().replaceOne(document, toBson(), new ReplaceOptions().upsert(true));
    }

    public Document toBson() {
        return new Document("_id", uuid.toString())
                .append("wins", wins)
                .append("loses", loses)
                .append("elo", elo)
                .append("games", gamesPlayed)
                .append("kills", kills)
                .append("deaths", deaths)
                .append("coins", coins);
    }

    @SneakyThrows
    public int getPing() {
        Object entityPlayer = getPlayer().getClass().getMethod("getHandle").invoke(getPlayer());
        return (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

}
