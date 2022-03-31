package com.lennertsoffers.elementalstones;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.FileStorageHandler;
import com.lennertsoffers.elementalstones.customClasses.models.MoveController;
import com.lennertsoffers.elementalstones.customClasses.models.ShamanVillager;
import com.lennertsoffers.elementalstones.eventHandlers.*;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import com.lennertsoffers.elementalstones.modMenu.commands.Commands;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ElementalStones extends JavaPlugin {

    public static FileConfiguration configuration;

    @Override
    public void onEnable() {
        FileStorageHandler fileStorageHandler = new FileStorageHandler(this.getLogger());
        fileStorageHandler.initDeathPlayerStones();

        configuration = this.getConfig();
        this.saveDefaultConfig();
        ItemStones.init();
        CraftItemManager.init();
        StaticVariables.staticVariablesInit(this);
        ShamanVillager.initShamanIngredients();

        getServer().getPluginManager().registerEvents(new BlockBreakEvent(), this);
        getServer().getPluginManager().registerEvents(new ClickEvent(this), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageEvent(), this);
        getServer().getPluginManager().registerEvents(new EntityDeathEvent(), this);
        getServer().getPluginManager().registerEvents(new EntityExplodeEvent(), this);
        getServer().getPluginManager().registerEvents(new InventoryOpenEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerConsumeEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractAtEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerItemHeldEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerToggleFlightEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerToggleSneakEvent(), this);
        getServer().getPluginManager().registerEvents(new PrepareItemCraftEvent(), this);
        getServer().getPluginManager().registerEvents(new VillagerCareerChangeEvent(), this);
        getServer().getPluginManager().registerEvents(new FallingBlockToBlockEvent(), this);
        getServer().getPluginManager().registerEvents(new FireworkExplodeEvent(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickEvent(), this);

        // Commands
        Objects.requireNonNull(this.getCommand("r")).setExecutor(new Commands());
        Objects.requireNonNull(this.getCommand("giveStone")).setExecutor(new Commands());
        Objects.requireNonNull(this.getCommand("stoneInventory")).setExecutor(new Commands());
        Objects.requireNonNull(this.getCommand("giveItem")).setExecutor(new Commands());
        Objects.requireNonNull(this.getCommand("shamanMajor")).setExecutor(new Commands());
        Objects.requireNonNull(this.getCommand("saveDefaultConfig")).setExecutor(new Commands());
        Objects.requireNonNull(this.getCommand("spawnCows")).setExecutor(new Commands());
        Objects.requireNonNull(this.getCommand("customModelData")).setExecutor(new Commands());
        Objects.requireNonNull(this.getCommand("checkShards")).setExecutor(new Commands());
        Objects.requireNonNull(this.getCommand("checkSpells")).setExecutor(new Commands());
        Objects.requireNonNull(this.getCommand("checkItems1")).setExecutor(new Commands());
        Objects.requireNonNull(this.getCommand("checkItems2")).setExecutor(new Commands());
        Objects.requireNonNull(this.getCommand("giveShards")).setExecutor(new Commands());
        Objects.requireNonNull(this.getCommand("setHunger")).setExecutor(new Commands());

        new BukkitRunnable() {
            @Override
            public void run() {
                MoveController.moveControllers.forEach(MoveController::updateScoreBoard);
            }
        }.runTaskTimer(this, 0L, 20L);

        Locale locale = new Locale("nl");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("LanguageBundle", locale);
        System.out.println(resourceBundle.getString("test"));
        System.out.println(resourceBundle.getString("test2"));
    }

    @Override
    public void onDisable() {
    }
}
