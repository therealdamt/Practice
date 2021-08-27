package xyz.damt.commands;

import me.vaperion.blade.command.annotation.Command;
import me.vaperion.blade.command.annotation.Name;
import me.vaperion.blade.command.annotation.Sender;
import org.bukkit.entity.Player;
import xyz.damt.Practice;
import xyz.damt.match.Match;
import xyz.damt.util.CC;

/**
 * This Project is property of damt © 2021
 * Redistribution of this Project is not allowed
 *
 * @author damt
 * Created: 8/7/2021
 * Project: Practice
 */

public class SpectateCommand {

    @Command(value = "spectate", async = true, quoted = false, description = "Spectate Command")
    public void spectateCommand(@Sender Player player, @Name("target") Player target) {
        Match match = Practice.getInstance().getMatchHandler().getMatch(player.getUniqueId());

        if (match != null && !match.isSpectator(player)) {
            player.sendMessage(CC.translate("&cYou may not spectate a match whilst being inside of a match!"));
            return;
        }

        if (match != null && match.isSpectator(player)) {
            player.sendMessage(CC.translate("&cPlease exit spectating your current match to spectate a new one!"));
            return;
        }

        match = Practice.getInstance().getMatchHandler().getMatch(target.getUniqueId());

        if (match == null) {
            player.sendMessage(CC.translate("&cThe user you specified is not currently inside of a match!"));
            return;
        }

        match.addSpectator(player);
        player.sendMessage(CC.translate("&aSpectating " + target.getName() + "'s match!"));
    }

    @Command(value = "leave", async = true, quoted = false, description = "Leave Command")
    public void leaveCommand(@Sender Player player) {
        Match match = Practice.getInstance().getMatchHandler().getMatch(player.getUniqueId());

        if (match == null) {
            player.sendMessage(CC.translate("&cYou must be spectating a match to do this command!"));
            return;
        }

        if (!match.isSpectator(player)) {
            player.sendMessage(CC.translate("&cYou must be a spectator to do this command!"));
            return;
        }

        match.removeSpectator(player);
        player.sendMessage(CC.translate("&aStopped spectating " + match.getPlayerOne().getName() + "'s match!"));
    }

}
