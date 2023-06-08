package com.lennertsoffers.elementalstones;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.gameplay.ShamanVillager;
import com.lennertsoffers.elementalstones.customClasses.models.handlers.FileStorageHandler;
import com.lennertsoffers.elementalstones.customClasses.models.initializers.CommandInitializer;
import com.lennertsoffers.elementalstones.customClasses.models.initializers.EventInitializer;
import com.lennertsoffers.elementalstones.customClasses.models.initializers.LanguageInitializer;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.MoveController;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class ElementalStones extends JavaPlugin {

    public static FileConfiguration configuration;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        StaticVariables.staticVariablesInit(this);
        FileStorageHandler fileStorageHandler = new FileStorageHandler(this.getLogger());
        fileStorageHandler.initDeathPlayerStones();

        configuration = this.getConfig();


        // Run initializers
        LanguageInitializer languageInitializer = new LanguageInitializer(this);
        languageInitializer.initialize();
        EventInitializer eventInitializer = new EventInitializer(this);
        eventInitializer.initialize();
        CommandInitializer commandInitializer = new CommandInitializer(this);
        commandInitializer.initialize();

        new ItemStones(languageInitializer.getLanguageBundle()).init();
        new CraftItemManager(languageInitializer.getLanguageBundle()).init();

        ShamanVillager.initShamanIngredients();

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
