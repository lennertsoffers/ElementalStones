package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.items.ItemStones;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class PrepareItemCraftEvent implements Listener {

    @EventHandler
    public void onPrepareItemCraft(org.bukkit.event.inventory.PrepareItemCraftEvent event) {

        // ItemStacks
        ItemStack TOTEM_OF_UNDYING = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemStack GLASS_BOTTLE = new ItemStack(Material.GLASS_BOTTLE);
        ItemStack CRYING_OBSIDIAN = new ItemStack(Material.CRYING_OBSIDIAN);
        ItemStack LEAD = new ItemStack(Material.LEAD);
        ItemStack SPIDER_EYE = new ItemStack(Material.SPIDER_EYE);
        ItemStack CANDLE = new ItemStack(Material.CANDLE);
        ItemStack WATER = new ItemStack(Material.WATER_BUCKET);
        ItemStack HAY = new ItemStack(Material.HAY_BLOCK);
        ItemStack SUGAR = new ItemStack(Material.SUGAR);
        ItemStack WHEAT = new ItemStack(Material.WHEAT);
        ItemStack HONEY = new ItemStack(Material.HONEY_BOTTLE);
        ItemStack RED_DYE = new ItemStack(Material.RED_DYE);
        ItemStack WHITE_DYE = new ItemStack(Material.WHITE_DYE);
        ItemStack EGG = new ItemStack(Material.EGG);
        ItemStack MILK = new ItemStack(Material.MILK_BUCKET);
        ItemStack KELP = new ItemStack(Material.DRIED_KELP);


        // Recipes
        ItemStack[] voodoo_doll_recipe = {null, CraftItemManager.SOUL_OF_EVOKER, null, null, TOTEM_OF_UNDYING, null, null, CraftItemManager.BABY_ZOMBIE_HIDE, null};
        ItemStack[] ship_in_bottle = {GLASS_BOTTLE, GLASS_BOTTLE, GLASS_BOTTLE, GLASS_BOTTLE, CraftItemManager.TWIG, CraftItemManager.BABY_ZOMBIE_HIDE, GLASS_BOTTLE, GLASS_BOTTLE, GLASS_BOTTLE};
        ItemStack[] palantir = {CRYING_OBSIDIAN, CRYING_OBSIDIAN, CRYING_OBSIDIAN, CRYING_OBSIDIAN, CraftItemManager.SOUL_OF_EVOKER, CRYING_OBSIDIAN, CRYING_OBSIDIAN, CRYING_OBSIDIAN, CRYING_OBSIDIAN};
        ItemStack[] blood_and_quil = {null, CraftItemManager.GOLDEN_FEATHER, null, null, CraftItemManager.BLOOD_OF_WANDERING_TRADER, null, null, null, null};
        ItemStack[] bundle_of_herbs = {CraftItemManager.THYME, CraftItemManager.OREGANO, null, LEAD, LEAD, null, CraftItemManager.ROSEMARY, CraftItemManager.DILL, null};
        ItemStack[] mystery_potion = {SPIDER_EYE, CraftItemManager.ROTTEN_APPLE, CraftItemManager.OREGANO, CraftItemManager.INSECT, CraftItemManager.BAT, GLASS_BOTTLE, GLASS_BOTTLE, GLASS_BOTTLE, GLASS_BOTTLE};
        ItemStack[] poisonous_apple = {null, CraftItemManager.INSECT, null, null, CraftItemManager.ROTTEN_APPLE, null, null, null, null};
        ItemStack[] carnivorous_plant = {null, CraftItemManager.SOUL_OF_EVOKER, null, null, CraftItemManager.DEAD_FLOWER, null, null, null, null};
        ItemStack[] scented_plant = {null, null, null, CraftItemManager.DEAD_FLOWER, CANDLE, CraftItemManager.THYME, null, null, null};
        ItemStack[] finn_soup = {WATER, KELP, WATER, CraftItemManager.FINN, CraftItemManager.ROSEMARY, CraftItemManager.FINN, WATER, CraftItemManager.DILL, WATER};
        ItemStack[] poisonous_dart = {null, CraftItemManager.INSECT, null, null, CraftItemManager.STINGER, null, null, CraftItemManager.GOLDEN_FEATHER, null};
        ItemStack[] broom = {null, null, null, HAY, CraftItemManager.TWIG, CraftItemManager.TWIG, null, null, null};
        ItemStack[] gingerbread_man = {null, MILK, null, SUGAR, WHEAT, HONEY, WHITE_DYE, EGG, RED_DYE};
        ItemStack[] antidote = {null, CraftItemManager.STINGER, null, null, CraftItemManager.BLOOD_OF_WANDERING_TRADER, null, null, MILK, null};
        ItemStack[] war_horn = {null, CraftItemManager.HOGLIN_TUSK, null, CraftItemManager.HOGLIN_TUSK, CraftItemManager.HOGLIN_TUSK, CraftItemManager.HOGLIN_TUSK, null, null, null};


        // Check CraftingMatrix
        ItemStack[] craftingMatrix = event.getInventory().getMatrix();
        if (Arrays.equals(voodoo_doll_recipe, craftingMatrix)) {
            event.getInventory().setResult(CraftItemManager.VOODOO_DOLL);
        } else if (Arrays.equals(ship_in_bottle, craftingMatrix)) {
            event.getInventory().setResult(CraftItemManager.SHIP_IN_BOTTLE);
        } else if (Arrays.equals(palantir, craftingMatrix)) {
            event.getInventory().setResult(CraftItemManager.PALANTIR);
        } else if (Arrays.equals(blood_and_quil, craftingMatrix)) {
            event.getInventory().setResult(CraftItemManager.BLOOD_AND_QUIL);
        } else if (Arrays.equals(bundle_of_herbs, craftingMatrix)) {
            event.getInventory().setResult(CraftItemManager.BUNDLE_OF_HERBS);
        } else if (Arrays.equals(mystery_potion, craftingMatrix)) {
            event.getInventory().setResult(CraftItemManager.MYSTERY_POTION);
        } else if (Arrays.equals(poisonous_apple, craftingMatrix)) {
            event.getInventory().setResult(CraftItemManager.POISONED_APPLE);
        } else if (Arrays.equals(carnivorous_plant, craftingMatrix)) {
            event.getInventory().setResult(CraftItemManager.CARNIVOROUS_PLANT);
        } else if (Arrays.equals(scented_plant, craftingMatrix)) {
            event.getInventory().setResult(CraftItemManager.SCENTED_CANDLE);
        } else if (Arrays.equals(finn_soup, craftingMatrix)) {
            event.getInventory().setResult(CraftItemManager.FINN_SOUP);
        } else if (Arrays.equals(poisonous_dart, craftingMatrix)) {
            event.getInventory().setResult(CraftItemManager.POISONOUS_DART);
        } else if (Arrays.equals(broom, craftingMatrix)) {
            event.getInventory().setResult(CraftItemManager.BROOM);
        } else if (Arrays.equals(gingerbread_man, craftingMatrix)) {
            event.getInventory().setResult(CraftItemManager.GINGERBREAD_MAN);
        } else if (Arrays.equals(antidote, craftingMatrix)) {
            event.getInventory().setResult(CraftItemManager.ANTIDOTE);
        } else if (Arrays.equals(war_horn, craftingMatrix)) {
            event.getInventory().setResult(CraftItemManager.WAR_HORN);
        }
    }
}
