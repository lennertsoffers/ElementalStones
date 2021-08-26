package com.lennertsoffers.elementalstones.stones.fireStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class HellfireStone extends FireStone {

    private static void spawnSmallFlameOrb(Location location, Vector direction, Random random, World world) {
        for (int j = 0; j < 2; j++) {
            Location flameLocation = location.clone().add(direction);
            flameLocation.add(random.nextGaussian() / 20, random.nextGaussian() / 20, random.nextGaussian() / 20);
            world.spawnParticle(Particle.FLAME, flameLocation, 0, 0, 0, 0);

        }
    }

    // PASSIVE


    // MOVE 4
    // Fire Track
    // -> You leave a fire track behind you
    // Activation
    public static void move4(ActivePlayer activePlayer) {
        activePlayer.setHellfireStoneMove4TimeRemaining();
    }
    // Move
    public static void move4(ActivePlayer activePlayer, PlayerMoveEvent event) {
        if (activePlayer.hasHellfireStoneMove4TimeRemaining()) {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            Block block = world.getBlockAt(event.getFrom().add(0, -1, 0));
            if (block.getType() != Material.AIR) {
                Block fireBlock = block.getRelative(BlockFace.UP);
                fireBlock.setType(Material.FIRE);
                StaticVariables.scheduler.scheduleSyncDelayedTask(StaticVariables.plugin, () -> fireBlock.setType(Material.AIR), 80L);
            }
        }
    }

    // MOVE 5
    // Follow up to floating fire
    //

    // MOVE 6


    // MOVE 7
    // Fire Blast
    // Shoot an array of destructing fire out of your hands
    public static void move7(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        new BukkitRunnable() {
            double stageOfBeam = 0.1;
            final Random random = new Random();
            @Override
            public void run() {
                final Location location = player.getEyeLocation().add(0, -0.6, 0);
                final Vector direction = location.getDirection();
                Location variableLocation = location.clone();
                for (double i = 0.1; i <= stageOfBeam; i += 0.1) {
                    variableLocation.add(direction.getX() / 40 * i, direction.getY() / 40 * i, direction.getZ() / 40 * i);
                    spawnSmallFlameOrb(variableLocation, direction, random, world);
                }
                if (stageOfBeam >= 7) {
                    this.cancel();
                }
                stageOfBeam += 0.35;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        new BukkitRunnable() {
            int tickCount = 0;
            final Random random = new Random();
            @Override
            public void run() {
                final Location location = player.getEyeLocation();
                final Vector direction = location.getDirection();
                location.add(0, -0.6, 0);
                for (double i = 0.1; i < 7; i += 0.1) {
                    spawnSmallFlameOrb(location, direction, random, world);
                    location.add(direction.getX() / 40 * i, direction.getY() / 40 * i, direction.getZ() / 40 * i);
                }
                if (tickCount > 100) {
                    this.cancel();
                }
                tickCount++;
            }
        }.runTaskTimer(StaticVariables.plugin, 20L, 1L);
    }

    // MOVE 8
}
