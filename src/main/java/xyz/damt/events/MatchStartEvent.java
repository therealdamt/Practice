package xyz.damt.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.damt.match.Match;

@Getter
public class MatchStartEvent extends Event {

    private final HandlerList handlerList = new HandlerList();

    private final Player playerOne;
    private final Player playerTwo;
    private final Match match;

    public MatchStartEvent(Match match, Player playerOne, Player playerTwo) {
        this.match = match;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
