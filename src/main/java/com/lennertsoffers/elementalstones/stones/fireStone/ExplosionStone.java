package com.lennertsoffers.elementalstones.stones.fireStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireFireworks;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.Grenade;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.GrenadeSmoke;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.FireworkTools;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ExplosionStone extends FireStone {

    // MOVE 4
    // Smoke Screen
    // -> Following up to flying rock
    // -> Explodes the flying block into a big smoke which makes it impossible to see through
    public static Runnable move4(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Grenade grenade = new GrenadeSmoke(player, Particle.REDSTONE, new Particle.DustOptions(Color.GRAY, 2));
            grenade.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }

    // MOVE 5
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
            World world = player.getWorld();
            Location startLocation = player.getLocation().add(0, 1, 0);
            Vector direction = startLocation.getDirection();

            Location endLocation = null;

            // Try to find target entity
            for (int i = 1; i <= 20; i++) {
                Collection<Entity> livingEntities = world.getNearbyEntities(startLocation.clone().add(direction.clone().multiply(i)), 1.5, 1.5, 1.5, entity -> entity instanceof LivingEntity && entity != player);
                if (!livingEntities.isEmpty()) {
                    for (Entity entity : livingEntities) {
                        Location entityLocation = entity.getLocation().add(0, 1, 0);

                        if (endLocation != null) {
                            if (MathTools.calculate3dDistance(startLocation, entityLocation) < MathTools.calculate3dDistance(startLocation, endLocation)) {
                                endLocation = entityLocation;
                            }
                        } else {
                            endLocation = entityLocation;
                        }
                    }
                }
            }

            // Set end location to target block or 20 blocks distance
            if (endLocation == null) {
                Block block = player.getTargetBlockExact(20, FluidCollisionMode.NEVER);

                if (block != null) {
                    endLocation = block.getLocation();
                } else {
                    endLocation = startLocation.clone().add(direction.clone().multiply(20));
                }
            }

            direction = new Vector(endLocation.getX() - startLocation.getX(), endLocation.getY() - startLocation.getY(), endLocation.getZ() - startLocation.getZ());
            Vector perpendicularDirection = direction.clone().rotateAroundY(Math.PI / 2).multiply(0.05);

            Location location1 = startLocation.clone().add(direction.clone().multiply(0.25).add(perpendicularDirection.clone().rotateAroundAxis(direction, StaticVariables.random.nextInt(360))));
            Location location2 = startLocation.clone().add(direction.clone().multiply(0.50)).add(perpendicularDirection.clone().rotateAroundAxis(direction, StaticVariables.random.nextInt(360)));

            Vector start_location1 = new Vector(location1.getX() - startLocation.getX(), location1.getY() - startLocation.getY(), location1.getZ() - startLocation.getZ());
            Vector location1_location2 = new Vector(location2.getX() - location1.getX(), location2.getY() - location1.getY(), location2.getZ() - location1.getZ());
            Vector location2_end = new Vector(endLocation.getX() - location2.getX(), endLocation.getY() - location2.getY(), endLocation.getZ() - location2.getZ());

            for (double i = 0.01; i <= 1; i += 0.01) {
                world.spawnParticle(Particle.WAX_OFF, startLocation.clone().add(start_location1.clone().multiply(i)), 0);
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    for (double i = 0.01; i <= 1; i += 0.01) {
                        world.spawnParticle(Particle.WAX_OFF, location1.clone().add(location1_location2.clone().multiply(i)), 0);
                    }
                }
            }.runTaskLater(StaticVariables.plugin, 2L);

            new BukkitRunnable() {
                @Override
                public void run() {
                    for (double i = 0.01; i <= 1; i += 0.01) {
                        world.spawnParticle(Particle.WAX_OFF, location2.clone().add(location2_end.clone().multiply(i)), 0);
                    }
                }
            }.runTaskLater(StaticVariables.plugin, 4L);

            Location finalEndLocation = endLocation;
            new BukkitRunnable() {
                @Override
                public void run() {
                    world.createExplosion(finalEndLocation, 2, true, true, player);
                }
            }.runTaskLater(StaticVariables.plugin, 6L);
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
