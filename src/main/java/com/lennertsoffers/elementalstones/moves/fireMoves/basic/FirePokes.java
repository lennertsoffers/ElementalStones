package com.lennertsoffers.elementalstones.moves.fireMoves.basic;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireBall;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class FirePokes extends Move {

    public FirePokes(ActivePlayer activePlayer) {
        super(activePlayer, "Fire Pokes", "fire_stone", "default", 2);
    }

    @Override
    public void useMove() {
        ActivePlayer activePlayer = this.getActivePlayer();
        FireBall fireBall = activePlayer.getFireBall();
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();

        if (fireBall == null) {
            FireBall newFireBall = new FireBall(player, world);
            newFireBall.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            activePlayer.setFireBall(newFireBall);
        } else {
            fireBall.poke(activePlayer);
        }
    }
}
