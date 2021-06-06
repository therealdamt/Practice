package xyz.damt.menu.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.damt.Practice;
import xyz.damt.kit.Kit;
import xyz.damt.menu.Menu;
import xyz.damt.queue.Queue;
import xyz.damt.util.CC;

import java.util.HashMap;
import java.util.Map;

public class RankedMenu extends Menu {

    public RankedMenu(Player player) {
        super(player);
    }

    @Override
    public void click(Player player, InventoryClickEvent event) {
        ItemStack stack = event.getCurrentItem();
        if (stack != null) event.setCancelled(true);

        if (stack == null || stack.getItemMeta() == null || stack.getItemMeta().getDisplayName() == null) return;

        Kit kit = Practice.getInstance().getKitHandler().getKit(stack, true);
        Queue queue = Practice.getInstance().getQueueHandler().getQueue(player.getUniqueId());

        if (kit == null) return;

        if (queue != null) {
            player.sendMessage(CC.translate("&cPlease leave your current queue before entering a new one!"));
            return;
        }

        kit.getQueue().add(player);
        player.sendMessage(CC.translate("&7You have entered &b" + kit.getName() + "&7's queue! (Elo)"));

        player.closeInventory();
    }

    @Override
    public Map<Integer, ItemStack> getMap(Player player) {
        final Map<Integer, ItemStack> map = new HashMap<>();

        for (Kit kit : Practice.getInstance().getKitHandler().getRankedKits()) {
            map.put(kit.getPriority(), kit.getItem());
        }

        return map;
    }

    @Override
    public boolean useFiller() {
        return true;
    }

    @Override
    public int getSize() {
        return getNumber(Practice.getInstance().getKitHandler().getRankedKits().size());
    }

    @Override
    public String getTitle() {
        return CC.translate("&b&lRanked Menu");
    }
}
