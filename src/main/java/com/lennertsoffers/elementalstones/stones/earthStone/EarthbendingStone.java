package com.lennertsoffers.elementalstones.stones.earthStone;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Objects;

public class EarthbendingStone {

    // MOVE 4
    public static void move4(Player player) {
        World world = player.getWorld();
        Block targetBlock = Objects.requireNonNull(player.getTargetBlockExact(100));
        FallingBlock fallingBlock = world.spawnFallingBlock(targetBlock.getLocation(), targetBlock.getBlockData());
        world.getBlockAt(targetBlock.getLocation()).setType(Material.AIR);
        fallingBlock.setVelocity(new Vector(0, 1, 0));
    }

    // MOVE 5

    // MOVE 6


    // MOVE 7


    // MOVE 8
}
