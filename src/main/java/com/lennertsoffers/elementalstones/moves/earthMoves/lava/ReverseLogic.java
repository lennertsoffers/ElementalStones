package com.lennertsoffers.elementalstones.moves.earthMoves.lava;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ReverseLogic extends Move {

    /**
     * <b>MOVE 4: Reverse Logic</b>
     * <p>
     *     The player heals over time while standing on magma blocks<br>
     *     <ul>
     *         <li><b>Heal:</b> 3 health/s</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     */
    public ReverseLogic(ActivePlayer activePlayer) {
        super(activePlayer, "Reverse Logic", "earth_stone", "lava_stone", 4);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();
        World world = player.getWorld();

        new BukkitRunnable() {
            int amountOfTicks = 0;

            @Override
            public void run() {
                Location location = player.getLocation().add(0, 1, 0);

                for (int i = 0; i < 2; i++) {
                    player.getWorld().spawnParticle(Particle.REDSTONE, location.getX() + StaticVariables.random.nextGaussian() / 3, location.getY() + StaticVariables.random.nextGaussian() / 3, location.getZ() + StaticVariables.random.nextGaussian() / 3, 0, 0, 0, 0, new Particle.DustOptions(Color.RED, 1));
                }

                if (amountOfTicks % 20 == 0) {
                    if (world.getBlockAt(location.clone().add(0, -2, 0)).getType() == Material.MAGMA_BLOCK) {
                        double health = player.getHealth() + 3;
                        health = health <= 20 ? health : 20;

                        player.setHealth(health);
                    }
                }

                if (amountOfTicks >= 200) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

        this.setCooldown();
    }
}
