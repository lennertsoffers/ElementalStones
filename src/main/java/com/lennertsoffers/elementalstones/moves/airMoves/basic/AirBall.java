package com.lennertsoffers.elementalstones.moves.airMoves.basic;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AirBall extends Move {

    /**
     * <b>MOVE 1: Air Ball</b>
     * <p>
     *     Shoots an air ball in the looking direction<br>
     *     Damages entities on hit<br>
     *     <ul>
     *         <li><b>Range: </b> 20</li>
     *         <li><b>Knockback:</b> 2</li>
     *     </ul>
     * </p>
     * @param activePlayer the activeplayer executing the move
     */
    public AirBall(ActivePlayer activePlayer) {
        super(activePlayer, "Air Ball", "air_stone", "default", 1);
    }
    
    @Override
    public void useMove() {
        Location location = this.getPlayer().getLocation().add(this.getPlayer().getLocation().getDirection()).add(0, 1, 0);
        Vector direction = location.getDirection();
        World world = this.getPlayer().getWorld();

        new BukkitRunnable() {
            int amountOfTicks = 0;
            boolean collision = false;

            @Override
            public void run() {
                double speedMultiplier = 1.3;
                for (int i = 0; i < 20; i++) {
                    world.spawnParticle(Particle.CLOUD, location.clone().add(StaticVariables.random.nextGaussian() / 6, StaticVariables.random.nextGaussian() / 6, StaticVariables.random.nextGaussian() / 6), 0, direction.getX() * speedMultiplier, direction.getY() * speedMultiplier, direction.getZ() * speedMultiplier);
                }

                if (NearbyEntityTools.damageNearbyEntities(getPlayer(), location, 5, 0.6, 0.8, 0.6, direction.clone().multiply(2))) {
                    collision = true;
                } else {
                    if (location.getBlock().getType() != Material.AIR) {
                        collision = true;
                    }
                }
                location.add(direction);

                if (amountOfTicks > 20 || collision) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

        this.setCooldown();
    }
}
