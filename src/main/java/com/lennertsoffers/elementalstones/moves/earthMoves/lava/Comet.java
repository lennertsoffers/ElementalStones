package com.lennertsoffers.elementalstones.moves.earthMoves.lava;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class Comet extends Move {

    public Comet(ActivePlayer activePlayer) {
        super(activePlayer, "Comet", "earth_stone", "lava_stone", 6);
    }

    @Override
    public void useMove() {
        ActivePlayer activePlayer = this.getActivePlayer();
        Player player = this.getPlayer();
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

                    com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.Comet comet = new com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.Comet(activePlayer, startLocation, endLocation);
                    comet.runTaskTimer(StaticVariables.plugin, 0L, 1L);
                }
            }.runTaskLater(StaticVariables.plugin, 40L);
        }
    }

    private void spawnTriangle(Location center, List<Integer> angles) {
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

            this.setCooldown();
        }
    }
}
