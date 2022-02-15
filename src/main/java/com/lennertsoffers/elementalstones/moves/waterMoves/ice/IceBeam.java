package com.lennertsoffers.elementalstones.moves.waterMoves.ice;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class IceBeam extends Move {

    public IceBeam(ActivePlayer activePlayer) {
        super(activePlayer, "Ice Beam", "water_stone", "ice_stone", 8);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();
        Location playerLocation = player.getLocation();
        World world = player.getWorld();
        Vector direction = playerLocation.getDirection();
        Vector perpendicularDirection = direction.clone().rotateAroundY(90);

        new BukkitRunnable() {
            double angle = 1;
            double angle2 = 180;
            Location particleLocation0;
            Location particleLocation1;
            Location particleLocation2;
            int amountOfTicks;

            @Override
            public void run() {
                angle += 0.3;
                angle2 += 0.3;
                particleLocation0 = playerLocation.clone().add(direction.clone().multiply(2)).add(0, 1.5, 0).add(direction.clone().multiply(0.5 * amountOfTicks));
                particleLocation1 = playerLocation.clone().add(direction.clone().multiply(2)).add(0, 1.5, 0).add(perpendicularDirection.clone().rotateAroundAxis(direction, angle).multiply(0.5)).add(direction.clone().multiply(0.5 * amountOfTicks));
                particleLocation2 = playerLocation.clone().add(direction.clone().multiply(2)).add(0, 1.5, 0).add(perpendicularDirection.clone().rotateAroundAxis(direction, angle2).multiply(0.5)).add(direction.clone().multiply(0.5 * amountOfTicks));
                for (int i = 0; i < 5; i++) {
                    double x1 = particleLocation1.getX() + StaticVariables.random.nextGaussian() / 20;
                    double y1 = particleLocation1.getY() + StaticVariables.random.nextGaussian() / 20;
                    double z1 = particleLocation1.getZ() + StaticVariables.random.nextGaussian() / 20;
                    double x2 = particleLocation2.getX() + StaticVariables.random.nextGaussian() / 20;
                    double y2 = particleLocation2.getY() + StaticVariables.random.nextGaussian() / 20;
                    double z2 = particleLocation2.getZ() + StaticVariables.random.nextGaussian() / 20;
                    world.spawnParticle(Particle.REDSTONE, x1, y1, z1, 0, new Particle.DustOptions(Color.fromRGB(0, 165, 255), 2f));
                    world.spawnParticle(Particle.REDSTONE, x2, y2, z2, 0, new Particle.DustOptions(Color.WHITE, 2f));
                }

                if (!world.getNearbyEntities(particleLocation0, 0.5, 0.5, 0.5).isEmpty()) {
                    for (Entity entity : world.getNearbyEntities(particleLocation0, 0.5, 0.5, 0.5)) {
                        if (entity != null) {
                            if (entity instanceof LivingEntity) {
                                LivingEntity livingEntity = (LivingEntity) entity;
                                if (livingEntity != player) {
                                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 3, false, true, false));
                                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 3, false, true, false));
                                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 3, false, true, false));
                                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 3, false, true, false));
                                    livingEntity.setFreezeTicks(livingEntity.getMaxFreezeTicks());
                                    livingEntity.damage(5);
                                    this.cancel();
                                    new BukkitRunnable() {
                                        int amountOfTicks = 0;

                                        @Override
                                        public void run() {
                                            Location targetLocation = livingEntity.getLocation();
                                            Location snowballTargetLocation = targetLocation.clone().add(0, 1.5, 0);
                                            if (amountOfTicks % 10 == 0) {
                                                Location snowBallLocation = MathTools.locationOnCircle(snowballTargetLocation, 6, Math.abs(livingEntity.getLocation().getYaw()), world);
                                                snowBallLocation.setY(snowBallLocation.getY() + 1);
                                                Vector velocity = new Vector(snowballTargetLocation.getX() - snowBallLocation.getX(), snowballTargetLocation.getY() - snowBallLocation.getY(), snowballTargetLocation.getZ() - snowBallLocation.getZ());
                                                Entity entity = world.spawnEntity(snowBallLocation, EntityType.SNOWBALL);
                                                entity.setVelocity(velocity.multiply(0.2));
                                            }
                                            livingEntity.setFreezeTicks(livingEntity.getMaxFreezeTicks());
                                            for (int i = 0; i < 50; i++) {
                                                ItemStack stack;
                                                if (StaticVariables.random.nextBoolean()) {
                                                    stack = new ItemStack(Material.ICE);
                                                } else {
                                                    stack = new ItemStack(Material.SNOW_BLOCK);
                                                }
                                                world.spawnParticle(Particle.ITEM_CRACK, targetLocation.clone().add(StaticVariables.random.nextGaussian() / 3, StaticVariables.random.nextGaussian() / 3, StaticVariables.random.nextGaussian() / 3), 0, 0, 0, 0, 0, stack);
                                            }
                                            if (amountOfTicks > 200 || livingEntity.isDead()) {
                                                this.cancel();
                                            }
                                            amountOfTicks++;
                                        }
                                    }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
                                }
                            }
                        }
                    }
                }
                if (amountOfTicks > 200) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

        this.setCooldown();
    }
}
