package xyz.damt.match;

import lombok.Getter;
import xyz.damt.Practice;
import xyz.damt.match.task.MatchTask;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class MatchHandler {

    @Getter private final HashMap<UUID, Match> matchHashMap;
    private final Practice practice;

    public MatchHandler(Practice practice) {
        this.practice = practice;
        this.matchHashMap = new HashMap<>();

        new MatchTask(this).runTaskTimerAsynchronously(practice, 20L, 20L);
    }

    public Collection<Match> getMatches() {
        return matchHashMap.values();
    }

    public Match getMatch(UUID uuid) {
        return matchHashMap.get(uuid);
    }

}
