package com.lennertsoffers.elementalstones.modMenu.commands;

import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

public class GetCustomModelDataCommand extends PlayerCommand {

    public GetCustomModelDataCommand(String command) {
        super(command);
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
