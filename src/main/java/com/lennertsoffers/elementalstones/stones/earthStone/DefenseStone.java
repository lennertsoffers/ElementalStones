package com.lennertsoffers.elementalstones.stones.earthStone;

import com.lennertsoffers.elementalstones.ElementalStones;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Objects;

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
        int playerX = location.getBlockX() - 3;
        int playerZ = location.getBlockZ() - 3;

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (!((i == 0 && j == 0))) {
                    player.getWorld().spawnParticle(Particle.ITEM_CRACK, playerX + i, location.getBlockY() + 1, playerZ + j, 100, 1, -0.5, 1, 0.1, new ItemStack(player.getWorld().getBlockAt(player.getLocation().add(0, -1, 0)).getType()));
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
        for (Entity entity : player.getNearbyEntities(4, 4, 3)) {
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
