package com.lennertsoffers.elementalstones.moves.fireMoves.hellfire;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireBlast;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class RingOfFire extends Move {

    public RingOfFire(ActivePlayer activePlayer) {
        super(activePlayer, "Ring Of Fire", "fire_stone", "hellfire_stone", 5);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();
        World world = player.getWorld();

        FireBlast fireBlast = new FireBlast(player, world);
        fireBlast.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }
}
