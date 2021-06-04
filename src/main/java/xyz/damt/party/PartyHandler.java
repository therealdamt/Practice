package xyz.damt.party;

import lombok.Getter;
import xyz.damt.Practice;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PartyHandler {

    @Getter private final Map<UUID, Party> map;

    private final Practice practice;

    public PartyHandler(Practice practice) {
        this.practice = practice;
        this.map = new HashMap<>();
    }

    public Party getParty(UUID uuid) {
        return map.get(uuid);
    }

    public Collection<Party> getParties() {
        return map.values();
    }

}
