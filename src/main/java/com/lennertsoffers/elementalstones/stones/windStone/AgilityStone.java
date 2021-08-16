package com.lennertsoffers.elementalstones.stones.windStone;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AgilityStone {

    // PASSIVE
    public static void passive() {
        StaticVariables.scheduler.scheduleSyncRepeatingTask(StaticVariables.plugin, () -> {
            for (Player player : StaticVariables.plugin.getServer().getOnlinePlayers()) {
                player.setFallDistance(player.getFallDistance() / 2);
            }
        }, 0L, 5L);
    }

    // MOVE 4
    public static void move4(Player player) {
        player.setVelocity(player.getVelocity().add(new Vector(0, 0.5, 0)));
    }


    // MOVE 5
    public static void move5(Player player) {

    }

    // MOVE 6


    // MOVE 7


    // MOVE 8
}
