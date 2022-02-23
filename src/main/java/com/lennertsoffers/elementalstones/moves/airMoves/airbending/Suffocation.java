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
