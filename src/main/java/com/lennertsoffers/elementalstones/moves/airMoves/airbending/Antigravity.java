package com.lennertsoffers.elementalstones.moves.airMoves.airbending;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class Antigravity extends Move {

    public Antigravity(ActivePlayer activePlayer) {
        super(activePlayer, "Antigravity", "air_stone", "airbending_stone", 8);
    }

    @Override
    public void useMove() {
        World world = this.getPlayer().getWorld();
        ArrayList<LivingEntity> livingEntities = new ArrayList<>();
        Vector velocity = new Vector(0, 0.3, 0);

        for (Entity entity : world.getNearbyEntities(this.getPlayer().getLocation(), 100, 100, 100, entity -> entity instanceof LivingEntity && entity != this.getPlayer())) {
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntities.add(livingEntity);
        }

        new BukkitRunnable() {
            int amountOfTicks = 0;

            @Override
            public void run() {
                livingEntities.forEach(livingEntity -> {
                    Location location = livingEntity.getLocation();

                    for (int i = 0; i < 5; i++) {
                        double x = location.getX() + StaticVariables.random.nextGaussian() * 3;
                        double y = location.getY() + StaticVariables.random.nextGaussian() * 3;
                        double z = location.getZ() + StaticVariables.random.nextGaussian() * 3;
                        world.spawnParticle(Particle.SPIT, x, y, z, 0, 0, 1, 0);
                    }

                    livingEntity.setVelocity(velocity);
                });

                amountOfTicks++;
                if (amountOfTicks > 600) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }
}
