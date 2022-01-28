package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Comet extends BukkitRunnable {

    private final ActivePlayer activePlayer;
    private final Location startLocation;
    private final Location endLocation;
    private final World world;
    private final List<FallingBlock> comet = new ArrayList<>();
    private boolean endComet = false;


    public Comet(ActivePlayer activePlayer, Location startLocation, Location endLocation) {
        this.activePlayer = activePlayer;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.world = startLocation.getWorld();

        spawnComet();
    }

    @Override
    public void run() {
        List<Location> startLocationList = this.getCometLocations(this.startLocation);
        List<Location> endLocationList = this.getCometLocations(this.endLocation);

        for (int i = 0; i < startLocationList.size(); i++) {
            Location blockStartLocation = startLocationList.get(i);
            Location blockEndLocation = endLocationList.get(i);

            FallingBlock fallingBlock = comet.get(i);

            Vector velocity = new Vector(blockEndLocation.getX() - blockStartLocation.getX(), blockEndLocation.getY() - blockStartLocation.getY(), blockEndLocation.getZ() - blockStartLocation.getZ()).multiply(0.005);

            world.spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, fallingBlock.getLocation(), 0, velocity.getX(), velocity.getY(), velocity.getZ());
            fallingBlock.setVelocity(velocity.multiply(5));
        }

        if (this.endComet) {
            this.cancel();
            comet.forEach(Entity::remove);
        }
    }

    private List<Location> getCometLocations(Location middleLocation) {
        List<Location> locationList = new ArrayList<>();

        locationList.add(middleLocation.clone().add(0, 0, 2));
        locationList.add(middleLocation.clone().add(0, 1, 2));
        locationList.add(middleLocation.clone().add(0, -1, 2));
        locationList.add(middleLocation.clone().add(1, 0, 2));
        locationList.add(middleLocation.clone().add(-1, 0, 2));

        locationList.add(middleLocation.clone().add(0, 0, -2));
        locationList.add(middleLocation.clone().add(0, 1, -2));
        locationList.add(middleLocation.clone().add(0, -1, -2));
        locationList.add(middleLocation.clone().add(1, 0, -2));
        locationList.add(middleLocation.clone().add(-1, 0, -2));

        locationList.add(middleLocation.clone().add(2, 0, 0));
        locationList.add(middleLocation.clone().add(2, 1, 0));
        locationList.add(middleLocation.clone().add(2, -1, 0));
        locationList.add(middleLocation.clone().add(2, 0, 1));
        locationList.add(middleLocation.clone().add(2, 0, -1));

        locationList.add(middleLocation.clone().add(-2, 0, 0));
        locationList.add(middleLocation.clone().add(-2, 1, 0));
        locationList.add(middleLocation.clone().add(-2, -1, 0));
        locationList.add(middleLocation.clone().add(-2, 0, 1));
        locationList.add(middleLocation.clone().add(-2, 0, -1));

        locationList.add(middleLocation.clone().add(0, 2, 0));
        locationList.add(middleLocation.clone().add(0, 2, 1));
        locationList.add(middleLocation.clone().add(0, 2, -1));
        locationList.add(middleLocation.clone().add(1, 2, 0));
        locationList.add(middleLocation.clone().add(-1, 2, 0));

        locationList.add(middleLocation.clone().add(0, -2, 0));
        locationList.add(middleLocation.clone().add(0, -2, 1));
        locationList.add(middleLocation.clone().add(0, -2, -1));
        locationList.add(middleLocation.clone().add(1, -2, 0));
        locationList.add(middleLocation.clone().add(-1, -2, 0));

        locationList.add(middleLocation.clone().add(1, 1, 1));
        locationList.add(middleLocation.clone().add(-1, 1, 1));
        locationList.add(middleLocation.clone().add(1, 1, -1));
        locationList.add(middleLocation.clone().add(-1, 1, -1));

        locationList.add(middleLocation.clone().add(1, -1, 1));
        locationList.add(middleLocation.clone().add(-1, -1, 1));
        locationList.add(middleLocation.clone().add(1, -1, -1));
        locationList.add(middleLocation.clone().add(-1, -1, -1));

        return locationList;
    }

    private void spawnComet() {
        BlockData blockData = Material.MAGMA_BLOCK.createBlockData();
        List<Location> startLocationList = this.getCometLocations(this.startLocation);

        startLocationList.forEach(blockStartLocation -> {
            FallingBlock fallingBlock = this.world.spawnFallingBlock(blockStartLocation, blockData);
            this.comet.add(fallingBlock);
            this.activePlayer.getCometFallingBlocks().add(fallingBlock);
        });

        this.activePlayer.setComet(this);
    }

    public void endComet(Location impactLocation) {
        this.endComet = true;
        activePlayer.getCometFallingBlocks().clear();

        world.createExplosion(impactLocation, 5, true, true, activePlayer.getPlayer());

        int explosionScale = 1;
        new BukkitRunnable() {
            int amountOfTicks = 0;

            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    world.spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE,
                            impactLocation.getX() + StaticVariables.random.nextGaussian() * explosionScale,
                            impactLocation.getY() + Math.abs(StaticVariables.random.nextGaussian() * explosionScale),
                            impactLocation.getZ() + StaticVariables.random.nextGaussian() * explosionScale,
                            0,
                            StaticVariables.random.nextGaussian() / 8,
                            StaticVariables.random.nextGaussian() / 8,
                            StaticVariables.random.nextGaussian() / 8
                    );
                }

                amountOfTicks++;
                if (amountOfTicks > 80) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }
}
