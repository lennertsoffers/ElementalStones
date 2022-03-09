package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.tools.ItemTools;
import com.lennertsoffers.elementalstones.items.ItemStones;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PrepareItemCraftEvent implements Listener {

    @EventHandler
    public void onPrepareItemCraft(org.bukkit.event.inventory.PrepareItemCraftEvent event) {

        // ITEMSTACKS
        // Normal materials
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

        // Short names for shards
        ItemStack CS = CraftItemManager.COMMON_SHARD;
        ItemStack US = CraftItemManager.UNCOMMON_SHARD;
        ItemStack RS = CraftItemManager.RARE_SHARD;
        ItemStack URS = CraftItemManager.ULTRA_RARE_SHARD;
        ItemStack LS = CraftItemManager.LEGENDARY_SHARD;


        // RECIPES
        // Recipes: CraftItems
        ItemStack[] voodoo_doll_recipe = {null, CraftItemManager.SOUL_OF_EVOKER, null, CraftItemManager.STINGER, TOTEM_OF_UNDYING, null, null, CraftItemManager.BABY_ZOMBIE_HIDE, null};
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

        // Recipes: Consumables

        // Recipes: Stones
        // -> LV 2
        ItemStack[] waterStone1 = {null, CS, null, CS, ItemStones.waterStone0, CS, null, CS, null};
        ItemStack[] fireStone1 = {null, CS, null, CS, ItemStones.fireStone0, CS, null, CS, null};
        ItemStack[] airStone1 = {null, CS, null, CS, ItemStones.airStone0, CS, null, CS, null};
        ItemStack[] earthStone1 = {null, CS, null, CS, ItemStones.earthStone0, CS, null, CS, null};

        // -> LV 3
        ItemStack[] waterStone2 = {null, CS, null, US, ItemStones.waterStone1, US, null, CS, null};
        ItemStack[] fireStone2 = {null, CS, null, US, ItemStones.fireStone1, US, null, CS, null};
        ItemStack[] airStone2 = {null, CS, null, US, ItemStones.airStone1, US, null, CS, null};
        ItemStack[] earthStone2 = {null, CS, null, US, ItemStones.earthStone1, US, null, CS, null};

        // -> LV 4: Choosing path
        ItemStack CS5 = CS.clone();
        CS5.setAmount(5);
        ItemStack[] waterStoneBending0 = {CS5, CraftItemManager.WATERBENDING_SPELL, CS5, CS5, ItemStones.waterStone2, CS5, CS5, CS5, CS5};
        ItemStack[] waterStoneIce0 = {CS5, CraftItemManager.ICE_SPELL, CS5, CS5, ItemStones.waterStone2, CS5, CS5, CS5, CS5};
        ItemStack[] fireStoneExplosion0 = {CS5, CraftItemManager.EXPLOSION_SPELL, CS5, CS5, ItemStones.fireStone2, CS5, CS5, CS5, CS5};
        ItemStack[] fireStoneHellFire0 = {CS5, CraftItemManager.HELLFIRE_SPELL, CS5, CS5, ItemStones.fireStone2, CS5, CS5, CS5, CS5};
        ItemStack[] airStoneAgility0 = {CS5, CraftItemManager.AGILITY_SPELL, CS5, CS5, ItemStones.airStone2, CS5, CS5, CS5, CS5};
        ItemStack[] airStoneBending0 = {CS5, CraftItemManager.AIRBENDING_SPELL, CS5, CS5, ItemStones.airStone2, CS5, CS5, CS5, CS5};
        ItemStack[] earthStoneLava0 = {CS5, CraftItemManager.LAVA_SPELL, CS5, CS5, ItemStones.earthStone2, CS5, CS5, CS5, CS5};
        ItemStack[] earthStoneBending0 = {CS5, CraftItemManager.EARTHBENDING_SPELL, CS5, CS5, ItemStones.earthStone2, CS5, CS5, CS5, CS5};

        // -> LV 5
        ItemStack[] waterStoneBending1 = {CS, US, CS, US, ItemStones.waterStoneBending0, US, CS, RS, CS};
        ItemStack[] waterStoneIce1 = {CS, US, CS, US, ItemStones.waterStoneIce0, US, CS, RS, CS};
        ItemStack[] fireStoneExplosion1 = {CS, US, CS, US, ItemStones.fireStoneExplosion0, US, CS, RS, CS};
        ItemStack[] fireStoneHellFire1 = {CS, US, CS, US, ItemStones.fireStoneHellFire0, US, CS, RS, CS};
        ItemStack[] airStoneAgility1 = {CS, US, CS, US, ItemStones.airStoneAgility0, US, CS, RS, CS};
        ItemStack[] airStoneBending1 = {CS, US, CS, US, ItemStones.airStoneBending0, US, CS, RS, CS};
        ItemStack[] earthStoneLava1 = {CS, US, CS, US, ItemStones.earthStoneLava0, US, CS, RS, CS};
        ItemStack[] earthStoneBending1 = {CS, US, CS, US, ItemStones.earthStoneBending0, US, CS, RS, CS};

        // -> LV 6
        ItemStack[] waterStoneBending2 = {US, RS, US, RS, ItemStones.waterStoneBending1, RS, US, URS, US};
        ItemStack[] waterStoneIce2 = {US, RS, US, RS, ItemStones.waterStoneIce1, RS, US, URS, US};
        ItemStack[] fireStoneExplosion2 = {US, RS, US, RS, ItemStones.fireStoneExplosion1, RS, US, URS, US};
        ItemStack[] fireStoneHellFire2 = {US, RS, US, RS, ItemStones.fireStoneHellFire1, RS, US, URS, US};
        ItemStack[] airStoneAgility2 = {US, RS, US, RS, ItemStones.airStoneAgility1, RS, US, URS, US};
        ItemStack[] airStoneBending2 = {US, RS, US, RS, ItemStones.airStoneBending1, RS, US, URS, US};
        ItemStack[] earthStoneLava2 = {US, RS, US, RS, ItemStones.earthStoneLava1, RS, US, URS, US};
        ItemStack[] earthStoneBending2 = {US, RS, US, RS, ItemStones.earthStoneBending1, RS, US, URS, US};

        // -> LV 7
        ItemStack US5 = US.clone();
        US5.setAmount(5);
        ItemStack[] waterStoneBending3 = {US5, URS, US5, URS, ItemStones.waterStoneBending2, URS, US5, URS, US5};
        ItemStack[] waterStoneIce3 = {US5, URS, US5, URS, ItemStones.waterStoneIce2, URS, US5, URS, US5};
        ItemStack[] fireStoneExplosion3 = {US5, URS, US5, URS, ItemStones.fireStoneExplosion2, URS, US5, URS, US5};
        ItemStack[] fireStoneHellFire3 = {US5, URS, US5, URS, ItemStones.fireStoneHellFire2, URS, US5, URS, US5};
        ItemStack[] airStoneAgility3 = {US5, URS, US5, URS, ItemStones.airStoneAgility2, URS, US5, URS, US5};
        ItemStack[] airStoneBending3 = {US5, URS, US5, URS, ItemStones.airStoneBending2, URS, US5, URS, US5};
        ItemStack[] earthStoneLava3 = {US5, URS, US5, URS, ItemStones.earthStoneLava2, URS, US5, URS, US5};
        ItemStack[] earthStoneBending3 = {US5, URS, US5, URS, ItemStones.earthStoneBending2, URS, US5, URS, US5};

        // -> LV 8
        ItemStack CS25 = CS.clone();
        ItemStack US15 = US.clone();
        ItemStack RS7 = RS.clone();
        ItemStack URS5 = URS.clone();
        CS25.setAmount(25);
        US15.setAmount(15);
        RS7.setAmount(7);
        URS5.setAmount(5);
        ItemStack[] waterStoneBending4 = {CS25, URS5, CS25, RS7, ItemStones.waterStoneBending3, RS7, US15, LS, US15};
        ItemStack[] waterStoneIce4 = {CS25, URS5, CS25, RS7, ItemStones.waterStoneIce3, RS7, US15, LS, US15};
        ItemStack[] fireStoneExplosion4 = {CS25, URS5, CS25, RS7, ItemStones.fireStoneExplosion3, RS7, US15, LS, US15};
        ItemStack[] fireStoneHellFire4 = {CS25, URS5, CS25, RS7, ItemStones.fireStoneHellFire3, RS7, US15, LS, US15};
        ItemStack[] airStoneAgility4 = {CS25, URS5, CS25, RS7, ItemStones.airStoneAgility3, RS7, US15, LS, US15};
        ItemStack[] airStoneBending4 = {CS25, URS5, CS25, RS7, ItemStones.airStoneBending3, RS7, US15, LS, US15};
        ItemStack[] earthStoneLava4 = {CS25, URS5, CS25, RS7, ItemStones.earthStoneLava3, RS7, US15, LS, US15};
        ItemStack[] earthStoneBending4 = {CS25, URS5, CS25, RS7, ItemStones.earthStoneBending3, RS7, US15, LS, US15};


        // Check CraftingMatrix
        ItemStack[] craftingMatrix = event.getInventory().getMatrix();
        ArrayList<ItemStack> singleItemCraftingMatrix = ItemTools.getSingleListFromStackList(craftingMatrix);

        // Craft items
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


        // Stones
        else if (Arrays.equals(waterStone1, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStone1);
        } else if (Arrays.equals(waterStone2, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStone2);
        } else if (Arrays.equals(waterStoneBending0, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneBending0);
        } else if (Arrays.equals(waterStoneBending1, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneBending1);
        } else if (Arrays.equals(waterStoneBending2, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneBending2);
        } else if (Arrays.equals(waterStoneBending3, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneBending3);
        } else if (Arrays.equals(waterStoneBending4, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneBending4);
        } else if (Arrays.equals(waterStoneIce0, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneIce0);
        } else if (Arrays.equals(waterStoneIce1, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneIce1);
        } else if (Arrays.equals(waterStoneIce2, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneIce2);
        } else if (Arrays.equals(waterStoneIce3, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneIce3);
        } else if (Arrays.equals(waterStoneIce4, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneIce4);
        }

        else if (Arrays.equals(fireStone1, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStone1);
        } else if (Arrays.equals(fireStone2, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStone2);
        } else if (Arrays.equals(fireStoneExplosion0, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneExplosion0);
        } else if (Arrays.equals(fireStoneExplosion1, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneExplosion1);
        } else if (Arrays.equals(fireStoneExplosion2, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneExplosion2);
        } else if (Arrays.equals(fireStoneExplosion3, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneExplosion3);
        } else if (Arrays.equals(fireStoneExplosion4, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneExplosion4);
        } else if (Arrays.equals(fireStoneHellFire0, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneHellFire0);
        } else if (Arrays.equals(fireStoneHellFire1, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneHellFire1);
        } else if (Arrays.equals(fireStoneHellFire2, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneHellFire2);
        } else if (Arrays.equals(fireStoneHellFire3, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneHellFire3);
        } else if (Arrays.equals(fireStoneHellFire4, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneHellFire4);
        }

        else if (Arrays.equals(airStone1, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStone1);
        } else if (Arrays.equals(airStone2, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStone2);
        } else if (Arrays.equals(airStoneAgility0, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneAgility0);
        } else if (Arrays.equals(airStoneAgility1, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneAgility1);
        } else if (Arrays.equals(airStoneAgility2, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneAgility2);
        } else if (Arrays.equals(airStoneAgility3, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneAgility3);
        } else if (Arrays.equals(airStoneAgility4, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneAgility4);
        } else if (Arrays.equals(airStoneBending0, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneBending0);
        } else if (Arrays.equals(airStoneBending1, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneBending1);
        } else if (Arrays.equals(airStoneBending2, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneBending2);
        } else if (Arrays.equals(airStoneBending3, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneBending3);
        } else if (Arrays.equals(airStoneBending4, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneBending4);
        }

        else if (Arrays.equals(earthStone1, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStone1);
        } else if (Arrays.equals(earthStone2, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStone2);
        } else if (Arrays.equals(earthStoneLava0, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneLava0);
        } else if (Arrays.equals(earthStoneLava1, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneLava1);
        } else if (Arrays.equals(earthStoneLava2, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneLava2);
        } else if (Arrays.equals(earthStoneLava3, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneLava3);
        } else if (Arrays.equals(earthStoneLava4, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneLava4);
        } else if (Arrays.equals(earthStoneBending0, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneBending0);
        } else if (Arrays.equals(earthStoneBending1, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneBending1);
        } else if (Arrays.equals(earthStoneBending2, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneBending2);
        } else if (Arrays.equals(earthStoneBending3, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneBending3);
        } else if (Arrays.equals(earthStoneBending4, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneBending4);
        }

        else if (!Collections.disjoint(CraftItemManager.craftItems, singleItemCraftingMatrix)) {
            event.getInventory().setResult(null);
        } else if (!Collections.disjoint(ItemStones.allStones, singleItemCraftingMatrix)) {
            event.getInventory().setResult(null);
        }


        for (ItemStack i : event.getInventory().getMatrix()) {
            if (i != null) {
                if (i.getType().equals(Material.BLAZE_ROD)) {
                    event.getInventory().setResult(null);
                }
            }
        }
    }
}
