package xyz.damt.commands;

import me.vaperion.blade.command.annotation.Command;
import me.vaperion.blade.command.annotation.Name;
import me.vaperion.blade.command.annotation.Sender;
import org.bukkit.entity.Player;
import xyz.damt.Practice;
import xyz.damt.menu.menus.ViewMenu;
import xyz.damt.profile.Profile;
import xyz.damt.util.CC;

import java.util.UUID;

public class ViewCommand {

    @Command(value = "view", quoted = false, description = "Opens View Menu")
    public void view(@Sender Player sender, @Name("target") UUID uuid) {
        Profile profile = Practice.getInstance().getProfileHandler().getProfile(uuid);

        if (profile == null || profile.getLastArmorContents() == null || profile.getLastInventoryContents() == null) {
            sender.sendMessage(CC.translate("&cThat user does not have a recent inventory!"));
            return;
        }

        new ViewMenu(sender, uuid).updateMenu();
    }

}
