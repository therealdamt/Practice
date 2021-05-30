package xyz.damt.commands.providers;

import me.vaperion.blade.command.argument.BladeProvider;
import me.vaperion.blade.command.container.BladeParameter;
import me.vaperion.blade.command.context.BladeContext;
import me.vaperion.blade.command.exception.BladeExitMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.damt.Practice;
import xyz.damt.kit.Kit;
import xyz.damt.util.CC;

import java.util.Collections;
import java.util.List;

public class KitCommandProvider implements BladeProvider<Kit> {

    private final Practice practice;
    private final Class<?> clazz;

    public KitCommandProvider(Practice practice) {
        this.practice = practice;
        this.clazz = Kit.class;
    }

    @Nullable
    @Override
    public Kit provide(@NotNull BladeContext bladeContext, @NotNull BladeParameter bladeParameter, @Nullable String s) throws BladeExitMessage {
        if (s == null) return null;
        return practice.getKitHandler().getKit(s);
    }

    @NotNull
    @Override
    public List<String> suggest(@NotNull BladeContext context, @NotNull String input) throws BladeExitMessage {
        return Collections.singletonList(CC.translate("&cThe kit " + input + " does not exist!"));
    }
}
