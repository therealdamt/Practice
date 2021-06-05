package xyz.damt.commands;

import me.vaperion.blade.command.annotation.*;
import org.bukkit.entity.Player;
import xyz.damt.Practice;
import xyz.damt.arena.Arena;
import xyz.damt.kit.Kit;
import xyz.damt.util.CC;

import java.util.Arrays;
import java.util.List;

public class ArenaCommand {

    private final List<String> helpMessage = Arrays.asList(
            "&7&m-------&b&lArena Help Commands&7&m-------",
            "&b/arena list &7- Lists all available arenas",
            "&b/arena create <name> &7- Creates an arena",
            "&b/arena delete <name> &7- Deletes an arena",
            "&b/arena tp <arena> &7- Teleports to the arena",
            "&b/arena corner <arena> <corner> &7- Sets the corner",
            "&b/arena position <arena> <number> &7- Sets the position",
            "&b/arena addkit <arena> <kit> &7- Adds a kit to the arena",
            "&b/arena removekit <arena> <kit> &7- Removes a kit from the arena",
            "&b/arena kitlist <arena> &7- Lists all the available kits for the arena",
            "&7&m-------&b&lArena Help Commands&7&m-------"
    );

    /**
     * Main Arena Command
     * @param player sender
     */
    @Command(value = "arena", async = true, quoted = false, description = "Arena Command")
    @Permission(value = "practice.arena", message = "You are not allowed to execute this command!")
    public void arenaCommand(@Sender Player player) {
        helpMessage.forEach(s -> player.sendMessage(CC.translate(s)));
    }

    /**
     * Arena Create Command
     * @param player sender
     * @param name of arena
     */

    @Command(value = "arena create", async = true, quoted = false, description = "Arena Create Command")
    @Permission(value = "practice.arena", message = "You are not allowed to execute this command!")
    public void arenaCreateCommand(@Sender Player player, @Name("name") String name) {
        Arena arena = Practice.getInstance().getArenaHandler().getArena(name);

        if (arena != null) {
            player.sendMessage(CC.translate("&cAn arena with that name already exists!"));
            return;
        }

        Arena arena1 = new Arena(name);
        player.sendMessage(CC.translate("&7Created the arena &b" + arena1.getName() + "&7! Please don't forget to set the following: " +
                "&b" + "position 1&7, &bposition 2&7, &bcenter"));
    }

    /**
     * Arena Set Position Command
     * @param player sender
     * @param arena arena selected
     * @param positionNumber set to the arena
     */

    @Command(value = "arena position", async = true, quoted = false, description = "Arena Set Position Command")
    @Permission(value = "practice.arena", message = "You are not allowed to execute this command!")
    public void arenaPositionCommand(@Sender Player player, @Name("arena") Arena arena,
                                     @Name("1/2") @Range(min = 1, max = 2) int positionNumber) {
        switch (positionNumber) {
            case 1:
                arena.setPositionOne(player.getLocation());
                player.sendMessage(CC.translate("&7Set &bposition one &7for the arena &b" + arena.getName() + "&7! Don't forget to set the &bcenter &7and" +
                        " &bposition two&7 if not set!"));
                break;
            case 2:
                arena.setPositionTwo(player.getLocation());
                player.sendMessage(CC.translate("&7Set &bposition two &7for the arena &b" + arena.getName() + "&7! Don't forget to set the &bcenter &7and" +
                        " &bposition one&7 if not set!"));
                break;
            default:
                player.sendMessage(CC.translate("&7There are only two positions: &b1&7,&b2&7!"));
                break;
        }
    }

    /**
     * Arena Delete Command
     * @param player sender
     * @param arena to delete
     */

    @Command(value = "arena delete", async = true, quoted = false, description = "Arena Delete Command")
    @Permission(value = "practice.arena", message = "You are not allowed to execute this command!")
    public void arenaDeleteCommand(@Sender Player player, @Name("arena") Arena arena) {
        arena.remove(true);
        player.sendMessage(CC.translate("&7Deleted the arena &b" + arena.getName() + "&7!"));
    }

    /**
     * Arena Center Command
     * @param player sender
     * @param arena to set the center for
     */

    @Command(value = "arena center", async = true, quoted = false, description = "Arena Center Command")
    @Permission(value = "practice.arena", message = "You are not allowed to execute this command!")
    public void arenaCenterCommand(@Sender Player player, @Name("arena") Arena arena) {
        arena.setCenter(player.getLocation());
        player.sendMessage(CC.translate("&7Set the &bcenter &7for the arena &b" + arena.getName() + "&7! Please don't forget to set " +
                "&bposition one &7and &bposition two &7if not already set!"));
    }

    /**
     * Arena Corner Command
     * @param player sender
     * @param arena to edit
     * @param cornerNumber to set to player's location
     */

    @Command(value = "arena corner", async = true, quoted = false, description = "Arena Corner Command")
    @Permission(value = "practice.arena", message = "You are not allowed to execute this command!")
    public void arenaCornerCommand(@Sender Player player, @Name("arena") Arena arena,
                                   @Name("1/2") @Range(min = 1, max = 2) int cornerNumber) {
        switch (cornerNumber) {
            case 1:
                arena.setCornerOne(player.getLocation());
                player.sendMessage(CC.translate("&7Set &bcorner one &7for the arena &b" + arena.getName()));
                break;
            case 2:
                arena.setCornerTwo(player.getLocation());
                player.sendMessage(CC.translate("&7Set &bcorner two &7for the arena &b" + arena.getName()));
                break;
            default:
                player.sendMessage(CC.translate("&7There are only two corners: &b1&7,&b2&7!"));
                break;
        }
    }

    /**
     * Arena Add Kit Command
     * @param player sender
     * @param arena to add the kit to
     * @param kit added to the arena
     */

    @Command(value = "arena addkit", async = true, quoted = false, description = "Arena Add kit Command")
    @Permission(value = "practice.arena", message = "You are not allowed to execute this command!")
    public void arenaAddKitCommand(@Sender Player player, @Name("arena") Arena arena, @Name("kit") Kit kit) {
        if (arena.getKits().contains(kit)) {
            player.sendMessage(CC.translate("&cThat kit is already added for this arena!"));
            return;
        }

        arena.getKits().add(kit);
        player.sendMessage(CC.translate("&7Added the kit &b" + kit.getName() + "&7 to the arena &b" + arena.getName()));
    }

    /**
     * Arena Remove Kit Command
     * @param player sender
     * @param arena to remove the kit from
     * @param kit to remove from the arena
     */

    @Command(value = "arena removekit", async = true, quoted = false, description = "Arena Remove kit Command")
    @Permission(value = "practice.arena", message = "You are not allowed to execute this command!")
    public void arenaRemoveKitCommand(@Sender Player player, @Name("arena") Arena arena, @Name("kit") Kit kit) {
        if (!arena.getKits().contains(kit)) {
            player.sendMessage(CC.translate("&cThat kit is not included in the arena's list of kits!"));
            return;
        }

        arena.getKits().remove(kit);
        player.sendMessage(CC.translate("&7Removed the kit &b" + kit.getName() + "&7 from the arena &b" + arena.getName()));
    }

    /**
     * Arena Kit List Command
     * @param player sender
     * @param arena to find the kits for
     */

    @Command(value = "arena kitlist", async = true, quoted = false, description = "Arena Remove Kit List Command")
    @Permission(value = "practice.arena", message = "You are not allowed to execute this command!")
    public void arenaKitListCommand(@Sender Player player, @Name("arena") Arena arena) {
        if (arena.getKits().isEmpty()) {
            player.sendMessage(CC.translate("&cThere are not kits that are attached to this arena!"));
            return;
        }

        player.sendMessage(CC.translate("&b&l" + arena.getName() + "'s Kits"));
        arena.getKits().forEach(kit -> {
            player.sendMessage(CC.translate("&7- &b" + kit.getName()));
        });
    }

    /**
     * Arena List Command
      * @param player sender
     */

    @Command(value = "arena list", async = true, quoted = false, description = "Arena List Command")
    @Permission(value = "practice.arena", message = "You are not allowed to execute this command!")
    public void arenaListCommand(@Sender Player player) {
        if (Practice.getInstance().getArenaHandler().getArenas().isEmpty()) {
            player.sendMessage(CC.translate("&cThere are no arenas registered!"));
            return;
        }

        player.sendMessage(CC.translate("&b&lRegistered Arenas"));
        Practice.getInstance().getArenaHandler().getArenas().forEach(arena -> {
            player.sendMessage(CC.translate("&7- &b" + arena.getName()));
        });
    }

    /**
     * Arena Teleport Command
     * @param player sender
     * @param arena to teleport to
     */

    @Command(value = "arena tp", quoted = false, description = "Arena Teleport Command")
    @Permission(value = "practice.arena", message = "You are not allowed to execute this command!")
    public void arenaTpComand(@Sender Player player, @Name("arena") Arena arena) {
        if (arena.getCenter() == null) {
            player.sendMessage(CC.translate("&cThis arena is not setup yet!"));
            return;
        }

        player.teleport(arena.getCenter());
        player.sendMessage(CC.translate("&7Teleported to the arena &b" + arena.getName() + "&7!"));
    }
}
