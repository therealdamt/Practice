package xyz.damt.commands;

import me.vaperion.blade.command.annotation.Command;
import me.vaperion.blade.command.annotation.Name;
import me.vaperion.blade.command.annotation.Sender;
import org.bukkit.entity.Player;
import xyz.damt.Practice;
import xyz.damt.arena.Arena;
import xyz.damt.kit.Kit;
import xyz.damt.match.Match;
import xyz.damt.menu.menus.UnrankedMenu;
import xyz.damt.profile.Profile;
import xyz.damt.util.CC;

public class DuelCommand {

    @Command(value = "duel", quoted = false, async = true, description = "Duel Command")
    public void duelCommand(@Sender Player player, @Name("target") Player target) {
        if (player.equals(target)) {
            player.sendMessage(CC.translate("&cYou cannot duel yourself!"));
            return;
        }

        new UnrankedMenu(player, target).updateMenu();
    }

    @Command(value = "accept", quoted = false, async = true, description = "Duel Accept Command")
    public void acceptCommand(@Sender Player player, @Name("target") Player target) {

        if (player.equals(target)) {
            player.sendMessage(CC.translate("&cYou cannot duel yourself!"));
            return;
        }

        Profile profile = Practice.getInstance().getProfileHandler().getProfile(player.getUniqueId());
        Match match = Practice.getInstance().getMatchHandler().getMatch(target.getUniqueId());

        if (match != null) {
            player.sendMessage(CC.translate("&cThat user is already inside of a match!"));
            profile.getPlayersSentDuel().remove(target.getUniqueId());
            return;
        }

        if (!profile.getPlayersSentDuel().containsKey(target.getUniqueId())) {
            player.sendMessage(CC.translate("&cYou do not have an invite request from that user!"));
            return;
        }

        Kit kit = profile.getPlayersSentDuel().get(target.getUniqueId());
        profile.getPlayersSentDuel().remove(target.getUniqueId());

        Arena arena = Practice.getInstance().getArenaHandler().getArenaOfKit(kit);

        if (arena == null) {
            player.sendMessage(CC.translate("&cLooks like there is no available arenas!"));
            target.sendMessage(CC.translate("&cLooks like there is no available arenas!"));
            return;
        }

        new Match(player, target, kit, arena, kit.isElo());
    }

}
