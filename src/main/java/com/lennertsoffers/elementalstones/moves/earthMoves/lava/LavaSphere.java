package com.lennertsoffers.elementalstones.moves.earthMoves.lava;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.entity.Player;

public class LavaSphere extends Move {

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
    }
}
