package com.lennertsoffers.elementalstones.customClasses.models.initializers;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class Initializer {
    private final JavaPlugin plugin;

    public Initializer(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    public abstract void initialize() throws InstantiationException, IllegalAccessException;
}
