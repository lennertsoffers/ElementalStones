package com.lennertsoffers.elementalstones.moves.airMoves.airbending;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class AirSlash extends Move {

    /**
     * <b>MOVE 4: Air Slash</b>
     * <p>
     *     A high speed high damage air slash<br>
     *     Executing this move while falling or jumping does critical damage<br>
     *     The move launches the player down and creates a slash where the player looks
     *     <ul>
     *         <li><b>Damage: </b> 4 (8 on critical)</li>
     *         <li><b>Range: </b> 1</li>
     *         <li><b>PotionEffects: </b>
     *             <ul>
     *                 <li>Slowness (duration: 10, amplifier: 2)</li>
     *                 <li>Blindness (duration: 20, amplifier: 1)</li>
     *             </ul>
     *         </li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     */
    public AirSlash(ActivePlayer activePlayer) {
        super(activePlayer, "Air Slash", "air_stone", "airbending_stone", 4);
    }

    @Override
    public void useMove() {
        boolean critical = !((Entity) this.getPlayer()).isOnGround();
        if (critical) {
            this.getPlayer().setVelocity(this.getPlayer().getVelocity().add(new Vector(0, -1, 0)));
            this.getActivePlayer().setCriticalOnGround(true);
        } else {
            slashEffect(this.getPlayer(), false);
        }

        this.setCooldown();
    }

    /**
     * <b>BukkitRunnable that creates the slash effect</b>
     * <p>
     *     When the attack is critical, crit particles are spawned too
     * </p>
     * @param player the player executing this move
     * @param critical if the move is a critical or not
     */
    public static void slashEffect(Player player, boolean critical) {
        World world = player.getWorld();
        Location location = player.getLocation();
        Vector direction = location.getDirection();
        location.add(0, 2, 0);
        List<PotionEffect> potionEffects = Arrays.asList(
                new PotionEffect(PotionEffectType.SLOW, 10, 2, false, false, false),
                new PotionEffect(PotionEffectType.BLINDNESS, 20, 1, false, false, false)
        );

        new BukkitRunnable() {
            int amountOfTicks = 0;

            @Override
            public void run() {

                Location runnableLocation = location.clone();

                for (int i = 70; i >= (70 - (amountOfTicks * 46.66)); i--) {
                    Location particleLocation = runnableLocation.clone().add(direction.clone().rotateAroundY(i / 100f).multiply(2));

                    double nominator = Math.abs(i) / 2.5;

                    if (nominator < 10) {
                        nominator = 10;
                    }

                    double locationX = particleLocation.getX() + StaticVariables.random.nextGaussian() / nominator;
                    double locationY = particleLocation.getY() + StaticVariables.random.nextGaussian() / nominator;
                    double locationZ = particleLocation.getZ() + StaticVariables.random.nextGaussian() / nominator;

                    world.spawnParticle(Particle.SPELL_MOB, locationX, locationY, locationZ, 0, 1, 1, 1);

                    if (i % 10 == 0) {
                        int damage = 4;
                        if (critical) {
                            world.spawnParticle(Particle.CRIT, particleLocation.clone().add(0, 0.3, 0), 0);
                            damage = 8;
                        }

                        NearbyEntityTools.damageNearbyEntities(player, particleLocation, damage, 0.4, 0.4, 0.4, potionEffects);
                    }

                    runnableLocation.add(0, -1 / 100f, 0);
                }

                if (amountOfTicks > 3) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }
}
