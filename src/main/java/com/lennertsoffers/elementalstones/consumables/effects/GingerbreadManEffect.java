package com.lennertsoffers.elementalstones.consumables.effects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GingerbreadManEffect implements ConsumableEffect {

    @Override
    public void playEffect(Player player) {
        int newFoodLevel = player.getFoodLevel() + 10;
        if (newFoodLevel > 20) {
            newFoodLevel = 20;
        }

        player.setFoodLevel(newFoodLevel);
        player.setSaturation(40);

        player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 2400, 3, true, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 3, true, false, true));
    }
}
