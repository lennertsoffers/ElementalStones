package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.stones.windStone.AirStoneSharedPassive;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerToggleFlightEvent implements Listener {
    @EventHandler
    public void onPlayerToggleFlight(org.bukkit.event.player.PlayerToggleFlightEvent event) {
        AirStoneSharedPassive.passive2(event);
    }
}
