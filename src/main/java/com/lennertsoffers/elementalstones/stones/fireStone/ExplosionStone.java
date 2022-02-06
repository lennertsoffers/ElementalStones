package com.lennertsoffers.elementalstones.stones.fireStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireFireworks;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.FireworkTools;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class ExplosionStone extends FireStone {

    // MOVE 4
    // Smoke Screen
    // -> Following up to flying rock
    // -> Explodes the flying block into a big smoke which makes it impossible to see through
    public static Runnable move4(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            Location startLocation = player.getLocation();
            Vector direction = startLocation.getDirection().setY(0);
            Vector startVelocity = player.getVelocity();
            if (startVelocity.getY() < 0) {
                startVelocity.setY(0);
            }

            startLocation.add(direction).add(0, 1, 0).add(direction.clone().rotateAroundY(-90).multiply(2));

            new BukkitRunnable() {
                double i = 0;

                @Override
                public void run() {
                    HashMap<String, Double> result = MathTools.calculatePointOnThrowFunction(15, 1, startLocation.getYaw(), -startLocation.getPitch(), i, startVelocity);

                    int offset = 15;
                    double x = startLocation.getX() + result.get("x");
                    double y = startLocation.getY() + result.get("y");
                    double z = startLocation.getZ() + result.get("z");

                    for (int i = 0; i < 5; i++) {
                        double randomX = x + (StaticVariables.random.nextGaussian() / offset);
                        double randomY = y + (StaticVariables.random.nextGaussian() / offset);
                        double randomZ = z + (StaticVariables.random.nextGaussian() / offset);
                        world.spawnParticle(Particle.REDSTONE, randomX, randomY, randomZ, 0, new Particle.DustOptions(Color.GRAY, 2));
                    }

                    i += 0.05;
                    if (i > 4) {
                        this.cancel();
                    }

                    Location particleLocation = new Location(world, x, y, z);
                    if (CheckLocationTools.isSolidBlock(world.getBlockAt(particleLocation))) {
                        new BukkitRunnable() {
                            int loops = 0;

                            @Override
                            public void run() {
                                for (int i = 1; i < 400; i++) {
                                    double offsetBomb = 8;
                                    if (loops == 3) {
                                        offsetBomb = 1;
                                    } else if (loops == 5) {
                                        offsetBomb = 0.5;
                                    }
                                    double offsetX = particleLocation.getX() + StaticVariables.random.nextGaussian() / offsetBomb;
                                    double offsetY = particleLocation.getY() + Math.abs(StaticVariables.random.nextGaussian()) / offsetBomb + 0.3;
                                    double offsetZ = particleLocation.getZ() + StaticVariables.random.nextGaussian() / offsetBomb;
                                    world.spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, offsetX, offsetY, offsetZ, 0, StaticVariables.random.nextGaussian() / 40, Math.abs(StaticVariables.random.nextGaussian() / 40), StaticVariables.random.nextGaussian() / 40);
                                }

                                loops++;
                                if (loops > 20) {
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(StaticVariables.plugin, 0L, 20L);
                        this.cancel();
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }

    // MOVE 5
    // Better Gear
    // -> Swaps your current armor for the best possible armor in the game
    // -> After 60s, your normal gear will return
    public static Runnable move5(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            Location location = player.getLocation();
            Vector direction = location.getDirection().setY(0);

            if (player.isSneaking()) {
                Vector perpendicularDirection = direction.clone().rotateAroundY(Math.PI / 2);

                location.add(direction.clone().multiply(20));
                Location finalCenterLocation = location.clone();
                location.add(perpendicularDirection.clone().multiply(-10));

                new BukkitRunnable() {
                    int rows = 1;

                    @Override
                    public void run() {
                        for (int i = 1; i < 20; i++) {
                            Location fireworkLocation = location.clone().add(perpendicularDirection.clone().multiply(i)).add(direction.clone().multiply(rows));
                            fireworkLocation.setY(world.getHighestBlockYAt(location) + 1);
                            FireworkTools.setRandomMeta(((Firework) world.spawnEntity(fireworkLocation, EntityType.FIREWORK)), 1, null, 3, 3, -1, -1);
                        }

                        rows++;
                        if (rows > 10) {
                            this.cancel();
                            finalCenterLocation.setY(world.getHighestBlockYAt(finalCenterLocation) + 1);
                            FireworkTools.setRandomMeta(((Firework) world.spawnEntity(finalCenterLocation, EntityType.FIREWORK)), 1, FireworkEffect.Type.BALL_LARGE, 2, 2, 0, 1);
                        }
                    }
                }.runTaskTimer(StaticVariables.plugin, 0L, 50L);
            } else if (activePlayer.getFireFireworks() == null) {
                FireFireworks fireFireworks = new FireFireworks(player);
                activePlayer.setFireFireworks(fireFireworks);
                fireFireworks.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            } else {
                activePlayer.getFireFireworks().shootFireworks();
                activePlayer.setFireFireworks(null);
            }
        };
    }

    // Combustion beam
    public static Runnable move6(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = player.getLocation();
        };
    }

    // Random explosion
    public static Runnable move7(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = player.getLocation();
        };
    }

    // War machine
    public static Runnable move8(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = player.getLocation();
            World world = player.getWorld();
        };
    }
}
