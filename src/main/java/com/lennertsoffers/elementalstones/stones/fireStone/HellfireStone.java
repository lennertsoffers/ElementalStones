package com.lennertsoffers.elementalstones.stones.fireStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HellfireStone extends FireStone {

    private static final Map<LivingEntity, Long> damagedEntityCoolDown = new HashMap<>();

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
                        if (HellfireStone.damagedEntityCoolDown.containsKey(livingEntity)) {
                            if (HellfireStone.damagedEntityCoolDown.get(livingEntity) < System.currentTimeMillis()) {
                                HellfireStone.damagedEntityCoolDown.replace(livingEntity, System.currentTimeMillis() + 1000);
                                livingEntity.damage(4);
                                entity.setFireTicks(entity.getFireTicks() + 40);
                            }
                        } else {
                            HellfireStone.damagedEntityCoolDown.put(livingEntity, System.currentTimeMillis() + 1000);
                            livingEntity.damage(4);
                            entity.setFireTicks(entity.getFireTicks() + 40);
                        }
                    }
                }
            }
        }
    }

    // PASSIVE


    // MOVE 4
    // Fire Track
    // -> You leave a fire track behind you
    // Activation
    public static void move4(ActivePlayer activePlayer) {
        activePlayer.setHellfireStoneMove4TimeRemaining();
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
    public static void move5(ActivePlayer activePlayer) {
        if (activePlayer.getFloatingFire() != null) {
            activePlayer.getFloatingFire().cancel();
            Player player = activePlayer.getPlayer();
            new BukkitRunnable() {
                int counter = 0;
                @Override
                public void run() {
                    Location location = player.getLocation();
                    Vector direction = location.getDirection();
                    Location fireBallLocation = location.add(direction.getX() * 2.5, direction.getY() * 2.5 + 0.85, direction.getZ() * 2.5).add(0, 1.5, 0);
                    player.getWorld().spawnEntity(fireBallLocation, EntityType.FIREBALL).setVelocity(direction);
                    if (counter >= 10) {
                        this.cancel();
                    }
                    counter++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        }
    }

    // MOVE 6


    // MOVE 7
    // Fire Blast
    // Shoot an array of destructing fire out of your hands
    public static void move7(ActivePlayer activePlayer) {
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
    }

    // MOVE 8
}
