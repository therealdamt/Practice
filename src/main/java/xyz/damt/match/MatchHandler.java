package xyz.damt.match;

import lombok.Getter;
import org.bukkit.entity.Player;
import xyz.damt.Practice;
import xyz.damt.match.task.MatchTask;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class MatchHandler {

    @Getter
    private final HashMap<UUID, Match> matchHashMap;
    private final Practice practice;

    public MatchHandler(Practice practice) {
        this.practice = practice;
        this.matchHashMap = new HashMap<>();

        new MatchTask(this).runTaskTimerAsynchronously(practice, 20L, 20L);
    }

    public final Collection<Match> getMatches() {
        return matchHashMap.values();
    }

    public final Match getMatchSpectator(Player player) {
        return getMatches().stream().filter(match -> match.getSpectators().contains(player)).findFirst().orElse(null);
    }

    public final Match getMatch(UUID uuid) {
        return matchHashMap.get(uuid);
    }

    public final boolean checkIfSpectator(Player player) {
        return getMatch(player.getUniqueId()).getSpectators().contains(player);
    }

}
