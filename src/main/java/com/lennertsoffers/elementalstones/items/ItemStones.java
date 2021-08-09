package com.lennertsoffers.elementalstones.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemStones {

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

    // EarthStones: Sandbending path
    public static ItemStack earthStoneSandBending0;
    public static ItemStack earthStoneSandBending1;
    public static ItemStack earthStoneSandBending2;
    public static ItemStack earthStoneSandBending3;
    public static ItemStack earthStoneSandBending4;

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
    public static ItemStack magicStoneEnergy0;
    public static ItemStack magicStoneEnergy1;
    public static ItemStack magicStoneEnergy2;
    public static ItemStack magicStoneEnergy3;
    public static ItemStack magicStoneEnergy4;

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

    private static final String[][][] moveDeclaration = {
            {
                    {
                            "Move 1: info",
                            "Move 2: info",
                            "Move 3: info"
                    },
                    {
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
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
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
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
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
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
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
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
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    },
                    {
                            "Move 4: info",
                            "Move 5: info",
                            "Move 6: info",
                            "Move 7: info",
                            "Move 8: info"
                    }
            }
    };

    private static ItemStack createStone(String displayName, String stoneTypeLore, String moveTypeLore, int stoneType, int moveType, int numberOfMoves) {
        ItemStack stack = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + stoneTypeLore);
        if (!(moveTypeLore.equals(""))) {
            lore.add(ChatColor.YELLOW + moveTypeLore);
            for (int i = 0; i < 3; i++) {
                lore.add(ChatColor.YELLOW + moveDeclaration[stoneType][0][i]);
            }
            for (int i = 0; i < numberOfMoves; i++) {
                lore.add(ChatColor.YELLOW + moveDeclaration[stoneType][moveType][i]);
            }
        } else {
            for (int i = 0; i < numberOfMoves; i++) {
                lore.add(ChatColor.YELLOW + moveDeclaration[stoneType][0][i]);
            }
        }
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    public static void init() {
        waterStone0 = createStone("Water Stone", "An old relic used to manipulate water in surroundings.", "", 0, 0, 1);
        waterStone1 = createStone("Water Stone Lv2", "An old relic used to manipulate water in surroundings.", "", 0, 0, 2);
        waterStone2 = createStone("Water Stone Lv3", "An old relic used to manipulate water in surroundings.", "", 0, 0, 3);
        waterStoneOcean0 = createStone("Ocean Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the behaviour of the ocean", 0, 1, 1);
        waterStoneOcean1 = createStone("Ocean Stone Lv 2", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the behaviour of the ocean", 0, 1, 2);
        waterStoneOcean2 = createStone("Ocean Stone Lv 3", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the behaviour of the ocean", 0, 1, 3);
        waterStoneOcean3 = createStone("Ocean Stone Lv 4", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the behaviour of the ocean", 0, 1, 4);
        waterStoneOcean4 = createStone("Completed Ocean Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the behaviour of the ocean", 0, 1, 5);
        waterStoneBending0 = createStone("Waterbending Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the movement of water in close range", 0, 2, 1);
        waterStoneBending1 = createStone("Waterbending Stone Lv 2", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the movement of water in close range", 0, 2, 2);
        waterStoneBending2 = createStone("Waterbending Stone Lv 3", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the movement of water in close range", 0, 2, 3);
        waterStoneBending3 = createStone("Waterbending Stone Lv 4", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the movement of water in close range", 0, 2, 4);
        waterStoneBending4 = createStone("Completed Waterbending Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the movement of water in close range", 0, 2, 5);
        waterStoneIce0 = createStone("Waterbending Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to use ice in your advantage", 0, 3, 1);
        waterStoneIce1 = createStone("Waterbending Stone Lv 2", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to use ice in your advantage", 0, 3, 2);
        waterStoneIce2 = createStone("Waterbending Stone Lv 3", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to use ice in your advantage", 0, 3, 3);
        waterStoneIce3 = createStone("Waterbending Stone Lv 4", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to use ice in your advantage", 0, 3, 4);
        waterStoneIce4 = createStone("Completed Waterbending Stone", "An old relic used to manipulate water in surroundings.",  "This relic magical powers are especially useful to use ice in your advantage", 0, 3, 5);
    }

}
