package com.lennertsoffers.elementalstones.stones.windStone;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Collections;

public class AirStoneSharedPassive extends AirStone {

    /**
     * <b>PASSIVE 1: No Fall Damage</b>
     * <p>
     *     The player won't take any fall damage<br>
     * </p>
     *
     * @param event this move is triggered when the player would normally take fall damage
     */
    public static void passive1(ActivePlayer activePlayer, EntityDamageEvent event) {
        Player player = activePlayer.getPlayer();
        if (
                !Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.airbendingStones) ||
                !Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.agilityStones)
        ) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
            }
        }
    }

    /**
     * <b>PASSIVE 2: Double Jump</b>
     * <p>
     *     The player can jump a second time after jumping in the air with space bar<br>
     *     If the player is standing still he get launched up<br>
     *     Otherwise he gets launched in the moving direction<br>
     *     For the AirbendingStone the velocity and height is is multiplied with 0.6<br>
     * </p>
     *
     * @param event this move is triggered when the player would normally toggle flight mode
     */
    public static void passive2(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());

        if (activePlayer != null) {
            boolean airbendingDoubleJump = false;
            boolean agilityDoubleJump = false;

            if (!Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.airbendingStones)) {
                airbendingDoubleJump = true;
            } else if (!Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.agilityStones)) {
                agilityDoubleJump = true;
            }

            if (airbendingDoubleJump || agilityDoubleJump) {
                event.setCancelled(true);

                if (activePlayer.canDoubleJump()) {
                    Vector launchDirection;
                    launchDirection = activePlayer.getMovingDirection();
                    launchDirection.setY(1);

                    if (airbendingDoubleJump) {
                        launchDirection.multiply(0.6);
                    }

                    if (player.getVelocity().getY() < 0) {
                        player.setVelocity(player.getVelocity().setY(0));
                    }
                    player.setVelocity(player.getVelocity().add(launchDirection));
                    activePlayer.disableDoubleJump();

                    Location location = player.getLocation();
                    World world = player.getWorld();
                    Vector inverseLaunchDirection = launchDirection.clone().multiply(-0.5);
                    for (int i = 0; i < 40; i++) {
                        double x = location.getX() + StaticVariables.random.nextGaussian() / 10;
                        double y = location.getY() + StaticVariables.random.nextGaussian() / 10;
                        double z = location.getZ() + StaticVariables.random.nextGaussian() / 10;
                        world.spawnParticle(Particle.CLOUD, x, y, z, 0, inverseLaunchDirection.getX(), inverseLaunchDirection.getY(), inverseLaunchDirection.getZ());
                    }
                }
            }
        }
    }
}
