package com.lennertsoffers.elementalstones.moves.waterMoves.waterbending;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class AquaRing extends Move {

    private final ArrayList<Location> waterBlockLocations = new ArrayList<>();

    public AquaRing(ActivePlayer activePlayer) {
        super(activePlayer, "Aqua Ring", "water_stone", "waterbending_stone", 7);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();
        Location location = player.getLocation();
        Vector direction = location.getDirection().setY(0);
        ArrayList<Integer> ignoreAnglesB = new ArrayList<>();
        ArrayList<Integer> ignoreAnglesM = new ArrayList<>();
        ArrayList<Integer> ignoreAnglesT = new ArrayList<>();
        
        new BukkitRunnable() {
            int amountOfTicks = 1;

            @Override
            public void run() {

                replaceWater();
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
                        knockbackEntities(waterBlockLocationB);
                    }
                    if (!ignoreAnglesM.contains(i)) {
                        waterBlockLocations.add(waterBlockLocationM);
                        waterBlockLocationM.getBlock().setType(Material.WATER);
                        knockbackEntities(waterBlockLocationM);
                    }
                    if (!ignoreAnglesT.contains(i)) {
                        waterBlockLocations.add(waterBlockLocationT);
                        waterBlockLocationT.getBlock().setType(Material.WATER);
                        knockbackEntities(waterBlockLocationT);
                    }
                }

                if (amountOfTicks > 20) {
                    this.cancel();
                    replaceWater();
                    waterBlockLocations.clear();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

        this.setCooldown();
    }

    private void knockbackEntities(Location location) {
        Player player = this.getPlayer();
        
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
    
    private void replaceWater() {
        for (Location waterBlockLocation : this.waterBlockLocations) {
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
