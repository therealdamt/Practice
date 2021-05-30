package xyz.damt.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.damt.Practice;
import xyz.damt.events.MatchEndEvent;
import xyz.damt.events.MatchStartEvent;
import xyz.damt.profile.Profile;

public class StatsListener implements Listener {

    @EventHandler
    public void onMatchStartEvent(MatchStartEvent e) {
        Profile playerOneProfile = Practice.getInstance().getProfileHandler().getProfile(e.getPlayerOne().getUniqueId());
        Profile playerTwoProfile = Practice.getInstance().getProfileHandler().getProfile(e.getPlayerTwo().getUniqueId());

        playerOneProfile.setGamesPlayed(playerOneProfile.getGamesPlayed() + 1);
        playerTwoProfile.setGamesPlayed(playerTwoProfile.getGamesPlayed() + 1);
    }

    @EventHandler
    public void onMatchEndEvent(MatchEndEvent e) {
        Profile winnerProfile = Practice.getInstance().getProfileHandler().getProfile(e.getWinner().getUniqueId());
        Profile loserProfile = Practice.getInstance().getProfileHandler().getProfile(e.getLoser().getUniqueId());

        loserProfile.setDeaths(loserProfile.getDeaths() + 1);
        loserProfile.setLoses(loserProfile.getLoses() + 1);

        winnerProfile.setKills(winnerProfile.getKills() + 1);
        winnerProfile.setWins(winnerProfile.getWins() + 1);
    }

}
