package com.lennertsoffers.elementalstones.stones.windStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import java.util.Objects;

public class AgilityStone {

    // PASSIVE
    public static void passive(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());
        if (Objects.requireNonNull(activePlayer).canDoubleJump()) {
            Vector launchDirection;
            if (Math.abs(activePlayer.getMovingDirection().getX()) < 0.1 && Math.abs(activePlayer.getMovingDirection().getZ()) < 0.1) {
                System.out.println(activePlayer.getMovingDirection());
                launchDirection = player.getLocation().getDirection();
            } else {
                launchDirection = activePlayer.getMovingDirection();
            }
            player.setVelocity(launchDirection.setY(1));
            activePlayer.disableDoubleJump();
        }
    }

    // MOVE 4
    //

    // MOVE 5


    // MOVE 6


    // MOVE 7


    // MOVE 8
}
