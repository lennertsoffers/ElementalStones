package com.lennertsoffers.elementalstones.customClasses.tools;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import org.bukkit.entity.Player;

import java.util.Objects;

public class DamageCalculations {

    public static double calculateFallDamage(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        return Math.max(0, (int) activePlayer.getFallingDistance() - 3);
    }
}
