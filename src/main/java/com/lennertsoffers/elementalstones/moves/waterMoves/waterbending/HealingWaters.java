package com.lennertsoffers.elementalstones.moves.waterMoves.waterbending;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class HealingWaters extends Move {

    /**
     * <b>MOVE 5: Healing Waters</b>
     * <p>
     *     Heals the player over time if he/she stand in water<br>
     *     The player will be slower but gets the absorption effect<br>
     *     <ul>
     *         <li><b>Heal:</b> 3health/s</li>
     *         <li><b>Duration:</b> 10s</li>
     *         <li><b>PotionEffects:</b>
     *             <ul>
     *                 <li>Absorption (duration: 10s, amplifier: 1)</li>
     *                 <li>Slowness (duration: 2s, amplifier: 2)</li>
     *             </ul>
     *         </li>
     *     </ul>
     * </p>
     * @param activePlayer the activePlayer executing the move
     */
    public HealingWaters(ActivePlayer activePlayer) {
        super(activePlayer, "Healing Waters", "water_stone", "waterbending_stone", 5);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();
        new BukkitRunnable() {
            int amountOfSeconds = 0;

            @Override
            public void run() {
                Location location = player.getLocation();
                if (location.getBlock().getType() == Material.WATER) {
                    if (player.getHealth() > 19) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 1, true, true, true));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 2, true, true, true));
                    }

                    player.setHealth(player.getHealth() + 3);

                }
                location.add(0, 1, 0);
                if (amountOfSeconds > 10) {
                    this.cancel();
                }
                amountOfSeconds++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 20L);

        this.setCooldown();
    }
}
