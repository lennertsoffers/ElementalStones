package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class GrenadeWarMachineSmall extends Grenade {

    public GrenadeWarMachineSmall(Player player, Particle particle, Particle.DustOptions dustOptions, Location impactLocation) {
        super(player, particle, dustOptions, 10, impactLocation, new Vector(0, 0, 0));
    }

    @Override
    public void explode(Location impactLocation) {

    }
}
