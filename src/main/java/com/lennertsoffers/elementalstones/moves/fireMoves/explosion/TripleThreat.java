package com.lennertsoffers.elementalstones.moves.fireMoves.explosion;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireFireworks;
import com.lennertsoffers.elementalstones.customClasses.tools.FireworkTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class TripleThreat extends Move {

    /**
     * <b>MOVE 5: Triple Threat</b>
     * <p>
     *     The player takes 3 rockets flowing its movements<br>
     *     These rockets will be launched automatically after 20s<br>
     *     Reactivating this move will launch the fireworks<br>
     *     When crouching a firework show will appear<br>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @see FireFireworks
     */
    public TripleThreat(ActivePlayer activePlayer) {
        super(activePlayer, "Triple Threat", "fire_stone", "explosion_stone", 5);
    }

    @Override
    public void useMove() {
        ActivePlayer activePlayer = this.getActivePlayer();
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        Location location = player.getLocation();
        Vector direction = location.getDirection().setY(0);

        if (player.isSneaking()) {
            Vector perpendicularDirection = direction.clone().rotateAroundY(Math.PI / 2);

            location.add(direction.clone().multiply(20));
            Location finalCenterLocation = location.clone();
            location.add(perpendicularDirection.clone().multiply(-10));

            new BukkitRunnable() {
                int rows = 1;

                @Override
                public void run() {
                    for (int i = 1; i < 20; i++) {
                        Location fireworkLocation = location.clone().add(perpendicularDirection.clone().multiply(i)).add(direction.clone().multiply(rows));
                        fireworkLocation.setY(world.getHighestBlockYAt(location) + 1);
                        FireworkTools.setRandomMeta(((Firework) world.spawnEntity(fireworkLocation, EntityType.FIREWORK)), 1, null, 3, 3, -1, -1);
                    }

                    rows++;
                    if (rows > 10) {
                        this.cancel();
                        finalCenterLocation.setY(world.getHighestBlockYAt(finalCenterLocation) + 1);
                        FireworkTools.setRandomMeta(((Firework) world.spawnEntity(finalCenterLocation, EntityType.FIREWORK)), 1, FireworkEffect.Type.BALL_LARGE, 2, 2, 0, 1);
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 50L);
            this.setCooldown();
        } else if (activePlayer.getFireFireworks() == null) {
            FireFireworks fireFireworks = new FireFireworks(activePlayer);
            activePlayer.setFireFireworks(fireFireworks);
            fireFireworks.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else {
            activePlayer.getFireFireworks().shootFireworks();
            activePlayer.setFireFireworks(null);
            this.setCooldown();
        }
    }
}
