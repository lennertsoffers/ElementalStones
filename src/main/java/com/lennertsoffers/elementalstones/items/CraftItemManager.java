package com.lennertsoffers.elementalstones.items;

import com.lennertsoffers.elementalstones.customClasses.tools.StringListTools;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftItemManager {

    // Base items
    public static ItemStack BABY_ZOMBIE_HIDE;
    public static ItemStack ROTTEN_APPLE;
    public static ItemStack INSECT;
    public static ItemStack BAT;
    public static ItemStack THYME;
    public static ItemStack OREGANO;
    public static ItemStack DILL;
    public static ItemStack ROSEMARY;
    public static ItemStack GOLDEN_FEATHER;
    public static ItemStack DEAD_FLOWER;
    public static ItemStack TWIG;
    public static ItemStack SOUL_OF_EVOKER;
    public static ItemStack VILLAGER_BLOOD;

    // Craft Items
    public static ItemStack VOODOO_DOLL;
    public static ItemStack SHIP_IN_BOTTLE;
    public static ItemStack PALANTIR;
    public static ItemStack BLOOD_AND_QUIL;
    public static ItemStack BUNDLE_OF_HERBS;
    public static ItemStack MYSTERY_POTION;
    public static ItemStack POISONED_APPLE;
    public static ItemStack CARNIVOROUS_PLANT;
    public static ItemStack SCENTED_CANDLE;


    // Create Item
    private static int itemId = 0;
    private static ItemStack createItem(String displayName, String lore) {
        ItemStack itemStack = new ItemStack(Material.BOWL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(displayName);
            StringListTools.formatLore(lore, ChatColor.GRAY);
            itemMeta.setCustomModelData(itemId);
            itemId++;
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


    // Set Items
    public static void init() {
        BABY_ZOMBIE_HIDE = createItem("Baby Zombie Hide", "");
        ROTTEN_APPLE = createItem("Rotten Apple", "");
        INSECT = createItem("Insect", "");
        BAT = createItem("Bat", "");
        THYME = createItem("Thyme", "");
        OREGANO = createItem("Oregano", "");
        DILL = createItem("Dill", "");
        ROSEMARY = createItem("Rosemary", "");
        GOLDEN_FEATHER = createItem("Golden Feather", "");
        DEAD_FLOWER = createItem("Dead Flower", "");
        TWIG = createItem("Twig", "");
        SOUL_OF_EVOKER = createItem("Soul Of Evoker", "");
        VILLAGER_BLOOD = createItem("Blood", "");

        VOODOO_DOLL = createItem("Voodoo Doll", "");
        SHIP_IN_BOTTLE = createItem("Ship In Bottle", "");
        PALANTIR = createItem("Palantir", "");
        BLOOD_AND_QUIL = createItem("Blood And Quil", "");
        BUNDLE_OF_HERBS = createItem("Bundle Of Herbs", "");
        MYSTERY_POTION = createItem("Mystery Potion", "");
        POISONED_APPLE = createItem("Poisoned Apple", "");
        CARNIVOROUS_PLANT = createItem("Carnivorous Plant", "");
        SCENTED_CANDLE = createItem("Scented Candle", "");
    }
}
