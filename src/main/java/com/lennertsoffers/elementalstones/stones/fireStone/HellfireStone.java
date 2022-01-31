package com.lennertsoffers.elementalstones.stones.fireStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

public class HellfireStone extends FireStone {

    private static final Map<LivingEntity, Long> damagedEntityCoolDownMove7 = new HashMap<>();
    private static final Map<LivingEntity, Long> damagedEntityCoolDownMove8 = new HashMap<>();


    private static void spawnSmallFlameOrb(Location location, Vector direction, Random random, World world) {
        for (int j = 0; j < 2; j++) {
            Location flameLocation = location.clone().add(direction);
            flameLocation.add(random.nextGaussian() / 20, random.nextGaussian() / 20, random.nextGaussian() / 20);
            world.spawnParticle(Particle.FLAME, flameLocation, 0, 0, 0, 0);
        }
    }

    private static void damageNearbyEntitiesMove7(Location location, World world, Player player) {
        for (Entity entity : world.getNearbyEntities(location, 0.5, 0.5, 0.5)) {
            if (entity != null) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    if (livingEntity != player) {
                        if (HellfireStone.damagedEntityCoolDownMove7.containsKey(livingEntity)) {
                            if (HellfireStone.damagedEntityCoolDownMove7.get(livingEntity) < System.currentTimeMillis()) {
                                HellfireStone.damagedEntityCoolDownMove7.replace(livingEntity, System.currentTimeMillis() + 1000);
                                livingEntity.damage(4);
                                entity.setFireTicks(entity.getFireTicks() + 40);
                            }
                        } else {
                            HellfireStone.damagedEntityCoolDownMove7.put(livingEntity, System.currentTimeMillis() + 1000);
                            livingEntity.damage(4);
                            entity.setFireTicks(entity.getFireTicks() + 40);
                        }
                    }
                }
            }
        }
    }

    // PASSIVE
    // Friendly Fire
    public static void passive(ActivePlayer activePlayer, EntityDamageEvent event) {
        if (event.getCause().equals(EntityDamageEvent.DamageCause.FIRE) || event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {
            event.setCancelled(true);
        }
    }

    // MOVE 4
    // Fire Track
    // -> You leave a fire track behind you
    // Activation
    public static Runnable move4(ActivePlayer activePlayer) {
        return activePlayer::setHellfireStoneMove4TimeRemaining;
    }
    // Move
    public static void move4(ActivePlayer activePlayer, PlayerMoveEvent event) {
        if (activePlayer.hasHellfireStoneMove4TimeRemaining()) {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            Block block = world.getBlockAt(event.getFrom().add(0, -1, 0));
            if (block.getType() != Material.AIR) {
                Block fireBlock = block.getRelative(BlockFace.UP);
                fireBlock.setType(Material.FIRE);
                StaticVariables.scheduler.scheduleSyncDelayedTask(StaticVariables.plugin, () -> fireBlock.setType(Material.AIR), 80L);
            }
        }
    }

    // MOVE 5
    // Fire Blast
    // -> Follow up to floating fire
    // -> Shoots fire ball in the looking direction
    public static Runnable move5(ActivePlayer activePlayer) {
        return () -> {
//            if (activePlayer.getFloatingFire() != null) {
//                activePlayer.cancelFloatingFire();
//                Player player = activePlayer.getPlayer();
//                new BukkitRunnable() {
//                    int counter = 0;
//
//                    @Override
//                    public void run() {
//                        Location location = player.getLocation();
//                        Vector direction = location.getDirection();
//                        Location fireBallLocation = location.add(direction.getX() * 2.5, direction.getY() * 2.5 + 0.85, direction.getZ() * 2.5).add(0, 1.5, 0);
//                        player.getWorld().spawnEntity(fireBallLocation, EntityType.FIREBALL).setVelocity(direction);
//                        if (counter >= 10) {
//                            this.cancel();
//                        }
//                        counter++;
//                    }
//                }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
//            }
        };
    }

    // MOVE 6
    // Into the Underworld
    // -> Follow up to floating fire
    // -> Instant nether teleportation
    public static Runnable move6(ActivePlayer activePlayer) {
        return () -> {
//            if (activePlayer.getFloatingFire() != null) {
//                activePlayer.cancelFloatingFire();
//                Player player = activePlayer.getPlayer();
//                new BukkitRunnable() {
//                    int tickCount = 0;
//                    final Random random = new Random();
//                    final double nominator = 12;
//                    final Location fireballLocation = activePlayer.getFloatingFireLocation();
//
//                    @Override
//                    public void run() {
//                        World world = player.getWorld();
//                        Vector direction = player.getLocation().add(0, 1.5, 0).toVector().subtract(fireballLocation.toVector());
//                        for (int i = 0; i < tickCount; i++) {
//                            Location particleLocation = fireballLocation.clone();
//                            world.spawnParticle(Particle.FLAME, particleLocation.add(random.nextGaussian() / nominator, random.nextGaussian() / nominator, random.nextGaussian() / nominator), 0, direction.getX() / 50, direction.getY() / 50, direction.getZ() / 50, 2);
//                        }
//                        fireballLocation.add(direction.getX() / 80, direction.getY() / 80, direction.getZ() / 80);
//                        if (tickCount > 100) {
//                            this.cancel();
//                        }
//                        tickCount++;
//                    }
//                }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
//                new BukkitRunnable() {
//                    @Override
//                    public void run() {
//                        World overworld = Bukkit.getWorld("WORLD");
//                        World nether = Bukkit.getWorld("WORLD_NETHER");
//                        if (overworld != null) {
//                            if (nether != null) {
//                                if (player.getWorld().getEnvironment() == World.Environment.NETHER) {
//                                    Location location = player.getLocation();
//                                    location.setWorld(overworld);
//                                    location.setX(location.getX() * 8);
//                                    location.setZ(location.getZ() * 8);
//                                    location.setY(overworld.getHighestBlockYAt(location));
//                                    player.teleport(location);
//                                } else if (player.getWorld().getEnvironment() == World.Environment.NORMAL) {
//                                    Location location = player.getLocation();
//                                    location.setWorld(nether);
//                                    location.setX(location.getX() / 8);
//                                    location.setY(32);
//                                    location.setZ(location.getZ() / 8);
//                                    boolean foundLocation = false;
//                                    int height = 32;
//                                    while (height < 110 && !foundLocation) {
//                                        location.setY(height);
//                                        if (
//                                                nether.getBlockAt(location).getType().isSolid() &&
//                                                        nether.getBlockAt(location.getBlockX(), height + 1, location.getBlockZ()).getType() == Material.AIR &&
//                                                        nether.getBlockAt(location.getBlockX(), height + 2, location.getBlockZ()).getType() == Material.AIR
//                                        ) {
//                                            location = new Location(nether, location.getBlockX(), height, location.getBlockZ());
//                                            foundLocation = true;
//                                        }
//                                        height++;
//                                    }
//                                    if (!foundLocation) {
//                                        location.setY(31);
//                                        Location blockLocation = location.clone();
//                                        blockLocation.setX(blockLocation.getBlockX() + 2);
//                                        blockLocation.setZ(blockLocation.getBlockZ() + 2);
//                                        for (int i = 1; i <= 25; i++) {
//                                            nether.getBlockAt(blockLocation).setType(Material.OBSIDIAN);
//                                            blockLocation.add(-1, 0, 0);
//                                            if (i % 5 == 0) {
//                                                blockLocation.add(5, 0, -1);
//                                            }
//                                        }
//                                        for (int i = 1; i <= 2; i++) {
//                                            blockLocation = location.clone();
//                                            blockLocation.setX(blockLocation.getBlockX() + 2);
//                                            blockLocation.setZ(blockLocation.getBlockZ() + 2);
//                                            blockLocation.add(0, i, 0);
//                                            nether.getBlockAt(blockLocation).setType(Material.NETHERRACK);
//                                            nether.getBlockAt(blockLocation.add(-1, 0, 0)).setType(Material.NETHERRACK);
//                                            nether.getBlockAt(blockLocation.add(-1, 0, 0)).setType(Material.NETHERRACK);
//                                            nether.getBlockAt(blockLocation.add(-1, 0, 0)).setType(Material.NETHERRACK);
//                                            nether.getBlockAt(blockLocation.add(-1, 0, 0)).setType(Material.NETHERRACK);
//                                            nether.getBlockAt(blockLocation.add(0, 0, -1)).setType(Material.NETHERRACK);
//                                            nether.getBlockAt(blockLocation.add(1, 0, 0)).setType(Material.AIR);
//                                            nether.getBlockAt(blockLocation.add(1, 0, 0)).setType(Material.AIR);
//                                            nether.getBlockAt(blockLocation.add(1, 0, 0)).setType(Material.AIR);
//                                            nether.getBlockAt(blockLocation.add(1, 0, 0)).setType(Material.NETHERRACK);
//                                            nether.getBlockAt(blockLocation.add(0, 0, -1)).setType(Material.NETHERRACK);
//                                            nether.getBlockAt(blockLocation.add(-1, 0, 0)).setType(Material.AIR);
//                                            nether.getBlockAt(blockLocation.add(-1, 0, 0)).setType(Material.AIR);
//                                            nether.getBlockAt(blockLocation.add(-1, 0, 0)).setType(Material.AIR);
//                                            nether.getBlockAt(blockLocation.add(-1, 0, 0)).setType(Material.NETHERRACK);
//                                            nether.getBlockAt(blockLocation.add(0, 0, -1)).setType(Material.NETHERRACK);
//                                            nether.getBlockAt(blockLocation.add(1, 0, 0)).setType(Material.AIR);
//                                            nether.getBlockAt(blockLocation.add(1, 0, 0)).setType(Material.AIR);
//                                            nether.getBlockAt(blockLocation.add(1, 0, 0)).setType(Material.AIR);
//                                            nether.getBlockAt(blockLocation.add(1, 0, 0)).setType(Material.NETHERRACK);
//                                            nether.getBlockAt(blockLocation.add(0, 0, -1)).setType(Material.NETHERRACK);
//                                            nether.getBlockAt(blockLocation.add(-1, 0, 0)).setType(Material.NETHERRACK);
//                                            nether.getBlockAt(blockLocation.add(-1, 0, 0)).setType(Material.NETHERRACK);
//                                            nether.getBlockAt(blockLocation.add(-1, 0, 0)).setType(Material.NETHERRACK);
//                                            nether.getBlockAt(blockLocation.add(-1, 0, 0)).setType(Material.NETHERRACK);
//                                        }
//                                        blockLocation.add(3, 1, 3);
//                                        for (int i = 1; i <= 9; i++) {
//                                            nether.getBlockAt(blockLocation).setType(Material.NETHERRACK);
//                                            blockLocation.add(-1, 0, 0);
//                                            if (i % 3 == 0) {
//                                                blockLocation.add(3, 0, -1);
//                                            }
//                                        }
//                                    }
//                                    player.teleport(location);
//
//                                } else {
//                                    player.teleport(nether.getSpawnLocation());
//                                }
//                            } else {
//                                player.sendMessage("Incorrect configuration of nether name");
//                            }
//                        } else {
//                            player.sendMessage("Incorrect configuration of overworld name");
//                        }
//                    }
//                }.runTaskLater(StaticVariables.plugin, 120L);
//            }
        };
    }

    // MOVE 7
    // Fire Beam
    // Shoot an array of destructing fire out of your hands
    public static Runnable move7(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            new BukkitRunnable() {
                double stageOfBeam = 0.1;
                final Random random = new Random();

                @Override
                public void run() {
                    final Location location = player.getEyeLocation().add(0, -0.6, 0);
                    final Vector direction = location.getDirection();
                    Location variableLocation = location.clone();
                    for (double i = 0.1; i <= stageOfBeam; i += 0.1) {
                        damageNearbyEntitiesMove7(variableLocation, world, player);
                        spawnSmallFlameOrb(variableLocation, direction, random, world);
                        variableLocation.add(direction.getX() / 40 * i, direction.getY() / 40 * i, direction.getZ() / 40 * i);
                    }
                    if (stageOfBeam >= 7) {
                        this.cancel();
                    }
                    stageOfBeam += 0.35;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            new BukkitRunnable() {
                int tickCount = 0;
                final Random random = new Random();

                @Override
                public void run() {
                    final Location location = player.getEyeLocation();
                    final Vector direction = location.getDirection();
                    location.add(0, -0.6, 0);
                    for (double i = 0.1; i < 7; i += 0.1) {
                        damageNearbyEntitiesMove7(location, world, player);
                        spawnSmallFlameOrb(location, direction, random, world);
                        location.add(direction.getX() / 40 * i, direction.getY() / 40 * i, direction.getZ() / 40 * i);
                    }
                    if (tickCount > 100) {
                        this.cancel();
                    }
                    tickCount++;
                }
            }.runTaskTimer(StaticVariables.plugin, 20L, 1L);
        };
    }

    // MOVE 8
    // Dragons Breath
    // -> Creates a ring of fire around the player damaging entities
    // -> Player shoots continuously fireballs at the location he aims
    // -> This move has to charge for 3 seconds
    public static Runnable move8(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            new BukkitRunnable() {
                int tickCount = 0;
                final Random random = new Random();

                @Override
                public void run() {
                    Location location = player.getLocation().add(0, 1, 0);
                    Location particleLocation = location.clone();
                    double addX = random.nextDouble();
                    double addY = random.nextDouble();
                    double addZ = random.nextDouble();
                    if (random.nextBoolean()) {
                        particleLocation.add(addX, 0, 0);
                    } else {
                        particleLocation.add(-addX, 0, 0);
                    }

                    if (random.nextBoolean()) {
                        particleLocation.add(0, addY, 0);
                    } else {
                        particleLocation.add(0, -addY, 0);
                    }

                    if (random.nextBoolean()) {
                        particleLocation.add(0, 0, addZ);
                    } else {
                        particleLocation.add(0, 0, 0 - addZ);
                    }
                    Vector particleDirection = location.toVector().subtract(particleLocation.toVector());
                    world.spawnParticle(Particle.FLAME, particleLocation, 0, particleDirection.getX() / 100, particleDirection.getY() / 100, particleDirection.getZ() / 100, 4);
                    if (tickCount > 58) {
                        this.cancel();
                    }
                    tickCount++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            new BukkitRunnable() {
                int radius = 20;
                final Random random = new Random();

                @Override
                public void run() {
                    Location location = player.getLocation();
                    if (radius % 2 == 0) {
                        Entity fireball = world.spawnEntity(location.clone().add(0, 3, 0), EntityType.FIREBALL);
                        fireball.setVelocity(location.getDirection().setY(-0.3));
                    }
                    for (int i = 0; i < 360; i++) {
                        Location particleLocation = MathTools.locationOnCircle(location, (double) radius / 10, i, world);
                        particleLocation.add(random.nextGaussian() / 2, random.nextGaussian() / 2, random.nextGaussian() / 2);
                        Vector particleDirection = MathTools.directionOfVector(location, particleLocation);
                        particleDirection.setX(particleDirection.getX() / 150);
                        particleDirection.setZ(particleDirection.getZ() / 150);
                        Location newParticleLocation = particleLocation.add(particleDirection).getBlock().getLocation();
                        world.spawnParticle(Particle.FLAME, particleLocation, 0, particleDirection.getX(), 0, particleDirection.getZ(), 70);
                        Stream.concat(world.getNearbyEntities(newParticleLocation, 0.5, 0.5, 0.5).stream(), world.getNearbyEntities(particleLocation, 0.5, 0.5, 0.5).stream()).forEach(entity -> {
                            if (entity != null) {
                                if (entity != player) {
                                    if (entity instanceof LivingEntity) {
                                        LivingEntity livingEntity = (LivingEntity) entity;
                                        if (HellfireStone.damagedEntityCoolDownMove8.containsKey(livingEntity)) {
                                            if (HellfireStone.damagedEntityCoolDownMove8.get(livingEntity) < System.currentTimeMillis()) {
                                                livingEntity.setFireTicks(400);
                                                livingEntity.damage(5);
                                                HellfireStone.damagedEntityCoolDownMove8.put(livingEntity, System.currentTimeMillis() + (10 * 1000));
                                            }
                                        } else {
                                            livingEntity.setFireTicks(400);
                                            livingEntity.damage(5);
                                            HellfireStone.damagedEntityCoolDownMove8.put(livingEntity, System.currentTimeMillis() + (10 * 1000));
                                        }

                                    }
                                }
                            }
                        });
                    }
                    if (radius >= 100) {
                        this.cancel();
                    }
                    radius += 1;
                }
            }.runTaskTimer(StaticVariables.plugin, 60L, 1L);
        };
    }
}




























