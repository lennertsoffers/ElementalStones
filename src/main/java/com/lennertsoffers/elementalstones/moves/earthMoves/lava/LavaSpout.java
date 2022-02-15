package com.lennertsoffers.elementalstones.moves.earthMoves.lava;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class LavaSpout extends Move {

    public LavaSpout(ActivePlayer activePlayer) {
        super(activePlayer, "Lava Spout", "earth_stone", "lava_stone", 7);
    }

    @Override
    public void useMove() {
        ActivePlayer activePlayer = this.getActivePlayer();
        Player player = activePlayer.getPlayer();

        Block targetBlock = player.getTargetBlockExact(15);

        if (targetBlock != null) {
            Location location = CheckLocationTools.getClosestAirBlockLocation(targetBlock.getLocation());

            if (location != null) {
                location.add(0, -1, 0);

                com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.LavaSpout lavaSpout = new com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.LavaSpout(activePlayer, location);
                lavaSpout.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            }

            this.setCooldown();
        }
    }
}
