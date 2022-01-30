package com.lennertsoffers.elementalstones.stones.windStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class AgilityStone extends AirStoneSharedPassive {


    // MOVES //


    /**
     * <b>MOVE 4: Forwards Dash</b>
     * <p>
     *     Player dashes in its looking direction<br>
     *     It damages entities along its way<br>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move4(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();

            new BukkitRunnable() {
                int amountOfTicks = 0;
                @Override
                public void run() {
                    World world = player.getWorld();

                    if (NearbyEntityTools.damageNearbyEntities(player, player.getLocation(), activePlayer.isInAirBoost() ? 7 : 3, 0.7, 0.7, 0.7)) {
                        world.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 1);
                    }

                    world.spawnParticle(Particle.CLOUD, player.getLocation().add(0, 1, 0), 0);

                    if (amountOfTicks > 10) {
                        this.cancel();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

            int multiplier = 2;
            if (activePlayer.isInAirBoost()) {
                multiplier = 4;
            }

            Vector velocity = player.getLocation().getDirection().multiply(multiplier);
            if (velocity.getY() < 0.2 && velocity.getY() > -0.2) {
                velocity.setY(0.2);
            }

            player.setVelocity(velocity);
        };
    }

    /**
     * <b>MOVE 5: Flying Knifes</b>
     * <p>
     *     Hovers 5 arrows around the player on activation<br>
     *     Each time the move gets activated again an arrow is shot<br>
     *     <ul>
     *         <li><b>Duration:</b> 2 minutes</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     * @see AgilityStone#getArrowLocations(Player)
     */
    public static Runnable move5(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();

            if (activePlayer.getMove5Arrows().isEmpty()) {
                World world = player.getWorld();

                getArrowLocations(player).forEach(arrowLocation -> {
                    Arrow arrow = (Arrow) world.spawnEntity(arrowLocation, EntityType.ARROW);
                    arrow.setGlowing(true);
                    activePlayer.getMove5Arrows().add(arrow);
                    arrow.setBounce(true);
                });

                new BukkitRunnable() {
                    int amountOfTicks = 0;

                    @Override
                    public void run() {
                        List<Location> toLocations = getArrowLocations(player);

                        for (int i = 0; i < activePlayer.getMove5Arrows().size(); i++) {
                            Arrow arrow = activePlayer.getMove5Arrows().get(i);

                            if (arrow.isDead()) {
                                activePlayer.getMove5Arrows().remove(arrow);
                            } else {
                                Location toLocation = toLocations.get(i);
                                Location fromLocation = arrow.getLocation();

                                double differenceX = toLocation.getX() - fromLocation.getX();
                                double differenceY = toLocation.getY() - fromLocation.getY();
                                double differenceZ = toLocation.getZ() - fromLocation.getZ();

                                Vector velocity = new Vector(differenceX, differenceY, differenceZ);

                                arrow.setVelocity(velocity.multiply(0.5));
                            }

                            if (amountOfTicks > 2400 || activePlayer.getMove5Arrows().size() == 0) {
                                this.cancel();
                                activePlayer.getMove5Arrows().clear();
                            }
                            amountOfTicks++;
                        }
                    }
                }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            } else {
                Arrow arrow = activePlayer.getMove5Arrows().removeLast();
                Location location = player.getLocation();
                arrow.setVelocity(location.getDirection().multiply(3));
            }
        };
    }

    /**
     * <b>MOVE 6: Smoke Ball</b>
     * <p>
     *     Throw a smoke ball in the looking direction<br>
     *     While the ball is moving, you can control it by changing your looking direction<br>
     *     If the ball collides with a living entity or block it will create a smoke screen<br>
     *     <ul>
     *         <li><b>Duration:</b> 20</li>
     *         <li><b>PotionEffect:</b> Slowness (duration: 5, amplifier 3)</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move6(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            Location startingLocation = player.getLocation().add(player.getLocation().getDirection().clone().multiply(2)).add(0, 1, 0);
            final Location impactLocation = player.getLocation();
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOW, 100, 3, true, true, true);

            BukkitRunnable smoke = new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {
                    for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
                        double radius = Math.sin(i) * 3;
                        double y = Math.cos(i) * 3;
                        for (double a = 0; a < Math.PI * 2; a += Math.PI / 10) {
                            double x = Math.cos(a) * radius;
                            double z = Math.sin(a) * radius;
                            Location particleLocation = impactLocation.clone().add(0, 2, 0).add(x, y, z);
                            player.getWorld().spawnParticle(Particle.REDSTONE, particleLocation.add(StaticVariables.random.nextGaussian() / 4, StaticVariables.random.nextGaussian() / 4, StaticVariables.random.nextGaussian() / 4), 0, 0, 0, 0, 0, new Particle.DustOptions(Color.WHITE, 100));
                        }
                    }

                    world.getNearbyEntities(impactLocation, 3, 3, 3, entity -> entity instanceof LivingEntity && entity != player).forEach(entity -> ((LivingEntity) entity).addPotionEffect(potionEffect));

                    if (amountOfTicks > 80) {
                        this.cancel();
                    }
                    amountOfTicks += 1;
                }
            };

            new BukkitRunnable() {
                int amountOfTicks = 0;
                final Location currentLocation = startingLocation;
                boolean endThrow = false;

                @Override
                public void run() {
                    Vector playerDirection = player.getLocation().getDirection();
                    world.spawnParticle(Particle.CLOUD, startingLocation, 0, playerDirection.getX(), playerDirection.getY(), playerDirection.getZ());

                    if (currentLocation.getBlock().getType().isSolid()) {
                        endThrow = true;
                    } else if (!world.getNearbyEntities(currentLocation, 0.5, 0.5, 0.5, entity -> entity instanceof LivingEntity).isEmpty()) {
                        endThrow = true;
                    }

                    if (amountOfTicks > 20 || endThrow) {
                        impactLocation.setX(currentLocation.getX());
                        impactLocation.setY(currentLocation.getY());
                        impactLocation.setZ(currentLocation.getZ());
                        this.cancel();
                        smoke.runTaskTimer(StaticVariables.plugin, 0, 5L);
                    }

                    amountOfTicks++;
                    currentLocation.add(playerDirection);
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }

    /**
     * <b>MOVE 7: Charge Jump</b>
     * <p>
     *     Charges until next activation (max 10 seconds)<br>
     *     The longer you charge, the higher you will jump<br>
     *     The player gets slowness when charging<br>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move7(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();

            if ((int) activePlayer.getCharge() == -1) {
                activePlayer.setMove7LaunchState(1);
                activePlayer.setChargingStart();
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000, 3, true, false, false));
                int nominator = 10;

                new BukkitRunnable() {
                    int amountOfTicks = 0;

                    @Override
                    public void run() {
                        Location location = player.getLocation().add(0, 1, 0);
                        Location particleLocation = MathTools.locationOnCircle(location.clone().add(0, StaticVariables.random.nextGaussian() / 2, 0), 2, StaticVariables.random.nextInt(180), world);
                        world.spawnParticle(Particle.CLOUD, particleLocation, 0, (location.getX() - particleLocation.getX()) / nominator, (location.getY() - particleLocation.getY()) / nominator, (location.getZ() - particleLocation.getZ()) / nominator);

                        if (amountOfTicks >= 200 || activePlayer.getMove7LaunchState() == 2) {
                            this.cancel();
                            if (activePlayer.getMove7LaunchState() != 2) {
                                move7(activePlayer).run();
                            }
                        }
                        amountOfTicks++;
                    }
                }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            } else {
                int nominator = 2000;
                if (activePlayer.isInAirBoost()) {
                    nominator = 1000;
                }
                activePlayer.setMove7LaunchState(2);
                double velocityY = activePlayer.getCharge() / nominator;
                activePlayer.resetCharge();
                player.removePotionEffect(PotionEffectType.SLOW);
                player.setVelocity(new Vector(0, velocityY, 0));

                new BukkitRunnable() {
                    int amountOfTicks = 0;

                    @Override
                    public void run() {
                        world.spawnParticle(Particle.CLOUD, player.getLocation(), 0);

                        amountOfTicks++;
                        if (amountOfTicks > velocityY * 10) {
                            this.cancel();
                        }
                    }
                }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            }
        };
    }

    /**
     * <b>ULTIMATE: Hyperspeed</b>
     * <p>
     *     Makes the player run faster<br>
     *     Makes the player jump higher<br>
     *     Dashes do more damage<br>
     *     Charge jump is stronger<br>
     *     <ul>
     *         <li><b>PotionEffects:</b>
     *             <ul>
     *                 <li>Speed (duration: 1min, amplifier: 3</li>
     *                 <li>Jump (duration: 1min, amplifier: 2</li>
     *             </ul>
     *         </li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move8(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            activePlayer.activateAirBoost();
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 3, false, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1200, 2, false, false, false));
        };
    }


    // HELPERS //


    /**
     * <b>Gets the locations around the player for the arrows position</b>
     *
     * @param player the player around which the locations are
     * @return a list of locations around the player
     */
    private static List<Location> getArrowLocations(Player player) {
        Location location = player.getLocation();
        Vector direction = location.getDirection();

        return Arrays.asList(
                location.clone().add(0, 1, 0).add(direction.clone().rotateAroundY(90)).add(direction.clone().multiply(1.3)),
                location.clone().add(0, 1, 0).add(direction.clone().rotateAroundY(-90)).add(direction.clone().multiply(1.3)),
                location.clone().add(0, 2, 0).add(direction.clone().rotateAroundY(90)).add(direction.clone().multiply(1.3)),
                location.clone().add(0, 2, 0).add(direction.clone().rotateAroundY(-90)).add(direction.clone().multiply(1.3)),
                location.clone().add(0, 2.5, 0).add(direction.clone().multiply(1.3))
        );
    }
}





















