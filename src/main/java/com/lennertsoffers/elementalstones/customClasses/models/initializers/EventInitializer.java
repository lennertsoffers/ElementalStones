package com.lennertsoffers.elementalstones.customClasses.models.initializers;

import com.lennertsoffers.elementalstones.eventHandlers.*;
import org.bukkit.plugin.java.JavaPlugin;

public class EventInitializer extends Initializer {

    public EventInitializer(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void initialize() {
        this.getPlugin().getServer().getPluginManager().registerEvents(new BlockBreakEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new ClickEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new EntityDamageByEntityEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new EntityDamageEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new EntityDeathEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new EntityExplodeEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new InventoryOpenEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new PlayerConsumeEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new PlayerInteractAtEntityEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new PlayerItemHeldEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new PlayerMoveEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new PlayerQuitEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new PlayerRespawnEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new PlayerToggleFlightEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new PlayerToggleSneakEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new PrepareItemCraftEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new VillagerCareerChangeEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new FallingBlockToBlockEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new FireworkExplodeEvent(), this.getPlugin());
        this.getPlugin().getServer().getPluginManager().registerEvents(new InventoryClickEvent(), this.getPlugin());
    }
}
