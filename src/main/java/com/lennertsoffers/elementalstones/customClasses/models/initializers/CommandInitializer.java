package com.lennertsoffers.elementalstones.customClasses.models.initializers;

import com.lennertsoffers.elementalstones.modMenu.commands.CheckConsumablesCommand;
import com.lennertsoffers.elementalstones.modMenu.commands.ReloadCommand;
import com.lennertsoffers.elementalstones.modMenu.commands.SaveDefaultConfigCommand;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class CommandInitializer extends Initializer {
    public CommandInitializer(JavaPlugin  plugin) {
        super(plugin);
    }

    public void initialize() {
        PluginCommand checkConsumablesCommand = this.getPlugin().getCommand("checkconsumablecommand");
        if (checkConsumablesCommand != null) {
            checkConsumablesCommand.setExecutor(new CheckConsumablesCommand("checkconsumablecommand"));
        }

        PluginCommand reloadCommand = this.getPlugin().getCommand("r");
        if (reloadCommand != null) {
            reloadCommand.setExecutor(new ReloadCommand("r"));
        }
    }
}
