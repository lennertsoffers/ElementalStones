package com.lennertsoffers.elementalstones;

import com.lennertsoffers.elementalstones.event.PlayerItemHeldEvent;
import com.lennertsoffers.elementalstones.event.PrepareItemCraftEvent;
import org.bukkit.plugin.java.JavaPlugin;
import com.lennertsoffers.elementalstones.items.ItemStones;

public final class ElementalStones extends JavaPlugin {

    @Override
    public void onEnable() {
        ItemStones.init();
        getServer().getPluginManager().registerEvents(new PrepareItemCraftEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerItemHeldEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
