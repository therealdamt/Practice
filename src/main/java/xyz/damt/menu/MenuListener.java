package xyz.damt.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.damt.Practice;

public class MenuListener implements Listener {

    private final MenuHandler menuHandler;
    private final Practice practice;

    public MenuListener(MenuHandler menuHandler, Practice practice) {
        this.menuHandler = menuHandler;
        this.practice = practice;
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        if (e.getClickedInventory() == null) return;

        Player player = (Player) e.getWhoClicked();
        Menu menu = menuHandler.getMenu(player.getUniqueId());

        if (menu == null) return;

        menu.click(player, e);
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player)) return;
        if (e.getInventory() == null) return;

        Player player = (Player) e.getPlayer();
        Menu menu = menuHandler.getMenu(player.getUniqueId());

        if (menu == null) return;

        menuHandler.getMenuHashMap().remove(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Menu menu = menuHandler.getMenu(player.getUniqueId());

        if (menu == null) return;

        menuHandler.getMenuHashMap().remove(player.getUniqueId());
    }

}
