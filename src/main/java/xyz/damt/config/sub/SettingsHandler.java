package xyz.damt.config.sub;

import org.bukkit.configuration.file.FileConfiguration;

public class SettingsHandler {

    public final boolean USE_PLACEHOLDER;
    public final String COIN_SHOP_COMMAND;
    public final int REQUIRED_WINS;

    public SettingsHandler(FileConfiguration fileConfiguration) {
        this.USE_PLACEHOLDER = fileConfiguration.getBoolean("settings.use-placeholder");
        this.COIN_SHOP_COMMAND = fileConfiguration.getString("settings.coinshop-command");
        this.REQUIRED_WINS = fileConfiguration.getInt("settings.required-wins");
    }
}
