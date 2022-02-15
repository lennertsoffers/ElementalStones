package com.lennertsoffers.elementalstones.moves.earthMoves.basic;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Earthquake extends Move {

    public Earthquake(ActivePlayer activePlayer) {
        super(activePlayer, "Earthquake", "earth_stone", "default", 2);
    }

    @Override
    public void useMove() {
        Location middlePoint = this.getPlayer().getLocation();
        World world = this.getPlayer().getWorld();
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

                    NearbyEntityTools.damageNearbyEntities(getPlayer(), location.clone().add(0, 1, 0), 1, 1, 1, 1, potionEffects);

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
    }
}
