package com.lennertsoffers.elementalstones.customClasses.models.initializers;

import com.lennertsoffers.elementalstones.customClasses.annotations.CommandExecutor;
import com.lennertsoffers.elementalstones.modMenu.commands.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.Set;

public class CommandInitializer extends Initializer {
    public CommandInitializer(JavaPlugin  plugin) {
        super(plugin);
    }

    @Override
    public void initialize() {
        Set<Class<?>> commandExecutors = new Reflections("com.lennertsoffers.elementalstones.modMenu.commands").getTypesAnnotatedWith(CommandExecutor.class);

        try {
            for (Class<?> commandExecutor : commandExecutors) {
                Object commandExecutorObject = commandExecutor.newInstance();

                if (commandExecutorObject instanceof Command) {
                    Command command = (Command) commandExecutorObject;

                    PluginCommand pluginCommand = this.getPlugin().getCommand(command.getCommand());
                    if (pluginCommand != null) {
                        pluginCommand.setExecutor(command);
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
