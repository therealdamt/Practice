package xyz.damt.commands.providers;

import me.vaperion.blade.command.argument.BladeProvider;
import me.vaperion.blade.command.container.BladeParameter;
import me.vaperion.blade.command.context.BladeContext;
import me.vaperion.blade.command.exception.BladeExitMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.damt.Practice;
import xyz.damt.arena.Arena;

public class ArenaCommandProvider implements BladeProvider<Arena> {

    private final Practice practice;
    private final Class<?> clazz;

    public ArenaCommandProvider(Practice practice) {
        this.practice = practice;
        this.clazz = Arena.class;
    }

    @Nullable
    @Override
    public Arena provide(@NotNull BladeContext bladeContext, @NotNull BladeParameter bladeParameter, @Nullable String s) throws BladeExitMessage {
        if (s == null) return null;
        return practice.getArenaHandler().getArena(s);
    }
}
