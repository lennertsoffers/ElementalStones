package com.lennertsoffers.elementalstones.stones.earthStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.Pillar;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EarthStone {

    /**
     * <b>MOVE 1: Stone Pillar</b>
     * <p>
     *     Creates a pillar on the targeted location<br>
     *     Colliding entities are launched up<br>
     *     The pillar will be removed after a certain amount of time<br>
     *     <ul>
     *         <li><b>Duration:</b> 3s</li>
     *         <li><b>Range: </b> 40</li>
     *         <li><b>Knockup:</b> 1</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     * @see Pillar
     */
    public static Runnable move1(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Block targetBlock = player.getTargetBlockExact(40);
            Vector velocity = new Vector(0, 1, 0);

            if (targetBlock != null) {
                World world = player.getWorld();
                Location targetLocation = targetBlock.getLocation();

                if (!targetBlock.getType().isSolid()) {
                    targetLocation.add(0, -1, 0);
                }

                List<Block> moveBlocks = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    Location underGroundLocation = targetLocation.clone().add(0, -i, 0);
                    Location aboveGroundLocation = targetLocation.clone().add(0, i + 1, 0);
                    Block underGroundBlock = world.getBlockAt(underGroundLocation);
                    Block aboveGroundBlock = world.getBlockAt(aboveGroundLocation);

                    if (
                            underGroundBlock.getType().isSolid() &&
                            (CheckLocationTools.isFoliage(aboveGroundLocation) || aboveGroundBlock.getType().isAir())
                    ) {
                        moveBlocks.add(underGroundBlock);
                    } else {
                        break;
                    }
                }

                if (moveBlocks.size() > 0) {
                    Pillar pillar = new Pillar(targetLocation, moveBlocks, true);
                    for (Entity entity : world.getNearbyEntities(targetLocation, 1, 3, 1)) {
                        entity.setVelocity(velocity);
                    }
                    pillar.runTaskTimer(StaticVariables.plugin, 3L, 1L);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Collections.reverse(moveBlocks);

                            Pillar pillarRemoval = new Pillar(targetLocation, moveBlocks, false);
                            pillarRemoval.runTaskTimer(StaticVariables.plugin, 3L, 1L);
                        }
                    }.runTaskLater(StaticVariables.plugin, 66L);
                }
            }
        };
    }

    /**
     * <b>MOVE 2: Earthquake</b>
     * <p>
     *     Makes a circular earthquake damaging, slowing and confusing living entities
     *     <ul>
     *         <li><b>Duration:</b> 4s</li>
     *         <li><b>Damage:</b> 1</li>
     *         <li><b>Range:</b> 10</li>
     *         <li><b>PotionEffects: </b>
     *             <ul>
     *                 <li>Slowness (duration: 10, amplifier: 1)</li>
     *                 <li>Confusion (duration: 20, amplifier: 1)</li>
     *             </ul>
     *         </li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move2(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location middlePoint = player.getLocation();
            World world = player.getWorld();
            List<PotionEffect> potionEffects = new ArrayList<>();
            potionEffects.add(new PotionEffect(PotionEffectType.SLOW, 200, 1, false, false, false));
            potionEffects.add(new PotionEffect(PotionEffectType.CONFUSION, 400, 1, false, false, true));

            ArrayList<Location> earthquakeLocations = new ArrayList<>();

            for (int radius = 0; radius < 10; radius++) {
                for (int angle = 0; angle < 360; angle++) {
                    Location newLocation = MathTools.locationOnCircle(middlePoint, radius, angle, world);
                    Location roundedLocation = CheckLocationTools.getClosestAirBlockLocation(newLocation.getBlock().getLocation());
                    if (roundedLocation != null) {
                        if (!earthquakeLocations.contains(roundedLocation)) {
                            earthquakeLocations.add(roundedLocation);
                        }
                    }
                }
            }

            new BukkitRunnable() {
                int amountOfTicks = 0;
                @Override
                public void run() {
                    for (Location location : earthquakeLocations) {
                        Block block = world.getBlockAt(location.clone().add(0, -1, 0));
                        BlockData blockData = block.getBlockData();

                        NearbyEntityTools.damageNearbyEntities(player, location.clone().add(0, 1, 0), 1, 1, 1, 1, potionEffects);

                        for (int i = 0; i < 10; i++) {
                            double x = location.getX() + StaticVariables.random.nextInt(10) / 10d;
                            double z = location.getZ() + StaticVariables.random.nextInt(10) / 10d;
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    world.spawnParticle(Particle.BLOCK_CRACK, x, location.getY(), z, 1, blockData);
                                }
                            }.runTaskLater(StaticVariables.plugin, StaticVariables.random.nextInt(9));
                        }
                    }

                    if (amountOfTicks > 8) {
                        this.cancel();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 10L);
        };
    }

    /**
     * <b>MOVE 3: Pushback</b>
     * <p>
     *     Launches a wall that pushes away entities
     *     <ul>
     *         <li><b>Range:</b> 40</li>
     *         <li><b>Knockback:</b> 1.5</li>
     *         <li><b>Knockup:</b> 0.2</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move3(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            Location location = player.getLocation().add(player.getLocation().getDirection().multiply(2));
            Vector direction = location.getDirection().setY(0);
            Vector directionLeft = direction.clone().rotateAroundY(90);
            Vector directionRight = direction.clone().rotateAroundY(-90);
            Material material = world.getBlockAt(player.getLocation().add(0, -1, 0)).getType();

            if (!CheckLocationTools.isSolidBlock(material)) {
                material = Material.STONE;
            }
            Material finalMaterial = material;
            ArrayList<Location> toChangeLocations = new ArrayList<>();

            new BukkitRunnable() {
                int amountOfTicks = 0;
                @Override
                public void run() {

                    for (Location l : toChangeLocations) {
                        world.getBlockAt(l).setType(Material.AIR);
                    }
                    toChangeLocations.clear();

                    ArrayList<Location> setBlockLocations = new ArrayList<>();
                    for (int i = 0; i < 2; i++) {
                        Location locationLeft = CheckLocationTools.getClosestAirBlockLocation(location.clone().add(directionLeft));
                        Location locationMiddle  = CheckLocationTools.getClosestAirBlockLocation(location);
                        Location locationRight = CheckLocationTools.getClosestAirBlockLocation(location.clone().add(directionRight));

                        if (locationLeft != null) {
                            locationLeft.add(0, i, 0);
                            setBlockLocations.add(locationLeft);
                        }
                        if (locationMiddle != null) {
                            locationMiddle.add(0, i, 0);
                            setBlockLocations.add(locationMiddle);
                        }
                        if (locationRight != null) {
                            locationRight.add(0, i, 0);
                            setBlockLocations.add(locationRight);
                        }
                    }

                    for (Location l : setBlockLocations) {
                        world.getBlockAt(l).setType(finalMaterial);
                        toChangeLocations.add(l);
                        for (Entity entity : world.getNearbyEntities(l, 1, 1, 1)) {
                            if (entity != player) {
                                entity.setVelocity(direction.clone().multiply(1.5).setY(0.2));
                            }
                        }
                    }

                    location.add(direction);

                    if (amountOfTicks > 40) {
                        this.cancel();
                        for (Location l : toChangeLocations) {
                            world.getBlockAt(l).setType(Material.AIR);
                        }
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }
}
