package xyz.damt.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import xyz.damt.Practice;
import xyz.damt.profile.Profile;

public class PracticePlaceHolderHook extends PlaceholderExpansion {

    private final Practice practice;

    public PracticePlaceHolderHook(Practice practice) {
        this.practice = practice;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "practice";
    }

    @Override
    public String getAuthor() {
        return "damt";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        Profile profile = practice.getProfileHandler().getProfile(p.getUniqueId());

        if (params.equalsIgnoreCase("coins")) return String.valueOf(profile.getCoins());
        if (params.equalsIgnoreCase("elo")) return String.valueOf(profile.getElo());
        if (params.equalsIgnoreCase("kills")) return String.valueOf(profile.getKills());
        if (params.equalsIgnoreCase("deaths")) return String.valueOf(profile.getDeaths());
        if (params.equalsIgnoreCase("wins")) return String.valueOf(profile.getWins());
        if (params.equalsIgnoreCase("games")) return String.valueOf(profile.getGamesPlayed());
        if (params.equalsIgnoreCase("loses")) return String.valueOf(profile.getLoses());

        return null;
    }
}
