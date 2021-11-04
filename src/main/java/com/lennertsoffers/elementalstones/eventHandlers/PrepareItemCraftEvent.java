package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class PrepareItemCraftEvent implements Listener {

    @EventHandler
    public void onPrepareItemCraft(org.bukkit.event.inventory.PrepareItemCraftEvent event) {
        ItemStack[] craftingMatrix = event.getInventory().getMatrix();


        ItemStack s = ItemStones.baseStone;

        ItemStack BC = new ItemStack(Material.COD_BUCKET);
        ItemStack BP = new ItemStack(Material.PUFFERFISH_BUCKET);
        ItemStack BS = new ItemStack(Material.SALMON_BUCKET);
        ItemStack BT = new ItemStack(Material.TROPICAL_FISH_BUCKET);
        ItemStack D = new ItemStack(Material.DIAMOND_BLOCK);
        ItemStack H = new ItemStack(Material.HEART_OF_THE_SEA);
        ItemStack I = new ItemStack(Material.ICE);
        ItemStack P = new ItemStack(Material.PACKED_ICE);
        ItemStack B = new ItemStack(Material.BLUE_ICE);
        ItemStack S = new ItemStack(Material.NAUTILUS_SHELL);
        ItemStack T = new ItemStack(Material.TRIDENT);
        ItemStack R = new ItemStack(Material.RED_NETHER_BRICK_SLAB);
        ItemStack G = new ItemStack(Material.GHAST_TEAR);
        ItemStack W = new ItemStack(Material.WITHER_ROSE);
        ItemStack N = new ItemStack(Material.NETHER_STAR);
        ItemStack PS = new ItemStack(Material.MUSIC_DISC_PIGSTEP);
        ItemStack EA = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
        ItemStack TH = new ItemStack(Material.TURTLE_HELMET);
        ItemStack SC = new ItemStack(Material.SCUTE);
        ItemStack DE = new ItemStack(Material.DRAGON_EGG);
        ItemStack DB = new ItemStack(Material.DRAGON_BREATH);
        ItemStack LA = new ItemStack(Material.LAVA_BUCKET);
        ItemStack AD = new ItemStack(Material.ANCIENT_DEBRIS);
        ItemStack NE = new ItemStack(Material.NETHERITE_INGOT);
        ItemStack EL = new ItemStack(Material.ELYTRA);
        ItemStack CH = new ItemStack(Material.CHAINMAIL_HELMET);
        ItemStack BE = new ItemStack(Material.BEACON);
        ItemStack ET = new ItemStack(Material.ENCHANTING_TABLE);
        ItemStack BO = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemStack OB = new ItemStack(Material.OBSIDIAN);
        ItemStack CO = new ItemStack(Material.CRYING_OBSIDIAN);
        ItemStack EC = new ItemStack(Material.END_CRYSTAL);
        ItemStack TO = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemStack CL = new ItemStack(Material.CLOCK);
        ItemStack TN = new ItemStack(Material.TNT);
        ItemStack FC = new ItemStack(Material.FIRE_CHARGE);
        ItemStack SB = new ItemStack(Material.SOUL_CAMPFIRE);
        ItemStack LS = new ItemStack(Material.LODESTONE);
        ItemStack MDW = new ItemStack(Material.MUSIC_DISC_WARD);
        ItemStack MDB = new ItemStack(Material.MUSIC_DISC_BLOCKS);
        ItemStack MDC = new ItemStack(Material.MUSIC_DISC_CAT);
        ItemStack MDF = new ItemStack(Material.MUSIC_DISC_FAR);
        ItemStack MDM = new ItemStack(Material.MUSIC_DISC_MELLOHI);
        ItemStack MD1 = new ItemStack(Material.MUSIC_DISC_11);
        ItemStack GB = new ItemStack(Material.GLASS_BOTTLE);
        ItemStack NB = new ItemStack(Material.NETHERITE_BLOCK);
        ItemStack SP = new ItemStack(Material.STONE_PICKAXE);
        ItemStack IP = new ItemStack(Material.IRON_PICKAXE);
        ItemStack GP = new ItemStack(Material.GOLDEN_PICKAXE);
        ItemStack DP = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemStack NP = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemStack SS = new ItemStack(Material.SUSPICIOUS_STEW);
        ItemStack BM = new ItemStack(Material.BONE_MEAL);
        ItemStack WSS = new ItemStack(Material.WITHER_SKELETON_SKULL);
        ItemStack LP = new ItemStack(Material.LINGERING_POTION);
        ItemStack WS = new ItemStack(Material.WET_SPONGE);
        ItemStack DS = new ItemStack(Material.SPONGE);

        ItemStack[] waterStone0recipe = {null, S, null, null, s, null, null, S, null};
        ItemStack[] waterStone1recipe = {null, S, null, S, ItemStones.waterStone0, S, null, S, null};
        ItemStack[] waterStone2recipe = {S, S, S, S, ItemStones.waterStone1, S, S, S, S};
        ItemStack[] waterStoneBending0recipe = {BC, BP, BC, BP, ItemStones.waterStone2, BP, BC, BP, BC};
        ItemStack[] waterStoneBending1recipe = {BT, BS, BT, BS, ItemStones.waterStoneBending0, BS, BT, BS, BT};
        ItemStack[] waterStoneBending2recipe = {S, S, S, S, ItemStones.waterStoneBending1, S, S, S, S};
        ItemStack[] waterStoneBending3recipe = {null, T, null, null, ItemStones.waterStoneBending2, null, null, null, null};
        ItemStack[] waterStoneBending4recipe = {D, T, D, D, ItemStones.waterStoneBending3, D, D, T, D};
        ItemStack[] waterStoneIce0recipe = {I, P, B, null, ItemStones.waterStone2, null, B, P, I};
        ItemStack[] waterStoneIce1recipe = {DS, DS, DS, DS, ItemStones.waterStoneIce0, DS, DS, DS, DS};
        ItemStack[] waterStoneIce2recipe = {WS, WS, WS, WS, ItemStones.waterStoneIce1, WS, WS, WS, WS};
        ItemStack[] waterStoneIce3recipe = {B, B, B, B, ItemStones.waterStoneIce1, B, B, B, B};
        ItemStack[] waterStoneIce4recipe = {B, D, B, D, ItemStones.waterStoneIce3, D, B, D, B};

        ItemStack[] fireStone0recipe = {null, G, null, null, s, null, null, null, null};
        ItemStack[] fireStone1recipe = {null, R, null, null, ItemStones.fireStone0, null, null, R, null};
        ItemStack[] fireStone2recipe = {null, G, null, R, ItemStones.fireStone1, R, null, G, null};
        ItemStack[] fireStoneLava0recipe = {LA, LA, LA, LA, ItemStones.fireStone2, LA, LA, LA, LA};
        ItemStack[] fireStoneLava1recipe = {LA, G, LA, LA, ItemStones.fireStoneLava0, LA, LA, G, LA};
        ItemStack[] fireStoneLava2recipe = {AD, LA, AD, null, ItemStones.fireStoneLava1, LA, LA, LA, LA};
        ItemStack[] fireStoneLava3recipe = {G, G, G, G, ItemStones.fireStoneLava2, G, G, G, G};
        ItemStack[] fireStoneLava4recipe = {LA, EA, LA, LA, ItemStones.fireStoneLava3, LA, LA, LA, LA};
        ItemStack[] fireStoneHellFire0recipe = {SB, SB, SB, SB, ItemStones.fireStone2, SB, SB, SB, SB};
        ItemStack[] fireStoneHellFire1recipe = {FC, SB, FC, SB, ItemStones.fireStoneHellFire0, SB, FC, SB, FC};
        ItemStack[] fireStoneHellFire2recipe = {DB, FC, DB, FC, ItemStones.fireStoneHellFire1, FC, DB, FC, DB};
        ItemStack[] fireStoneHellFire3recipe = {DB, DB, DB, DB, ItemStones.fireStoneHellFire2, DB, DB, DB, DB};
        ItemStack[] fireStoneHellFire4recipe = {null, NE, null, null, ItemStones.fireStoneHellFire3, null, null, PS, null};

        ItemStack[] airStone0recipe = {null, MDW, null, null, s, null, null, null, null};
        ItemStack[] airStone1recipe = {null, MDB, null, null, ItemStones.airStone0, null, null, null, null};
        ItemStack[] airStone2recipe = {null, MDC, null, null, ItemStones.airStone1, null, null, null, null};
        ItemStack[] airStoneAgility0recipe = {null, MDF, null, null, ItemStones.airStone2, null, null, null, null};
        ItemStack[] airStoneAgility1recipe = {null, MDM, null, null, ItemStones.airStoneAgility0, null, null, null, null};
        ItemStack[] airStoneAgility2recipe = {null, MD1, null, null, ItemStones.airStoneAgility1, null, null, null, null};
        ItemStack[] airStoneAgility3recipe = {MDW, MDB, MDC, MDF, ItemStones.airStoneAgility2, MDM, null, MD1, null};
        ItemStack[] airStoneAgility4recipe = {null, EL, null, null, ItemStones.airStoneAgility3, null, null, EL, null};
        ItemStack[] airStoneBending0recipe = {null, GB, null, null, ItemStones.airStone2, null, null, null, null};
        ItemStack[] airStoneBending1recipe = {null, GB, null, GB, ItemStones.airStoneBending0, GB, null, GB, null };
        ItemStack[] airStoneBending2recipe = {GB, GB, GB, GB, ItemStones.airStoneBending1, GB, GB, GB, GB};
        ItemStack[] airStoneBending3recipe = {null, BE, null, ItemStones.airStoneBending2, null, null, null, null};
        ItemStack[] airStoneBending4recipe = {null, N, null, null, ItemStones.airStoneBending3, null, null, N, null};

        ItemStack[] earthStone0recipe = {null, OB, null, null, s, null, null, null, null};
        ItemStack[] earthStone1recipe = {null, OB, null, OB, ItemStones.earthStone0, OB, null, OB, null};
        ItemStack[] earthStone2recipe = {CO, OB, CO, OB, ItemStones.earthStone1, OB, CO, OB, CO};
        ItemStack[] earthStoneDefense0recipe = {null, CH, null, null, ItemStones.earthStone2, null, null, null, null};
        ItemStack[] earthStoneDefense1recipe = {null, SC, null, null, ItemStones.earthStoneDefense0, null, null, null, null};
        ItemStack[] earthStoneDefense2recipe = {null, SC, null, SC, ItemStones.earthStoneDefense1, SC, null, SC, null};
        ItemStack[] earthStoneDefense3recipe = {null, TH, null, null, ItemStones.earthStoneDefense2, null, null, null, null};
        ItemStack[] earthStoneDefense4recipe = {null, TH, null, TH, ItemStones.earthStoneDefense3, TH, null, TH, null};
        ItemStack[] earthStoneBending0recipe = {null, OB, null, null, ItemStones.earthStone2, null, null, null, null};
        ItemStack[] earthStoneBending1recipe = {null, OB, null, OB, ItemStones.earthStoneBending0, OB, null, OB, null};
        ItemStack[] earthStoneBending2recipe = {OB, OB, OB, OB, ItemStones.earthStone0, OB, OB, OB, OB};
        ItemStack[] earthStoneBending3recipe = {CO, CO, CO, CO, ItemStones.earthStone0, CO, CO, CO, CO};
        ItemStack[] earthStoneBending4recipe = {null, NB, null, null, ItemStones.earthStoneBending3, null, null, NB, null};

        if (Arrays.equals(waterStone0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStone0);
        } else if (Arrays.equals(waterStone1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStone1);
        } else if (Arrays.equals(waterStone2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStone2);
        } else if (Arrays.equals(waterStoneBending0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneBending0);
        } else if (Arrays.equals(waterStoneBending1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneBending1);
        } else if (Arrays.equals(waterStoneBending2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneBending2);
        } else if (Arrays.equals(waterStoneBending3recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneBending3);
        } else if (Arrays.equals(waterStoneBending4recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneBending4);
        } else if (Arrays.equals(waterStoneIce0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneIce0);
        } else if (Arrays.equals(waterStoneIce1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneIce1);
        } else if (Arrays.equals(waterStoneIce2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneIce2);
        } else if (Arrays.equals(waterStoneIce3recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneIce3);
        } else if (Arrays.equals(waterStoneIce4recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneIce4);
        } else if (Arrays.equals(fireStone0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStone0);
        } else if (Arrays.equals(fireStone1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStone1);
        } else if (Arrays.equals(fireStone2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStone2);
        } else if (Arrays.equals(fireStoneLava0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneLava0);
        } else if (Arrays.equals(fireStoneLava1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneLava1);
        } else if (Arrays.equals(fireStoneLava2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneLava2);
        } else if (Arrays.equals(fireStoneLava3recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneLava3);
        } else if (Arrays.equals(fireStoneLava4recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneLava4);
        } else if (Arrays.equals(fireStoneHellFire0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneHellFire0);
        } else if (Arrays.equals(fireStoneHellFire1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneHellFire1);
        } else if (Arrays.equals(fireStoneHellFire2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneHellFire2);
        } else if (Arrays.equals(fireStoneHellFire3recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneHellFire3);
        } else if (Arrays.equals(fireStoneHellFire4recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneHellFire4);
        } else if (Arrays.equals(airStone0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStone0);
        } else if (Arrays.equals(airStone1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStone1);
        } else if (Arrays.equals(airStone2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStone2);
        } else if (Arrays.equals(airStoneAgility0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneAgility0);
        } else if (Arrays.equals(airStoneAgility1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneAgility1);
        } else if (Arrays.equals(airStoneAgility2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneAgility2);
        } else if (Arrays.equals(airStoneAgility3recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneAgility3);
        } else if (Arrays.equals(airStoneAgility4recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneAgility4);
        } else if (Arrays.equals(airStoneBending0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneBending0);
        } else if (Arrays.equals(airStoneBending1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneBending1);
        } else if (Arrays.equals(airStoneBending2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneBending2);
        } else if (Arrays.equals(airStoneBending3recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneBending3);
        } else if (Arrays.equals(airStoneBending4recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneBending4);
        } else if (Arrays.equals(earthStone0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStone0);
        } else if (Arrays.equals(earthStone1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStone1);
        } else if (Arrays.equals(earthStone2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStone2);
        } else if (Arrays.equals(earthStoneDefense0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneDefense0);
        } else if (Arrays.equals(earthStoneDefense1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneDefense1);
        } else if (Arrays.equals(earthStoneDefense2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneDefense2);
        } else if (Arrays.equals(earthStoneDefense3recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneDefense3);
        } else if (Arrays.equals(earthStoneDefense4recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneDefense4);
        } else if (Arrays.equals(earthStoneBending0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneBending0);
        } else if (Arrays.equals(earthStoneBending1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneBending1);
        } else if (Arrays.equals(earthStoneBending2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneBending2);
        } else if (Arrays.equals(earthStoneBending3recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneBending3);
        } else if (Arrays.equals(earthStoneBending4recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneBending4);
        }
    }
}
