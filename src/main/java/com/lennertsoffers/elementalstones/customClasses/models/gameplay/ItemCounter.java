package com.lennertsoffers.elementalstones.customClasses.models.gameplay;

import org.bukkit.Material;

public enum ItemCounter {
    NO_EFFECT(Material.BLAZE_ROD),
    SPELL(Material.DIAMOND),
    SHARD(Material.LEATHER),
    APPLE(Material.APPLE),
    POTION(Material.POTION),
    FOOD(Material.COOKIE),
    BOTTLE(Material.GLASS_BOTTLE),
    STEW(Material.RABBIT_STEW),
    CONSUMABLE(Material.SPIDER_EYE);

    int amount;
    final Material material;

    ItemCounter(Material material) {
        this.material = material;
        this.amount = 1;
    }

    public int getAmount() {
        return this.amount;
    }

    public void createItem() {
        this.amount++;
    }

    public Material getMaterial() {
        return this.material;
    }
}
