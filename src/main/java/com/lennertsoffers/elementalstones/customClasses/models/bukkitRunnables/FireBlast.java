package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FireBlast extends BukkitRunnable {

    private final Player player;
    private final World world;
    private double distance = 1.5;
    private double height = 0;
    private double heightAddition = -0.1;

    public FireBlast(Player player, World world) {
        this.player = player;
        this.world = world;
    }

    @Override
    public void run() {
        Location location = this.player.getLocation().add(0, 0.5, 0);

        for (int i = 0; i <= 360; i += 5) {
            Location particleLocation = MathTools.locationOnCircle(location, this.distance, i, this.world).add(0, height, 0);
            world.spawnParticle(Particle.FLAME, particleLocation, 0);
        }

        this.height += this.heightAddition;


        this.distance += 0.4;
        if (this.distance > 10) {
            this.cancel();
        }
    }
}
