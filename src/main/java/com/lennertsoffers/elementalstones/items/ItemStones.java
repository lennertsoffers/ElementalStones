package com.lennertsoffers.elementalstones.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemStones {

    // BASE STONE
    public static ItemStack baseStone;

    // WATER STONES
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


    // FIRE STONES
    // FireStones: Default
    public static ItemStack fireStone0;
    public static ItemStack fireStone1;
    public static ItemStack fireStone2;

    // FireStones: Lava path
    public static ItemStack fireStoneLava0;
    public static ItemStack fireStoneLava1;
    public static ItemStack fireStoneLava2;
    public static ItemStack fireStoneLava3;
    public static ItemStack fireStoneLava4;

    // FireStones: Explosion path
    public static ItemStack fireStoneExplosion0;
    public static ItemStack fireStoneExplosion1;
    public static ItemStack fireStoneExplosion2;
    public static ItemStack fireStoneExplosion3;
    public static ItemStack fireStoneExplosion4;

    // FireStones: Hellfire path
    public static ItemStack fireStoneHellFire0;
    public static ItemStack fireStoneHellFire1;
    public static ItemStack fireStoneHellFire2;
    public static ItemStack fireStoneHellFire3;
    public static ItemStack fireStoneHellFire4;

    // WIND STONES
    // WindStones: Default
    public static ItemStack windStone0;
    public static ItemStack windStone1;
    public static ItemStack windStone2;

    // WindStones: Agility path
    public static ItemStack windStoneAgility0;
    public static ItemStack windStoneAgility1;
    public static ItemStack windStoneAgility2;
    public static ItemStack windStoneAgility3;
    public static ItemStack windStoneAgility4;

    // WindStones: Bending path
    public static ItemStack windStoneBending0;
    public static ItemStack windStoneBending1;
    public static ItemStack windStoneBending2;
    public static ItemStack windStoneBending3;
    public static ItemStack windStoneBending4;

    // WindStones: Weather path
    public static ItemStack windStoneWeather0;
    public static ItemStack windStoneWeather1;
    public static ItemStack windStoneWeather2;
    public static ItemStack windStoneWeather3;
    public static ItemStack windStoneWeather4;

    // EARTH STONES
    // EarthStones: Default
    public static ItemStack earthStone0;
    public static ItemStack earthStone1;
    public static ItemStack earthStone2;

    // EarthStones: Defense path
    public static ItemStack earthStoneDefense0;
    public static ItemStack earthStoneDefense1;
    public static ItemStack earthStoneDefense2;
    public static ItemStack earthStoneDefense3;
    public static ItemStack earthStoneDefense4;

    // EarthStones: Earthbending path
    public static ItemStack earthStoneBending0;
    public static ItemStack earthStoneBending1;
    public static ItemStack earthStoneBending2;
    public static ItemStack earthStoneBending3;
    public static ItemStack earthStoneBending4;

    // EarthStones: Mining path
    public static ItemStack earthStoneMining0;
    public static ItemStack earthStoneMining1;
    public static ItemStack earthStoneMining2;
    public static ItemStack earthStoneMining3;
    public static ItemStack earthStoneMining4;

    // MAGIC STONES
    // MagicStones: Default
    public static ItemStack magicStone0;
    public static ItemStack magicStone1;
    public static ItemStack magicStone2;

    // MagicStones: Life path
    public static ItemStack magicStoneLife0;
    public static ItemStack magicStoneLife1;
    public static ItemStack magicStoneLife2;
    public static ItemStack magicStoneLife3;
    public static ItemStack magicStoneLife4;

    // MagicStones: Energy path
    public static ItemStack magicStoneDarkMagic0;
    public static ItemStack magicStoneDarkMagic1;
    public static ItemStack magicStoneDarkMagic2;
    public static ItemStack magicStoneDarkMagic3;
    public static ItemStack magicStoneDarkMagic4;

    // MagicStones: Potions path
    public static ItemStack magicStonePotion0;
    public static ItemStack magicStonePotion1;
    public static ItemStack magicStonePotion2;
    public static ItemStack magicStonePotion3;
    public static ItemStack magicStonePotion4;

    // MagicStones: Beasts and Creatures path
    public static ItemStack magicStoneBeastsAndCreatures0;
    public static ItemStack magicStoneBeastsAndCreatures1;
    public static ItemStack magicStoneBeastsAndCreatures2;
    public static ItemStack magicStoneBeastsAndCreatures3;
    public static ItemStack magicStoneBeastsAndCreatures4;

    public static ArrayList<ItemStack> allStones = new ArrayList<>();

    private static final String[][][] moveDeclaration = {
            {
                    {
                            "Move 1: info",
                            "Move 2: info",
                            "Move 3: info"
                    },
                    {
                            "Passive: info",
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
                            "Passive: info",
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
                            "Passive: info",
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    }
            },
            {
                    {
                            "Move 1: info",
                            "Move 2: info",
                            "Move 3: info"
                    },
                    {
                            "Passive: info",
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
                            "Passive: info",
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
                            "Passive: info",
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    }
            },
            {
                    {
                            "Move 1: info",
                            "Move 2: info",
                            "Move 3: info"
                    },
                    {
                            "Passive: info",
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
                            "Passive: info",
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
                            "Passive: info",
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    }
            },
            {
                    {
                            "Move 1: info",
                            "Move 2: info",
                            "Move 3: info"
                    },
                    {
                            "Passive: info",
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
                            "Passive: info",
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
                            "Passive: info",
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    }
            },
            {
                    {
                            "Move 1: info",
                            "Move 2: info",
                            "Move 3: info"
                    },
                    {
                            "Passive: info",
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
                            "Passive: info",
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
                            "Passive: info",
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
                            "Passive: info",
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    }
            }
    };

    private static void createBaseStone() {
        ItemStack stack = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Base Stone");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + "This relic is the basis for many powerful");
        lore.add(ChatColor.YELLOW + "stones you can control the elements with");
        meta.setLore(lore);
        stack.setItemMeta(meta);

        baseStone = stack;

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("base-stone"), stack);
        recipe.shape(" D ", "DND", " D ");
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('N', Material.NETHERITE_BLOCK);
        Bukkit.getServer().addRecipe(recipe);
    }

    private static ItemStack createStone(String displayName, String stoneTypeLore, String moveTypeLore, int stoneType, int moveType, int numberOfMoves) {
        ItemStack stack = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + stoneTypeLore);
        if (!(moveTypeLore.equals(""))) {
            lore.add(ChatColor.YELLOW + moveTypeLore);
            lore.add(ChatColor.YELLOW + "");

            lore.add(ChatColor.YELLOW + moveDeclaration[stoneType][moveType][0]);
            for (int i = 0; i < 3; i++) {
                lore.add(ChatColor.YELLOW + moveDeclaration[stoneType][0][i]);
            }
            for (int i = 1; i <= numberOfMoves; i++) {
                lore.add(ChatColor.YELLOW + moveDeclaration[stoneType][moveType][i]);
            }
        } else {
            lore.add(ChatColor.YELLOW + "");
            for (int i = 0; i < numberOfMoves; i++) {
                lore.add(ChatColor.YELLOW + moveDeclaration[stoneType][0][i]);
            }
        }
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    public static void init() {

        // BASE STONE
        createBaseStone();

        // WATER STONES
        waterStone0 = createStone(ChatColor.BLUE + "Water Stone", "An old relic used to manipulate water in surroundings.", "", 0, 0, 1);
        waterStone1 = createStone(ChatColor.BLUE + "Water Stone Lv2", "An old relic used to manipulate water in surroundings.", "", 0, 0, 2);
        waterStone2 = createStone(ChatColor.BLUE + "Water Stone Lv3", "An old relic used to manipulate water in surroundings.", "", 0, 0, 3);
        waterStoneOcean0 = createStone(ChatColor.BLUE + "Ocean Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the behaviour of the ocean", 0, 1, 1);
        waterStoneOcean1 = createStone(ChatColor.BLUE + "Ocean Stone Lv2", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the behaviour of the ocean", 0, 1, 2);
        waterStoneOcean2 = createStone(ChatColor.BLUE + "Ocean Stone Lv3", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the behaviour of the ocean", 0, 1, 3);
        waterStoneOcean3 = createStone(ChatColor.BLUE + "Ocean Stone Lv4", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the behaviour of the ocean", 0, 1, 4);
        waterStoneOcean4 = createStone(ChatColor.BLUE + "Completed Ocean Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the behaviour of the ocean", 0, 1, 5);
        waterStoneBending0 = createStone(ChatColor.BLUE + "Waterbending Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the movement of water in close range", 0, 2, 1);
        waterStoneBending1 = createStone(ChatColor.BLUE + "Waterbending Stone Lv2", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the movement of water in close range", 0, 2, 2);
        waterStoneBending2 = createStone(ChatColor.BLUE + "Waterbending Stone Lv3", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the movement of water in close range", 0, 2, 3);
        waterStoneBending3 = createStone(ChatColor.BLUE + "Waterbending Stone Lv4", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the movement of water in close range", 0, 2, 4);
        waterStoneBending4 = createStone(ChatColor.BLUE + "Completed Waterbending Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the movement of water in close range", 0, 2, 5);
        waterStoneIce0 = createStone(ChatColor.BLUE + "Waterbending Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to use ice in your advantage", 0, 3, 1);
        waterStoneIce1 = createStone(ChatColor.BLUE + "Waterbending Stone Lv2", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to use ice in your advantage", 0, 3, 2);
        waterStoneIce2 = createStone(ChatColor.BLUE + "Waterbending Stone Lv3", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to use ice in your advantage", 0, 3, 3);
        waterStoneIce3 = createStone(ChatColor.BLUE + "Waterbending Stone Lv4", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to use ice in your advantage", 0, 3, 4);
        waterStoneIce4 = createStone(ChatColor.BLUE + "Completed Waterbending Stone", "An old relic used to manipulate water in surroundings.",  "This relic magical powers are especially useful to use ice in your advantage", 0, 3, 5);

        // FIRE STONES
        fireStone0 = createStone(ChatColor.RED + "Fire Stone", "An old relic used to utilize the power of fire.", "", 1, 0, 1);
        fireStone1 = createStone(ChatColor.RED + "Fire Stone Lv 2", "An old relic used to utilize the power of fire.", "", 1, 0, 2);
        fireStone2 = createStone(ChatColor.RED + "Fire Stone Lv3", "An old relic used to utilize the power of fire.", "", 1, 0, 3);
        fireStoneLava0 = createStone(ChatColor.RED + "Lava Stone", "An old relic used to utilize the power of fire.", "The powers of this relic are specialized to influence the flow of lava in your vicinity", 1, 1, 1);
        fireStoneLava1 = createStone(ChatColor.RED + "Lava Stone Lv2", "An old relic used to utilize the power of fire.", "The powers of this relic are specialized to influence the flow of lava in your vicinity", 1, 1, 2);
        fireStoneLava2 = createStone(ChatColor.RED + "Lava Stone Lv3", "An old relic used to utilize the power of fire.", "The powers of this relic are specialized to influence the flow of lava in your vicinity", 1, 1, 3);
        fireStoneLava3 = createStone(ChatColor.RED + "Lava Stone Lv4", "An old relic used to utilize the power of fire.", "The powers of this relic are specialized to influence the flow of lava in your vicinity", 1, 1, 4);
        fireStoneLava4 = createStone(ChatColor.RED + "Completed Lava Stone", "An old relic used to utilize the power of fire.", "The powers of this relic are specialized to influence the flow of lava in your vicinity", 1, 1, 5);
        fireStoneExplosion0 = createStone(ChatColor.RED + "Explosion Stone", "An old relic used to utilize the power of fire.", "The powers of this relic are used to create explosions", 1, 2, 1);
        fireStoneExplosion1 = createStone(ChatColor.RED + "Explosion Stone Lv2", "An old relic used to utilize the power of fire.", "The powers of this relic are used to create explosions", 1, 2, 2);
        fireStoneExplosion2 = createStone(ChatColor.RED + "Explosion Stone Lv3", "An old relic used to utilize the power of fire.", "The powers of this relic are used to create explosions", 1, 2, 3);
        fireStoneExplosion3 = createStone(ChatColor.RED + "Explosion Stone Lv4", "An old relic used to utilize the power of fire.", "The powers of this relic are used to create explosions", 1, 2, 4);
        fireStoneExplosion4 = createStone(ChatColor.RED + "Completed Explosion Stone", "An old relic used to utilize the power of fire.", "The powers of this relic are used to create explosions", 1, 2, 5);
        fireStoneHellFire0 = createStone(ChatColor.RED + "Hellfire Stone", "An old relic used to utilize the power of fire.", "This path is an expansion on the fire related moves and aims on high damage", 1, 2, 1);
        fireStoneHellFire1 = createStone(ChatColor.RED + "Hellfire Stone Lv2", "An old relic used to utilize the power of fire.", "This path is an expansion on the fire related moves and aims on high damage", 1, 2, 2);
        fireStoneHellFire2 = createStone(ChatColor.RED + "Hellfire Stone Lv3", "An old relic used to utilize the power of fire.", "This path is an expansion on the fire related moves and aims on high damage", 1, 2, 3);
        fireStoneHellFire3 = createStone(ChatColor.RED + "Hellfire Stone Lv4", "An old relic used to utilize the power of fire.", "This path is an expansion on the fire related moves and aims on high damage", 1, 2, 4);
        fireStoneHellFire4 = createStone(ChatColor.RED + "Completed Hellfire Stone", "An old relic used to utilize the power of fire.", "This path is an expansion on the fire related moves and aims on high damage", 1, 2, 5);

        // WIND STONES
        windStone0 = createStone(ChatColor.WHITE + "Wind Stone", "An old relic that befriends you with the air around you", "", 2, 0, 1);
        windStone1 = createStone(ChatColor.WHITE + "Wind Stone Lv2", "An old relic that befriends you with the air around you", "", 2, 0, 2);
        windStone2 = createStone(ChatColor.WHITE + "Wind Stone Lv3", "An old relic that befriends you with the air around you", "", 2, 0, 3);
        windStoneAgility0 = createStone(ChatColor.WHITE + "Agility Stone", "An old relic that befriends you with the air around you", "You use the powers of the agility stone to make you more agile", 2, 1, 1);
        windStoneAgility1 = createStone(ChatColor.WHITE + "Agility Stone Lv2", "An old relic that befriends you with the air around you", "You use the powers of the agility stone to make you more agile", 2, 1, 2);
        windStoneAgility2 = createStone(ChatColor.WHITE + "Agility Stone Lv3", "An old relic that befriends you with the air around you", "You use the powers of the agility stone to make you more agile", 2, 1, 3);
        windStoneAgility3 = createStone(ChatColor.WHITE + "Agility Stone Lv4", "An old relic that befriends you with the air around you", "You use the powers of the agility stone to make you more agile", 2, 1, 4);
        windStoneAgility4 = createStone(ChatColor.WHITE + "Completed Agility Stone", "An old relic that befriends you with the air around you", "You use the powers of the agility stone to make you more agile", 2, 1, 5);
        windStoneBending0 = createStone(ChatColor.WHITE + "Airbending Stone", "An old relic that befriends you with the air around you", "The airbending stone makes it possible to move the air around you and use it as an offense", 2, 2, 1);
        windStoneBending1 = createStone(ChatColor.WHITE + "Airbending Stone Lv2", "An old relic that befriends you with the air around you", "The airbending stone makes it possible to move the air around you and use it as an offense", 2, 2, 2);
        windStoneBending2 = createStone(ChatColor.WHITE + "Airbending Stone Lv3", "An old relic that befriends you with the air around you", "The airbending stone makes it possible to move the air around you and use it as an offense", 2, 2, 3);
        windStoneBending3 = createStone(ChatColor.WHITE + "Airbending Stone Lv4", "An old relic that befriends you with the air around you", "The airbending stone makes it possible to move the air around you and use it as an offense", 2, 2, 4);
        windStoneBending4 = createStone(ChatColor.WHITE + "Completed Airbending Stone", "An old relic that befriends you with the air around you", "The airbending stone makes it possible to move the air around you and use it as an offense", 2, 2, 5);
        windStoneWeather0 = createStone(ChatColor.WHITE + "Weather Stone", "An old relic that befriends you with the air around you", "The weather stone gives you the possibility to alter with the weather", 2, 3, 1);
        windStoneWeather1 = createStone(ChatColor.WHITE + "Weather Stone Lv2", "An old relic that befriends you with the air around you", "The weather stone gives you the possibility to alter with the weather", 2, 3, 2);
        windStoneWeather2 = createStone(ChatColor.WHITE + "Weather Stone Lv3", "An old relic that befriends you with the air around you", "The weather stone gives you the possibility to alter with the weather", 2, 3, 3);
        windStoneWeather3 = createStone(ChatColor.WHITE + "Weather Stone Lv4", "An old relic that befriends you with the air around you", "The weather stone gives you the possibility to alter with the weather", 2, 3, 4);
        windStoneWeather4 = createStone(ChatColor.WHITE + "Completed Weather Stone", "An old relic that befriends you with the air around you", "The weather stone gives you the possibility to alter with the weather", 2, 3, 5);


        // EARTH STONES
        earthStone0 = createStone(ChatColor.DARK_GRAY + "Earth Stone", "An old relic that makes it possible to alter with the earth around you", "", 3, 0, 1);
        earthStone1 = createStone(ChatColor.DARK_GRAY + "Earth Stone Lv2", "An old relic that makes it possible to alter with the earth around you", "", 3, 0, 2);
        earthStone2 = createStone(ChatColor.DARK_GRAY + "Earth Stone Lv3", "An old relic that makes it possible to alter with the earth around you", "", 3, 0, 3);
        earthStoneDefense0 = createStone(ChatColor.DARK_GRAY + "Defense Stone", "An old relic that makes it possible to alter with the earth around you", "Holding this stone makes you harder than rock and gives you defensive abilities", 3, 1, 1);
        earthStoneDefense1 = createStone(ChatColor.DARK_GRAY + "Defense Stone Lv2", "An old relic that makes it possible to alter with the earth around you", "Holding this stone makes you harder than rock and gives you defensive abilities", 3, 1, 2);
        earthStoneDefense2 = createStone(ChatColor.DARK_GRAY + "Defense Stone Lv3", "An old relic that makes it possible to alter with the earth around you", "Holding this stone makes you harder than rock and gives you defensive abilities", 3, 1, 3);
        earthStoneDefense3 = createStone(ChatColor.DARK_GRAY + "Defense Stone Lv4", "An old relic that makes it possible to alter with the earth around you", "Holding this stone makes you harder than rock and gives you defensive abilities", 3, 1, 4);
        earthStoneDefense4 = createStone(ChatColor.DARK_GRAY + "Completed Defense Stone", "An old relic that makes it possible to alter with the earth around you", "Holding this stone makes you harder than rock and gives you defensive abilities", 3, 1, 5);
        earthStoneBending0 = createStone(ChatColor.DARK_GRAY + "Earthbending Stone", "An old relic that makes it possible to alter with the earth around you", "With this stone you can terraform the terrain or even use the terrain offensive against your enemies", 3, 2, 1);
        earthStoneBending1 = createStone(ChatColor.DARK_GRAY + "Earthbending Stone Lv2", "An old relic that makes it possible to alter with the earth around you", "With this stone you can terraform the terrain or even use the terrain offensive against your enemies", 3, 2, 2);
        earthStoneBending2 = createStone(ChatColor.DARK_GRAY + "Earthbending Stone Lv3", "An old relic that makes it possible to alter with the earth around you", "With this stone you can terraform the terrain or even use the terrain offensive against your enemies", 3, 2, 3);
        earthStoneBending3 = createStone(ChatColor.DARK_GRAY + "Earthbending Stone Lv4", "An old relic that makes it possible to alter with the earth around you", "With this stone you can terraform the terrain or even use the terrain offensive against your enemies", 3, 2, 4);
        earthStoneBending4 = createStone(ChatColor.DARK_GRAY + "Completed Earthbending Stone", "An old relic that makes it possible to alter with the earth around you", "With this stone you can terraform the terrain or even use the terrain offensive against your enemies", 3, 2, 5);
        earthStoneMining0 = createStone(ChatColor.DARK_GRAY + "Mining Stone", "An old relic that makes it possible to alter with the earth around you", "Using this relic and its abilities, mining gets a lot easier", 3, 3, 1);
        earthStoneMining1 = createStone(ChatColor.DARK_GRAY + "Mining Stone Lv2", "An old relic that makes it possible to alter with the earth around you", "Using this relic and its abilities, mining gets a lot easier", 3, 3, 2);
        earthStoneMining2 = createStone(ChatColor.DARK_GRAY + "Mining Stone Lv3", "An old relic that makes it possible to alter with the earth around you", "Using this relic and its abilities, mining gets a lot easier", 3, 3, 3);
        earthStoneMining3 = createStone(ChatColor.DARK_GRAY + "Mining Stone Lv4", "An old relic that makes it possible to alter with the earth around you", "Using this relic and its abilities, mining gets a lot easier", 3, 3, 4);
        earthStoneMining4 = createStone(ChatColor.DARK_GRAY + "Completed Mining Stone", "An old relic that makes it possible to alter with the earth around you", "Using this relic and its abilities, mining gets a lot easier", 3, 3, 5);

        // MAGIC STONES
        magicStone0 = createStone(ChatColor.DARK_PURPLE + "Magic Stone", "The most extraordinary base stone of them all", "", 4, 0, 1);
        magicStone1 = createStone(ChatColor.DARK_PURPLE + "Magic Stone", "The most extraordinary base stone of them all", "", 4, 0, 2);
        magicStone2 = createStone(ChatColor.DARK_PURPLE + "Magic Stone", "The most extraordinary base stone of them all", "", 4, 0, 3);
        magicStoneLife0 = createStone(ChatColor.DARK_PURPLE + "Magic Stone", "The most extraordinary base stone of them all", "Giving life, takes life...", 4, 1, 1);
        magicStoneLife1 = createStone(ChatColor.DARK_PURPLE + "Magic Stone Lv2", "The most extraordinary base stone of them all", "Giving life, takes life...", 4, 1, 2);
        magicStoneLife2 = createStone(ChatColor.DARK_PURPLE + "Magic Stone Lv3", "The most extraordinary base stone of them all", "Giving life, takes life...", 4, 1, 3);
        magicStoneLife3 = createStone(ChatColor.DARK_PURPLE + "Magic Stone Lv4", "The most extraordinary base stone of them all", "Giving life, takes life...", 4, 1, 4);
        magicStoneLife4 = createStone(ChatColor.DARK_PURPLE + "Completed Magic Stone", "The most extraordinary base stone of them all", "Giving life, takes life...", 4, 1, 5);
        magicStoneDarkMagic0 = createStone(ChatColor.BLACK+ "Dark Magic Stone", "The most extraordinary base stone of them all", "This relic contains more energy most can handle", 4, 2, 1);
        magicStoneDarkMagic1 = createStone(ChatColor.BLACK + "Dark Magic Stone Lv2", "The most extraordinary base stone of them all", "Darkness is everywhere", 4, 2, 2);
        magicStoneDarkMagic2 = createStone(ChatColor.BLACK + "Dark Magic Stone Lv3", "The most extraordinary base stone of them all", "Darkness is everywhere", 4, 2, 3);
        magicStoneDarkMagic3 = createStone(ChatColor.BLACK + "Dark Magic Stone Lv4", "The most extraordinary base stone of them all", "Darkness is everywhere", 4, 2, 4);
        magicStoneDarkMagic4 = createStone(ChatColor.BLACK + "Completed Dark Magic Stone", "The most extraordinary base stone of them all", "Darkness is everywhere", 4, 2, 5);
        magicStonePotion0 = createStone(ChatColor.DARK_PURPLE + "Potion Stone", "The most extraordinary base stone of them all", "There are all kinds of potions, good ones and bad ones", 4, 3, 1);
        magicStonePotion1 = createStone(ChatColor.DARK_PURPLE + "Potion Stone Lv2", "The most extraordinary base stone of them all", "There are all kinds of potions, good ones and bad ones", 4, 3, 2);
        magicStonePotion2 = createStone(ChatColor.DARK_PURPLE + "Potion Stone Lv3", "The most extraordinary base stone of them all", "There are all kinds of potions, good ones and bad ones", 4, 3, 3);
        magicStonePotion3 = createStone(ChatColor.DARK_PURPLE + "Potion Stone Lv4", "The most extraordinary base stone of them all", "There are all kinds of potions, good ones and bad ones", 4, 3, 4);
        magicStonePotion4 = createStone(ChatColor.DARK_PURPLE + "Completed Potion Stone", "The most extraordinary base stone of them all", "There are all kinds of potions, good ones and bad ones", 4, 3, 5);
        magicStoneBeastsAndCreatures0 = createStone(ChatColor.DARK_PURPLE + "Beasts and Creatures Stone", "The most extraordinary base stone of them all", "Be careful for mutations on the wrong creatures", 4, 4, 1);
        magicStoneBeastsAndCreatures1 = createStone(ChatColor.DARK_PURPLE + "Beasts and Creatures Stone Lv2", "The most extraordinary base stone of them all", "Be careful for mutations on the wrong creatures", 4, 4, 2);
        magicStoneBeastsAndCreatures2 = createStone(ChatColor.DARK_PURPLE + "Beasts and Creatures Stone Lv3", "The most extraordinary base stone of them all", "Be careful for mutations on the wrong creatures", 4, 4, 3);
        magicStoneBeastsAndCreatures3 = createStone(ChatColor.DARK_PURPLE + "Beasts and Creatures Stone Lv4", "The most extraordinary base stone of them all", "Be careful for mutations on the wrong creatures", 4, 4, 4);
        magicStoneBeastsAndCreatures4 = createStone(ChatColor.DARK_PURPLE + "Completed Beasts and Creatures Stone", "The most extraordinary base stone of them all", "Be careful for mutations on the wrong creatures", 4, 4, 5);

        allStones.addAll(Arrays.asList(
                waterStone0,
                waterStone1,
                waterStone2,
                waterStoneOcean0,
                waterStoneOcean1,
                waterStoneOcean2,
                waterStoneOcean3,
                waterStoneOcean4,
                waterStoneBending0,
                waterStoneBending1,
                waterStoneBending2,
                waterStoneBending3,
                waterStoneBending4,
                waterStoneIce0,
                waterStoneIce1,
                waterStoneIce2,
                waterStoneIce3,
                waterStoneIce4,
                fireStone0,
                fireStone1,
                fireStone2,
                fireStoneLava0,
                fireStoneLava1,
                fireStoneLava2,
                fireStoneLava3,
                fireStoneLava4,
                fireStoneExplosion0,
                fireStoneExplosion1,
                fireStoneExplosion2,
                fireStoneExplosion3,
                fireStoneExplosion4,
                fireStoneHellFire0,
                fireStoneHellFire1,
                fireStoneHellFire2,
                fireStoneHellFire3,
                fireStoneHellFire4,
                windStone0,
                windStone1,
                windStone2,
                windStoneAgility0,
                windStoneAgility1,
                windStoneAgility2,
                windStoneAgility3,
                windStoneAgility4,
                windStoneBending0,
                windStoneBending1,
                windStoneBending2,
                windStoneBending3,
                windStoneBending4,
                windStoneWeather0,
                windStoneWeather1,
                windStoneWeather2,
                windStoneWeather3,
                windStoneWeather4,
                earthStone0,
                earthStone1,
                earthStone2,
                earthStoneDefense0,
                earthStoneDefense1,
                earthStoneDefense2,
                earthStoneDefense3,
                earthStoneDefense4,
                earthStoneBending0,
                earthStoneBending1,
                earthStoneBending2,
                earthStoneBending3,
                earthStoneBending4,
                earthStoneMining0,
                earthStoneMining1,
                earthStoneMining2,
                earthStoneMining3,
                earthStoneMining4,
                magicStone0,
                magicStone1,
                magicStone2,
                magicStoneLife0,
                magicStoneLife1,
                magicStoneLife2,
                magicStoneLife3,
                magicStoneLife4,
                magicStoneDarkMagic0,
                magicStoneDarkMagic1,
                magicStoneDarkMagic2,
                magicStoneDarkMagic3,
                magicStoneDarkMagic4,
                magicStonePotion0,
                magicStonePotion1,
                magicStonePotion2,
                magicStonePotion3,
                magicStonePotion4,
                magicStoneBeastsAndCreatures0,
                magicStoneBeastsAndCreatures1,
                magicStoneBeastsAndCreatures2,
                magicStoneBeastsAndCreatures3,
                magicStoneBeastsAndCreatures4
        ));
    }
}
