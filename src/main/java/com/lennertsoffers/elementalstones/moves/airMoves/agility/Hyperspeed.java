package com.lennertsoffers.elementalstones.moves.airMoves.agility;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Hyperspeed extends Move {

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
