package xyz.damt.commands.providers;

import me.vaperion.blade.command.argument.BladeProvider;
import me.vaperion.blade.command.container.BladeParameter;
import me.vaperion.blade.command.context.BladeContext;
import me.vaperion.blade.command.exception.BladeExitMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.damt.Practice;
import xyz.damt.kit.Kit;
import xyz.damt.queue.Queue;

public class QueueCommandProvider implements BladeProvider<Queue> {

    private final Practice practice;

    public QueueCommandProvider(Practice practice) {
        this.practice = practice;
    }

    @Nullable
    @Override
    public Queue provide(@NotNull BladeContext bladeContext, @NotNull BladeParameter bladeParameter, @Nullable String s) throws BladeExitMessage {
        Kit kit = practice.getKitHandler().getKit(s);

        if (kit == null || s == null || kit.getQueue() == null)
            throw new BladeExitMessage("The queue " + s + " does not exist!");

        return kit.getQueue();
    }

}
