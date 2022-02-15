package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.moves.fireMoves.hellfire.FireShields;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FireWall extends BukkitRunnable {

    private final ActivePlayer activePlayer;
    private final World world;
    private final Location bottomLeftLocation;
    private final Vector wallDirection;

    public FireWall(ActivePlayer activePlayer, Location bottomLeftLocation, Vector wallDirection) {
        this.activePlayer = activePlayer;
        Player player = activePlayer.getPlayer();
        this.world = player.getWorld();
        this.bottomLeftLocation = bottomLeftLocation;
        this.wallDirection = wallDirection;

        if (this.activePlayer.canPlaceWall()) {
            this.activePlayer.placeWall();
        }
    }

    @Override
    public void run() {
        for (double i = 0; i < 2.0; i += 0.1) {
            this.activePlayer.getWallLocations().add(this.bottomLeftLocation.clone().add(this.wallDirection.clone().multiply(i)).add(0, 1, 0));

            for (double j = 0; j < 2.0; j += 0.1) {
                this.world.spawnParticle(Particle.FLAME, this.bottomLeftLocation.clone().add(this.wallDirection.clone().multiply(i)).add(0, j, 0), 0);
            }
        }

        if (System.currentTimeMillis() >this.activePlayer.getFireWallsEndTime()) {
            this.cancel();

            if (this.activePlayer.getFireWallsEndTime() != -1) {
                this.activePlayer.endFireWalls();
                this.activePlayer.getMoveController().getMoves().forEach(move -> {
                    if (move instanceof FireShields) {
                        move.setCooldown();
                    }
                });
            }
        }
    }
}
