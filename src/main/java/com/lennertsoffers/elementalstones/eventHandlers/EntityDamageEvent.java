package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.stones.windStone.AirbendingStone;
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
            AirbendingStone.passive1(activePlayer, event);
//            if (event.getDamage() >= player.getHealth()) {
//                event.setCancelled(true);
//                DefenseStone.move8(player);
//            }
//            LavaStone.passive2(activePlayer, event);
//            LavaStone.move8(activePlayer, event);
        }
    }
}
