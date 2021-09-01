package com.lennertsoffers.elementalstones.stones.fireStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.Tools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class LavaStone {

    // PASSIVE
    public static void passive(ActivePlayer activePlayer, PlayerMoveEvent event) {
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
                int index = StaticVariables.random.nextInt(locationGroup.size() - 1);
                world.getBlockAt(locationGroup.get(index)).setType(Material.LAVA);
                locationGroup.remove(index);
                if (blocksRemoved >= 25) {
                    this.cancel();
                }
                blocksRemoved++;
            }
        });
    }
}
