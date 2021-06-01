package xyz.damt.commands;

import me.vaperion.blade.command.annotation.Command;
import me.vaperion.blade.command.annotation.Sender;
import org.bukkit.entity.Player;
import xyz.damt.Practice;
import xyz.damt.menu.menus.UnrankedMenu;

public class UnrankedCommand {

    @Command(value = "unranked", quoted = false, description = "Opens Unranked Menu")
    public void unranked(@Sender Player sender) {
        new UnrankedMenu(sender, null).updateMenu();
    }

}
