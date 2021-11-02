package com.lennertsoffers.elementalstones.stones.windStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Objects;

public class WindStone {

    // MOVE 1
    // Air Ball
    // -> The player throws an air ball in the looking direction
    // -> Damages entities on hit
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
    // A(i)rea Control
    // -> Blast away every living entity in close range
    public static void move2(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        Location location = player.getLocation().add(0, 1, 0);
        for (int i = 0; i < 360; i++) {
            Location particleLocation = MathTools.locationOnCircle(location, 4, i, world);
            Vector direction = particleLocation.add(-location.getX(), 0, -location.getZ()).toVector().setY(0);
            for (int j = 0; j < 3; j++) {
                world.spawnParticle(Particle.SNOWFLAKE, location.clone().add(StaticVariables.random.nextGaussian() / 4, StaticVariables.random.nextGaussian() / 4, StaticVariables.random.nextGaussian() / 4), 0, direction.getX() / 5, 0, direction.getZ() / 5);
            }
        }
        if (!world.getNearbyEntities(location, 7, 2, 7).isEmpty()) {
            for (Entity entity : world.getNearbyEntities(location, 7, 2, 7)) {
                if (entity != null) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        if (livingEntity != player) {
                            Vector direction = livingEntity.getLocation().add(-location.getX(), 0, -location.getZ()).toVector().setY(0.2);
                            livingEntity.setVelocity(direction);
                        }
                    }
                }
            }
        }
    }

    // MOVE 3
    // Suction
    // -> Sucks all the items in close range towards the player
    public static void move3(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        if (!world.getNearbyEntities(player.getLocation(), 15, 15, 15).isEmpty()) {
            for (Entity entity : world.getNearbyEntities(player.getLocation(), 15, 15, 15)) {
                if (entity != null) {
                    if (entity instanceof Item) {
                        Item item = (Item) entity;
                        item.setGlowing(true);
                        item.setPickupDelay(0);
                        item.setVelocity(new Vector(0, 0.7, 0));
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                item.setVelocity(MathTools.getDirectionNormVector3d(item.getLocation(), player.getLocation()).multiply(3));
                            }
                        }.runTaskLater(StaticVariables.plugin, 10L);
                    }
                }
            }
        }
    }
}
