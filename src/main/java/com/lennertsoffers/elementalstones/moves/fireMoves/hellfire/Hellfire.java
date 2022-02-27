package com.lennertsoffers.elementalstones.moves.fireMoves.hellfire;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireHellfireStorm;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.entity.Player;

public class Hellfire extends Move {

    /**
     * <b>ULTIMATE: Hellfire</b>
     * <p>
     *     Creates a storm of lightning and fireballs around the player<br>
     *     These fireballs will not break blocks<br>
     *     <ul>
     *         <li><b>Range:</b> 30</li>
     *         <li><b>Duration:</b> 10s</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @see FireHellfireStorm
     */
    public Hellfire(ActivePlayer activePlayer) {
        super(activePlayer, "Hellfire", "fire_stone", "hellfire_stone", 8);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();
        FireHellfireStorm fireHellfireStorm = new FireHellfireStorm(player);
        fireHellfireStorm.runTaskTimer(StaticVariables.plugin, 0L, 5L);

        this.setCooldown();
    }
}
