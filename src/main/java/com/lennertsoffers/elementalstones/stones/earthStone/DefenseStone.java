package com.lennertsoffers.elementalstones.stones.earthStone;

import com.lennertsoffers.elementalstones.ElementalStones;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class DefenseStone {

    private final ElementalStones plugin;

    public DefenseStone(ElementalStones plugin) {
        this.plugin = plugin;
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, this::defenseStonePassive, 0L, 200L);
    }

    // PASSIVE
    private void defenseStonePassive() {
        for (Player player : this.plugin.getServer().getOnlinePlayers()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 220, 1, true, true, true));
        }
    }

    // MOVE 8
    public static void defenseStoneMove8(Player player) {
        Location location = player.getLocation();
        World world = player.getWorld();
        int playerX = location.getBlockX();
        int playerY = location.getBlockY();
        int playerZ = location.getBlockZ();
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
