package com.lennertsoffers.elementalstones.stones.earthStone;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Objects;

public class EarthbendingStone {

    private static FallingBlock move4Block = null;

    // MOVE 4
    public static void move4(Player player) {
        World world = player.getWorld();
        Block targetBlock = Objects.requireNonNull(player.getTargetBlockExact(100));
        FallingBlock fallingBlock = world.spawnFallingBlock(targetBlock.getLocation(), targetBlock.getBlockData());
        world.getBlockAt(targetBlock.getLocation()).setType(Material.AIR);
        fallingBlock.setVelocity(new Vector(0, 0.7, 0));
        fallingBlock.setHurtEntities(true);
        move4Block = fallingBlock;
    }

    // MOVE 5
    public static void move5(Player player) {
        if (move4Block == null) {
            return;
        }
        Location playerLocation = player.getLocation();
        Location blockLocation = move4Block.getLocation();
        double playerX = playerLocation.getX();
        double playerZ = playerLocation.getZ();
        double blockX = blockLocation.getX();
        double blockZ = blockLocation.getBlockZ();
        Vector.
        Vector vector = new Vector(1/(blockX - playerX) * 4, 0, 1/(blockZ - playerZ) * 4    );
        move4Block.setVelocity(vector);
    }

    // MOVE 6


    // MOVE 7


    // MOVE 8
}
