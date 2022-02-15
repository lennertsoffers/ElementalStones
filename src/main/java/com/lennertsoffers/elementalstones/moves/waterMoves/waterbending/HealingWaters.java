package com.lennertsoffers.elementalstones.moves.waterMoves.waterbending;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class HealingWaters extends Move {

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
    }
}
