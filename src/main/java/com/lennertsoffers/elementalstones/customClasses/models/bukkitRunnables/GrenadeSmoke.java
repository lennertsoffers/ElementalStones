package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GrenadeSmoke extends Grenade {

    public GrenadeSmoke(Player player, Particle particle, Particle.DustOptions dustOptions) {
        super(player, 15, particle, dustOptions);
    }

    @Override
    public void explode(Location impactLocation) {
        new BukkitRunnable() {
            int loops = 0;

            @Override
            public void run() {
                for (int i = 1; i < 400; i++) {
                    double offsetBomb = 8;
                    if (loops == 3) {
                        offsetBomb = 1;
                    } else if (loops == 5) {
                        offsetBomb = 0.5;
                    }
                    double offsetX = impactLocation.getX() + StaticVariables.random.nextGaussian() / offsetBomb;
                    double offsetY = impactLocation.getY() + Math.abs(StaticVariables.random.nextGaussian()) / offsetBomb + 0.3;
                    double offsetZ = impactLocation.getZ() + StaticVariables.random.nextGaussian() / offsetBomb;
                    GrenadeSmoke.super.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, offsetX, offsetY, offsetZ, 0, StaticVariables.random.nextGaussian() / 40, Math.abs(StaticVariables.random.nextGaussian() / 40), StaticVariables.random.nextGaussian() / 40);
                }

                loops++;
                if (loops > 20) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 20L);
    }
}
