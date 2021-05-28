package xyz.damt;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.arena.ArenaHandler;
import xyz.damt.arena.ArenaListener;
import xyz.damt.config.ConfigHandler;
import xyz.damt.handler.MongoHandler;
import xyz.damt.handler.ServerHandler;
import xyz.damt.kit.KitHandler;
import xyz.damt.listener.StatsListener;
import xyz.damt.match.MatchHandler;
import xyz.damt.match.listener.MatchListener;
import xyz.damt.profile.ProfileHandler;
import xyz.damt.profile.ProfileListener;
import xyz.damt.queue.QueueHandler;
import xyz.damt.scoreboard.Adapter;
import xyz.damt.util.assemble.Assemble;
import xyz.damt.util.assemble.AssembleStyle;

import java.util.Arrays;

@Getter
public class Practice extends JavaPlugin {

    @Getter private static Practice instance;

    private ConfigHandler configHandler;
    private MongoHandler mongoHandler;
    private ProfileHandler profileHandler;
    private KitHandler kitHandler;
    private ArenaHandler arenaHandler;
    private MatchHandler matchHandler;
    private ServerHandler serverHandler;
    private QueueHandler queueHandler;

    @Override
    public void onLoad() {
        instance = this;
        this.saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        this.configHandler = new ConfigHandler(this);
        this.mongoHandler = new MongoHandler(this);
        this.matchHandler = new MatchHandler(this);
        this.serverHandler = new ServerHandler(this);
        this.queueHandler = new QueueHandler(this);

        this.profileHandler = new ProfileHandler(this);
        this.profileHandler.load();

        this.kitHandler = new KitHandler(this);
        this.kitHandler.load();

        this.arenaHandler = new ArenaHandler(this);
        this.arenaHandler.load();

        new Assemble(this, new Adapter(this), AssembleStyle.KOHI, 10);
        this.registerPlugin();
    }

    public void registerPlugin() {
        Arrays.asList(
                new ProfileListener(),
                new StatsListener(),
                new MatchListener(),
                new ArenaListener()
        ).forEach(listenerAdapter -> listenerAdapter.register(this));
    }


    @Override
    public void onDisable() {
        kitHandler.getKits().forEach(kit -> kit.save(false));
        profileHandler.getProfiles().forEach(profile -> profile.save(false));

        arenaHandler.getArenas().forEach(arena -> {
            arena.save(false);
            arena.rollback();
        });

    }



}
