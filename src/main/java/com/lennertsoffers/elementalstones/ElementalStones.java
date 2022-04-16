package com.lennertsoffers.elementalstones;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.handlers.FileStorageHandler;
import com.lennertsoffers.elementalstones.customClasses.models.initializers.EventInitializer;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.MoveController;
import com.lennertsoffers.elementalstones.customClasses.models.gameplay.ShamanVillager;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import com.lennertsoffers.elementalstones.modMenu.commands.SaveDefaultConfigCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

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

        EventInitializer eventInitializer = new EventInitializer(this);
        eventInitializer.initialize();

        new BukkitRunnable() {
            @Override
            public void run() {
                MoveController.moveControllers.forEach(MoveController::updateScoreBoard);
            }
        }.runTaskTimer(this, 0L, 20L);
    }

    @Override
    public void onDisable() {
    }
}
