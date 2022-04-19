package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.annotations.Event;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import com.lennertsoffers.elementalstones.passives.PassiveHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

@Event
public class EntityDamageEvent implements Listener {

    @EventHandler
    public void onHit(org.bukkit.event.entity.EntityDamageEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());

            if (activePlayer == null) {
                return;
            }

            PassiveHandler.featherFalling(activePlayer, event);
            PassiveHandler.magmaMaster(activePlayer, event);
            PassiveHandler.friendlyFire(event, player);
            PassiveHandler.explosionResistance(event, player);

            if (event.getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALL) {
                if (activePlayer.isMove8active()) {
                    event.setCancelled(true);
                } else {
                    PassiveHandler.shockwave(activePlayer, event);
                }
            }

            else if (event.getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.LIGHTNING) {
                for (ItemStack itemStack : player.getInventory().getContents()) {
                    if (itemStack != null) {
                        if (itemStack.getType() == Material.GLASS_BOTTLE) {
                            itemStack = CraftItemManager.BOTTLE_OF_LIGHTNING.clone();
                            itemStack.setAmount(itemStack.getAmount());
                        }
                    }
                }
            }
        }
    }
}
