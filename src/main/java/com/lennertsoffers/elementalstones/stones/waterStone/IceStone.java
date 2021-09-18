package com.lennertsoffers.elementalstones.stones.waterStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import net.minecraft.server.PacketPlayOutWorldParticles;
import net.minecraft.server.PlayerConnection;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

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


    // MOVE 6


    // MOVE 7


    // MOVE 8

}