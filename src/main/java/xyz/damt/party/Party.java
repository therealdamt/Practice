package xyz.damt.party;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.damt.Practice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class Party {

    private final UUID leader;

    private List<UUID> members;
    private boolean open;

    public Party(UUID leader) {
        this.leader = leader;
        this.members = new ArrayList<>();

        Practice.getInstance().getPartyHandler().getMap().put(leader, this);
    }

    public boolean isLeader(UUID uuid) {
        return leader.equals(uuid);
    }

    public List<UUID> getEveryone() {
        List<UUID> array = new ArrayList<>(members);
        array.add(leader);
        return array;
    }

    public List<Player> getPlayers() {
        return getEveryone().stream().map(Bukkit::getPlayer).collect(Collectors.toList());
    }

    public void disband() {
        getEveryone().forEach(uuid -> {
            Practice.getInstance().getPartyHandler().getMap().remove(uuid);
        });

        members.clear();
    }

    public void addMember(UUID uuid) {
        members.add(uuid);
        Practice.getInstance().getPartyHandler().getMap().put(uuid, this);
    }

    public void kickMember(UUID uuid) {
        members.remove(uuid);
        Practice.getInstance().getPartyHandler().getMap().remove(uuid);
    }

    @Override
    public String toString() {
        return "Party {" +
                " leader=" + leader.toString() +
                ", members=" + members.toString() +
                '}';
    }
}
