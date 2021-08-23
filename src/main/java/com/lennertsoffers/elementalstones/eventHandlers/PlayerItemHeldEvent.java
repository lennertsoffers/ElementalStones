package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.stones.earthStone.DefenseStone;

import com.lennertsoffers.elementalstones.stones.earthStone.EarthStone;
import com.lennertsoffers.elementalstones.stones.earthStone.MiningStone;
import com.lennertsoffers.elementalstones.stones.fireStone.FireStone;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class PlayerItemHeldEvent implements Listener {

    @EventHandler
    public void onPlayerItemHeld(org.bukkit.event.player.PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());
        if (activePlayer != null) {
            if (event.getNewSlot() == 0) {
                FireStone.move2(activePlayer);
            }
            if (activePlayer.isActive()) {
                ItemStack previousItem = player.getInventory().getItem(event.getPreviousSlot());
                if (!(previousItem == null)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
