package xyz.damt.listener;

import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import xyz.damt.Practice;
import xyz.damt.menu.menus.DuelRequestsMenu;
import xyz.damt.menu.menus.StatisticsMenu;
import xyz.damt.util.CC;
import xyz.damt.util.cooldown.DurationFormatter;

public class InteractListener implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack stack = player.getItemInHand();

        if (stack == null || stack.getItemMeta() == null || stack.getItemMeta().getDisplayName() == null
                || Practice.getInstance().getMatchHandler().getMatch(player.getUniqueId()) != null) return;

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            switch (stack.getType()) {
                case IRON_SWORD:
                    player.chat("/unranked");
                    break;
                case DIAMOND_SWORD:
                    player.chat("/ranked");
                    break;
                case EMERALD:
                    player.chat("/party create");
                    break;
                case COMPASS:
                    new DuelRequestsMenu(player).updateMenu();
                    break;
                case PAPER:
                    new StatisticsMenu(player).updateMenu();
                    break;
                case RED_ROSE:
                    player.chat("/leavequeue");
                    break;
                default:
                    break;
            }
        }
    }

    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent e) {
        if (!(e.getEntity() instanceof EnderPearl)) return;
        if (!(e.getEntity().getShooter() instanceof Player)) return;

        Player player = (Player) e.getEntity().getShooter();
        if (Practice.getInstance().getMatchHandler().getMatch(player.getUniqueId()) == null) return;

        if (Practice.getInstance().getCooldownHandler().getEnderPearlCooldown().isOnCooldown(player)) {
            player.sendMessage(CC.translate("&cYou are still on pearl cooldown for " +
                    DurationFormatter.getRemaining(Practice.getInstance().getCooldownHandler().getEnderPearlCooldown().getRemaining(player), true)));

            e.setCancelled(true);

            if (player.getItemInHand() == null)
                player.setItemInHand(new ItemStack(Material.ENDER_PEARL));
            else
                player.getItemInHand().setAmount(player.getItemInHand().getAmount() + 1);

            player.updateInventory();
            return;
        }

        Practice.getInstance().getCooldownHandler().getEnderPearlCooldown().placeOnCooldown(player,
                Practice.getInstance().getConfigHandler().getSettingsHandler().PEARL_COOLDOWN_TIME);
    }

}
