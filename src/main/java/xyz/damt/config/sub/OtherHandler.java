package xyz.damt.config.sub;

import org.bukkit.configuration.file.FileConfiguration;
import xyz.damt.util.CC;

import java.util.List;

public class OtherHandler {

    public final List<String> JOIN_MESSAGE;

    public OtherHandler(FileConfiguration fileConfiguration) {
        this.JOIN_MESSAGE = CC.translate(fileConfiguration.getStringList("other.join-message"));
    }
}
