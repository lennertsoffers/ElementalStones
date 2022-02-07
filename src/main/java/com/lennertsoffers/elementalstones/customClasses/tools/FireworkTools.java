package com.lennertsoffers.elementalstones.customClasses.tools;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;

import static com.lennertsoffers.elementalstones.customClasses.StaticVariables.random;

public class FireworkTools {
    private static Color randomColor() {
        return Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public static Firework setMeta(Firework firework, int power, FireworkEffect.Type fireworkEffectType, List<Color> colours, List<Color> fadeColours, boolean flicker, boolean trail) {
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.setPower(power);
        fireworkMeta.addEffect(FireworkEffect.builder().with(fireworkEffectType).withColor(colours).withFade(fadeColours).flicker(flicker).trail(trail).build());
        firework.setFireworkMeta(fireworkMeta);
        return firework;
    }

    public static Firework setRandomMeta(Firework firework, int power, FireworkEffect.Type fireworkEffectType, int maxColours, int maxFadeColours, int flickerMode, int trailMode) {
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        // Power
        if (power == -1) {
            fireworkMeta.setPower(random.nextInt(128));
        } else {
            fireworkMeta.setPower(power);
        }

        // Builder to add effects
        FireworkEffect.Builder effectBuilder = FireworkEffect.builder();

        // Colours
        ArrayList<Color> colors = new ArrayList<>();
        for (int i = 0; i < StaticVariables.random.nextInt(maxColours) + 1; i++) {
            colors.add(randomColor());
        }
        effectBuilder.withColor(colors);

        // Fade colours
        ArrayList<Color> fadeColors = new ArrayList<>();
        for (int i = 0; i < StaticVariables.random.nextInt(maxFadeColours) + 1; i++) {
            fadeColors.add(randomColor());
        }
        effectBuilder.withFade(fadeColors);

        // FireworkEffectType
        if (fireworkEffectType != null) {
            effectBuilder.with(fireworkEffectType);
        }

        // Flicker
        if (flickerMode == -1) {
            effectBuilder.flicker(StaticVariables.random.nextBoolean());
        } else {
            effectBuilder.flicker(flickerMode != 0);
        }

        // Trail
        if (trailMode == -1) {
            effectBuilder.trail(StaticVariables.random.nextBoolean());
        } else {
            effectBuilder.trail(trailMode != 0);
        }

        fireworkMeta.addEffect(effectBuilder.build());
        firework.setFireworkMeta(fireworkMeta);

        return firework;
    }
}
