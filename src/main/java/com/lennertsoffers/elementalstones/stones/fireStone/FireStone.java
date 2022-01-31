package com.lennertsoffers.elementalstones.stones.fireStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FireStone {

    // MOVE 1
    // A-Quick-Snack
    // -> Turns every raw stack of food to the cooked version of it
    public static Runnable move1(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null) {
                    Material material = itemStack.getType();
                    if (material == Material.BEEF) {
                        itemStack.setType(Material.COOKED_BEEF);
                    } else if (material == Material.PORKCHOP) {
                        itemStack.setType(Material.COOKED_PORKCHOP);
                    } else if (material == Material.SALMON) {
                        itemStack.setType(Material.COOKED_SALMON);
                    } else if (material == Material.MUTTON) {
                        itemStack.setType(Material.COOKED_MUTTON);
                    } else if (material == Material.POTATO) {
                        itemStack.setType(Material.BAKED_POTATO);
                    } else if (material == Material.CHICKEN) {
                        itemStack.setType(Material.COOKED_CHICKEN);
                    } else if (material == Material.COD) {
                        itemStack.setType(Material.COOKED_COD);
                    } else if (material == Material.RABBIT) {
                        itemStack.setType(Material.COOKED_RABBIT);
                    }
                }
            }
        };
    }

    // MOVE 2
    // Floating Fire
    // -> You can summon a fireball and hold it right in front of you which damages entities
    public static Runnable move2(ActivePlayer activePlayer) {
        return () -> {
            if (activePlayer.getFloatingFire() == null) {
                Player player = activePlayer.getPlayer();
                World world = player.getWorld();
                Random random = new Random();
                activePlayer.setFloatingFire(new BukkitRunnable() {
                    int ticksLived = 1200;
                    final Map<LivingEntity, Long> damagedEntityCooldown = new HashMap<>();

                    @Override
                    public void run() {
                        Vector direction = player.getLocation().getDirection();
                        Location location = player.getLocation().add(direction.getX() * 2.5, direction.getY() * 2.5, direction.getZ() * 2.5).add(0, 1.5, 0);
                        activePlayer.setFloatingFireLocation(location);
                        double nominator = ticksLived / 200f * 2;
                        for (Entity entity : world.getNearbyEntities(location, 0.5, 0.5, 0.5)) {
                            if (entity != null) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingEntity = (LivingEntity) entity;
                                    if (livingEntity != player) {
                                        if (damagedEntityCooldown.containsKey(livingEntity)) {
                                            if (damagedEntityCooldown.get(livingEntity) < System.currentTimeMillis()) {
                                                entity.setFireTicks(200);
                                                ((LivingEntity) entity).damage(10 / nominator);
                                                damagedEntityCooldown.replace(livingEntity, System.currentTimeMillis() + (10 * 1000));
                                            }
                                        } else {
                                            entity.setFireTicks(200);
                                            ((LivingEntity) entity).damage(10 / nominator);
                                            damagedEntityCooldown.put(livingEntity, System.currentTimeMillis() + (10 * 1000));
                                        }
                                    }
                                }
                            }
                        }
                        for (int i = 0; i < 30; i++) {
                            Vector fireballDirection = player.getLocation().getDirection();
                            Location fireballLocation = player.getLocation().add(fireballDirection.getX() * 2.5, fireballDirection.getY() * 2.5 + 1, direction.getZ() * 2.5).add(0, 1.5, 0);
                            world.spawnParticle(Particle.FLAME, fireballLocation.add(random.nextGaussian() / nominator, random.nextGaussian() / nominator, random.nextGaussian() / nominator), 0, 0, 0, 0);
                        }
                        if (ticksLived > 2400) {
                            this.cancel();
                        }
                        ticksLived++;
                    }
                });
            }
            System.out.println(activePlayer.getFloatingFire());
        };
    }

    /**
     * <b>MOVE 3: Fire Fly</b>
     * <p>
     *     The player get trusted in the looking direction of the player<br>
     *     Any entities colliding with the player get some damage and are set on fire<br>
     *     <ul>
     *         <li><b>Damage:</b> 4</li>
     *         <li><b>Duration:</b> 30s</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move3(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
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
                                        world.spawnParticle(Particle.FLAME, location, 0, random.nextGaussian() / 8, random.nextDouble() / -8, random.nextGaussian() / 8);
                                    }

                                    this.cancel();
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

                    NearbyEntityTools.damageNearbyEntities(player, location, 4, 1, 1, 1, direction, p -> p.setFireTicks(100));

                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }
}
