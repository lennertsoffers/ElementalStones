package com.lennertsoffers.elementalstones.stones.earthStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.Comet;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.LavaSphere;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.LavaSpout;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.LavaWave;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class LavaStone extends EarthStone {

    // PASSIVE
    // Passive1: Lava Walker
    public static void passive1(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        Location location = player.getLocation().add(0, -1, 0);
        ArrayList<Location> locationGroup = new ArrayList<>();
        if (CheckLocationTools.lavaAroundPlayer(location)) {
            location.add(2, 0, 2);
            Location startLocation = location.clone();
            for (int i = 1; i <= 25; i++) {
                if (!activePlayer.isInLavaBlockLocations(location)) {
                    locationGroup.add(location.clone());
                    if (
                            !(startLocation.getX() == location.getX() && startLocation.getZ() == location.getZ()) &&
                                    !(startLocation.getX() == location.getX() && startLocation.getZ() - 4 == location.getZ()) &&
                                    !(startLocation.getX() - 4 == location.getX() && startLocation.getZ() == location.getZ()) &&
                                    !(startLocation.getX() - 4 == location.getX() && startLocation.getZ() - 4 == location.getZ())
                    ) {
                        world.getBlockAt(location).setType(Material.BASALT);
                    }
                }
                location.add(-1, 0, 0);
                if (i % 5 == 0) {
                    location.add(5, 0, -1);
                }
            }
        }
        activePlayer.setRemoveBasald(new BukkitRunnable() {
            int blocksRemoved = 1;
            @Override
            public void run() {
                if (locationGroup.size() >= 1) {
                    int index = StaticVariables.random.nextInt(Math.abs(locationGroup.size()));
                    world.getBlockAt(locationGroup.get(index)).setType(Material.LAVA);
                    locationGroup.remove(index);
                }
                if (blocksRemoved >= 25) {
                    this.cancel();
                }
                blocksRemoved++;
            }
        });
    }

    // Passive 2: Magma Master
    public static void passive2(ActivePlayer activePlayer, EntityDamageEvent event) {
        if (!Collections.disjoint(Arrays.asList(activePlayer.getPlayer().getInventory().getContents()), ItemStones.lavaStones)) {
            if (
                    event.getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.LAVA ||
                            event.getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.FIRE_TICK ||
                            event.getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.FIRE ||
                            event.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR
            ) {
                event.setCancelled(true);
            }
        }
    }


    /**
     * <b>MOVE 4: Reverse Logic</b>
     * <p>
     *     The player heals over time while standing on magma blocks<br>
     *     <ul>
     *         <li><b>Heal:</b> 3 health/s</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move4(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();

            new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {
                    Location location = player.getLocation().add(0, 1, 0);

                    for (int i = 0; i < 2; i++) {
                        player.getWorld().spawnParticle(Particle.REDSTONE, location.getX() + StaticVariables.random.nextGaussian() / 3, location.getY() + StaticVariables.random.nextGaussian() / 3, location.getZ() + StaticVariables.random.nextGaussian() / 3, 0, 0, 0, 0, new Particle.DustOptions(Color.RED, 1));
                    }

                    if (amountOfTicks % 20 == 0) {
                        if (world.getBlockAt(location.clone().add(0, -2, 0)).getType() == Material.MAGMA_BLOCK) {
                            double health = player.getHealth() + 3;
                            health = health <= 20 ? health : 20;

                            player.setHealth(health);
                        }
                    }

                    if (amountOfTicks >= 200) {
                        this.cancel();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }

    /**
     * <b>MOVE 5: Lava Wave</b>
     * <p>
     *     Creates a wave of lava in the looking direction
     *     <ul>
     *         <li><b>Damage:</b> 40</li>
     *         <li><b>Range: </b> 50</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     * @see LavaWave
     */
    public static Runnable move5(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location playerLocation = player.getLocation();

            float yaw = playerLocation.getYaw();
            if (yaw > -25 && yaw < 25) {
                new LavaWave(activePlayer, true, true, true).runTaskTimer(StaticVariables.plugin, 0L, 1L);
            } else if (yaw >= 25 && yaw < 65) {
                new LavaWave(activePlayer, false, true, false).runTaskTimer(StaticVariables.plugin, 0L, 1L);
            } else if (yaw >= 65 && yaw < 115) {
                new LavaWave(activePlayer, true, false, true).runTaskTimer(StaticVariables.plugin, 0L, 1L);
            } else if (yaw >= 115 && yaw < 155) {
                new LavaWave(activePlayer, false, false, true).runTaskTimer(StaticVariables.plugin, 0L, 1L);
            } else if (yaw < -155 || yaw > 155) {
                new LavaWave(activePlayer, true, false, false).runTaskTimer(StaticVariables.plugin, 0L, 1L);
            } else if (yaw <= -25 && yaw > -65) {
                new LavaWave(activePlayer, false, true, true).runTaskTimer(StaticVariables.plugin, 0L, 1L);
            } else if (yaw <= -65 && yaw > -115) {
                new LavaWave(activePlayer, true, true, false).runTaskTimer(StaticVariables.plugin, 0L, 1L);
            } else {
                new LavaWave(activePlayer, false, false, false).runTaskTimer(StaticVariables.plugin, 0L, 1L);
            }
        };
    }

    /**
     * <b>MOVE 6: Comet</b>
     * <p>
     *     Marks a spot where a comet wil land after a few seconds
     *     <ul>
     *         <li><b>Range: </b> 30</li>
     *         <li><b>Explosion Power: </b>5</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     * @see Comet
     */
    public static Runnable move6(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            Block targetBlock = player.getTargetBlockExact(30);
            if (targetBlock != null) {
                Location targetBlockLocation = targetBlock.getLocation();

                new BukkitRunnable() {
                    int amountOfTicks = 0;
                    
                    @Override
                    public void run() {
                        for (int i = 0; i < 360; i++) {
                            Location particleLocation = CheckLocationTools.getClosestAirBlockLocation(MathTools.locationOnCircle(targetBlockLocation, 3, i, world));

                            if (particleLocation != null) {
                                world.spawnParticle(Particle.FLAME, particleLocation, 0);
                            }
                        }

                        spawnTriangle(targetBlockLocation, Arrays.asList(0, 90, 180));
                        spawnTriangle(targetBlockLocation, Arrays.asList(45, 135, 225));

                        if (amountOfTicks > 50) {
                            this.cancel();
                        }

                        System.out.println(amountOfTicks);
                        amountOfTicks += 5;
                    }
                }.runTaskTimer(StaticVariables.plugin, 0L, 5L);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Location startLocation;
                        Location endLocation;

                        int randomInt = StaticVariables.random.nextInt(4);

                        if (randomInt == 0) {
                            startLocation = targetBlockLocation.clone().add(0.5, 100, 40.5);
                            endLocation = targetBlockLocation.clone().add(0.5, 0, -2.5);
                        } else if (randomInt == 1) {
                            startLocation = targetBlockLocation.clone().add(0.5, 100, -40.5);
                            endLocation = targetBlockLocation.clone().add(0.5, 0, 0.5);
                        } else if (randomInt == 2) {
                            startLocation = targetBlockLocation.clone().add(40.5, 100, 0.5);
                            endLocation = targetBlockLocation.clone().add(-1.5, 0, -1.5);
                        } else {
                            startLocation = targetBlockLocation.clone().add(-40.5, 100, 0.5);
                            endLocation = targetBlockLocation.clone().add(0.5, 0, -1.5);
                        }

                        Comet comet = new Comet(activePlayer, startLocation, endLocation);
                        comet.runTaskTimer(StaticVariables.plugin, 0L, 1L);
                    }
                }.runTaskLater(StaticVariables.plugin, 40L);
            }
        };
    }

    /**
     * <b>Spawns a line of particles connecting 2 points on a circle with a certain angle</b>
     * @param center the center of the circle
     * @param angles the angle at which the line must spawn
     */
    private static void spawnTriangle(Location center, List<Integer> angles) {
        World world = center.getWorld();

        if (world != null) {
            Location loc1 = MathTools.locationOnCircle(center, 3, angles.get(0), world);
            Location loc2 = MathTools.locationOnCircle(center, 3, angles.get(1), world);
            Location loc3 = MathTools.locationOnCircle(center, 3, angles.get(2), world);

            Vector loc1loc2 = new Vector(loc2.getX() - loc1.getX(), 0, loc2.getZ() - loc1.getZ()).multiply(0.05);
            Vector loc1loc3 = new Vector(loc3.getX() - loc1.getX(), 0, loc3.getZ() - loc1.getZ()).multiply(0.05);
            Vector loc2loc3 = new Vector(loc3.getX() - loc2.getX(), 0, loc3.getZ() - loc2.getZ()).multiply(0.05);

            for (double i = 0; i < 20; i += 0.1) {
                Location particleLocation1 = CheckLocationTools.getClosestAirBlockLocation(loc1.clone().add(loc1loc2.clone().multiply(i)));
                Location particleLocation2 = CheckLocationTools.getClosestAirBlockLocation(loc1.clone().add(loc1loc3.clone().multiply(i)));
                Location particleLocation3 = CheckLocationTools.getClosestAirBlockLocation(loc2.clone().add(loc2loc3.clone().multiply(i)));

                if (particleLocation1 != null) {
                    world.spawnParticle(Particle.FLAME, particleLocation1, 0);
                }

                if (particleLocation2 != null) {
                    world.spawnParticle(Particle.FLAME, particleLocation2, 0);
                }

                if (particleLocation3 != null) {
                    world.spawnParticle(Particle.FLAME, particleLocation3, 0);
                }
            }
        }
    }

    /**
     * <b>MOVE 7: Lava Spout</b>
     * <p>
     *     The targeted block bursts open creating an intense flow of lava<br>
     *     This lava spout will launch entities up<br>
     *     <ul>
     *         <li><b>Range: </b> 15</li>
     *         <li><b>Knockup: </b>1</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     * @see LavaSpout
     */
    public static Runnable move7(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();

            Block targetBlock = player.getTargetBlockExact(15);

            if (targetBlock != null) {
                Location location = CheckLocationTools.getClosestAirBlockLocation(targetBlock.getLocation());

                if (location != null) {
                    location.add(0, -1, 0);

                    LavaSpout lavaSpout = new LavaSpout(player, location);
                    lavaSpout.runTaskTimer(StaticVariables.plugin, 0L, 1L);
                }
            }
        };
    }

    /**
     * <b>ULTIMATE: Lava Sphere</b>
     * <p>
     *     The player begins to fly on a large ball of lava<br>
     *     This lava does damage to entities you fly over<br>
     *     After the move the player shoots up and can try to land safely on the lowering lava<br>
     *     <ul>
     *         <li><b>Duration: </b> 1min</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     * @see LavaSphere
     */
    public static Runnable move8(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            activePlayer.setLavaStoneMove8Active(true);
            player.setAllowFlight(true);
            player.setFlying(true);

            LavaSphere lavaSphere = new LavaSphere(player);
            lavaSphere.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }
}