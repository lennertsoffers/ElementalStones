package com.lennertsoffers.elementalstones.stones.earthStone;

import com.lennertsoffers.elementalstones.ElementalStones;
import com.lennertsoffers.elementalstones.customClasses.Tools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.Objects;

public class EarthStone {

    protected final ElementalStones plugin;
    protected final BukkitScheduler scheduler;

    public EarthStone(ElementalStones plugin) {
        this.plugin = plugin;
        this.scheduler = plugin.getServer().getScheduler();
    }

    private static void placePillar(Location location) {
        for (int i = 0; i < 3; i++) {
            location.setY(location.getY() + 1.0);
            location.getBlock().setType(Material.STONE);
        }
    }

    // MOVE 1
    public static void move1(Player player, PlayerInteractEvent event, ElementalStones plugin) {
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            Location location = Objects.requireNonNull(event.getClickedBlock()).getLocation();
            Server server = player.getServer();
            double playerX = player.getLocation().getX();
            double playerZ = player.getLocation().getZ();
            double blockX = location.getX();
            double blockZ = location.getZ();

            if (Tools.checkPlayerCollision(playerX, blockX) && Tools.checkPlayerCollision(playerZ, blockZ)) {
                player.setVelocity(new Vector(0, 1 ,0));
                server.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    placePillar(location);
                }, 3L);
            } else {
                placePillar(location);
            }
        }
    }

    // MOVE 2
    public static void move2(Player player) {

    }

    // MOVE 3
    public static void move3(Player player) {

    }
}
