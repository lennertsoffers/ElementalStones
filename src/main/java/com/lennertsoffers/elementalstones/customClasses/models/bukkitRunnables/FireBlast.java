package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FireBlast extends BukkitRunnable {

    private final Location center;
    private final Player player;
    private final World world;
    private double distance = 1;

    public FireBlast(Player player, World world) {
        this.player = player;
        this.center = player.getLocation().add(0, 0.5, 0);
        this.world = world;
    }

    @Override
    public void run() {
        for (int i = 0; i <= 360; i += 1) {
            Location particleLocation = MathTools.locationOnCircle(this.center, this.distance, i).add(0, Math.cos(2 * this.distance) / 4, 0);
            world.spawnParticle(Particle.FLAME, particleLocation, 0);

            NearbyEntityTools.damageNearbyEntities(this.player, particleLocation, 3, 0.1, 0.1, 0.1, livingEntity -> {
                Location entityLocation = livingEntity.getLocation();
                Vector velocity = new Vector(entityLocation.getX() - this.center.getX(), 0.2, entityLocation.getZ() - this.center.getZ());
                velocity.multiply(1 / velocity.length()).multiply(3);
                livingEntity.setVelocity(velocity);
                livingEntity.setFireTicks(livingEntity.getFireTicks() + 60);
            });
        }

        this.distance += 0.2;
        if (this.distance > 10) {
            this.cancel();
        }
    }
}
