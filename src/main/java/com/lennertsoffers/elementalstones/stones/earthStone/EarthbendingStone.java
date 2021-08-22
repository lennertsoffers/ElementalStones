package com.lennertsoffers.elementalstones.stones.earthStone;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Objects;

public class EarthbendingStone {

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
            private void spawnFlyingBlocks() {
                world.spawnFallingBlock(location, world.getBlockAt(location).getBlockData()).setVelocity(new Vector(0, 0.3, 0));
                world.getBlockAt(location).setType(Material.AIR);

                if (x) {
                    location.add(0, 0, 1);
                } else {
                    location.add(1, 0, 0);
                }
            }
            @Override
            public void run() {
                spawnFlyingBlocks();
                spawnFlyingBlocks();
                world.spawnFallingBlock(location, world.getBlockAt(location).getBlockData()).setVelocity(new Vector(0, 0.3, 0));
                world.getBlockAt(location).setType(Material.AIR);

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
                counter++;
                if (counter > 15) {
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
            private void spawnFlyingBlocks() {
                world.spawnFallingBlock(location, world.getBlockAt(location).getBlockData()).setVelocity(new Vector(0, 0.3, 0));
                world.getBlockAt(location).setType(Material.AIR);
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
                world.spawnFallingBlock(location, world.getBlockAt(location).getBlockData()).setVelocity(new Vector(0, 0.3, 0));
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
                if (counter > 15) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 2L);
    }


    // MOVE 4
    // Rock Sniper
    // -> Following up to flying rock
    // -> Shoots the flying rock in the looking direction of the player
    public static void move4(Player player, FallingBlock move4Block) {
        if (move4Block == null) {
            return;
        }
        Location playerLocation = player.getLocation();
        move4Block.setVelocity(new Vector(playerLocation.getDirection().getX() * 5, 0, playerLocation.getDirection().getZ() * 5));
        new BukkitRunnable() {
            int tickCount = 0;
            @Override
            public void run() {
                List<Entity> nearbyEntities = move4Block.getNearbyEntities(1, 1, 1);
                if (!(nearbyEntities.isEmpty())) {
                    for (Entity entity : nearbyEntities) {
                        if (entity instanceof LivingEntity) {
                            if (entity != player) {
                                LivingEntity livingEntity = (LivingEntity) entity;
                                livingEntity.damage(10.0);
                            }
                        }
                    }
                }
                tickCount++;
                if (tickCount >= 50) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }

    // MOVE 5
    // Stomp:
    // -> Create an underground shockwave that damages entities along its way
    public static void move5(Player player) {
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
                location.add(0, - amountAdded, 0);
                counter++;
                if (counter >= 50) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

    }

    // MOVE 6
    // Earth Prison
    // Creates an hard to escape prison at the targeted block
    public static void move6(Player player) {
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
    }

    // MOVE 7
    // Earth Wave
    // -> Creates an earth wave in the looking direction of the player
    public static void move7(Player player) {
        Location location = player.getLocation();
        float yaw = Math.abs(location.getYaw());
        if ((yaw >= 0 && yaw < 25) || (yaw >= 335 && yaw <= 360)) {
            earthWavePerpendicular(player.getWorld(), player, true, false);
            System.out.println("P-TF");
        } else if (yaw >= 25 && yaw < 65) {
            earthWaveDiagonal(player.getWorld(), player, true, false);
            System.out.println("D-TF");
        } else if (yaw >= 65 && yaw < 115) {
            earthWavePerpendicular(player.getWorld(), player, false, true);
            System.out.println("P-FT");
        } else if (yaw >= 115 && yaw < 155) {
            earthWaveDiagonal(player.getWorld(), player, false, true);
            System.out.println("D-FT");
        } else if (yaw >= 155 && yaw < 205) {
            earthWavePerpendicular(player.getWorld(), player, false, false);
            System.out.println("P-FF");
        } else if (yaw >= 205 && yaw < 245) {
            earthWaveDiagonal(player.getWorld(), player, false, false);
            System.out.println("D-FF");
        } else if (yaw >= 245 && yaw < 295) {
            earthWavePerpendicular(player.getWorld(), player, true, true);
            System.out.println("P-TT");
        } else {
            earthWaveDiagonal(player.getWorld(), player, true, true);
            System.out.println("D-TT");
            System.out.println(yaw);
        }
    }


    // MOVE 8
    // Reverse Machine Gun
    // -> Selects 8 blocks around player and shoots them up
    // -> Second time you activate this ability the stones fly in the looking direction of the player
    // -> (On third activation you let the stones come back to the player)
}
