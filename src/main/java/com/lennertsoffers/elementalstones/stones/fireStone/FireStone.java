package com.lennertsoffers.elementalstones.stones.fireStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FireStone {

    /**
     * <b>MOVE 1: A-Quick-Snack</b>
     * <p>
     *     Turns any raw food to the cooked variant of it<br>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
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
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            Location startLocation = player.getLocation();

            world.setTime(18000);
            world.setThundering(true);


            Block block = player.getTargetBlockExact(30);
            if (block != null) {
                Location location = block.getLocation();

                for (int i = 0; i < 50; i++) {
                    world.strikeLightning(location);
                }
            }

        };
    }

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
        };
    }
}
