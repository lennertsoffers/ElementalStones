package com.lennertsoffers.elementalstones.stones.windStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Objects;

public class AgilityStone {

    // PASSIVE
    // Double Jump
    // -> The player can jump a second time after jumping in the air with space bar
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
            player.setVelocity(player.getVelocity().add(launchDirection.setY(1)));
            activePlayer.disableDoubleJump();
        }
    }

    // No fall damage
    // -> The player cannot get fall damage

    // MOVE 4
    // Forward Dash
    // -> Player dashes forwards
    public static void move4(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        player.setVelocity(player.getLocation().getDirection().multiply(2).setY(0.1));
    }

    // MOVE 5
    // Backward Dash
    // -> Player dashes backwards
    public static void move5(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        player.setVelocity(player.getLocation().getDirection().multiply(-2).setY(0.1));
    }

    // MOVE 6


    // MOVE 7
    // Charge Jump
    // -> Charges until next activation
    // -> The longer you charge, the higher you jump
    // -> Adds slowness when charging
    public static void move7(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        if ((int) activePlayer.getCharge() == -1) {
            activePlayer.setChargingStart();
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000, 10, true, false, false));
            activePlayer.setMove7LaunchState(1);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (activePlayer.getMove7LaunchState() == 1) {
                        activePlayer.setMove7LaunchState(0);
                        move7(activePlayer);
                    }
                }
            }.runTaskLater(StaticVariables.plugin, 90);
        } else {
            double velocityY = activePlayer.getCharge() / 1000;
            activePlayer.resetCharge();
            player.removePotionEffect(PotionEffectType.SLOW);
            activePlayer.setMove7LaunchState(2);
            player.setVelocity(new Vector(0, velocityY, 0));
        }
    }


    // MOVE 8
}





















