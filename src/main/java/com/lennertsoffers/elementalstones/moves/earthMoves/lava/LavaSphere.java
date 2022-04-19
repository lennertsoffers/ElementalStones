package com.lennertsoffers.elementalstones.moves.earthMoves.lava;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.entity.Player;

public class LavaSphere extends Move {

    /**
     * <b>ULTIMATE: Lava Sphere</b>
     * <p>
     *     The player begins to fly on a large ball of lava<br>
     *     This lava does damage to entities you fly over<br>
     *     After the move the player shoots up and can try to land safely on the lowering lava<br>
     *     <ul>
     *         <li><b>Duration: </b> 1min</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @see LavaSphere
     */
    public LavaSphere(ActivePlayer activePlayer) {
        super(activePlayer, "Lava Sphere", "earth_stone", "lava_stone", 8);
    }

    @Override
    public void useMove() {
        ActivePlayer activePlayer = this.getActivePlayer();
        Player player = this.getPlayer();
        player.setAllowFlight(true);
        player.setFlying(true);

        com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.LavaSphere lavaSphere = new com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.LavaSphere(activePlayer);
        lavaSphere.runTaskTimer(StaticVariables.plugin, 0L, 1L);

        this.setCooldown();
    }
}
