package xyz.damt.menu.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.damt.menu.Menu;
import xyz.damt.profile.Profile;
import xyz.damt.util.CC;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ViewMenu extends Menu {

    private final Profile target;

    public ViewMenu(Player player, Profile target) {
        super(player, 54, CC.translate("&b&l" + target.getOfflinePlayer().getName() + "'s Inventory View"));

        this.target = target;
    }

    @Override
    public void click(Player player, InventoryClickEvent event) {
        if (event.getCurrentItem() != null) event.setCancelled(true);
    }

    @Override
    public Map<Integer, ItemStack> getMap(Player player) {
        final Map<Integer, ItemStack> map = new ConcurrentHashMap<>();

        int items = 0;

        for (ItemStack stack : target.getLastInventoryContents()) {
            map.put(items, stack);
            items++;
        }

        int armor = 45;

        for (ItemStack stack : target.getLastArmorContents()) {
            map.put(armor, stack);
            armor++;
        }

        return map;
    }
}
