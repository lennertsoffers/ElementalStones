package com.lennertsoffers.elementalstones.modMenu.commands;

import com.lennertsoffers.elementalstones.items.CraftItemManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CheckShardsCommand extends PlayerCommand {

    public CheckShardsCommand(String command) {
        super(command);
    }

    @Override
    boolean execute() {
        Inventory inventory = Bukkit.createInventory(this.getPlayer(), 18);
        int slot = 0;
        for (ItemStack itemStack : Arrays.asList(CraftItemManager.COMMON_SHARD, CraftItemManager.UNCOMMON_SHARD, CraftItemManager.RARE_SHARD, CraftItemManager.ULTRA_RARE_SHARD, CraftItemManager.LEGENDARY_SHARD)) {
            inventory.setItem(slot, itemStack);
            slot++;
        }
        this.getPlayer().openInventory(inventory);

        return true;
    }
}
