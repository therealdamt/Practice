package xyz.damt.config;

import lombok.Getter;
import xyz.damt.Practice;
import xyz.damt.config.sub.DatabaseHandler;
import xyz.damt.config.sub.ScoreboardHandler;

@Getter
public class ConfigHandler {

    private final Practice practice;

    private final DatabaseHandler databaseHandler;
    private final ScoreboardHandler scoreboardHandler;

    public ConfigHandler(Practice practice) {
        this.practice = practice;

        this.databaseHandler = new DatabaseHandler(practice.getConfig());
        this.scoreboardHandler = new ScoreboardHandler(practice.getConfig());
    }

}
