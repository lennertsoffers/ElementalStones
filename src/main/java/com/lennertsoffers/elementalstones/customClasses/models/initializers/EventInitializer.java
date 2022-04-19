package com.lennertsoffers.elementalstones.customClasses.models.initializers;

import com.lennertsoffers.elementalstones.customClasses.annotations.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.Set;

public class EventInitializer extends Initializer {

    public EventInitializer(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void initialize() {
        Set<Class<?>> eventClasses = new Reflections("com.lennertsoffers.elementalstones.eventHandlers").getTypesAnnotatedWith(Event.class);

        try {
            for (Class<?> eventClass : eventClasses) {
                Object eventInstance = eventClass.newInstance();

                if (eventInstance instanceof Listener) {
                    this.getPlugin().getServer().getPluginManager().registerEvents((Listener) eventInstance, this.getPlugin());
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
