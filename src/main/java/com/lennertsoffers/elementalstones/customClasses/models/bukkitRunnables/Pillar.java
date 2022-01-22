package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Pillar extends BukkitRunnable {
    private int amountOfPushUps = 0;
    private final World world;
    private final Location targetLocation;
    private final Vector velocity = new Vector(0, 1, 0);
    private final List<Block> moveBlocks;
    private final int nextBlockY;

    public Pillar(Location targetLocation, List<Block> moveBlocks, boolean creation) {
        this.targetLocation = targetLocation;
        this.moveBlocks = moveBlocks;
        this.world = targetLocation.getWorld();
        nextBlockY = creation ? 1 : -1;
    }

    @Override
    public void run() {
        if (nextBlockY == 1) {
            for (Entity entity : world.getNearbyEntities(targetLocation, 1, 3, 1)) {
                entity.setVelocity(velocity);
            }
        }

        List<Block> newMoveBlocks = new ArrayList<>();
        moveBlocks.forEach(moveBlock -> {
            Block block = world.getBlockAt(moveBlock.getLocation().add(0, nextBlockY, 0));
            block.setType(moveBlock.getType());
            newMoveBlocks.add(block);
            moveBlock.setType(Material.AIR);
        });
        moveBlocks.clear();
        moveBlocks.addAll(newMoveBlocks);

        amountOfPushUps++;
        if (amountOfPushUps >= 3) {
            this.cancel();
        }
    }
}
