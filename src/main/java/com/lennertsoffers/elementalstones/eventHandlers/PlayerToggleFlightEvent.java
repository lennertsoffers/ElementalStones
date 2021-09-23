package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.items.ItemStones;
import com.lennertsoffers.elementalstones.stones.windStone.AgilityStone;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerToggleFlightEvent implements Listener {
    @EventHandler
    public void onPlayerToggleFlight(org.bukkit.event.player.PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().contains(ItemStones.airStoneAgility0) ||
            player.getInventory().contains(ItemStones.airStoneAgility1) ||
            player.getInventory().contains(ItemStones.airStoneAgility2) ||
            player.getInventory().contains(ItemStones.airStoneAgility3) ||
            player.getInventory().contains(ItemStones.airStoneAgility4)) {
            AgilityStone.passive(event);
        }
        event.setCancelled(true);
    }
}
