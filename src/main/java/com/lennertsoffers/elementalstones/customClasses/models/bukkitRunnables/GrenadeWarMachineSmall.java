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
import org.bukkit.util.Vector;

import java.util.Arrays;

public class GrenadeWarMachineSmall extends Grenade {

    public GrenadeWarMachineSmall(Player player, Particle particle, Particle.DustOptions dustOptions, Location impactLocation) {
        super(player, particle, dustOptions, StaticVariables.random.nextInt(3) + 10, impactLocation, new Vector(0, 0, 0), 10, 20);
    }

    @Override
    public void explode(Location impactLocation) {
        Firework firework = FireworkTools.setMeta((Firework) super.getWorld().spawnEntity(impactLocation, EntityType.FIREWORK), 1, FireworkEffect.Type.BURST, Arrays.asList(Color.fromRGB(139, 0, 0), Color.fromRGB(50, 50, 50)), Arrays.asList(Color.fromRGB(155, 135, 12), Color.fromRGB(200, 0, 0)), false, true);
        firework.detonate();

        new BukkitRunnable() {
            @Override
            public void run() {
                getWorld().createExplosion(impactLocation, 3, true, true, getPlayer());
            }
        }.runTaskLater(StaticVariables.plugin, 6L);
    }
}
