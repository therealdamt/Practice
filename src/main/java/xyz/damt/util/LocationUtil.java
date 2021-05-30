package xyz.damt.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import java.util.Objects;

@UtilityClass
public class LocationUtil {

    public String locationToString(Location location) {
        if (location == null) return "null";
        return location.getWorld().getName() + "," + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + "," + (int) location.getYaw() + "," + (int) + location.getPitch();
    }

    public Location stringToLocation(String string) {
        if (string == null || string.equalsIgnoreCase("null") || string.contains("null")) return null;

        String[] args  = string.split(",");
        return new Location(Bukkit.getWorld(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
    }

    public String chunkToString(Chunk chunk) {
        return chunk.getWorld().getName() + "," + chunk.getX() + "," + chunk.getZ();
    }

    public Chunk stringToChunk(String string) {
        String[] args = string.split(",");
        return Objects.requireNonNull(Bukkit.getWorld(args[0])).getChunkAt(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
    }


}
