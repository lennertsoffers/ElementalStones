package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.annotations.Event;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireHellfireStorm;
import com.lennertsoffers.elementalstones.passives.PassiveHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Event
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

        PassiveHandler.cuteCreepers(event);
    }
}
