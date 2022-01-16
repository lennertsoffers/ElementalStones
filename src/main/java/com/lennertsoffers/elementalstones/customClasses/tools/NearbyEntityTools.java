package com.lennertsoffers.elementalstones.customClasses.tools;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class NearbyEntityTools {

    public static void damageNearbyEntities(Player player, Location location, double amount, double x, double y, double z) {
        World world = location.getWorld();
        if (world != null) {
            Collection<Entity> nearbyEntities = world.getNearbyEntities(location, x, y, z, entity -> entity != player && entity instanceof LivingEntity);
            if (!nearbyEntities.isEmpty()) {
                for (LivingEntity livingEntity : nearbyEntities.stream().map(entity -> (LivingEntity) entity).collect(Collectors.toList())) {
                    livingEntity.damage(amount, player);
                }
            }
        }
    }

    public static void damageNearbyEntities(Player player, Location location, double amount, double x, double y, double z, Vector direction) {
        World world = location.getWorld();
        if (world != null) {
            Collection<Entity> nearbyEntities = world.getNearbyEntities(location, x, y, z, entity -> entity != player && entity instanceof LivingEntity);
            if (!nearbyEntities.isEmpty()) {
                for (LivingEntity livingEntity : nearbyEntities.stream().map(entity -> (LivingEntity) entity).collect(Collectors.toList())) {
                    livingEntity.damage(amount, player);
                    livingEntity.setVelocity(direction);
                }
            }
        }
    }

    public static void damageNearbyEntities(Player player, Location location, double amount, double x, double y, double z, List<PotionEffect> potionEffects) {
        World world = location.getWorld();
        if (world != null) {
            Collection<Entity> nearbyEntities = world.getNearbyEntities(location, x, y, z, entity -> entity != player && entity instanceof LivingEntity);
            if (!nearbyEntities.isEmpty()) {
                for (LivingEntity livingEntity : nearbyEntities.stream().map(entity -> (LivingEntity) entity).collect(Collectors.toList())) {
                    livingEntity.damage(amount, player);
                    for (PotionEffect potionEffect : potionEffects) {
                        livingEntity.addPotionEffect(potionEffect);
                    }
                }
            }
        }
    }

    public static void damageNearbyEntities(Player player, Location location, double amount, double x, double y, double z, Vector direction, List<PotionEffect> potionEffects) {
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
                }
            }
        }
    }
}
