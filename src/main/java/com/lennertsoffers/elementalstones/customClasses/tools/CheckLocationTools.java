package com.lennertsoffers.elementalstones.customClasses.tools;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Objects;

public class CheckLocationTools {
    public static boolean checkPlayerCollision(double player, double block) {
        return !(player > block + 1.3 || player < block - 0.3);
    }

    public static boolean locationAroundClear(Location location, World world) {
        return world.getBlockAt(location.add(0, 1, 0)).getType() == Material.AIR &&
                world.getBlockAt(location.add(1, 0, 0)).getType() == Material.AIR &&
                world.getBlockAt(location.add(0, 0, 1)).getType() == Material.AIR &&
                world.getBlockAt(location.add(-1, 0, 0)).getType() == Material.AIR &&
                world.getBlockAt(location.add(-1, 0, 0)).getType() == Material.AIR &&
                world.getBlockAt(location.add(0, 0, -1)).getType() == Material.AIR &&
                world.getBlockAt(location.add(0, 0, -1)).getType() == Material.AIR &&
                world.getBlockAt(location.add(1, 0, 0)).getType() == Material.AIR &&
                world.getBlockAt(location.add(1, 0, 0)).getType() == Material.AIR;
    }

    public static boolean lavaAroundPlayer(Location location) {
        World world = location.getWorld();
        location.add(1, 0, 1);
        for (int i = 1; i <= 9; i++) {
            if (Objects.requireNonNull(world).getBlockAt(location).getType() == Material.LAVA) {
                return true;
            }
            location.add(-1, 0, 0);
            if (i % 3 == 0) {
                location.add(3, 0, -1);
            }
        }
        return false;
    }
}
