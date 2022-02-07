package com.lennertsoffers.elementalstones.customClasses.models;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.function.Consumer;
import java.util.function.Function;

public enum Effect {
    SKELETON_TRAP("skeleton_trap", 200, location -> {

    }),
    ANGRY_BEES("angry_bees", 200, location -> {

    }),
    SHULKER_BULLETS("shulker_bullets", 100, location -> {

    }),
    TREE("tree", 100, location -> {

    }),
    ANCIENT_DEBRIS("ancient_debris", 100, location -> {

    }),
    DAY_NIGHT("day_night", 100, location -> {

    }),
    WEATHER("weather", 100, location -> {

    }),
    RANDOM_TELEPORT("random_teleport", 100, location -> {

    }),
    XP("xp", 100, location -> {

    }),
    CHARGED_CREEPER("charged_creeper", 100, location -> {

    }),
    FISH("fish", 100, location -> {

    }),
    POTION("potion", 100, location -> {

    }),
    WITHER("wither", 10, location -> {

    }),
    DISC_11("disc_11", 1, location -> {

    }),
    END_MUSIC("end_music", 1, location -> {

    });

    private final String name;
    private final int chance;
    private final Consumer<Location> effect;

    Effect(String name, int chance, Consumer<Location> effect) {
        this.name = name;
        this.chance = chance;
        this.effect = effect;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getChance() {
        return this.chance;
    }
    
    public void playEffect(Location location) {
        this.effect.accept(location);
    }
}
