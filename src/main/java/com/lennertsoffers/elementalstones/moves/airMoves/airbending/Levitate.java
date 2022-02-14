package com.lennertsoffers.elementalstones.moves.airMoves.airbending;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Levitate extends Move {

    public Levitate(ActivePlayer activePlayer) {
        super(activePlayer, "Levitate", "air_stone", "airbending_stone", 8);
    }

    @Override
    public void useMove() {
        World world = this.getPlayer().getWorld();
        Location location = this.getPlayer().getLocation().add(0, 1, 0);
        Vector direction = location.getDirection();

        if (!this.getActivePlayer().hasPossibleTarget() && this.getActivePlayer().isNotLevitatingTarget()) {

            // Finding target
            Entity target = null;
            for (int i = 1; i < 40; i++) {
                if (!world.getNearbyEntities(location.clone().add(direction.clone().multiply(i)), 1, 1, 1).isEmpty()) {
                    for (Entity entity : world.getNearbyEntities(location.clone().add(direction.clone().multiply(i)), 1, 1, 1)) {
                        if (entity != null) {
                            if (!(entity instanceof Item || entity instanceof Arrow)) {
                                if (entity != this.getPlayer()) {
                                    if (target != null) {
                                        if (MathTools.calculate3dDistance(location, entity.getLocation()) < MathTools.calculate3dDistance(location, target.getLocation())) {
                                            target = entity;
                                        }
                                    } else {
                                        target = entity;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (target != null) {
                target.setGlowing(true);
                this.getActivePlayer().setPossibleTarget(target);
            }
        } else if (this.getActivePlayer().isNotLevitatingTarget()) {

            // Levitating target
            this.getActivePlayer().clearTarget();
            this.getActivePlayer().setMove8from(null);
            this.getActivePlayer().setMove8to(null);
            Entity target = this.getActivePlayer().getPossibleTarget();
            this.getActivePlayer().setTarget(target);
            this.getActivePlayer().setLevitatingTask(new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {
                    Location playerLocation = getPlayer().getLocation();
                    Vector playerDirection = playerLocation.getDirection();
                    playerLocation.add(playerDirection.clone().multiply(10));

                    if (playerLocation.getBlock().getType().isSolid()) {
                        playerLocation.setY(world.getHighestBlockYAt(playerLocation) + 1);
                    }

                    getActivePlayer().setMove8from(getActivePlayer().getMove8to());
                    getActivePlayer().setMove8to(playerLocation);
                    target.teleport(playerLocation);

                    for (int i = 0; i < 10; i++) {
                        double particleX = playerLocation.clone().getX() + StaticVariables.random.nextGaussian() / 5;
                        double particleY = playerLocation.clone().getY() + StaticVariables.random.nextGaussian() / 5 + 1;
                        double particleZ = playerLocation.clone().getZ() + StaticVariables.random.nextGaussian() / 5;
                        int grayValue = (1 + StaticVariables.random.nextInt(30));
                        world.spawnParticle(Particle.SPELL_MOB, particleX, particleY, particleZ, 0, grayValue, grayValue, grayValue);
                    }

                    if (amountOfTicks > 400) {
                        getActivePlayer().stopLevitatingTask();
                    }

                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L));
        } else {
            this.getActivePlayer().stopLevitatingTask();
            Entity target = this.getActivePlayer().getTarget();

            if (
                    this.getActivePlayer().getMove8from().getX() != this.getActivePlayer().getMove8to().getX() &&
                    this.getActivePlayer().getMove8from().getY() != this.getActivePlayer().getMove8to().getY() &&
                    this.getActivePlayer().getMove8from().getZ() != this.getActivePlayer().getMove8to().getZ()
            ) {
                target.setVelocity(MathTools.getDirectionNormVector3d(this.getActivePlayer().getMove8from(), this.getActivePlayer().getMove8to()).multiply(4));
            }
        }
    }
}
