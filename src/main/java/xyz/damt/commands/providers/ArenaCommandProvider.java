package xyz.damt.commands.providers;

import me.vaperion.blade.command.argument.BladeProvider;
import me.vaperion.blade.command.container.BladeParameter;
import me.vaperion.blade.command.context.BladeContext;
import me.vaperion.blade.command.exception.BladeExitMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.damt.Practice;
import xyz.damt.arena.Arena;
import xyz.damt.util.CC;

import java.util.Collections;
import java.util.List;

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

    @NotNull
    @Override
    public List<String> suggest(@NotNull BladeContext context, @NotNull String input) throws BladeExitMessage {
        return Collections.singletonList(CC.translate("&cThe arena " + input + " does not exist!"));
    }
}
