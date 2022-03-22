package com.lennertsoffers.elementalstones.consumables.effects;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.OffsetParticleLocation;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.PotionEffectTools;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class MysteryPotionEffect implements ConsumableEffect {

    @Override
    public void playEffect(Player player) {
        World world = player.getWorld();
        Location location = player.getLocation();
        Vector direction = location.getDirection();

        player.addPotionEffect(PotionEffectTools.randomPotionEffect());

        new BukkitRunnable() {
            int angle = 90;
            int amountOfTicks = 0;

            @Override
            public void run() {
                List<Location> particleLocations = new ArrayList<>();
                particleLocations.add(location.clone().add(direction.clone().rotateAroundY(angle)));
                particleLocations.add(location.clone().add(direction.clone().rotateAroundY(MathTools.mirrorAngle(angle))));

                angle = MathTools.incrementAngle(angle, 10);

                for (Location particleLocation : particleLocations) {
                    particleLocation.add(0, amountOfTicks / 10F, 0);
                    OffsetParticleLocation opl = new OffsetParticleLocation(location, 3);

                    world.spawnParticle(Particle.REDSTONE, opl.getLocation(), 0, new Particle.DustOptions(Color.fromRGB(StaticVariables.random.nextInt(255), StaticVariables.random.nextInt(255), StaticVariables.random.nextInt(255)), 2F));
                }

                amountOfTicks++;
                if (amountOfTicks > 60) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }
}
