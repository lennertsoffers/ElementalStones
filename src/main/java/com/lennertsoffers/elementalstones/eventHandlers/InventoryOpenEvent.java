package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.annotations.Event;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChatEvent;

@Event
public class InventoryOpenEvent implements Listener {

    @EventHandler
    public void onOpenInventory(org.bukkit.event.inventory.InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());

        if (activePlayer != null) {
            if (event.getInventory().getType() == InventoryType.MERCHANT) {
                if (activePlayer.closeTradingInventories()) {
                    event.setCancelled(true);
                    activePlayer.setCloseTradingInventories(false);
                }
            }
        }
    }
}
