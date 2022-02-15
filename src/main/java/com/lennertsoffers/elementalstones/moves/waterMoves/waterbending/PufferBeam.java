package com.lennertsoffers.elementalstones.moves.waterMoves.waterbending;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PufferBeam extends Move {

    public PufferBeam(ActivePlayer activePlayer) {
        super(activePlayer, "Puffer Beam", "water_stone", "waterbending_stone", 6);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();
        new BukkitRunnable() {
            int amountOfTicks = 0;

            @Override
            public void run() {
                Location location = player.getLocation().add(0, 1, 0);
                location.add(location.getDirection());

                Entity pufferFish = player.getWorld().spawnEntity(location, EntityType.PUFFERFISH);
                pufferFish.setVelocity(location.getDirection().multiply(2));

                if (amountOfTicks >= 100) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }
}
