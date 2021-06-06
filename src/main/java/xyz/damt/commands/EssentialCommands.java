package xyz.damt.commands;

import me.vaperion.blade.command.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import xyz.damt.Practice;
import xyz.damt.match.Match;
import xyz.damt.profile.Profile;
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

    @Command(value = "build", quoted = false, description = "Build Command")
    @Permission(value = "practice.build", message = "You are not allowed to execute this command!")
    public void build(@Sender Player player) {
        Profile profile = Practice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        if (profile.isBuild()) {
            profile.setBuild(false);
        } else {
            profile.setBuild(true);
        }

        player.sendMessage(CC.translate("&7You have set your build mode to &b" + profile.isBuild()));
    }

    @Command(value = "ping", quoted = false, async = true, description = "Ping Command")
    public void ping(@Sender Player player) {
        Profile profile = Practice.getInstance().getProfileHandler().getProfile(player.getUniqueId());
        player.sendMessage(CC.translate("&7Your ping is &b" + profile.getPing() + "&7!"));
    }

    @Command(value = "spawn", quoted = false, async = true, description = "Spawn Command")
    public void spawn(@Sender Player player) {
        Match match = Practice.getInstance().getMatchHandler().getMatch(player.getUniqueId());

        if (match != null) {
            player.sendMessage(CC.translate("&cYou may not do this whilst inside of a match!"));
            return;
        }

        player.teleport(Practice.getInstance().getServerHandler().getSpawnLocation());
        player.sendMessage(CC.translate("&7Teleported to &bspawn&7!"));
    }

    @Command(value = "setspawn", quoted = false, async = true, description = "SetSpawn Command")
    @Permission(value = "practice.setspawn", message = "You are not allowed to execute this command!")
    public void setSpawn(@Sender Player player) {
        Practice.getInstance().getServerHandler().setSpawnLocation(player.getLocation());
        player.sendMessage(CC.translate("&7Set the spawn loaction to &byour location&7!"));
    }

    @Command(value = "debug", quoted = false, async = true, description = "Debug Command")
    @Permission(value = "practice.op", message = "You are not allowed to execute this command!")
    public void debugCommand(@Sender Player player, @Name("wins") int wins, @Name("loses") int loses,
                             @Name("kills") int kills, @Name("deaths") int deaths, @Name("coins") int coins) {
        try {
            Profile profile = Practice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

            profile.setCoins(coins);
            profile.setKills(kills);
            profile.setWins(wins);
            profile.setLoses(loses);
            profile.setDeaths(deaths);

            player.sendMessage(CC.translate("&7The debug command has worked &b%100&7!"));
        } catch (Exception e) {
            player.sendMessage(CC.translate("&cThe exception " + e.getCause() + " has been made!"));
            e.printStackTrace();
        }
    }

}
