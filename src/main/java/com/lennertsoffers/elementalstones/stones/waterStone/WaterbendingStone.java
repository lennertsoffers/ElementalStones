package com.lennertsoffers.elementalstones.stones.waterStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.SetBlockTools;
import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class WaterbendingStone extends WaterStone {

    // PASSIVE
    // Passive 1: Deep breath
    // -> When the player is in the water, he gets the water breathing effect
    public static void passive1(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.getInventory().contains(ItemStones.waterStoneBending0) ||
                    player.getInventory().contains(ItemStones.waterStoneBending1) ||
                    player.getInventory().contains(ItemStones.waterStoneBending2) ||
                    player.getInventory().contains(ItemStones.waterStoneBending3) ||
                    player.getInventory().contains(ItemStones.waterStoneBending4)
                ) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 210, 1, false, false, false));
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 200L);
    }

    // Passive 2: Water Walker
    // -> Move normal in water
    public static void passive2(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        if (player.getLocation().getBlock().getType() == Material.WATER) {
            if (player.getInventory().contains(ItemStones.waterStoneBending0) ||
                    player.getInventory().contains(ItemStones.waterStoneBending1) ||
                    player.getInventory().contains(ItemStones.waterStoneBending2) ||
                    player.getInventory().contains(ItemStones.waterStoneBending3) ||
                    player.getInventory().contains(ItemStones.waterStoneBending4)
            ) {
                if (player.getPose() != Pose.SNEAKING) {
                    if (!activePlayer.isDoublePassive1()) {
                        player.setVelocity(player.getLocation().getDirection().multiply(0.3));
                    } else {
                        player.setVelocity(player.getLocation().getDirection());
                    }
                }
            }
        }
    }


    // MOVE 4
    // Bubblebeam
    // -> Shoots beam of bubbles in the looking direction of the player
    public static Runnable move4(ActivePlayer activePlayer) {
        return () -> {
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
        };
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
    // Healing water
    // -> The player heals himself if he/she stands in water using this move
    public static Runnable move5(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            new BukkitRunnable() {
                int amountOfSeconds = 0;

                @Override
                public void run() {
                    Location location = player.getLocation();
                    if (location.getBlock().getType() == Material.WATER) {
                        if (player.getHealth() > 19) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 1, true, true, true));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 2, true, true, true));
                        }
                        player.setHealth(player.getHealth() + 1);

                    }
                    location.add(0, 1, 0);
                    if (amountOfSeconds > 10) {
                        this.cancel();
                    }
                    amountOfSeconds++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 20L);
        };
    }


    // MOVE 6
    // Puffer Beam
    // -> Rapidly shoots 100 puffer fish in the looking direction
    public static Runnable move6(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {
                    Location location = player.getLocation().add(0, 1, 0);
                    location.add(location.getDirection());

                    Entity pufferFish = player.getWorld().spawnEntity(location, EntityType.PUFFERFISH);
                    pufferFish.setVelocity(location.getDirection().multiply(2));

                    if (amountOfTicks >= 100) {
                        this.cancel();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }


    // MOVE 7
    // Wave
    // -> Creates a huge wave knocking back entities
    public static Runnable move7(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = player.getLocation();
            Vector direction = location.getDirection().setY(0);
            ArrayList<Location> waterBlockLocations = new ArrayList<>();
            ArrayList<Integer> ignoreAnglesB = new ArrayList<>();
            ArrayList<Integer> ignoreAnglesM = new ArrayList<>();
            ArrayList<Integer> ignoreAnglesT = new ArrayList<>();
            new BukkitRunnable() {
                int amountOfTicks = 1;

                @Override
                public void run() {

                    replaceWater(waterBlockLocations);
                    waterBlockLocations.clear();
                    for (int i = 0; i < 360; i++) {
                        System.out.println(i);
                        Location waterBlockLocationB = location.clone().add(direction.clone().rotateAroundY(i).multiply(amountOfTicks));
                        Location waterBlockLocationM = waterBlockLocationB.clone().add(0, 1, 0);
                        Location waterBlockLocationT = waterBlockLocationB.clone().add(0, 2, 0);

                        if (waterBlockLocationB.getBlock().getType() != Material.AIR && waterBlockLocationB.getBlock().getType() != Material.WATER) {
                            ignoreAnglesB.add(i);
                            System.out.println("test");
                        }
                        if (waterBlockLocationM.getBlock().getType() != Material.AIR && waterBlockLocationM.getBlock().getType() != Material.WATER) {
                            ignoreAnglesM.add(i);
                            System.out.println("test");
                        }
                        if (waterBlockLocationT.getBlock().getType() != Material.AIR && waterBlockLocationT.getBlock().getType() != Material.WATER) {
                            ignoreAnglesT.add(i);
                            System.out.println("test");
                        }

                        if (!ignoreAnglesB.contains(i)) {
                            waterBlockLocations.add(waterBlockLocationB);
                            waterBlockLocationB.getBlock().setType(Material.WATER);
                            knockBackEntities(waterBlockLocationB, player);
                        }
                        if (!ignoreAnglesM.contains(i)) {
                            waterBlockLocations.add(waterBlockLocationM);
                            waterBlockLocationM.getBlock().setType(Material.WATER);
                            knockBackEntities(waterBlockLocationM, player);
                        }
                        if (!ignoreAnglesT.contains(i)) {
                            waterBlockLocations.add(waterBlockLocationT);
                            waterBlockLocationT.getBlock().setType(Material.WATER);
                            knockBackEntities(waterBlockLocationT, player);
                        }
                    }

                    if (amountOfTicks > 20) {
                        this.cancel();
                        replaceWater(waterBlockLocations);
                        waterBlockLocations.clear();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }

    // Check if entity collision
    private static void knockBackEntities(Location location, Player player) {
        if (location.getWorld() != null) {
            if (!location.getWorld().getNearbyEntities(location, 0.5, 0.5, 0.5).isEmpty()) {
                for (Entity entity : location.getWorld().getNearbyEntities(location, 0.5, 0.5, 0.5)) {
                    if (entity != null) {
                        if (entity != player) {
                            System.out.println(MathTools.getDirectionNormVector(player.getLocation(), entity.getLocation()).multiply(0.05));
                            entity.setVelocity(MathTools.getDirectionNormVector(player.getLocation(), entity.getLocation()).multiply(0.05));
                        }
                    }
                }
            }
        }
    }

    // Replace water with air again
    private static void replaceWater(ArrayList<Location> waterBlockLocations) {
        for (Location waterBlockLocation : waterBlockLocations) {
            if (waterBlockLocation.getBlock().getType() == Material.WATER) {
                waterBlockLocation.getBlock().setType(Material.AIR);
            }
            if (waterBlockLocation.clone().add(0, 1, 0).getBlock().getType() == Material.WATER) {
                waterBlockLocation.clone().add(0, 1, 0).getBlock().setType(Material.AIR);
            }
            if (waterBlockLocation.clone().add(0, 2, 0).getBlock().getType() == Material.WATER) {
                waterBlockLocation.clone().add(0, 2, 0).getBlock().setType(Material.AIR);
            }
        }
    }

    // MOVE 8
    // Water Wall
    public static Runnable move8(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {

                    Block targetBlock = player.getTargetBlockExact(10);
                    if (targetBlock != null) {
                        targetBlock.getLocation().add(0, 4, 0).getBlock().setType(Material.WATER);
                        new BukkitRunnable() {
                            int amountOfAliveTicks = 0;

                            @Override
                            public void run() {
                                if (!player.getWorld().getNearbyEntities(targetBlock.getLocation(), 2, 4, 2).isEmpty()) {
                                    for (Entity entity : player.getWorld().getNearbyEntities(targetBlock.getLocation(), 1, 4, 1)) {
                                        if (entity != null) {
                                            entity.setVelocity(new Vector(0, 0, 0));
                                        }
                                    }
                                }
                                if (amountOfAliveTicks > 200) {
                                    this.cancel();
                                    targetBlock.getLocation().add(0, 4, 0).getBlock().setType(Material.AIR);
                                }
                                amountOfAliveTicks++;
                            }
                        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
                    }


                    if (amountOfTicks > 19) {
                        this.cancel();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }
}




















