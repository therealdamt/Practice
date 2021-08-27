package xyz.damt.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.damt.Practice;
import xyz.damt.match.Match;
import xyz.damt.profile.Profile;
import xyz.damt.util.CC;
import xyz.damt.util.PacketUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

        e.getPlayer().teleport(Practice.getInstance().getServerHandler().getSpawnLocation());

        Practice.getInstance().getConfigHandler().getOtherHandler().JOIN_MESSAGE.forEach(e.getPlayer()::sendMessage);
        Practice.getInstance().getServerHandler().giveSpawnItems(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        e.setQuitMessage(null);

        Player player = e.getPlayer();

        PacketUtils.allowMovement(player);
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
        if (Practice.getInstance().getMatchHandler().getMatch(e.getPlayer().getUniqueId()) == null)
            e.setCancelled(true);
    }

    @EventHandler
    public void onItemPickUpEvent(PlayerPickupItemEvent e) {
        if (Practice.getInstance().getMatchHandler().getMatch(e.getPlayer().getUniqueId()) == null)
            e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerClickInventoryEvent(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        if (e.getClickedInventory() == null) return;
        if (!(e.getClickedInventory() instanceof PlayerInventory)) return;

        Player player = (Player) e.getWhoClicked();
        Profile profile = Practice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        if (Practice.getInstance().getMatchHandler().getMatch(player.getUniqueId()) != null || profile.isBuild()) return;
        if (e.getCurrentItem() != null) e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e) {
        Player player = e.getPlayer();

        Profile profile = Practice.getInstance().getProfileHandler().getProfile(player.getUniqueId());
        Match match = Practice.getInstance().getMatchHandler().getMatch(player.getUniqueId());

        if (!profile.isBuild() && match == null) e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();

        Profile profile = Practice.getInstance().getProfileHandler().getProfile(player.getUniqueId());
        Match match = Practice.getInstance().getMatchHandler().getMatch(player.getUniqueId());

        if (!profile.isBuild() && match == null) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();

        e.setCancelled(true);
        player.getServer().broadcastMessage(CC.translate("&a" + player.getName() + "&7: " + e.getMessage()));
    }

    @EventHandler
    public void onEntityDeathEvent(PlayerDeathEvent e) {
        (new BukkitRunnable() {
            public void run() {
                try {
                    Object nmsPlayer = e.getEntity().getClass().getMethod("getHandle").invoke(e.getEntity());
                    Object con = nmsPlayer.getClass().getDeclaredField("playerConnection").get(nmsPlayer);
                    Class<?> EntityPlayer = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".EntityPlayer");

                    Field minecraftServer = con.getClass().getDeclaredField("minecraftServer");
                    minecraftServer.setAccessible(true);
                    Object mcserver = minecraftServer.get(con);
                    Object playerlist = mcserver.getClass().getDeclaredMethod("getPlayerList").invoke(mcserver);
                    Method moveToWorld = playerlist.getClass().getMethod("moveToWorld", EntityPlayer, Integer.TYPE, Boolean.TYPE);
                    moveToWorld.invoke(playerlist, nmsPlayer, 0, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).runTaskLater(Practice.getInstance(), 2L);
    }


}
