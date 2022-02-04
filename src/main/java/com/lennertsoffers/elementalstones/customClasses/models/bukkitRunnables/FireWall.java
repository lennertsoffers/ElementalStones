package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class FireWall extends BukkitRunnable {

    private final Player player;
    private final World world;
    private final Location bottomLeftLocation;
    private final Vector wallDirection;
    private final Vector nullVelocity = new Vector(0, 0, 0);

    private static long endTime = -1;
    private static int amountOfWalls = 0;
    private static BukkitTask slowTask = null;
    private static final ArrayList<Location> wallLocations = new ArrayList<>();

    public FireWall(Player player, Location bottomLeftLocation, Vector wallDirection) {
        this.player = player;
        this.world = player.getWorld();
        this.bottomLeftLocation = bottomLeftLocation;
        this.wallDirection = wallDirection;

        if (endTime == -1) {
            endTime = System.currentTimeMillis();

            slowTask = new BukkitRunnable() {
                @Override
                public void run() {
                    wallLocations.forEach(location -> world.getNearbyEntities(location, 0.1, 1, 0.1).forEach(entity -> entity.setVelocity(nullVelocity)));
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        }

        if (amountOfWalls < 5) {
            amountOfWalls++;
        }
    }

    @Override
    public void run() {
        for (double i = 0; i < 2.0; i += 0.1) {
            wallLocations.add(bottomLeftLocation.clone().add(wallDirection.clone().multiply(i)).add(0, 1, 0));

            for (double j = 0; j < 2.0; j += 0.1) {
                this.world.spawnParticle(Particle.FLAME, bottomLeftLocation.clone().add(wallDirection.clone().multiply(i)).add(0, j, 0), 0);
            }
        }

        if (System.currentTimeMillis() > endTime) {
            this.cancel();

            if (slowTask != null) {
                slowTask.cancel();
                slowTask = null;
                amountOfWalls = 0;
                endTime = -1;
            }
        }
    }

    public static boolean canSpawnWall() {
        return amountOfWalls < 5;
    }
}
