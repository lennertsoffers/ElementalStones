package com.lennertsoffers.elementalstones.moves.earthMoves.earthbending;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class EarthSpikes extends Move {

    /**
     * <b>MOVE 4: Earth Spikes</b>
     * <p>
     *     Summons an array of spikes out of the ground in the players looking direction.
     *     Entities hurt get mining fatigue for a brief moment.
     *     <ul>
     *         <li><b>Damage:</b> 5</li>
     *         <li><b>Range:</b> 6</li>
     *         <li><b>PotionEffect:</b> Mining fatigue (duration: 5s, amplifier: 2)</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     */
    public EarthSpikes(ActivePlayer activePlayer) {
        super(activePlayer, "Earth Spikes", "earth_stone", "earthbending_stone", 4);
    }

    @Override
    public void useMove() {
        Location location = this.getPlayer().getLocation();
        Vector direction = location.getDirection();
        direction.setY(0);

        float yaw = location.getYaw();
        if (yaw > -25 && yaw < 25) {
            this.spikeWave(location, false, true, true);
        } else if (yaw >= 25 && yaw < 65) {
            this.spikeWave(location, false, true, false);
        } else if (yaw >= 65 && yaw < 115) {
            this.spikeWave(location, true, false, true);
        } else if (yaw >= 115 && yaw < 155) {
            this.spikeWave(location, false, false, false);
        } else if (yaw < -155 || yaw > 155) {
            this.spikeWave(location, false, false, true);
        } else if (yaw <= -25 && yaw > -65) {
            this.spikeWave(location, true, true, false);
        } else if (yaw <= -65 && yaw > -115) {
            this.spikeWave(location, true, true, true);
        } else {
            this.spikeWave(location, true, false, false);
        }

        this.setCooldown();
    }

    private void spikeWave(Location location, boolean var0, boolean var1, boolean perpendicular) {
        Player player = this.getPlayer();
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
}
