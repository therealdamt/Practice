package xyz.damt.commands;

import me.vaperion.blade.command.annotation.Command;
import me.vaperion.blade.command.annotation.Name;
import me.vaperion.blade.command.annotation.Permission;
import me.vaperion.blade.command.annotation.Sender;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.damt.Practice;
import xyz.damt.kit.Kit;
import xyz.damt.kit.KitType;
import xyz.damt.util.CC;

import java.util.Arrays;
import java.util.List;

public class KitCommand {

    private final List<String> helpMessage = Arrays.asList(
            "&7&m-------&b&lKit Help Commands&7&m-------",
            "&b/kit create <name> <elo> &7- Creates a kit based on your inventory",
            "&b/kit edit <kit> &7- Edits a kit to your inventory",
            "&b/kit delete <kit> &7- Deletes a kit",
            "&b/kit seticon <kit> <material> &7- Sets an icon to your kit",
            "&b/kit setcolor <kit <color> &7- Sets a color for your kit",
            "&b/kit build <kit> &7- Makes the kit able to build",
            "&b/kit setpriority <kit> <priority> &7- Sets the priority of a kit",
            "&b/kit load <kit> &7- Loads a kit into your inventory",
            "&b/kit list &7- Lists all available registered kits",
            "&7&m-------&b&lKit Help Commands&7&m-------"
    );

    /**
     * Main Kit Command
     *
     * @param player sender
     */

    @Command(value = {"kit", "kits"}, quoted = false, async = true, description = "Kit Command")
    @Permission(value = "practice.kit", message = "You are not allowed to execute this command!")
    public void kitCommand(@Sender Player player) {
        helpMessage.forEach(s -> player.sendMessage(CC.translate(s)));
    }

    /**
     * Kit Create Command
     *
     * @param player sender
     * @param name   kit name
     * @param elo    checks if arena is elo
     */

    @Command(value = {"kit create", "kits create"}, quoted = false, async = true, description = "Kit Create Command")
    @Permission(value = "practice.kit", message = "You are not allowed to execute this command!")
    public void kitCreateCommand(@Sender Player player, @Name("name") String name, @Name("elo") boolean elo) {
        Kit kit = elo ? Practice.getInstance().getKitHandler().getKit(name + "elo")
                : Practice.getInstance().getKitHandler().getKit(name);

        if (kit != null) {
            player.sendMessage(CC.translate("&cA kit with that name already exists!"));
            return;
        }

        Kit newKit = new Kit(name, elo);

        newKit.setContents(player.getInventory().getContents());
        newKit.setArmorContents(player.getInventory().getArmorContents());

        newKit.save(true);
        player.sendMessage(CC.translate("&7You have created a kit with the name &b" + newKit.getName()));
    }

    /**
     * Kit Delete Command
     *
     * @param player sender
     * @param kit    to delete
     */

    @Command(value = {"kit delete", "kits delete"}, quoted = false, async = true, description = "Kit Delete Command")
    @Permission(value = "practice.kit", message = "You are not allowed to execute this command!")
    public void kitDeleteCommand(@Sender Player player, @Name("kit") Kit kit) {
        kit.remove(true);
        player.sendMessage(CC.translate("&7You have delete a kit with the name &b" + kit.getName()));
    }

    /**
     * Kit Edit Command
     *
     * @param player sender
     * @param kit    to edit
     */

    @Command(value = {"kit edit", "kits edit"}, quoted = false, async = true, description = "Kit Edit Command")
    @Permission(value = "practice.kit", message = "You are not allowed to execute this command!")
    public void kitEditCommand(@Sender Player player, @Name("kit") Kit kit) {
        kit.setArmorContents(player.getInventory().getArmorContents());
        kit.setContents(player.getInventory().getContents());

        player.sendMessage(CC.translate("&7You have edited the kit &b" + kit.getName()));
    }

    /**
     * Kit Set Icon Command
     *
     * @param player   sender
     * @param kit      to set icon for
     * @param material icon
     */

    @Command(value = {"kit seticon", "kits seticon"}, quoted = false, async = true, description = "Kit Set Icon Command")
    @Permission(value = "practice.kit", message = "You are not allowed to execute this command!")
    public void kitSetIcon(@Sender Player player, Kit kit, @Name("icon") Material material) {
        kit.setIcon(material);
        player.sendMessage(CC.translate("&7You have set the icon of the kit &b" + kit.getName() + "&7 to &b" + material.toString()));
    }

    /**
     * Kit Set Color Command
     *
     * @param player sender
     * @param kit    to set the color for
     * @param color  string color
     */

    @Command(value = {"kit setcolor", "kits setcolor"}, quoted = false, async = true, description = "Kit Set Color Command")
    @Permission(value = "practice.kit", message = "You are not allowed to execute this command!")
    public void kitSetColor(@Sender Player player, Kit kit, @Name("color") String color) {
        kit.setColor(color);
        player.sendMessage(CC.translate("&7You have set the color of the kit &b" + kit.getName() + "&7 to the color " + color + "color&7!"));
    }

    /**
     * Kit Build Command
     *
     * @param player sender
     * @param kit    to set builder
     */

    @Command(value = {"kit build", "kits build"}, quoted = false, async = true, description = "Kit Build Command")
    @Permission(value = "practice.kit", message = "You are not allowed to execute this command!")
    public void kitBuildCommand(@Sender Player player, @Name("kit") Kit kit) {
        switch (kit.getKitType()) {
            case BUILD:
                kit.setKitType(KitType.NO_BUILD);
                player.sendMessage(CC.translate("&7You have &bdisabled &7building for the kit &b" + kit.getName()));
                break;
            case NO_BUILD:
                kit.setKitType(KitType.BUILD);
                player.sendMessage(CC.translate("&7You havbe &benabled &7building for the kit &b" + kit.getName()));
                break;
            default:
                break;
        }
    }

    /**
     * Kit Load Command
     *
     * @param player sender
     * @param kit    to load
     */

    @Command(value = {"kit load", "kits load"}, quoted = false, async = false, description = "Kit Load Command")
    @Permission(value = "practice.kit", message = "You are not allowed to execute this command!")
    public void kitLoadCommand(@Sender Player player, @Name("kit") Kit kit) {
        player.getInventory().setContents(kit.getContents());
        player.getInventory().setArmorContents(kit.getArmorContents());

        player.sendMessage(CC.translate("&7Loaded the kit &b" + kit.getName() + "&7!"));
    }

    /**
     * Kit Set Priority command
     * @param player sender
     * @param kit to set priority
     * @param priority the thing
     */

    @Command(value = {"kit setpriority", "kits setpriority"}, quoted = false, async = true, description = "Kit Set Priority Command")
    @Permission(value = "practice.kit", message = "You are not allowed to execute this command!")
    public void kitPrioritycommand(@Sender Player player, @Name("kit") Kit kit, @Name("priority") int priority) {
        kit.setPriority(priority);
        player.sendMessage(CC.translate("&7Set the priority of &b" + kit.getName() + "&7 to &b" + priority + "&7!"));
    }

    /**
     * Kit List Command
     *
     * @param player sender
     */

    @Command(value = {"kit list", "kits list"}, quoted = false, async = true, description = "Kit List Command")
    @Permission(value = "practice.kit", message = "You are not allowed to execute this command!")
    public void kitListCommand(@Sender Player player) {
        player.sendMessage(CC.translate("&b&lRegistered Kits"));

        Practice.getInstance().getKitHandler().getKits().forEach(kit ->
            player.sendMessage(CC.translate("&7- &b" + kit.getName())));
    }


}
