package xyz.damt.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.damt.match.Match;

public class MatchEndEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final Player winner;
    private final Player loser;
    private final Match match;

    public MatchEndEvent(Match match, Player winner, Player loser) {
        this.winner = winner;
        this.loser = loser;
        this.match = match;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getLoser() {
        return loser;
    }

    public Match getMatch() {
        return match;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
