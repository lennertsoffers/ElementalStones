package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.ElementalStones;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

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
                    blockMaterial == Material.TALL_GRASS ||
                    blockMaterial == Material.LARGE_FERN ||
                    blockMaterial == Material.CORNFLOWER ||
                    blockMaterial == Material.LILY_OF_THE_VALLEY ||
                    blockMaterial == Material.AZALEA ||
                    blockMaterial == Material.FLOWERING_AZALEA ||
                    blockMaterial == Material.SPORE_BLOSSOM
            ) {
                if (StaticVariables.random.nextInt(ElementalStones.configuration.getInt("drop_chance.herb")) == 0) {
                    switch (StaticVariables.random.nextInt(3)) {
                        case 0:
                            world.dropItemNaturally(blockLocation, CraftItemManager.OREGANO);
                        case 1:
                            world.dropItemNaturally(blockLocation, CraftItemManager.THYME);
                        case 2:
                            world.dropItemNaturally(blockLocation, CraftItemManager.ROSEMARY);
                        default:
                            world.dropItemNaturally(blockLocation, CraftItemManager.DILL);
                    }
                }
            }

            else if (
                    blockMaterial == Material.WITHER_ROSE ||
                    blockMaterial == Material.CRIMSON_ROOTS ||
                    blockMaterial == Material.CRIMSON_FUNGUS ||
                    blockMaterial == Material.WARPED_ROOTS ||
                    blockMaterial == Material.WARPED_FUNGUS
            ) {
                if (StaticVariables.random.nextInt(ElementalStones.configuration.getInt("drop_chance.dead_flower")) == 0) {
                    world.dropItemNaturally(blockLocation, CraftItemManager.DEAD_FLOWER);
                }
            }

            else if (blockMaterial == Material.DEAD_BUSH) {
                if (StaticVariables.random.nextInt(ElementalStones.configuration.getInt("drop_chance.twig")) == 0) {
                    world.dropItemNaturally(blockLocation, CraftItemManager.TWIG);
                }
            }
        }
    }
}
