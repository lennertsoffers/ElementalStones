package com.lennertsoffers.elementalstones.customClasses;

import com.lennertsoffers.elementalstones.ElementalStones;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Random;

public class StaticVariables {

    public static ElementalStones plugin;
    public static BukkitScheduler scheduler;
    public static Random random;

    public static void staticVariablesInit(ElementalStones p) {
        plugin = p;
        scheduler = plugin.getServer().getScheduler();
        random = new Random();
    }
}
