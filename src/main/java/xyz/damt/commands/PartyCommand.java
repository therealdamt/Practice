package xyz.damt.commands;

import me.vaperion.blade.command.annotation.Command;
import me.vaperion.blade.command.annotation.Name;
import me.vaperion.blade.command.annotation.Sender;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.damt.Practice;
import xyz.damt.party.Party;
import xyz.damt.profile.Profile;
import xyz.damt.util.CC;
import xyz.damt.util.TextBuilder;

import java.util.Arrays;
import java.util.List;

public class PartyCommand {

    private final List<String> helpMessage = Arrays.asList(
            "&7&m------------&b&lParty Help Commands&7&m------------",
            "&b/party create &7- Creates a party",
            "&b/party disband &7- Disbands a party",
            "&b/party leave &7- Leaves a party",
            "&b/party open &7- Opens the party",
            "&b/party invite <player> &7- Invites a player to the party",
            "&b/party kick <player> &7- Kicks a player out of your party",
            "&b/party uninvite <player> &7- Uninvites a player you have previously invited",
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

    @Command(value = "party invite", async = true, quoted = false, description = "Party Invite Command")
    public void partyInviteCommand(@Sender Player player, @Name("player") Player target) {
        Party party = Practice.getInstance().getPartyHandler().getParty(player.getUniqueId());

        if (party == null) {
            player.sendMessage(CC.translate("&cYou must be in a party to do this command!"));
            return;
        }

        if (!party.isLeader(player.getUniqueId())) {
            player.sendMessage(CC.translate("&cYou must be the leader of the party to do this command!"));
            return;
        }

        Profile profile = Practice.getInstance().getProfileHandler().getProfile(target.getUniqueId());

        if (profile.getPartyInvites().contains(party)) {
            player.sendMessage(CC.translate("&cThat user already has a party invite!"));
            return;
        }

        profile.getPartyInvites().add(party);

        player.sendMessage(CC.translate("&7You have invited the player &b" + target.getName() + "&7 to your party!"));

        target.sendMessage("&7You have been invited to &b" + player.getName() + "'s party&7!");
        target.spigot().sendMessage(new TextBuilder().setText("Click here to accept!").setColor(ChatColor.AQUA)
                .setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + player.getName())).build());
    }

    @Command(value = "party kick", async = true, quoted = false, description = "Party Invite Command")
    public void partyKickCommand(@Sender Player player, @Name("player") Player target) {
        Party party = Practice.getInstance().getPartyHandler().getParty(player.getUniqueId());

        if (party == null) {
            player.sendMessage(CC.translate("&cYou must be in a party to do this command!"));
            return;
        }

        if (!party.isLeader(player.getUniqueId())) {
            player.sendMessage(CC.translate("&cYou must be the leader of the party to do this command!"));
            return;
        }

        Party targetParty = Practice.getInstance().getPartyHandler().getParty(target.getUniqueId());

        if (targetParty == null || !targetParty.isLeader(player.getUniqueId())) {
            player.sendMessage(CC.translate("&cThe user that you invited isn't inside of your party!"));
            return;
        }

        party.kickMember(target.getUniqueId());

        target.sendMessage(CC.translate("&cYou have been kicked from " + player.getName() + "'s party!"));
        party.getMembers().stream().map(Bukkit::getPlayer).forEach(player1 -> {
            if (player1 == null)
                return;

            player1.sendMessage(CC.translate("&cThe user " + target.getName() + " has been kicked from the party!"));
        });
    }

    @Command(value = "party accept", description = "Party Accept Command", quoted = false, async = true)
    public void partyAcceptCommand(@Sender Player player, @Name("party") Party party) {
        Profile profile = Practice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        if (!profile.getPartyInvites().contains(party)) {
            player.sendMessage(CC.translate("&cThe party you specified did not invite you!"));
            return;
        }

        party.addMember(player.getUniqueId());

        player.sendMessage(CC.translate("&aJoined party!"));
        party.getMembers().stream().map(Bukkit::getPlayer).forEach(player1 -> {
            if (player1 == null)
                return;

            player1.sendMessage(CC.translate("&aThe user " + player.getName() + " has joined the party!"));
        });
    }

}
