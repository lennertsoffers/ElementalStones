package com.lennertsoffers.elementalstones.stones.waterStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.SetBlockTools;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

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
    // Healing water
    // -> The player heals himself if he/she stands in water using this move
    public static void move5(ActivePlayer activePlayer) {
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
                if (amountOfSeconds > 10 ) {
                    this.cancel();
                }
                amountOfSeconds++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 20L);
    }


    // MOVE 6
    // Puffer Beam
    // -> Rapidly shoots 100 puffer fish in the looking direction
    public static void move6(ActivePlayer activePlayer) {
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
    }


    // MOVE 7
    // Wave
    // -> Creates a huge wave knocking back entities
    public static void move7(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        Map<Character, Material> characterMaterialMap = new HashMap<>();
        characterMaterialMap.put('A', Material.AIR);
        characterMaterialMap.put('W', Material.WATER);
        ArrayList<Material> overrideBlocks = new ArrayList<>();
        overrideBlocks.add(Material.WATER);
        String[] waveLayer0 = {
                "AAAAAAAAA",
                "AAAAAAAAA",
                "AAWWWWWAA",
                "AWWWWWWWA",
                "AWWW*WWWA",
                "AWWWWWWWA",
                "AAWWWWWAA",
                "AAAAAAAAA",
                "AAAAAAAAA"
        };
        String[] waveLayer1 = {
                "AAAAAAA",
                "AWWWWWA",
                "AWW*WWA",
                "AWWWWWA",
                "AAAAAAA"
        };
        Location startLocation1 = player.getLocation().add(2, 0, 0);
        Location startLocation2 = player.getLocation().add(-2, 0, 0);
        Location startLocation3 = player.getLocation().add(0, 0, 2);
        Location startLocation4 = player.getLocation().add(0, 0, -2);
        new BukkitRunnable() {
            int amountOfTicks = 0;
            @Override
            public void run() {

//                SetBlockTools.setBlocks(startLocation, waveLayer0, characterMaterialMap, true, overrideBlocks, Material.WATER, activePlayer);
//                SetBlockTools.setBlocks(startLocation.clone().add(0, 1, 0), waveLayer1, characterMaterialMap, true, overrideBlocks, Material.WATER, activePlayer);
//                startLocation.add(1, 0, 0);
                if (amountOfTicks > 100) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }

    // MOVE 8
    // Water Wall
    public static void move8(ActivePlayer activePlayer) {
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
    }
}




















