package com.lennertsoffers.elementalstones.modMenu.commands;

import com.lennertsoffers.elementalstones.customClasses.annotations.CommandExecutor;
import org.bukkit.inventory.meta.ItemMeta;

@CommandExecutor
public class GetCustomModelDataCommand extends PlayerCommand {

    public GetCustomModelDataCommand() {
        super("getCustomModelData");
    }

    @Override
    boolean execute() {
        ItemMeta itemMeta = this.getPlayer().getInventory().getItemInMainHand().getItemMeta();

        if (itemMeta != null) {
            this.getPlayer().sendMessage(String.valueOf(itemMeta.getCustomModelData()));
        }

        return true;
    }
}
