package com.lennertsoffers.elementalstones.stones.earthStone;

import com.lennertsoffers.elementalstones.ElementalStones;
import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.Objects;

public class EarthStone {

    private static void placePillar(Location location) {
        for (int i = 0; i < 3; i++) {
            location.setY(location.getY() + 1.0);
            location.getBlock().setType(Material.STONE);
        }
    }

    // MOVE 1
    // Stone Pillar
    // -> Creates a pillar on the targeted location
    // -> If an entity collides with the pillar it flies up
    // -> The player will not get fall damage when he lands
    public static Runnable move1(Player player, PlayerInteractEvent event, ElementalStones plugin) {
        return () -> {
            if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                Location location = Objects.requireNonNull(event.getClickedBlock()).getLocation();
                Server server = player.getServer();
                double playerX = player.getLocation().getX();
                double playerZ = player.getLocation().getZ();
                double blockX = location.getX();
                double blockZ = location.getZ();

                if (CheckLocationTools.checkPlayerCollision(playerX, blockX) && CheckLocationTools.checkPlayerCollision(playerZ, blockZ)) {
                    player.setVelocity(new Vector(0, 1, 0));
                    server.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        placePillar(location);
                    }, 3L);
                } else {
                    placePillar(location);
                }
            }
        };
    }

    // MOVE 2
    // Flying Rock
    // -> The targeted block will fly up a bit
    // -> Primer for moves in the upgraded versions of the stone
    public static Runnable move2(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            Block targetBlock = Objects.requireNonNull(player.getTargetBlockExact(100));
            Location targetBlockLocation = targetBlock.getLocation();
            if (CheckLocationTools.locationAroundClear(targetBlockLocation.clone(), world)) {
                FallingBlock fallingBlock = world.spawnFallingBlock(targetBlock.getLocation(), targetBlock.getBlockData());
                world.getBlockAt(targetBlock.getLocation()).setType(Material.AIR);
                fallingBlock.setVelocity(new Vector(0, 0.7, 0));
                fallingBlock.setHurtEntities(false);
                activePlayer.setFallingBlock(fallingBlock);
            }
        };
    }

    // MOVE 3
    // Bunker
    // -> Hides the player in a bunker under the ground
    public static Runnable move3(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            Location location = player.getLocation();
            for (int i = 0; i < 3; i++) {
                world.getBlockAt(location).setType(Material.AIR);
                world.getBlockAt(location.add(1, -1, 0)).setType(Material.AIR);
                world.getBlockAt(location.add(0, 0, 1)).setType(Material.AIR);
                world.getBlockAt(location.add(-1, 0, 0)).setType(Material.AIR);
                world.getBlockAt(location.add(-1, 0, 0)).setType(Material.AIR);
                world.getBlockAt(location.add(0, 0, -1)).setType(Material.AIR);
                world.getBlockAt(location.add(0, 0, -1)).setType(Material.AIR);
                world.getBlockAt(location.add(1, 0, 0)).setType(Material.AIR);
                world.getBlockAt(location.add(1, 0, 0)).setType(Material.AIR);
                world.getBlockAt(location.add(-1, 0, 1)).setType(Material.AIR);
            }
            world.getBlockAt(location).setType(Material.AIR);
            player.setVelocity(new Vector(0, -10, 0));
            StaticVariables.scheduler.scheduleSyncDelayedTask(StaticVariables.plugin, () -> {
                world.getBlockAt(location.add(0, 2, 0)).setType(Material.SAND);
                world.getBlockAt(location.add(1, 0, 0)).setType(Material.SAND);
                world.getBlockAt(location.add(0, 0, 1)).setType(Material.SAND);
                world.getBlockAt(location.add(-1, 0, 0)).setType(Material.SAND);
                world.getBlockAt(location.add(-1, 0, 0)).setType(Material.SAND);
                world.getBlockAt(location.add(0, 0, -1)).setType(Material.SAND);
                world.getBlockAt(location.add(0, 0, -1)).setType(Material.SAND);
                world.getBlockAt(location.add(1, 0, 0)).setType(Material.SAND);
                world.getBlockAt(location.add(1, 0, 0)).setType(Material.SAND);
            }, 10L);
        };
    }
}
