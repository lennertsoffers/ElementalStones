package com.lennertsoffers.elementalstones.moves.waterMoves.basic;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class DolphinDive extends Move {

    /**
     * <b>MOVE 2: Dolphin Dive</b>
     * <p>
     *     The player gets the dolphins grace effect for 1 minute<br>
     *     If the player has the waterbending stone, its passive effect gets doubled<br>
     *     <ul>
     *         <li><b>PotionEffect:</b> Dolphin Grace (duration: 1min, amplifier 3)</li>
     *     </ul>
     * </p>
     * @param activePlayer the activeplayer executing the move
     */
    public DolphinDive(ActivePlayer activePlayer) {
        super(activePlayer, "Dolphin Dive", "water_stone", "default", 2);
    }

    @Override
    public void useMove() {
        ActivePlayer activePlayer = this.getActivePlayer();
        activePlayer.setDoublePassive1(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                activePlayer.setDoublePassive1(false);
            }
        }.runTaskLater(StaticVariables.plugin, 1200L);
        Player player = activePlayer.getPlayer();
        player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 1200, 3, true, true, true));

        this.setCooldown();
    }
}
