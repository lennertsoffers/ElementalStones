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
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class WindCloak extends Move {

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
