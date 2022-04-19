package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.annotations.Event;
import com.lennertsoffers.elementalstones.passives.PassiveHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Event
public class PlayerToggleFlightEvent implements Listener {
    @EventHandler
    public void onPlayerToggleFlight(org.bukkit.event.player.PlayerToggleFlightEvent event) {
        PassiveHandler.doubleJump(event);
    }
}
