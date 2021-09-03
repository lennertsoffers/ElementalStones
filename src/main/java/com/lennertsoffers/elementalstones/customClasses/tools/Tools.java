package com.lennertsoffers.elementalstones.customClasses.tools;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import jdk.nashorn.internal.ir.IfNode;
import net.minecraft.server.INamableTileEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Tools {

    public static boolean checkPlayerCollision(double player, double block) {
        return !(player > block + 1.3 || player < block - 0.3);
    }

    public static double lengthOfVector(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static double lengthOfVector(double x1, double x2, double y1, double y2, double z1, double z2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
    }

    public static Vector directionOfVector(Location a, Location b) {
        double dX = a.getX() - b.getX();
        double dY = a.getY() - b.getY();
        double dZ = a.getZ() - b.getZ();
        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.sin(pitch) * Math.sin(yaw);
        double z = Math.cos(pitch);
        return new Vector(x, z, y);
    }

    public static Location locationOnCircle(Location location, double radius, double angle, World world) {
        double particleX = location.getX() + radius * Math.cos(angle);
        double particleZ = location.getZ() + radius * Math.sin(angle);
        return new Location(world, particleX, location.getY() , particleZ);
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





    private static void setBlockTool(Location startLocation,
                                     String[] stringList,
                                     Map<Character, Material> characterMaterialMap,
                                     boolean onlyFillAir,
                                     ArrayList<Material> overrideBlocks,
                                     Material locationBlockType,
                                     double amountOfDamage,
                                     Player player,
                                     ActivePlayer activePlayer)
    {
        // Stop function if world is not defined
        World world = startLocation.getWorld();
        if (world == null) {
            return;
        }

        // Search the startingLocation on the grid
        int columnLocation = 0;
        int rowLocation = 0;
        for (int row = 0; row < stringList.length; row++) {
            for (int column = 0; column < stringList[0].length(); column++) {
                if (stringList[row].charAt(column) == '*') {
                    columnLocation = column;
                    rowLocation = row;
                }
            }
        }

        // Set position in the bottom left corner
        Location startingLocation = startLocation.clone().add(-rowLocation, 0, -columnLocation);

        // Loop trough all characters of the grid
        for (int row = 0; row < stringList.length; row++) {
            String string = stringList[row];
            for (int column = 0; column < stringList[0].length(); column++) {

                // Create new location object with the current location on the grid
                Location blockLocation = startingLocation.clone().add(row, 0, column);

                // Get the material of the current selected block
                Material material = world.getBlockAt(blockLocation).getType();

                // Only change the material of this block if the following statement is true
                if (material == Material.AIR || !onlyFillAir || overrideBlocks.contains(material)) {

                    // If the character on the grid equals '?', nothing is changed
                    // If the character on the grid equals '*' and locationBlockType contains a value, this block is set to this value
                    // Else, the material of the block is set to the corresponding material in the characterMaterialMap
                    if (string.charAt(column) == '*' && locationBlockType != null) {
                        world.getBlockAt(blockLocation).setType(locationBlockType);
                    } else {
                        world.getBlockAt(blockLocation).setType(characterMaterialMap.get(string.charAt(column)));
                    }
                }

                // If the nearby entities have to be damaged by the wave, the amount of damage is set on all of them
                if (amountOfDamage != -1) {
                    for (Entity entity : world.getNearbyEntities(blockLocation, 0.5, 0.5, 0.5)) {
                        if (entity instanceof LivingEntity) {
                            LivingEntity livingEntity = (LivingEntity) entity;
                            if (livingEntity != player) {
                                livingEntity.damage(amountOfDamage);
                            }
                        }
                    }
                }

                // If there is an activePlayer and the block placed is a lava block, the location must be added to its lavaBlockLocations
                if (activePlayer != null) {
                    if (characterMaterialMap.get(string.charAt(column)) == Material.LAVA) {
                        activePlayer.addLavaBlockLocation(blockLocation);
                    }
                }
            }
        }
    }
}
    
    
    // -> Fill blocks
    public static void setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir) 
    {
        setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, new ArrayList<>(), null, -1, null, null);
    }

    // -> Fill blocks
    // -> Add LavaBlocks to activePlayer
    public static void setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 ActivePlayer activePlayer) 
    {
        setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, new ArrayList<>(), null, -1, null, activePlayer);
    }

    // -> Fill blocks
    // -> Overrides non fill blocks set by boolean onlyFillAir
    public static void setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 ArrayList<Material> overrideBlocks) 
    {
        setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, overrideBlocks, null, -1, null, null);
    }

    // -> Fill blocks
    // -> Overrides non fill blocks set by boolean onlyFillAir
    // -> Add LavaBlocks to activePlayer
    public static void setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 ArrayList<Material> overrideBlocks,
                                 ActivePlayer activePlayer) 
    {
        setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, overrideBlocks, null, -1, null, activePlayer);
    }

    // -> Fill blocks
    // -> Set material of starting location
    public static void setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 Material locationBlockType) 
    {
        setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, new ArrayList<>(), locationBlockType, -1, null, null);
    }    
    
    // -> Fill blocks
    // -> Set material of starting location
    // -> Add LavaBlocks to activePlayer
    public static void setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 Material locationBlockType,
                                 ActivePlayer activePlayer) 
    {
        setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, new ArrayList<>(), locationBlockType, -1, null, activePlayer);
    }

    // -> Fill blocks
    // -> Damages nearby livingEntities except player
    public static void setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 double amountOfDamage,
                                 Player player) 
    {
        setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, new ArrayList<>(), null, amountOfDamage, player, null);
    }

    // -> Fill blocks
    // -> Damages nearby livingEntities except player
    // -> Add LavaBlocks to activePlayer
    public static void setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 double amountOfDamage,
                                 Player player,
                                 ActivePlayer activePlayer) 
    {
        setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, new ArrayList<>(), null, amountOfDamage, player, activePlayer);
    }

    // -> Fill blocks
    // -> Overrides non fill blocks set by boolean onlyFillAir
    // -> Set material of starting location
    public static void setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 ArrayList<Material> overrideBlocks,
                                 Material locationBlockType) 
    {
        setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, overrideBlocks, locationBlockType, -1, null, null);
    }
    
    // -> Fill blocks
    // -> Overrides non fill blocks set by boolean onlyFillAir
    // -> Set material of starting location
    // -> Add LavaBlocks to activePlayer
    public static void setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 ArrayList<Material> overrideBlocks,
                                 Material locationBlockType,
                                 ActivePlayer activePlayer) 
    {
        setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, overrideBlocks, locationBlockType, -1, null, activePlayer);
    }
    
    // -> Fill blocks
    // -> Overrides non fill blocks set by boolean onlyFillAir
    // -> Damages nearby livingEntities except player
    public static void setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 ArrayList<Material> overrideBlocks,
                                 double amountOfDamage,
                                 Player player)
    {
        setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, overrideBlocks, null, amountOfDamage, player, null);
    }
    
    // -> Fill blocks
    // -> Overrides non fill blocks set by boolean onlyFillAir
    // -> Damages nearby livingEntities except player
    // -> Add LavaBlocks to activePlayer
    public static void setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 ArrayList<Material> overrideBlocks,
                                 double amountOfDamage,
                                 Player player,
                                 ActivePlayer activePlayer)
    {
        setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, overrideBlocks, null, amountOfDamage, player, activePlayer);
    }

    // -> Fill blocks
    // -> Set material of starting location
    // -> Damages nearby livingEntities except player
    public static void setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 Material locationBlockType,
                                 double amountOfDamage,
                                 Player player)
    {
        setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, new ArrayList<>(), locationBlockType, amountOfDamage, player, null);
    }
    
    // -> Fill blocks
    // -> Set material of starting location
    // -> Damages nearby livingEntities except player
    // -> Add LavaBlocks to activePlayer
    public static void setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 Material locationBlockType,
                                 double amountOfDamage,
                                 Player player,
                                 ActivePlayer activePlayer)
    {
        setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, new ArrayList<>(), locationBlockType, amountOfDamage, player, activePlayer);
    }
    
    // -> Fill blocks
    // -> Overrides non fill blocks set by boolean onlyFillAir
    // -> Damages nearby livingEntities except player
    // -> Set material of starting location
    public static void setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 ArrayList<Material> overrideBlocks,
                                 Material locationBlockType,
                                 double amountOfDamage,
                                 Player player)
    {
        setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, overrideBlocks, locationBlockType, amountOfDamage, player, null);
    }
    
    // -> Fill blocks
    // -> Overrides non fill blocks set by boolean onlyFillAir
    // -> Damages nearby livingEntities except player
    // -> Set material of starting location
    // -> Add LavaBlocks to activePlayer
    public static void setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 ArrayList<Material> overrideBlocks,
                                 Material locationBlockType,
                                 double amountOfDamage,
                                 Player player,
                                 ActivePlayer activePlayer)
    {
        setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, overrideBlocks, locationBlockType, amountOfDamage, player, activePlayer);
    }
