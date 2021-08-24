package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.stones.earthStone.EarthbendingStone;
import com.lennertsoffers.elementalstones.stones.fireStone.HellfireStone;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class PlayerMoveEvent implements Listener {

    @EventHandler
    public void onPlayerMove(org.bukkit.event.player.PlayerMoveEvent event) {
        Player player = event.getPlayer();
        HellfireStone.move4(Objects.requireNonNull(ActivePlayer.getActivePlayer(player.getUniqueId())), event);
//        if (event.getFrom().getY() < Objects.requireNonNull(event.getTo()).getY()) {
//            // Jump
//            ActivePlayer activePlayer = Objects.requireNonNull(ActivePlayer.getActivePlayer(event.getPlayer().getUniqueId()));
//            EarthbendingStone.passive(activePlayer);
//        }
    }
}
