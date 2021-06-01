package xyz.damt.commands;

import me.vaperion.blade.command.annotation.Combined;
import me.vaperion.blade.command.annotation.Command;
import me.vaperion.blade.command.annotation.Permission;
import me.vaperion.blade.command.annotation.Sender;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import xyz.damt.util.CC;

public class EssentialCommands {

    @Command(value = "gmc", quoted = false, description = "Gamemode Creative Command")
    @Permission(value = "practice.gamemode", message = "You are not allowed to execute this command!")
    public void gmc(@Sender Player player) {
        player.setGameMode(GameMode.CREATIVE);
        player.sendMessage(CC.translate("&aYou have been set in creative mode!"));
    }

    @Command(value = "gms", quoted = false, description = "Gamemode Survival Command")
    @Permission(value = "practice.gamemode", message = "You are not allowed to execute this command!")
    public void gms(@Sender Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.sendMessage(CC.translate("&aYou have been set in survival mode!"));
    }

    @Command(value = "gma", quoted = false, description = "Gamemode Adventure Command")
    @Permission(value = "practice.gamemode", message = "You are not allowed to execute this command!")
    public void gma(@Sender Player player) {
        player.setGameMode(GameMode.ADVENTURE);
        player.sendMessage(CC.translate("&aYou have been set in adventure mode!"));
    }

    @Command(value = "clear", quoted = false, description = "Clear Command")
    @Permission(value = "practice.clear", message = "You are not allowed to execute this command!")
    public void clear(@Sender Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.sendMessage(CC.translate("&aYou have cleared your inventory!"));
    }

    @Command(value = "more", quoted = false, description = "More Command")
    @Permission(value = "practice.more", message = "You are not allowed to execute this command!")
    public void more(@Sender Player player) {
        if (player.getItemInHand() == null) {
            player.sendMessage(CC.translate("&cPlease hold something to increase it's amount!"));
            return;
        }

        player.getItemInHand().setAmount(64);
        player.sendMessage(CC.translate("&aBuffed that amount for ya!"));
    }

    @Command(value = "broadcast", quoted = false, description = "Broadcast Command")
    @Permission(value = "practice.broadcast", message = "You are not allowed to execute this command!")
    public void broadcast(@Sender Player player, @Combined String broadcast) {
        Bukkit.getServer().broadcastMessage(CC.translate(broadcast));
    }
}
