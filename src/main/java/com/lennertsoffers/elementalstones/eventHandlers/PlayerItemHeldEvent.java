package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.stones.earthStone.EarthbendingStone;
import com.lennertsoffers.elementalstones.stones.waterStone.IceStone;
import com.lennertsoffers.elementalstones.stones.waterStone.WaterbendingStone;
import com.lennertsoffers.elementalstones.stones.windStone.AgilityStone;
import com.lennertsoffers.elementalstones.stones.windStone.AirbendingStone;
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
                if (activePlayer.move1 != null) {
                    activePlayer.move1.run();
                }
            }
            else if (event.getNewSlot() == 1) {
                if (activePlayer.move2 != null) {
                    activePlayer.move2.run();
                }
            }
            else if (event.getNewSlot() == 2) {
                if (activePlayer.move3 != null) {
                    activePlayer.move3.run();
                }
            }
            else if (event.getNewSlot() == 3) {
                if (activePlayer.move4 != null) {
                    activePlayer.move4.run();
                }
            }
            else if (event.getNewSlot() == 4) {
                if (activePlayer.move5 != null) {
                    activePlayer.move5.run();
                }
            }
            else if (event.getNewSlot() == 5) {
                if (activePlayer.move6 != null) {
                    activePlayer.move6.run();
                }
            }
            else if (event.getNewSlot() == 6) {
                if (activePlayer.move7 != null) {
                    activePlayer.move7.run();
                }
            }
            else if (event.getNewSlot() == 7) {
                if (activePlayer.move8 != null) {
                    activePlayer.move8.run();
                }
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
