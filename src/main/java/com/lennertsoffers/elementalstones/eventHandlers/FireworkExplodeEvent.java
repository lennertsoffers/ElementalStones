package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.stones.fireStone.ExplosionStone;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FireworkExplodeEvent implements Listener {

    @EventHandler
    public void onFireworkExplode(org.bukkit.event.entity.FireworkExplodeEvent event) {
        Firework firework = event.getEntity();
        if (ExplosionStone.move7Fireworks.contains(firework)) {
            ExplosionStone.move7Fireworks.remove(firework);
            ExplosionStone.move7Effect(firework.getLocation());
        }
    }
}
