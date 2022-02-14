package com.lennertsoffers.elementalstones.moves.airMoves.agility;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Dash extends Move {

    public Dash(ActivePlayer activePlayer) {
        super(activePlayer, "Dash", "air_stone", "agility_stone", 4);
    }
    
    @Override
    public void useMove() {
        new BukkitRunnable() {
            int amountOfTicks = 0;
            @Override
            public void run() {
                World world = getPlayer().getWorld();

                if (NearbyEntityTools.damageNearbyEntities(getPlayer(), getPlayer().getLocation(), getActivePlayer().isInAirBoost() ? 7 : 3, 0.7, 0.7, 0.7)) {
                    world.playSound(getPlayer().getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 1);
                }

                world.spawnParticle(Particle.CLOUD, getPlayer().getLocation().add(0, 1, 0), 0);

                if (amountOfTicks > 10) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

        int multiplier = 2;
        if (this.getActivePlayer().isInAirBoost()) {
            multiplier = 4;
        }

        Vector velocity = this.getPlayer().getLocation().getDirection().multiply(multiplier);
        if (velocity.getY() < 0.2 && velocity.getY() > -0.2) {
            velocity.setY(0.2);
        }

        this.getPlayer().setVelocity(velocity);
    }
}
