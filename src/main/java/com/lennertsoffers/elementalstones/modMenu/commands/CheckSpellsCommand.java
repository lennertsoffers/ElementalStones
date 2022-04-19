package com.lennertsoffers.elementalstones.modMenu.commands;

import com.lennertsoffers.elementalstones.customClasses.annotations.CommandExecutor;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@CommandExecutor
public class CheckSpellsCommand extends PlayerCommand {

    public CheckSpellsCommand() {
        super("checkSpells");
    }

    @Override
    boolean execute() {
        Inventory inventory = Bukkit.createInventory(this.getPlayer(), 18);
        int slot = 0;
        for (ItemStack itemStack : Arrays.asList(CraftItemManager.AGILITY_SPELL, CraftItemManager.AIRBENDING_SPELL, CraftItemManager.HELLFIRE_SPELL, CraftItemManager.EXPLOSION_SPELL, CraftItemManager.WATERBENDING_SPELL, CraftItemManager.ICE_SPELL, CraftItemManager.EARTHBENDING_SPELL, CraftItemManager.LAVA_SPELL)) {
            inventory.setItem(slot, itemStack);
            slot++;
        }
        this.getPlayer().openInventory(inventory);

        return true;
    }
}
