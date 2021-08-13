package com.lennertsoffers.elementalstones.stones.earthStone;

import com.lennertsoffers.elementalstones.ElementalStones;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

public class DefenseStone extends EarthStone {

    private static ItemStack addEnchantments(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
        itemMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 5, true);
        itemMeta.addEnchant(Enchantment.PROTECTION_FALL, 5, true);
        itemMeta.addEnchant(Enchantment.PROTECTION_FIRE, 5, true);
        itemMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 5, true);
        item.setItemMeta(itemMeta);
        return item;
    }

    // PASSIVE
    private static void defenseStonePassive() {
        StaticVariables.scheduler.scheduleSyncRepeatingTask(StaticVariables.plugin, () -> {
            for (Player player : StaticVariables.plugin.getServer().getOnlinePlayers()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 220, 1, true, true, true));
            }
        }, 0L, 200L);
    }

    // MOVE 4
    public static void move4(Player player) {

    }

    // MOVE 5
    public static void move5(Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggings = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        ItemStack betterHelmet = DefenseStone.addEnchantments(new ItemStack(Material.NETHERITE_HELMET));
        ItemStack betterChestplate = DefenseStone.addEnchantments(new ItemStack(Material.NETHERITE_CHESTPLATE));
        ItemStack betterLeggings = DefenseStone.addEnchantments(new ItemStack(Material.NETHERITE_LEGGINGS));
        ItemStack betterBoots = DefenseStone.addEnchantments(new ItemStack(Material.NETHERITE_BOOTS));

        player.getInventory().setHelmet(betterHelmet);
        player.getInventory().setChestplate(betterChestplate);
        player.getInventory().setLeggings(betterLeggings);
        player.getInventory().setBoots(betterBoots);

    }

    // MOVE 6
    public static void move6(Player player) {

    }

    // MOVE 7
    public static void move7(Player player) {
        Location location = player.getLocation();
        List<Entity> entities = player.getNearbyEntities(3, 0, 3);
        int playerX = location.getBlockX() - 3;
        int playerY = location.getBlockY();
        int playerZ = location.getBlockZ() - 3;

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (!((i == 0 && j == 0) || (i == 0 && j == 6) || (i == 6 && j == 0) || (i == 6 && j == 6))) {
                    ItemStack stack;
                    if (player.getWorld().getBlockAt(player.getLocation().add(0, -1, 0)).getType().equals(Material.AIR)) {
                        stack = new ItemStack(Material.STONE);
                    } else {
                        stack = new ItemStack(player.getWorld().getBlockAt(player.getLocation().add(0, -1, 0)).getType());
                    }
                    player.getWorld().spawnParticle(Particle.ITEM_CRACK, playerX + i, playerY + 0.5, playerZ + j, 50, 1, 0, 1, 0.1, stack);
                    for (Entity entity : entities) {
                        try {
                            LivingEntity livingEntity = (LivingEntity) entity;
                            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 2));
                        } finally {
                            entity.setVelocity(new Vector(0, 0.5, 0));
                        }
                    }
                }
            }
        }
    }

    // MOVE 8
    public static void move8(Player player) {
        Location location = player.getLocation();
        World world = player.getWorld();
        int playerX = location.getBlockX();
        int playerY = location.getBlockY();
        int playerZ = location.getBlockZ();
        for (Entity entity : player.getNearbyEntities(4, 4, 4)) {
            int entityX = entity.getLocation().getBlockX();
            int entityZ = entity.getLocation().getBlockZ();
            entity.setVelocity(new Vector((double) 1/(entityX - playerX), 0.4, (double) 1/(entityZ - playerZ)));
        }

            if (world.getBlockAt(location.add(0, -1, 0)).getType() != Material.AIR) {
            location.add(0, 1, 0);
            for (int i = 0; i < 3; i++) {
                world.getBlockAt(playerX + 2, playerY + i, playerZ - 1).setType(Material.STONE);
                world.getBlockAt(playerX + 2, playerY + i, playerZ).setType(Material.STONE);
                world.getBlockAt(playerX + 2, playerY + i, playerZ + 1).setType(Material.STONE);
                world.getBlockAt(playerX - 1, playerY + i, playerZ + 2).setType(Material.STONE);
                world.getBlockAt(playerX, playerY + i, playerZ + 2).setType(Material.STONE);
                world.getBlockAt(playerX + 1, playerY + i, playerZ + 2).setType(Material.STONE);
                world.getBlockAt(playerX - 2, playerY + i, playerZ - 1).setType(Material.STONE);
                world.getBlockAt(playerX - 2, playerY + i, playerZ).setType(Material.STONE);
                world.getBlockAt(playerX - 2, playerY + i, playerZ + 1).setType(Material.STONE);
                world.getBlockAt(playerX - 1, playerY + i, playerZ - 2).setType(Material.STONE);
                world.getBlockAt(playerX, playerY + i, playerZ - 2).setType(Material.STONE);
                world.getBlockAt(playerX + 1, playerY + i, playerZ - 2).setType(Material.STONE);

                world.getBlockAt(playerX + 1, playerY + i, playerZ + 1).setType(Material.AIR);
                world.getBlockAt(playerX + 1, playerY + i, playerZ).setType(Material.AIR);
                world.getBlockAt(playerX + 1, playerY + i, playerZ - 1).setType(Material.AIR);
                world.getBlockAt(playerX, playerY + i, playerZ + 1).setType(Material.AIR);
                world.getBlockAt(playerX, playerY + i, playerZ).setType(Material.AIR);
                world.getBlockAt(playerX, playerY + i, playerZ - 1).setType(Material.AIR);
                world.getBlockAt(playerX - 1, playerY + i, playerZ + 1).setType(Material.AIR);
                world.getBlockAt(playerX - 1, playerY + i, playerZ).setType(Material.AIR);
                world.getBlockAt(playerX - 1, playerY + i, playerZ - 1).setType(Material.AIR);
            }
            world.getBlockAt(playerX + 1, playerY + 3, playerZ + 1).setType(Material.STONE);
            world.getBlockAt(playerX + 1, playerY + 3, playerZ).setType(Material.STONE);
            world.getBlockAt(playerX + 1, playerY + 3, playerZ - 1).setType(Material.STONE);
            world.getBlockAt(playerX, playerY + 3, playerZ + 1).setType(Material.STONE);
            world.getBlockAt(playerX, playerY + 3, playerZ).setType(Material.STONE);
            world.getBlockAt(playerX, playerY + 3, playerZ - 1).setType(Material.STONE);
            world.getBlockAt(playerX - 1, playerY + 3, playerZ + 1).setType(Material.STONE);
            world.getBlockAt(playerX - 1, playerY + 3, playerZ).setType(Material.STONE);
            world.getBlockAt(playerX - 1, playerY + 3, playerZ - 1).setType(Material.STONE);
        }
    }
}
