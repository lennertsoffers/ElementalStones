package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
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
            if (activePlayer.areMovesEnabled()) {
                if (event.getNewSlot() == 0) {
                    activePlayer.getMoveController().getMove1().activateMove();
                } else if (event.getNewSlot() == 1) {
                    activePlayer.getMoveController().getMove2().activateMove();

                } else if (event.getNewSlot() == 2) {
                    activePlayer.getMoveController().getMove3().activateMove();

                } else if (event.getNewSlot() == 3) {
                    activePlayer.getMoveController().getMove4().activateMove();

                } else if (event.getNewSlot() == 4) {
                    activePlayer.getMoveController().getMove5().activateMove();

                } else if (event.getNewSlot() == 5) {
                    activePlayer.getMoveController().getMove6().activateMove();

                } else if (event.getNewSlot() == 6) {
                    activePlayer.getMoveController().getMove7().activateMove();

                } else if (event.getNewSlot() == 7) {
                    activePlayer.getMoveController().getMove8().activateMove();
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
