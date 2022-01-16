package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class FallingBlockToBlockEvent implements Listener {

    @EventHandler
    public void entityChangeBlock(EntityChangeBlockEvent e) {

        if (e.getEntity() instanceof FallingBlock) {
            FallingBlock fallingBlock = (FallingBlock) e.getEntity();

            for (ActivePlayer activePlayer : ActivePlayer.getActivePlayers()) {

                System.out.println(activePlayer.getMove6LaunchedFallingBlocks());

                if (activePlayer.getMove6LaunchedFallingBlocks().contains(fallingBlock)) {
                    e.setCancelled(true);
                    activePlayer.getMove6LaunchedFallingBlocks().remove(fallingBlock);
                }
                if (activePlayer.getMove6FallingBlocks().contains(fallingBlock)) {
                    e.setCancelled(true);
                    activePlayer.getMove6FallingBlocks().remove(fallingBlock);
                }
            }
        }
    }
}
