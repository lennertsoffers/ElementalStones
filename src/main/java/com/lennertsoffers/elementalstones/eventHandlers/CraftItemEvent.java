package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class CraftItemEvent implements Listener {

    @EventHandler
    public void onCraftItem(org.bukkit.event.inventory.CraftItemEvent event) {
        ItemStack result = event.getInventory().getResult();

        if (ItemStones.allStones.stream().anyMatch(itemStack -> itemStack.isSimilar(result))) {
            event.getInventory().setMatrix(new ItemStack[] {});
        }
    }
}
