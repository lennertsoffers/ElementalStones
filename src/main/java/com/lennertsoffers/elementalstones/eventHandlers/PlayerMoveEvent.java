package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.stones.earthStone.EarthbendingStone;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class PlayerMoveEvent implements Listener {

    @EventHandler
    public void onPlayerMove(org.bukkit.event.player.PlayerMoveEvent event) {
        if (event.getFrom().getY() < Objects.requireNonNull(event.getTo()).getY()) {
            // Jump
            ActivePlayer activePlayer = Objects.requireNonNull(ActivePlayer.getActivePlayer(event.getPlayer().getUniqueId()));
            EarthbendingStone.passive(activePlayer);
        }
    }
}
