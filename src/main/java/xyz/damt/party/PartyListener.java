package xyz.damt.party;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.damt.Practice;
import xyz.damt.util.CC;

public class PartyListener implements Listener {

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        Party party = Practice.getInstance().getPartyHandler().getParty(e.getPlayer().getUniqueId());

        if (party == null) return;

        if (party.isLeader(e.getPlayer().getUniqueId())) {
            party.disband();

            party.getPlayers().forEach(player -> {
                if (player == null) return;
                player.sendMessage(CC.translate("&7The party you were has been &bdisbanded&7!"));
            });
            return;
        }

        party.getMembers().remove(e.getPlayer().getUniqueId());
        party.getPlayers().forEach(player -> {
            if (player == null) return;
            player.sendMessage(CC.translate("&b" + e.getPlayer().getName() + "&7 has left the party!"));
        });
    }


}
