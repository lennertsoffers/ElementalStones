package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.stones.windStone.AirbendingStone;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityToggleGlideEvent implements Listener {

    @EventHandler
    public void onEntityToggleGlide(org.bukkit.event.entity.EntityToggleGlideEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());
            if (activePlayer != null) {
                AirbendingStone.passive2(activePlayer, event);
            }
        }
    }

}
