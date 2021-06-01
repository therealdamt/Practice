package xyz.damt.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.PlayerInventory;
import xyz.damt.Practice;
import xyz.damt.match.Match;

public class ServerListener implements Listener {

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();

        Match match = Practice.getInstance().getMatchHandler().getMatch(player.getUniqueId());
        if (match == null) e.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Practice.getInstance().getConfigHandler().getOtherHandler().JOIN_MESSAGE.forEach(e.getPlayer()::sendMessage);
        Practice.getInstance().getServerHandler().giveSpawnItems(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }

    @EventHandler
    public void onEntitySpawnEvent(EntitySpawnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent e) {
        if (!(e.getEntity() instanceof Player)) return;

        Player player = (Player) e.getEntity();
        if (Practice.getInstance().getMatchHandler().getMatch(player.getUniqueId()) == null) e.setCancelled(true);
    }

    @EventHandler
    public void onItemDropEvent(PlayerDropItemEvent e) {
        if (Practice.getInstance().getMatchHandler().getMatch(e.getPlayer().getUniqueId()) == null) e.setCancelled(true);
    }

    @EventHandler
    public void onItemPickUpEvent(PlayerPickupItemEvent e) {
        if (Practice.getInstance().getMatchHandler().getMatch(e.getPlayer().getUniqueId()) == null) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerClickInventoryEvent(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        if (e.getClickedInventory() == null) return;
        if (!(e.getClickedInventory() instanceof PlayerInventory)) return;

        if (e.getCurrentItem() != null) e.setCancelled(true);
    }

}
