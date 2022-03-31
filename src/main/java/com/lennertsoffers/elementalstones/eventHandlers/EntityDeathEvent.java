package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.ElementalStones;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.Boss;
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
            Boss boss = Boss.getBoss(entity.getUniqueId());
            if (boss != null) {
                Boss.killedBoss(boss);
            }

            else {
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

                // Twig
                else if (entity instanceof Fox) {
                    if (StaticVariables.random.nextInt(ElementalStones.configuration.getInt("drop_chance.twig")) == 0) {
                        world.dropItemNaturally(deathLocation, CraftItemManager.TWIG);
                    }
                }

                // Soul Of Evoker
                else if (entity instanceof Evoker) {
                    if (StaticVariables.random.nextInt(ElementalStones.configuration.getInt("drop_chance.soul_of_evoker")) == 0) {
                        world.dropItemNaturally(deathLocation, CraftItemManager.SOUL_OF_EVOKER);
                    }
                }

                // Wandering Trader Blood
                else if (entity instanceof WanderingTrader) {
                    if (StaticVariables.random.nextInt(ElementalStones.configuration.getInt("drop_chance.blood_of_wandering_trader")) == 0) {
                        world.dropItemNaturally(deathLocation, CraftItemManager.BLOOD_OF_WANDERING_TRADER);
                    }
                }

                // Stinger
                else if (entity instanceof Bee) {
                    if (StaticVariables.random.nextInt(ElementalStones.configuration.getInt("drop_chance.stinger")) == 0) {
                        world.dropItemNaturally(deathLocation, CraftItemManager.STINGER);
                    }
                }

                // Hoglin tusk
                else if (entity instanceof Hoglin) {
                    if (StaticVariables.random.nextInt(ElementalStones.configuration.getInt("drop_chance.hoglin_tusk")) == 0) {
                        world.dropItemNaturally(deathLocation, CraftItemManager.HOGLIN_TUSK);
                    }
                }

                // Finn
                else if (entity instanceof Dolphin) {
                    if (StaticVariables.random.nextInt(ElementalStones.configuration.getInt("drop_chance.finn")) == 0) {
                        world.dropItemNaturally(deathLocation, CraftItemManager.FINN);
                    }
                }

                // Player
                else if (entity instanceof Player) {
                    ActivePlayer activePlayer = ActivePlayer.getActivePlayer(entity.getUniqueId());
                    if (activePlayer != null) {
                        activePlayer.death();
                    }
                }
            }
        }
    }
}
