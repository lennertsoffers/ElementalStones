package com.lennertsoffers.elementalstones.stones.earthStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FlyingPlatform;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import sun.awt.windows.ThemeReader;

import java.util.*;

public class EarthbendingStone extends EarthStone {

    private static void earthWaveNew(Location location, boolean var0, boolean var1, boolean perpendicular, ActivePlayer activePlayer) {

        Player player = activePlayer.getPlayer();

        World world = location.getWorld();
        if (world == null) {
            return;
        }

        int startX;
        int startZ;
        Vector additionRow;
        Vector additionRowBlock;
        Vector additionRowStage1;
        Vector additionRowStage2;

        if (perpendicular) {
            if (var0) {
                if (var1) {
                    startX = 2;
                    additionRow = new Vector(1, 0, 0);
                } else {
                    startX = -2;
                    additionRow = new Vector(-1, 0, 0);
                }
                startZ = -1;
                additionRowBlock = new Vector(0, 0, 1);
            } else {
                if (var1) {
                    startZ = 2;
                    additionRow = new Vector(0, 0, 1);
                } else {
                    startZ = -2;
                    additionRow = new Vector(0, 0, -1);
                }
                startX = -1;
                additionRowBlock = new Vector(1, 0, 0);
            }

            Location rowLocation = location.clone().add(startX, 0, startZ);
            Vector velocity = new Vector(0, 0.3, 0);

            new BukkitRunnable() {
                int lengthOfWave = 0;

                @Override
                public void run() {
                    Location blockOnRowLocation = rowLocation.clone();

                    for (int i = 0; i < 3; i++) {
                        Location launchBlockLocation = CheckLocationTools.getClosestAirBlockLocation(blockOnRowLocation);

                        if (launchBlockLocation != null) {
                            launchBlockLocation.add(0, -1, 0);
                            spawnFallingBlock(launchBlockLocation, world, velocity, player);
                        }

                        blockOnRowLocation.add(additionRowBlock);
                    }

                    rowLocation.add(additionRow);

                    lengthOfWave++;
                    if (lengthOfWave >= 50) {
                        this.cancel();
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else {
            if (var0) {
                if (var1) {
                    startX = 3;
                    startZ = 1;
                    additionRowStage1 = new Vector(0, 0, 1);
                    additionRowStage2 = new Vector(1, 0, 0);
                    additionRowBlock = new Vector(-1, 0, 1);
                } else {
                    startX = -3;
                    startZ = -1;
                    additionRowStage1 = new Vector(0, 0, -1);
                    additionRowStage2 = new Vector(-1, 0, 0);
                    additionRowBlock = new Vector(1, 0, -1);
                }
            } else {
                if (var1) {
                    startX = 3;
                    startZ = -1;
                    additionRowStage1 = new Vector(0, 0, -1);
                    additionRowStage2 = new Vector(1, 0, 0);
                    additionRowBlock = new Vector(-1, 0, -1);
                } else {
                    startX = -3;
                    startZ = 1;
                    additionRowStage1 = new Vector(0, 0, 1);
                    additionRowStage2 = new Vector(-1, 0, 0);
                    additionRowBlock = new Vector(1, 0, 1);
                }
            }

            Location rowLocation = location.clone().add(startX, 0, startZ);
            Vector velocity = new Vector(0, 0.3, 0);

            new BukkitRunnable() {
                int lengthOfWave = 0;
                int blocksToPlace = 3;

                @Override
                public void run() {
                    Location blockOnRowLocation = rowLocation.clone();

                    for (int i = 0; i < blocksToPlace; i++) {
                        Location launchBlockLocation = CheckLocationTools.getClosestAirBlockLocation(blockOnRowLocation);

                        if (launchBlockLocation != null) {
                            launchBlockLocation.add(0, -1, 0);
                            spawnFallingBlock(launchBlockLocation, world, velocity, player);
                        }

                        blockOnRowLocation.add(additionRowBlock);
                    }


                    if (blocksToPlace == 3) {
                        rowLocation.add(additionRowStage1);
                        blocksToPlace = 2;
                    } else {
                        rowLocation.add(additionRowStage2);
                        blocksToPlace = 3;
                    }

                    lengthOfWave++;
                    if (lengthOfWave >= 50) {
                        this.cancel();
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        }
    }

    private static void spawnFallingBlock(Location l, World world, Vector velocity, Player player) {
        Location location = new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ());
        Block launchBlock = world.getBlockAt(location);
        FallingBlock fallingBlock = world.spawnFallingBlock(location.clone().add(0.5, 0, 0.5), launchBlock.getBlockData());
        fallingBlock.setDropItem(false);
        fallingBlock.setVelocity(velocity);
        launchBlock.setType(Material.AIR);

        Vector direction = new Vector(0, 1, 0);
        List<PotionEffect> potionEffects = new ArrayList<>();
        potionEffects.add(new PotionEffect(PotionEffectType.SLOW, 100, 1, true, true, true));

        NearbyEntityTools.damageNearbyEntities(player, location, 0, 1.5, 1.5, 1.5, direction ,potionEffects);
    }

    private static void spikeWave(Location location, boolean var0, boolean var1, boolean perpendicular, Player player) {

        World world = location.getWorld();
        if (world == null) {
            return;
        }

        // Determines the shape of the spikes of the arguments given
        int startX;
        int startZ;

        if (perpendicular) {
            if (var0) {
                startX = 2;
                startZ = 0;
            } else {
                startX = 0;
                startZ = 2;
            }

            if (!var1) {
                startX *= -1;
                startZ *= -1;
            }
        } else {
            if (var0) {
                if (var1) {
                    startX = 2;
                    startZ = 2;
                } else {
                    startX = 2;
                    startZ = -2;
                }
            } else {
                if (var1) {
                    startX = -2;
                    startZ = 2;
                } else {
                    startX = -2;
                    startZ = -2;
                }
            }
        }

        // Creates a list with all the base locations for the spikes
        List<List<Location>> spikeRows = new ArrayList<>();
        Location startLocation = location.add(startX, 0, startZ);

        int widthParam = 3;

        for (int j = 1; j < 7; j++) {
            List<Location> row = new ArrayList<>();
            int width = widthParam / 2;

            Vector startLocationAdditionVector;
            Vector rowLocationAdditionVector;
            if (perpendicular) {
                if (var0) {
                    if (var1) {
                        startLocationAdditionVector = new Vector(j, 0, -width);
                    } else {
                        startLocationAdditionVector = new Vector(-j, 0, -width);
                    }
                    rowLocationAdditionVector = new Vector(0, 0, 1);
                } else {
                    if (var1) {
                        startLocationAdditionVector = new Vector(-width, 0, j);
                    } else {
                        startLocationAdditionVector = new Vector(-width, 0, -j);
                    }
                    rowLocationAdditionVector = new Vector(1, 0, 0);
                }
            } else {
                if (var0) {
                    if (var1) {
                        rowLocationAdditionVector = new Vector(-1, 0, 1);
                    } else {
                        rowLocationAdditionVector = new Vector(-1, 0, -1);
                    }
                    startLocationAdditionVector = new Vector(j, 0, 0);
                } else {
                    if (var1) {
                        rowLocationAdditionVector = new Vector(1, 0, 1);
                    } else {
                        rowLocationAdditionVector = new Vector(1, 0, -1);
                    }
                    startLocationAdditionVector = new Vector(-j, 0, 0);
                }
            }

            Location rowLocation = startLocation.clone().add(startLocationAdditionVector);
            for (int i = 0; i < widthParam; i++) {
                row.add(CheckLocationTools.getClosestAirBlockLocation(rowLocation.clone()));
                rowLocation.add(rowLocationAdditionVector);
            }

            spikeRows.add(row);

            if (j % 2 == 0) {
                widthParam += 2;
            }
        }

        // Placement of spikes
        new BukkitRunnable() {
            int rowIndex = 0;

            @Override
            public void run() {

                List<Location> spikeRow = spikeRows.get(rowIndex);

                for (Location loc : spikeRow) {
                    if (loc != null) {
                        Location spikeBlockLocation = loc.clone();

                        new BukkitRunnable() {
                            int i;
                            final int maxHeight = rowIndex;

                            @Override
                            public void run() {
                                Block spikeBlock = world.getBlockAt(spikeBlockLocation);
                                if (spikeBlock.getType() == Material.AIR) {

                                    List<PotionEffect> potionEffects = new ArrayList<>();
                                    potionEffects.add(new PotionEffect(PotionEffectType.SLOW_DIGGING, 70, 2, false, false, true));
                                    NearbyEntityTools.damageNearbyEntities(player, spikeBlockLocation, 5, 1, 1, 1, potionEffects);

                                    spikeBlock.setType(Material.POINTED_DRIPSTONE);
                                    spikeBlockLocation.add(0, 1, 0);
                                } else {
                                    this.cancel();
                                }

                                if (i >= maxHeight) {
                                    this.cancel();
                                }

                                i++;
                            }
                        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
                    }
                }

                rowIndex++;

                if (rowIndex == spikeRows.size()) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

        // Removal of spikes
        new BukkitRunnable() {
            int iterations = 5;

            @Override
            public void run() {
                for (int i = iterations; i >= 0; i--) {
                    List<Location> row = spikeRows.get(i);

                    for (Location spikeLocation : row) {
                        Block block = world.getBlockAt(spikeLocation.clone().add(0, i, 0));

                        if (block.getType() == Material.POINTED_DRIPSTONE) {
                            block.setType(Material.AIR);
                        }
                    }
                }
                spikeRows.remove(0);

                if (iterations == 0) {
                    this.cancel();
                }

                iterations--;
            }
        }.runTaskTimer(StaticVariables.plugin, 60L, 3L);
    }


    // PASSIVE
    // Joinker
    public static void passive(ActivePlayer activePlayer, EntityDamageEvent event) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        float fallDistance = player.getFallDistance();
        Vector velocity = new Vector(0, 0.2, 0);
        System.out.println(player.getFallDistance());

        event.setCancelled(true);

        new BukkitRunnable() {
            int range = 2;

            @Override
            public void run() {

                List<Block> ring = new ArrayList<>();
                for (int i = 0; i < 360; i+= 1) {
                    Location locationOnCircle = CheckLocationTools.getClosestAirBlockLocation(MathTools.locationOnCircle(player.getLocation(), range, i, world));

                    if (locationOnCircle != null) {
                        locationOnCircle.add(0, -1, 0);

                        Block block = world.getBlockAt(locationOnCircle);

                        if (ring.stream().noneMatch(b -> b.getLocation().getBlockX() == block.getLocation().getBlockX() && b.getLocation().getBlockZ() == block.getLocation().getBlockZ())) {
                            ring.add(block);

                            FallingBlock fallingBlock = world.spawnFallingBlock(block.getLocation().add(0.5, 0, 0.5), block.getBlockData());
                            fallingBlock.setDropItem(true);
                            block.setType(Material.AIR);

                            fallingBlock.setVelocity(velocity);
                        }
                    }
                }

                range++;
                if (range > fallDistance) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }


    // MOVE 4
    // Earth Spikes
    // Summons an array of spikes out of the ground in the players looking direction
    // Entities hurt get mining fatigue for a brief moment
    public static Runnable move4(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = player.getLocation();
            Vector direction = location.getDirection();
            direction.setY(0);

            float yaw = location.getYaw();
            if (yaw > -25 && yaw < 25) {
                spikeWave(location, false, true, true, player);
            } else if (yaw >= 25 && yaw < 65) {
                spikeWave(location, false, true, false, player);
            } else if (yaw >= 65 && yaw < 115) {
                spikeWave(location, true, false, true, player);
            } else if (yaw >= 115 && yaw < 155) {
                spikeWave(location, false, false, false, player);
            } else if (yaw < -155 || yaw > 155) {
                spikeWave(location, false, false, true, player);
            } else if (yaw <= -25 && yaw > -65) {
                spikeWave(location, true, true, false, player);
            } else if (yaw <= -65 && yaw > -115) {
                spikeWave(location, true, true, true, player);
            } else {
                spikeWave(location, true, false, false, player);
            }
        };
    }

    // MOVE 5
    // Stomp:
    // -> Create an underground shockwave that damages entities along its way
    public static Runnable move5(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            Location location = player.getLocation();
            Vector direction = location.getDirection();
            direction.setX(direction.getX());
            direction.setY(0);
            direction.setZ(direction.getZ());
            new BukkitRunnable() {
                int counter = 0;

                @Override
                public void run() {
                    location.add(direction);
                    int amountAdded = 0;
                    while (world.getBlockAt(location).getType() != Material.AIR && amountAdded < 50) {
                        location.add(0, 1, 0);
                        amountAdded++;
                    }
                    while (world.getBlockAt(location.getBlockX(), location.getBlockY() - 1, location.getBlockZ()).getType() == Material.AIR) {
                        location.add(0, -1, 0);
                        amountAdded--;
                    }
                    world.spawnParticle(Particle.SMOKE_LARGE, location, 0, 0, -0.5, 0);

                    NearbyEntityTools.damageNearbyEntities(player, location, 7, 1, 1, 1, new Vector(0, 0.5, 0));

                    location.add(0, -amountAdded, 0);
                    counter++;
                    if (counter >= 50) {
                        this.cancel();
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }

    // MOVE 6
    // Rock Throw
    // Select a set of blocks by doing this move on them
    // Shoot the selected blocks in your looking direction by crouching and executing this move
    public static Runnable move6(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();

            // Select blocks to shoot
            if (!player.isSneaking()) {
                Block block = player.getTargetBlockExact(80, FluidCollisionMode.NEVER);

                if (block != null && block.getType().isSolid()) {
                    FallingBlock fallingBlock = world.spawnFallingBlock(block.getLocation().add(0.5, 0, 0.5), block.getBlockData());

                    Vector velocity = new Vector(0, 0.8, 0);
                    if (activePlayer.isMove8active()) {
                        velocity.multiply(2);
                    }
                    fallingBlock.setVelocity(velocity);
                    fallingBlock.setDropItem(false);

                    activePlayer.addLocationMaterialMapping(block.getLocation(), block.getType());
                    block.setType(Material.AIR);

                    activePlayer.getMove6FallingBlocks().add(fallingBlock);
                }
            }

            // Shoot selected blocks in looking direction
            else {
                Vector direction = player.getLocation().getDirection();
                for (FallingBlock fallingBlock : activePlayer.getMove6FallingBlocks()) {
                    fallingBlock.setVelocity(direction.clone().multiply(3));
                    activePlayer.getMove6LaunchedFallingBlocks().add(fallingBlock);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!fallingBlock.isDead()) {
                                NearbyEntityTools.damageNearbyEntities(player, fallingBlock.getLocation(), 4, 1.5, 1.5, 1.5, direction);
                            } else {
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
                }

                activePlayer.getMove6FallingBlocks().clear();
            }
        };
    }

    // MOVE 7
    // Earth Wave
    // -> Creates an earth wave in the looking direction of the player
    public static Runnable move7(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = player.getLocation();
            float yaw = location.getYaw();
            if (yaw > -25 && yaw < 25) {
                earthWaveNew(location, false, true, true, activePlayer);
            } else if (yaw >= 25 && yaw < 65) {
                earthWaveNew(location, false, false, false, activePlayer);
            } else if (yaw >= 65 && yaw < 115) {
                earthWaveNew(location, true, false, true, activePlayer);
            } else if (yaw >= 115 && yaw < 155) {
                earthWaveNew(location, true, false, false, activePlayer);
            } else if (yaw < -155 || yaw > 155) {
                earthWaveNew(location, false, false, true, activePlayer);
            } else if (yaw <= -25 && yaw > -65) {
                earthWaveNew(location, true, true, false, activePlayer);
            } else if (yaw <= -65 && yaw > -115) {
                earthWaveNew(location, true, true, true, activePlayer);
            } else {
                earthWaveNew(location, false, true, false, activePlayer);
            }
        };
    }


    // MOVE 8
    // Reverse Turret
    // -> Selects 8 blocks around player and shoots them up
    // -> Second time you activate this ability the stones fly in the looking direction of the player
    // -> (On third activation you let the stones come back to the player)
    public static Runnable move8(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();

            // Create platform
            if (!activePlayer.isMove8active()) {
                if (!player.isInWater() || world.getBlockAt(player.getLocation()).getType() == Material.LAVA) {

                    Vector startVelocity = new Vector(0, 1, 0);
                    player.setVelocity(startVelocity.multiply(2));

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Location location = player.getLocation().add(0, -1, 0);
                            player.setAllowFlight(true);
                            List<FallingBlock> platform = new ArrayList<>();

                            List<Location> launchLocations = new ArrayList<>();
                            for (int i = 0; i < 46; i++) {
                                Location locationInCircle = new Location(world,
                                        location.getBlockX() + StaticVariables.random.nextInt(40) - 20,
                                        location.getBlockY(),
                                        location.getBlockZ() + StaticVariables.random.nextInt(40) - 20
                                );
                                Location closestLocation = CheckLocationTools.getClosestAirBlockLocation(locationInCircle);
                                if (closestLocation == null) {
                                    closestLocation = locationInCircle;
                                }

                                launchLocations.add(closestLocation.add(0, -1, 0));
                            }

                            startVelocity.multiply(0.8);
                            for (Location launchLocation : launchLocations) {
                                Block block = world.getBlockAt(launchLocation);
                                Material material = block.getType();

                                activePlayer.addLocationMaterialMapping(launchLocation, material);

                                if (!material.isSolid()) {
                                    material = Material.STONE;
                                }

                                FallingBlock fallingBlock = world.spawnFallingBlock(launchLocation.clone().add(0.5, 0, 0.5), material.createBlockData());
                                fallingBlock.setDropItem(false);
                                fallingBlock.setVelocity(startVelocity);
                                block.setType(Material.AIR);
                                platform.add(fallingBlock);
                            }

                            activePlayer.setMove8FallingBlocks(platform);

                            // Make player fly
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    player.setAllowFlight(true);
                                    player.setFlying(true);
                                }
                            }.runTaskLater(StaticVariables.plugin, 10L);


                            // Platform of earth beneath the player
                            activePlayer.setMovesEnabled(false);
                            new FlyingPlatform(true, 60, 0.1, activePlayer, player, platform).runTaskTimer(StaticVariables.plugin, 20L, 1L);
                            new FlyingPlatform(activePlayer, player, platform).runTaskTimer(StaticVariables.plugin, 80L, 1L);
                        }
                    }.runTaskLater(StaticVariables.plugin, 10L);
                }
            }

            // Launch platform down
            else {
                move8launch(activePlayer);
            }
        };
    }

    public static void move8launch(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();

        Vector platformFallVelocity = new Vector(0, -1, 0);
        for (FallingBlock fallingBlock : activePlayer.getMove8FallingBlocks()) {
            fallingBlock.setVelocity(platformFallVelocity);
            player.setVelocity(platformFallVelocity);
        }
        move8End(activePlayer);


        Location location = activePlayer.getPlayer().getLocation();
        earthWaveNew(location, true, true, true, activePlayer);
        earthWaveNew(location, true, false, true, activePlayer);
        earthWaveNew(location, false, true, true, activePlayer);
        earthWaveNew(location, false, false, true, activePlayer);

        new BukkitRunnable() {
            @Override
            public void run() {
                earthWaveNew(location, true, true, false, activePlayer);
                earthWaveNew(location, true, false, false, activePlayer);
                earthWaveNew(location, false, true, false, activePlayer);
                earthWaveNew(location, false, false, false, activePlayer);
            }
        }.runTaskLater(StaticVariables.plugin, 13L);

        new BukkitRunnable() {
            @Override
            public void run() {
                activePlayer.setMove8active(false);
            }
        }.runTaskLater(StaticVariables.plugin, 40L);
    }

    public static void move8End(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        player.setFlying(false);
        player.setAllowFlight(false);
        activePlayer.setMovesEnabled(true);
    }
}

// TODO - Fix passive
