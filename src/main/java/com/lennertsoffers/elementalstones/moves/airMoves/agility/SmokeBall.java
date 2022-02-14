package com.lennertsoffers.elementalstones.moves.airMoves.agility;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SmokeBall extends Move {

    public SmokeBall(ActivePlayer activePlayer) {
        super(activePlayer, "Smoke Ball", "air_stone", "agility_stone", 6);
    }
    
    @Override
    public void useMove() {
        World world = this.getPlayer().getWorld();
        Location startingLocation = this.getPlayer().getLocation().add(this.getPlayer().getLocation().getDirection().clone().multiply(2)).add(0, 1, 0);
        final Location impactLocation = this.getPlayer().getLocation();
        PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOW, 100, 3, true, true, true);

        BukkitRunnable smoke = new BukkitRunnable() {
            int amountOfTicks = 0;

            @Override
            public void run() {
                for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
                    double radius = Math.sin(i) * 3;
                    double y = Math.cos(i) * 3;
                    for (double a = 0; a < Math.PI * 2; a += Math.PI / 10) {
                        double x = Math.cos(a) * radius;
                        double z = Math.sin(a) * radius;
                        Location particleLocation = impactLocation.clone().add(0, 2, 0).add(x, y, z);
                        getPlayer().getWorld().spawnParticle(Particle.REDSTONE, particleLocation.add(StaticVariables.random.nextGaussian() / 4, StaticVariables.random.nextGaussian() / 4, StaticVariables.random.nextGaussian() / 4), 0, 0, 0, 0, 0, new Particle.DustOptions(Color.WHITE, 100));
                    }
                }

                world.getNearbyEntities(impactLocation, 3, 3, 3, entity -> entity instanceof LivingEntity && entity != getPlayer()).forEach(entity -> ((LivingEntity) entity).addPotionEffect(potionEffect));

                if (amountOfTicks > 80) {
                    this.cancel();
                }
                amountOfTicks += 1;
            }
        };

        new BukkitRunnable() {
            int amountOfTicks = 0;
            final Location currentLocation = startingLocation;
            boolean endThrow = false;

            @Override
            public void run() {
                Vector playerDirection = getPlayer().getLocation().getDirection();
                world.spawnParticle(Particle.CLOUD, startingLocation, 0, playerDirection.getX(), playerDirection.getY(), playerDirection.getZ());

                if (currentLocation.getBlock().getType().isSolid()) {
                    endThrow = true;
                } else if (!world.getNearbyEntities(currentLocation, 0.5, 0.5, 0.5, entity -> entity instanceof LivingEntity).isEmpty()) {
                    endThrow = true;
                }

                if (amountOfTicks > 20 || endThrow) {
                    impactLocation.setX(currentLocation.getX());
                    impactLocation.setY(currentLocation.getY());
                    impactLocation.setZ(currentLocation.getZ());
                    this.cancel();
                    smoke.runTaskTimer(StaticVariables.plugin, 0, 5L);
                }

                amountOfTicks++;
                currentLocation.add(playerDirection);
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }
}
