package com.lennertsoffers.elementalstones.consumables.effects;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class BottleOfLightningEffect implements ConsumableEffect {

    @Override
    public void playEffect(Player player) {
        World world = player.getWorld();

        world.setThundering(true);
        world.setStorm(true);
        world.setThunderDuration(3000);
    }
}
