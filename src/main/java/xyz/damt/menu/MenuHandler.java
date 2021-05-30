package xyz.damt.menu;

import lombok.Getter;
import xyz.damt.Practice;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class MenuHandler {

    @Getter private final HashMap<UUID, Menu> menuHashMap = new HashMap<>();
    private final Practice practice;

    public MenuHandler(Practice practice) {
        this.practice = practice;

        practice.getServer().getPluginManager().registerEvents(new MenuListener(this, practice), practice);
    }

    public Menu getMenu(UUID uuid) {
        return menuHashMap.get(uuid);
    }

    public Collection<Menu> getMenus() {
        return menuHashMap.values();
    }


}
