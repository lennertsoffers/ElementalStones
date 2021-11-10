package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.ShamanVillager;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.*;

import java.util.ArrayList;

public class PlayerInteractAtEntityEvent implements Listener {

    @EventHandler
    public void onPlayerInteractAtEntity(org.bukkit.event.player.PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());

        if (activePlayer != null) {
            // Is Villager
            if (event.getRightClicked() instanceof Villager) {
                if (player.getInventory().getItemInMainHand().isSimilar(CraftItemManager.ROSEMARY)) {
                    Villager villager = (Villager) event.getRightClicked();
                    ShamanVillager shamanVillager = ShamanVillager.getShamanVillager(villager.getUniqueId());
                    Inventory inventory = Bukkit.createInventory(player, 9);
                    activePlayer.setShamanInventory(inventory);
                    activePlayer.setInteractingWith(shamanVillager);
                }
            }
        }
    }
}
