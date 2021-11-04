package com.lennertsoffers.elementalstones.customClasses;

import com.lennertsoffers.elementalstones.ElementalStones;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.Random;

public class StaticVariables {

    public static ElementalStones plugin;
    public static BukkitScheduler scheduler;
    public static Random random;
    public static HashMap<String, HashMap<String, Integer>> coolDownMapping = new HashMap<>();

    public static void staticVariablesInit(ElementalStones p) {
        plugin = p;
        scheduler = plugin.getServer().getScheduler();
        random = new Random();
        fillCoolDownMapping();

    }

    private static void fillCoolDownMapping() {
        // Water
        HashMap<String, Integer> waterStone = new HashMap<>();
        waterStone.put("move1", 1);
        waterStone.put("move2", 1);
        waterStone.put("move3", 1);

        HashMap<String, Integer> waterbendingStone = new HashMap<>();
        waterbendingStone.put("move4", 1);
        waterbendingStone.put("move5", 1);
        waterbendingStone.put("move6", 1);
        waterbendingStone.put("move7", 1);
        waterbendingStone.put("move8", 1);

        HashMap<String, Integer> iceStone = new HashMap<>();
        iceStone.put("move4", 1);
        iceStone.put("move5", 1);
        iceStone.put("move6", 1);
        iceStone.put("move7", 1);
        iceStone.put("move8", 1);


        // Fire
        HashMap<String, Integer> fireStone = new HashMap<>();
        fireStone.put("move1", 1);
        fireStone.put("move2", 1);
        fireStone.put("move3", 1);

        HashMap<String, Integer> hellfireStone = new HashMap<>();
        hellfireStone.put("move4", 1);
        hellfireStone.put("move5", 1);
        hellfireStone.put("move6", 1);
        hellfireStone.put("move7", 1);
        hellfireStone.put("move8", 1);

        HashMap<String, Integer> lavaStone = new HashMap<>();
        lavaStone.put("move4", 1);
        lavaStone.put("move5", 1);
        lavaStone.put("move6", 1);
        lavaStone.put("move7", 1);
        lavaStone.put("move8", 1);


        // Air
        HashMap<String, Integer> airStone = new HashMap<>();
        airStone.put("move1", 1);
        airStone.put("move2", 1);
        airStone.put("move3", 1);

        HashMap<String, Integer> airbendingStone = new HashMap<>();
        airbendingStone.put("move4", 1);
        airbendingStone.put("move5", 1);
        airbendingStone.put("move6", 1);
        airbendingStone.put("move7", 1);
        airbendingStone.put("move8", 1);

        HashMap<String, Integer> agilityStone = new HashMap<>();
        agilityStone.put("move4", 1);
        agilityStone.put("move5", 1);
        agilityStone.put("move6", 1);
        agilityStone.put("move7", 1);
        agilityStone.put("move8", 1);


        // Earth
        HashMap<String, Integer> earthStone = new HashMap<>();
        earthStone.put("move1", 1);
        earthStone.put("move2", 1);
        earthStone.put("move3", 1);

        HashMap<String, Integer> earthbendingStone = new HashMap<>();
        earthbendingStone.put("move4", 1);
        earthbendingStone.put("move5", 1);
        earthbendingStone.put("move6", 1);
        earthbendingStone.put("move7", 1);
        earthbendingStone.put("move8", 1);

        HashMap<String, Integer> defenseStone = new HashMap<>();
        defenseStone.put("move4", 1);
        defenseStone.put("move5", 1);
        defenseStone.put("move6", 1);
        defenseStone.put("move7", 1);
        defenseStone.put("move8", 1);


        // Fill cooldownMapping
        coolDownMapping.put("waterStone", waterStone);
        coolDownMapping.put("waterbendingStone", waterbendingStone);
        coolDownMapping.put("iceStone", iceStone);
        coolDownMapping.put("fireStone", fireStone);
        coolDownMapping.put("hellfireStone", hellfireStone);
        coolDownMapping.put("lavaStone", lavaStone);
        coolDownMapping.put("airStone", airStone);
        coolDownMapping.put("airbendingStone", airbendingStone);
        coolDownMapping.put("agilityStone", agilityStone);
        coolDownMapping.put("earthStone", earthStone);
        coolDownMapping.put("earthbendingStone", earthbendingStone);
        coolDownMapping.put("defenseStone", defenseStone);
    }
}
