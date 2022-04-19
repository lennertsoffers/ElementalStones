package com.lennertsoffers.elementalstones.items;

import com.lennertsoffers.elementalstones.customClasses.models.gameplay.ItemCounter;
import com.lennertsoffers.elementalstones.customClasses.models.gameplay.ShamanTradeItem;
import com.lennertsoffers.elementalstones.customClasses.tools.StringListTools;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

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
    public static ItemStack VOODOO_DOLL;
    public static ItemStack SHIP_IN_BOTTLE;
    public static ItemStack PALANTIR;
    public static ItemStack BLOOD_AND_QUIL;
    public static ItemStack BUNDLE_OF_HERBS;
    public static ItemStack CARNIVOROUS_PLANT;
    public static ItemStack SCENTED_CANDLE;
    public static ItemStack FINN_SOUP;
    public static ItemStack WAR_HORN;
    public static ItemStack POISONOUS_DART;
    public static ItemStack BROOM;

    // Consumables

    public static ItemStack ROTTEN_APPLE;
    public static ItemStack POISONED_APPLE;
    public static ItemStack MYSTERY_POTION;
    public static ItemStack ANTIDOTE;
    public static ItemStack GINGERBREAD_MAN;
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
    public static ArrayList<ItemStack> baseItems = new ArrayList<>();
    public static ArrayList<ItemStack> consumables = new ArrayList<>();



    private final ResourceBundle languageBundle;

    public CraftItemManager(ResourceBundle languageBundle) {
        this.languageBundle = languageBundle;
    }

    public void init() {
        this.createItems();
        this.addItems();
        this.createShamanItems();
    }

    private ItemStack createItem(String displayName, String lore, ItemCounter itemCounter) {
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

    private void createItems() {
        // No Effect
        BABY_ZOMBIE_HIDE = this.createItem(
                this.languageBundle.getString("baby_zombie_hide_name"),
                this.languageBundle.getString("baby_zombie_hide_lore"),
                ItemCounter.NO_EFFECT
        );
        INSECT = this.createItem(
                this.languageBundle.getString("insect_name"),
                this.languageBundle.getString("insect_lore"),
                ItemCounter.NO_EFFECT
        );
        BAT = this.createItem(
                this.languageBundle.getString("bat_name"),
                this.languageBundle.getString("bat_lore"),
                ItemCounter.NO_EFFECT
        );
        THYME = this.createItem(
                this.languageBundle.getString("thyme_name"),
                this.languageBundle.getString("thyme_lore"),
                ItemCounter.NO_EFFECT
        );
        OREGANO = this.createItem(
                this.languageBundle.getString("oregano_name"),
                this.languageBundle.getString("oregano_lore"),
                ItemCounter.NO_EFFECT
        );
        DILL = this.createItem(
                this.languageBundle.getString("dill_name"),
                this.languageBundle.getString("dill_lore"),
                ItemCounter.NO_EFFECT
        );
        ROSEMARY = this.createItem(
                this.languageBundle.getString("rosemary_name"),
                this.languageBundle.getString("rosemary_lore"),
                ItemCounter.NO_EFFECT
        );
        GOLDEN_FEATHER = this.createItem(
                this.languageBundle.getString("golden_feather_name"),
                this.languageBundle.getString("golden_feather_lore"),
                ItemCounter.NO_EFFECT
        );
        DEAD_FLOWER = this.createItem(
                this.languageBundle.getString("dead_flower_name"),
                this.languageBundle.getString("dead_flower_lore"),
                ItemCounter.NO_EFFECT
        );
        TWIG = this.createItem(
                this.languageBundle.getString("twig_name"),
                this.languageBundle.getString("twig_lore"),
                ItemCounter.NO_EFFECT
        );
        SOUL_OF_EVOKER = this.createItem(
                this.languageBundle.getString("soul_of_evoker_name"),
                this.languageBundle.getString("soul_of_evoker_lore"),
                ItemCounter.NO_EFFECT
        );
        BLOOD_OF_WANDERING_TRADER = this.createItem(
                this.languageBundle.getString("blood_of_wandering_trader_name"),
                this.languageBundle.getString("blood_of_wandering_trader_lore"),
                ItemCounter.NO_EFFECT
        );
        STINGER = this.createItem(
                this.languageBundle.getString("stinger_name"),
                this.languageBundle.getString("stinger_lore"),
                ItemCounter.NO_EFFECT
        );
        HOGLIN_TUSK = this.createItem(
                this.languageBundle.getString("hoglin_tusk_name"),
                this.languageBundle.getString("hoglin_tusk_lore"),
                ItemCounter.NO_EFFECT
        );
        FINN = this.createItem(
                this.languageBundle.getString("finn_name"),
                this.languageBundle.getString("finn_lore"),
                ItemCounter.NO_EFFECT
        );
        SHIP_IN_BOTTLE = this.createItem(
                this.languageBundle.getString("ship_in_bottle_name"),
                this.languageBundle.getString("ship_in_bottle_lore"),
                ItemCounter.NO_EFFECT
        );
        BLOOD_AND_QUIL = this.createItem(
                this.languageBundle.getString("blood_and_quil_name"),
                this.languageBundle.getString("blood_and_quil_lore"),
                ItemCounter.NO_EFFECT
        );
        BUNDLE_OF_HERBS = this.createItem(
                this.languageBundle.getString("bundle_of_herbs_name"),
                this.languageBundle.getString("bundle_of_herbs_lore"),
                ItemCounter.NO_EFFECT
        );
        CARNIVOROUS_PLANT = this.createItem(
                this.languageBundle.getString("carnivorous_plant_name"),
                this.languageBundle.getString("carnivorous_plant_lore"),
                ItemCounter.NO_EFFECT
        );
        SCENTED_CANDLE = this.createItem(
                this.languageBundle.getString("scented_candle_name"),
                this.languageBundle.getString("scented_candle_lore"),
                ItemCounter.NO_EFFECT
        );
        POISONOUS_DART = this.createItem(
                this.languageBundle.getString("poisonous_dart_name"),
                this.languageBundle.getString("poisonous_dart_lore"),
                ItemCounter.NO_EFFECT
        );
        BROOM = this.createItem(
                this.languageBundle.getString("broom_name"),
                this.languageBundle.getString("broom_lore"),
                ItemCounter.NO_EFFECT
        );

        // Spell
        WATERBENDING_SPELL = this.createItem(
                this.languageBundle.getString("waterbending_spell_name"),
                this.languageBundle.getString("waterbending_spell_lore"),
                ItemCounter.SPELL
        );
        ICE_SPELL = this.createItem(
                this.languageBundle.getString("ice_spell_name"),
                this.languageBundle.getString("ice_spell_lore"),
                ItemCounter.SPELL
        );
        EXPLOSION_SPELL = this.createItem(
                this.languageBundle.getString("explosion_spell_name"),
                this.languageBundle.getString("explosion_spell_lore"),
                ItemCounter.SPELL
        );
        HELLFIRE_SPELL = this.createItem(
                this.languageBundle.getString("hellfire_spell_name"),
                this.languageBundle.getString("hellfire_spell_lore"),
                ItemCounter.SPELL
        );
        AGILITY_SPELL = this.createItem(
                this.languageBundle.getString("agility_spell_name"),
                this.languageBundle.getString("agility_spell_lore"),
                ItemCounter.SPELL
        );
        AIRBENDING_SPELL = this.createItem(
                this.languageBundle.getString("airbending_spell_name"),
                this.languageBundle.getString("airbending_spell_lore"),
                ItemCounter.SPELL
        );
        LAVA_SPELL = this.createItem(
                this.languageBundle.getString("lava_spell_name"),
                this.languageBundle.getString("lava_spell_lore"),
                ItemCounter.SPELL
        );
        EARTHBENDING_SPELL = this.createItem(
                this.languageBundle.getString("earthbending_spell_name"),
                this.languageBundle.getString("earthbending_spell_lore"),
                ItemCounter.SPELL
        );

        // Shard
        COMMON_SHARD = this.createItem(
                this.languageBundle.getString("common_shard_name"),
                this.languageBundle.getString("common_shard_lore"),
                ItemCounter.SHARD
        );
        UNCOMMON_SHARD = this.createItem(
                this.languageBundle.getString("uncommon_shard_name"),
                this.languageBundle.getString("uncommon_shard_lore"),
                ItemCounter.SHARD
        );
        RARE_SHARD = this.createItem(
                this.languageBundle.getString("rare_shard_name"),
                this.languageBundle.getString("rare_shard_lore"),
                ItemCounter.SHARD
        );
        ULTRA_RARE_SHARD = this.createItem(
                this.languageBundle.getString("ultra_rare_shard_name"),
                this.languageBundle.getString("ultra_rare_shard_lore"),
                ItemCounter.SHARD
        );
        LEGENDARY_SHARD = this.createItem(
                this.languageBundle.getString("legendary_shard_name"),
                this.languageBundle.getString("legendary_shard_lore"),
                ItemCounter.SHARD
        );

        // Apple
        ROTTEN_APPLE = this.createItem(
                this.languageBundle.getString("rotten_apple_name"),
                this.languageBundle.getString("rotten_apple_lore"),
                ItemCounter.APPLE
        );
        POISONED_APPLE = this.createItem(
                this.languageBundle.getString("poisoned_apple_name"),
                this.languageBundle.getString("poisoned_apple_lore"),
                ItemCounter.APPLE
        );

        // Food
        GINGERBREAD_MAN = this.createItem(
                this.languageBundle.getString("gingerbread_man_name"),
                this.languageBundle.getString("gingerbread_man_lore"),
                ItemCounter.FOOD
        );

        // Bottle
        BOTTLE_OF_LIGHTNING = this.createItem(
                this.languageBundle.getString("bottle_of_lightning_name"),
                this.languageBundle.getString("bottle_of_lightning_lore"),
                ItemCounter.BOTTLE
        );
        MYSTERY_POTION = this.createItem(
                this.languageBundle.getString("mystery_potion_name"),
                this.languageBundle.getString("mystery_potion_lore"),
                ItemCounter.BOTTLE
        );

        // Stew
        FINN_SOUP = this.createItem(
                this.languageBundle.getString("finn_soup_name"),
                this.languageBundle.getString("finn_soup_lore"),
                ItemCounter.STEW
        );

        // Consumable
        VOODOO_DOLL = this.createItem(
                this.languageBundle.getString("voodoo_doll_name"),
                this.languageBundle.getString("voodoo_doll_lore"),
                ItemCounter.CONSUMABLE
        );
        WAR_HORN = this.createItem(
                this.languageBundle.getString("war_horn_name"),
                this.languageBundle.getString("war_horn_lore"),
                ItemCounter.CONSUMABLE
        );
        ANTIDOTE = this.createItem(
                this.languageBundle.getString("antidote_name"),
                this.languageBundle.getString("antidote_lore"),
                ItemCounter.CONSUMABLE
        );
        PALANTIR = this.createItem(
                this.languageBundle.getString("palantir_name"),
                this.languageBundle.getString("palantir_lore"),
                ItemCounter.CONSUMABLE
        );
    }

    private void addItems() {
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

        baseItems.addAll(Arrays.asList(
                CraftItemManager.BABY_ZOMBIE_HIDE,
                CraftItemManager.INSECT,
                CraftItemManager.BAT,
                CraftItemManager.THYME,
                CraftItemManager.OREGANO,
                CraftItemManager.DILL,
                CraftItemManager.ROSEMARY,
                CraftItemManager.GOLDEN_FEATHER,
                CraftItemManager.DEAD_FLOWER,
                CraftItemManager.TWIG,
                CraftItemManager.SOUL_OF_EVOKER,
                CraftItemManager.BLOOD_OF_WANDERING_TRADER,
                CraftItemManager.STINGER,
                CraftItemManager.HOGLIN_TUSK,
                CraftItemManager.FINN
        ));

        consumables.addAll(Arrays.asList(
                CraftItemManager.VOODOO_DOLL,
                CraftItemManager.SHIP_IN_BOTTLE,
                CraftItemManager.PALANTIR,
                CraftItemManager.BLOOD_AND_QUIL,
                CraftItemManager.BUNDLE_OF_HERBS,
                CraftItemManager.CARNIVOROUS_PLANT,
                CraftItemManager.SCENTED_CANDLE,
                CraftItemManager.FINN_SOUP,
                CraftItemManager.WAR_HORN,
                CraftItemManager.POISONOUS_DART,
                CraftItemManager.BROOM,
                CraftItemManager.ROTTEN_APPLE,
                CraftItemManager.POISONED_APPLE,
                CraftItemManager.GINGERBREAD_MAN,
                CraftItemManager.ANTIDOTE,
                CraftItemManager.BOTTLE_OF_LIGHTNING,
                CraftItemManager.MYSTERY_POTION
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
    }

    private void createShamanItems() {
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
