package com.lennertsoffers.elementalstones.event;

import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class PrepareItemCraftEvent implements Listener {

    @EventHandler
    public void onPrepareItemCraft(org.bukkit.event.inventory.PrepareItemCraftEvent event) {
        ItemStack[] itemStackMatrix = event.getInventory().getMatrix();
        boolean slotNull = false;
        for (ItemStack stack : itemStackMatrix) {
            if (stack == null) {
                slotNull = true;
                break;
            }
        }

        if (!slotNull) {
            ItemStack nautilusShell = new ItemStack(Material.NAUTILUS_SHELL);
            ItemStack heartOfTheSea = new ItemStack(Material.HEART_OF_THE_SEA);
            if (itemStackMatrix[0].equals(nautilusShell) &&
                    itemStackMatrix[1].equals(heartOfTheSea) &&
                    itemStackMatrix[2].equals(nautilusShell) &&
                    itemStackMatrix[3].equals(heartOfTheSea) &&
                    itemStackMatrix[4].equals(ItemStones.waterStone0) &&
                    itemStackMatrix[5].equals(heartOfTheSea) &&
                    itemStackMatrix[6].equals(nautilusShell) &&
                    itemStackMatrix[7].equals(heartOfTheSea) &&
                    itemStackMatrix[8].equals(nautilusShell)
            ) {
                event.getInventory().setResult(ItemStones.waterStone1);
            }
        }
    }
}
