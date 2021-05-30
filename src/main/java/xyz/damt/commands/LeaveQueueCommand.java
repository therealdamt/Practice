package xyz.damt.commands;

import me.vaperion.blade.command.annotation.Command;
import me.vaperion.blade.command.annotation.Sender;
import org.bukkit.entity.Player;
import xyz.damt.Practice;
import xyz.damt.queue.Queue;
import xyz.damt.util.CC;

public class LeaveQueueCommand {

    @Command(value = "leavequeue", async = true, quoted = false, description = "Leaves queue")
    public void leaveQueue(@Sender Player player) {
        Queue queue = Practice.getInstance().getQueueHandler().getQueue(player.getUniqueId());

        if (queue == null) {
            player.sendMessage(CC.translate("&cYou need to be inside of a queue to execute this command!"));
            return;
        }

        queue.remove(player);
        player.sendMessage(CC.translate("&7You have been removed from &b" + queue.getKit().getName() + "&7's queue!"));
    }

}
