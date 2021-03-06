package com.lennertsoffers.elementalstones.moves.fireMoves.hellfire;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.LinkedList;

public class FireTrack extends Move {

    /**
     * <b>MOVE 4: Fire Track</b>
     * <p>
     *     The player leaves a track of fire behind him/her<br>
     *     The speed of the player is drastically improved<br>
     *     <ul>
     *         <li><b>Duration:</b> 30s</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     */
    public FireTrack(ActivePlayer activePlayer) {
        super(activePlayer, "Fire Track", "fire_stone", "hellfire_stone", 4);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 3, true, true, true));
        LinkedList<HashMap<String, Object>> fireBlockInfo = new LinkedList<>();

        new BukkitRunnable() {
            int amountOfTicks = 0;

            @Override
            public void run() {
                Location location = player.getLocation();
                Block block = location.getBlock();
                Material blockMaterial = block.getType();

                location.add(0, -1, 0);
                Block walkingBlock = location.getBlock();
                Material walkingMaterial = walkingBlock.getType();

                if (
                        walkingMaterial != Material.AIR &&
                                walkingMaterial != Material.LAVA &&
                                walkingMaterial != Material.WATER &&
                                walkingMaterial.isSolid() &&
                                (blockMaterial == Material.AIR || CheckLocationTools.isFoliage(blockMaterial))
                ) {
                    HashMap<String, Object> fireBlockInfoMap = new HashMap<>();
                    fireBlockInfoMap.put("block", block);
                    fireBlockInfoMap.put("material", blockMaterial);
                    fireBlockInfo.add(fireBlockInfoMap);

                    block.setType(Material.FIRE);

                    if (fireBlockInfo.size() > 20) {
                        HashMap<String, Object> removeFireBlockInfoMap = fireBlockInfo.pop();
                        Block fireBlock = (Block) removeFireBlockInfoMap.get("block");
                        Material fireBlockMaterial = (Material) removeFireBlockInfoMap.get("material");

                        fireBlock.setType(fireBlockMaterial);
                    }
                }

                amountOfTicks++;
                if (amountOfTicks > 600) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

        this.setCooldown();
    }
}
