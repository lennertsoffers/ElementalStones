package com.lennertsoffers.elementalstones.customClasses.models;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Location;

public class OffsetParticleLocation {
    private final double x;
    private final double y;
    private final double z;

    public OffsetParticleLocation(Location location, double offset) {
        this.x = location.getX() + StaticVariables.random.nextGaussian() / 100 * offset;
        this.y = location.getY() + StaticVariables.random.nextGaussian() / 100 * offset;
        this.z = location.getZ() + StaticVariables.random.nextGaussian() / 100 * offset;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
