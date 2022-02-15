package com.lennertsoffers.elementalstones.moves.earthMoves.earthbending;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Stomp extends Move {

    public Stomp(ActivePlayer activePlayer) {
        super(activePlayer, "Stomp", "earth_stone", "earthbending_stone", 5);
    }

    @Override
    public void useMove() {
        World world = this.getPlayer().getWorld();
        Location location = this.getPlayer().getLocation();
        Vector direction = location.getDirection();
        direction.setX(direction.getX());
        direction.setY(0);
        direction.setZ(direction.getZ());
        new BukkitRunnable() {
            int counter = 0;

            @Override
            public void run() {
                location.add(direction);
                Location blockLocation = CheckLocationTools.getClosestAirBlockLocation(location);

                if (blockLocation != null) {
                    world.spawnParticle(Particle.SMOKE_LARGE, blockLocation, 0, 0, -0.5, 0);

                    NearbyEntityTools.damageNearbyEntities(getPlayer(), blockLocation, 7, 1, 1, 1, new Vector(0, 0.5, 0));
                }

                counter++;
                if (counter >= 50) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }
}
