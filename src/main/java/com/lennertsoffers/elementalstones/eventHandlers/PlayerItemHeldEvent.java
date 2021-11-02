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
                AirbendingStone.move8(activePlayer);
            }
//            else if (event.getNewSlot() == 1) {
//                EarthbendingStone.move2(activePlayer);
//            }
//            else if (event.getNewSlot() == 2) {
//                EarthbendingStone.move3(player);
//            }
//            else if (event.getNewSlot() == 3) {
//                EarthbendingStone.move4(activePlayer);
//            }
//            else if (event.getNewSlot() == 4) {
//                EarthbendingStone.move5(player);
//            }
//            else if (event.getNewSlot() == 5) {
//                EarthbendingStone.move6(player);
//            }
//            else if (event.getNewSlot() == 6) {
//                EarthbendingStone.move7(player);
//            }
//            else if (event.getNewSlot() == 7) {
//                EarthbendingStone.move8(activePlayer);
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
