package com.lennertsoffers.elementalstones.stones.earthStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class EarthbendingStone extends EarthStone {

    private static void damageLivingEntities(World world, Location location, Player player) {
        for (Entity entity : world.getNearbyEntities(location, 0.5, 3, 0.5)) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                if (livingEntity != player) {
                    livingEntity.setVelocity(new Vector(0, 1, 0));
                    livingEntity.damage(10);
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 140, 2));
                }
            }
        }
    }

    private static void damageLivingEntitiesCollidedWithFallingBlock(Player player, FallingBlock fallingBlock, double amount) {
        List<Entity> nearbyEntities = fallingBlock.getNearbyEntities(1, 1, 1);
        if (!(nearbyEntities.isEmpty())) {
            for (Entity entity : nearbyEntities) {
                if (entity instanceof LivingEntity) {
                    if (entity != player) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        livingEntity.damage(amount);
                    }
                }
            }
        }
    }

    private static int checkAirBlocks(World world, Location location) {
        int amountAdded = 0;
        while (world.getBlockAt(location.getBlockX(), location.getBlockY() + 1, location.getBlockZ()).getType() != Material.AIR && amountAdded < 50) {
            location.add(0, 1, 0);
            amountAdded++;
        }
        while (world.getBlockAt(location).getType() == Material.AIR) {
            location.add(0, -1, 0);
            amountAdded--;
        }
        return amountAdded;
    }

    private static void earthWavePerpendicular(World world, Player player, boolean positive, boolean x) {
        Location location = player.getLocation();
        if (x) {
            if (positive) {
                location.add(1, -1, -1);
            } else {
                location.add(-1, -1, -1);
            }
        } else {
            if (positive) {
                location.add(-1, -1, 1);
            } else {
                location.add(-1, -1, -1);
            }
        }

        new BukkitRunnable() {
            int counter = 0;
            int amountAdded = 0;

            private void spawnFlyingBlocks() {
                amountAdded = checkAirBlocks(world, location);
                world.spawnFallingBlock(location, world.getBlockAt(location).getBlockData()).setVelocity(new Vector(0, 0.3, 0));
                world.getBlockAt(location).setType(Material.AIR);
                damageLivingEntities(world, location, player);
                if (x) {
                    location.add(0, -amountAdded, 1);
                } else {
                    location.add(1, -amountAdded, 0);
                }
            }
            @Override
            public void run() {
                spawnFlyingBlocks();
                spawnFlyingBlocks();
                amountAdded = checkAirBlocks(world, location);
                world.spawnFallingBlock(location, world.getBlockAt(location).getBlockData()).setVelocity(new Vector(0, 0.3, 0));
                world.getBlockAt(location).setType(Material.AIR);
                damageLivingEntities(world, location, player);

                if (x) {
                    if (positive) {
                        location.add(1, 0, -2);
                    } else {
                        location.add(-1, 0, -2);
                    }
                } else {
                    if (positive) {
                        location.add(-2, 0, 1);
                    } else {
                        location.add(-2, 0, -1);
                    }
                }
                location.add(0, -amountAdded, 0);
                counter++;
                if (counter > 24) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 2L);
    }

    private static void earthWaveDiagonal(World world, Player player, boolean positive, boolean x) {
        Location location = player.getLocation();
        if (x) {
            if (positive) {
                location.add(2, -1, 0);
            } else {
                location.add(0, -1, -2);
            }
        } else {
            if (positive) {
                location.add(0, -1, 2);
            } else {
                location.add(2, -1, 0);
            }
        }

        new BukkitRunnable() {
            int counter = 0;
            int amountAdded = 0;

            private void spawnFlyingBlocks() {
                amountAdded = checkAirBlocks(world, location);
                FallingBlock fallingBlock = world.spawnFallingBlock(location, world.getBlockAt(location).getBlockData());
                fallingBlock.setDropItem(false);
                fallingBlock.setVelocity(new Vector(0, 0.3, 0));
                world.getBlockAt(location).setType(Material.AIR);
                damageLivingEntities(world, location, player);
                if (x) {
                    location.add(-1, 0, 1);
                } else {
                    location.add(-1, 0, -1);
                }
            }
            @Override
            public void run() {
                spawnFlyingBlocks();
                if (counter % 2 == 0) {
                    spawnFlyingBlocks();
                }
                amountAdded = checkAirBlocks(world, location);
                FallingBlock fallingBlock = world.spawnFallingBlock(location, world.getBlockAt(location).getBlockData());
                fallingBlock.setDropItem(false);
                fallingBlock.setVelocity(new Vector(0, 0.3, 0));
                damageLivingEntities(world, location, player);
                world.getBlockAt(location).setType(Material.AIR);

                if (x) {
                    if (positive) {
                        location.add(2, 0, -1);
                    } else {
                        location.add(1, 0, -2);
                    }
                } else {
                    if (positive) {
                        location.add(1, 0, 2);
                    } else {
                        location.add(2, 0, 1);
                    }
                }
                counter++;
                if (counter > 24) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 2L);
    }

    private static void spikeWavePerpendicular(Location location, boolean var0, boolean var1, boolean perpendicular, Player player) {

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

                    Location spikeBlockLocation = loc.clone();

                    new BukkitRunnable() {
                        int i;
                        final int maxHeight = rowIndex;

                        @Override
                        public void run() {
                            Block spikeBlock = world.getBlockAt(spikeBlockLocation);
                            if (spikeBlock.getType() == Material.AIR) {

                                Collection<Entity> nearbyEntities = world.getNearbyEntities(spikeBlockLocation, 1, 1, 1);
                                if (!nearbyEntities.isEmpty()) {
                                    for (Entity entity : nearbyEntities) {
                                        if (entity instanceof LivingEntity) {
                                            LivingEntity livingEntity = (LivingEntity) entity;

                                            if (livingEntity != player) {
                                                livingEntity.damage(2, player);
                                                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 70, 2, false, false, true));
                                            }
                                        }
                                    }
                                }

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
    public static Runnable passive(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = player.getLocation();
            Location locationAround = location.clone().add(1, -1, 0);
            World world = player.getWorld();
            Material material = world.getBlockAt(location.add(0, -1, 0)).getType();

            if (material == Material.STONE || material == Material.COBBLESTONE || material == Material.DIRT || material == Material.SAND || material == Material.SANDSTONE || material == Material.ANDESITE || material == Material.DIORITE || material == Material.GRANITE) {
                if (CheckLocationTools.locationAroundClear(locationAround, world)) {
                    Block block = world.getBlockAt(location);
                    FallingBlock fallingBlock = world.spawnFallingBlock(location, block.getBlockData());
                    world.getBlockAt(location).setType(Material.AIR);
                    fallingBlock.setVelocity(new Vector(0, 0.3, 0));
                    player.setVelocity(player.getVelocity().add(new Vector(0, 0.1, 0)));
                    fallingBlock.setDropItem(false);
                    StaticVariables.scheduler.scheduleSyncDelayedTask(StaticVariables.plugin, () -> {
                        fallingBlock.remove();
                        world.getBlockAt(location).setType(block.getType());
                    }, 20L);
                }
            }
        };
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
                System.out.println("section 1");
                spikeWavePerpendicular(location, false, true, true, player);
            } else if (yaw >= 25 && yaw < 65) {
                System.out.println("section 2");
                spikeWavePerpendicular(location, false, true, false, player);
            } else if (yaw >= 65 && yaw < 115) {
                System.out.println("section 3");
                spikeWavePerpendicular(location, true, false, true, player);
            } else if (yaw >= 115 && yaw < 155) {
                System.out.println("section 4");
                spikeWavePerpendicular(location, false, false, false, player);
            } else if (yaw < -155 || yaw > 155) {
                System.out.println("section 5");
                spikeWavePerpendicular(location, false, false, true, player);
            } else if (yaw <= -25 && yaw > -65) {
                System.out.println("section 6");
                spikeWavePerpendicular(location, true, true, false, player);
            } else if (yaw <= -65 && yaw > -115) {
                System.out.println("section 7");
                spikeWavePerpendicular(location, true, true, true, player);
            } else {
                System.out.println("section 8");
                spikeWavePerpendicular(location, true, false, false, player);
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
                    for (Entity entity : world.getNearbyEntities(location, 1.0, 1.0, 1.0)) {
                        if (entity instanceof LivingEntity) {
                            if (entity != player) {
                                entity.setVelocity(new Vector(0, 0.5, 0));
                                ((LivingEntity) entity).damage(10);
                            }
                        }
                    }
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
    // Earth Prison
    // Creates an hard to escape prison at the targeted block
    public static Runnable move6(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = Objects.requireNonNull(player.getTargetBlockExact(20)).getLocation().add(2, 1, -1);
            World world = player.getWorld();
            new BukkitRunnable() {
                int counter = 0;

                @Override
                public void run() {
                    world.getBlockAt(location).setType(Material.STONE);
                    world.getBlockAt(location.add(0, 0, 1)).setType(Material.STONE);
                    world.getBlockAt(location.add(0, 0, 1)).setType(Material.STONE);
                    world.getBlockAt(location.add(-1, 0, 1)).setType(Material.STONE);
                    world.getBlockAt(location.add(-1, 0, 0)).setType(Material.STONE);
                    world.getBlockAt(location.add(-1, 0, 0)).setType(Material.STONE);
                    world.getBlockAt(location.add(-1, 0, -1)).setType(Material.STONE);
                    world.getBlockAt(location.add(0, 0, -1)).setType(Material.STONE);
                    world.getBlockAt(location.add(0, 0, -1)).setType(Material.STONE);
                    world.getBlockAt(location.add(1, 0, -1)).setType(Material.STONE);
                    world.getBlockAt(location.add(1, 0, 0)).setType(Material.STONE);
                    world.getBlockAt(location.add(1, 0, 0)).setType(Material.STONE);
                    location.add(1, 1, 1);
                    counter++;
                    if (!(counter < 3)) {
                        this.cancel();
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }

    // MOVE 7
    // Earth Wave
    // -> Creates an earth wave in the looking direction of the player
    public static Runnable move7(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = player.getLocation();
            float yaw = Math.abs(location.getYaw());
            if ((yaw >= 0 && yaw < 25) || (yaw >= 335 && yaw <= 360)) {
                earthWavePerpendicular(player.getWorld(), player, true, false);
            } else if (yaw >= 25 && yaw < 65) {
                earthWaveDiagonal(player.getWorld(), player, true, false);
            } else if (yaw >= 65 && yaw < 115) {
                earthWavePerpendicular(player.getWorld(), player, false, true);
            } else if (yaw >= 115 && yaw < 155) {
                earthWaveDiagonal(player.getWorld(), player, false, true);
            } else if (yaw >= 155 && yaw < 205) {
                earthWavePerpendicular(player.getWorld(), player, false, false);
            } else if (yaw >= 205 && yaw < 245) {
                earthWaveDiagonal(player.getWorld(), player, false, false);
            } else if (yaw >= 245 && yaw < 295) {
                earthWavePerpendicular(player.getWorld(), player, true, true);
            } else {
                earthWaveDiagonal(player.getWorld(), player, true, true);
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
            if (activePlayer.getMove8Stage() > 0 && activePlayer.getMove8FallingBlocks() == null) {
                activePlayer.setMove8Stage(0);
                activePlayer.setMove8FallingBlocks(null);
            }
            if (activePlayer.getMove8Stage() == 0) {
                World world = player.getWorld();
                Location location = player.getLocation();
                List<FallingBlock> fallingBlocks = new ArrayList<>();
                fallingBlocks.add(world.spawnFallingBlock(location.add(3, -1, -1), location.getBlock().getBlockData()));
                world.getBlockAt(location).setType(Material.AIR);
                fallingBlocks.add(world.spawnFallingBlock(location.add(0, 0, 2), location.getBlock().getBlockData()));
                world.getBlockAt(location).setType(Material.AIR);
                fallingBlocks.add(world.spawnFallingBlock(location.add(-2, 0, 2), location.getBlock().getBlockData()));
                world.getBlockAt(location).setType(Material.AIR);
                fallingBlocks.add(world.spawnFallingBlock(location.add(-2, 0, 0), location.getBlock().getBlockData()));
                world.getBlockAt(location).setType(Material.AIR);
                fallingBlocks.add(world.spawnFallingBlock(location.add(-2, 0, -2), location.getBlock().getBlockData()));
                world.getBlockAt(location).setType(Material.AIR);
                fallingBlocks.add(world.spawnFallingBlock(location.add(0, 0, -2), location.getBlock().getBlockData()));
                world.getBlockAt(location).setType(Material.AIR);
                fallingBlocks.add(world.spawnFallingBlock(location.add(2, 0, -2), location.getBlock().getBlockData()));
                world.getBlockAt(location).setType(Material.AIR);
                fallingBlocks.add(world.spawnFallingBlock(location.add(2, 0, 0), location.getBlock().getBlockData()));
                world.getBlockAt(location).setType(Material.AIR);
                for (FallingBlock fallingBlock : fallingBlocks) {
                    fallingBlock.setDropItem(false);
                    fallingBlock.setVelocity(new Vector(0, 1, 0));
                }
                activePlayer.setMove8FallingBlocks(fallingBlocks);
                activePlayer.increaseMove8Stage();
            } else if (activePlayer.getMove8Stage() == 1) {
                Vector direction = player.getLocation().getDirection();
                for (FallingBlock fallingBlock : activePlayer.getMove8FallingBlocks()) {
                    fallingBlock.setVelocity(new Vector(direction.getX() * 4, direction.getY() * 4, direction.getZ() * 4));
                    new BukkitRunnable() {
                        int tickCount = 0;

                        @Override
                        public void run() {
                            damageLivingEntitiesCollidedWithFallingBlock(player, fallingBlock, 7.5);
                            tickCount++;
                            if (tickCount >= 100) {
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
                }
                activePlayer.increaseMove8Stage();
            } else if (activePlayer.getMove8Stage() == 2) {
                Location playerLocation = player.getLocation();
                double playerX = playerLocation.getX();
                double playerY = playerLocation.getY();
                double playerZ = playerLocation.getZ();
                for (FallingBlock fallingBlock : activePlayer.getMove8FallingBlocks()) {
                    Location fallingBlockLocation = fallingBlock.getLocation();
                    double fallingBlockX = fallingBlockLocation.getX();
                    double fallingBlockY = fallingBlockLocation.getY();
                    double fallingBlockZ = fallingBlockLocation.getZ();
                    double length = MathTools.lengthOfVector(playerX, fallingBlockX, playerY, fallingBlockY, playerZ, fallingBlockZ);
                    fallingBlock.setVelocity(new Vector(-(fallingBlockX - playerX) / length * 5, -(fallingBlockY - playerY) / length * 2, -(fallingBlockZ - playerZ) / length * 5));
                }
                activePlayer.setMove8FallingBlocks(null);
            } else {
                activePlayer.setMove8Stage(0);
            }
        };
    }
}
