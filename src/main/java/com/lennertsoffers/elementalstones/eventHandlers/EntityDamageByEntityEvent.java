package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.moves.airMoves.airbending.WindCloak;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDamageByEntityEvent implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(org.bukkit.event.entity.EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            ActivePlayer activePlayer = ActivePlayer.getActivePlayer(event.getEntity().getUniqueId());
            if (activePlayer != null) {
                WindCloak.windCloakKnockback(activePlayer, event);

                if (event.getDamager() instanceof Arrow) {
                    event.setCancelled(true);
                }
            }
        }
    }

}
