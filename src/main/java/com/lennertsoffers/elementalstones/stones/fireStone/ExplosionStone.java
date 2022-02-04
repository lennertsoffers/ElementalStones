package com.lennertsoffers.elementalstones.stones.fireStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.RandomiserTools;
import com.lennertsoffers.elementalstones.stones.earthStone.EarthStone;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ExplosionStone extends FireStone {

    // MOVE 4
    // Smoke Screen
    // -> Following up to flying rock
    // -> Explodes the flying block into a big smoke which makes it impossible to see through
    public static Runnable move4(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            Location startLocation = player.getLocation();
            Vector direction = startLocation.getDirection().setY(0);
            Vector startVelocity = player.getVelocity();
            if (startVelocity.getY() < 0) {
                startVelocity.setY(0);
            }

            startLocation.add(direction).add(0, 1, 0).add(direction.clone().rotateAroundY(-90).multiply(2));

            new BukkitRunnable() {
                double i = 0;

                @Override
                public void run() {
                    HashMap<String, Double> result = MathTools.calculatePointOnThrowFunction(15, 1, startLocation.getYaw(), -startLocation.getPitch(), i, startVelocity);

                    int offset = 15;
                    double x = startLocation.getX() + result.get("x");
                    double y = startLocation.getY() + result.get("y");
                    double z = startLocation.getZ() + result.get("z");

                    for (int i = 0; i < 5; i++) {
                        double randomX = x + (StaticVariables.random.nextGaussian() / offset);
                        double randomY = y + (StaticVariables.random.nextGaussian() / offset);
                        double randomZ = z + (StaticVariables.random.nextGaussian() / offset);
                        world.spawnParticle(Particle.REDSTONE, randomX, randomY, randomZ, 0, new Particle.DustOptions(Color.GRAY, 2));
                    }

                    i += 0.05;
                    if (i > 4) {
                        this.cancel();
                    }

                    Location particleLocation = new Location(world, x, y, z);
                    if (CheckLocationTools.isSolidBlock(world.getBlockAt(particleLocation))) {
                        new BukkitRunnable() {
                            int loops = 0;

                            @Override
                            public void run() {
                                for (int i = 1; i < 400; i++) {
                                    double offsetBomb = 8;
                                    if (loops == 3) {
                                        offsetBomb = 1;
                                    } else if (loops == 5) {
                                        offsetBomb = 0.5;
                                    }
                                    double offsetX = particleLocation.getX() + StaticVariables.random.nextGaussian() / offsetBomb;
                                    double offsetY = particleLocation.getY() + Math.abs(StaticVariables.random.nextGaussian()) / offsetBomb + 0.3;
                                    double offsetZ = particleLocation.getZ() + StaticVariables.random.nextGaussian() / offsetBomb;
                                    world.spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, offsetX, offsetY, offsetZ, 0, StaticVariables.random.nextGaussian() / 40, Math.abs(StaticVariables.random.nextGaussian() / 40), StaticVariables.random.nextGaussian() / 40);
                                }

                                loops++;
                                if (loops > 20) {
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(StaticVariables.plugin, 0L, 20L);
                        this.cancel();
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }

    // MOVE 5
    // Better Gear
    // -> Swaps your current armor for the best possible armor in the game
    // -> After 60s, your normal gear will return
    public static Runnable move5(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            Location location = player.getLocation();
            Vector direction = location.getDirection().setY(0);

            if (player.isSneaking()) {
                Vector perpendicularDirection = direction.clone().rotateAroundY(Math.PI / 2);

                location.add(direction.clone().multiply(20));
                Location finalCenterLocation = location.clone();
                location.add(perpendicularDirection.clone().multiply(-10));

                new BukkitRunnable() {
                    int rows = 1;

                    @Override
                    public void run() {
                        for (int i = 1; i < 20; i++) {
                            Location fireworkLocation = location.clone().add(perpendicularDirection.clone().multiply(i)).add(direction.clone().multiply(rows));
                            fireworkLocation.setY(world.getHighestBlockYAt(location) + 1);

                            Firework firework = (Firework) world.spawnEntity(fireworkLocation, EntityType.FIREWORK);

                            FireworkMeta fireworkMeta = firework.getFireworkMeta();
                            fireworkMeta.setPower(1);

                            ArrayList<Color> colors = new ArrayList<>();
                            ArrayList<Color> fadeColors = new ArrayList<>();
                            for (int k = 0; k < StaticVariables.random.nextInt(3) + 1; k++) {
                                colors.add(RandomiserTools.randomColor());
                            }
                            for (int k = 0; k < StaticVariables.random.nextInt(3) + 1; k++) {
                                fadeColors.add(RandomiserTools.randomColor());
                            }

                            if (player.isSneaking()) {
                                fireworkMeta.addEffect(FireworkEffect.builder().with(RandomiserTools.randomEnum(FireworkEffect.Type.class)).withColor(colors).withFade(fadeColors).flicker(StaticVariables.random.nextBoolean()).trail(StaticVariables.random.nextBoolean()).build());
                            } else {
                                fireworkMeta.addEffect(FireworkEffect.builder().withColor(colors).withFade(fadeColors).flicker(StaticVariables.random.nextBoolean()).trail(StaticVariables.random.nextBoolean()).build());
                            }

                            firework.setFireworkMeta(fireworkMeta);
                        }

                        rows++;
                        if (rows > 10) {
                            this.cancel();
                            finalCenterLocation.setY(world.getHighestBlockYAt(finalCenterLocation) + 1);
                            Firework firework = (Firework) world.spawnEntity(finalCenterLocation, EntityType.FIREWORK);
                            FireworkMeta fireworkMeta = firework.getFireworkMeta();
                            fireworkMeta.setPower(1);
                            fireworkMeta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(Color.RED, Color.BLUE).withFade(Color.BLACK).flicker(false).trail(true).build());
                            firework.setFireworkMeta(fireworkMeta);
                        }
                    }
                }.runTaskTimer(StaticVariables.plugin, 0L, 50L);
            } else {
                location.add(0, 1, 0).add(direction.clone().multiply(2));
                Firework firework = (Firework) world.spawnEntity(location, EntityType.FIREWORK);
                firework.setShotAtAngle(true);
                FireworkMeta fireworkMeta = firework.getFireworkMeta();
                fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.BLACK).withFade(Color.GRAY).flicker(false).trail(true).build());
                fireworkMeta.setUnbreakable(true);
                fireworkMeta.setPower(127);
                firework.setFireworkMeta(fireworkMeta);


                new BukkitRunnable() {
                    @Override
                    public void run() {
                        firework.setTicksLived(1);
                        FireworkMeta fireworkMeta = firework.getFireworkMeta();
                        fireworkMeta.setPower(127);
                        firework.setFireworkMeta(fireworkMeta);
                        firework.teleport(player.getLocation().add(player.getLocation().getDirection().multiply(2)).add(0, 1, 0));
                    }
                }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            }
        };
    }

    // MOVE 6
    // Wall
    // -> Place a stone wall a few blocks before the player to block attacks
    public static Runnable move6(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = player.getLocation();
        };
    }

    // MOVE 7
    // Shockwave
    // -> Creates a shockwave around the player knocking up all entities and giving them the slowness effect
    public static Runnable move7(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = player.getLocation();
        };
    }

    // MOVE 8
    // Last Chance
    // -> When something should have killed the player, he gets a last chance by not getting the damage
    // -> All entities in close range are knocked back and the player is protected by a stone bunker
    public static Runnable move8(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = player.getLocation();
            World world = player.getWorld();
        };
    }
}

// TODO - 3 rockets instead of 1
