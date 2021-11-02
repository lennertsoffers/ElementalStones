package com.lennertsoffers.elementalstones.stones.windStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AirbendingStone {

    // PASSIVE


    // MOVE 4
    // Air Slash
    // -> A high speed high damage air slash
    public static void move4(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        Location location = player.getLocation();
        Vector direction = location.getDirection();
        location.add(0, 2, 0);

        new BukkitRunnable() {
            int amountOfTicks = 0;
            @Override
            public void run() {

                Location runnableLocation = location.clone();

                for (int i = 70; i >= (70 - (amountOfTicks * 46.66)); i--) {
                    Location particleLocation = runnableLocation.clone().add(direction.clone().rotateAroundY(i/100f).multiply(2));

                    double nominator = Math.abs(i) / 2.5;

                    if (nominator < 10) {
                        nominator = 10;
                    }

                    double locationX = particleLocation.getX() + StaticVariables.random.nextGaussian() / nominator;
                    double locationY = particleLocation.getY() + StaticVariables.random.nextGaussian() / nominator;
                    double locationZ = particleLocation.getZ() + StaticVariables.random.nextGaussian() / nominator;

                    world.spawnParticle(Particle.SPELL_MOB, locationX, locationY, locationZ, 0, 1, 1, 1);
                    runnableLocation.add(0, -1/100f, 0);
                }

                if (amountOfTicks > 3) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

    }


    // MOVE 5
    // Tracking blade
    // -> Shoots a little blade of air in the looking direction tracking the closest entity
    public static void move5(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        Location playerLocation = player.getLocation();
        Location bladeStartLocation = playerLocation.clone();
        Vector bladeStartDirection = bladeStartLocation.getDirection();
        bladeStartLocation.add(bladeStartDirection).add(0, 1, 0);

        LivingEntity target = null;
        double closest = -1;
        if (!world.getNearbyEntities(playerLocation, 50, 5, 50).isEmpty()) {
            for (Entity entity : world.getNearbyEntities(playerLocation, 50, 5, 50)) {
                if (entity != null) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        if (livingEntity != player) {
                            double distance = MathTools.calculate3dDistance(playerLocation, livingEntity.getLocation());
                            if (closest == -1 || distance < closest ) {
                                closest = distance;
                                target = livingEntity;
                            }
                        }
                    }
                }
            }
        }
        if (target != null) {
            target.setGlowing(true);
            LivingEntity finalTarget = target;
            Location currentLocation = bladeStartLocation.clone();

            new BukkitRunnable() {
                int amountOfTicks = 0;
                @Override
                public void run() {

                    Location targetLocation = finalTarget.getLocation().add(0, 1, 0);
                    Vector pathDirection = MathTools.getDirectionNormVector3d(currentLocation, targetLocation);

                    for (int i = 0; i < 20; i++) {
                        double particleLocationX = currentLocation.getX() + StaticVariables.random.nextGaussian() / 4;
                        double particleLocationZ = currentLocation.getZ() + StaticVariables.random.nextGaussian() / 4;
                        world.spawnParticle(Particle.END_ROD, particleLocationX, currentLocation.getY(), particleLocationZ, 0);
                    }

                    currentLocation.add(pathDirection.multiply(0.2));

                    if (targetLocation.getX() > currentLocation.getX() - 0.04 && targetLocation.getX() < currentLocation.getX() + 0.04 ||
                            targetLocation.getX() > currentLocation.getX() - 0.04 && targetLocation.getX() < currentLocation.getX() + 0.04 ||
                            targetLocation.getX() > currentLocation.getX() - 0.04 && targetLocation.getX() < currentLocation.getX() + 0.04
                    ) {
                        this.cancel();
                        finalTarget.damage(5);
                        finalTarget.setGlowing(false);
                        for (int i = 0; i < 200; i++) {
                            world.spawnParticle(Particle.END_ROD, currentLocation, 0, StaticVariables.random.nextGaussian() / 10, StaticVariables.random.nextGaussian() / 10, StaticVariables.random.nextGaussian() / 10);
                        }
                    }
                    else if (amountOfTicks > 400 || finalTarget.isDead()) {
                        this.cancel();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        }
    }


    // MOVE 6
    // Wind Cloak
    // -> Gives the player a cloak of wind
    // -> If the player gets damaged by an entity, the entity gets knocked back hard

    // activation
    public static void move6(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        activePlayer.setWindCloak(true);

        new BukkitRunnable() {
            int amountOfTicks = 0;
            @Override
            public void run() {

                Location location = player.getLocation().add(0, 1.1, 0);
                for (int i = 0; i < 10; i++) {
                    world.spawnParticle(Particle.SPIT, MathTools.locationOnCircle(location, 0.5, i, world), 0);
                }

                if (amountOfTicks > 200) {
                    this.cancel();
                    activePlayer.setWindCloak(false);
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }

    // knockback entities on damage
    public static void move6knockback(ActivePlayer activePlayer, EntityDamageByEntityEvent event) {
        if (activePlayer.hasWindCloak()) {
            Player player = activePlayer.getPlayer();
            Entity entity = event.getDamager();
            if (entity != player) {
                if (entity instanceof LivingEntity) {
                    LivingEntity damager = (LivingEntity) entity;
                    Vector direction = MathTools.getDirectionNormVector(player.getLocation(), damager.getLocation());
                    damager.setVelocity(direction.clone().multiply(5).setY(0.5));
                    damager.damage(3, player);
                }
            }
        }
    }


    // MOVE 7
    // Tornado
    // -> Shoots a tornado launching entities up
    public static void move7(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        final Location location = player.getLocation();
        final Vector direction = location.getDirection();
        location.add(direction);

        new BukkitRunnable() {
            int amountOfTicks = 0;
            int angle1 = 0;
            @Override
            public void run() {

                Location centerLocation = location.clone();
                for (int i = 10; i < 100; i++) {
                    Location particle1Location = MathTools.locationOnCircle(centerLocation, i / 20f, angle1, world);
                    world.spawnParticle(Particle.SPELL_MOB, particle1Location, 0, 1 ,1 ,1);
                    angle1+= 37;
                    centerLocation.add(0, 0.1, 0);
                    if (i % 10 == 0) {
                        if (!world.getNearbyEntities(centerLocation, i/20f, 1, i/20f).isEmpty()) {
                            for (Entity entity : world.getNearbyEntities(centerLocation, i/20f, 1, i/20f)) {
                                if (entity != null) {
                                    if (entity instanceof LivingEntity) {
                                        LivingEntity livingEntity = (LivingEntity) entity;
                                        if (livingEntity != player) {
                                            livingEntity.setVelocity(new Vector(0, 2, 0));
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
                location.add(direction.clone().multiply(0.2));

                if (amountOfTicks > 200) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }


    // MOVE 8
    // Levitate
    // -> Gives player the ability to select an entity and move it in the air for 10 seconds
}
