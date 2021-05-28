package xyz.damt.listener;

import org.bukkit.event.EventHandler;
import xyz.damt.events.MatchEndEvent;
import xyz.damt.events.MatchStartEvent;
import xyz.damt.profile.Profile;
import xyz.damt.util.framework.listener.ListenerAdapter;

public class StatsListener extends ListenerAdapter {

    @EventHandler
    public void onMatchStartEvent(MatchStartEvent e) {
        Profile playerOneProfile = practice.getProfileHandler().getProfile(e.getPlayerOne().getUniqueId());
        Profile playerTwoProfile = practice.getProfileHandler().getProfile(e.getPlayerTwo().getUniqueId());

        playerOneProfile.setGamesPlayed(playerOneProfile.getGamesPlayed() + 1);
        playerTwoProfile.setGamesPlayed(playerTwoProfile.getGamesPlayed() + 1);
    }

    @EventHandler
    public void onMatchEndEvent(MatchEndEvent e) {
        Profile winnerProfile = practice.getProfileHandler().getProfile(e.getWinner().getUniqueId());
        Profile loserProfile = practice.getProfileHandler().getProfile(e.getLoser().getUniqueId());

        loserProfile.setDeaths(loserProfile.getDeaths() + 1);
        loserProfile.setLoses(loserProfile.getLoses() + 1);

        winnerProfile.setKills(winnerProfile.getKills() + 1);
        winnerProfile.setWins(winnerProfile.getWins() + 1);
    }

}
