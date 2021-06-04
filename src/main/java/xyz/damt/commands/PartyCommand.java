package xyz.damt.commands;

import me.vaperion.blade.command.annotation.Command;
import me.vaperion.blade.command.annotation.Sender;
import org.bukkit.entity.Player;
import xyz.damt.Practice;
import xyz.damt.party.Party;
import xyz.damt.util.CC;

import java.util.Arrays;
import java.util.List;

public class PartyCommand {

    private final List<String> helpMessage = Arrays.asList(
            "&7&m------------&b&lParty Help Commands&7&m------------",
            "&b/party create &7- Creates a party",
            "&b/party disband &7- Disbands a party",
            "&b/party leave &7- Leaves a party",
            "&b/party open &7- Opens the party",
            "&7&m------------&b&lParty Help Commands&7&m------------"
    );

    @Command(value = "party", async = true, quoted = false, description = "Party Command")
    public void partyCommand(@Sender Player player) {
        helpMessage.forEach(s -> player.sendMessage(CC.translate(s)));
    }

    @Command(value = "party create", async = true, quoted = false, description = "Party Create Command")
    public void partyCreateCommand(@Sender Player player) {
        if (Practice.getInstance().getPartyHandler().getParty(player.getUniqueId()) != null) {
            player.sendMessage(CC.translate("&cYou already own a party!"));
            return;
        }

        new Party(player.getUniqueId());
        player.sendMessage(CC.translate("&7Created a &bparty!"));
    }

    @Command(value = "party disband", async = true, quoted = false, description = "Party Disband Command")
    public void partyDisbandCommand(@Sender Player player) {
        Party party = Practice.getInstance().getPartyHandler().getParty(player.getUniqueId());

        if (party == null) {
            player.sendMessage(CC.translate("&cYou must be in a party to do this command!"));
            return;
        }

        if (!party.isLeader(player.getUniqueId())) {
            player.sendMessage(CC.translate("&cYou must be the leader of the party to do this command!"));
            return;
        }

        party.disband();
        party.getPlayers().forEach(user -> {
            if (user != null) user.sendMessage(CC.translate("&7The party has been &bdisbanded&7!"));
        });
    }

    @Command(value = "party leave", async = true, quoted = false, description = "Party Leave Command")
    public void partyLeaveCommand(@Sender Player player) {
        Party party = Practice.getInstance().getPartyHandler().getParty(player.getUniqueId());

        if (party == null) {
            player.sendMessage(CC.translate("&cYou must be in a party to do this command!"));
            return;
        }

        if (party.isLeader(player.getUniqueId())) {
            player.sendMessage(CC.translate("&cYou are the leader, if you are trying to leave the party please do /party disband!"));
            return;
        }

        party.getMembers().remove(player.getUniqueId());
        party.getPlayers().forEach(user -> {
            if (user != null) user.sendMessage(CC.translate("&b" + player.getName() + "&7 has left the party!"));
        });
    }

    @Command(value = "party open", async = true, quoted = false, description = "Party Open Command")
    public void partyOpenCommand(@Sender Player player) {
        Party party = Practice.getInstance().getPartyHandler().getParty(player.getUniqueId());

        if (party == null) {
            player.sendMessage(CC.translate("&cYou must be in a party to do this command!"));
            return;
        }

        if (!party.isLeader(player.getUniqueId())) {
            player.sendMessage(CC.translate("&cYou must be the leader of the party to do this command!"));
            return;
        }

        if (party.isOpen()) {
            party.setOpen(false);
        } else {
            party.setOpen(true);
        }

        player.sendMessage(CC.translate("&7You have set the party's open status to &b" + party.isOpen()));
    }

}
