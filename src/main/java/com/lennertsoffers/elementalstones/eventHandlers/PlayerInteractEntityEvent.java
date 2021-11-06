package com.lennertsoffers.elementalstones.eventHandlers;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerInteractEntityEvent implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(org.bukkit.event.player.PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (entity instanceof Villager) {
            Villager villager = (Villager) entity;
        }
    }
}
