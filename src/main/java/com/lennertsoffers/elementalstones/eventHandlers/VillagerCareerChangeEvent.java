package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.ShamanVillager;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VillagerCareerChangeEvent implements Listener {

    @EventHandler
    public void onVillagerCareerChange(org.bukkit.event.entity.VillagerCareerChangeEvent event) {
        if (event.getProfession() != Villager.Profession.FLETCHER) {
            new ShamanVillager(event.getEntity());
        }
    }

}
