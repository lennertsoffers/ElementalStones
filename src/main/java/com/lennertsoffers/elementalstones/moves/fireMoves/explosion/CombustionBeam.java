package com.lennertsoffers.elementalstones.moves.fireMoves.explosion;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;

public class CombustionBeam extends Move {

    public CombustionBeam(ActivePlayer activePlayer) {
        super(activePlayer, "Combustion Beam", "fire_stone", "explosion_stone", 6);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();
        World world = player.getWorld();
        Location startLocation = player.getLocation().add(0, 1, 0);
        Vector direction = startLocation.getDirection();

        Location endLocation = null;

        // Try to find target entity
        for (int i = 1; i <= 20; i++) {
            Collection<Entity> livingEntities = world.getNearbyEntities(startLocation.clone().add(direction.clone().multiply(i)), 1.5, 1.5, 1.5, entity -> entity instanceof LivingEntity && entity != player);
            if (!livingEntities.isEmpty()) {
                for (Entity entity : livingEntities) {
                    Location entityLocation = entity.getLocation().add(0, 1, 0);

                    if (endLocation != null) {
                        if (MathTools.calculate3dDistance(startLocation, entityLocation) < MathTools.calculate3dDistance(startLocation, endLocation)) {
                            endLocation = entityLocation;
                        }
                    } else {
                        endLocation = entityLocation;
                    }
                }
            }
        }

        // Set end location to target block or 20 blocks distance
        if (endLocation == null) {
            Block block = player.getTargetBlockExact(20, FluidCollisionMode.NEVER);

            if (block != null) {
                endLocation = block.getLocation().add(0.5, 2, 0.5);
            } else {
                endLocation = startLocation.clone().add(direction.clone().multiply(20));
            }
        }

        direction = new Vector(endLocation.getX() - startLocation.getX(), endLocation.getY() - startLocation.getY(), endLocation.getZ() - startLocation.getZ());
        Vector perpendicularDirection = direction.clone().rotateAroundY(Math.PI / 2).multiply(0.05);

        Location location1 = startLocation.clone().add(direction.clone().multiply(0.25).add(perpendicularDirection.clone().rotateAroundAxis(direction, StaticVariables.random.nextInt(360))));
        Location location2 = startLocation.clone().add(direction.clone().multiply(0.50)).add(perpendicularDirection.clone().rotateAroundAxis(direction, StaticVariables.random.nextInt(360)));

        Vector start_location1 = new Vector(location1.getX() - startLocation.getX(), location1.getY() - startLocation.getY(), location1.getZ() - startLocation.getZ());
        Vector location1_location2 = new Vector(location2.getX() - location1.getX(), location2.getY() - location1.getY(), location2.getZ() - location1.getZ());
        Vector location2_end = new Vector(endLocation.getX() - location2.getX(), endLocation.getY() - location2.getY(), endLocation.getZ() - location2.getZ());

        for (double i = 0.01; i <= 1; i += 0.01) {
            world.spawnParticle(Particle.WAX_OFF, startLocation.clone().add(start_location1.clone().multiply(i)), 0);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                for (double i = 0.01; i <= 1; i += 0.01) {
                    world.spawnParticle(Particle.WAX_OFF, location1.clone().add(location1_location2.clone().multiply(i)), 0);
                }
            }
        }.runTaskLater(StaticVariables.plugin, 2L);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (double i = 0.01; i <= 1; i += 0.01) {
                    world.spawnParticle(Particle.WAX_OFF, location2.clone().add(location2_end.clone().multiply(i)), 0);
                }
            }
        }.runTaskLater(StaticVariables.plugin, 4L);

        Location finalEndLocation = endLocation;
        new BukkitRunnable() {
            @Override
            public void run() {
                double damage = player.getHealth() <= 8 ? player.getHealth() - 0.5 : 8;
                player.damage(damage);
                world.createExplosion(finalEndLocation, 3, true, true, player);
            }
        }.runTaskLater(StaticVariables.plugin, 6L);
    }
}
