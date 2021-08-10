package com.lennertsoffers.elementalstones.event;

import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ClickEvent implements Listener {

    private static final HashMap<Player, Boolean> activePlayers = new HashMap<>();

    public static HashMap<Player, Boolean> getActivePlayers() {
        return activePlayers;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        try {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                for (ItemStack itemStack : ItemStones.allStones) {
                    if (player.getInventory().getItemInMainHand() == itemStack) {
                        if (activePlayers.get(player)) {
                            activePlayers.replace(player, false);
                        } else {
                            activePlayers.put(player, true);
                        }
                        System.out.println(activePlayers);
                    }
                }
            }
        } catch (java.lang.NullPointerException exception) {
            System.out.println("error");
        }
    }
}
