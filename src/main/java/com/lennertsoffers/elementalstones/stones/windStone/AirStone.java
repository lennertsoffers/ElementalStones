package com.lennertsoffers.elementalstones.stones.windStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AirStone {

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
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move1(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = player.getLocation().add(player.getLocation().getDirection()).add(0, 1, 0);
            Vector direction = location.getDirection();
            World world = player.getWorld();

            new BukkitRunnable() {
                int amountOfTicks = 0;
                boolean collision = false;

                @Override
                public void run() {
                    double speedMultiplier = 1.3;
                    for (int i = 0; i < 20; i++) {
                        world.spawnParticle(Particle.CLOUD, location.clone().add(StaticVariables.random.nextGaussian() / 6, StaticVariables.random.nextGaussian() / 6, StaticVariables.random.nextGaussian() / 6), 0, direction.getX() * speedMultiplier, direction.getY() * speedMultiplier, direction.getZ() * speedMultiplier);
                    }

                    if (NearbyEntityTools.damageNearbyEntities(player, location, 5, 0.6, 0.8, 0.6, direction.clone().multiply(2))) {
                        collision = true;
                    } else {
                        System.out.println(location.getBlock().getType());

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
        };
    }

    /**
     * <b>MOVE 2: A(i)rea Control</b>
     * <p>
     *     Blast away every living entity in close range<br>
     *     <ul>
     *         <li><b>Range: </b> 7</li>
     *         <li><b>Knockback:</b> more the further away from the player</li>
     *         <li><b>Knockup: </b> 0.2</li>
     *     </ul>
     * </p>
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move2(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            Location location = player.getLocation().add(0, 1, 0);
            for (int i = 0; i < 360; i++) {
                Location particleLocation = MathTools.locationOnCircle(location, 4, i, world);
                Vector direction = particleLocation.add(-location.getX(), 0, -location.getZ()).toVector().setY(0);
                for (int j = 0; j < 3; j++) {
                    world.spawnParticle(Particle.SNOWFLAKE, location.clone().add(StaticVariables.random.nextGaussian() / 4, StaticVariables.random.nextGaussian() / 4, StaticVariables.random.nextGaussian() / 4), 0, direction.getX() / 5, 0, direction.getZ() / 5);
                }
            }
            if (!world.getNearbyEntities(location, 7, 2, 7).isEmpty()) {
                for (Entity entity : world.getNearbyEntities(location, 7, 2, 7)) {
                    if (entity != null) {
                        if (entity instanceof LivingEntity) {
                            LivingEntity livingEntity = (LivingEntity) entity;
                            if (livingEntity != player) {
                                Vector direction = livingEntity.getLocation().add(-location.getX(), 0, -location.getZ()).toVector().setY(0.2);
                                livingEntity.setVelocity(direction);
                            }
                        }
                    }
                }
            }
        };
    }

    /**
     * <b>MOVE 3: Suction</b>
     * <p>
     *     Sucks all the items in close range towards the player<br>
     *     It launches the items up and after 0.5s the are shot towards the player<br>
     *     The targeted items get the glowing effect<br>
     *     <ul>
     *         <li><b>Range: </b> 15</li>
     *     </ul>
     * </p>
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move3(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            if (!world.getNearbyEntities(player.getLocation(), 15, 15, 15).isEmpty()) {
                for (Entity entity : world.getNearbyEntities(player.getLocation(), 15, 15, 15)) {
                    if (entity != null) {
                        if (entity instanceof Item) {
                            Item item = (Item) entity;
                            item.setGlowing(true);
                            item.setPickupDelay(0);
                            item.setVelocity(new Vector(0, 0.7, 0));
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    item.setVelocity(MathTools.getDirectionNormVector3d(item.getLocation(), player.getLocation()).multiply(3));
                                }
                            }.runTaskLater(StaticVariables.plugin, 10L);
                        }
                    }
                }
            }
        };
    }
}
