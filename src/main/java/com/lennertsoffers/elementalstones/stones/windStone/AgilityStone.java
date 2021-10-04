package com.lennertsoffers.elementalstones.stones.windStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AgilityStone extends WindStone {

    private static void damageOnDash(ActivePlayer activePlayer) {
        ArrayList<LivingEntity> damagedEntities = new ArrayList<>();
        Player player = activePlayer.getPlayer();
        new BukkitRunnable() {
            int amountOfTicks = 0;
            @Override
            public void run() {
                if (!player.getWorld().getNearbyEntities(player.getLocation(), 0.5, 0.5, 0.5).isEmpty()) {
                    for (Entity entity : player.getWorld().getNearbyEntities(player.getLocation(), 0.5, 0.5, 0.5)) {
                        if (entity != null) {
                            if (entity instanceof LivingEntity) {
                                LivingEntity livingEntity = (LivingEntity) entity;
                                if (livingEntity != player) {
                                    if (!damagedEntities.contains(livingEntity)) {
                                        damagedEntities.add(livingEntity);
                                        if (activePlayer.isInAirBoost()) {
                                            livingEntity.damage(6);
                                            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 1);
                                        } else {
                                            livingEntity.damage(2);
                                            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (amountOfTicks > 10) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }

    // PASSIVE
    // Double Jump
    // -> The player can jump a second time after jumping in the air with space bar
    public static void passive(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());
        if (Objects.requireNonNull(activePlayer).canDoubleJump()) {
            Vector launchDirection;
            if (Math.abs(activePlayer.getMovingDirection().getX()) < 0.1 && Math.abs(activePlayer.getMovingDirection().getZ()) < 0.1) {
                System.out.println(activePlayer.getMovingDirection());
                launchDirection = player.getLocation().getDirection();
            } else {
                launchDirection = activePlayer.getMovingDirection();
            }
            player.setVelocity(player.getVelocity().add(launchDirection.setY(1)));
            activePlayer.disableDoubleJump();
        }
    }

    // No fall damage
    // -> The player cannot get fall damage

    // MOVE 4
    // Forward Dash
    // -> Player dashes forwards and damages entities along the way
    public static void move4(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        damageOnDash(activePlayer);
        int multiplier = 2;
        if (activePlayer.isInAirBoost()) {
            multiplier = 4;
        }
        player.setVelocity(player.getLocation().getDirection().multiply(multiplier).setY(0.1));
    }

    // MOVE 5
    // Backward Dash
    // -> Player dashes backwards and damages entities along the way
    public static void move5(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        damageOnDash(activePlayer);
        int multiplier = 2;
        if (activePlayer.isInAirBoost()) {
            multiplier = 4;
        }
        player.setVelocity(player.getLocation().getDirection().multiply(-multiplier).setY(0.1));
    }

    // MOVE 6
    // Smoke Ball
    // -> Throw a smoke ball in the looking direction
    // -> While the ball is being threw, control it by changing your looking direction
    // -> Activate ability again to create the smoke screen or wait till the ball collides with a block or entity
    public static void move6(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        Location startingLocation = player.getLocation().add(player.getLocation().getDirection()).add(0, 1, 0);
        final Location[] impactLocation = {player.getLocation()};
        BukkitRunnable smoke = new BukkitRunnable() {
            int amountOfTicks = 0;
            @Override
            public void run() {
                for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
                    double radius = Math.sin(i) * 3;
                    double y = Math.cos(i) * 3;
                    for (double a = 0; a < Math.PI * 2; a+= Math.PI / 10) {
                        double x = Math.cos(a) * radius;
                        double z = Math.sin(a) * radius;
                        Location particleLocation = impactLocation[0].clone().add(0, 2, 0).add(x, y, z);
                        player.getWorld().spawnParticle(Particle.REDSTONE, particleLocation.add(StaticVariables.random.nextGaussian() / 4, StaticVariables.random.nextGaussian() / 4, StaticVariables.random.nextGaussian() / 4), 0, 0, 0, 0, 0, new Particle.DustOptions(Color.WHITE, 1000));
                    }
                }
                if (amountOfTicks > 100) {
                    this.cancel();
                }
                amountOfTicks += 1;
            }
        };

        new BukkitRunnable() {
            int amountOfTicks = 0;
            final Location currentLocation = startingLocation;
            @Override
            public void run() {
                Vector playerDirection = player.getLocation().getDirection();
                Objects.requireNonNull(currentLocation.getWorld()).spawnParticle(Particle.CLOUD, startingLocation, 0, playerDirection.getX(), playerDirection.getY(), playerDirection.getZ());

                if (currentLocation.getBlock().getType().isSolid()) {
                    impactLocation[0] = currentLocation;
                    this.cancel();
                    smoke.runTaskTimer(StaticVariables.plugin, 0, 3L);
                } else if (!player.getWorld().getNearbyEntities(currentLocation, 0.5, 0.5, 0.5).isEmpty()) {
                    for (Entity entity : player.getWorld().getNearbyEntities(currentLocation, 0.5, 0.5, 0.5)) {
                        if (entity != null) {
                            impactLocation[0] = currentLocation;
                            this.cancel();
                            smoke.runTaskTimer(StaticVariables.plugin, 0, 3L);
                        }
                    }
                } else if (amountOfTicks > 30) {
                    impactLocation[0] = currentLocation;
                    this.cancel();
                    smoke.runTaskTimer(StaticVariables.plugin, 0, 3L);
                }

                amountOfTicks++;
                currentLocation.add(playerDirection);
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }

    // MOVE 7
    // Charge Jump
    // -> Charges until next activation
    // -> The longer you charge, the higher you jump
    // -> Adds slowness when charging
    public static void move7(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        if ((int) activePlayer.getCharge() == -1) {
            activePlayer.setChargingStart();
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000, 3, true, false, false));
            activePlayer.setMove7LaunchState(1);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (activePlayer.getMove7LaunchState() == 1) {
                        activePlayer.setMove7LaunchState(0);
                        move7(activePlayer);
                    }
                }
            }.runTaskLater(StaticVariables.plugin, 90);
        } else {
            int nominator = 2000;
            if (activePlayer.isInAirBoost()) {
                nominator = 1000;
            }
            double velocityY = activePlayer.getCharge() / nominator;
            activePlayer.resetCharge();
            player.removePotionEffect(PotionEffectType.SLOW);
            activePlayer.setMove7LaunchState(2);
            player.setVelocity(new Vector(0, velocityY, 0));
        }
    }

    // MOVE 8
    // Air Boost
    // -> Makes the player run faster
    // -> Makes the player jump higher
    // -> Dashes do more damage
    // -> Charge jump charges faster
    // -> Cooldowns shorter
    public static void move8(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        activePlayer.activateAirBoost();
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 3, false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1200, 1, false, false, false));
    }
}





















