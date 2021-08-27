package xyz.damt.match.task;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.damt.Practice;
import xyz.damt.match.MatchHandler;
import xyz.damt.util.CC;
import xyz.damt.util.PacketUtils;

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

            if (match.getCountdownTime() <= 0 && !match.isHasStarted()) {
                match.setHasStarted(true);

                match.playersToList().forEach(player -> {
                    PacketUtils.sendTitle(Practice.getInstance(), player,
                            CC.translate("&a&lMatch Started!"), null, 5, 20, 5);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 0.5f, 0.5f);
                });

                Bukkit.getScheduler().runTask(Practice.getInstance(), () -> {
                    PacketUtils.allowMovement(match.getPlayerTwo());
                    PacketUtils.allowMovement(match.getPlayerOne());
                });
            }

            if (!match.isHasStarted() && match.getCountdownTime() <= 5)
                match.playersToList().forEach(player -> {
                    PacketUtils.sendTitle(Practice.getInstance(), player,
                            CC.translate("&7Match Starting In &b" + match.getCountdownTime()), null, 2, 10, 2);
                    player.playSound(player.getLocation(), Sound.NOTE_PIANO, 0.5F, 0.5F);
                });

        });
    }
}
