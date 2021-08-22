package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.ElementalStones;
import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.items.ItemStones;
import com.lennertsoffers.elementalstones.stones.earthStone.EarthbendingStone;
import com.lennertsoffers.elementalstones.stones.windStone.AgilityStone;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClickEvent implements Listener {

    protected final ElementalStones plugin;

    public ClickEvent(ElementalStones plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (ItemStones.allStones.contains(player.getInventory().getItemInMainHand())) {
                ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());
                if (activePlayer != null) {
                    if (activePlayer.isActive()) {
                        activePlayer.setActive(false);
                        player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You left move mode!");
                    } else {
                        activePlayer.setActive(true);
                        player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You are in move mode!");
                    }
                }
            }
        }

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {}
    }
}
