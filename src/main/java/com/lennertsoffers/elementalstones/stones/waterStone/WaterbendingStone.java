package com.lennertsoffers.elementalstones.stones.waterStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class WaterbendingStone extends WaterStone {

    // Passive


    // MOVE 4
    // Bubblebeam
    // -> Shoots beam of bubbles in the looking direction of the player
    public static void move4(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();

        // Creation of beam
        new BukkitRunnable() {
            int amountOfTicks = 0;
            @Override
            public void run() {

                Location location = player.getLocation().add(0, 1, 0).add(player.getLocation().getDirection());

                for (int i = 0; i < amountOfTicks; i++) {
                    spawnParticles(location);
                    move4damageEntities(location, player);
                    location.add(player.getLocation().getDirection().multiply(0.4));
                }

                if (amountOfTicks > 30) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

        // Static beam
        new BukkitRunnable() {
            int amountOfTicks = 0;
            @Override
            public void run() {

                Location location = player.getLocation().add(0, 1, 0).add(player.getLocation().getDirection());

                for (int i = 0; i < 30; i++) {
                    spawnParticles(location);
                    move4damageEntities(location, player);
                    location.add(player.getLocation().getDirection().multiply(0.4));
                }

                if (amountOfTicks > 30) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 30L, 1L);

        // Removing beam
        new BukkitRunnable() {
            int amountOfTicks = 0;
            @Override
            public void run() {

                Location location = player.getLocation().add(0, 1, 0).add(player.getLocation().getDirection());
                location.add(player.getLocation().getDirection().multiply(0.4 * 30));
                for (int i = amountOfTicks; i < 30; i++) {
                    spawnParticles(location);
                    move4damageEntities(location, player);
                    location.add(player.getLocation().getDirection().multiply(-0.4));
                }

                if (amountOfTicks > 30) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 60L, 1L);
    }

    // play particle beam
    private static void spawnParticles(Location location) {
        for (int j = 0; j < 10; j++) {
            double locationX = location.getX() + StaticVariables.random.nextGaussian() / 10;
            double locationY = location.getY() + StaticVariables.random.nextGaussian() / 10;
            double locationZ = location.getZ() + StaticVariables.random.nextGaussian() / 10;
            Objects.requireNonNull(location.getWorld()).spawnParticle(Particle.WATER_BUBBLE, locationX, locationY, locationZ, 0);
        }
    }

    // damage entities in bubble beam
    private static void move4damageEntities(Location location, Player player) {
        World world = location.getWorld();
        if (world != null) {
            if (!world.getNearbyEntities(location, 0.5, 0.5, 0.5).isEmpty()) {
                for (Entity entity : world.getNearbyEntities(location, 0.5, 0.5, 0.5)) {
                    if (entity != null) {
                        if (entity instanceof LivingEntity) {
                            LivingEntity livingEntity = (LivingEntity) entity;
                            if (livingEntity != player) {
                                livingEntity.damage(1);
                            }
                        }
                    }
                }
            }
        }
    }


    // MOVE 5
    // Water ball
    // -> The player is able to pick up a ball of water
    // -> The water used for this ball is used from the world
    // -> The player will be unable to do this move if he isn't pointing at a water source


    // MOVE 6
    // Puffer Beam
    // -> Rapidly shoots 100 puffer fish in the looking direction



    // MOVE 7
    // Air bubble
    // -> When the player goes underwater there is an air bubble created around it



    // MOVE 8
    // Wave
    // -> Creates a huge wave knocking back entities
}
