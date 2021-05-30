package xyz.damt.kit;

import lombok.Getter;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import xyz.damt.Practice;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class KitHandler {

    @Getter private final HashMap<String, Kit> kitHashMap = new HashMap<>();
    private final Practice practice;

    public KitHandler(Practice practice) {
        this.practice = practice;
    }

    public void load() {
        practice.getServer().getScheduler().runTaskAsynchronously(practice, () -> {
            practice.getMongoHandler().getKits().find().forEach((Consumer<? super Document>) document ->
                new Kit(document.getString("_id")));
        });
    }

    public Collection<Kit> getKits() {
        return this.kitHashMap.values();
    }

    public Kit getKit(String name) {
        return kitHashMap.get(name.toLowerCase());
    }

    public List<Kit> getUnrankedKits() {
        return getKits().stream().filter(kit -> !kit.isElo()).collect(Collectors.toList());
    }

    public List<Kit> getRankedKits() {
        return getKits().stream().filter(kit -> kit.isElo()).collect(Collectors.toList());
    }

    public Kit getKit(ItemStack stack, boolean elo) {
        return getKits().stream().filter(kit -> kit.getItem().equals(stack) && kit.isElo() == elo).findFirst().orElse(null);
    }

}
