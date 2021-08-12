package com.lennertsoffers.elementalstones.event;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.items.ItemStones;
import com.lennertsoffers.elementalstones.stones.earthStone.DefenseStone;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClickEvent implements Listener {

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

        if (event.getAction() == Action.LEFT_CLICK_AIR) {
            System.out.println(Particle.BLOCK_CRACK.getDataType());
            DefenseStone.defenseStoneMove7(event.getPlayer());
            System.out.println("test");
        }
    }
}
