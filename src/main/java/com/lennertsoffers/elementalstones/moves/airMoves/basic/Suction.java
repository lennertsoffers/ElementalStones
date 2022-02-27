package com.lennertsoffers.elementalstones.moves.airMoves.basic;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Suction extends Move {

    /**
     * <b>MOVE 3: Suction</b>
     * <p>
     *     Sucks all the items in close range towards the player<br>
     *     It launches the items up and after 0.5s the are shot towards the player<br>
     *     The targeted items get the glowing effect<br>
     *     <ul>
     *         <li><b>Range: </b> 15</li>
     *     </ul>
     * </p>
     * @param activePlayer the activeplayer executing the move
     */
    public Suction(ActivePlayer activePlayer) {
        super(activePlayer, "Suction", "air_stone", "default", 3);
    }

    @Override
    public void useMove() {
        World world = this.getPlayer().getWorld();
        if (!world.getNearbyEntities(this.getPlayer().getLocation(), 15, 15, 15).isEmpty()) {
            for (Entity entity : world.getNearbyEntities(this.getPlayer().getLocation(), 15, 15, 15)) {
                if (entity != null) {
                    if (entity instanceof Item) {
                        Item item = (Item) entity;
                        item.setGlowing(true);
                        item.setPickupDelay(0);
                        item.setVelocity(new Vector(0, 0.7, 0));
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                item.setVelocity(MathTools.getDirectionNormVector3d(item.getLocation(), getPlayer().getLocation()).multiply(3));
                            }
                        }.runTaskLater(StaticVariables.plugin, 10L);
                    }
                }
            }
        }

        this.setCooldown();
    }
}
