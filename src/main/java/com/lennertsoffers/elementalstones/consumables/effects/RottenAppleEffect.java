package com.lennertsoffers.elementalstones.consumables.effects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RottenAppleEffect implements ConsumableEffect {

    @Override
    public void playEffect(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 600, 3, true, true, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1200, 3, true, true, true));
    }
}
