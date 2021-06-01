package xyz.damt.handler;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.damt.Practice;
import xyz.damt.util.CC;
import xyz.damt.util.ItemBuilder;

@Getter
@Setter
public class ServerHandler {

    private final Practice practice;

    private Location spawnLocation;

    public ServerHandler(Practice practice) {
        this.practice = practice;
        this.spawnLocation = practice.getServer().getWorld("world").getSpawnLocation();
    }

    public void giveSpawnItems(Player player) {
        player.closeInventory();

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20D);
        player.setFoodLevel(20);

        player.getInventory().setItem(0, new ItemBuilder(Material.IRON_SWORD).name(CC.translate("&c&lUnranked Queue")).build());
        player.getInventory().setItem(1, new ItemBuilder(Material.DIAMOND_SWORD).name(CC.translate("&b&lRanked Queue")).build());
        player.getInventory().setItem(4, new ItemBuilder(Material.PAPER).name(CC.translate("&e&lStatistics")).build());
        player.getInventory().setItem(7, new ItemBuilder(Material.COMPASS).name(CC.translate("&2&lDuel Requests")).build());
        player.getInventory().setItem(8, new ItemBuilder(Material.EMERALD).name(CC.translate("&6&lCoin Shop")).build());
    }

}
