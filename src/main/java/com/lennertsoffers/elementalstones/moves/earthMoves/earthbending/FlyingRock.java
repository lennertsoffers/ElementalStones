package com.lennertsoffers.elementalstones.moves.earthMoves.earthbending;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FlyingPlatform;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class FlyingRock extends Move {

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
     * @see FlyingPlatform
     */
    public FlyingRock(ActivePlayer activePlayer) {
        super(activePlayer, "Flying Rock", "earth_stone", "earthbending_stone", 8);
    }

    @Override
    public void useMove() {
        ActivePlayer activePlayer = this.getActivePlayer();
        Player player = this.getPlayer();
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
            launch(activePlayer);
            this.setCooldown();
        }
    }

    public static void launch(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();

        Vector platformFallVelocity = new Vector(0, -1, 0);
        for (FallingBlock fallingBlock : activePlayer.getMove8FallingBlocks()) {
            fallingBlock.setVelocity(platformFallVelocity);
            player.setVelocity(platformFallVelocity);
        }
        end(activePlayer);

        Location location = activePlayer.getPlayer().getLocation();
        EarthWave.earthWaveNew(location, true, true, true, activePlayer);
        EarthWave.earthWaveNew(location, true, false, true, activePlayer);
        EarthWave.earthWaveNew(location, false, true, true, activePlayer);
        EarthWave.earthWaveNew(location, false, false, true, activePlayer);

        new BukkitRunnable() {
            @Override
            public void run() {
                EarthWave.earthWaveNew(location, true, true, false, activePlayer);
                EarthWave.earthWaveNew(location, true, false, false, activePlayer);
                EarthWave.earthWaveNew(location, false, true, false, activePlayer);
                EarthWave.earthWaveNew(location, false, false, false, activePlayer);
            }
        }.runTaskLater(StaticVariables.plugin, 13L);
    }

    public static void end(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        player.setFlying(false);
        player.setAllowFlight(false);
        new BukkitRunnable() {
            @Override
            public void run() {
                activePlayer.setMove8active(false);
            }
        }.runTaskLater(StaticVariables.plugin, 40L);        activePlayer.setMovesEnabled(true);
        activePlayer.getMoveController().getMoves().forEach(move -> {
            if (move instanceof FlyingRock) {
                move.setCooldown();
            }
        });
    }
}
