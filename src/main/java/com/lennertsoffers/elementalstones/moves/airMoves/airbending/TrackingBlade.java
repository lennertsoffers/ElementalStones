package com.lennertsoffers.elementalstones.moves.airMoves.airbending;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class TrackingBlade extends Move {

    /**
     * <b>MOVE 5: Tracking Blade</b>
     * <p>
     *     Shoots a blade of air that tracks the closets entity you are looking at<br>
     *     It explodes on collision of after a certain amount of time<br>
     *     <ul>
     *         <li><b>Damage: </b> 6</li>
     *         <li><b>Range: </b> 55</li>
     *         <li><b>Duration: </b> 20</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public TrackingBlade(ActivePlayer activePlayer) {
        super(activePlayer, "Tracking Blade", "air_stone", "airbending_stone", 5);
    }

    @Override
    public void useMove() {
        World world = this.getPlayer().getWorld();
        Location playerLocation = this.getPlayer().getLocation();
        Location bladeStartLocation = playerLocation.clone();
        Vector bladeStartDirection = bladeStartLocation.getDirection();
        bladeStartLocation.add(bladeStartDirection).add(0, 1, 0);

        LivingEntity target = null;
        for (int i = 1; i < 50; i++) {
            if (!world.getNearbyEntities(playerLocation.clone().add(bladeStartDirection.clone().multiply(i)), 5, 2, 5).isEmpty()) {
                for (Entity entity : world.getNearbyEntities(playerLocation.clone().add(bladeStartDirection.clone().multiply(i)), 5, 2, 5)) {
                    if (entity != null) {
                        if (entity instanceof LivingEntity) {
                            LivingEntity livingEntity = (LivingEntity) entity;
                            if (entity != this.getPlayer()) {
                                if (target != null) {
                                    if (MathTools.calculate3dDistance(playerLocation, entity.getLocation()) < MathTools.calculate3dDistance(playerLocation, target.getLocation())) {
                                        target = livingEntity;
                                    }
                                } else {
                                    target = livingEntity;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (target != null) {
            target.setGlowing(true);
            LivingEntity finalTarget = target;
            Location currentLocation = bladeStartLocation.clone();

            new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {

                    Location targetLocation = finalTarget.getLocation().add(0, 1, 0);
                    Vector pathDirection = MathTools.getDirectionNormVector3d(currentLocation, targetLocation);

                    for (int i = 0; i < 20; i++) {
                        double particleLocationX = currentLocation.getX() + StaticVariables.random.nextGaussian() / 10;
                        double particleLocationZ = currentLocation.getZ() + StaticVariables.random.nextGaussian() / 10;
                        world.spawnParticle(Particle.END_ROD, particleLocationX, currentLocation.getY(), particleLocationZ, 0);
                    }

                    currentLocation.add(pathDirection.multiply(0.2));

                    if (targetLocation.getX() > currentLocation.getX() - 0.1 && targetLocation.getX() < currentLocation.getX() + 0.1 ||
                            targetLocation.getX() > currentLocation.getX() - 0.1 && targetLocation.getX() < currentLocation.getX() + 0.1 ||
                            targetLocation.getX() > currentLocation.getX() - 0.1 && targetLocation.getX() < currentLocation.getX() + 0.1
                    ) {
                        this.cancel();
                        finalTarget.damage(6, getPlayer());
                        finalTarget.setGlowing(false);
                        for (int i = 0; i < 200; i++) {
                            world.spawnParticle(Particle.END_ROD, currentLocation, 0, StaticVariables.random.nextGaussian() / 10, StaticVariables.random.nextGaussian() / 10, StaticVariables.random.nextGaussian() / 10);
                        }
                    } else if (amountOfTicks > 400 || finalTarget.isDead()) {
                        this.cancel();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

            this.setCooldown();
        }
    }
}
