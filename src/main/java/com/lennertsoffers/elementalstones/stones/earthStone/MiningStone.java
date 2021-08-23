package com.lennertsoffers.elementalstones.stones.earthStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class MiningStone {

    // PASSIVE


    // MOVE 4
    // Rock Smash
    // -> Following up to flying rock
    // -> Launches the rock back into the ground to create a hole straight down
    public static void move4(ActivePlayer activePlayer) {
        FallingBlock fallingBlock = activePlayer.getFallingBlock();
        if (fallingBlock != null) {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            fallingBlock.setVelocity(new Vector(0, -0.5, 0));
            Location initialLocation = fallingBlock.getLocation();
            for (int i = 0; i < 50; i++) {
                world.spawnParticle(Particle.SPIT, initialLocation, 0, 0.0, 0.5, 0.0);
            }
            new BukkitRunnable() {
                int amountOfTicks = 0;
                @Override
                public void run() {
                    Location location = fallingBlock.getLocation();
                    Block[] blocks = {
                        world.getBlockAt(location.add(0, -1, 0)),
                        world.getBlockAt(location.add(0, 0, 1)),
                        world.getBlockAt(location.add(1, 0, 0)),
                        world.getBlockAt(location.add(0, 0, -1)),
                        world.getBlockAt(location.add(0, 0, -1)),
                        world.getBlockAt(location.add(-1, 0, 0)),
                        world.getBlockAt(location.add(-1, 0, 0)),
                        world.getBlockAt(location.add(0, 0, 1)),
                        world.getBlockAt(location.add(0, 0, 1))
                    };
                    for (Block block : blocks) {
                        if (block.getType() != Material.AIR && block.getType() != Material.BEDROCK) {
                            world.spawnParticle(Particle.EXPLOSION_NORMAL, block.getLocation(), 0, 0.0, 0.1, 0.0);
                            block.breakNaturally();
                            block.getDrops().clear();
                        }
                    }

                    amountOfTicks++;
                    if (amountOfTicks > 100) {
                        this.cancel();
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        }
    }

    // MOVE 5


    // MOVE 6


    // MOVE 7


    // MOVE 8
}
