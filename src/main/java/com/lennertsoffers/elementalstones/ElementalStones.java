package com.lennertsoffers.elementalstones;

import com.lennertsoffers.elementalstones.event.*;
import com.lennertsoffers.elementalstones.stones.earthStone.DefenseStone;
import com.lennertsoffers.elementalstones.stones.earthStone.EarthStone;
import org.bukkit.plugin.java.JavaPlugin;
import com.lennertsoffers.elementalstones.items.ItemStones;

public final class ElementalStones extends JavaPlugin {

    @Override
    public void onEnable() {
        ItemStones.init();
        getServer().getPluginManager().registerEvents(new PrepareItemCraftEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerItemHeldEvent(), this);
        getServer().getPluginManager().registerEvents(new ClickEvent(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitEvent(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageEvent(), this);
        new EarthStone(this);
        new DefenseStone(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
