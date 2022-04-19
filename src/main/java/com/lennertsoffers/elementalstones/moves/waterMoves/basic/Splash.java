package com.lennertsoffers.elementalstones.moves.waterMoves.basic;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Splash extends Move {

    /**
     * <b>MOVE 1: Splash</b>
     * <p>
     *     Splashes around some water<br>
     *     The higher the level of the player, the more damage it does<br>
     *     <ul>
     *         <li><b>Damage:</b> 2^(level / 30)</li>
     *     </ul>
     * </p>
     * @param activePlayer the activeplayer executing the move
     */
    public Splash(ActivePlayer activePlayer) {
        super(activePlayer, "Splash", "water_stone", "default", 1);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();
        World world = player.getWorld();
        player.setVelocity(new Vector(0, 0.2, 0));
        new BukkitRunnable() {
            @Override
            public void run() {
                for (
                        int i = 0;
                        i < 360; i++) {
                    Location centerLocation = MathTools.locationOnCircle(player.getLocation(), 3, i, world);
                    NearbyEntityTools.damageNearbyEntities(player, centerLocation, Math.pow(2, player.getLevel() / 30f), 1, 1, 1);

                    for (int j = 0; j < 10; j++) {
                        double locationX = centerLocation.getX() + StaticVariables.random.nextGaussian() / 2;
                        double locationY = centerLocation.getY() + StaticVariables.random.nextGaussian() / 10;
                        double locationZ = centerLocation.getZ() + StaticVariables.random.nextGaussian() / 2;
                        world.spawnParticle(Particle.BUBBLE_POP, locationX, locationY, locationZ, 0);
                    }
                }
            }
        }.runTaskLater(StaticVariables.plugin, 5L);

        this.setCooldown();
    }
}
