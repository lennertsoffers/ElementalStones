package com.lennertsoffers.elementalstones.customClasses.tools;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import java.util.*;

public class SetBlockTools {

    /**
     * <b>Takes an 3D string array and places the blocks in the world</b>
     * <p>
     *     Place a character in the string and map it in the characterMaterialMap
     *     You have to provide a '*' as your starting location
     *     If you provide a '?', the block will remain unchanged
     * </p>
     * 
     * @param activePlayer the activeplayer that 'places' the blocks
     * @param startLocation the location of the '*' in the stringList
     * @param stringList the 3D array of strings with the different layers 
     * @param characterMaterialMap the mappings of the materials to the characters of the strings
     * @param onlyFillAir if the function should only fill air blocks of override all blocks
     * @param overrideMaterials always overrides these materials
     * @param overrideLocations always overrides these locations
     * @param locationBlockType the material on the spot you placed the '*'
     * @param amountOfDamage -1 if no damage should be dealt
     * @param velocity the velocity you add to the damaged entities, null if none
     * @param potionEffects the potionEffects you add to the damaged entities, null if none
     * @see NearbyEntityTools#damageNearbyEntities(Player, Location, double, double, double, double)
     */
    public static void setBlocksInWorld(
            ActivePlayer activePlayer,
            Location startLocation,
            String[][] stringList,
            Map<Character, Material> characterMaterialMap,
            boolean onlyFillAir,
            List<Material> overrideMaterials,
            List<Location> overrideLocations,
            Material locationBlockType,
            double amountOfDamage,
            Vector velocity,
            List<PotionEffect> potionEffects
    ) {
        if (activePlayer != null) {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();

            // Search the starting location on the grid
            int startX = 0;
            int startY = 0;
            int startZ = 0;
            for (int i = 0; i < stringList.length; i++) {
                for (int j = 0; j < stringList[i].length; j++) {
                    for (int k = 0; k < stringList[i][j].length(); k++) {
                        if (stringList[i][j].charAt(k) == '*') {
                            startX = j;
                            startY = i;
                            startZ = k;
                            break;
                        }
                    }
                }
            }

            // Set position in the bottom left corner
            Location startingLocation = startLocation.clone().add(-startX, startY, -startZ);

            // Loop over the grid of characters
            for (int layerY = 0; layerY < stringList.length; layerY++) {
                for (int rowX = 0; rowX < stringList[layerY].length; rowX++) {
                    String row = stringList[layerY][rowX];

                    for (int columnZ = 0; columnZ < row.length(); columnZ++) {
                        // Create new location object with the current location on the grid
                        Location blockLocation = startingLocation.clone().add(rowX, layerY, columnZ).getBlock().getLocation();

                        // Get the material of the current selected block
                        Material material = world.getBlockAt(blockLocation).getType();

                        // Only air blocks can be changed
                        // Or onlyFillAir is false
                        // Or the block or location is overridable
                        if (material == Material.AIR || !onlyFillAir || overrideMaterials.contains(material) || overrideLocations.contains(blockLocation)) {
                            // Get the specified material for the specified location
                            if (row.charAt(columnZ) == '*') {
                                if (locationBlockType != null) {
                                    activePlayer.addLocationMaterialMapping(blockLocation, material);
                                    world.getBlockAt(blockLocation).setType(locationBlockType);
                                }

                            // Get the material from the mapping
                            } else if (row.charAt(columnZ) != '?') {
                                activePlayer.addLocationMaterialMapping(blockLocation, material);

                                Material blockMaterial = characterMaterialMap.get(row.charAt(columnZ));
                                world.getBlockAt(blockLocation).setType(blockMaterial);

                                if (blockMaterial == Material.LAVA) {
                                    activePlayer.getLavaLocations().add(blockLocation);
                                }
                            }

                            // If damage must be done this is handled here
                            if (amountOfDamage != -1) {
                                NearbyEntityTools.damageNearbyEntities(player, blockLocation, amountOfDamage, 1, 1, 1, velocity, potionEffects);
                            }
                        }
                    }
                }
            }
        }
    }
}
