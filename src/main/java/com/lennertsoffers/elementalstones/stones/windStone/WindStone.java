package com.lennertsoffers.elementalstones.stones.windStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class WindStone {

    // MOVE 1
    // Air Ball
    // -> The player throws an air ball in the looking direction
    public static void move1(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        Location location = player.getLocation().add(player.getLocation().getDirection()).add(0, 1, 0);
        Vector direction = location.getDirection();
        World world = player.getWorld();
        new BukkitRunnable() {
            int amountOfTicks = 0;
            boolean collision = false;
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    world.spawnParticle(Particle.CLOUD, location.clone().add(StaticVariables.random.nextGaussian() / 6, StaticVariables.random.nextGaussian() / 6, StaticVariables.random.nextGaussian() / 6), 0, direction.getX() * 1.2, direction.getY() * 1.2, direction.getZ() * 1.2);
                }
                if (!world.getNearbyEntities(location, 0.6, 0.6, 0.6).isEmpty()) {
                    for (Entity entity : world.getNearbyEntities(location, 0.6, 0.6, 0.6)) {
                        if (entity != null) {
                            if (entity instanceof LivingEntity) {
                                LivingEntity livingEntity = (LivingEntity) entity;
                                if (livingEntity != player) {
                                    livingEntity.damage(5);
                                    livingEntity.setVelocity(direction.multiply(2));
                                    collision = true;
                                }
                            }
                        }
                    }
                }
                if (location.getBlock().getType() != Material.AIR) {
                    collision = true;
                }
                location.add(direction);

                if (amountOfTicks > 40 || collision) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

    }


    // MOVE 2



    // MOVE 3


}
