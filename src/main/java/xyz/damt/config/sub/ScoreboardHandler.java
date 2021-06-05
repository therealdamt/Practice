package xyz.damt.config.sub;

import org.bukkit.configuration.file.FileConfiguration;
import xyz.damt.util.CC;

import java.util.List;

public class ScoreboardHandler {

    public final String TITLE;

    public final List<String> NORMAL_SCOREBOARD;
    public final List<String> IN_MATCH_SCOREBOARD;
    public final List<String> PEARL_COOLDOWN;

    public ScoreboardHandler(FileConfiguration fileConfiguration) {
        this.TITLE = CC.translate(fileConfiguration.getString("scoreboard.title"));

        this.NORMAL_SCOREBOARD = CC.translate(fileConfiguration.getStringList("scoreboard.normal"));
        this.IN_MATCH_SCOREBOARD = CC.translate(fileConfiguration.getStringList("scoreboard.in-game"));
        this.PEARL_COOLDOWN = CC.translate(fileConfiguration.getStringList("scoreboard.enderpearl-cooldown"));
    }

}
