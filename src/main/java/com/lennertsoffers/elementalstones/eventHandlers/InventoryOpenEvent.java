package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;

public class InventoryOpenEvent implements Listener {

    @EventHandler
    public void onOpenInventory(org.bukkit.event.inventory.InventoryOpenEvent event) {
        if (event.getInventory().getType() == InventoryType.MERCHANT) {
            Player player = (Player) event.getPlayer();
            ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());

            if (activePlayer != null) {
                if (activePlayer.closeTradingInventories()) {
                    event.setCancelled(true);
                    activePlayer.setCloseTradingInventories(false);
                }
            }
        }
    }
}
