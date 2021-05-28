package xyz.damt.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.damt.Practice;
import xyz.damt.match.Match;
import xyz.damt.profile.Profile;
import xyz.damt.util.assemble.AssembleAdapter;

import java.util.List;
import java.util.stream.Collectors;

public class Adapter implements AssembleAdapter {

    private final Practice practice;

    public Adapter(Practice practice) {
        this.practice = practice;
    }

    @Override
    public String getTitle(Player player) {
        return practice.getConfigHandler().getScoreboardHandler().TITLE;
    }

    @Override
    public List<String> getLines(Player player) {

        Profile profile = practice.getProfileHandler().getProfile(player.getUniqueId());
        Match match = practice.getMatchHandler().getMatch(player.getUniqueId());

        if (match == null) {
            return practice.getConfigHandler().getScoreboardHandler().NORMAL_SCOREBOARD.stream().map(string ->
                    string.replace("{elo}", String.valueOf(profile.getElo()))
            .replace("{wins}", String.valueOf(profile.getWins())).replace("{loses}", String.valueOf(profile.getLoses()))
            .replace("{coins}", String.valueOf(profile.getCoins())).replace("{online}", String.valueOf(Bukkit.getOnlinePlayers().size()))
            .replace("{queue}", String.valueOf(practice.getQueueHandler().getPlayersInQueueSize())
            .replace("{kills}", String.valueOf(profile.getKills())).replace("{deaths}", String.valueOf(profile.getDeaths()))
            .replace("{played}", String.valueOf(profile.getGamesPlayed())))
            .replace("{ping}", String.valueOf(profile.getPing()))).collect(Collectors.toList());
        }

        Player opponent = match.getOpponent(player);
        Profile opponentProfile = practice.getProfileHandler().getProfile(opponent.getUniqueId());

        return practice.getConfigHandler().getScoreboardHandler().IN_MATCH_SCOREBOARD.stream().map(string ->
                string.replace("{opponent}", opponent.getName()).replace("{opponent_ping}", String.valueOf(opponentProfile.getPing()))
        .replace("{player}", player.getName()).replace("{player_ping}", String.valueOf(profile.getPing()))
        .replace("{opponent_elo}", String.valueOf(opponentProfile.getElo())).replace("{player_elo}", String.valueOf(profile.getElo())))
                .collect(Collectors.toList());
    }
}
