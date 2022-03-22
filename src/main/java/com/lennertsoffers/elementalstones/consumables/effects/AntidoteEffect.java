package com.lennertsoffers.elementalstones.consumables.effects;

import com.lennertsoffers.elementalstones.customClasses.tools.PotionEffectTools;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class AntidoteEffect implements ConsumableEffect {
    @Override
    public void playEffect(Player player) {
        Collection<PotionEffect> potionEffects = player.getActivePotionEffects();

        if (!potionEffects.isEmpty()) {
            potionEffects.forEach(potionEffect -> {
                PotionEffectType potionEffectType = potionEffect.getType();

                if (!PotionEffectTools.isGoodPotionEffect(potionEffectType)) {
                    player.removePotionEffect(potionEffectType);
                }
            });
        }
    }
}
