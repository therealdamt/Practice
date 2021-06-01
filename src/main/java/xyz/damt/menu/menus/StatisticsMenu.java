package xyz.damt.menu.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.damt.Practice;
import xyz.damt.menu.Menu;
import xyz.damt.profile.Profile;
import xyz.damt.util.CC;
import xyz.damt.util.ItemBuilder;

import java.util.HashMap;
import java.util.Map;

public class StatisticsMenu extends Menu {

    public StatisticsMenu(Player player) {
        super(player);
    }

    @Override
    public void click(Player player, InventoryClickEvent event) {
        if (event.getCurrentItem() != null) event.setCancelled(true);
    }

    @Override
    public Map<Integer, ItemStack> getMap(Player player) {
        final Map<Integer, ItemStack> map = new HashMap<>();
        Profile profile = Practice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        map.put(1, new ItemBuilder(Material.EMERALD_BLOCK).name(CC.translate("&a&lWins")).lore(CC.translate("&a" + profile.getWins())).build());
        map.put(3, new ItemBuilder(Material.REDSTONE_BLOCK).name(CC.translate("&4&lLoses")).lore(CC.translate("&4" + profile.getLoses())).build());
        map.put(5, new ItemBuilder(Material.IRON_SWORD).name(CC.translate("&e&lKills")).lore(CC.translate("&e" + profile.getKills())).build());
        map.put(7, new ItemBuilder(Material.REDSTONE).name(CC.translate("&c&lDeaths")).lore(CC.translate("&c" + profile.getDeaths())).build());

        return map;
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public String getTitle() {
        return getPlayer().getName() + "'s Statistics";
    }
}
