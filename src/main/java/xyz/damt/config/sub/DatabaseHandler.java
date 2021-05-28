package xyz.damt.config.sub;

import org.bukkit.configuration.file.FileConfiguration;

public class DatabaseHandler {

    public final boolean MONGO_HAS_AUTH;
    public final String MONGO_HOST;
    public final int MONGO_PORT;

    public final String MONGO_AUTH_DATABASE;
    public final String MONGO_USERNAME;
    public final String MONGO_PASSWORD;

    public DatabaseHandler(FileConfiguration fileConfiguration) {
        this.MONGO_HAS_AUTH = fileConfiguration.getBoolean("mongo.auth.enabled");
        this.MONGO_HOST = fileConfiguration.getString("mongo.host");
        this.MONGO_PORT = fileConfiguration.getInt("mongo.port");

        this.MONGO_AUTH_DATABASE = fileConfiguration.getString("mongo.auth.database");
        this.MONGO_USERNAME = fileConfiguration.getString("mongo.auth.username");
        this.MONGO_PASSWORD = fileConfiguration.getString("mongo.auth.password");
    }

}
