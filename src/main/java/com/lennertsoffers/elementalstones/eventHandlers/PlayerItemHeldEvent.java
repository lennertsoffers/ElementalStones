package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.stones.earthStone.DefenseStone;

import com.lennertsoffers.elementalstones.stones.earthStone.EarthStone;
import com.lennertsoffers.elementalstones.stones.earthStone.EarthbendingStone;
import com.lennertsoffers.elementalstones.stones.earthStone.MiningStone;
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
                EarthStone.move2(activePlayer);
            } else if (event.getNewSlot() == 1) {
                MiningStone.move4(activePlayer);
            } else if (event.getNewSlot() == 4) {
                System.out.println("test");
                DefenseStone.move7(player);
            } else if (event.getNewSlot() == 3) {
                DefenseStone.move5(player);
            } else if (event.getNewSlot() == 5) {
                DefenseStone.move7(player);
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
