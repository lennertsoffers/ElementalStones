package com.lennertsoffers.elementalstones.moves.fireMoves.basic;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireBall;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class FirePokes extends Move {

    /**
     * <b>MOVE 2: Fire Pokes</b>
     * <p>
     *     Creates a ball of fire that is a source for 6 little fire projectiles<br>
     *     Shoot one of the projectiles by reactivating the move<br>
     *     The fire ball will progressively get smaller if the pokes decrease in amount<br>
     *     <ul>
     *         <li><b>Damage:</b> 2</li>
     *         <li><b>FireTicks:</b> 20</li>
     *         <li><b>Duration: </b> 20s</li>
     *         <li><b>Ammo:</b> 6</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @see FireBall
     */
    public FirePokes(ActivePlayer activePlayer) {
        super(activePlayer, "Fire Pokes", "fire_stone", "default", 2);
    }

    @Override
    public void useMove() {
        ActivePlayer activePlayer = this.getActivePlayer();
        FireBall fireBall = activePlayer.getFireBall();
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();

        if (fireBall == null) {
            FireBall newFireBall = new FireBall(player, world);
            newFireBall.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            activePlayer.setFireBall(newFireBall);
        } else {
            if (!fireBall.poke(activePlayer)) {
                this.setCooldown();
            }
        }
    }
}
