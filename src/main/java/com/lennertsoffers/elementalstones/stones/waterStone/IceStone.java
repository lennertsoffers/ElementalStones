package com.lennertsoffers.elementalstones.stones.waterStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.SetBlockTools;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class IceStone extends WaterStone {

    // PASSIVE


    // MOVE 4
    // Ice Shards
    // -> You can throw ice shards damaging entities on impact
    // -> A player can have up to 10 shards

    // Move
    public static void move4(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        if (activePlayer.useIceShard()) {
            Location location = player.getLocation().add(0, 1.5, 0);
            Vector direction = location.getDirection();
            new BukkitRunnable() {
                int amountOfTicks = 1;
                Location midpoint = location.add(direction);
                @Override
                public void run() {
                    for (int i = 0; i < 60; i++) {
                        Particle.DustOptions dustOptions;
                        if (StaticVariables.random.nextBoolean()) {
                            dustOptions = new Particle.DustOptions(Color.fromRGB(0, 165, 255), 0.3f);
                        } else {
                            dustOptions = new Particle.DustOptions(Color.WHITE, 0.3f);
                        }
                        if (world.getBlockAt(midpoint).getType().isSolid()) {
                            this.cancel();
                            move4Impact(midpoint);
                        }
                        for (Entity entity : world.getNearbyEntities(midpoint, 0.3, 0.3, 0.3)) {
                            if (entity != null) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingEntity = (LivingEntity) entity;
                                    if (livingEntity != player) {
                                        livingEntity.damage(1);
                                        livingEntity.setVelocity(direction.setY(0.3));
                                        this.cancel();
                                        move4Impact(midpoint);
                                    }
                                }
                            }
                        }
                        world.spawnParticle(Particle.REDSTONE, midpoint.clone().add(StaticVariables.random.nextGaussian() / 20, StaticVariables.random.nextGaussian() / 20, StaticVariables.random.nextGaussian() / 20), 0, direction.getX(), direction.getY(), direction.getZ(), dustOptions);
                        if (i % 2 == 0) {
                            midpoint.add(direction.getX() / 30, direction.getY() / 30, direction.getZ() / 30);
                        }
                    }
                    if (amountOfTicks > 10) {
                        this.cancel();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        }
    }

    // particles on impact
    private static void move4Impact(Location impactLocation) {
        for (int i = 0; i < 5; i++) {
            ItemStack stack;
            if (StaticVariables.random.nextBoolean()) {
                stack = new ItemStack(Material.ICE);
            } else {
                stack = new ItemStack(Material.SNOW_BLOCK);
            }
            Objects.requireNonNull(impactLocation.getWorld()).spawnParticle(Particle.ITEM_CRACK, impactLocation.clone().add(StaticVariables.random.nextGaussian() / 10, StaticVariables.random.nextGaussian() / 10, StaticVariables.random.nextGaussian() / 10), 0, 0, 0, 0, stack);
        }
    }

    // MOVE 5
    // Ice Beam
    // ->

    // MOVE 6
    // Snow Stomp
    // -> Turns a pad of ground into powder snow trapping entities standing on it
    public static void move6(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        Location playerLocation = Objects.requireNonNull(player.getTargetBlockExact(20)).getLocation();
        ArrayList<Location> snowBlockLocations = new ArrayList<>();
        snowBlockLocations.add(playerLocation.clone().add(-1, 0, 2));
        snowBlockLocations.add(playerLocation.clone().add(0, 0, 2));
        snowBlockLocations.add(playerLocation.clone().add(1, 0, 2));
        snowBlockLocations.add(playerLocation.clone().add(-1, 0, -2));
        snowBlockLocations.add(playerLocation.clone().add(0, 0, -2));
        snowBlockLocations.add(playerLocation.clone().add(1, 0, -2));
        snowBlockLocations.add(playerLocation.clone().add(2, 0, -1));
        snowBlockLocations.add(playerLocation.clone().add(2, 0, 0));
        snowBlockLocations.add(playerLocation.clone().add(2, 0, 1));
        snowBlockLocations.add(playerLocation.clone().add(-2, 0, -1));
        snowBlockLocations.add(playerLocation.clone().add(-2, 0, 0));
        snowBlockLocations.add(playerLocation.clone().add(-2, 0, 1));
        playerLocation.add(-1, 0, -1);
        for (int i = 1; i <= 9; i++) {
            snowBlockLocations.add(playerLocation.clone());
            if (i % 3 == 0) {
                playerLocation.add(-3, 0, 1);
            }
            playerLocation.add(1, 0, 0);
        }
        for (Location location : snowBlockLocations) {
            Block block = world.getHighestBlockAt(location);
            Block block1 = world.getBlockAt(block.getLocation().add(0, -1, 0));
            Block block2 = world.getBlockAt(block.getLocation().add(0, -2, 0));
            Block block3 = world.getBlockAt(block.getLocation().add(0, -3, 0));
            activePlayer.addLocationMaterialMapping(block.getLocation(), block.getType());
            activePlayer.addLocationMaterialMapping(block1.getLocation(), block1.getType());
            activePlayer.addLocationMaterialMapping(block2.getLocation(), block1.getType());
            activePlayer.addLocationMaterialMapping(block3.getLocation(), block1.getType());
            block.setType(Material.POWDER_SNOW);
            block1.setType(Material.POWDER_SNOW);
            block2.setType(Material.POWDER_SNOW);
            block3.setType(Material.POWDER_SNOW);
        }
    }

    // MOVE 7
    // Deep Freeze
    // -> Throws an ice bal that penetrates trough walls
    // -> If an entity is hit by this ball, it will be unable to move and see

    // move
    public static void move7(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        Location location = player.getLocation().add(0, 1.5, 0).add(player.getLocation().getDirection());
        World world = player.getWorld();
        new BukkitRunnable() {
            int amountOfTicks = 0;
            final Location currentLocation = location;
            @Override
            public void run() {
                Vector playerDirection = player.getLocation().getDirection();
                for (int i = 0; i < 10; i++) {
                    ItemStack stack;
                    if (StaticVariables.random.nextBoolean()) {
                        stack = new ItemStack(Material.ICE);
                    } else {
                        stack = new ItemStack(Material.SNOW_BLOCK);
                    }
                    world.spawnParticle(Particle.ITEM_CRACK, location.clone().add(StaticVariables.random.nextGaussian() / 10, StaticVariables.random.nextGaussian() / 10, StaticVariables.random.nextGaussian() / 10), 0, 0, 0, 0, 0, stack);
                    if (!world.getNearbyEntities(location, 0.5, 0.5, 0.5).isEmpty()) {
                        for (Entity entity : world.getNearbyEntities(location, 0.5, 0.5, 0.5)) {
                            if (entity != null) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingEntity = (LivingEntity) entity;
                                    if (livingEntity != player) {
                                        freezeEffect(livingEntity, activePlayer);
                                        this.cancel();
                                    }
                                }
                            }
                        }
                    }
                }

                if (amountOfTicks > 800) {
                    this.cancel();
                }
                amountOfTicks++;
                currentLocation.add(playerDirection.multiply(0.1));
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }

    // freeze effect
    private static void freezeEffect(LivingEntity target, ActivePlayer activePlayer) {
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 100, false, false, false));
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 100, false, false, false));
        target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 10, false, false, false));
        Location startLocation = target.getLocation();
        String[] form1Layer0 = {
                "?PBP?",
                "BIIIB",
                "BI*IP",
                "PPPPI",
                "?IBB?"
        };
        String[] form1Layer1 = {
                "??P??",
                "?PPI?",
                "BP*PB",
                "?PPP?",
                "??B??"
        };
        String[] form1Layer2 = {
                "?B?",
                "I*B",
                "?P?"
        };
        String[] form2Layer0 = {
                "??I??",
                "?PBI?",
                "PP*PB",
                "?BIP?",
                "??P??"
        };
        String[] form2Layer1 = {
                "?P?",
                "I*B",
                "?P?"
        };
        String[] form2Layer2 = {
                "*"
        };
        Map<Character, Material> characterMaterialMap = new HashMap<>();
        characterMaterialMap.put('I', Material.ICE);
        characterMaterialMap.put('P', Material.PACKED_ICE);
        characterMaterialMap.put('B', Material.BLUE_ICE);

        SetBlockTools.setBlocks(startLocation, form1Layer0, characterMaterialMap, true, Material.POWDER_SNOW, activePlayer);
        SetBlockTools.setBlocks(startLocation.clone().add(0, 1, 0), form1Layer1, characterMaterialMap, true, Material.PACKED_ICE, activePlayer);
        SetBlockTools.setBlocks(startLocation.clone().add(0, 2, 0), form1Layer2, characterMaterialMap, true, Material.PACKED_ICE, activePlayer);
        target.setFreezeTicks(100);
        new BukkitRunnable() {
            @Override
            public void run() {
                activePlayer.resetWorld();
                SetBlockTools.setBlocks(startLocation, form2Layer0, characterMaterialMap, true, Material.POWDER_SNOW, activePlayer);
                SetBlockTools.setBlocks(startLocation.clone().add(0, 1, 0), form2Layer1, characterMaterialMap, true, Material.PACKED_ICE, activePlayer);
                SetBlockTools.setBlocks(startLocation.clone().add(0, 2, 0), form2Layer2, characterMaterialMap, true, Material.PACKED_ICE, activePlayer);
            }
        }.runTaskLater(StaticVariables.plugin, 100L);
        new BukkitRunnable() {
            @Override
            public void run() {
                activePlayer.resetWorld();
            }
        }.runTaskLater(StaticVariables.plugin, 200L);
    }


    // MOVE 8

}