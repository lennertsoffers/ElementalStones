package com.lennertsoffers.elementalstones.moves.fireMoves.hellfire;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireWall;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class FireShields extends Move {

    /**
     * <b>MOVE 6: Fire Shields</b>
     * <p>
     *     The player can place 5 walls of fire<br>
     *     These walls will protect the player from entities or other moves<br>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @see FireWall
     */
    public FireShields(ActivePlayer activePlayer) {
        super(activePlayer, "Fire Shields", "fire_stone", "hellfire_stone", 6);
    }

    @Override
    public void useMove() {
        ActivePlayer activePlayer = this.getActivePlayer();

        if (activePlayer.canPlaceWall()) {
            Player player = activePlayer.getPlayer();
            Location feetLocation = player.getLocation();
            Vector direction = feetLocation.getDirection().setY(0);

            Location wallBottomLeft = feetLocation.clone().add(direction.clone().multiply(4));
            direction.rotateAroundY(Math.PI / 2);
            wallBottomLeft.add(direction.clone().multiply(-1));

            FireWall fireWall = new FireWall(activePlayer, wallBottomLeft, direction);
            fireWall.runTaskTimer(StaticVariables.plugin, 0L, 10L);
        }
    }
}
