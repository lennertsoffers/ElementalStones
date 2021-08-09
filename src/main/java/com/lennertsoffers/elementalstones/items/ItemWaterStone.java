package com.lennertsoffers.elementalstones.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
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

    private static final String[][] moveDeclaration = {
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
    };

    private static ItemStack createWaterStone(String displayName, String additionalLore, int waterStoneType, int numberOfMoves) {
        ItemStack stack = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + displayName);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + "An old relic used to manipulate water in surroundings.");
        if (!(additionalLore.equals(""))) {
            lore.add(ChatColor.YELLOW + additionalLore);
            for (int i = 0; i < 3; i++) {
                lore.add(ChatColor.YELLOW + moveDeclaration[0][i]);
            }
            for (int i = 0; i < numberOfMoves; i++) {
                lore.add(ChatColor.YELLOW + moveDeclaration[waterStoneType][i]);
            }
        } else {
            for (int i = 0; i < numberOfMoves; i++) {
                lore.add(ChatColor.YELLOW + moveDeclaration[0][i]);
            }
        }
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }


    public static void init() {
        waterStone0 = createWaterStone("Water Stone", "", 0, 1);
        waterStone1 = createWaterStone("Water Stone Lv2", "", 0, 2);
        waterStone2 = createWaterStone("Water Stone Lv3", "", 0, 3);
        waterStoneOcean0 = createWaterStone("Ocean Stone", "This relic magical powers are especially useful to manipulate the behaviour of the ocean", 1, 1);
        waterStoneOcean1 = createWaterStone("Ocean Stone", "This relic magical powers are especially useful to manipulate the behaviour of the ocean", 1, 2);
        waterStoneOcean2 = createWaterStone("Ocean Stone", "This relic magical powers are especially useful to manipulate the behaviour of the ocean", 1, 3);
        waterStoneOcean3 = createWaterStone("Ocean Stone", "This relic magical powers are especially useful to manipulate the behaviour of the ocean", 1, 4);
        waterStoneOcean4 = createWaterStone("Ocean Stone", "This relic magical powers are especially useful to manipulate the behaviour of the ocean", 1, 5);
        waterStoneBending0 = createWaterStone("Waterbending Stone", "This relic magical powers are especially useful to manipulate the movement of water in close range", 2, 1);
        waterStoneBending1 = createWaterStone("Waterbending Stone", "This relic magical powers are especially useful to manipulate the movement of water in close range", 2, 2);
        waterStoneBending2 = createWaterStone("Waterbending Stone", "This relic magical powers are especially useful to manipulate the movement of water in close range", 2, 3);
        waterStoneBending3 = createWaterStone("Waterbending Stone", "This relic magical powers are especially useful to manipulate the movement of water in close range", 2, 4);
        waterStoneBending4 = createWaterStone("Waterbending Stone", "This relic magical powers are especially useful to manipulate the movement of water in close range", 2, 5);
        waterStoneIce0 = createWaterStone("Waterbending Stone", "This relic magical powers are especially useful to use ice in your advantage", 3, 1);
        waterStoneIce1 = createWaterStone("Waterbending Stone", "This relic magical powers are especially useful to use ice in your advantage", 3, 2);
        waterStoneIce2 = createWaterStone("Waterbending Stone", "This relic magical powers are especially useful to use ice in your advantage", 3, 3);
        waterStoneIce3 = createWaterStone("Waterbending Stone", "This relic magical powers are especially useful to use ice in your advantage", 3, 4);
        waterStoneIce4 = createWaterStone("Waterbending Stone", "This relic magical powers are especially useful to use ice in your advantage", 3, 5);
    }

}
