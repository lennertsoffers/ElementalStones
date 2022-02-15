package com.lennertsoffers.elementalstones.moves.fireMoves.explosion;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.Grenade;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.GrenadeSmoke;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class SmokeBomb extends Move {

    public SmokeBomb(ActivePlayer activePlayer) {
        super(activePlayer, "Smoke Bomb", "fire_stone", "explosion_stone", 4);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();
        Grenade grenade = new GrenadeSmoke(player, Particle.REDSTONE, new Particle.DustOptions(Color.GRAY, 2));
        grenade.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }
}
