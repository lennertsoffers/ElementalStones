package com.lennertsoffers.elementalstones.stones.earthStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FlyingPlatform;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class EarthbendingStone extends EarthStone {

    // HELPERS

    /**
     * <b>Spawns in an wave of fallingblocks into the world</b>
     * <p>
     *     The fallingblocks are spawned and then their corresponding block is set to air again<br>
     *     The earth wave knocks up living entities and gives them the slowness effect<br>
     * </p>
     * <p>
     *     This method depends on the spawnFallingBlock method<br>
     * </p>
     *
     * @param location the start location of the player
     * @param var0 tweaks the direction of the wave
     * @param var1 tweaks the direction of the wave
     * @param perpendicular if the wave should be perpendicular to the axes
     * @param activePlayer the activeplayer executing this move
     * @see EarthbendingStone#spawnFallingBlock(Location, Vector, Player)
     */
    private static void earthWaveNew(Location location, boolean var0, boolean var1, boolean perpendicular, ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();

        World world = location.getWorld();
        if (world == null) {
            return;
        }

        int startX;
        int startZ;
        Vector additionRow;
        Vector additionRowBlock;
        Vector additionRowStage1;
        Vector additionRowStage2;

        if (perpendicular) {
            if (var0) {
                if (var1) {
                    startX = 2;
                    additionRow = new Vector(1, 0, 0);
                } else {
                    startX = -2;
                    additionRow = new Vector(-1, 0, 0);
                }
                startZ = -1;
                additionRowBlock = new Vector(0, 0, 1);
            } else {
                if (var1) {
                    startZ = 2;
                    additionRow = new Vector(0, 0, 1);
                } else {
                    startZ = -2;
                    additionRow = new Vector(0, 0, -1);
                }
                startX = -1;
                additionRowBlock = new Vector(1, 0, 0);
            }

            Location rowLocation = location.clone().add(startX, 0, startZ);
            Vector velocity = new Vector(0, 0.3, 0);

            new BukkitRunnable() {
                int lengthOfWave = 0;

                @Override
                public void run() {
                    Location blockOnRowLocation = rowLocation.clone();

                    for (int i = 0; i < 3; i++) {
                        Location launchBlockLocation = CheckLocationTools.getClosestAirBlockLocation(blockOnRowLocation);

                        if (launchBlockLocation != null) {
                            launchBlockLocation.add(0, -1, 0);
                            spawnFallingBlock(launchBlockLocation, velocity, player);
                        }

                        blockOnRowLocation.add(additionRowBlock);
                    }

                    rowLocation.add(additionRow);

                    lengthOfWave++;
                    if (lengthOfWave >= 50) {
                        this.cancel();
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else {
            if (var0) {
                if (var1) {
                    startX = 3;
                    startZ = 1;
                    additionRowStage1 = new Vector(0, 0, 1);
                    additionRowStage2 = new Vector(1, 0, 0);
                    additionRowBlock = new Vector(-1, 0, 1);
                } else {
                    startX = -3;
                    startZ = -1;
                    additionRowStage1 = new Vector(0, 0, -1);
                    additionRowStage2 = new Vector(-1, 0, 0);
                    additionRowBlock = new Vector(1, 0, -1);
                }
            } else {
                if (var1) {
                    startX = 3;
                    startZ = -1;
                    additionRowStage1 = new Vector(0, 0, -1);
                    additionRowStage2 = new Vector(1, 0, 0);
                    additionRowBlock = new Vector(-1, 0, -1);
                } else {
                    startX = -3;
                    startZ = 1;
                    additionRowStage1 = new Vector(0, 0, 1);
                    additionRowStage2 = new Vector(-1, 0, 0);
                    additionRowBlock = new Vector(1, 0, 1);
                }
            }

            Location rowLocation = location.clone().add(startX, 0, startZ);
            Vector velocity = new Vector(0, 0.3, 0);

            new BukkitRunnable() {
                int lengthOfWave = 0;
                int blocksToPlace = 3;

                @Override
                public void run() {
                    Location blockOnRowLocation = rowLocation.clone();

                    for (int i = 0; i < blocksToPlace; i++) {
                        Location launchBlockLocation = CheckLocationTools.getClosestAirBlockLocation(blockOnRowLocation);

                        if (launchBlockLocation != null) {
                            launchBlockLocation.add(0, -1, 0);
                            spawnFallingBlock(launchBlockLocation, velocity, player);
                        }

                        blockOnRowLocation.add(additionRowBlock);
                    }


                    if (blocksToPlace == 3) {
                        rowLocation.add(additionRowStage1);
                        blocksToPlace = 2;
                    } else {
                        rowLocation.add(additionRowStage2);
                        blocksToPlace = 3;
                    }

                    lengthOfWave++;
                    if (lengthOfWave >= 50) {
                        this.cancel();
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        }
    }

    /**
     * <b>Spawns a falling block in the world</b>
     * <p>
     *     On the provided location, a fallingblock gets spawned<br>
     *     The fallingblock is of the same material as the block on that position<br>
     *     The real block is set to air and the fallingblock is launched up<br>
     * </p>
     *
     * @param location the location of the block
     * @param velocity speed at which the block should be launched up
     * @param player the player executing this move
     */
    private static void spawnFallingBlock(Location location, Vector velocity, Player player) {
        Location l = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        World world = location.getWorld();

        if (world != null) {
            Block launchBlock = world.getBlockAt(l);
            FallingBlock fallingBlock = world.spawnFallingBlock(l.clone().add(0.5, 0, 0.5), launchBlock.getBlockData());
            fallingBlock.setDropItem(false);
            fallingBlock.setVelocity(velocity);
            launchBlock.setType(Material.AIR);

            Vector direction = new Vector(0, 1, 0);
            List<PotionEffect> potionEffects = new ArrayList<>();
            potionEffects.add(new PotionEffect(PotionEffectType.SLOW, 100, 1, true, true, true));

            NearbyEntityTools.damageNearbyEntities(player, l, 0, 1.5, 1.5, 1.5, direction, potionEffects);
        }
    }

    /**
     * <b>Spawns in an array of dripstone spikes</b>
     * <p>
     *     If a spike collides with an entity, it gets 5 damage and the slow digging effect for 5 seconds<br>
     * </p>
     *
     * @param location the start location of the player
     * @param var0 tweaks the direction of the spikes
     * @param var1 tweaks the direction of the spikes
     * @param perpendicular if the spikes should be perpendicular to the axes
     * @param player the player executing this move
     */
    private static void spikeWave(Location location, boolean var0, boolean var1, boolean perpendicular, Player player) {
        World world = location.getWorld();
        if (world == null) {
            return;
        }

        // Determines the shape of the spikes of the arguments given
        int startX;
        int startZ;

        if (perpendicular) {
            if (var0) {
                startX = 2;
                startZ = 0;
            } else {
                startX = 0;
                startZ = 2;
            }

            if (!var1) {
                startX *= -1;
                startZ *= -1;
            }
        } else {
            if (var0) {
                if (var1) {
                    startX = 2;
                    startZ = 2;
                } else {
                    startX = 2;
                    startZ = -2;
                }
            } else {
                if (var1) {
                    startX = -2;
                    startZ = 2;
                } else {
                    startX = -2;
                    startZ = -2;
                }
            }
        }

        // Creates a list with all the base locations for the spikes
        List<List<Location>> spikeRows = new ArrayList<>();
        Location startLocation = location.add(startX, 0, startZ);

        int widthParam = 3;

        for (int j = 1; j < 7; j++) {
            List<Location> row = new ArrayList<>();
            int width = widthParam / 2;

            Vector startLocationAdditionVector;
            Vector rowLocationAdditionVector;
            if (perpendicular) {
                if (var0) {
                    if (var1) {
                        startLocationAdditionVector = new Vector(j, 0, -width);
                    } else {
                        startLocationAdditionVector = new Vector(-j, 0, -width);
                    }
                    rowLocationAdditionVector = new Vector(0, 0, 1);
                } else {
                    if (var1) {
                        startLocationAdditionVector = new Vector(-width, 0, j);
                    } else {
                        startLocationAdditionVector = new Vector(-width, 0, -j);
                    }
                    rowLocationAdditionVector = new Vector(1, 0, 0);
                }
            } else {
                if (var0) {
                    if (var1) {
                        rowLocationAdditionVector = new Vector(-1, 0, 1);
                    } else {
                        rowLocationAdditionVector = new Vector(-1, 0, -1);
                    }
                    startLocationAdditionVector = new Vector(j, 0, 0);
                } else {
                    if (var1) {
                        rowLocationAdditionVector = new Vector(1, 0, 1);
                    } else {
                        rowLocationAdditionVector = new Vector(1, 0, -1);
                    }
                    startLocationAdditionVector = new Vector(-j, 0, 0);
                }
            }

            Location rowLocation = startLocation.clone().add(startLocationAdditionVector);
            for (int i = 0; i < widthParam; i++) {
                row.add(CheckLocationTools.getClosestAirBlockLocation(rowLocation.clone()));
                rowLocation.add(rowLocationAdditionVector);
            }

            spikeRows.add(row);

            if (j % 2 == 0) {
                widthParam += 2;
            }
        }

        // List of potion effects to add to the entity that gets hurt
        List<PotionEffect> potionEffects = new ArrayList<>();
        potionEffects.add(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100, 2, false, false, true));

        // Placement of spikes
        new BukkitRunnable() {
            int rowIndex = 0;

            @Override
            public void run() {

                List<Location> spikeRow = spikeRows.get(rowIndex);

                for (Location loc : spikeRow) {
                    if (loc != null) {
                        Location spikeBlockLocation = loc.clone();

                        new BukkitRunnable() {
                            int i;
                            final int maxHeight = rowIndex;

                            @Override
                            public void run() {
                                Block spikeBlock = world.getBlockAt(spikeBlockLocation);
                                if (spikeBlock.getType() == Material.AIR) {
                                    NearbyEntityTools.damageNearbyEntities(player, spikeBlockLocation, 5, 1, 1, 1, potionEffects);

                                    spikeBlock.setType(Material.POINTED_DRIPSTONE);
                                    spikeBlockLocation.add(0, 1, 0);
                                } else {
                                    this.cancel();
                                }

                                if (i >= maxHeight) {
                                    this.cancel();
                                }

                                i++;
                            }
                        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
                    }
                }

                rowIndex++;

                if (rowIndex == spikeRows.size()) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

        // Removal of spikes
        new BukkitRunnable() {
            int iterations = 5;

            @Override
            public void run() {
                for (int i = iterations; i >= 0; i--) {
                    List<Location> row = spikeRows.get(i);

                    for (Location spikeLocation : row) {
                        Block block = world.getBlockAt(spikeLocation.clone().add(0, i, 0));

                        if (block.getType() == Material.POINTED_DRIPSTONE) {
                            block.setType(Material.AIR);
                        }
                    }
                }
                spikeRows.remove(0);

                if (iterations == 0) {
                    this.cancel();
                }

                iterations--;
            }
        }.runTaskTimer(StaticVariables.plugin, 60L, 3L);
    }

    /**
     * <b>Launches the platform of the ultimate down</b>
     * <p>
     *     It stops all BukkitRunnables keeping the platform up<br>
     *     The player will stop flying but take no fall damage<br>
     *     Earth Wave move is executed<br>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @see EarthbendingStone#move7(ActivePlayer)
     * @see FlyingPlatform
     */
    public static void move8launch(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();

        Vector platformFallVelocity = new Vector(0, -1, 0);
        for (FallingBlock fallingBlock : activePlayer.getMove8FallingBlocks()) {
            fallingBlock.setVelocity(platformFallVelocity);
            player.setVelocity(platformFallVelocity);
        }
        move8End(activePlayer);


        Location location = activePlayer.getPlayer().getLocation();
        earthWaveNew(location, true, true, true, activePlayer);
        earthWaveNew(location, true, false, true, activePlayer);
        earthWaveNew(location, false, true, true, activePlayer);
        earthWaveNew(location, false, false, true, activePlayer);

        new BukkitRunnable() {
            @Override
            public void run() {
                earthWaveNew(location, true, true, false, activePlayer);
                earthWaveNew(location, true, false, false, activePlayer);
                earthWaveNew(location, false, true, false, activePlayer);
                earthWaveNew(location, false, false, false, activePlayer);
            }
        }.runTaskLater(StaticVariables.plugin, 13L);

        new BukkitRunnable() {
            @Override
            public void run() {
                activePlayer.setMove8active(false);
            }
        }.runTaskLater(StaticVariables.plugin, 40L);
    }

    /**
     * <b>Ends the ultimate move</b>
     * <p>
     *     This is the gentle alternative for stopping the ultimate<br>
     *     It just stops the BukkitRunnables keeping the platform up<br>
     *     This is triggered when the time runs out or the platform touches the ground<br>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @see FlyingPlatform
     */
    public static void move8End(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        player.setFlying(false);
        player.setAllowFlight(false);
        activePlayer.setMovesEnabled(true);
    }


    // MOVES

    /**
     * <b>PASSIVE: Shockwave</b>
     * <p>
     *     Creates a shockwave around the player when he falls<br>
     *     Activates when the player should normally get fall damage (no fall damage is taken)<br>
     *     This wave knocks up entities<br>
     *     The size and the knockup height depends on the fall distance of the player<br>
     *     <ul>
     *         <li><b>PotionEffect:</b> Slowness (duration: 2s, amplifier: 1)</li>
     *     </ul>
     * </p>
     *
     *
     * @param activePlayer the activeplayer for whom this move is executed
     * @param event the EntityDamageEvent from the event listener
     */
    public static void passive(ActivePlayer activePlayer, EntityDamageEvent event) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        float fallDistance;
        if (player.getFallDistance() < 20) {
            fallDistance = player.getFallDistance();
        } else {
            fallDistance = 20;
        }
        List<PotionEffect> potionEffects = new ArrayList<>();
        potionEffects.add(new PotionEffect(PotionEffectType.SLOW, 40, 1, false, false, false));
        Vector velocity = new Vector(0, 0.2, 0);
        System.out.println(player.getFallDistance());

        event.setCancelled(true);

        List<Block> rings = new ArrayList<>();
        new BukkitRunnable() {
            int range = 2;

            @Override
            public void run() {
                for (int i = 0; i < 360; i+= 1) {
                    Location locationOnCircle = CheckLocationTools.getClosestAirBlockLocation(MathTools.locationOnCircle(player.getLocation(), range, i, world));

                    if (locationOnCircle != null) {
                        locationOnCircle.add(0, -1, 0);

                        Block block = world.getBlockAt(locationOnCircle);

                        if (rings.stream().noneMatch(b -> b.getX() == block.getX() && b.getZ() == block.getZ())) {
                            rings.add(block);

                            BlockData blockData = block.getBlockData();
                            block.setType(Material.AIR);

                            FallingBlock fallingBlock = world.spawnFallingBlock(block.getLocation().add(0.5, 0, 0.5), blockData);
                            fallingBlock.setDropItem(true);
                            fallingBlock.setVelocity(velocity);

                            double amountY = (fallDistance - range - 2) / 10;
                            if (amountY < 0.4) {
                                amountY = 0.4;
                            }
                            NearbyEntityTools.damageNearbyEntities(player, block.getLocation(), 0, 1, 2, 1, new Vector(0, amountY, 0), potionEffects);
                        }
                    }
                }

                range++;
                if (range > fallDistance - 2) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }

    /**
     * <b>MOVE 4: Earth Spikes</b>
     * <p>
     *     Summons an array of spikes out of the ground in the players looking direction.
     *     Entities hurt get mining fatigue for a brief moment.
     *     <ul>
     *         <li><b>Damage:</b> 5</li>
     *         <li><b>PotionEffect:</b> Mining fatigue (duration: 5s, amplifier: 2)</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move4(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = player.getLocation();
            Vector direction = location.getDirection();
            direction.setY(0);

            float yaw = location.getYaw();
            if (yaw > -25 && yaw < 25) {
                spikeWave(location, false, true, true, player);
            } else if (yaw >= 25 && yaw < 65) {
                spikeWave(location, false, true, false, player);
            } else if (yaw >= 65 && yaw < 115) {
                spikeWave(location, true, false, true, player);
            } else if (yaw >= 115 && yaw < 155) {
                spikeWave(location, false, false, false, player);
            } else if (yaw < -155 || yaw > 155) {
                spikeWave(location, false, false, true, player);
            } else if (yaw <= -25 && yaw > -65) {
                spikeWave(location, true, true, false, player);
            } else if (yaw <= -65 && yaw > -115) {
                spikeWave(location, true, true, true, player);
            } else {
                spikeWave(location, true, false, false, player);
            }
        };
    }

    /**
     * <b>MOVE 5: Stomp</b>
     * <p>
     *     Create an underground shockwave that damages entities along its way
     *     <ul>
     *         <li><b>Damage:</b> 7</li>
     *         <li><b>Knockup:</b> 0.5</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move5(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
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
                    Location blockLocation = CheckLocationTools.getClosestAirBlockLocation(location);

                    if (blockLocation != null) {
                        blockLocation.add(0, -1, 0);

                        world.spawnParticle(Particle.SMOKE_LARGE, blockLocation, 0, 0, -0.5, 0);

                        NearbyEntityTools.damageNearbyEntities(player, blockLocation, 7, 1, 1, 1, new Vector(0, 0.5, 0));
                    }

                    counter++;
                    if (counter >= 50) {
                        this.cancel();
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }

    /**
     * <b>MOVE 6: Rock Throw</b>
     * <p>
     *     Select a block by executing this move and aiming at a block<br>
     *     Multiple blocks can be selected by doing this move multiple times<br>
     *     The selected blocks will fly up<br>
     * </p>
     * <p>
     *     By crouching and executing this move, the blocks will be launched in your looking direction<br>
     *     An entity hurt by this attack will get a bit of knockback<br>
     *     <ul>
     *         <li><b>Damage:</b> 4</li>
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

            // Select blocks to shoot
            if (!player.isSneaking()) {
                Block block = player.getTargetBlockExact(80, FluidCollisionMode.NEVER);

                if (block != null && block.getType().isSolid()) {
                    FallingBlock fallingBlock = world.spawnFallingBlock(block.getLocation().add(0.5, 0, 0.5), block.getBlockData());

                    Vector velocity = new Vector(0, 0.8, 0);
                    if (activePlayer.isMove8active()) {
                        velocity.multiply(2);
                    }
                    fallingBlock.setVelocity(velocity);
                    fallingBlock.setDropItem(false);

                    activePlayer.addLocationMaterialMapping(block.getLocation(), block.getType());
                    block.setType(Material.AIR);

                    activePlayer.getMove6FallingBlocks().add(fallingBlock);
                }
            }

            // Shoot selected blocks in looking direction
            else {
                Vector direction = player.getLocation().getDirection();
                for (FallingBlock fallingBlock : activePlayer.getMove6FallingBlocks()) {
                    fallingBlock.setVelocity(direction.clone().multiply(3));
                    activePlayer.getMove6LaunchedFallingBlocks().add(fallingBlock);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!fallingBlock.isDead()) {
                                NearbyEntityTools.damageNearbyEntities(player, fallingBlock.getLocation(), 4, 1.5, 1.5, 1.5, direction);
                            } else {
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
                }

                activePlayer.getMove6FallingBlocks().clear();
            }
        };
    }

    /**
     * <b>MOVE 7: Earth Wave</b>
     * <p>
     *     Creates an earth wave in the looking direction of the player<br>
     *     <ul>
     *         <li><b>Damage:</b> 0</li>
     *         <li><b>PotionEffect: </b> Slowness (duration: 5s, amplifier: 1)</li>
     *         <li><b>Knockup: </b> 1</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move7(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = player.getLocation();
            float yaw = location.getYaw();
            if (yaw > -25 && yaw < 25) {
                earthWaveNew(location, false, true, true, activePlayer);
            } else if (yaw >= 25 && yaw < 65) {
                earthWaveNew(location, false, false, false, activePlayer);
            } else if (yaw >= 65 && yaw < 115) {
                earthWaveNew(location, true, false, true, activePlayer);
            } else if (yaw >= 115 && yaw < 155) {
                earthWaveNew(location, true, false, false, activePlayer);
            } else if (yaw < -155 || yaw > 155) {
                earthWaveNew(location, false, false, true, activePlayer);
            } else if (yaw <= -25 && yaw > -65) {
                earthWaveNew(location, true, true, false, activePlayer);
            } else if (yaw <= -65 && yaw > -115) {
                earthWaveNew(location, true, true, true, activePlayer);
            } else {
                earthWaveNew(location, false, true, false, activePlayer);
            }
        };
    }

    /**
     * <b>ULTIMATE: Flying Rock</b>
     * <p>
     *     The player flies up and a set of random blocks in the close range fly up to<br>
     *     These blocks will form into a flying island<br>
     * </p>
     * <p>
     *     When the move is activated again, the rock will be launched at the ground<br>
     *     This wil create an Earth Wave in all 8 directions<br>
     *     <ul>
     *         <li><b>Duration:</b> 1min</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     * @see EarthbendingStone#move7(ActivePlayer)
     * @see FlyingPlatform
     */
    public static Runnable move8(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();

            // Create platform
            if (!activePlayer.isMove8active()) {
                if (!player.isInWater() || world.getBlockAt(player.getLocation()).getType() == Material.LAVA) {

                    Vector startVelocity = new Vector(0, 1, 0);
                    player.setVelocity(startVelocity.multiply(2));

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Location location = player.getLocation().add(0, -1, 0);
                            player.setAllowFlight(true);
                            List<FallingBlock> platform = new ArrayList<>();

                            List<Location> launchLocations = new ArrayList<>();
                            for (int i = 0; i < 46; i++) {
                                Location locationInCircle = new Location(world,
                                        location.getBlockX() + StaticVariables.random.nextInt(40) - 20,
                                        location.getBlockY(),
                                        location.getBlockZ() + StaticVariables.random.nextInt(40) - 20
                                );
                                Location closestLocation = CheckLocationTools.getClosestAirBlockLocation(locationInCircle);
                                if (closestLocation == null) {
                                    closestLocation = locationInCircle;
                                }

                                launchLocations.add(closestLocation.add(0, -1, 0));
                            }

                            startVelocity.multiply(0.8);
                            for (Location launchLocation : launchLocations) {
                                Block block = world.getBlockAt(launchLocation);
                                Material material = block.getType();

                                activePlayer.addLocationMaterialMapping(launchLocation, material);

                                if (!material.isSolid()) {
                                    material = Material.STONE;
                                }

                                FallingBlock fallingBlock = world.spawnFallingBlock(launchLocation.clone().add(0.5, 0, 0.5), material.createBlockData());
                                fallingBlock.setDropItem(false);
                                fallingBlock.setVelocity(startVelocity);
                                block.setType(Material.AIR);
                                platform.add(fallingBlock);
                            }

                            activePlayer.setMove8FallingBlocks(platform);

                            // Make player fly
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    player.setAllowFlight(true);
                                    player.setFlying(true);
                                }
                            }.runTaskLater(StaticVariables.plugin, 10L);


                            // Platform of earth beneath the player
                            activePlayer.setMovesEnabled(false);
                            new FlyingPlatform(true, 60, 0.1, activePlayer, player, platform).runTaskTimer(StaticVariables.plugin, 20L, 1L);
                            new FlyingPlatform(activePlayer, player, platform).runTaskTimer(StaticVariables.plugin, 80L, 1L);
                        }
                    }.runTaskLater(StaticVariables.plugin, 10L);
                }
            }

            // Launch platform down
            else {
                move8launch(activePlayer);
            }
        };
    }
}
