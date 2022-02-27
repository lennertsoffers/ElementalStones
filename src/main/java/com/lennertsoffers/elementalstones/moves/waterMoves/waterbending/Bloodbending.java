package com.lennertsoffers.elementalstones.moves.waterMoves.waterbending;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Bloodbending extends Move {

    /**
     * <b>ULTIMATE: Bloodbending</b>
     * <p>
     *     <b>Activation 1:</b> the player selects the closest target he/she is looking at<br>
     *     <b>Activation 2:</b> the player grabs the target and can move it around freely<br>
     *     <b>Activation 3:</b> if the player makes a throw movement with the target and then looses it, the target will fly away (optional)<br>
     *     <ul>
     *         <li><b>Range:</b> 40</li>
     *         <li><b>Duration:</b> 20</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing this move
     */
    public Bloodbending(ActivePlayer activePlayer) {
        super(activePlayer, "Bloodbending", "air_stone", "airbending_stone", 8);
    }

    @Override
    public void useMove() {
        World world = this.getPlayer().getWorld();
        Location location = this.getPlayer().getLocation().add(0, 1, 0);
        Vector direction = location.getDirection();

        if (this.getActivePlayer().hasPossibleTarget() && this.getActivePlayer().isNotLevitatingTarget()) {

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
                        world.spawnParticle(Particle.ITEM_CRACK, particleX, particleY, particleZ, 0, Material.REDSTONE_BLOCK);
                    }

                    if (target.isDead()) {
                        endLevitation();
                    }

                    if (amountOfTicks > 400) {
                        endLevitation();
                    }

                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L));
        } else {
            this.endLevitation();
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

    private void endLevitation() {
        this.setCooldown();
        this.getActivePlayer().stopLevitatingTask();
    }
}
