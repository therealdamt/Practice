package xyz.damt.menu.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.damt.Practice;
import xyz.damt.menu.Menu;
import xyz.damt.profile.Profile;
import xyz.damt.util.CC;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ViewMenu extends Menu {

    private final UUID target;

    public ViewMenu(Player player, UUID target) {
        super(player, 54,  "Inventory View");
        this.target = target;
    }

    @Override
    public void click(Player player, InventoryClickEvent event) {
        if (event.getCurrentItem() != null) event.setCancelled(true);
    }

    @Override
    public Map<Integer, ItemStack> getMap(Player player) {
        final Map<Integer, ItemStack> map = new ConcurrentHashMap<>();
        Profile profile = Practice.getInstance().getProfileHandler().getProfile(target);

        int items = 0;

        for (ItemStack stack : profile.getLastInventoryContents()) {
            map.put(items, stack);
            items++;
        }

        int armor = 45;

        for (ItemStack stack : profile.getLastArmorContents()) {
            map.put(armor, stack);
            armor++;
        }

        return map;
    }
}
