package xyz.damt.config.sub;

import org.bukkit.configuration.file.FileConfiguration;

public class SettingsHandler {

    public final boolean USE_PLACEHOLDER;
    public final int REQUIRED_WINS;

    public SettingsHandler(FileConfiguration fileConfiguration) {
        this.USE_PLACEHOLDER = fileConfiguration.getBoolean("settings.use-placeholder");
        this.REQUIRED_WINS = fileConfiguration.getInt("settings.required-wins");
    }
}
