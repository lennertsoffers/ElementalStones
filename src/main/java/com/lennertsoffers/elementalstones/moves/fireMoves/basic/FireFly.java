package com.lennertsoffers.elementalstones.moves.fireMoves.basic;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class FireFly extends Move {

    /**
     * <b>MOVE 3: Fire Fly</b>
     * <p>
     *     The player get trusted in the looking direction of the player<br>
     *     Any entities colliding with the player get some damage and are set on fire<br>
     *     <ul>
     *         <li><b>Damage:</b> 3</li>
     *         <li><b>Duration:</b> 30s</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     */
    public FireFly(ActivePlayer activePlayer) {
        super(activePlayer, "Fire Fly", "fire_stone", "default", 3);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();
        World world = player.getWorld();
        Random random = new Random();
        Vector noVelocity = new Vector(0, 0, 0);
        player.setGliding(true);

        new BukkitRunnable() {
            int amountOfTicks = 0;

            @Override
            public void run() {
                if (amountOfTicks > 600 || (amountOfTicks > 20 && ((Entity) player).isOnGround())) {
                    player.setGliding(false);
                    this.cancel();

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Location location = player.getLocation();
                            if (player.getFallDistance() > 3) {
                                player.setVelocity(noVelocity);
                                player.setFallDistance(0);

                                for (int i = 0; i < 50; i++) {
                                    world.spawnParticle(Particle.FLAME, location, 0, random.nextGaussian() / 4, random.nextDouble() / -4, random.nextGaussian() / 4);
                                }
                            } else if (((Entity) player).isOnGround()) {
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(StaticVariables.plugin, 0L, 5L);
                }

                Location location = player.getLocation();
                Vector direction = location.getDirection();
                player.setVelocity(new Vector(direction.getX(), direction.getY(), direction.getZ()));
                player.setGliding(true);

                for (int i = 0; i < 20; i++) {
                    world.spawnParticle(Particle.FLAME, location, 0, random.nextDouble() / 10, random.nextDouble() / 10, random.nextDouble() / 10);
                }

                NearbyEntityTools.damageNearbyEntities(player, location, 3, 1, 1, 1, livingEntity -> livingEntity.setFireTicks(100));

                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

        this.setCooldown();
    }
}
