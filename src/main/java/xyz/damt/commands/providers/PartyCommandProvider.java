package xyz.damt.commands.providers;

import me.vaperion.blade.command.argument.BladeProvider;
import me.vaperion.blade.command.container.BladeParameter;
import me.vaperion.blade.command.context.BladeContext;
import me.vaperion.blade.command.exception.BladeExitMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.damt.Practice;
import xyz.damt.party.Party;

public class PartyCommandProvider implements BladeProvider<Party> {

    @Nullable
    @Override
    public Party provide(@NotNull BladeContext bladeContext, @NotNull BladeParameter bladeParameter, @Nullable String s) throws BladeExitMessage {
        Player player = Bukkit.getPlayer(s);
        Party party = Practice.getInstance().getPartyHandler().getParty(player.getUniqueId());

        if (player == null || s == null || party == null) throw new BladeExitMessage("Cannot find a party of that player!");

        return party;
    }

}
