package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import javax.jws.Oneway;
import java.util.ArrayList;

public class FireWall extends BukkitRunnable {

    private final Player player;
    private final World world;
    private final Location bottomLeftLocation;
    private final Vector wallDirection;
    private final ArrayList<Location> nullLocations = new ArrayList<>();

    private int amountOfTicks = 0;

    public FireWall(Player player, Location bottomLeftLocation, Vector wallDirection) {
        this.player = player;
        this.world = player.getWorld();
        this.bottomLeftLocation = bottomLeftLocation;
        this.wallDirection = wallDirection;

    }

    @Override
    public void run() {
        this.world.spawnParticle(Particle.SOUL_FIRE_FLAME, bottomLeftLocation, 0);
        for (double i = 0; i < 2.0; i += 0.1) {
            for (double j = 0; j < 2.0; j += 0.1) {
                this.world.spawnParticle(Particle.FLAME, bottomLeftLocation.clone().add(wallDirection.clone().multiply(i)).add(0, j, 0), 0);
            }
        }

        this.amountOfTicks += 10;
        if (this.amountOfTicks > 200) {
            this.cancel();
        }
    }
}
