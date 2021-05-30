package xyz.damt.handler;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.damt.Practice;

@Getter @Setter
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

        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20D);
        player.setFoodLevel(20);
    }

}
