package xyz.damt.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.damt.match.Match;

@Getter
public class MatchEndEvent extends Event {

    private final HandlerList handlerList = new HandlerList();

    private final Player winner;
    private final Player loser;
    private final Match match;

    public MatchEndEvent(Match match, Player winner, Player loser) {
        this.winner = winner;
        this.loser = loser;
        this.match = match;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
