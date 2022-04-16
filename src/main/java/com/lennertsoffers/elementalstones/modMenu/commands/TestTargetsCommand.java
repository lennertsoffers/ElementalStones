package com.lennertsoffers.elementalstones.modMenu.commands;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

public class TestTargetsCommand extends PlayerCommand {
    public TestTargetsCommand(String command) {
        super(command);
    }

    @Override
    boolean execute() {
        World world = this.getPlayer().getWorld();
        Location location = this.getPlayer().getLocation().add(0, 1, 0);

        for (int i = 0; i < 100; i++) {
            Location spawnLocation = location.clone();
            spawnLocation.setX(location.getX() + StaticVariables.random.nextGaussian() * 10);
            spawnLocation.setZ(location.getZ() + StaticVariables.random.nextGaussian() * 10);

            world.spawnEntity(spawnLocation, EntityType.COW);
        }

        return true;
    }
}
