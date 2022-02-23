package com.lennertsoffers.elementalstones.moves.earthMoves.earthbending;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class RockThrow extends Move {

    public RockThrow(ActivePlayer activePlayer) {
        super(activePlayer, "Rock Throw", "earth_stone", "earthbending_stone", 6);
    }

    @Override
    public void useMove() {
        ActivePlayer activePlayer = this.getActivePlayer();
        Player player = this.getPlayer();
        World world = player.getWorld();

        // Select blocks to shoot
        if (!player.isSneaking()) {
            Block block = player.getTargetBlockExact(80, FluidCollisionMode.NEVER);

            if (block != null && block.getType().isSolid()) {
                FallingBlock fallingBlock = world.spawnFallingBlock(block.getLocation().add(0.5, 0, 0.5), block.getBlockData());

                Vector velocity = new Vector(0, 0.8, 0);
                if (activePlayer.isMove8active()) {
                    velocity.multiply(2);
                }
                fallingBlock.setVelocity(velocity);
                fallingBlock.setDropItem(false);

                activePlayer.addLocationMaterialMapping(block.getLocation(), block.getType());
                block.setType(Material.AIR);

                activePlayer.getMove6FallingBlocks().add(fallingBlock);
            }
        }

        // Shoot selected blocks in looking direction
        else {
            Vector direction = player.getLocation().getDirection();
            for (FallingBlock fallingBlock : activePlayer.getMove6FallingBlocks()) {
                fallingBlock.setVelocity(direction.clone().multiply(3));
                activePlayer.getMove6LaunchedFallingBlocks().add(fallingBlock);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!fallingBlock.isDead()) {
                            NearbyEntityTools.damageNearbyEntities(player, fallingBlock.getLocation(), 4, 1.5, 1.5, 1.5, direction);
                        } else {
                            this.cancel();
                        }
                    }
                }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            }

            activePlayer.getMove6FallingBlocks().clear();
            this.setCooldown();
        }
    }
}
