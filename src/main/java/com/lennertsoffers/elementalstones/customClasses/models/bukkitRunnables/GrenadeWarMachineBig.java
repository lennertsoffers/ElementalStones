package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.FireworkTools;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class GrenadeWarMachineBig extends Grenade {

    public GrenadeWarMachineBig(Player player, int power, Particle particle, Particle.DustOptions dustOptions) {
        super(player, power, particle, dustOptions);
    }

    @Override
    public void explode(Location impactLocation) {
        Firework firework = FireworkTools.setMeta((Firework) super.getWorld().spawnEntity(impactLocation, EntityType.FIREWORK), 1, FireworkEffect.Type.BURST, Arrays.asList(Color.fromRGB(139, 0, 0), Color.fromRGB(50, 50, 50)), Arrays.asList(Color.fromRGB(155, 135, 12), Color.fromRGB(200, 0, 0)), false, true);
        firework.detonate();
        new BukkitRunnable() {
            @Override
            public void run() {
                getWorld().createExplosion(impactLocation, 4, true, true, getPlayer());
            }
        }.runTaskLater(StaticVariables.plugin, 6L);

        impactLocation.add(0, -0.4, 0);

        for (int i = 0; i < 15; i++) {
            Location transformedLocation = impactLocation.clone();
            transformedLocation.setPitch(-StaticVariables.random.nextInt(30) - 50);
            transformedLocation.setYaw(StaticVariables.random.nextInt(360) - 180);

            Grenade smallGrenade = new GrenadeWarMachineSmall(this.getPlayer(), Particle.TOTEM, null, transformedLocation);
            smallGrenade.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        }
    }
}
