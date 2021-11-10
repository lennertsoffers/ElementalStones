package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.ShamanVillager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class InventoryCloseEvent implements Listener {

    @EventHandler
    public void onInventoryClose(org.bukkit.event.inventory.InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());
            if (activePlayer != null) {
                if (activePlayer.getShamanInventory() == event.getInventory()) {
                    activePlayer.getInteractingWith().acceptItems(event.getInventory());
                    activePlayer.setInteractingWith(null);
                    activePlayer.setShamanInventory(null);
                }
            }
        }
    }
}
