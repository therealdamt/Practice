package xyz.damt.config;

import lombok.Getter;
import xyz.damt.Practice;
import xyz.damt.config.sub.DatabaseHandler;
import xyz.damt.config.sub.OtherHandler;
import xyz.damt.config.sub.ScoreboardHandler;
import xyz.damt.config.sub.SettingsHandler;

@Getter
public class ConfigHandler {

    private final Practice practice;

    private final DatabaseHandler databaseHandler;
    private final ScoreboardHandler scoreboardHandler;
    private final SettingsHandler settingsHandler;
    private final OtherHandler otherHandler;

    public ConfigHandler(Practice practice) {
        this.practice = practice;

        this.databaseHandler = new DatabaseHandler(practice.getConfig());
        this.settingsHandler = new SettingsHandler(practice.getConfig());
        this.scoreboardHandler = new ScoreboardHandler(practice.getConfig());
        this.otherHandler = new OtherHandler(practice.getConfig());
    }

}
