package xyz.damt.commands.providers;

import me.vaperion.blade.command.argument.BladeProvider;
import me.vaperion.blade.command.container.BladeParameter;
import me.vaperion.blade.command.context.BladeContext;
import me.vaperion.blade.command.exception.BladeExitMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.damt.Practice;

import java.util.UUID;

public class UUIDCommandProvider implements BladeProvider<UUID> {

    private final Practice practice;

    public UUIDCommandProvider(Practice practice) {
        this.practice = practice;
    }

    @Nullable
    @Override
    public UUID provide(@NotNull BladeContext bladeContext, @NotNull BladeParameter bladeParameter, @Nullable String s) throws BladeExitMessage {
        if (s.length() < 16) return null;
        UUID uuid;

        try {
            uuid = UUID.fromString(s);
        } catch (IllegalArgumentException e) {
            return null;
        }

        if (uuid == null) return null;
        return uuid;
    }
}
