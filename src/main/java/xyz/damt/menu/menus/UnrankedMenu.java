package xyz.damt.menu.menus;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.damt.Practice;
import xyz.damt.kit.Kit;
import xyz.damt.menu.Menu;
import xyz.damt.profile.Profile;
import xyz.damt.queue.Queue;
import xyz.damt.util.CC;

import java.util.HashMap;
import java.util.Map;

public class UnrankedMenu extends Menu {

    private final Player target;

    public UnrankedMenu(Player player, Player target) {
        super(player);

        this.target = target;
    }

    @Override
    public void click(Player player, InventoryClickEvent event) {
        ItemStack stack = event.getCurrentItem();
        if (stack != null) event.setCancelled(true);

        if (stack == null || stack.getItemMeta() == null || stack.getItemMeta().getDisplayName() == null) return;

        Kit kit = Practice.getInstance().getKitHandler().getKit(stack, false);
        if (kit == null) return;

        if (target == null) {
            Queue queue = Practice.getInstance().getQueueHandler().getQueue(player.getUniqueId());

            if (queue != null) {
                player.sendMessage(CC.translate("&cPlease leave your current queue before entering a new one!"));
                return;
            }

            kit.getQueue().add(player);
            player.sendMessage(CC.translate("&7You have entered &b" + kit.getName() + "&7's queue!"));

            player.closeInventory();
            return;
        }

        Profile profile = Practice.getInstance().getProfileHandler().getProfile(target.getUniqueId());

        if (profile.getPlayersSentDuel().containsKey(player.getUniqueId())) {
            player.sendMessage(CC.translate("&cThat user already has a duel request from you!"));
            return;
        }

        profile.getPlayersSentDuel().put(player.getUniqueId(), kit);

        TextComponent message = new TextComponent("Click Here to Accept " + player.getName() + "'s duel for the kit " + kit.getName());

        message.setColor(ChatColor.AQUA);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/accept " + player.getName()));

        target.spigot().sendMessage(message);

        player.sendMessage(CC.translate("&7You have sent &b" + target.getName() + "&7 a duel with the kit &b" + kit.getName()));
        player.closeInventory();
    }

    @Override
    public Map<Integer, ItemStack> getMap(Player player) {
        final Map<Integer, ItemStack> map = new HashMap<>();

        for (Kit kit : Practice.getInstance().getKitHandler().getUnrankedKits()) {
            map.put(kit.getPriority(), kit.getItem());
        }

        return map;
    }

    @Override
    public String getTitle() {
        if (target != null) return target.getName() + "'s Duel";
        return CC.translate("&c&lUnranked Menu");
    }

    @Override
    public boolean useFiller() {
        return true;
    }

    @Override
    public int getSize() {
        return getNumber(Practice.getInstance().getKitHandler().getUnrankedKits().size());
    }
}
