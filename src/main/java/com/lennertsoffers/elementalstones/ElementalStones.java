package com.lennertsoffers.elementalstones;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.eventHandlers.*;
import com.lennertsoffers.elementalstones.modMenu.Commands;
import com.lennertsoffers.elementalstones.stones.windStone.AgilityStone;
import org.bukkit.plugin.java.JavaPlugin;
import com.lennertsoffers.elementalstones.items.ItemStones;

import java.util.Objects;

public final class ElementalStones extends JavaPlugin {

    @Override
    public void onEnable() {
        ItemStones.init();
        StaticVariables.staticVariablesInit(this);
        getServer().getPluginManager().registerEvents(new PrepareItemCraftEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerItemHeldEvent(), this);
        getServer().getPluginManager().registerEvents(new ClickEvent(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitEvent(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveEvent(), this);
        AgilityStone.passive();

        // Commands
        Objects.requireNonNull(this.getCommand("r")).setExecutor(new Commands());
        Objects.requireNonNull(this.getCommand("giveStone")).setExecutor(new Commands());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
