package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FireBall extends BukkitRunnable {

    private final Player player;
    private final World world;
    private int amountOfPokes;
    private int amountOfTicks = 0;

    public FireBall(Player player, World world) {
        this.player = player;
        this.world = world;
        this.amountOfPokes = 6;
    }

    @Override
    public void run() {

        Location center = this.getFireBallLocation();
        double nominator = 60 / (float) amountOfPokes;

        for (int i = 0; i < 10 * amountOfPokes; i++) {
            double x = center.getX() + StaticVariables.random.nextGaussian() / nominator;
            double y = center.getY() + StaticVariables.random.nextGaussian() / nominator;
            double z = center.getZ() + StaticVariables.random.nextGaussian() / nominator;
            world.spawnParticle(Particle.FLAME, x, y, z, 0);
        }

        this.amountOfTicks++;
        if (this.amountOfTicks > 400) {
            this.cancel();
        }
    }

    private Location getFireBallLocation() {
        Location location = this.player.getLocation();
        Vector direction = location.getDirection();

        location.add(direction.clone().multiply(2)).add(0, 2, 0).add(direction.clone().rotateAroundY(90));

        return location;
    }

    public boolean poke(ActivePlayer activePlayer) {
        boolean hasPokes = true;
        this.amountOfPokes--;
        if (this.amountOfPokes == 0) {
            this.cancel();
            activePlayer.setFireBall(null);
            hasPokes = false;
        }

        Location location = this.getFireBallLocation();
        Vector direction = location.getDirection();

        new BukkitRunnable() {
            int amountOfTicks = 0;

            @Override
            public void run() {
                if (
                        amountOfTicks > 20 ||
                        (location.getBlock().getType() != Material.AIR && !CheckLocationTools.isFoliage(location)) ||
                        NearbyEntityTools.damageNearbyEntities(player, location, 2, 0.3, 0.3, 0.3, livingEntity -> livingEntity.setFireTicks(20))
                ) {
                    this.cancel();
                }


                for (int i = 0; i < 10; i++) {
                    double x = location.getX() + StaticVariables.random.nextGaussian() / 20;
                    double y = location.getY() + StaticVariables.random.nextGaussian() / 20;
                    double z = location.getZ() + StaticVariables.random.nextGaussian() / 20;
                    world.spawnParticle(Particle.FLAME, x, y, z, 0);
                }
                location.add(direction);

                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

        return hasPokes;
    }
}
