package xyz.damt.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;

@UtilityClass
public class PacketUtils {

    private void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendTitle(JavaPlugin javaPlugin, Player player, String titleText, String subtitleText,
                          int enterTime, int stayTime, int leaveTime) {
        javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> {
            try {
                Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
                Object titleChat = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + titleText + "\"}");

                Object enumSubtitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
                Object subtitleChat = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                        .invoke(null, "{\"text\":\"" + subtitleText + "\"}");

                Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
                Object titlePacket = titleConstructor.newInstance(enumTitle, titleChat, enterTime, stayTime, leaveTime);
                Object subtitlePacket = titleConstructor.newInstance(enumSubtitle, subtitleChat, enterTime, stayTime, leaveTime);

                sendPacket(player, titlePacket);
                sendPacket(player, subtitlePacket);

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

}
