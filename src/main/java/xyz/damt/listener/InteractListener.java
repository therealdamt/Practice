package xyz.damt.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import xyz.damt.Practice;
import xyz.damt.menu.menus.DuelRequestsMenu;
import xyz.damt.menu.menus.StatisticsMenu;

public class InteractListener implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack stack = player.getItemInHand();

        if (stack == null || stack.getItemMeta() == null || stack.getItemMeta().getDisplayName() == null) return;

        switch (stack.getType()) {
            case IRON_SWORD:
                player.chat("/unranked");
                break;
            case DIAMOND_SWORD:
                player.chat("/ranked");
                break;
            case EMERALD:
                player.chat(Practice.getInstance().getConfigHandler().getSettingsHandler().COIN_SHOP_COMMAND);
                break;
            case COMPASS:
                new DuelRequestsMenu(player).updateMenu();
                break;
            case PAPER:
                new StatisticsMenu(player).updateMenu();
                break;
            default:
                break;
        }
    }

}
