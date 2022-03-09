package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.tools.ItemTools;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;

public class InventoryClickEvent implements Listener {

    @EventHandler
    public void onInventoryClick(org.bukkit.event.inventory.InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();

        if (inventory != null) {

            if (inventory.getType() == InventoryType.WORKBENCH || inventory.getType() == InventoryType.CRAFTING) {
                if (inventory.getType() == InventoryType.WORKBENCH) {
                    if (event.getSlotType() == InventoryType.SlotType.RESULT) {

                        System.out.println("click");

                        CraftingInventory craftingInventory = (CraftingInventory) inventory;
                        ItemStack[] craftingMatrix = craftingInventory.getMatrix();
                        ArrayList<ItemStack> singleItemCraftingMatrix = ItemTools.getSingleListFromStackList(craftingMatrix);

                        if (!Collections.disjoint(CraftItemManager.craftItems, singleItemCraftingMatrix)) {
                            craftingInventory.setResult(null);
                        } else if (!Collections.disjoint(ItemStones.allStones, singleItemCraftingMatrix)) {
                            craftingInventory.setResult(null);
                        }

                        ItemStack itemInSlot = inventory.getItem(event.getSlot());

                        if (ItemStones.allStones.contains(itemInSlot)) {
                            inventory.clear();
                            event.getWhoClicked().setItemOnCursor(itemInSlot);
                        }
                    }
                }
            }
        }
    }
}
