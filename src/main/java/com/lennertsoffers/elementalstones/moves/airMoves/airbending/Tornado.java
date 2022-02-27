package com.lennertsoffers.elementalstones.moves.airMoves.airbending;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Tornado extends Move {

    /**
     * <b>MOVE 7: Tornado</b>
     * <p>
     *     Shoots a tornado in the looking direction launching entities up<br>
     *     <ul>
     *         <li><b>Knockup: </b> 2</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     */
    public Tornado(ActivePlayer activePlayer) {
        super(activePlayer, "Tornado", "air_stone", "airbending_stone", 7);
    }

    @Override
    public void useMove() {
        World world = this.getPlayer().getWorld();
        final Location location = this.getPlayer().getLocation();
        final Vector direction = location.getDirection();
        location.add(direction);
        Vector velocity = new Vector(0, 2, 0);

        new BukkitRunnable() {
            int amountOfTicks = 0;
            int angle1 = 0;

            @Override
            public void run() {

                Location centerLocation = location.clone();
                for (int i = 10; i < 100; i++) {
                    Location particle1Location = MathTools.locationOnCircle(centerLocation, i / 20f, angle1, world);
                    world.spawnParticle(Particle.SPELL_MOB, particle1Location, 0, 1, 1, 1);
                    angle1 += 37;
                    centerLocation.add(0, 0.1, 0);
                    if (i % 10 == 0) {
                        NearbyEntityTools.damageNearbyEntities(getPlayer(), centerLocation, 0, i / 20f, 1, i / 20f, velocity);
                    }
                }
                location.add(direction.clone().multiply(0.2));

                if (amountOfTicks > 200) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

        this.setCooldown();
    }
}
