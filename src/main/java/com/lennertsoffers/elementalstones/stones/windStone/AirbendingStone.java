package com.lennertsoffers.elementalstones.stones.windStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AirbendingStone {

    // PASSIVE


    // MOVE 4
    // Air cutter
    // ->


    // MOVE 5
    // Tracking blade
    // -> Shoots a little blade of air in the looking direction tracking the closest entity
    public static void move5(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        Location playerLocation = player.getLocation();
        Location bladeStartLocation = playerLocation.clone();
        Vector bladeStartDirection = bladeStartLocation.getDirection();
        bladeStartLocation.add(bladeStartDirection).add(0, 1, 0);

        LivingEntity target = null;
        double closest = -1;
        if (!world.getNearbyEntities(playerLocation, 50, 5, 50).isEmpty()) {
            for (Entity entity : world.getNearbyEntities(playerLocation, 50, 5, 50)) {
                if (entity != null) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        if (livingEntity != player) {
                            double distance = MathTools.calculate3dDistance(playerLocation, livingEntity.getLocation());
                            if (closest == -1 || distance < closest ) {
                                closest = distance;
                                target = livingEntity;
                            }
                        }
                    }
                }
            }
        }
        if (target != null) {
            target.setGlowing(true);
            LivingEntity finalTarget = target;
            Location currentLocation = bladeStartLocation.clone();

            new BukkitRunnable() {
                int amountOfTicks = 0;
                @Override
                public void run() {
                    Location targetLocation = finalTarget.getLocation().add(0, 1, 0);

                    Vector pathDirection = MathTools.getDirectionNormVector3d(currentLocation, targetLocation);

                    for (int i = 0; i < 10; i++) {
                        double particleLocationX = currentLocation.getX() + StaticVariables.random.nextGaussian() / 10;
                        double particleLocationZ = currentLocation.getZ() + StaticVariables.random.nextGaussian() / 10;
                        world.spawnParticle(Particle.END_ROD, particleLocationX, currentLocation.getY(), particleLocationZ, 0);
                    }

                    currentLocation.add(pathDirection.multiply(0.2));

                    if (amountOfTicks > 2000 || finalTarget.isDead()) {
                        this.cancel();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        }
    }


    // MOVE 6
    // Breathtaking
    // -> Sucks


    // MOVE 7
    // Tornado
    // -> Shoots a tornado launching entities up


    // MOVE 8
    // Levitate
    // -> Gives player the ability to select an entity and move it in the air for 10 seconds
}
