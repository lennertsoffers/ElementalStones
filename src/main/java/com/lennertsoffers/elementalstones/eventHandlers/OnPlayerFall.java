package com.lennertsoffers.elementalstones.eventHandlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnPlayerFall implements Listener {

    @EventHandler
    public void onPlayerFall(wa.was.playerairevents.events.PlayerFallEvent event) {
        System.out.println(event.getPlayer());
    }
}
