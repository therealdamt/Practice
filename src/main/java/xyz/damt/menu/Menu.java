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
import xyz.damt.util.ItemBuilder;

import java.util.Map;

@Getter
@Setter
public abstract class Menu {

    public abstract Map<Integer, ItemStack> getMap(Player player);

    public void click(Player player, InventoryClickEvent event) {
    }

    private final Player player;

    protected boolean useFiller = false;
    private ItemStack fillerBlock = new ItemBuilder(Material.STAINED_GLASS, 1, DyeColor.GRAY.getData()).name(" ").build();

    public Menu(Player player) {
        this.player = player;
    }

    public void updateMenu() {
        Inventory inventory = Bukkit.createInventory(null, getSize(), getTitle());

        if (useFiller)
            for (int i = 0; i < getSize(); i++)
                getMap(player).put(i, fillerBlock);

        getMap(player).keySet().forEach(integer ->
                inventory.setItem(integer, getMap(player).get(integer)));

        if (player.getOpenInventory().equals(inventory)) {
            player.updateInventory();
            return;
        }

        player.openInventory(inventory);
        Practice.getInstance().getMenuHandler().getMenuHashMap().put(player.getUniqueId(), this);
    }

    public int getSize() {
        return 9;
    }

    public String getTitle() {
        return "Title";
    }
}
