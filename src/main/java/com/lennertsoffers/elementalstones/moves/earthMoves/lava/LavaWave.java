package com.lennertsoffers.elementalstones.moves.earthMoves.lava;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LavaWave extends Move {

    /**
     * <b>MOVE 5: Lava Wave</b>
     * <p>
     *     Creates a wave of lava in the looking direction
     *     <ul>
     *         <li><b>Damage:</b> 40</li>
     *         <li><b>Range: </b> 50</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @see LavaWave
     */
    public LavaWave(ActivePlayer activePlayer) {
        super(activePlayer, "Lava Wave", "earth_stone", "lava_stone", 5);
    }

    @Override
    public void useMove() {
        ActivePlayer activePlayer = this.getActivePlayer();
        Player player = this.getPlayer();
        Location playerLocation = player.getLocation();

        float yaw = playerLocation.getYaw();
        if (yaw > -25 && yaw < 25) {
            new com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.LavaWave(activePlayer, true, true, true).runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else if (yaw >= 25 && yaw < 65) {
            new com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.LavaWave(activePlayer, false, true, false).runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else if (yaw >= 65 && yaw < 115) {
            new com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.LavaWave(activePlayer, true, false, true).runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else if (yaw >= 115 && yaw < 155) {
            new com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.LavaWave(activePlayer, false, false, true).runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else if (yaw < -155 || yaw > 155) {
            new com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.LavaWave(activePlayer, true, false, false).runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else if (yaw <= -25 && yaw > -65) {
            new com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.LavaWave(activePlayer, false, true, true).runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else if (yaw <= -65 && yaw > -115) {
            new com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.LavaWave(activePlayer, true, true, false).runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else {
            new com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.LavaWave(activePlayer, false, false, false).runTaskTimer(StaticVariables.plugin, 0L, 1L);
        }

        this.setCooldown();
    }
}
