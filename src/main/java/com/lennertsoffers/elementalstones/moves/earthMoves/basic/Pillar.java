package com.lennertsoffers.elementalstones.moves.earthMoves.basic;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pillar extends Move {

    /**
     * <b>MOVE 1: Pillar</b>
     * <p>
     *     Creates a pillar on the targeted location<br>
     *     Colliding entities are launched up<br>
     *     The pillar will be removed after a certain amount of time<br>
     *     <ul>
     *         <li><b>Duration:</b> 3s</li>
     *         <li><b>Range: </b> 40</li>
     *         <li><b>Knockup:</b> 1</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @see Pillar
     */
    public Pillar(ActivePlayer activePlayer) {
        super(activePlayer, "Pillar", "earth_stone", "default", 1);
    }

    @Override
    public void useMove() {
        Block targetBlock = this.getPlayer().getTargetBlockExact(40);
        Vector velocity = new Vector(0, 1, 0);

        if (targetBlock != null) {
            World world = this.getPlayer().getWorld();
            Location targetLocation = targetBlock.getLocation();

            if (!targetBlock.getType().isSolid()) {
                targetLocation.add(0, -1, 0);
            }

            List<Block> moveBlocks = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Location underGroundLocation = targetLocation.clone().add(0, -i, 0);
                Location aboveGroundLocation = targetLocation.clone().add(0, i + 1, 0);
                Block underGroundBlock = world.getBlockAt(underGroundLocation);
                Block aboveGroundBlock = world.getBlockAt(aboveGroundLocation);

                if (
                        underGroundBlock.getType().isSolid() &&
                                (CheckLocationTools.isFoliage(aboveGroundLocation) || aboveGroundBlock.getType().isAir())
                ) {
                    moveBlocks.add(underGroundBlock);
                } else {
                    break;
                }
            }

            if (moveBlocks.size() > 0) {
                com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.Pillar pillar = new com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.Pillar(targetLocation, moveBlocks, true);
                for (Entity entity : world.getNearbyEntities(targetLocation, 1, 3, 1)) {
                    entity.setVelocity(velocity);
                }
                pillar.runTaskTimer(StaticVariables.plugin, 3L, 1L);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Collections.reverse(moveBlocks);

                        com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.Pillar pillarRemoval = new com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.Pillar(targetLocation, moveBlocks, false);
                        pillarRemoval.runTaskTimer(StaticVariables.plugin, 3L, 1L);
                    }
                }.runTaskLater(StaticVariables.plugin, 66L);
            }

            this.setCooldown();
        }
    }
}
