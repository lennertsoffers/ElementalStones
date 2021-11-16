package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.models.ShamanVillager;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VillagerCareerChangeEvent implements Listener {

    @EventHandler
    public void onVillagerCareerChange(org.bukkit.event.entity.VillagerCareerChangeEvent event) {
        Villager villager = event.getEntity();
        if (event.getProfession() == Villager.Profession.FLETCHER) {
            if (!ShamanVillager.isShamanVillager(villager)) {
                new ShamanVillager(event.getEntity());
            }
        }
    }

}
