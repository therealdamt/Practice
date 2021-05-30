package xyz.damt.commands;

import me.vaperion.blade.command.annotation.Command;
import me.vaperion.blade.command.annotation.Sender;
import org.bukkit.entity.Player;
import xyz.damt.Practice;
import xyz.damt.kit.Kit;
import xyz.damt.menu.menus.RankedMenu;
import xyz.damt.profile.Profile;
import xyz.damt.util.CC;

public class RankedCommand {

    @Command(value = "ranked", quoted = false, description = "Opens Ranked Menu")
    public void ranked(@Sender Player sender) {
        Profile profile = Practice.getInstance().getProfileHandler().getProfile(sender.getUniqueId());
        int amountOfWins = Practice.getInstance().getConfigHandler().getSettingsHandler().REQUIRED_WINS;

        if (profile.getWins() < amountOfWins) {
            sender.sendMessage(CC.translate("&cYou must have " + amountOfWins + " to join ranked gamemodes!"));
            return;
        }

        new RankedMenu(sender).updateMenu();
    }

}
