package xyz.damt.menu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.damt.Practice;
import xyz.damt.util.CC;

import java.util.Map;

@Getter
@Setter
public abstract class Menu {

    public abstract Map<Integer, ItemStack> getMap(Player player);

    public void click(Player player, InventoryClickEvent event) {
    }

    private final Player player;
    private final Inventory inventory;

    private int size;
    private String title;
    private boolean useFiller = false;
    private ItemStack fillerBlock = new ItemStack(Material.STAINED_GLASS, 1, DyeColor.BLACK.getData());

    public Menu(Player player, int size, String title) {
        this.player = player;
        this.size = size;
        this.title = title;

        this.inventory = Bukkit.createInventory(null, size, title);

        if (useFiller)
            for (int i = 0; i < size; i++)
                getMap(player).put(i, fillerBlock);

        getMap(player).keySet().forEach(integer ->
                inventory.setItem(integer, getMap(player).get(integer)));
    }

    public void updateMenu() {

        if (player.getOpenInventory().equals(inventory)) {
            player.updateInventory();
            return;
        }

        player.openInventory(inventory);
        Practice.getInstance().getMenuHandler().getMenuHashMap().put(player.getUniqueId(), this);
    }

    public int getNumber(int number, int multiple) {
        int x = number;

        while (true) {
            double check = x / multiple;
            if (CC.isDouble(check)) {
                x += 1;
            } else {
                break;
            }
        }

        return x;
    }

}
