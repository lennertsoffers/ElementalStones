package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.annotations.Event;
import com.lennertsoffers.elementalstones.moves.fireMoves.explosion.RandomRocket;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Event
public class FireworkExplodeEvent implements Listener {

    @EventHandler
    public void onFireworkExplode(org.bukkit.event.entity.FireworkExplodeEvent event) {
        Firework firework = event.getEntity();
        if (RandomRocket.randomRocketFireworks.contains(firework)) {
            RandomRocket.randomRocketFireworks.remove(firework);
            RandomRocket.effect(firework.getLocation());
        }
    }
}
