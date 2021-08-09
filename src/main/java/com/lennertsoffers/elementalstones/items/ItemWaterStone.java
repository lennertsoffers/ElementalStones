package com.lennertsoffers.elementalstones.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemWaterStone {

    // WaterStones: Default
    public static ItemStack waterStone0;
    public static ItemStack waterStone1;
    public static ItemStack waterStone2;

    // WaterStones: Ocean path
    public static ItemStack waterStoneOcean0;
    public static ItemStack waterStoneOcean1;
    public static ItemStack waterStoneOcean2;
    public static ItemStack waterStoneOcean3;
    public static ItemStack waterStoneOcean4;

    // WaterStones: Bending path
    public static ItemStack waterStoneBending0;
    public static ItemStack waterStoneBending1;
    public static ItemStack waterStoneBending2;
    public static ItemStack waterStoneBending3;
    public static ItemStack waterStoneBending4;

    // WaterStones: Ice path
    public static ItemStack waterStoneIce0;
    public static ItemStack waterStoneIce1;
    public static ItemStack waterStoneIce2;
    public static ItemStack waterStoneIce3;
    public static ItemStack waterStoneIce4;

    public static void init() {
        createWaterStone0();
        createWaterStone1();
        createWaterStone2();
    }

    private static void createWaterStone0() {
        ItemStack stack = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + "Water Stone");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + "An old relic used to manipulate water in surroundings.");
        lore.add(ChatColor.YELLOW + "Move 1: info");
        meta.setLore(lore);
        stack.setItemMeta(meta);

        waterStone0 = stack;

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("water-stone"), stack);
        recipe.shape("NHN", "H H", "NHN");
        recipe.setIngredient('N', Material.NAUTILUS_SHELL);
        recipe.setIngredient('H', Material.HEART_OF_THE_SEA);
        Bukkit.getServer().addRecipe(recipe);
    }

    private static void createWaterStone1() {
        ItemStack stack = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + "Water Stone Lv2");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + "An old relic used to manipulate water in surroundings.");
        lore.add(ChatColor.YELLOW + "Move 1: info");
        lore.add(ChatColor.YELLOW + "Move 2: info");
        meta.setLore(lore);
        stack.setItemMeta(meta);

        waterStone1 = stack;
    }

    private static void createWaterStone2() {
        ItemStack stack = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + "Water Stone Lv3");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + "An old relic used to manipulate water in surroundings.");
        lore.add(ChatColor.YELLOW + "Move 1: info");
        lore.add(ChatColor.YELLOW + "Move 2: info");
        lore.add(ChatColor.YELLOW + "Move 3: info");
        meta.setLore(lore);
        stack.setItemMeta(meta);

        waterStone2 = stack;
    }
}
