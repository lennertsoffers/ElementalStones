package com.lennertsoffers.elementalstones.stones.waterStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.SetBlockTools;
import com.lennertsoffers.elementalstones.customClasses.tools.StringListTools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WaterStone {

    // MOVE 1
    // Splash
    // -> Splashes around some water
    // -> The higher your level, the more damage it does
    public static Runnable move1(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            player.setVelocity(new Vector(0, 0.2, 0));
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (
                            int i = 0;
                            i < 360; i++) {
                        Location centerLocation = MathTools.locationOnCircle(player.getLocation(), 3, i, world);
                        if (!world.getNearbyEntities(centerLocation, 1, 1, 1).isEmpty()) {
                            for (Entity entity : world.getNearbyEntities(centerLocation, 1, 1, 1)) {
                                if (entity != null) {
                                    if (entity instanceof LivingEntity) {
                                        LivingEntity livingEntity = (LivingEntity) entity;
                                        if (livingEntity != player) {
                                            livingEntity.damage(Math.pow(2, player.getLevel() / 30f), player);
                                        }
                                    }
                                }
                            }
                        }
                        for (int j = 0; j < 10; j++) {
                            double locationX = centerLocation.getX() + StaticVariables.random.nextGaussian() / 2;
                            double locationY = centerLocation.getY() + StaticVariables.random.nextGaussian() / 10;
                            double locationZ = centerLocation.getZ() + StaticVariables.random.nextGaussian() / 2;
                            world.spawnParticle(Particle.BUBBLE_POP, locationX, locationY, locationZ, 0);
                        }
                    }
                }
            }.runTaskLater(StaticVariables.plugin, 5L);
        };
    }

    // MOVE 2
    // Dolphin Dive
    // -> Player gets dolphin effect for 1 minute
    public static Runnable move2(ActivePlayer activePlayer) {
        return () -> {
            activePlayer.setDoublePassive1(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    activePlayer.setDoublePassive1(false);
                }
            }.runTaskLater(StaticVariables.plugin, 1800L);
            Player player = activePlayer.getPlayer();
            player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 1800, 10, true, true, true));
        };
    }




    // MOVE 3
    // Water Spear
    // -> Throw one of your water arms that damages entities on impact
    // -> Creates splash damage
    public static Runnable move3(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            ArrayList<Location> spearLocations = new ArrayList<>();
            Vector initialDirection = player.getLocation().getDirection();
            Location startLocation = player.getLocation().add(initialDirection.rotateAroundY(180)).add(0, 2, 0);
            Vector direction = player.getLocation().getDirection().multiply(0.9);
            new BukkitRunnable() {
                final Location location = startLocation.clone();
                int distance = 0;

                @Override
                public void run() {
                    Location currentLocation = location.clone();
                    for (Entity entity : player.getWorld().getNearbyEntities(currentLocation, 1, 1, 1)) {
                        if (entity instanceof LivingEntity) {
                            LivingEntity livingEntity = (LivingEntity) entity;
                            if (livingEntity != player) {
                                livingEntity.damage(5);
                            }
                        }
                    }
                    spearLocations.add(currentLocation);
                    for (Location location : spearLocations) {
                        if (placeWaterBlock(location, false)) {
                            this.cancel();
                            new BukkitRunnable() {
                                final ArrayList<Location> waterLocationsToRemove = spearLocations;

                                @Override
                                public void run() {
                                    if (waterLocationsToRemove.size() > 0) {
                                        placeWaterBlock(waterLocationsToRemove.get(0), true);
                                        waterLocationsToRemove.remove(0);
                                    } else {
                                        this.cancel();
                                    }
                                }
                            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
                        }
                    }
                    location.add(direction);
                    if (spearLocations.size() > 6) {
                        placeWaterBlock(spearLocations.get(0), true);
                        spearLocations.remove(0);
                    }
                    if (distance > 70) {
                        this.cancel();
                        for (Location location : spearLocations) {
                            location.getBlock().setType(Material.AIR);
                        }
                    }
                    distance++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }

    // place blocks if not already water
    private static boolean placeWaterBlock(Location location, boolean remove) {
        Location locationTop = location.clone().add(0, 1, 0);
        Location locationBottom = location.clone().add(0, -1, 0);
        if (remove) {
            if (location.getBlock().getType() == Material.WATER) {
                location.getBlock().setType(Material.AIR);
            }
        } else {
            if (location.getBlock().getType() == Material.AIR) {
                location.getBlock().setType(Material.POWDER_SNOW);
            } else {
                if (location.getBlock().getType() != Material.WATER || (locationBottom.getBlock().getType() != Material.WATER && locationBottom.getBlock().getType() != Material.AIR)) {
                    return true;
                }
            }
        }
        if (locationTop.getBlock().getType() == Material.AIR) {
            locationTop.getBlock().setType(Material.AIR);
        }
        if (locationBottom.getBlock().getType() == Material.AIR || locationBottom.getBlock().getType() == Material.WATER) {
            locationBottom.getBlock().setType(Material.AIR);
        }
        return false;
    }
}

























