package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.ShamanVillager;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;

public class PlayerInteractAtEntityEvent implements Listener {

    @EventHandler
    public void onPlayerInteractAtEntity(org.bukkit.event.player.PlayerInteractAtEntityEvent event) {

        // Is Villager
        if (event.getRightClicked() instanceof Villager) {
            Villager villager = (Villager) event.getRightClicked();
            if (villager.getProfession().equals(Villager.Profession.FLETCHER)) {
                new ShamanVillager(villager);
            }



        }
    }
}
