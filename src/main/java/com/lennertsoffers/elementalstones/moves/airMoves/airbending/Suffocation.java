package com.lennertsoffers.elementalstones.moves.airMoves.airbending;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.AirBreath;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Suffocation extends Move {

    /**
     * <b>ULTIMATE: Suffocation</b>
     * <p>
     *     Selects all entities in a 10 block range<br>
     *     A particle effect will come out of these entities (representing their breath) and move slowly towards the executor<br>
     *     If the player can take the breath, the entity corresponding to this breath will get damaged and the player will be healed<br>
     *     If the entity of which this breath is takes the breath, nothing will happen and the entity is safe again<br>
     *     If no one takes the breath after 30s, the entity will get damaged an no one healed<br>
     *     <ul>
     *         <li><b>Damage:</b> 10</li>
     *         <li><b>Heal:</b> 10</li>
     *         <li><b>Duration:</b> 30s</li>
     *         <li><b>Range:</b> 10</li>
     *     </ul>
     * </p>
     * @param activePlayer the activeplayer executing the move
     * @see AirBreath
     */
    public Suffocation(ActivePlayer activePlayer) {
        super(activePlayer, "Suffocation", "air_stone", "airbending_stone", 8);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();
        World world = player.getWorld();
        boolean foundEntity = false;

        for (Entity entity : world.getNearbyEntities(player.getLocation(), 10, 10, 10, entity -> entity instanceof LivingEntity && entity != player)) {
            AirBreath airBreath = new AirBreath((LivingEntity) entity, player);
            airBreath.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            entity.setGlowing(true);
            foundEntity = true;
        }

        if (foundEntity) {
            this.setCooldown();
        }
    }
}
