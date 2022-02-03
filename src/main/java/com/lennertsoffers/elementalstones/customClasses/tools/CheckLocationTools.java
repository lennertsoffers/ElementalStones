package com.lennertsoffers.elementalstones.customClasses.tools;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
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

        if (world != null) {
            location.add(1, 0, 1);
            for (int i = 1; i <= 9; i++) {
                if (world.getBlockAt(location).getType() == Material.LAVA) {
                    return true;
                }
                location.add(-1, 0, 0);
                if (i % 3 == 0) {
                    location.add(3, 0, -1);
                }
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
        return isFoliage(block.getType());
    }

    public static boolean isFoliage(Material material) {
        return material == Material.DANDELION ||
                material == Material.POPPY ||
                material == Material.BLUE_ORCHID ||
                material == Material.ALLIUM ||
                material == Material.AZURE_BLUET ||
                material == Material.ORANGE_TULIP ||
                material == Material.PINK_TULIP ||
                material == Material.RED_TULIP ||
                material == Material.WHITE_TULIP ||
                material == Material.OXEYE_DAISY ||
                material == Material.CORNFLOWER ||
                material == Material.LILY_OF_THE_VALLEY ||
                material == Material.WITHER_ROSE ||
                material == Material.SUNFLOWER ||
                material == Material.LILAC ||
                material == Material.ROSE_BUSH ||
                material == Material.PEONY ||
                material == Material.DEAD_BUSH ||
                material == Material.SWEET_BERRY_BUSH ||
                material == Material.GRASS ||
                material == Material.TALL_GRASS ||
                material == Material.FERN;
    }

    public static boolean isSolidBlock(Block block) {
        Material material = block.getType();
        return material.isSolid() && material.isBlock() && material.isOccluding();
    }

    public static boolean isSolidBlock(Material material) {
        return material.isSolid() && material.isBlock() && material.isOccluding();
    }

    public static List<Location> getSphere5Locations(Location middleLocation) {
        List<Location> locationList = new ArrayList<>();

        locationList.add(middleLocation.clone().add(0, 0, 2));
        locationList.add(middleLocation.clone().add(0, 1, 2));
        locationList.add(middleLocation.clone().add(0, -1, 2));
        locationList.add(middleLocation.clone().add(1, 0, 2));
        locationList.add(middleLocation.clone().add(-1, 0, 2));

        locationList.add(middleLocation.clone().add(0, 0, -2));
        locationList.add(middleLocation.clone().add(0, 1, -2));
        locationList.add(middleLocation.clone().add(0, -1, -2));
        locationList.add(middleLocation.clone().add(1, 0, -2));
        locationList.add(middleLocation.clone().add(-1, 0, -2));

        locationList.add(middleLocation.clone().add(2, 0, 0));
        locationList.add(middleLocation.clone().add(2, 1, 0));
        locationList.add(middleLocation.clone().add(2, -1, 0));
        locationList.add(middleLocation.clone().add(2, 0, 1));
        locationList.add(middleLocation.clone().add(2, 0, -1));

        locationList.add(middleLocation.clone().add(-2, 0, 0));
        locationList.add(middleLocation.clone().add(-2, 1, 0));
        locationList.add(middleLocation.clone().add(-2, -1, 0));
        locationList.add(middleLocation.clone().add(-2, 0, 1));
        locationList.add(middleLocation.clone().add(-2, 0, -1));

        locationList.add(middleLocation.clone().add(0, 2, 0));
        locationList.add(middleLocation.clone().add(0, 2, 1));
        locationList.add(middleLocation.clone().add(0, 2, -1));
        locationList.add(middleLocation.clone().add(1, 2, 0));
        locationList.add(middleLocation.clone().add(-1, 2, 0));

        locationList.add(middleLocation.clone().add(0, -2, 0));
        locationList.add(middleLocation.clone().add(0, -2, 1));
        locationList.add(middleLocation.clone().add(0, -2, -1));
        locationList.add(middleLocation.clone().add(1, -2, 0));
        locationList.add(middleLocation.clone().add(-1, -2, 0));

        locationList.add(middleLocation.clone().add(1, 1, 1));
        locationList.add(middleLocation.clone().add(-1, 1, 1));
        locationList.add(middleLocation.clone().add(1, 1, -1));
        locationList.add(middleLocation.clone().add(-1, 1, -1));

        locationList.add(middleLocation.clone().add(1, -1, 1));
        locationList.add(middleLocation.clone().add(-1, -1, 1));
        locationList.add(middleLocation.clone().add(1, -1, -1));
        locationList.add(middleLocation.clone().add(-1, -1, -1));

        return locationList;
    }
}
