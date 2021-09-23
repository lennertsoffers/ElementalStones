package com.lennertsoffers.elementalstones.stones.windStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class AgilityStone {

    // PASSIVE
    public static void passive(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        player.setVelocity(player.getLocation().getDirection().setY(1));
    }

    // MOVE 4
    //

    // MOVE 5


    // MOVE 6


    // MOVE 7


    // MOVE 8
}
