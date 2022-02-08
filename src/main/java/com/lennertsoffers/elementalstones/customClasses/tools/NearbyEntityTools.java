package com.lennertsoffers.elementalstones.customClasses.tools;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Consumer;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NearbyEntityTools {

    public static boolean damageNearbyEntities(Player player, Location location, double amount, double x, double y, double z) {
        boolean collision = false;
        World world = location.getWorld();

        if (world != null) {
            Collection<Entity> nearbyEntities = world.getNearbyEntities(location, x, y, z, entity -> entity != player && entity instanceof LivingEntity);
            if (!nearbyEntities.isEmpty()) {
                for (LivingEntity livingEntity : nearbyEntities.stream().map(entity -> (LivingEntity) entity).collect(Collectors.toList())) {
                    livingEntity.damage(amount, player);

                    collision = true;
                }
            }
        }

        return collision;
    }

    public static boolean damageNearbyEntities(Player player, Location location, double amount, double x, double y, double z, Vector direction) {
        boolean collision = false;
        World world = location.getWorld();

        if (world != null) {
            Collection<Entity> nearbyEntities = world.getNearbyEntities(location, x, y, z, entity -> entity != player && entity instanceof LivingEntity);
            if (!nearbyEntities.isEmpty()) {
                for (LivingEntity livingEntity : nearbyEntities.stream().map(entity -> (LivingEntity) entity).collect(Collectors.toList())) {
                    livingEntity.damage(amount, player);
                    livingEntity.setVelocity(direction);

                    collision = true;
                }
            }
        }

        return collision;
    }

    public static boolean damageNearbyEntities(Player player, Location location, double amount, double x, double y, double z, List<PotionEffect> potionEffects) {
        boolean collision = false;
        World world = location.getWorld();

        if (world != null) {
            Collection<Entity> nearbyEntities = world.getNearbyEntities(location, x, y, z, entity -> entity != player && entity instanceof LivingEntity);
            if (!nearbyEntities.isEmpty()) {
                for (LivingEntity livingEntity : nearbyEntities.stream().map(entity -> (LivingEntity) entity).collect(Collectors.toList())) {
                    livingEntity.damage(amount, player);
                    for (PotionEffect potionEffect : potionEffects) {
                        livingEntity.addPotionEffect(potionEffect);
                    }

                    collision = true;
                }
            }
        }

        return collision;
    }

    public static boolean damageNearbyEntities(Player player, Location location, double amount, double x, double y, double z, Vector direction, List<PotionEffect> potionEffects) {
        boolean collision = false;
        World world = location.getWorld();
        if (world != null) {
            Collection<Entity> nearbyEntities = world.getNearbyEntities(location, x, y, z, entity -> entity != player && entity instanceof LivingEntity);
            if (!nearbyEntities.isEmpty()) {
                for (LivingEntity livingEntity : nearbyEntities.stream().map(entity -> (LivingEntity) entity).collect(Collectors.toList())) {
                    livingEntity.damage(amount, player);
                    livingEntity.setVelocity(direction);
                    for (PotionEffect potionEffect : potionEffects) {
                        livingEntity.addPotionEffect(potionEffect);
                    }

                    collision = true;
                }
            }
        }

        return collision;
    }

    public static boolean damageNearbyEntities(Player player, Location location, double amount, double x, double y, double z, Consumer<LivingEntity> consumer) {
        boolean collision = false;
        World world = location.getWorld();
        if (world != null) {
            Collection<Entity> nearbyEntities = world.getNearbyEntities(location, x, y, z, entity -> entity != player && entity instanceof LivingEntity);
            if (!nearbyEntities.isEmpty()) {
                for (LivingEntity livingEntity : nearbyEntities.stream().map(entity -> (LivingEntity) entity).collect(Collectors.toList())) {
                    livingEntity.damage(amount, player);

                    consumer.accept(livingEntity);

                    collision = true;
                }
            }
        }

        return collision;
    }

    public static boolean damageNearbyEntities(Player player, Location location, double amount, double x, double y, double z, Vector direction, List<PotionEffect> potionEffects, Consumer<LivingEntity> consumer) {
        boolean collision = false;
        World world = location.getWorld();
        if (world != null) {
            Collection<Entity> nearbyEntities = world.getNearbyEntities(location, x, y, z, entity -> entity != player && entity instanceof LivingEntity);
            if (!nearbyEntities.isEmpty()) {
                for (LivingEntity livingEntity : nearbyEntities.stream().map(entity -> (LivingEntity) entity).collect(Collectors.toList())) {
                    livingEntity.damage(amount, player);
                    livingEntity.setVelocity(direction);

                    for (PotionEffect potionEffect : potionEffects) {
                        livingEntity.addPotionEffect(potionEffect);
                    }

                    consumer.accept(livingEntity);

                    collision = true;
                }
            }
        }

        return collision;
    }

    public static boolean damageNearbyEntities(Player player, Location location, double amount, double x, double y, double z, Vector direction, Consumer<LivingEntity> consumer) {
        boolean collision = false;
        World world = location.getWorld();
        if (world != null) {
            Collection<Entity> nearbyEntities = world.getNearbyEntities(location, x, y, z, entity -> entity != player && entity instanceof LivingEntity);
            if (!nearbyEntities.isEmpty()) {
                for (LivingEntity livingEntity : nearbyEntities.stream().map(entity -> (LivingEntity) entity).collect(Collectors.toList())) {
                    livingEntity.damage(amount, player);
                    livingEntity.setVelocity(direction);

                    consumer.accept(livingEntity);

                    collision = true;
                }
            }
        }

        return collision;
    }

    public static LivingEntity getClosestEntity(Location location, int offsetX, int offsetY, int offsetZ, Predicate<Entity> predicate) {
        World world = location.getWorld();

        if (world != null) {
            LivingEntity target = null;
            List<LivingEntity> nearbyEntities = world.getNearbyEntities(location, offsetX, offsetY, offsetZ, predicate).stream().map(entity -> (LivingEntity) entity).collect(Collectors.toList());

            if (!nearbyEntities.isEmpty()) {
                for (LivingEntity livingEntity : nearbyEntities){
                    if (target != null) {
                        if (MathTools.calculate3dDistance(location, livingEntity.getLocation()) < MathTools.calculate3dDistance(location, target.getLocation())) {
                            target = livingEntity;
                        }
                    } else {
                        target = livingEntity;
                    }
                }

                return target;
            }
        }

        return null;
    }
}
