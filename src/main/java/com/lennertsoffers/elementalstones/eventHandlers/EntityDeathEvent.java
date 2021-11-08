package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.ElementalStones;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDeathEvent implements Listener {

    @EventHandler
    public void onEntityDeath(org.bukkit.event.entity.EntityDeathEvent event) {
        Location deathLocation = event.getEntity().getLocation();
        World world = deathLocation.getWorld();
        LivingEntity entity = event.getEntity();

        if (world != null) {
            // Baby Zombie Hide
            if (entity instanceof Zombie) {
                Zombie zombie = (Zombie) entity;
                if (!zombie.isAdult()) {
                    if (StaticVariables.random.nextInt(ElementalStones.configuration.getInt("drop_chance.baby_zombie_hide")) == 0) {
                        world.dropItemNaturally(deathLocation, CraftItemManager.BABY_ZOMBIE_HIDE);
                    }
                }
            }

            // Insect
            else if (entity instanceof Parrot || entity instanceof Phantom) {
                if (StaticVariables.random.nextInt(ElementalStones.configuration.getInt("drop_chance.insect")) == 0) {
                    world.dropItemNaturally(deathLocation, CraftItemManager.INSECT);
                }
            }

            // Bat
            else if (entity instanceof Bat) {
                if (StaticVariables.random.nextInt(ElementalStones.configuration.getInt("drop_chance.bat")) == 0) {
                    world.dropItemNaturally(deathLocation, CraftItemManager.BAT);
                }
            }

            // Golden Feather
            else if (entity instanceof Chicken) {
                if (StaticVariables.random.nextInt(ElementalStones.configuration.getInt("drop_chance.golden_feather")) == 0) {
                    world.dropItemNaturally(deathLocation, CraftItemManager.GOLDEN_FEATHER);
                }
            }

            // Soul Of Evoker
            else if (entity instanceof Evoker) {
                if (StaticVariables.random.nextInt(ElementalStones.configuration.getInt("drop_chance.soul_of_evoker")) == 0) {
                    world.dropItemNaturally(deathLocation, CraftItemManager.SOUL_OF_EVOKER);
                }
            }

            // Villager Blood
            else if (entity instanceof Villager) {
                if (StaticVariables.random.nextInt(ElementalStones.configuration.getInt("drop_chance.villager_blood")) == 0) {
                    world.dropItemNaturally(deathLocation, CraftItemManager.VILLAGER_BLOOD);
                }
            }
        }
    }
}
