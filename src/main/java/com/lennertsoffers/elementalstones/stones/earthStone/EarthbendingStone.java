package com.lennertsoffers.elementalstones.stones.earthStone;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Location;
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
                                double health = livingEntity.getHealth() - 10;
                                if (health < 0) {
                                    health = 0;
                                }
                                livingEntity.setHealth(health);
                            }
                        }
                    }
                }
                tickCount++;
                System.out.println(tickCount);
                if (tickCount >= 50) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }

    // MOVE 5
    public static void move5(Player player, FallingBlock move4Block) {
        if (move4Block == null) {
            return;
        }
    }

    // MOVE 6
    public static void move6(Player player, FallingBlock move4Block) {
        Location location = move4Block.getLocation();

    }

    // MOVE 7


    // MOVE 8
}
