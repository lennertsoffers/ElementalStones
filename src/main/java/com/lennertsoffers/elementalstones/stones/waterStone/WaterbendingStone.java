package com.lennertsoffers.elementalstones.stones.waterStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class WaterbendingStone extends WaterStone {


    // PASSIVES


    /**
     * <b>PASSIVE 1: Deep Breath</b>
     * <p>
     *     When the player is in water, he gets the water breathing effect<br>
     *     <ul>
     *         <li><b>PotionEffect:</b> Water Breathing (duration: 10s, amplifier: 1)</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activePlayer going into the water
     */
    public static void passive1(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.waterBendingStones)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 210, 1, false, false, false));
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 200L);
    }

    /**
     * <b>PASSIVE 2: Water Walker</b>
     * <p>
     *     The player always moves forward in water at a normal speed<br>
     *     You can cancel the movement by crouching<br>
     *     Your speed can be doubled when executing move2<br>
     * </p>
     *
     * @param activePlayer the activeplayer moving through the water
     */
    public static void passive2(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        if (player.getLocation().getBlock().getType() == Material.WATER) {
            if (!Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.waterBendingStones)) {
                if (player.getPose() != Pose.SNEAKING) {
                    if (!activePlayer.isDoublePassive1()) {
                        player.setVelocity(player.getLocation().getDirection().multiply(0.3));
                    } else {
                        player.setVelocity(player.getLocation().getDirection());
                    }
                }
            }
        }
    }


    // MOVES


    /**
     * <b>MOVE 4: Bubblebeam</b>
     * <p>
     *     Shoots a beam of bubbles in the looking direction of the player<br>
     *     This beam damages entities over time<br>
     *     <ul>
     *         <li><b>Damage:</b> 1</li>
     *         <li><b>Duration:</b> 4.5s</li>
     *     </ul>
     * </p>
     * @param activePlayer the activePlayer executing the move
     * @return a BukkitRunnable that can be executed as move
     * @see WaterbendingStone#move4SpawnParticles(Location)
     */
    public static Runnable move4(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();

            // Creation of beam
            new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {

                    Location location = player.getLocation().add(0, 1, 0).add(player.getLocation().getDirection());

                    for (int i = 0; i < amountOfTicks; i++) {
                        move4SpawnParticles(location);
                        NearbyEntityTools.damageNearbyEntities(player, location, 1, 0.5, 0.5, 0.5);
                        location.add(player.getLocation().getDirection().multiply(0.4));
                    }

                    if (amountOfTicks > 30) {
                        this.cancel();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

            // Static beam
            new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {

                    Location location = player.getLocation().add(0, 1, 0).add(player.getLocation().getDirection());

                    for (int i = 0; i < 30; i++) {
                        move4SpawnParticles(location);
                        NearbyEntityTools.damageNearbyEntities(player, location, 1, 0.5, 0.5, 0.5);
                        location.add(player.getLocation().getDirection().multiply(0.4));
                    }

                    if (amountOfTicks > 30) {
                        this.cancel();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 30L, 1L);

            // Removing beam
            new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {

                    Location location = player.getLocation().add(0, 1, 0).add(player.getLocation().getDirection());
                    location.add(player.getLocation().getDirection().multiply(0.4 * 30));
                    for (int i = amountOfTicks; i < 30; i++) {
                        move4SpawnParticles(location);
                        NearbyEntityTools.damageNearbyEntities(player, location, 1, 0.5, 0.5, 0.5);
                        location.add(player.getLocation().getDirection().multiply(-0.4));
                    }

                    if (amountOfTicks > 30) {
                        this.cancel();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 60L, 1L);
        };
    }

    /**
     * <b>MOVE 5: Healing Waters</b>
     * <p>
     *     Heals the player over time if he/she stand in water<br>
     *     The player will be slower but gets the absorption effect<br>
     *     <ul>
     *         <li><b>Heal:</b> 3health/s</li>
     *         <li><b>Duration:</b> 10s</li>
     *         <li><b>PotionEffects:</b>
     *             <ul>
     *                 <li>Absorption (duration: 10s, amplifier: 1)</li>
     *                 <li>Slowness (duration: 2s, amplifier: 2)</li>
     *             </ul>
     *         </li>
     *     </ul>
     * </p>
     * @param activePlayer the activePlayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move5(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            new BukkitRunnable() {
                int amountOfSeconds = 0;

                @Override
                public void run() {
                    Location location = player.getLocation();
                    if (location.getBlock().getType() == Material.WATER) {
                        if (player.getHealth() > 19) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 1, true, true, true));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 2, true, true, true));
                        }

                        player.setHealth(player.getHealth() + 3);

                    }
                    location.add(0, 1, 0);
                    if (amountOfSeconds > 10) {
                        this.cancel();
                    }
                    amountOfSeconds++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 20L);
        };
    }

    /**
     * <b>MOVE 6: Puffer Beam</b>
     * <p>
     *     Rapidly shoots 100 puffer fish in the looking direction
     * </p>
     *
     * @param activePlayer the activePlayer executing this move
     * @return a BukkitRunnable that can be run as move
     */
    public static Runnable move6(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {
                    Location location = player.getLocation().add(0, 1, 0);
                    location.add(location.getDirection());

                    Entity pufferFish = player.getWorld().spawnEntity(location, EntityType.PUFFERFISH);
                    pufferFish.setVelocity(location.getDirection().multiply(2));

                    if (amountOfTicks >= 100) {
                        this.cancel();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }

    /**
     * <b>MOVE 7: Aqua Ring</b>
     * <p>
     *     Creates a huge wave around the player knocking back all entities<br>
     *     <ul>
     *         <li><b>Knockback 4:</b> 4</li>
     *     </ul>
     * </p>
     * @param activePlayer the activePlayer executing the move
     * @return a BukkitRunnable that can be executed as move
     * @see WaterbendingStone#move7ReplaceWater(ArrayList)
     * @see WaterbendingStone#move7KnockBackEntities(Location, Player)
     */
    public static Runnable move7(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = player.getLocation();
            Vector direction = location.getDirection().setY(0);
            ArrayList<Location> waterBlockLocations = new ArrayList<>();
            ArrayList<Integer> ignoreAnglesB = new ArrayList<>();
            ArrayList<Integer> ignoreAnglesM = new ArrayList<>();
            ArrayList<Integer> ignoreAnglesT = new ArrayList<>();
            new BukkitRunnable() {
                int amountOfTicks = 1;

                @Override
                public void run() {

                    move7ReplaceWater(waterBlockLocations);
                    waterBlockLocations.clear();
                    for (int i = 0; i < 360; i++) {
                        System.out.println(i);
                        Location waterBlockLocationB = location.clone().add(direction.clone().rotateAroundY(i).multiply(amountOfTicks));
                        Location waterBlockLocationM = waterBlockLocationB.clone().add(0, 1, 0);
                        Location waterBlockLocationT = waterBlockLocationB.clone().add(0, 2, 0);

                        if (waterBlockLocationB.getBlock().getType() != Material.AIR && waterBlockLocationB.getBlock().getType() != Material.WATER) {
                            ignoreAnglesB.add(i);
                        }
                        if (waterBlockLocationM.getBlock().getType() != Material.AIR && waterBlockLocationM.getBlock().getType() != Material.WATER) {
                            ignoreAnglesM.add(i);
                        }
                        if (waterBlockLocationT.getBlock().getType() != Material.AIR && waterBlockLocationT.getBlock().getType() != Material.WATER) {
                            ignoreAnglesT.add(i);
                        }

                        if (!ignoreAnglesB.contains(i)) {
                            waterBlockLocations.add(waterBlockLocationB);
                            waterBlockLocationB.getBlock().setType(Material.WATER);
                            move7KnockBackEntities(waterBlockLocationB, player);
                        }
                        if (!ignoreAnglesM.contains(i)) {
                            waterBlockLocations.add(waterBlockLocationM);
                            waterBlockLocationM.getBlock().setType(Material.WATER);
                            move7KnockBackEntities(waterBlockLocationM, player);
                        }
                        if (!ignoreAnglesT.contains(i)) {
                            waterBlockLocations.add(waterBlockLocationT);
                            waterBlockLocationT.getBlock().setType(Material.WATER);
                            move7KnockBackEntities(waterBlockLocationT, player);
                        }
                    }

                    if (amountOfTicks > 20) {
                        this.cancel();
                        move7ReplaceWater(waterBlockLocations);
                        waterBlockLocations.clear();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }

    // MOVE 8
    // Water Wall
    public static Runnable move8(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {

                    Block targetBlock = player.getTargetBlockExact(10);
                    if (targetBlock != null) {
                        targetBlock.getLocation().add(0, 4, 0).getBlock().setType(Material.WATER);
                        new BukkitRunnable() {
                            int amountOfAliveTicks = 0;

                            @Override
                            public void run() {
                                if (!player.getWorld().getNearbyEntities(targetBlock.getLocation(), 2, 4, 2).isEmpty()) {
                                    for (Entity entity : player.getWorld().getNearbyEntities(targetBlock.getLocation(), 1, 4, 1)) {
                                        if (entity != null) {
                                            entity.setVelocity(new Vector(0, 0, 0));
                                        }
                                    }
                                }
                                if (amountOfAliveTicks > 200) {
                                    this.cancel();
                                    targetBlock.getLocation().add(0, 4, 0).getBlock().setType(Material.AIR);
                                }
                                amountOfAliveTicks++;
                            }
                        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
                    }


                    if (amountOfTicks > 19) {
                        this.cancel();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }


    // HELPERS


    /**
     * <b>Creates the particle effect of a bubble beam</b>
     * <p>
     *     Spawns a ball of bubble particles in the world on the given location<br>
     * </p>
     *
     * @param location the location where the particles must spawn
     */
    private static void move4SpawnParticles(Location location) {
        World world = location.getWorld();

        if (world != null) {
            for (int j = 0; j < 10; j++) {
                double locationX = location.getX() + StaticVariables.random.nextGaussian() / 10;
                double locationY = location.getY() + StaticVariables.random.nextGaussian() / 10;
                double locationZ = location.getZ() + StaticVariables.random.nextGaussian() / 10;
                world.spawnParticle(Particle.WATER_BUBBLE, locationX, locationY, locationZ, 0);
            }
        }
    }

    /**
     * <b>Knocks back all entities on the location away from the player</b>
     * @param location the location to check for entities
     * @param player the player from where the entities must be knocked back
     */
    private static void move7KnockBackEntities(Location location, Player player) {
        if (location.getWorld() != null) {
            if (!location.getWorld().getNearbyEntities(location, 0.5, 0.5, 0.5).isEmpty()) {
                for (Entity entity : location.getWorld().getNearbyEntities(location, 0.5, 0.5, 0.5)) {
                    if (entity != null) {
                        if (entity != player) {
                            entity.setVelocity(MathTools.getDirectionNormVector(player.getLocation(), entity.getLocation()));
                        }
                    }
                }
            }
        }
    }

    /**
     * <b>Replaces water blocks with air again</b>
     * @param waterBlockLocations a list of locations that must be set to air again
     */
    private static void move7ReplaceWater(ArrayList<Location> waterBlockLocations) {
        for (Location waterBlockLocation : waterBlockLocations) {
            if (waterBlockLocation.getBlock().getType() == Material.WATER) {
                waterBlockLocation.getBlock().setType(Material.AIR);
            }
            if (waterBlockLocation.clone().add(0, 1, 0).getBlock().getType() == Material.WATER) {
                waterBlockLocation.clone().add(0, 1, 0).getBlock().setType(Material.AIR);
            }
            if (waterBlockLocation.clone().add(0, 2, 0).getBlock().getType() == Material.WATER) {
                waterBlockLocation.clone().add(0, 2, 0).getBlock().setType(Material.AIR);
            }
        }
    }
}




















