package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AirBreath extends BukkitRunnable {

    private final LivingEntity owner;
    private final Player target;
    private final World world;
    private final Location orbLocation;

    private int amountOfTicks = 0;

    public AirBreath(LivingEntity owner, Player target) {
        this.owner = owner;
        this.target = target;
        this.world = owner.getWorld();
        this.orbLocation = owner.getLocation();
    }

    @Override
    public void run() {
        this.moveOrb();
        this.spawnOrbParticles();

        if (this.amountOfTicks > 20) {
            this.checkNearbyEntities();
        }

        this.amountOfTicks++;
        if (this.amountOfTicks > 600) {
            this.cancel();
            this.owner.damage(10, this.target);
        }
    }

    private void moveOrb() {
        Location targetLocation = this.target.getLocation();
        this.orbLocation.add(targetLocation.getX() - this.orbLocation.getX(), targetLocation.getY() - this.orbLocation.getY(), targetLocation.getZ() - this.orbLocation.getZ());
    }

    private void spawnOrbParticles() {
        double offset = 10;

        for (int i = 0; i < 10; i++) {
            double x = this.orbLocation.getX() + StaticVariables.random.nextGaussian() / offset;
            double y = this.orbLocation.getY() + StaticVariables.random.nextGaussian() / offset;
            double z = this.orbLocation.getZ() + StaticVariables.random.nextGaussian() / offset;

            this.world.spawnParticle(Particle.CLOUD, x, y, z, 0);
        }
    }

    private void checkNearbyEntities() {
        world.getNearbyEntities(this.orbLocation, 1, 1, 1, entity -> entity instanceof LivingEntity).forEach(entity -> {
            LivingEntity player = (LivingEntity) entity;

            if (player != this.owner) {
                player.setHealth(player.getHealth() + 10);
                this.owner.damage(10, this.target);
            }
            this.cancel();
        });
    }
}
