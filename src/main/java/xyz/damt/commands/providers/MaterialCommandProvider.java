package xyz.damt.commands.providers;

import me.vaperion.blade.command.argument.BladeProvider;
import me.vaperion.blade.command.container.BladeParameter;
import me.vaperion.blade.command.context.BladeContext;
import me.vaperion.blade.command.exception.BladeExitMessage;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.damt.util.CC;

import java.util.Collections;
import java.util.List;

public class MaterialCommandProvider implements BladeProvider<Material> {

    @Nullable
    @Override
    public Material provide(@NotNull BladeContext bladeContext, @NotNull BladeParameter bladeParameter, @Nullable String s) throws BladeExitMessage {
        Material material = Material.valueOf(s.toUpperCase());
        if (s == null || material == null) return null;
        return material;
    }

    @NotNull
    @Override
    public List<String> suggest(@NotNull BladeContext context, @NotNull String input) throws BladeExitMessage {
        return Collections.singletonList(CC.translate("&cThe material " + input + " does not exist!"));
    }
}
