package xyz.damt.commands.providers;

import me.vaperion.blade.command.argument.BladeProvider;
import me.vaperion.blade.command.container.BladeParameter;
import me.vaperion.blade.command.context.BladeContext;
import me.vaperion.blade.command.exception.BladeExitMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.damt.Practice;
import xyz.damt.kit.Kit;

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
        Kit kit = practice.getKitHandler().getKit(s);

        if (s == null || kit == null)
            throw new BladeExitMessage("The kit " + s + " does not exist!");

        return kit;
    }

}
