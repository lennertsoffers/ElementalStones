package com.lennertsoffers.elementalstones.consumables.effects;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GingerbreadManEffect implements ConsumableEffect {

    @Override
    public void playEffect(Player player) {
        World world = player.getWorld();
        Location location = player.getLocation();

        world.playEffect(location, Effect.FIREWORK_SHOOT, 10, 100);
        world.playSound(location, Sound.ENTITY_DOLPHIN_EAT, 1, 0);

        player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 2400, 3, true, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2400, 3, true, false, true));
    }
}
