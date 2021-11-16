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
    // Rock Throw
    // -> Following up to flying rock
    // -> Shoots the flying rock in the looking direction of the player
    public static Runnable move4(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            FallingBlock move4Block = activePlayer.getFallingBlock();
            if (move4Block == null) {
                return;
            }
            Location playerLocation = player.getLocation();
            move4Block.setVelocity(new Vector(playerLocation.getDirection().getX() * 8, 0, playerLocation.getDirection().getZ() * 8));
            new BukkitRunnable() {
                int tickCount = 0;

                @Override
                public void run() {
                    damageLivingEntitiesCollidedWithFallingBlock(player, move4Block, 10.0);
                    tickCount++;
                    if (tickCount >= 100) {
                        this.cancel();
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
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
