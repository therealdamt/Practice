package xyz.damt.queue;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.damt.Practice;
import xyz.damt.arena.Arena;
import xyz.damt.events.QueueJoinEvent;
import xyz.damt.events.QueueLeaveEvent;
import xyz.damt.kit.Kit;
import xyz.damt.match.Match;
import xyz.damt.profile.Profile;
import xyz.damt.util.CC;

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

        Arena arena = Practice.getInstance().getArenaHandler().getArenaOfKit(kit);


        if (queueType.equals(QueueType.ELO)) {
            int difference = oneProfile.getElo() - twoProfile.getElo();

            if (difference <= 250) {
                playersInQueue.remove(1);
                playersInQueue.remove(0);

                if (arena == null) {
                    one.sendMessage(CC.translate("&cThere is no empty arena for you to join!"));
                    two.sendMessage(CC.translate("&cThere is no empty arena for you to join!"));

                    Bukkit.getPluginManager().callEvent(new QueueLeaveEvent(this, one));
                    Bukkit.getPluginManager().callEvent(new QueueLeaveEvent(this, two));
                    return;
                }

                Player finalTwo = two;
                Bukkit.getScheduler().runTask(Practice.getInstance(), () -> new Match(one, finalTwo, kit, arena, true));
            } else {
                if (i > playersInQueue.size()) return;

                i++;
                two = Bukkit.getPlayer(playersInQueue.get(i));
            }
            return;
        }

        playersInQueue.remove(1);
        playersInQueue.remove(0);

        if (arena == null) {
            one.sendMessage(CC.translate("&cThere is no empty arena for you to join!"));
            two.sendMessage(CC.translate("&cThere is no empty arena for you to join!"));

            Bukkit.getPluginManager().callEvent(new QueueLeaveEvent(this, one));
            Bukkit.getPluginManager().callEvent(new QueueLeaveEvent(this, two));
            return;
        }

        Player finalTwo1 = two;
        Bukkit.getScheduler().runTask(Practice.getInstance(), () -> new Match(one, finalTwo1, kit, arena, true));
    }

    public void add(Player player) {
        playersInQueue.add(player.getUniqueId());

        Bukkit.getPluginManager().callEvent(new QueueJoinEvent(this, player));
    }

    public void remove(Player player) {
        playersInQueue.remove(player.getUniqueId());

        Bukkit.getPluginManager().callEvent(new QueueLeaveEvent(this, player));
    }

    public boolean IsInQueue(Player player) {
        return playersInQueue.contains(player.getUniqueId());
    }

}
