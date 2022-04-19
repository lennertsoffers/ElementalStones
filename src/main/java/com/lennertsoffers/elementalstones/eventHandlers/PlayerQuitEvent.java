package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.annotations.Event;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Event
public class PlayerQuitEvent implements Listener {

    @EventHandler
    public void onPlayerLeaveEvent(org.bukkit.event.player.PlayerQuitEvent event) {
        ActivePlayer.removeActivePlayer(event.getPlayer().getUniqueId());
    }
}
