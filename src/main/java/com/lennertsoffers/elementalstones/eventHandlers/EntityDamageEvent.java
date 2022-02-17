package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.passives.PassiveHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDamageEvent implements Listener {

    @EventHandler
    public void onHit(org.bukkit.event.entity.EntityDamageEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());

            if (activePlayer == null) {
                return;
            }

            PassiveHandler.featherFalling(activePlayer, event);
            PassiveHandler.magmaMaster(activePlayer, event);
            PassiveHandler.friendlyFire(event, player);
            PassiveHandler.explosionResistance(event, player);

            if (event.getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALL) {
                if (activePlayer.isMove8active()) {
                    event.setCancelled(true);
                } else {
                    PassiveHandler.shockwave(activePlayer, event);
                }
            }
        }
    }
}
