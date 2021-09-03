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

public class SetBlockTools {

    // Tool to effectively set the blocks in the world
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
                    if (string.charAt(column) == '*') {
                        if (locationBlockType != null) {
                            world.getBlockAt(blockLocation).setType(locationBlockType);
                        }
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
}
