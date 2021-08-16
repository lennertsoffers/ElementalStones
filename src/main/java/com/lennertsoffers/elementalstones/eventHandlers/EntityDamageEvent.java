package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.stones.earthStone.DefenseStone;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDamageEvent implements Listener {

    @EventHandler
    public void onHit(org.bukkit.event.entity.EntityDamageEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if (event.getDamage() >= player.getHealth()) {
                event.setCancelled(true);
                DefenseStone.move8(player);
            }
        }
    }
}
