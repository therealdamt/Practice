package xyz.damt.menu.menus;

import org.bukkit.Bukkit;
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
import java.util.UUID;
import java.util.stream.Collectors;

public class ViewMenu extends Menu {

    private final UUID target;

    public ViewMenu(Player player, UUID target) {
        super(player);
        this.target = target;
    }

    @Override
    public void click(Player player, InventoryClickEvent event) {
        if (event.getCurrentItem() != null) event.setCancelled(true);
    }

    @Override
    public Map<Integer, ItemStack> getMap(Player player) {
        final Map<Integer, ItemStack> map = new HashMap<>();
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

        map.put(53, new ItemBuilder(Material.POTION).name(CC.translate("&b&lPotion Effects"))
        .lore(CC.translate(profile.getLastPotionEffects().stream().map(potionEffect -> "&b" + potionEffect.getType().toString().toLowerCase() + " &7: &b" + potionEffect.getDuration())
                .collect(Collectors.toList()))).build());

        map.put(52, new ItemBuilder(Material.RED_ROSE).name(CC.translate("&c&lHealth")).lore(CC.translate("&c" + profile.getLastHealth() + "&7/&c20")).build());
        map.put(51, new ItemBuilder(Material.COOKED_BEEF).name(CC.translate("&a&lFood")).lore(CC.translate("&a" + profile.getLastFood())).build());

        return map;
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public String getTitle() {
        return CC.translate(Bukkit.getOfflinePlayer(target).getName() + "'s Inventory View");
    }
}
