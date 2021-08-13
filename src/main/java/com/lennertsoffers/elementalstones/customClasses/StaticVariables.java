package com.lennertsoffers.elementalstones.customClasses;

import com.lennertsoffers.elementalstones.ElementalStones;
import org.bukkit.scheduler.BukkitScheduler;

public class StaticVariables {

    public static ElementalStones plugin;
    public static BukkitScheduler scheduler;

    public static void staticVariablesInit(ElementalStones p) {
        plugin = p;
        scheduler = plugin.getServer().getScheduler();
    }
}
