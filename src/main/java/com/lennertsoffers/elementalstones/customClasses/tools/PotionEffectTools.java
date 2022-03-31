package com.lennertsoffers.elementalstones.customClasses.tools;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

public class PotionEffectTools {

    public static PotionEffect randomPotionEffect() {
        PotionEffect potionEffect;

        int random = StaticVariables.random.nextInt(32);
        if (random == 0) {
            potionEffect = new PotionEffect(PotionEffectType.SPEED, 2400, 3, true, true, true);
        } else if (random == 1) {
            potionEffect = new PotionEffect(PotionEffectType.ABSORPTION, 2400, 3, true, true, true);
        } else if (random == 2) {
            potionEffect = new PotionEffect(PotionEffectType.BAD_OMEN, 2400, 1, true, true, true);
        } else if (random == 3) {
            potionEffect = new PotionEffect(PotionEffectType.BLINDNESS, 2400, 3, true, true, true);
        } else if (random == 4) {
            potionEffect = new PotionEffect(PotionEffectType.CONDUIT_POWER, 2400, 3, true, true, true);
        } else if (random == 5) {
            potionEffect = new PotionEffect(PotionEffectType.CONFUSION, 2400, 3, true, true, true);
        } else if (random == 6) {
            potionEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2400, 3, true, true, true);
        } else if (random == 7) {
            potionEffect = new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 2400, 3, true, true, true);
        } else if (random == 8) {
            potionEffect = new PotionEffect(PotionEffectType.FAST_DIGGING, 2400, 3, true, true, true);
        } else if (random == 9) {
            potionEffect = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2400, 3, true, true, true);
        } else if (random == 10) {
            potionEffect = new PotionEffect(PotionEffectType.GLOWING, 2400, 3, true, true, true);
        } else if (random == 11) {
            potionEffect = new PotionEffect(PotionEffectType.HARM, 2400, 3, true, true, true);
        } else if (random == 12) {
            potionEffect = new PotionEffect(PotionEffectType.HEAL, 2400, 3, true, true, true);
        } else if (random == 13) {
            potionEffect = new PotionEffect(PotionEffectType.HEALTH_BOOST, 2400, 3, true, true, true);
        } else if (random == 14) {
            potionEffect = new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 2400, 3, true, true, true);
        } else if (random == 15) {
            potionEffect = new PotionEffect(PotionEffectType.HUNGER, 2400, 3, true, true, true);
        } else if (random == 16) {
            potionEffect = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2400, 3, true, true, true);
        } else if (random == 17) {
            potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, 2400, 3, true, true, true);
        } else if (random == 18) {
            potionEffect = new PotionEffect(PotionEffectType.JUMP, 2400, 3, true, true, true);
        } else if (random == 19) {
            potionEffect = new PotionEffect(PotionEffectType.LEVITATION, 2400, 3, true, true, true);
        } else if (random == 20) {
            potionEffect = new PotionEffect(PotionEffectType.LUCK, 2400, 3, true, true, true);
        } else if (random == 21) {
            potionEffect = new PotionEffect(PotionEffectType.NIGHT_VISION, 2400, 3, true, true, true);
        } else if (random == 22) {
            potionEffect = new PotionEffect(PotionEffectType.POISON , 2400, 3, true, true, true);
        } else if (random == 23) {
            potionEffect = new PotionEffect(PotionEffectType.REGENERATION, 2400, 3, true, true, true);
        } else if (random == 24) {
            potionEffect = new PotionEffect(PotionEffectType.SLOW_DIGGING, 2400, 3, true, true, true);
        } else if (random == 25) {
            potionEffect = new PotionEffect(PotionEffectType.SLOW, 2400, 3, true, true, true);
        } else if (random == 26) {
            potionEffect = new PotionEffect(PotionEffectType.SLOW_FALLING, 2400, 3, true, true, true);
        } else if (random == 27) {
            potionEffect = new PotionEffect(PotionEffectType.UNLUCK, 2400, 3, true, true, true);
        } else if (random == 28) {
            potionEffect = new PotionEffect(PotionEffectType.WATER_BREATHING, 2400, 3, true, true, true);
        } else if (random == 29) {
            potionEffect = new PotionEffect(PotionEffectType.WEAKNESS, 2400, 3, true, true, true);
        } else if (random == 30) {
            potionEffect = new PotionEffect(PotionEffectType.WITHER, 2400, 3, true, true, true);
        } else {
            potionEffect = new PotionEffect(PotionEffectType.SATURATION, 2400, 3, true, true, true);
        }

        return potionEffect;
    }

    public static boolean isGoodPotionEffect(PotionEffectType potionEffectType) {
        ArrayList<PotionEffectType> goodTypes = new ArrayList<>(
                Arrays.asList(
                        PotionEffectType.ABSORPTION,
                        PotionEffectType.CONDUIT_POWER,
                        PotionEffectType.DAMAGE_RESISTANCE,
                        PotionEffectType.DOLPHINS_GRACE,
                        PotionEffectType.FAST_DIGGING,
                        PotionEffectType.FIRE_RESISTANCE,
                        PotionEffectType.HEAL,
                        PotionEffectType.HEALTH_BOOST,
                        PotionEffectType.HERO_OF_THE_VILLAGE,
                        PotionEffectType.INCREASE_DAMAGE,
                        PotionEffectType.INVISIBILITY,
                        PotionEffectType.JUMP,
                        PotionEffectType.LUCK,
                        PotionEffectType.NIGHT_VISION,
                        PotionEffectType.REGENERATION,
                        PotionEffectType.SATURATION,
                        PotionEffectType.SPEED,
                        PotionEffectType.WATER_BREATHING
                )
        );

        return goodTypes.contains(potionEffectType);
    }
}
