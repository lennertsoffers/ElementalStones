package com.lennertsoffers.elementalstones.consumables.effects;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PoisonedAppleEffect implements ConsumableEffect {

    @Override
    public void playEffect(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 3, true, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 3, true, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 600, 3, true, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 600, 1, true, false, true));

        AttributeInstance maxHealthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealthAttribute != null) {
            player.setHealth(maxHealthAttribute.getDefaultValue());
        }
    }
}
