package com.lennertsoffers.elementalstones.moves.earthMoves.earthbending;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class EarthWave extends Move {

    public EarthWave(ActivePlayer activePlayer) {
        super(activePlayer, "Earth Wave", "earth_stone", "earthbending_stone", 7);
    }

    @Override
    public void useMove() {
        Location location = this.getPlayer().getLocation();
        float yaw = location.getYaw();
        if (yaw > -25 && yaw < 25) {
            earthWaveNew(location, false, true, true, this.getActivePlayer());
        } else if (yaw >= 25 && yaw < 65) {
            earthWaveNew(location, false, false, false, this.getActivePlayer());
        } else if (yaw >= 65 && yaw < 115) {
            earthWaveNew(location, true, false, true, this.getActivePlayer());
        } else if (yaw >= 115 && yaw < 155) {
            earthWaveNew(location, true, false, false, this.getActivePlayer());
        } else if (yaw < -155 || yaw > 155) {
            earthWaveNew(location, false, false, true, this.getActivePlayer());
        } else if (yaw <= -25 && yaw > -65) {
            earthWaveNew(location, true, true, false, this.getActivePlayer());
        } else if (yaw <= -65 && yaw > -115) {
            earthWaveNew(location, true, true, true, this.getActivePlayer());
        } else {
            earthWaveNew(location, false, true, false, this.getActivePlayer());
        }
    }

    public static void earthWaveNew(Location location, boolean var0, boolean var1, boolean perpendicular, ActivePlayer activePlayer) {
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
}
