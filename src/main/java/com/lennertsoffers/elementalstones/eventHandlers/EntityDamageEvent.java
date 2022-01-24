package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.stones.earthStone.EarthbendingStone;
import com.lennertsoffers.elementalstones.stones.earthStone.LavaStone;
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
            LavaStone.passive2(activePlayer, event);

            if (event.getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALL) {
                if (activePlayer.isMove8active()) {
                    event.setCancelled(true);
                } else {
                    EarthbendingStone.passive(activePlayer, event);
                }
            }

//            if (event.getDamage() >= player.getHealth()) {
//                event.setCancelled(true);
//                DefenseStone.move8(player);
//            }
//            LavaStone.move8(activePlayer, event);
        }
    }
}
