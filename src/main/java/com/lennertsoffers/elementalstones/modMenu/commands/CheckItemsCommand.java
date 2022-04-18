package com.lennertsoffers.elementalstones.modMenu.commands;

import com.lennertsoffers.elementalstones.customClasses.annotations.CommandExecutor;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@CommandExecutor
public class CheckItemsCommand extends PlayerCommand {

    public CheckItemsCommand() {
        super("checkItems");
    }

    @Override
    boolean execute() {
        List<ItemStack> itemStacks = CraftItemManager.baseItems;

        Inventory inventory = Bukkit.createInventory(this.getPlayer(), 18);
        for (int i = 0; i < itemStacks.size() - 1; i++) {
            inventory.setItem(i, itemStacks.get(i));
        }

        this.getPlayer().openInventory(inventory);

        return true;
    }
}
