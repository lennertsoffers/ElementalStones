package com.lennertsoffers.elementalstones.customClasses.tools;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class SetBlockTools {

    // Tool to effectively set the blocks in the world
    private static ArrayList<Location> setBlockTool(Location startLocation,
                                     String[] stringList,
                                     Map<Character, Material> characterMaterialMap,
                                     boolean onlyFillAir,
                                     ArrayList<Material> overrideBlocks,
                                     Material locationBlockType,
                                     double amountOfDamage,
                                     Player player,
                                     ActivePlayer activePlayer)
    {
        // Arraylist of locations to return
        ArrayList<Location> locationArrayList = new ArrayList<>();

        // Stop function if world is not defined
        World world = startLocation.getWorld();
        if (world == null) {
            return locationArrayList;
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
                locationArrayList.add(blockLocation);

                // Get the material of the current selected block
                Material material = world.getBlockAt(blockLocation).getType();

                // Only change the material of this block if the following statement is true
                if (material == Material.AIR || !onlyFillAir || overrideBlocks.contains(material)) {

                    // If the character on the grid equals '?', nothing is changed
                    // If the character on the grid equals '*' and locationBlockType contains a value, this block is set to this value
                    // Else, the material of the block is set to the corresponding material in the characterMaterialMap
                    if (string.charAt(column) == '*') {
                        if (locationBlockType != null) {
                            Objects.requireNonNull(activePlayer).addLocationMaterialMapping(blockLocation, material);
                            world.getBlockAt(blockLocation).setType(locationBlockType);
                        }
                    } else if (string.charAt(column) != '?') {
                        Objects.requireNonNull(activePlayer).addLocationMaterialMapping(blockLocation, material);
                        world.getBlockAt(blockLocation).setType(characterMaterialMap.get(string.charAt(column)));
                    }
                }

                // If the nearby entities have to be damaged, the amount of damage is set on all of them
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
        return locationArrayList;
    }



    // -> Fill blocks and return locations
    public static ArrayList<Location> setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir)
    {
        return setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, new ArrayList<>(), null, -1, null, null);
    }

    // -> Fill blocks and return locations
    // -> Add LavaBlocks to activePlayer
    public static ArrayList<Location> setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 ActivePlayer activePlayer)
    {
        return setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, new ArrayList<>(), null, -1, null, activePlayer);
    }

    // -> Fill blocks and return locations
    // -> Overrides non fill blocks set by boolean onlyFillAir
    public static ArrayList<Location> setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 ArrayList<Material> overrideBlocks)
    {
        return setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, overrideBlocks, null, -1, null, null);
    }

    // -> Fill blocks and return locations
    // -> Overrides non fill blocks set by boolean onlyFillAir
    // -> Add LavaBlocks to activePlayer
    public static ArrayList<Location> setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 ArrayList<Material> overrideBlocks,
                                 ActivePlayer activePlayer)
    {
        return setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, overrideBlocks, null, -1, null, activePlayer);
    }

    // -> Fill blocks and return locations
    // -> Set material of starting location
    public static ArrayList<Location> setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 Material locationBlockType)
    {
        return setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, new ArrayList<>(), locationBlockType, -1, null, null);
    }

    // -> Fill blocks and return locations
    // -> Set material of starting location
    // -> Add LavaBlocks to activePlayer
    public static ArrayList<Location> setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 Material locationBlockType,
                                 ActivePlayer activePlayer)
    {
        return setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, new ArrayList<>(), locationBlockType, -1, null, activePlayer);
    }

    // -> Fill blocks and return locations
    // -> Damages nearby livingEntities except player
    public static ArrayList<Location> setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 double amountOfDamage,
                                 Player player)
    {
        return setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, new ArrayList<>(), null, amountOfDamage, player, null);
    }

    // -> Fill blocks and return locations
    // -> Damages nearby livingEntities except player
    // -> Add LavaBlocks to activePlayer
    public static ArrayList<Location> setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 double amountOfDamage,
                                 Player player,
                                 ActivePlayer activePlayer)
    {
        return setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, new ArrayList<>(), null, amountOfDamage, player, activePlayer);
    }

    // -> Fill blocks and return locations
    // -> Overrides non fill blocks set by boolean onlyFillAir
    // -> Set material of starting location
    public static ArrayList<Location> setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 ArrayList<Material> overrideBlocks,
                                 Material locationBlockType)
    {
        return setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, overrideBlocks, locationBlockType, -1, null, null);
    }

    // -> Fill blocks and return locations
    // -> Overrides non fill blocks set by boolean onlyFillAir
    // -> Set material of starting location
    // -> Add LavaBlocks to activePlayer
    public static ArrayList<Location> setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 ArrayList<Material> overrideBlocks,
                                 Material locationBlockType,
                                 ActivePlayer activePlayer)
    {
        return setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, overrideBlocks, locationBlockType, -1, null, activePlayer);
    }

    // -> Fill blocks and return locations
    // -> Overrides non fill blocks set by boolean onlyFillAir
    // -> Damages nearby livingEntities except player
    public static ArrayList<Location> setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 ArrayList<Material> overrideBlocks,
                                 double amountOfDamage,
                                 Player player)
    {
        return setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, overrideBlocks, null, amountOfDamage, player, null);
    }

    // -> Fill blocks and return locations
    // -> Overrides non fill blocks set by boolean onlyFillAir
    // -> Damages nearby livingEntities except player
    // -> Add LavaBlocks to activePlayer
    public static ArrayList<Location> setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 ArrayList<Material> overrideBlocks,
                                 double amountOfDamage,
                                 Player player,
                                 ActivePlayer activePlayer)
    {
        return setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, overrideBlocks, null, amountOfDamage, player, activePlayer);
    }

    // -> Fill blocks and return locations
    // -> Set material of starting location
    // -> Damages nearby livingEntities except player
    public static ArrayList<Location> setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 Material locationBlockType,
                                 double amountOfDamage,
                                 Player player)
    {
        return setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, new ArrayList<>(), locationBlockType, amountOfDamage, player, null);
    }

    // -> Fill blocks and return locations
    // -> Set material of starting location
    // -> Damages nearby livingEntities except player
    // -> Add LavaBlocks to activePlayer
    public static ArrayList<Location> setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 Material locationBlockType,
                                 double amountOfDamage,
                                 Player player,
                                 ActivePlayer activePlayer)
    {
        return setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, new ArrayList<>(), locationBlockType, amountOfDamage, player, activePlayer);
    }

    // -> Fill blocks and return locations
    // -> Overrides non fill blocks set by boolean onlyFillAir
    // -> Damages nearby livingEntities except player
    // -> Set material of starting location
    public static ArrayList<Location> setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 ArrayList<Material> overrideBlocks,
                                 Material locationBlockType,
                                 double amountOfDamage,
                                 Player player)
    {
        return setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, overrideBlocks, locationBlockType, amountOfDamage, player, null);
    }

    // -> Fill blocks and return locations
    // -> Overrides non fill blocks set by boolean onlyFillAir
    // -> Damages nearby livingEntities except player
    // -> Set material of starting location
    // -> Add LavaBlocks to activePlayer
    public static ArrayList<Location> setBlocks(Location startLocation,
                                 String[] stringList,
                                 Map<Character, Material> characterMaterialMap,
                                 boolean onlyFillAir,
                                 ArrayList<Material> overrideBlocks,
                                 Material locationBlockType,
                                 double amountOfDamage,
                                 Player player,
                                 ActivePlayer activePlayer)
    {
        return setBlockTool(startLocation, stringList, characterMaterialMap, onlyFillAir, overrideBlocks, locationBlockType, amountOfDamage, player, activePlayer);
    }
}
