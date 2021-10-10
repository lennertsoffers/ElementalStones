package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.stones.windStone.AgilityStone;
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
                AgilityStone.move1(activePlayer);
            }
            else if (event.getNewSlot() == 1) {
                AgilityStone.move2(activePlayer);
            }
            else if (event.getNewSlot() == 2) {
                AgilityStone.move3(activePlayer);
            }
            else if (event.getNewSlot() == 3) {
                AgilityStone.move4(activePlayer);
            }
            else if (event.getNewSlot() == 4) {
                AgilityStone.move5(activePlayer);
            }
            else if (event.getNewSlot() == 5) {
                AgilityStone.move6(activePlayer);
            }
            else if (event.getNewSlot() == 6) {
                AgilityStone.move7(activePlayer);
            }
            else if (event.getNewSlot() == 7) {
                AgilityStone.move8(activePlayer);
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
