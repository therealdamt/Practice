package xyz.damt.menu.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.damt.Practice;
import xyz.damt.kit.Kit;
import xyz.damt.menu.Menu;
import xyz.damt.queue.Queue;
import xyz.damt.util.CC;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UnrankedMenu extends Menu {

    public UnrankedMenu(Player player) {
        super(player, 0, CC.translate("&c&lUnranked Queue Menu"));

        int number = Practice.getInstance().getKitHandler().getUnrankedKits().size();
        int result = number % 9 == 0 ? number : number + (9 - (number % 9));

        this.setSize(result);
    }

    @Override
    public void click(Player player, InventoryClickEvent event) {
        ItemStack stack = event.getCurrentItem();
        if (stack != null) event.setCancelled(true);

        if (stack == null || stack.getItemMeta() == null || stack.getItemMeta().getDisplayName() == null) return;

        Kit kit = Practice.getInstance().getKitHandler().getKit(stack, false);
        Queue queue = Practice.getInstance().getQueueHandler().getQueue(player.getUniqueId());

        if (kit == null) return;

        if (queue != null) {
            player.sendMessage(CC.translate("&cPlease leave your current queue before entering a new one!"));
            return;
        }

        kit.getQueue().add(player);
        player.sendMessage(CC.translate("&7You have entered &b" + kit.getName() + "&7's queue!"));
    }

    @Override
    public Map<Integer, ItemStack> getMap(Player player) {
        final Map<Integer, ItemStack> map = new ConcurrentHashMap<>();

        int i = 0;

        for (Kit kit : Practice.getInstance().getKitHandler().getUnrankedKits()) {
            map.put(i, kit.getItem());
            i++;
        }

        return map;
    }

}
