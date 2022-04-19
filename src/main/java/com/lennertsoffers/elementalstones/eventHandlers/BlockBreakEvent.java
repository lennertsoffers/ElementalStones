package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.ElementalStones;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.annotations.Event;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Event
public class BlockBreakEvent implements Listener {

    @EventHandler
    public void onBlockBreak(org.bukkit.event.block.BlockBreakEvent event) {
        Material blockMaterial = event.getBlock().getType();
        Location blockLocation = event.getBlock().getLocation();
        World world = blockLocation.getWorld();

        if (world != null) {
            // Rotten Apple
            if (
                    blockMaterial == Material.ACACIA_LEAVES ||
                            blockMaterial == Material.AZALEA_LEAVES ||
                            blockMaterial == Material.BIRCH_LEAVES ||
                            blockMaterial == Material.DARK_OAK_LEAVES ||
                            blockMaterial == Material.JUNGLE_LEAVES ||
                            blockMaterial == Material.FLOWERING_AZALEA_LEAVES ||
                            blockMaterial == Material.SPRUCE_LEAVES ||
                            blockMaterial == Material.OAK_LEAVES
            ) {
                if (StaticVariables.random.nextInt(ElementalStones.configuration.getInt("drop_chance.rotten_apple")) == 0) {
                    world.dropItemNaturally(blockLocation, CraftItemManager.ROTTEN_APPLE);
                }
            }

            // Herbs
            else if (
                    blockMaterial == Material.GRASS ||
                    blockMaterial == Material.TALL_GRASS ||
                    blockMaterial == Material.LARGE_FERN
            ) {
                if (StaticVariables.random.nextInt(ElementalStones.configuration.getInt("drop_chance.herb")) == 0) {
                    int random = StaticVariables.random.nextInt(4);
                    if (random == 0) {
                        world.dropItemNaturally(blockLocation, CraftItemManager.OREGANO);
                    } else if (random == 1) {
                        world.dropItemNaturally(blockLocation, CraftItemManager.DILL);
                    } else if (random == 2) {
                        world.dropItemNaturally(blockLocation, CraftItemManager.ROSEMARY);
                    } else {
                        world.dropItemNaturally(blockLocation, CraftItemManager.THYME);
                    }
                }
            }

            // Dead Flower
            else if (
                    blockMaterial == Material.DANDELION ||
                    blockMaterial == Material.POPPY ||
                    blockMaterial == Material.BLUE_ORCHID ||
                    blockMaterial == Material.ALLIUM ||
                    blockMaterial == Material.AZURE_BLUET ||
                    blockMaterial == Material.RED_TULIP ||
                    blockMaterial == Material.ORANGE_TULIP ||
                    blockMaterial == Material.WHITE_TULIP ||
                    blockMaterial == Material.PINK_TULIP ||
                    blockMaterial == Material.OXEYE_DAISY ||
                    blockMaterial == Material.SUNFLOWER ||
                    blockMaterial == Material.LILAC ||
                    blockMaterial == Material.ROSE_BUSH ||
                    blockMaterial == Material.PEONY ||
                    blockMaterial == Material.CORNFLOWER ||
                    blockMaterial == Material.LILY_OF_THE_VALLEY ||
                    blockMaterial == Material.AZALEA ||
                    blockMaterial == Material.FLOWERING_AZALEA ||
                    blockMaterial == Material.SPORE_BLOSSOM
            ) {
                if (StaticVariables.random.nextInt(ElementalStones.configuration.getInt("drop_chance.dead_flower")) == 0) {
                    world.dropItemNaturally(blockLocation, CraftItemManager.DEAD_FLOWER);
                }
            }

        }
    }
}
