package xyz.damt.kit.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import xyz.damt.Practice;
import xyz.damt.match.Match;

public class SoupKitListener implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack stack = player.getItemInHand();

        if (stack == null || !stack.getType().equals(Material.MUSHROOM_SOUP)) return;

        Match match = Practice.getInstance().getMatchHandler().getMatch(player.getUniqueId());
        if (match == null || !match.getKit().getName().replace("Elo", "").equalsIgnoreCase("soup")) return;

        if (player.getHealth() + 4 > 20)
            player.setHealth(20);
         else 
             player.setHealth(player.getHealth() + 4);

        player.setItemInHand(new ItemStack(Material.BOWL));
        player.updateInventory();
    }

}
