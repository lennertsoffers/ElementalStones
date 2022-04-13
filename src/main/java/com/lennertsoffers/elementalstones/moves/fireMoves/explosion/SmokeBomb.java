package com.lennertsoffers.elementalstones.moves.fireMoves.explosion;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.Grenade;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.GrenadeSmoke;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class SmokeBomb extends Move {

    /**
     * <b>MOVE 4: Smoke Bomb</b>
     * <p>
     *     The player throws a bomb in its looking direction<br>
     *     The falling path of this bomb will act with natural physics<br>
     *     Any entities caught in the smoke will get blindness and slowness<br>
     *     <ul>
     *         <li><b>Duration: </b> 20s</li>
     *         <li><b>PotionEffects:</b>
     *             <ul>
     *                 <li>Blindness (duration: 10s, amplifier: 3)</li>
     *                 <li>Slowness (duration: 10s, amplifier: 2)</li>
     *             </ul>
     *         </li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @see Grenade
     * @see GrenadeSmoke
     */
    public SmokeBomb(ActivePlayer activePlayer) {
        super(activePlayer, "Smoke Bomb", "fire_stone", "explosion_stone", 4);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();
        Grenade grenade = new GrenadeSmoke(player, Particle.REDSTONE, new Particle.DustOptions(Color.GRAY, 2));
        grenade.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        this.setCooldown();
    }
}
