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
    // FireStones: Lava path
    // FireStones: Explosion path
    // FireStones: Hellfire path

    // WIND STONES
    // WindStones: Agility path
    // WindStones: Speed path
    // WindStones: Weather path

    // EARTH STONES
    // EarthStones: Defense path
    // EarthStones: Earthbending path
    // EarthStones: Sandbending path

    // MAGIC STONES
    // MagicStones: Life path
    // MagicStones: Energy path
    // MagicStones: Potions path
    // MagicStones: Beasts and Creatures path

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
        waterStoneOcean1 = createStone("Ocean Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the behaviour of the ocean", 0, 1, 2);
        waterStoneOcean2 = createStone("Ocean Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the behaviour of the ocean", 0, 1, 3);
        waterStoneOcean3 = createStone("Ocean Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the behaviour of the ocean", 0, 1, 4);
        waterStoneOcean4 = createStone("Ocean Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the behaviour of the ocean", 0, 1, 5);
        waterStoneBending0 = createStone("Waterbending Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the movement of water in close range", 0, 2, 1);
        waterStoneBending1 = createStone("Waterbending Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the movement of water in close range", 0, 2, 2);
        waterStoneBending2 = createStone("Waterbending Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the movement of water in close range", 0, 2, 3);
        waterStoneBending3 = createStone("Waterbending Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the movement of water in close range", 0, 2, 4);
        waterStoneBending4 = createStone("Waterbending Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to manipulate the movement of water in close range", 0, 2, 5);
        waterStoneIce0 = createStone("Waterbending Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to use ice in your advantage", 0, 3, 1);
        waterStoneIce1 = createStone("Waterbending Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to use ice in your advantage", 0, 3, 2);
        waterStoneIce2 = createStone("Waterbending Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to use ice in your advantage", 0, 3, 3);
        waterStoneIce3 = createStone("Waterbending Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to use ice in your advantage", 0, 3, 4);
        waterStoneIce4 = createStone("Waterbending Stone", "An old relic used to manipulate water in surroundings.", "This relic magical powers are especially useful to use ice in your advantage", 0, 3, 5);
    }

}
