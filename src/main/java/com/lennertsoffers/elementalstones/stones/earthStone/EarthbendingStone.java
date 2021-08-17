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

public class EarthbendingStone {

    // MOVE 4
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
                location.add(0, -amountAdded, 0);
                counter++;
                if (counter >= 50) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

    }

    // MOVE 6
    public static void move6(Player player, FallingBlock move4Block) {
        Location location = move4Block.getLocation();

    }

    // MOVE 7
    // instant dirt house

    // MOVE 8

}
