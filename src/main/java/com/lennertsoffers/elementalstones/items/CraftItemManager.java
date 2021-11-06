package com.lennertsoffers.elementalstones.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.naming.spi.ObjectFactory;

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
    public static ItemStack BLOOD;

    // Craft Items
    public static ItemStack VOODOO_DOLL;
    public static ItemStack SHIP_IN_BOTTLE;
    public static ItemStack GLASS_SPHERE;
    public static ItemStack BLOOD_AND_QUIL;
    public static ItemStack BUNDLE_OF_HERBS;
    public static ItemStack MYSTERY_POTION;
    public static ItemStack DIAMOND_RING;
    public static ItemStack POISONED_APPLE;
    public static ItemStack CARNIVOROUS_PLANT;


    // Create Item
    private void createItem() {
        ItemStack itemStack = new ItemStack(Material.BOWL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {

        }
    }

}
