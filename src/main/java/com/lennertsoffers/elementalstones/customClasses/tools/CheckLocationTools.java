package com.lennertsoffers.elementalstones.customClasses.tools;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CheckLocationTools {
    public static boolean checkEntityCollision(Entity entity, Location blockLocation) {
        Location playerLocation = entity.getLocation();

        double diffX = Math.abs(playerLocation.getX() - blockLocation.getX());
        double diffZ = Math.abs(playerLocation.getZ() - blockLocation.getZ());

        System.out.println(diffX);
        System.out.println(diffZ);

        return diffX < entity.getWidth() || diffZ < entity.getWidth();
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

    public static Location getClosestAirBlockLocation(Location location) {
        World world = location.getWorld();
        if (world != null) {
            Location startLocation = location.clone();
            int difference = 0;

            while (Math.abs(difference) <= 50) {
                Location positiveLoopLocation = startLocation.clone().add(0, difference, 0);
                Location negativeLoopLocation = startLocation.clone().add(0, -difference, 0);

                if (
                        (world.getBlockAt(positiveLoopLocation).getType() == Material.AIR || isFoliage(positiveLoopLocation)) &&
                        isSolidBlock(world.getBlockAt(positiveLoopLocation.clone().add(0, -1, 0)).getType())
                ) {
                    return positiveLoopLocation;
                } else if (
                        (world.getBlockAt(negativeLoopLocation).getType() == Material.AIR || isFoliage(negativeLoopLocation)) &&
                        isSolidBlock(world.getBlockAt(negativeLoopLocation.clone().add(0, -1, 0)).getType())
                ) {
                    return negativeLoopLocation;
                }

                difference++;
            }
        }

        return null;
    }

    public static boolean isFoliage(Location location) {
        Block block = location.getBlock();

        return block.getType() == Material.DANDELION ||
                block.getType() == Material.POPPY ||
                block.getType() == Material.BLUE_ORCHID ||
                block.getType() == Material.ALLIUM ||
                block.getType() == Material.AZURE_BLUET ||
                block.getType() == Material.ORANGE_TULIP ||
                block.getType() == Material.PINK_TULIP ||
                block.getType() == Material.RED_TULIP ||
                block.getType() == Material.WHITE_TULIP ||
                block.getType() == Material.OXEYE_DAISY ||
                block.getType() == Material.CORNFLOWER ||
                block.getType() == Material.LILY_OF_THE_VALLEY ||
                block.getType() == Material.WITHER_ROSE ||
                block.getType() == Material.SUNFLOWER ||
                block.getType() == Material.LILAC ||
                block.getType() == Material.ROSE_BUSH ||
                block.getType() == Material.PEONY ||
                block.getType() == Material.DEAD_BUSH ||
                block.getType() == Material.SWEET_BERRY_BUSH ||
                block.getType() == Material.GRASS ||
                block.getType() == Material.TALL_GRASS ||
                block.getType() == Material.FERN;
    }

    public static boolean isSolidBlock(Block block) {
        Material material = block.getType();
        return material.isSolid() && material.isBlock() && material.isOccluding();
    }

    public static boolean isSolidBlock(Material material) {
        return material.isSolid() && material.isBlock() && material.isOccluding();
    }
}
