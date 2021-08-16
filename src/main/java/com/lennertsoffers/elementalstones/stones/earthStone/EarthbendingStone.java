package com.lennertsoffers.elementalstones.stones.earthStone;

import com.lennertsoffers.elementalstones.customClasses.Tools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Objects;

public class EarthbendingStone {

    // MOVE 4
    public static void move4(Player player, FallingBlock move4Block) {
        if (move4Block == null) {
            return;
        }
        Location playerLocation = player.getLocation();
        Location blockLocation = move4Block.getLocation();
        double playerX = playerLocation.getX();
        double playerZ = playerLocation.getZ();
        double blockX = blockLocation.getX();
        double blockZ = blockLocation.getBlockZ();
        double length = Tools.lengthOfVector(blockX, playerX, blockZ, playerZ);
        Vector entityVector = new Vector(((blockX - playerX) / length) * 5, 0, ((blockZ - playerZ) / length) * 5);
        move4Block.setVelocity(entityVector);
    }

    // MOVE 5
    public static void move5(Player player, FallingBlock move4Block) {
        if (move4Block == null) {
            return;
        }
    }

    // MOVE 6


    // MOVE 7


    // MOVE 8
}
