package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.annotations.Event;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.moves.earthMoves.earthbending.FlyingRock;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

@Event
public class FallingBlockToBlockEvent implements Listener {

    @EventHandler
    public void entityChangeBlock(EntityChangeBlockEvent e) {

        if (e.getEntity() instanceof FallingBlock) {
            FallingBlock fallingBlock = (FallingBlock) e.getEntity();

            for (ActivePlayer activePlayer : ActivePlayer.getActivePlayers()) {
                if (activePlayer.getMove6LaunchedFallingBlocks().contains(fallingBlock)) {
                    e.setCancelled(true);
                    activePlayer.getMove6LaunchedFallingBlocks().remove(fallingBlock);
                } else if (activePlayer.getCometFallingBlocks().contains(fallingBlock) && activePlayer.getComet() != null) {
                    e.setCancelled(true);
                    activePlayer.getComet().endComet(fallingBlock.getLocation());
                    activePlayer.getCometFallingBlocks().clear();
                }

                if (activePlayer.isMove8active()) {
                    if (!activePlayer.getMove8FallingBlocks().isEmpty()) {
                        if (activePlayer.getMove8FallingBlocks().contains(fallingBlock)) {
                            e.setCancelled(true);
                            activePlayer.clearMove8FallingBlocks();
                            FlyingRock.end(activePlayer);
                        }
                    }
                }
            }
        }
    }
}
