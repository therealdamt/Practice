package xyz.damt.handler;

import lombok.Getter;
import xyz.damt.util.cooldown.Cooldown;

@Getter
public class CooldownHandler {

    private final Cooldown enderPearlCooldown;

    public CooldownHandler() {
        this.enderPearlCooldown = new Cooldown();
    }

}
