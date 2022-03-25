package com.lennertsoffers.elementalstones.consumables.effects;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BundleOfHerbsEffect implements ConsumableEffect {

    private final Entity entity;

    public  BundleOfHerbsEffect(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void playEffect(Player player) {
        this.entity.addPassenger(player);

        new BukkitRunnable() {
            int amountOfTicks = 0;

            @Override
            public void run() {
                Vector direction = player.getLocation().getDirection().setY(0).multiply(0.5);
                entity.setVelocity(direction);

                amountOfTicks++;
                if (amountOfTicks > 60) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }
}
