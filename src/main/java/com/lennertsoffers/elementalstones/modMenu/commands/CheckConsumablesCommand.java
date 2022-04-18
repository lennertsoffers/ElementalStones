package com.lennertsoffers.elementalstones.modMenu.commands;

import com.lennertsoffers.elementalstones.customClasses.annotations.CommandExecutor;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@CommandExecutor
public class CheckConsumablesCommand extends PlayerCommand {
    public CheckConsumablesCommand() {
        super("checkConsumables");
    }

    @Override
    boolean execute() {
        List<ItemStack> itemStacks = CraftItemManager.consumables;

        Inventory inventory = Bukkit.createInventory(this.getPlayer(), 18);
        int slot = 0;
        for (ItemStack itemStack : itemStacks) {
            inventory.setItem(slot, itemStack);
            slot++;
        }
        this.getPlayer().openInventory(inventory);

        return true;
    }
}
