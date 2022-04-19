package com.lennertsoffers.elementalstones.customClasses.models.gameplay;

import com.lennertsoffers.elementalstones.ElementalStones;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public class ShamanTradeItem {

    private final String name;
    private final ItemStack item;
    private final int xpValue;

    private static final ArrayList<ShamanTradeItem> shamanTradeItems = new ArrayList<>();

    public ShamanTradeItem(ItemStack itemStack) {
        this.item = itemStack;
        String displayName = Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName();
        this.name = displayName.toLowerCase().replace(" ", "_");
        this.xpValue = ElementalStones.configuration.getInt("xp_value." + this.name);
        shamanTradeItems.add(this);
    }

    public String getName() {
        return name;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getXpValue() {
        return xpValue;
    }

    public static ArrayList<ShamanTradeItem> getShamanXpItems() {
        return shamanTradeItems;
    }
}
