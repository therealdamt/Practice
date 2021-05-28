package xyz.damt.queue;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.damt.Practice;
import xyz.damt.kit.Kit;
import xyz.damt.match.Match;
import xyz.damt.profile.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Queue {

    private final Kit kit;
    private final QueueType queueType;
    private final List<UUID> playersInQueue;

    private int i = 1;

    public Queue(Kit kit, QueueType queueType) {
        this.kit = kit;
        this.queueType = queueType;
        this.playersInQueue = new ArrayList<>();

        Practice.getInstance().getQueueHandler().getKitQueueHashMap().put(kit, this);
    }

    public void move() {
        if (playersInQueue.isEmpty() || playersInQueue.size() < 2) return;

        Player one = Bukkit.getPlayer(playersInQueue.get(0));
        Player two = Bukkit.getPlayer(playersInQueue.get(1));

        Profile oneProfile = Practice.getInstance().getProfileHandler().getProfile(one.getUniqueId());
        Profile twoProfile = Practice.getInstance().getProfileHandler().getProfile(two.getUniqueId());

        if (queueType.equals(QueueType.ELO)) {
            int difference = oneProfile.getElo() - twoProfile.getElo();

            if (difference <= 250) {
                new Match(one, two, kit, Practice.getInstance().getArenaHandler().getArenaOfKit(kit), true);
            } else {
                i++;
                two = Bukkit.getPlayer(playersInQueue.get(i));
            }
            return;
        }

        playersInQueue.remove(0);
        playersInQueue.remove(1);

        new Match(one, two, kit, Practice.getInstance().getArenaHandler().getArenaOfKit(kit), false);
    }

    public void add(Player player) {
        playersInQueue.add(player.getUniqueId());
    }

    public void remove(Player player) {
        playersInQueue.remove(player.getUniqueId());
    }

    public boolean IsInQueue(Player player) {
        return playersInQueue.contains(player.getUniqueId());
    }

}
