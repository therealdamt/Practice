package xyz.damt.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.damt.match.Match;

public class MatchStartEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final Player playerOne;
    private final Player playerTwo;
    private final Match match;

    public MatchStartEvent(Match match, Player playerOne, Player playerTwo) {
        this.match = match;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
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
