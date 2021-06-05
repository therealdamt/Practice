package xyz.damt.profile;

import lombok.Getter;
import org.bson.Document;
import xyz.damt.Practice;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

public class ProfileHandler {

    @Getter private final HashMap<UUID, Profile> profileHashMap;

    private final Practice practice;

    public ProfileHandler(Practice practice) {
        this.practice = practice;
        this.profileHashMap = new HashMap<>();

        this.load();
    }

    public void load() {
        practice.getServer().getScheduler().runTaskAsynchronously(practice, () -> {
           practice.getMongoHandler().getProfiles().find().forEach((Consumer<? super Document>) document ->
                   new Profile(UUID.fromString(document.getString("_id"))));
        });
    }

    public Collection<Profile> getProfiles() {
        return this.profileHashMap.values();
    }

    public Profile getProfile(UUID uuid) {
        return profileHashMap.get(uuid);
    }

    public boolean hasProfile(UUID uuid) {
        return getProfiles().stream().anyMatch(profile -> profile.getUuid().equals(uuid));
    }

}
