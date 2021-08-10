package com.lennertsoffers.elementalstones.event;

import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class PlayerItemHeldEvent implements Listener {

    @EventHandler
    public void onPlayerItemHeld(org.bukkit.event.player.PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        try {
            if (ClickEvent.getActivePlayers().get(player)) {
                ItemStack previousItem = player.getInventory().getItem(event.getPreviousSlot());
                if (!(previousItem == null)) {
                    if (previousItem.equals(ItemStones.baseStone)) {
                        event.setCancelled(true);
                    }
                }
            }
        } catch (java.lang.NullPointerException exception) {
            System.out.println("error");
        }
    }
}
