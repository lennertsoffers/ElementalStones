package com.lennertsoffers.elementalstones.customClasses.models.initializers;

import com.lennertsoffers.elementalstones.eventHandlers.*;
import org.bukkit.plugin.Plugin;

public class EventInitializer {
    private final Plugin plugin;

    public EventInitializer(Plugin plugin) {
        this.plugin = plugin;
    }

    public void initialize() {
        this.plugin.getServer().getPluginManager().registerEvents(new BlockBreakEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new ClickEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new EntityDamageByEntityEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new EntityDamageEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new EntityDeathEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new EntityExplodeEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new InventoryOpenEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new PlayerConsumeEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new PlayerInteractAtEntityEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new PlayerItemHeldEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new PlayerMoveEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new PlayerQuitEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new PlayerRespawnEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new PlayerToggleFlightEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new PlayerToggleSneakEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new PrepareItemCraftEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new VillagerCareerChangeEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new FallingBlockToBlockEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new FireworkExplodeEvent(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new InventoryClickEvent(), this.plugin);
    }
}
