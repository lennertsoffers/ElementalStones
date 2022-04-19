package com.lennertsoffers.elementalstones.modMenu.commands;

import com.lennertsoffers.elementalstones.customClasses.annotations.CommandExecutor;
import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

@CommandExecutor
public class StoneInventoryCommand extends PlayerCommand {

    public StoneInventoryCommand() {
        super("stoneInventory");
    }

    @Override
    boolean execute() {
        if (this.getArgs().length != 0) {
            if (this.getArgs()[0].matches("water|fire|air|earth")) {
                ArrayList<ItemStack> selectedStones;

                switch (args[0]) {
                    case "water":
                        selectedStones = (ArrayList<ItemStack>) ItemStones.waterStones.clone();
                        break;
                    case "fire":
                        selectedStones = (ArrayList<ItemStack>) ItemStones.fireStones.clone();
                        break;
                    case "air":
                        selectedStones = (ArrayList<ItemStack>) ItemStones.airStones.clone();
                        break;
                    default:
                        selectedStones = (ArrayList<ItemStack>) ItemStones.earthStones.clone();
                }

                Inventory inventory = Bukkit.createInventory(this.getPlayer(), 18, this.getArgs()[0]);
                int slot = 0;

                for (ItemStack itemStack : selectedStones) {
                    inventory.setItem(slot, itemStack);
                    slot++;
                }
                this.getPlayer().openInventory(inventory);

                return true;
            }
        }

        return false;
    }
}
