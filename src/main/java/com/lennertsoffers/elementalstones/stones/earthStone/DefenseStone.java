package com.lennertsoffers.elementalstones.stones.earthStone;

import com.lennertsoffers.elementalstones.ElementalStones;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

public class DefenseStone extends EarthStone {

    public DefenseStone(ElementalStones plugin) {
        super(plugin);
        super.scheduler.scheduleSyncRepeatingTask(plugin, this::defenseStonePassive, 0L, 200L);
    }

    // PASSIVE
    private void defenseStonePassive() {
        for (Player player : this.plugin.getServer().getOnlinePlayers()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 220, 1, true, true, true));
        }
    }

    // MOVE 4
    public static void move4(Player player) {

    }

    // MOVE 5
    public static void move5(Player player) {

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
