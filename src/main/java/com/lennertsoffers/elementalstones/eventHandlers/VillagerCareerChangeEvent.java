package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.annotations.Event;
import com.lennertsoffers.elementalstones.customClasses.models.gameplay.ShamanVillager;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Event
public class VillagerCareerChangeEvent implements Listener {

    @EventHandler
    public void onVillagerCareerChange(org.bukkit.event.entity.VillagerCareerChangeEvent event) {
        if (event.getProfession() == Villager.Profession.FLETCHER) {
            new ShamanVillager(event.getEntity(), true);
        }
    }

}
