package xyz.damt.match.task;

import org.bukkit.scheduler.BukkitRunnable;
import xyz.damt.match.MatchHandler;
import xyz.damt.util.CC;

public class MatchTask extends BukkitRunnable {

    private final MatchHandler matchHandler;

    public MatchTask(MatchHandler matchHandler) {
        this.matchHandler = matchHandler;
    }

    @Override
    public void run() {
        matchHandler.getMatches().forEach(match -> {
            if (match.getCountdownTime() > 0)
                match.setCountdownTime(match.getCountdownTime() - 1);

            if (match.getCountdownTime() <= 0)
                match.setHasStarted(true);

            if (!match.isHasStarted() && match.getCountdownTime() <= 5)
                match.playersToList().forEach(player -> player.sendMessage(CC.translate("&7The match is going to start in &b" + match.getCountdownTime())));
        });
    }
}
