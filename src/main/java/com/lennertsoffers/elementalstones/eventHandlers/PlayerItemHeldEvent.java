package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.stones.fireStone.HellfireStone;
import com.lennertsoffers.elementalstones.stones.fireStone.LavaStone;
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
                LavaStone.move7(activePlayer);
            }
//            else if (event.getNewSlot() == 1) {
//                HellfireStone.move2(activePlayer);
//            } else if (event.getNewSlot() == 2) {
//                HellfireStone.move3(activePlayer);
//            } else if (event.getNewSlot() == 3) {
//                HellfireStone.move4(activePlayer);
//            } else if (event.getNewSlot() == 4) {
//                HellfireStone.move5(activePlayer);
//            } else if (event.getNewSlot() == 5) {
//                HellfireStone.move6(activePlayer);
//            } else if (event.getNewSlot() == 6) {
//                HellfireStone.move7(activePlayer);
//            } else if (event.getNewSlot() == 7) {
//                HellfireStone.move8(activePlayer);
//            }
            if (activePlayer.isActive()) {
                ItemStack previousItem = player.getInventory().getItem(event.getPreviousSlot());
                if (!(previousItem == null)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
