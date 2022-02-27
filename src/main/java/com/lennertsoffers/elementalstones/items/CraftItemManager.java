package com.lennertsoffers.elementalstones.items;

import com.lennertsoffers.elementalstones.customClasses.models.ItemCounter;
import com.lennertsoffers.elementalstones.customClasses.models.ShamanTradeItem;
import com.lennertsoffers.elementalstones.customClasses.tools.StringListTools;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class CraftItemManager {

    // Base items
    public static ItemStack BABY_ZOMBIE_HIDE;
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
    public static ItemStack BLOOD_OF_WANDERING_TRADER;
    public static ItemStack STINGER;
    public static ItemStack HOGLIN_TUSK;
    public static ItemStack FINN;

    // Craft Items
    // Summons random boss
    public static ItemStack VOODOO_DOLL;
    // Right click to locate the nearest shipwreck
    public static ItemStack SHIP_IN_BOTTLE;
    public static ItemStack PALANTIR;
    public static ItemStack BLOOD_AND_QUIL;
    public static ItemStack BUNDLE_OF_HERBS;
    public static ItemStack CARNIVOROUS_PLANT;
    public static ItemStack SCENTED_CANDLE;
    // Gives water breathing
    public static ItemStack FINN_SOUP;
    // Right click plays raid sound
    public static ItemStack WAR_HORN;
    public static ItemStack POISONOUS_DART;
    public static ItemStack BROOM;

    // Consumables

    // Gives hunger & nausea
    public static ItemStack ROTTEN_APPLE;
    // Gives poison
    public static ItemStack POISONED_APPLE;
    // Random potionEffect
    public static ItemStack MYSTERY_POTION;
    // Turns bad potionEffect in good version
    public static ItemStack ANTIDOTE;
    // Gives luck resistance and saturation
    public static ItemStack GINGERBREAD_MAN;
    // Summons lightning
    public static ItemStack BOTTLE_OF_LIGHTNING;

    // Shards
    public static ItemStack COMMON_SHARD;
    public static ItemStack UNCOMMON_SHARD;
    public static ItemStack RARE_SHARD;
    public static ItemStack ULTRA_RARE_SHARD;
    public static ItemStack LEGENDARY_SHARD;
    
    // Path keys
    public static ItemStack WATERBENDING_SPELL;
    public static ItemStack ICE_SPELL;
    public static ItemStack EXPLOSION_SPELL;
    public static ItemStack HELLFIRE_SPELL;
    public static ItemStack AGILITY_SPELL;
    public static ItemStack AIRBENDING_SPELL;
    public static ItemStack LAVA_SPELL;
    public static ItemStack EARTHBENDING_SPELL;

    public static ArrayList<ItemStack> spells = new ArrayList<>();
    public static ArrayList<ItemStack> craftItems = new ArrayList<>();


    // Create Items

    private static ItemStack createItem(String displayName, String lore, ItemCounter itemCounter) {
        ItemStack itemStack = new ItemStack(itemCounter.getMaterial());

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            itemMeta.setDisplayName(displayName);
            StringListTools.formatLore(lore, ChatColor.GRAY);
            itemMeta.setCustomModelData(itemCounter.getAmount());

            itemCounter.createItem();
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static void init() {
        // No Effect
        BABY_ZOMBIE_HIDE = createItem("Baby Zombie Hide", "", ItemCounter.NO_EFFECT);
        INSECT = createItem("Insect", "", ItemCounter.NO_EFFECT);
        BAT = createItem("Bat", "", ItemCounter.NO_EFFECT);
        THYME = createItem("Thyme", "", ItemCounter.NO_EFFECT);
        OREGANO = createItem("Oregano", "", ItemCounter.NO_EFFECT);
        DILL = createItem("Dill", "", ItemCounter.NO_EFFECT);
        ROSEMARY = createItem("Rosemary", "", ItemCounter.NO_EFFECT);
        GOLDEN_FEATHER = createItem("Golden Feather", "", ItemCounter.NO_EFFECT);
        DEAD_FLOWER = createItem("Dead Flower", "", ItemCounter.NO_EFFECT);
        TWIG = createItem("Twig", "", ItemCounter.NO_EFFECT);
        SOUL_OF_EVOKER = createItem("Soul Of Evoker", "", ItemCounter.NO_EFFECT);
        BLOOD_OF_WANDERING_TRADER = createItem("Blood Of Wandering Trader", "", ItemCounter.NO_EFFECT);
        STINGER = createItem("Stinger", "", ItemCounter.NO_EFFECT);
        HOGLIN_TUSK = createItem("Hoglin Tusk", "", ItemCounter.NO_EFFECT);
        FINN = createItem("Finn", "", ItemCounter.NO_EFFECT);
        SHIP_IN_BOTTLE = createItem("Ship In Bottle", "", ItemCounter.NO_EFFECT);
        BLOOD_AND_QUIL = createItem("Blood And Quil", "", ItemCounter.NO_EFFECT);
        BUNDLE_OF_HERBS = createItem("Bundle Of Herbs", "", ItemCounter.NO_EFFECT);
        CARNIVOROUS_PLANT = createItem("Carnivorous Plant", "", ItemCounter.NO_EFFECT);
        SCENTED_CANDLE = createItem("Scented Candle", "", ItemCounter.NO_EFFECT);
        POISONOUS_DART = createItem("Poisonous Dart", "", ItemCounter.NO_EFFECT);
        BROOM = createItem("Broom", "", ItemCounter.NO_EFFECT);

        // Spell
        WATERBENDING_SPELL = createItem("Waterbending Spell", "", ItemCounter.SPELL);
        ICE_SPELL = createItem("Ice Spell", "", ItemCounter.SPELL);
        EXPLOSION_SPELL = createItem("Explosion Spell", "", ItemCounter.SPELL);
        HELLFIRE_SPELL = createItem("Hellfire Spell", "", ItemCounter.SPELL);
        AGILITY_SPELL = createItem("Agility Spell", "", ItemCounter.SPELL);
        AIRBENDING_SPELL = createItem("Airbending Spell", "", ItemCounter.SPELL);
        LAVA_SPELL = createItem("Lava Spell", "", ItemCounter.SPELL);
        EARTHBENDING_SPELL = createItem("Earthbending Spell", "", ItemCounter.SPELL);

        // Shard
        COMMON_SHARD = createItem("Common Shard", "", ItemCounter.SHARD);
        UNCOMMON_SHARD = createItem("Uncommon Shard", "", ItemCounter.SHARD);
        RARE_SHARD = createItem("Rare Shard", "", ItemCounter.SHARD);
        ULTRA_RARE_SHARD = createItem("Ultra Rare Shard", "", ItemCounter.SHARD);
        LEGENDARY_SHARD = createItem("Legendary Shard", "", ItemCounter.SHARD);

        // Apple
        ROTTEN_APPLE = createItem("Rotten Apple", "", ItemCounter.APPLE);
        POISONED_APPLE = createItem("Poisoned Apple", "", ItemCounter.APPLE);

        // Potion
        MYSTERY_POTION = createItem("Mystery Potion", "", ItemCounter.POTION);

        // Food
        GINGERBREAD_MAN = createItem("Gingerbread Man", "", ItemCounter.FOOD);

        // Bottle
        BOTTLE_OF_LIGHTNING = createItem("Bottle of Lightning", "", ItemCounter.BOTTLE);

        // Stew
        FINN_SOUP = createItem("Finn Soup", "", ItemCounter.STEW);

        // Consumable
        VOODOO_DOLL = createItem("Voodoo Doll", "", ItemCounter.CONSUMABLE);
        WAR_HORN = createItem("War Horn", "", ItemCounter.CONSUMABLE);
        ANTIDOTE = createItem("Antidote", "", ItemCounter.CONSUMABLE);
        PALANTIR = createItem("Palantir", "", ItemCounter.CONSUMABLE);

        // Add spells to list
        spells.addAll(Arrays.asList(
                WATERBENDING_SPELL,
                ICE_SPELL,
                EXPLOSION_SPELL,
                HELLFIRE_SPELL,
                AGILITY_SPELL,
                AIRBENDING_SPELL,
                LAVA_SPELL,
                EARTHBENDING_SPELL
        ));

        // Add all craft items to list
        craftItems.addAll(Arrays.asList(
                BABY_ZOMBIE_HIDE,
                INSECT,
                BAT,
                THYME,
                OREGANO,
                DILL,
                ROSEMARY,
                GOLDEN_FEATHER,
                DEAD_FLOWER,
                TWIG,
                SOUL_OF_EVOKER,
                BLOOD_OF_WANDERING_TRADER,
                STINGER,
                HOGLIN_TUSK,
                FINN,
                VOODOO_DOLL,
                SHIP_IN_BOTTLE,
                PALANTIR,
                BLOOD_AND_QUIL,
                BUNDLE_OF_HERBS,
                CARNIVOROUS_PLANT,
                SCENTED_CANDLE,
                FINN_SOUP,
                WAR_HORN,
                POISONOUS_DART,
                BROOM,
                ROTTEN_APPLE,
                POISONED_APPLE,
                MYSTERY_POTION,
                ANTIDOTE,
                GINGERBREAD_MAN,
                BOTTLE_OF_LIGHTNING,
                COMMON_SHARD,
                UNCOMMON_SHARD,
                RARE_SHARD,
                ULTRA_RARE_SHARD,
                LEGENDARY_SHARD,
                WATERBENDING_SPELL,
                ICE_SPELL,
                EXPLOSION_SPELL,
                HELLFIRE_SPELL,
                AGILITY_SPELL,
                AIRBENDING_SPELL,
                LAVA_SPELL,
                EARTHBENDING_SPELL
        ));

        // Create ShamanTradeItems
        new ShamanTradeItem(BABY_ZOMBIE_HIDE);
        new ShamanTradeItem(ROTTEN_APPLE);
        new ShamanTradeItem(INSECT);
        new ShamanTradeItem(BAT);
        new ShamanTradeItem(GOLDEN_FEATHER);
        new ShamanTradeItem(DEAD_FLOWER);
        new ShamanTradeItem(TWIG);
        new ShamanTradeItem(SOUL_OF_EVOKER);
        new ShamanTradeItem(BLOOD_OF_WANDERING_TRADER);
        new ShamanTradeItem(STINGER);
        new ShamanTradeItem(HOGLIN_TUSK);
        new ShamanTradeItem(FINN);
        new ShamanTradeItem(VOODOO_DOLL);
        new ShamanTradeItem(SHIP_IN_BOTTLE);
        new ShamanTradeItem(PALANTIR);
        new ShamanTradeItem(BLOOD_AND_QUIL);
        new ShamanTradeItem(BUNDLE_OF_HERBS);
        new ShamanTradeItem(CARNIVOROUS_PLANT);
        new ShamanTradeItem(SCENTED_CANDLE);
        new ShamanTradeItem(FINN_SOUP);
        new ShamanTradeItem(WAR_HORN);
        new ShamanTradeItem(POISONOUS_DART);
        new ShamanTradeItem(BROOM);
        new ShamanTradeItem(POISONED_APPLE);
        new ShamanTradeItem(GINGERBREAD_MAN);
    }
}
