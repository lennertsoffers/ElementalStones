package com.lennertsoffers.elementalstones.customClasses.models;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.function.Consumer;

public enum Effect {
    SKELETON_TRAP(200, location -> {
        World world = location.getWorld();

        if (world != null) {
            ItemStack helmet = new ItemStack(Material.NETHERITE_HELMET, 1);

            for (int i = 0; i < 10; i++) {
                double locationX = location.getX() + StaticVariables.random.nextGaussian() * 5;
                double locationZ = location.getZ() + StaticVariables.random.nextGaussian() * 5;

                Location spawnLocation = CheckLocationTools.getClosestAirBlockLocation(new Location(world, locationX, location.getY(), locationZ));
                if (spawnLocation != null) {
                    Skeleton skeleton = (Skeleton) world.spawnEntity(spawnLocation, EntityType.SKELETON);
                    EntityEquipment entityEquipment = skeleton.getEquipment();

                    if (entityEquipment != null) {
                        entityEquipment.setHelmet(helmet);
                        entityEquipment.setHelmetDropChance(0);
                    }
                }
            }
        }
    }),
    ANGRY_BEES(200, location -> {
        World world = location.getWorld();

        if (world != null) {

            LivingEntity target = NearbyEntityTools.getClosestEntity(location, 20, 20, 20, entity -> entity instanceof LivingEntity);

            for (int i = 0; i < 30; i++) {
                double locationX = location.getX() + StaticVariables.random.nextGaussian() * 3;
                double locationZ = location.getZ() + StaticVariables.random.nextGaussian() * 3;

                Location spawnLocation = CheckLocationTools.getClosestAirBlockLocation(new Location(world, locationX, location.getY(), locationZ));
                if (spawnLocation != null) {
                    Bee bee = (Bee) world.spawnEntity(spawnLocation, EntityType.BEE);
                    bee.setAnger(12000);

                    if (target != null) {
                        bee.setTarget(target);
                    }
                }
            }
        }
    }),
    SHULKER_BULLETS(100, location -> {
        World world = location.getWorld();

        if (world != null) {

            LivingEntity target = NearbyEntityTools.getClosestEntity(location, 20, 20, 20, entity -> entity instanceof LivingEntity);

            for (int i = 0; i < 20; i++) {
                double locationX = location.getX() + StaticVariables.random.nextGaussian() * 10;
                double locationZ = location.getZ() + StaticVariables.random.nextGaussian() * 10;

                Location spawnLocation = CheckLocationTools.getClosestAirBlockLocation(new Location(world, locationX, location.getY(), locationZ));
                if (spawnLocation != null) {
                    ShulkerBullet shulkerBullet = (ShulkerBullet) world.spawnEntity(spawnLocation, EntityType.SHULKER_BULLET);

                    if (target != null) {
                        shulkerBullet.setTarget(target);
                    }
                }
            }
        }
    }),
    TREE(100, location -> {
        World world = location.getWorld();

        if (world != null) {
            world.generateTree(world.getHighestBlockAt(location).getLocation().add(0, 1, 0), StaticVariables.random, TreeType.AZALEA);
        }
    }),
    ANCIENT_DEBRIS(100, location -> {
        World world = location.getWorld();

        if (world != null) {
            location.getBlock().setType(Material.ANCIENT_DEBRIS);
        }
    }),
    DAY_NIGHT(100, location -> {
        World world = location.getWorld();

        if (world != null) {
            long time = StaticVariables.random.nextInt(18000) + 6000 + world.getTime();

            if (time > 24000) {
                time -= 24000;
            }

            world.setTime(time);
        }
    }),
    WEATHER(100, location -> {
        World world = location.getWorld();

        if (world != null) {
            if (StaticVariables.random.nextBoolean()) {
                world.setStorm(true);
            } else {
                world.setThundering(true);
                world.setThunderDuration(12000);
            }
        }
    }),
    RANDOM_TELEPORT(100, location -> {
        World world = location.getWorld();

        if (world != null) {
            world.getNearbyEntities(location, 3, 3, 3, entity -> entity instanceof LivingEntity).forEach(entity -> {
                LivingEntity livingEntity = (LivingEntity) entity;

                int newX = location.getBlockX() + StaticVariables.random.nextInt(4000) - 2000;
                int newZ = location.getBlockZ() + StaticVariables.random.nextInt(4000) - 2000;
                livingEntity.teleport(world.getHighestBlockAt(newX, newZ).getLocation().add(0, 1, 0));
            });
        }
    }),
    XP(100, location -> {
        World world = location.getWorld();

        if (world != null) {
            for (int i = 0; i < 20; i++) {
                ThrownExpBottle thrownExpBottle = (ThrownExpBottle) world.spawnEntity(location, EntityType.THROWN_EXP_BOTTLE);
                thrownExpBottle.setVelocity(new Vector(StaticVariables.random.nextGaussian() / 10, StaticVariables.random.nextDouble() + 0.5, StaticVariables.random.nextGaussian() / 10));
            }
        }
    }),
    CHARGED_CREEPER(100, location -> {
        World world = location.getWorld();

        if (world != null) {
            Location spawnLocation = CheckLocationTools.getClosestAirBlockLocation(location);
            if (spawnLocation != null) {
                Creeper creeper = (Creeper) world.spawnEntity(spawnLocation, EntityType.CREEPER);
                creeper.setPowered(true);
            }
        }
    }),
    FISH(100, location -> {
        World world = location.getWorld();

        if (world != null) {

            new BukkitRunnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        double locationX = location.getX() + StaticVariables.random.nextGaussian() * 10;
                        double locationZ = location.getZ() + StaticVariables.random.nextGaussian() * 10;

                        Location spawnLocation = CheckLocationTools.getClosestAirBlockLocation(new Location(world, locationX, location.getY(), locationZ));
                        if (spawnLocation != null) {
                            int random = StaticVariables.random.nextInt(3);

                            if (random == 0) {
                                world.spawnEntity(spawnLocation, EntityType.COD);
                            } else if (random == 1) {
                                world.spawnEntity(spawnLocation, EntityType.SALMON);
                            } else {
                                world.spawnEntity(spawnLocation, EntityType.TROPICAL_FISH);
                            }
                        }
                    }
                }
            }.runTaskLater(StaticVariables.plugin, 20L);
        }
    }),
    POTION(100, location -> {
        World world = location.getWorld();

        if (world != null) {
            ItemStack itemStack = new ItemStack(Material.SPLASH_POTION);
            PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();

            if (potionMeta != null) {
                PotionEffect potionEffect;
                
                int random = StaticVariables.random.nextInt(32);
                if (random == 0) {
                    potionEffect = new PotionEffect(PotionEffectType.SPEED, 400, 3, true, true, true);
                } else if (random == 1) {
                    potionEffect = new PotionEffect(PotionEffectType.ABSORPTION, 400, 3, true, true, true);
                } else if (random == 2) {
                    potionEffect = new PotionEffect(PotionEffectType.BAD_OMEN, 400, 1, true, true, true);
                } else if (random == 3) {
                    potionEffect = new PotionEffect(PotionEffectType.BLINDNESS, 400, 3, true, true, true);
                } else if (random == 4) {
                    potionEffect = new PotionEffect(PotionEffectType.CONDUIT_POWER, 400, 3, true, true, true);
                } else if (random == 5) {
                    potionEffect = new PotionEffect(PotionEffectType.CONFUSION, 1200, 3, true, true, true);
                } else if (random == 6) {
                    potionEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, 3, true, true, true);
                } else if (random == 7) {
                    potionEffect = new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 400, 3, true, true, true);
                } else if (random == 8) {
                    potionEffect = new PotionEffect(PotionEffectType.FAST_DIGGING, 400, 3, true, true, true);
                } else if (random == 9) {
                    potionEffect = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 400, 3, true, true, true);
                } else if (random == 10) {
                    potionEffect = new PotionEffect(PotionEffectType.GLOWING, 400, 3, true, true, true);
                } else if (random == 11) {
                    potionEffect = new PotionEffect(PotionEffectType.HARM, 400, 3, true, true, true);
                } else if (random == 12) {
                    potionEffect = new PotionEffect(PotionEffectType.HEAL, 400, 3, true, true, true);
                } else if (random == 13) {
                    potionEffect = new PotionEffect(PotionEffectType.HEALTH_BOOST, 400, 3, true, true, true);
                } else if (random == 14) {
                    potionEffect = new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 400, 3, true, true, true);
                } else if (random == 15) {
                    potionEffect = new PotionEffect(PotionEffectType.HUNGER, 400, 3, true, true, true);
                } else if (random == 16) {
                    potionEffect = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 400, 3, true, true, true);
                } else if (random == 17) {
                    potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, 400, 3, true, true, true);
                } else if (random == 18) {
                    potionEffect = new PotionEffect(PotionEffectType.JUMP, 400, 3, true, true, true);
                } else if (random == 19) {
                    potionEffect = new PotionEffect(PotionEffectType.LEVITATION, 400, 3, true, true, true);
                } else if (random == 20) {
                    potionEffect = new PotionEffect(PotionEffectType.LUCK, 400, 3, true, true, true);
                } else if (random == 21) {
                    potionEffect = new PotionEffect(PotionEffectType.NIGHT_VISION, 400, 3, true, true, true);
                } else if (random == 22) {
                    potionEffect = new PotionEffect(PotionEffectType.POISON , 400, 3, true, true, true);
                } else if (random == 23) {
                    potionEffect = new PotionEffect(PotionEffectType.REGENERATION, 400, 3, true, true, true);
                } else if (random == 24) {
                    potionEffect = new PotionEffect(PotionEffectType.SLOW_DIGGING, 400, 3, true, true, true);
                } else if (random == 25) {
                    potionEffect = new PotionEffect(PotionEffectType.SLOW, 400, 3, true, true, true);
                } else if (random == 26) {
                    potionEffect = new PotionEffect(PotionEffectType.SLOW_FALLING, 400, 3, true, true, true);
                } else if (random == 27) {
                    potionEffect = new PotionEffect(PotionEffectType.UNLUCK, 400, 3, true, true, true);
                } else if (random == 28) {
                    potionEffect = new PotionEffect(PotionEffectType.WATER_BREATHING, 400, 3, true, true, true);
                } else if (random == 29) {
                    potionEffect = new PotionEffect(PotionEffectType.WEAKNESS, 400, 3, true, true, true);
                } else if (random == 30) {
                    potionEffect = new PotionEffect(PotionEffectType.WITHER, 400, 3, true, true, true);
                } else {
                    potionEffect = new PotionEffect(PotionEffectType.SATURATION, 400, 3, true, true, true);
                }

                potionMeta.addCustomEffect(potionEffect, true);
                itemStack.setItemMeta(potionMeta);

                ThrownPotion thrownPotion = (ThrownPotion) world.spawnEntity(location.add(0, 1, 0), EntityType.SPLASH_POTION);
                thrownPotion.setItem(itemStack);
                thrownPotion.setVelocity(new Vector(0, 1, 0));
            }
        }
    }),
    WITHER(10, location -> {
        World world = location.getWorld();

        if (world != null) {
            world.spawnEntity(location, EntityType.WITHER);
        }
    }),
    DISC_11(1, location -> {
        World world = location.getWorld();

        if (world != null) {
            world.playSound(location, Sound.MUSIC_DISC_11, SoundCategory.MUSIC, 100, 100);
        }
    }),
    END_MUSIC(1, location -> {
        World world = location.getWorld();

        if (world != null) {
            world.playSound(location, Sound.MUSIC_END, SoundCategory.MUSIC, 100, 100);
        }
    });

    private final int chance;
    private final Consumer<Location> effect;

    Effect(int chance, Consumer<Location> effect) {
        this.chance = chance;
        this.effect = effect;
    }

    public int getChance() {
        return this.chance;
    }
    
    public void playEffect(Location location) {
        this.effect.accept(location);
    }
}
