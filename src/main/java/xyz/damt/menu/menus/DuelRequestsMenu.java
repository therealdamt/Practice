package xyz.damt.menu.menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

public class DuelRequestsMenu extends Menu {

    public DuelRequestsMenu(Player player) {
        super(player);
    }

    @Override
    public void click(Player player, InventoryClickEvent event) {
        ItemStack stack = event.getCurrentItem();
        Profile profile = Practice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        if (stack == null || stack.getItemMeta() == null || stack.getItemMeta().getDisplayName() == null) return;
        event.setCancelled(true);

        Player target = Bukkit.getPlayer(ChatColor.stripColor(stack.getItemMeta().getDisplayName()));

        if (target == null) {

            player.sendMessage(CC.translate("&cThis user seems to have logged out!"));
            profile.getPlayersSentDuel().remove(Bukkit.getOfflinePlayer(ChatColor.stripColor(stack.getItemMeta().getDisplayName())).getUniqueId());

            player.closeInventory();
            return;
        }

        player.chat("/accept " + target.getName());
    }

    @Override
    public Map<Integer, ItemStack> getMap(Player player) {
        final Map<Integer, ItemStack> map = new HashMap<>();
        Profile profile = Practice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        int i = 0;

        for (UUID uuid : profile.getPlayersSentDuel().keySet()) {
            map.put(i, new ItemBuilder(Material.EMERALD_BLOCK).name(CC.translate("&b&l" + Bukkit.getOfflinePlayer(uuid).getName()))
                    .lore(CC.translate("&bKit&7: " + profile.getPlayersSentDuel().get(uuid).getName())).build());
        }

        return map;
    }

    @Override
    public int getSize() {
        return getNumber(Practice.getInstance().getProfileHandler().getProfile(this.getPlayer().getUniqueId()).getPlayersSentDuel().size());
    }

    @Override
    public boolean useFiller() {
        return true;
    }

    @Override
    public String getTitle() {
        return getPlayer().getName() + "'s Duel Requests Menu";
    }
}
