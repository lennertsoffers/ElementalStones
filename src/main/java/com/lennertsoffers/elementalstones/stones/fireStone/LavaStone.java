package com.lennertsoffers.elementalstones.stones.fireStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.Tools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class LavaStone {

    // PASSIVE
    public static void passive(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        Location location = player.getLocation().add(0, -1, 0);
        ArrayList<Location> locationGroup = new ArrayList<>();
        if (Tools.lavaAroundPlayer(location)) {
            location.add(2, 0, 2);
            Location startLocation = location.clone();
            for (int i = 1; i <= 25; i++) {
                locationGroup.add(location.clone());
                if (
                        !(startLocation.getX() == location.getX() && startLocation.getZ() == location.getZ()) &&
                                !(startLocation.getX() == location.getX() && startLocation.getZ() - 4 == location.getZ()) &&
                                !(startLocation.getX() - 4 == location.getX() && startLocation.getZ() == location.getZ()) &&
                                !(startLocation.getX() - 4 == location.getX() && startLocation.getZ() - 4 == location.getZ())
                ) {
                    world.getBlockAt(location).setType(Material.BASALT);
                }
                location.add(-1, 0, 0);
                if (i % 5 == 0) {
                    location.add(5, 0, -1);
                }
            }
        }
        activePlayer.setRemoveBasald(new BukkitRunnable() {
            int blocksRemoved = 1;
            @Override
            public void run() {
                if (locationGroup.size() >= 1) {
                    int index = StaticVariables.random.nextInt(Math.abs(locationGroup.size()));
                    world.getBlockAt(locationGroup.get(index)).setType(Material.LAVA);
                    locationGroup.remove(index);
                }
                if (blocksRemoved >= 25) {
                    this.cancel();
                }
                blocksRemoved++;
            }
        });
    }

    // MOVE 4
    // Lava Cross
    // -> Spawns lava cross along the axes
    public static void move4(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        Location location = player.getLocation();
        new BukkitRunnable() {
            int length = 0;
            final Location locationPosX = location.clone().add(1, 0, 0);
            final Location locationNegX = location.clone().add(-1, 0, 0);
            final Location locationPosZ = location.clone().add(0, 0, 1);
            final Location locationNegZ = location.clone().add(0, 0, -1);
            @Override
            public void run() {
                world.getBlockAt(locationPosX).setType(Material.LAVA);
                world.getBlockAt(locationNegX).setType(Material.LAVA);
                world.getBlockAt(locationPosZ).setType(Material.LAVA);
                world.getBlockAt(locationNegZ).setType(Material.LAVA);
                locationPosX.add(1, 0, 0);
                locationNegX.add(-1, 0, 0);
                locationPosZ.add(0, 0, 1);
                locationNegZ.add(0, 0, -1);
                if (length > 10) {
                    this.cancel();
                }
                length++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 5L);
    }

    // MOVE 5


    // MOVE 6


    // MOVE 7


    // MOVE 8
}
