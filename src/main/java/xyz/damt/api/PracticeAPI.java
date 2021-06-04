package xyz.damt.api;

import org.bukkit.entity.Player;
import xyz.damt.Practice;
import xyz.damt.api.coin.CoinAPI;
import xyz.damt.arena.Arena;
import xyz.damt.config.ConfigHandler;
import xyz.damt.kit.Kit;
import xyz.damt.match.Match;
import xyz.damt.profile.Profile;
import xyz.damt.queue.Queue;

import java.util.UUID;

public class PracticeAPI {

    private final Practice practice;
    private final CoinAPI coinAPI;

    public PracticeAPI(Practice practice) {
        this.practice = practice;
        this.coinAPI = new CoinAPI(practice);
    }

    public Profile getProfile(UUID uuid) {
        return practice.getPracticeAPI().getProfile(uuid);
    }

    public Match getMatch(UUID uuid) {
        return practice.getMatchHandler().getMatch(uuid);
    }

    public Arena getArena(String name) {
        return practice.getArenaHandler().getArena(name);
    }

    public Queue getQueue(Kit kit) {
        return practice.getQueueHandler().getQueue(kit);
    }

    public Queue getQueue(UUID uuid) {
        return practice.getQueueHandler().getQueue(uuid);
    }

    public ConfigHandler getConfigHandler() {
        return practice.getConfigHandler();
    }

    public void forceWin(Player player, int time) {
        Match match = practice.getPracticeAPI().getMatch(player.getUniqueId());
        if (match == null) return;

        match.stop(player.getUniqueId(), time);
    }


}
