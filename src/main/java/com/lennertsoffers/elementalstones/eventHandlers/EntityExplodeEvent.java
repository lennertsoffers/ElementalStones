package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireHellfireStorm;
import com.lennertsoffers.elementalstones.stones.fireStone.ExplosionStone;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityExplodeEvent implements Listener {

    @EventHandler
    public void onEntityExplode(org.bukkit.event.entity.EntityExplodeEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Fireball) {
            Fireball fireball = (Fireball) entity;

            if (FireHellfireStorm.fireballs.contains(fireball)) {
                event.setCancelled(true);
                FireHellfireStorm.fireballs.remove(fireball);

                entity.getWorld().createExplosion(fireball.getLocation(), 3, true, false);
            }
        }

        ExplosionStone.passive2(event);
    }
}
