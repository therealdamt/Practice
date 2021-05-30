package xyz.damt.commands.providers;

import me.vaperion.blade.command.argument.BladeProvider;
import me.vaperion.blade.command.container.BladeParameter;
import me.vaperion.blade.command.context.BladeContext;
import me.vaperion.blade.command.exception.BladeExitMessage;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MaterialCommandProvider implements BladeProvider<Material> {

    @Nullable
    @Override
    public Material provide(@NotNull BladeContext bladeContext, @NotNull BladeParameter bladeParameter, @Nullable String s) throws BladeExitMessage {
        Material material = Material.valueOf(s.toUpperCase());

        if (s == null || material == null)
            throw new BladeExitMessage("The material " + s + " does not exist!");

        return material;
    }

}
