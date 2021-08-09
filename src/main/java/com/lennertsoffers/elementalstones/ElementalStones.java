package com.lennertsoffers.elementalstones;

import com.lennertsoffers.elementalstones.event.PrepareItemCraftEvent;
import org.bukkit.plugin.java.JavaPlugin;
import com.lennertsoffers.elementalstones.items.ItemWaterStone;

public final class ElementalStones extends JavaPlugin {

    @Override
    public void onEnable() {
        ItemWaterStone.init();
        getServer().getPluginManager().registerEvents(new PrepareItemCraftEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
