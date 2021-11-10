package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.ShamanVillager;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class InventoryOpenEvent implements Listener {

    @EventHandler
    public void onInventoryOpen(org.bukkit.event.inventory.InventoryOpenEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());



            if (activePlayer != null) {
                if (activePlayer.getInteractingWith() != null) {
                    event.setCancelled(true);
                    Inventory inventory = activePlayer.getShamanInventory();
                    event.getPlayer().openInventory(inventory);
                }
            }
        }
    }
}
