package xyz.damt.match.listener;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.damt.Practice;
import xyz.damt.events.MatchEndEvent;
import xyz.damt.kit.KitType;
import xyz.damt.match.Match;
import xyz.damt.match.MatchState;
import xyz.damt.profile.Profile;
import xyz.damt.util.CC;
import xyz.damt.util.PacketUtils;
import xyz.damt.util.TextBuilder;

import java.util.ArrayList;

public class MatchListener implements Listener {

    private final Practice practice = Practice.getInstance();

    /*
    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (e.getFrom().getBlockX() == e.getTo().getBlockX()
                && e.getFrom().getBlockZ() == e.getTo().getBlockZ()) return;

        Match match = practice.getMatchHandler().getMatch(player.getUniqueId());
        if (match == null) return;

        if (!match.isHasStarted()) e.setCancelled(true);
    }
     */

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;

        Player player = (Player) e.getEntity();
        Match match = practice.getMatchHandler().getMatch(player.getUniqueId());

        if (match == null) return;

        if (!match.isHasStarted()) e.setCancelled(true);
    }

    @EventHandler
    public void onMatchWinEvent(PlayerDeathEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getEntity().getKiller() instanceof Player)) return;

        e.setDeathMessage(null);

        Player player = e.getEntity();
        Player killer = e.getEntity().getKiller();

        Match match = practice.getMatchHandler().getMatch(player.getUniqueId());
        if (match == null) return;

        match.setMatchState(MatchState.IS_ENDING);

        Location location = player.getLocation();
        Bukkit.getScheduler().runTaskLaterAsynchronously(Practice.getInstance(), () -> player.teleport(location), 5L);

        match.stop(killer.getUniqueId(), 3);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Match match = practice.getMatchHandler().getMatch(player.getUniqueId());

        if (match == null) return;

        Player winner = player.getUniqueId().equals(match.getPlayerOne().getUniqueId())
                ? match.getPlayerOne() : match.getPlayerTwo();
        match.stop(winner.getUniqueId(), 3);
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Match match = practice.getMatchHandler().getMatch(player.getUniqueId());

        if (match == null || match.getKit().getKitType().equals(KitType.BUILD)) return;

        e.setCancelled(true);
        player.sendMessage(CC.translate("&cYou may not build blocks whilst using a non-build kit!"));
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Match match = practice.getMatchHandler().getMatch(player.getUniqueId());

        if (match == null || match.getKit().getKitType().equals(KitType.BUILD)) return;

        e.setCancelled(true);
        player.sendMessage(CC.translate("&cYou may not break blocks whilst using a non-build kit!"));
    }

    @EventHandler
    public void onProfileUpdate(MatchEndEvent e) {
        Player killer = e.getWinner();
        Player player = e.getLoser();

        Profile killerProfile = Practice.getInstance().getProfileHandler().getProfile(killer.getUniqueId());
        Profile userProfile = Practice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        killerProfile.setLastInventoryContents(killer.getInventory().getContents());
        killerProfile.setLastArmorContents(killer.getInventory().getArmorContents());
        killerProfile.setLastFood(killer.getFoodLevel());
        killerProfile.setLastHealth(killer.getHealth());
        killerProfile.setLastPotionEffects(new ArrayList<>(player.getActivePotionEffects()));

        userProfile.setLastArmorContents(player.getInventory().getArmorContents());
        userProfile.setLastInventoryContents(player.getInventory().getContents());
        userProfile.setLastFood(player.getFoodLevel());
        userProfile.setLastHealth(player.getHealth());
        userProfile.setLastPotionEffects(new ArrayList<>(player.getActivePotionEffects()));
    }

    @EventHandler
    public void onMatchEndEvent(MatchEndEvent e) {

        TextComponent winner = new TextBuilder().setText("Winner: ").setColor(ChatColor.AQUA).setBold(false).build();

        TextBuilder builder = new TextBuilder().setText(e.getWinner().getName()).setColor(ChatColor.GREEN)
                .setBold(true).setUnderline(true).setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                "/view " + e.getWinner().getUniqueId().toString()));

        winner.addExtra(builder.build());

        TextComponent loser = new TextBuilder()
                .setText("Loser: ").setColor(ChatColor.AQUA).setBold(false).build();

        TextBuilder builder1 = new TextBuilder().setText(e.getLoser().getName()).setColor(ChatColor.RED)
                .setBold(true).setUnderline(true).setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                "/view " + e.getLoser().getUniqueId().toString()));

        loser.addExtra(builder1.build());

        e.getMatch().playersToList().forEach(player -> {
            player.sendMessage(CC.translate("&7&m---------------------"));
            player.spigot().sendMessage(winner);
            player.spigot().sendMessage(loser);
            player.sendMessage(" ");
            player.sendMessage(CC.translate("&7&oClick on the names to check the inventory!"));
            player.sendMessage(CC.translate("&7&m---------------------"));
        });

        PacketUtils.sendTitle(Practice.getInstance(), e.getWinner(), CC.translate("&a&lWINNER!"),
                CC.translate("&a" + e.getLoser().getName() + " was the loser!"), 20, 3 * 20, 20);

        PacketUtils.sendTitle(Practice.getInstance(), e.getLoser(), CC.translate("&c&lDEFEAT!"),
                CC.translate("&c" + e.getWinner().getName() + " was the winner!"), 20, 3 * 20, 20);
    }


}
