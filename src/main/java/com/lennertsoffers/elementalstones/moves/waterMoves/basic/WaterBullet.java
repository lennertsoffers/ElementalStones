package com.lennertsoffers.elementalstones.moves.waterMoves.basic;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.LinkedList;

public class WaterBullet extends Move {

    /**
     * <b>MOVE 3: Water Bullet</b>
     * <p>
     *     Shoots a bullet of water damaging and penetrating entities<br>
     *     <ul>
     *         <li><b>Damage:</b> 4</li>
     *         <li><b>Knockback:</b> 1</li>
     *     </ul>
     * </p>
     * @param activePlayer the activeplayer executing the move
     */
    public WaterBullet(ActivePlayer activePlayer) {
        super(activePlayer, "Water Bullet", "water_stone", "default", 3);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();
        World world = player.getWorld();
        Location location = player.getLocation().add(0, 1, 0);
        Vector vector = location.getDirection().multiply(0.5);
        location.add(vector.clone().multiply(5));

        LinkedList<Block> waterBlocks = new LinkedList<>();

        new BukkitRunnable() {
            int amountOfTicks = 1;

            @Override
            public void run() {
                if (waterBlocks.size() > 3) {
                    waterBlocks.pop().setType(Material.AIR);
                }

                Location blockLocation = location.clone().add(vector.clone().multiply(amountOfTicks));
                Block block = world.getBlockAt(blockLocation);
                NearbyEntityTools.damageNearbyEntities(player, blockLocation, 5, 1, 1, 1, vector);

                Material material = block.getType();
                if (material == Material.AIR || material == Material.FIRE || material == Material.WATER || CheckLocationTools.isFoliage(material)) {
                    block.setType(Material.WATER);
                    waterBlocks.add(block);
                } else {
                    this.cancel();

                    for (Block waterBlock : waterBlocks) {
                        waterBlock.setType(Material.AIR);
                    }
                    waterBlocks.clear();
                }

                location.add(vector);

                amountOfTicks++;
                if (amountOfTicks > 100) {
                    this.cancel();

                    for (Block waterBlock : waterBlocks) {
                        waterBlock.setType(Material.AIR);
                    }
                    waterBlocks.clear();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

        this.setCooldown();
    }
}
