package com.lennertsoffers.elementalstones.moves.airMoves.airbending;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class WindCloak extends Move {

    /**
     * <b>MOVE 6: Wind Cloak</b>
     * <p>
     *     Gives the player a cloak of wind<br>
     *     Any entity damaging this player will be knocked back and get the levitation effect<br>
     *     <ul>
     *         <li><b>Damage: </b> 3</li>
     *         <li><b>Knockback: </b> 5</li>
     *         <li><b>PotionEffect: </b> Levitation (duration: 5s, amplifier: 3)</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public WindCloak(ActivePlayer activePlayer) {
        super(activePlayer, "Wind Cloak", "air_stone", "airbending_stone", 6);
    }

    @Override
    public void useMove() {
        World world = this.getPlayer().getWorld();
        this.getActivePlayer().setWindCloak(true);

        new BukkitRunnable() {
            int amountOfTicks = 0;

            @Override
            public void run() {

                Location location = getPlayer().getLocation().add(0, 1.1, 0);
                for (int i = 0; i < 10; i++) {
                    world.spawnParticle(Particle.SPIT, MathTools.locationOnCircle(location, 0.5, i, world), 0);
                }

                if (amountOfTicks > 200) {
                    this.cancel();
                    getActivePlayer().setWindCloak(false);
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

        this.setCooldown();
    }

    /**
     * <b>Creates the knockback and levitation effect</b>
     * <p>
     *     Triggered when an entity damages the player with the wind cloak<br>
     * </p>
     *
     * @param activePlayer the activeplayer with the wind cloak
     * @param event the EntityDamageByEntityEvent triggered when the player is attacked
     */
    public static void windCloakKnockback(ActivePlayer activePlayer, EntityDamageByEntityEvent event) {
        if (activePlayer.hasWindCloak()) {
            Player player = activePlayer.getPlayer();
            Entity entity = event.getDamager();
            if (entity != player) {
                if (entity instanceof LivingEntity) {
                    LivingEntity damager = (LivingEntity) entity;
                    Vector direction = MathTools.getDirectionNormVector(player.getLocation(), damager.getLocation());
                    damager.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 3, true, true, true));
                    damager.setVelocity(direction.clone().multiply(5).setY(0.5));
                    damager.damage(3, player);
                }
            }
        }
    }
}
