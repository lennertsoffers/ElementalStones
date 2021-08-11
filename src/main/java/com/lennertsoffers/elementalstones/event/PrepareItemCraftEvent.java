package com.lennertsoffers.elementalstones.event;

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
        ItemStack SA = new ItemStack(Material.SADDLE);
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
        ItemStack DH = new ItemStack(Material.DRAGON_HEAD);
        ItemStack WS = new ItemStack(Material.WET_SPONGE);
        ItemStack DS = new ItemStack(Material.SPONGE);

        ItemStack[] waterStone0recipe = {null, S, null, null, s, null, null, S, null};
        ItemStack[] waterStone1recipe = {null, S, null, S, ItemStones.waterStone0, S, null, S, null};
        ItemStack[] waterStone2recipe = {S, S, S, S, ItemStones.waterStone1, S, S, S, S};
        ItemStack[] waterStoneOcean0recipe = {null, H, null, null, ItemStones.waterStone2, null, null, null, null};
        ItemStack[] waterStoneOcean1recipe = {null, H, null, null, ItemStones.waterStoneOcean0, null, null, H, null};
        ItemStack[] waterStoneOcean2recipe = {null, H, null, S, ItemStones.waterStoneOcean1, S, null, H, null};
        ItemStack[] waterStoneOcean3recipe = {S, H, S, S, ItemStones.waterStoneOcean2, S, S, H, S};
        ItemStack[] waterStoneOcean4recipe = {D, H, D, H, ItemStones.waterStoneOcean3, H, D, H, D};
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
        ItemStack[] fireStoneExplosion0recipe = {TN, TN, TN, TN, ItemStones.fireStone2, TN, TN, TN, TN};
        ItemStack[] fireStoneExplosion1recipe = {null, AD, null, null, ItemStones.fireStone2, null, null, null, null};
        ItemStack[] fireStoneExplosion2recipe = {AD, null, AD, null, ItemStones.fireStoneExplosion1, null, AD, null, AD};
        ItemStack[] fireStoneExplosion3recipe = {EC, TN, EC, TN, ItemStones.fireStoneExplosion2, TN, EC, TN, EC};
        ItemStack[] fireStoneExplosion4recipe = {EC, EC, EC, EC, ItemStones.fireStoneExplosion3, EC, EC, EC, EC};
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
        ItemStack[] airStoneWeather0recipe = {null, CL, null, null, ItemStones.airStone2, null, null, null, null};
        ItemStack[] airStoneWeather1recipe = {CL, CL, CL, null, ItemStones.airStoneWeather0, null, null, null, null};
        ItemStack[] airStoneWeather2recipe = {CL, CL, CL, CL, ItemStones.airStoneWeather1, CL, CL, CL, CL};
        ItemStack[] airStoneWeather3recipe = {null, LS, null, ItemStones.airStoneWeather2, null, null, null, null};
        ItemStack[] airStoneWeather4recipe = {CL, N, CL, CL, ItemStones.airStoneWeather3, CL, CL, CL, CL};

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
        ItemStack[] earthStoneMining0recipe = {SP, SP, SP, SP, ItemStones.earthStone2, SP, SP, SP, SP};
        ItemStack[] earthStoneMining1recipe = {IP, IP, IP, IP, ItemStones.earthStoneMining0, IP, IP, IP, IP};
        ItemStack[] earthStoneMining2recipe = {GP, GP, GP, GP, ItemStones.earthStoneMining1, GP, GP, GP, GP};
        ItemStack[] earthStoneMining3recipe = {DP, DP, DP, DP, ItemStones.earthStoneMining2, DP, DP, DP, DP};
        ItemStack[] earthStoneMining4recipe = {NP, NP, NP, NP, ItemStones.earthStoneMining3, NP, NP, NP, NP};

        ItemStack[] magicStone0recipe = {BO, BO, BO, BO, s, BO, BO, BO, BO};
        ItemStack[] magicStone1recipe = {BO, ET, BO, BO, ItemStones.magicStone0, BO, BO, ET, BO};
        ItemStack[] magicStone2recipe = {BO, ET, BO, ET, ItemStones.magicStone1, ET, BO, ET, BO};
        ItemStack[] magicStoneLife0recipe = {BM, BM, BM, BM, ItemStones.magicStone2, BM, BM, BM, BM};
        ItemStack[] magicStoneLife1recipe = {SS, SS, SS, SS, ItemStones.magicStoneLife0, SS, SS, SS, SS};
        ItemStack[] magicStoneLife2recipe = {SS, TO, SS, SS, ItemStones.magicStoneLife1, SS, SS, SS, SS};
        ItemStack[] magicStoneLife3recipe = {SS, TO, SS, SS, ItemStones.magicStoneLife2, SS, SS, TO, SS};
        ItemStack[] magicStoneLife4recipe = {TO, TO, TO, TO, ItemStones.magicStoneLife3, TO, TO, TO, TO};
        ItemStack[] magicStoneDarkMagic0recipe = {null, W, null, null, ItemStones.magicStone2, null, null, null, null};
        ItemStack[] magicStoneDarkMagic1recipe = {null, W, null, null, ItemStones.magicStoneDarkMagic0, null, null, W, null};
        ItemStack[] magicStoneDarkMagic2recipe = {null, W, null, W, ItemStones.magicStoneDarkMagic1, W, null, W, null};
        ItemStack[] magicStoneDarkMagic3recipe = {W, W, W, W, ItemStones.magicStoneDarkMagic2, W, W, W, W};
        ItemStack[] magicStoneDarkMagic4recipe = {null, DE, null, null, ItemStones.magicStoneDarkMagic3, null, null, null, null};
        ItemStack[] magicStonePotion0recipe = {null, LP, null, null, ItemStones.magicStone2, null, null, null, null};
        ItemStack[] magicStonePotion1recipe = {null, LP, null, null, ItemStones.magicStonePotion0, null, null, LP, null};
        ItemStack[] magicStonePotion2recipe = {null, LP, null, LP, ItemStones.magicStonePotion1, LP, null, LP, null};
        ItemStack[] magicStonePotion3recipe = {LP, LP, LP, LP, ItemStones.magicStonePotion2, LP, LP, LP, LP};
        ItemStack[] magicStonePotion4recipe = {WSS, WSS, WSS, WSS, ItemStones.magicStonePotion3, WSS, WSS, WSS, WSS};
        ItemStack[] magicStoneBeastsAndCreatures0recipe = {null, SA, null, null, ItemStones.magicStone2, null, null, null, null};
        ItemStack[] magicStoneBeastsAndCreatures1recipe = {null, SA, null, null, ItemStones.magicStoneBeastsAndCreatures0, null, null, SA, null};
        ItemStack[] magicStoneBeastsAndCreatures2recipe = {null, SA, null, SA, ItemStones.magicStoneBeastsAndCreatures1, SA, null, SA, null};
        ItemStack[] magicStoneBeastsAndCreatures3recipe = {SA, SA, SA, SA, ItemStones.magicStoneBeastsAndCreatures2, SA, SA, SA, SA};
        ItemStack[] magicStoneBeastsAndCreatures4recipe = {DH, DH, DH, DH, ItemStones.magicStoneBeastsAndCreatures3, DH, DH, DH, DH};

        if (Arrays.equals(waterStone0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStone0);
        } else if (Arrays.equals(waterStone1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStone1);
        } else if (Arrays.equals(waterStone2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStone2);
        } else if (Arrays.equals(waterStoneOcean0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneOcean0);
        } else if (Arrays.equals(waterStoneOcean1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneOcean1);
        } else if (Arrays.equals(waterStoneOcean2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneOcean2);
        } else if (Arrays.equals(waterStoneOcean3recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneOcean3);
        } else if (Arrays.equals(waterStoneOcean4recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.waterStoneOcean4);
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
        } else if (Arrays.equals(fireStoneExplosion0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneExplosion0);
        } else if (Arrays.equals(fireStoneExplosion1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneExplosion1);
        } else if (Arrays.equals(fireStoneExplosion2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneExplosion2);
        } else if (Arrays.equals(fireStoneExplosion3recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneExplosion3);
        } else if (Arrays.equals(fireStoneExplosion4recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.fireStoneExplosion4);
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
        } else if (Arrays.equals(airStoneWeather0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneWeather0);
        } else if (Arrays.equals(airStoneWeather1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneWeather1);
        } else if (Arrays.equals(airStoneWeather2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneWeather2);
        } else if (Arrays.equals(airStoneWeather3recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneWeather3);
        } else if (Arrays.equals(airStoneWeather4recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.airStoneWeather4);
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
        } else if (Arrays.equals(earthStoneMining0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneMining0);
        } else if (Arrays.equals(earthStoneMining1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneMining1);
        } else if (Arrays.equals(earthStoneMining2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneMining2);
        } else if (Arrays.equals(earthStoneMining3recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneMining3);
        } else if (Arrays.equals(earthStoneMining4recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.earthStoneMining4);
        } else if (Arrays.equals(magicStone0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStone0);
        } else if (Arrays.equals(magicStone1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStone1);
        } else if (Arrays.equals(magicStone2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStone2);
        } else if (Arrays.equals(magicStoneLife0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStoneLife0);
        } else if (Arrays.equals(magicStoneLife1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStoneLife1);
        } else if (Arrays.equals(magicStoneLife2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStoneLife2);
        } else if (Arrays.equals(magicStoneLife3recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStoneLife3);
        } else if (Arrays.equals(magicStoneLife4recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStoneLife4);
        } else if (Arrays.equals(magicStoneDarkMagic0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStoneDarkMagic0);
        } else if (Arrays.equals(magicStoneDarkMagic1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStoneDarkMagic1);
        } else if (Arrays.equals(magicStoneDarkMagic2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStoneDarkMagic2);
        } else if (Arrays.equals(magicStoneDarkMagic3recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStoneDarkMagic3);
        } else if (Arrays.equals(magicStoneDarkMagic4recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStoneDarkMagic4);
        } else if (Arrays.equals(magicStonePotion0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStonePotion0);
        } else if (Arrays.equals(magicStonePotion1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStonePotion1);
        } else if (Arrays.equals(magicStonePotion2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStonePotion2);
        } else if (Arrays.equals(magicStonePotion3recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStonePotion3);
        } else if (Arrays.equals(magicStonePotion4recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStonePotion4);
        } else if (Arrays.equals(magicStoneBeastsAndCreatures0recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStoneBeastsAndCreatures0);
        } else if (Arrays.equals(magicStoneBeastsAndCreatures1recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStoneBeastsAndCreatures1);
        } else if (Arrays.equals(magicStoneBeastsAndCreatures2recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStoneBeastsAndCreatures2);
        } else if (Arrays.equals(magicStoneBeastsAndCreatures3recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStoneBeastsAndCreatures3);
        } else if (Arrays.equals(magicStoneBeastsAndCreatures4recipe, craftingMatrix)) {
            event.getInventory().setResult(ItemStones.magicStoneBeastsAndCreatures4);
        }
    }
}
