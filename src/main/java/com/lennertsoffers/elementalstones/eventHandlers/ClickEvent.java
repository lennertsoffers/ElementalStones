package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.consumables.ConsumableHandler;
import com.lennertsoffers.elementalstones.customClasses.annotations.Event;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

@Event
public class ClickEvent implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());

        if (activePlayer != null) {
            if (event.getHand() == EquipmentSlot.HAND) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    ConsumableHandler.voodooDoll(player);
                    ConsumableHandler.shipInBottle(player);
                    ConsumableHandler.warHorn(player);
                    ConsumableHandler.antidote(player);
                    ConsumableHandler.bottleOfLightning(player);
                    ConsumableHandler.palantir(player);
                    ConsumableHandler.carnivorousPlant(player);
                    ConsumableHandler.broom(player);
                    ConsumableHandler.poisonousDart(player);

                    activePlayer.getPalantirSpectatorHandler().teleportToPlayer();

                    if (ItemStones.allStones.contains(player.getInventory().getItemInMainHand()) && player.getInventory().getHeldItemSlot() == 8) {
                        event.setCancelled(true);
                        activePlayer.toggleActive();
                    }
                }
            }
        }
    }
}





















