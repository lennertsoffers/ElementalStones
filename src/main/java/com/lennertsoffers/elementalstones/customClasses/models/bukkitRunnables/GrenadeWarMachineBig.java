package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class GrenadeWarMachineBig extends Grenade {

    public GrenadeWarMachineBig(Player player, Particle particle, Particle.DustOptions dustOptions) {
        super(player, particle, dustOptions);
    }

    @Override
    public void explode(Location impactLocation) {
        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.GRAY, 1);
        impactLocation.add(0, -0.6, 0);

        for (int i = 0; i < 6; i++) {
            Location transformedLocation = impactLocation.clone();
            transformedLocation.setPitch(-StaticVariables.random.nextInt(40) - 50);
            transformedLocation.setYaw(StaticVariables.random.nextInt(360) - 180);

            Grenade smallGrenade = new GrenadeWarMachineSmall(this.getPlayer(), Particle.TOTEM, dustOptions, transformedLocation);
            smallGrenade.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        }
    }
}
