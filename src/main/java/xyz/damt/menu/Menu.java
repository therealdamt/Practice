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
    private ItemStack fillerBlock
            = new ItemBuilder(Material.STAINED_GLASS_PANE).data(DyeColor.BLACK.getData()).name(" ").build();

    public Menu(Player player) {
        this.player = player;
    }

    public void updateMenu() {
        Inventory inventory = Bukkit.createInventory(null, getSize(), getTitle());

        if (useFiller()) {
            for (int i = 0; i < getSize(); i++) {
                getMap(player).put(i, fillerBlock);
            }
        }

        getMap(player).keySet().forEach(integer -> {
            inventory.setItem(integer, getMap(player).get(integer));
        });

        if (player.getOpenInventory().getTopInventory().equals(inventory)) {
            player.getOpenInventory().getTopInventory().setContents(inventory.getContents());
            return;
        }

        player.openInventory(inventory);
        Practice.getInstance().getMenuHandler().getMenuHashMap().put(player.getUniqueId(), this);
    }

    public int getNumber(int size) {
        if (size <= 9) return 9;
        if (size <= 18) return 18;
        if (size <= 27) return 27;
        if (size <= 36) return 36;
        if (size <= 45) return 45;
        if (size <= 54) return 54;
        return 9;
    }

    public int getSize() {
        return 9;
    }

    public boolean useFiller() {
        return false;
    }

    public String getTitle() {
        return "Title";
    }

}
