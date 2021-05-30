package xyz.damt.match.listener;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.damt.Practice;
import xyz.damt.events.MatchEndEvent;
import xyz.damt.kit.KitType;
import xyz.damt.match.Match;
import xyz.damt.profile.Profile;
import xyz.damt.util.CC;
import xyz.damt.util.PacketUtils;

public class MatchListener implements Listener {

    private final Practice practice = Practice.getInstance();

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Match match = practice.getMatchHandler().getMatch(player.getUniqueId());

        if (match == null) return;
        if (e.getFrom().getBlockX() == e.getTo().getBlockX()
                && e.getFrom().getBlockZ() == e.getTo().getBlockZ()) return;

        if (!match.isHasStarted()) e.setCancelled(true);
    }

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

        Player player = e.getEntity();
        Player killer = e.getEntity().getKiller();

        Match match = practice.getMatchHandler().getMatch(player.getUniqueId());

        if (match == null) return;

        Profile killerProfile = Practice.getInstance().getProfileHandler().getProfile(killer.getUniqueId());
        Profile userProfile = Practice.getInstance().getProfileHandler().getProfile(player.getUniqueId());

        killerProfile.setLastInventoryContents(killer.getInventory().getContents());
        killerProfile.setLastArmorContents(killer.getInventory().getArmorContents());

        userProfile.setLastArmorContents(player.getInventory().getArmorContents());
        userProfile.setLastInventoryContents(player.getInventory().getContents());

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
    public void onMatchEndEvent(MatchEndEvent e) {

        TextComponent winner = createTextComponent(e.getWinner().getName(), ChatColor.GRAY, true, true);
        winner.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/view " + e.getWinner().getUniqueId().toString()));

        TextComponent finalWinner = createTextComponent("Winner: ", ChatColor.AQUA, true, false);
        finalWinner.addExtra(winner);

        TextComponent loser = createTextComponent(e.getLoser().getName(), ChatColor.GRAY, true, true);
        loser.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/view " + e.getLoser().getUniqueId().toString()));

        TextComponent finalLoser = createTextComponent("Loser: ", ChatColor.RED, true, false);
        finalLoser.addExtra(loser);

        e.getMatch().playersToList().forEach(player -> {
            player.sendMessage(CC.translate("&7&m---------------------"));
            player.spigot().sendMessage(finalWinner);
            player.spigot().sendMessage(finalLoser);
            player.sendMessage(" ");
            player.sendMessage(CC.translate("&7&oClick on the names to check the inventory!"));
            player.sendMessage(CC.translate("&7&m---------------------"));
        });

        PacketUtils.sendTitle(Practice.getInstance(), e.getWinner(), CC.translate("&a&lWINNER!"),
                CC.translate("&a" + e.getLoser().getName() + " was the loser!"), 20, 3 * 20, 20);

        PacketUtils.sendTitle(Practice.getInstance(), e.getLoser(), CC.translate("&c&lDEFEAT!"),
                CC.translate("&c" + e.getWinner().getName() + " was the winner!"), 20, 3 * 20, 20);
    }

    private TextComponent createTextComponent(String name, ChatColor color, boolean bold, boolean underLined) {

        TextComponent textComponent = new TextComponent(name);
        textComponent.setColor(color);
        textComponent.setBold(bold);
        textComponent.setUnderlined(underLined);

        return textComponent;
    }


}
