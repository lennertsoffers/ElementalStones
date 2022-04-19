package com.lennertsoffers.elementalstones.moves.airMoves.agility;

import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Hyperspeed extends Move {

    /**
     * <b>ULTIMATE: Hyperspeed</b>
     * <p>
     *     Makes the player run faster<br>
     *     Makes the player jump higher<br>
     *     Dashes do more damage<br>
     *     Charge jump is stronger<br>
     *     <ul>
     *         <li><b>PotionEffects:</b>
     *             <ul>
     *                 <li>Speed (duration: 1min, amplifier: 3</li>
     *                 <li>Jump (duration: 1min, amplifier: 2</li>
     *             </ul>
     *         </li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     */
    public Hyperspeed(ActivePlayer activePlayer) {
        super(activePlayer, "Hyperspeed", "air_stone", "agility_stone", 8);
    }

    @Override
    public void useMove() {
        this.getActivePlayer().activateAirBoost();
        this.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 3, false, false, false));
        this.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1200, 2, false, false, false));
        this.setCooldown();
    }
}
