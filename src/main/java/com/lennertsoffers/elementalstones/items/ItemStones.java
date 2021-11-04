package com.lennertsoffers.elementalstones.items;

import com.lennertsoffers.elementalstones.customClasses.tools.StringListTools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemFlag;
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

    // FireStones: Hellfire path
    public static ItemStack fireStoneHellFire0;
    public static ItemStack fireStoneHellFire1;
    public static ItemStack fireStoneHellFire2;
    public static ItemStack fireStoneHellFire3;
    public static ItemStack fireStoneHellFire4;



    // AIR STONES
    // AirStones: Default
    public static ItemStack airStone0;
    public static ItemStack airStone1;
    public static ItemStack airStone2;

    // AirStones: Agility path
    public static ItemStack airStoneAgility0;
    public static ItemStack airStoneAgility1;
    public static ItemStack airStoneAgility2;
    public static ItemStack airStoneAgility3;
    public static ItemStack airStoneAgility4;

    // AirStones: Bending path
    public static ItemStack airStoneBending0;
    public static ItemStack airStoneBending1;
    public static ItemStack airStoneBending2;
    public static ItemStack airStoneBending3;
    public static ItemStack airStoneBending4;



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



    // Lists of stones
    public static ArrayList<ItemStack> allStones = new ArrayList<>();
    public static ArrayList<ItemStack> waterStones = new ArrayList<>();
    public static ArrayList<ItemStack> fireStones = new ArrayList<>();
    public static ArrayList<ItemStack> airStones = new ArrayList<>();
    public static ArrayList<ItemStack> earthStones = new ArrayList<>();



    // Id of the current stone
    private static int stoneId = 0;



    // Move declarations
    private static final String[][][] moveDeclaration =
            {
                    {
                            {
                                    "Move 1: Splash",
                                    "Move 2: Dolphin Dive",
                                    "Move 3: Water Spear"
                            },
                            {
                                    "Passive1: Deep Breath",
                                    "Passive2: Water Walker",
                                    "Move 4: Bubblebeam",
                                    "Move 5: Healing Water",
                                    "Move 6: Puffer Beam",
                                    "Move 7: Wave",
                                    "Move 8: Water Wall"
                            },
                            {
                                    "Passive: Frost Walker",
                                    "Move 4: Ice Shards",
                                    "Move 5: Ice Spear",
                                    "Move 6: Snow Stomp",
                                    "Move 7: Deep Freeze",
                                    "Move 8: Ice Beam"
                            }
                    },
                    {
                            {
                                    "Move 1: A-Quick-Snack",
                                    "Move 2: Floating Fire",
                                    "Move 3: Firefly"
                            },
                            {
                                    "Passive1: Lava Walker",
                                    "Passive2: Magma Master",
                                    "Move 4: Reverse Logic",
                                    "Move 5: Lava Wave",
                                    "Move 6: Rift",
                                    "Move 7: Lava Burst",
                                    "Move 8: Lava Rider"
                            },
                            {
                                    "Passive: Friendly Fire",
                                    "Move 4: Fire Track",
                                    "Move 5: Fire Blast",
                                    "Move 6: Into the Underworld",
                                    "Move 7: Fire Beam",
                                    "Move 8: Dragons Breath"
                            }
                    },
                    {
                            {
                                    "Move 1: Air Ball",
                                    "Move 2: A(i)r(ea) Control",
                                    "Move 3: Suction"
                            },
                            {
                                    "Passive: Double Jump",
                                    "Move 4: Forwards Dash",
                                    "Move 5: Backwards Dash",
                                    "Move 6: Smoke Ball",
                                    "Move 7: Charge Jump",
                                    "Move 8: Hyperspeed"
                            },
                            {
                                    "Passive1: Feather Falling",
                                    "Passive2: Speed Boost",
                                    "Move 4: Air Slash",
                                    "Move 5: Tracking Blade",
                                    "Move 6: Air Cloak",
                                    "Move 7: Tornado",
                                    "Move 8: Levitate"
                            }
                    },
                    {
                            {
                                    "Move 1: Stone Pillar",
                                    "Move 2: Flying Rock",
                                    "Move 3: Bunker"
                            },
                            {
                                    "Passive: Stone Resistance",
                                    "Move 4: Smoke Screen",
                                    "Move 5: Better Gear",
                                    "Move 6: Wall",
                                    "Move 7: Shockwave",
                                    "Move 8: Last Chance"
                            },
                            {
                                    "Passive: Joinker",
                                    "Move 4: Rock Throw",
                                    "Move 5: Stomp",
                                    "Move 6: Earth Prison",
                                    "Move 7: Earth Wave",
                                    "Move 8: Reverse Turret"
                            }
            }
    };



    // Build Methods
    // Method to build base stone
    private static void createBaseStone() {
        ItemStack stack = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.GOLD + "Base Stone");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.YELLOW + "This relic is the basis for many powerful");
            lore.add(ChatColor.YELLOW + "stones you can control the elements with");
            meta.setLore(lore);
            meta.setCustomModelData(stoneId);
            stoneId++;
            stack.setItemMeta(meta);
        }
        baseStone = stack;
    }

    // Method to build stone
    private static ItemStack createStone(String displayName, String stoneTypeLore, String moveTypeLore, int stoneType, int moveType, int numberOfMoves) {
        ItemStack stack = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = stack.getItemMeta();

        if (meta != null) {
            // Set display name of item
            meta.setDisplayName(displayName);

            // Set stone type lore of item
            List<String> lore = new ArrayList<>(StringListTools.formatLore(stoneTypeLore, ChatColor.GRAY));

            lore.add("");

            if (!(moveTypeLore.equals(""))) {
                // Set path lore of item
                lore.addAll(StringListTools.formatLore(moveTypeLore, ChatColor.GRAY));
                lore.add("");

                // Set passive declaration
                int firstMoveIndex = 1;
                lore.add(ChatColor.YELLOW + moveDeclaration[stoneType][moveType][0]);
                if (moveDeclaration[stoneType][moveType][1].contains("Passive")) {
                    lore.add(ChatColor.YELLOW + moveDeclaration[stoneType][moveType][1]);
                    firstMoveIndex = 2;
                }

                // Set base move declaration
                for (int i = 0; i < 3; i++) {
                    lore.addAll(StringListTools.formatLore(moveDeclaration[stoneType][0][i], ChatColor.YELLOW));
                }

                // Set type move declaration
                for (int i = firstMoveIndex; i <= (numberOfMoves + (firstMoveIndex - 1)); i++) {
                    lore.addAll(StringListTools.formatLore(moveDeclaration[stoneType][moveType][i], ChatColor.YELLOW));
                }
            } else {
                // Set base move declaration
                for (int i = 0; i < numberOfMoves; i++) {
                    lore.addAll(StringListTools.formatLore(moveDeclaration[stoneType][0][i], ChatColor.YELLOW));
                }
            }
            lore.add("");
            meta.setLore(lore);
            meta.setCustomModelData(stoneId);
            stoneId++;
            stack.setItemMeta(meta);
        }
        return stack;
    }

    public static void init() {

        // BASE STONE
        createBaseStone();



        // WATER STONES
        String waterStoneTypeLore = "An old relic used to manipulate water in surroundings";
        String moveIceTypeLore = "This relic magical powers are especially useful to use ice in your advantage";
        String moveWaterbendingTypeLore = "This relic magical powers are especially useful to manipulate the movement of water in close range";

        // WaterStones: Default
        waterStone0 = createStone(
                ChatColor.BLUE + "Water Stone",
                waterStoneTypeLore,
                "",
                0,
                0,
                1
        );
        waterStone1 = createStone(
                ChatColor.BLUE + "Water Stone Lv2",
                waterStoneTypeLore,
                "",
                0,
                0,
                2
        );
        waterStone2 = createStone(
                ChatColor.BLUE + "Water Stone Lv3",
                waterStoneTypeLore,
                "",
                0,
                0,
                3
        );

        // WaterStones: Bending path
        waterStoneBending0 = createStone(
                ChatColor.BLUE + "Waterbending Stone",
                waterStoneTypeLore,
                moveWaterbendingTypeLore,
                0,
                1,
                1
        );
        waterStoneBending1 = createStone(
                ChatColor.BLUE + "Waterbending Stone Lv2",
                waterStoneTypeLore,
                moveWaterbendingTypeLore,
                0,
                1,
                2
        );
        waterStoneBending2 = createStone(
                ChatColor.BLUE + "Waterbending Stone Lv3",
                waterStoneTypeLore,
                moveWaterbendingTypeLore,
                0,
                1,
                3
        );
        waterStoneBending3 = createStone(
                ChatColor.BLUE + "Waterbending Stone Lv4",
                waterStoneTypeLore,
                moveWaterbendingTypeLore,
                0,
                1,
                4
        );
        waterStoneBending4 = createStone(
                ChatColor.BLUE + "Completed Waterbending Stone",
                waterStoneTypeLore,
                moveWaterbendingTypeLore,
                0,
                1,
                5
        );

        // WaterStones: Ice path
        waterStoneIce0 = createStone(
                ChatColor.BLUE + "Ice Stone",
                waterStoneTypeLore,
                moveIceTypeLore,
                0,
                2,
                1
        );
        waterStoneIce1 = createStone(
                ChatColor.BLUE + "Ice Stone Lv2",
                waterStoneTypeLore,
                moveIceTypeLore,
                0,
                2,
                2
        );
        waterStoneIce2 = createStone(
                ChatColor.BLUE + "Ice Stone Lv3",
                waterStoneTypeLore,
                moveIceTypeLore,
                0,
                2,
                3
        );
        waterStoneIce3 = createStone(
                ChatColor.BLUE + "Ice Stone Lv4",
                waterStoneTypeLore,
                moveIceTypeLore,
                0,
                2,
                4
        );
        waterStoneIce4 = createStone(
                ChatColor.BLUE + "Completed Ice Stone",
                waterStoneTypeLore,
                moveIceTypeLore,
                0,
                2,
                5
        );



        // FIRE STONES
        String fireStoneTypeLore = "An old relic used to utilize the power of fire.";
        String moveHellfireTypeLore = "This path is an expansion on the fire related moves and aims on high damage";
        String moveLavaTypeLore = "The powers of this relic are specialized to influence the flow of lava in your vicinity";
        // FireStones: Default
        fireStone0 = createStone(
                ChatColor.RED + "Fire Stone",
                fireStoneTypeLore,
                "",
                1,
                0,
                1
        );
        fireStone1 = createStone(
                ChatColor.RED + "Fire Stone Lv2",
                fireStoneTypeLore,
                "",
                1,
                0,
                2
        );
        fireStone2 = createStone(
                ChatColor.RED + "Fire Stone Lv3",
                fireStoneTypeLore,
                "",
                1,
                0,
                3
        );

        // FireStones: Lava path
        fireStoneLava0 = createStone(
                ChatColor.RED + "Lava Stone",
                fireStoneTypeLore,
                moveLavaTypeLore,
                1,
                1,
                1
        );
        fireStoneLava1 = createStone(
                ChatColor.RED + "Lava Stone Lv2",
                fireStoneTypeLore,
                moveLavaTypeLore,
                1,
                1,
                2
        );
        fireStoneLava2 = createStone(
                ChatColor.RED + "Lava Stone Lv3",
                fireStoneTypeLore,
                moveLavaTypeLore,
                1,
                1,
                3
        );
        fireStoneLava3 = createStone(
                ChatColor.RED + "Lava Stone Lv4",
                fireStoneTypeLore,
                moveLavaTypeLore,
                1,
                1,
                4
        );
        fireStoneLava4 = createStone(
                ChatColor.RED + "Completed Lava Stone",
                fireStoneTypeLore,
                moveLavaTypeLore,
                1,
                1,
                5
        );

        // FireStones: Hellfire path
        fireStoneHellFire0 = createStone(
                ChatColor.RED + "Hellfire Stone",
                fireStoneTypeLore,
                moveHellfireTypeLore,
                1,
                2,
                1
        );
        fireStoneHellFire1 = createStone(
                ChatColor.RED + "Hellfire Stone Lv2",
                fireStoneTypeLore,
                moveHellfireTypeLore,
                1,
                2,
                2
        );
        fireStoneHellFire2 = createStone(
                ChatColor.RED + "Hellfire Stone Lv3",
                fireStoneTypeLore,
                moveHellfireTypeLore,
                1,
                2,
                3
        );
        fireStoneHellFire3 = createStone(
                ChatColor.RED + "Hellfire Stone Lv4",
                fireStoneTypeLore,
                moveHellfireTypeLore,
                1,
                2,
                4
        );
        fireStoneHellFire4 = createStone(
                ChatColor.RED + "Completed Hellfire Stone",
                fireStoneTypeLore,
                moveHellfireTypeLore,
                1,
                2,
                5
        );



        // AIR STONES
        String airStoneTypeLore = "An old relic that befriends you with the air around you";
        String moveAgilityTypeLore = "You use the powers of the agility stone to make you more agile";
        String moveAirbendingTypeLore = "The airbending stone makes it possible to move the air around you and use it as an offense";
        // AirStones: Default
        airStone0 = createStone(
                ChatColor.WHITE + "Air Stone",
                airStoneTypeLore,
                "",
                2,
                0,
                1
        );
        airStone1 = createStone(
                ChatColor.WHITE + "Air Stone Lv2",
                airStoneTypeLore,
                "",
                2,
                0,
                2
        );
        airStone2 = createStone(
                ChatColor.WHITE + "Air Stone Lv3",
                airStoneTypeLore,
                "",
                2,
                0,
                3
        );

        // AirStones: Agility path
        airStoneAgility0 = createStone(
                ChatColor.WHITE + "Agility Stone",
                airStoneTypeLore,
                moveAgilityTypeLore,
                2,
                1,
                1
        );
        airStoneAgility1 = createStone(
                ChatColor.WHITE + "Agility Stone Lv2",
                airStoneTypeLore,
                moveAgilityTypeLore,
                2,
                1,
                2
        );
        airStoneAgility2 = createStone(
                ChatColor.WHITE + "Agility Stone Lv3",
                airStoneTypeLore,
                moveAgilityTypeLore,
                2,
                1,
                3
        );
        airStoneAgility3 = createStone(
                ChatColor.WHITE + "Agility Stone Lv4",
                airStoneTypeLore,
                moveAgilityTypeLore,
                2,
                1,
                4
        );
        airStoneAgility4 = createStone(
                ChatColor.WHITE + "Completed Agility Stone",
                airStoneTypeLore,
                moveAgilityTypeLore,
                2,
                1,
                5
        );

        // AirStones: Bending path
        airStoneBending0 = createStone(
                ChatColor.WHITE + "Airbending Stone",
                airStoneTypeLore,
                moveAirbendingTypeLore,
                2,
                2,
                1
        );
        airStoneBending1 = createStone(
                ChatColor.WHITE + "Airbending Stone Lv2",
                airStoneTypeLore,
                moveAirbendingTypeLore,
                2,
                2,
                2
        );
        airStoneBending2 = createStone(
                ChatColor.WHITE + "Airbending Stone Lv3",
                airStoneTypeLore,
                moveAirbendingTypeLore,
                2,
                2,
                3
        );
        airStoneBending3 = createStone(
                ChatColor.WHITE + "Airbending Stone Lv4",
                airStoneTypeLore,
                moveAirbendingTypeLore,
                2,
                2,
                4
        );
        airStoneBending4 = createStone(
                ChatColor.WHITE + "Completed Airbending Stone",
                airStoneTypeLore,
                moveAirbendingTypeLore,
                2,
                2,
                5
        );



        // EARTH STONES
        String earthStoneTypeLore = "An old relic that makes it possible to alter with the earth around you";
        String moveEarthbendingTypeLore = "With this stone you can terraform the terrain or even use the terrain offensive against your enemies";
        String moveDefenseTypeLore = "Holding this stone makes you harder than rock and gives you defensive abilities";
        // EarthStones: Default
        earthStone0 = createStone(
                ChatColor.DARK_GREEN + "Earth Stone",
                earthStoneTypeLore,
                "",
                3,
                0,
                1
        );
        earthStone1 = createStone(
                ChatColor.DARK_GREEN + "Earth Stone Lv2",
                earthStoneTypeLore,
                "",
                3,
                0,
                2
        );
        earthStone2 = createStone(
                ChatColor.DARK_GREEN + "Earth Stone Lv3",
                earthStoneTypeLore,
                "",
                3,
                0,
                3
        );

        // EarthStones: Defense path
        earthStoneDefense0 = createStone(
                ChatColor.DARK_GREEN + "Defense Stone",
                earthStoneTypeLore,
                moveDefenseTypeLore,
                3,
                1,
                1
        );
        earthStoneDefense1 = createStone(
                ChatColor.DARK_GREEN + "Defense Stone Lv2",
                earthStoneTypeLore,
                moveDefenseTypeLore,
                3,
                1,
                2
        );
        earthStoneDefense2 = createStone(
                ChatColor.DARK_GREEN + "Defense Stone Lv3",
                earthStoneTypeLore,
                moveDefenseTypeLore,
                3,
                1,
                3
        );
        earthStoneDefense3 = createStone(
                ChatColor.DARK_GREEN + "Defense Stone Lv4",
                earthStoneTypeLore,
                moveDefenseTypeLore,
                3,
                1,
                4
        );
        earthStoneDefense4 = createStone(
                ChatColor.DARK_GREEN + "Completed Defense Stone",
                earthStoneTypeLore,
                moveDefenseTypeLore,
                3,
                1,
                5
        );

        // EarthStones: Bending path
        earthStoneBending0 = createStone(
                ChatColor.DARK_GREEN + "Earthbending Stone",
                earthStoneTypeLore,
                moveEarthbendingTypeLore,
                3,
                2,
                1
        );
        earthStoneBending1 = createStone(
                ChatColor.DARK_GREEN + "Earthbending Stone Lv2",
                earthStoneTypeLore,
                moveEarthbendingTypeLore,
                3,
                2,
                2
        );
        earthStoneBending2 = createStone(
                ChatColor.DARK_GREEN + "Earthbending Stone Lv3",
                earthStoneTypeLore,
                moveEarthbendingTypeLore,
                3,
                2,
                3
        );
        earthStoneBending3 = createStone(
                ChatColor.DARK_GREEN + "Earthbending Stone Lv4",
                earthStoneTypeLore,
                moveEarthbendingTypeLore,
                3,
                2,
                4
        );
        earthStoneBending4 = createStone(
                ChatColor.DARK_GREEN + "Completed Earthbending Stone",
                earthStoneTypeLore,
                moveEarthbendingTypeLore,
                3,
                2,
                5
        );

        allStones.addAll(Arrays.asList(
                waterStone0,
                waterStone1,
                waterStone2,
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
                fireStoneHellFire0,
                fireStoneHellFire1,
                fireStoneHellFire2,
                fireStoneHellFire3,
                fireStoneHellFire4,
                airStone0,
                airStone1,
                airStone2,
                airStoneAgility0,
                airStoneAgility1,
                airStoneAgility2,
                airStoneAgility3,
                airStoneAgility4,
                airStoneBending0,
                airStoneBending1,
                airStoneBending2,
                airStoneBending3,
                airStoneBending4,
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
                earthStoneBending4
        ));

        waterStones.addAll(Arrays.asList(
                waterStone0,
                waterStone1,
                waterStone2,
                waterStoneBending0,
                waterStoneBending1,
                waterStoneBending2,
                waterStoneBending3,
                waterStoneBending4,
                waterStoneIce0,
                waterStoneIce1,
                waterStoneIce2,
                waterStoneIce3,
                waterStoneIce4
        ));
        fireStones.addAll(Arrays.asList(
                fireStone0,
                fireStone1,
                fireStone2,
                fireStoneLava0,
                fireStoneLava1,
                fireStoneLava2,
                fireStoneLava3,
                fireStoneLava4,
                fireStoneHellFire0,
                fireStoneHellFire1,
                fireStoneHellFire2,
                fireStoneHellFire3,
                fireStoneHellFire4
        ));
        airStones.addAll(Arrays.asList(
                airStone0,
                airStone1,
                airStone2,
                airStoneAgility0,
                airStoneAgility1,
                airStoneAgility2,
                airStoneAgility3,
                airStoneAgility4,
                airStoneBending0,
                airStoneBending1,
                airStoneBending2,
                airStoneBending3,
                airStoneBending4
        ));
        earthStones.addAll(Arrays.asList(
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
                earthStoneBending4
        ));
    }
}
