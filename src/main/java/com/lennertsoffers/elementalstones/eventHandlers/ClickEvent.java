package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.ElementalStones;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.Boss;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;


public class ClickEvent implements Listener {

    protected final ElementalStones plugin;

    public ClickEvent(ElementalStones plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getHand() == EquipmentSlot.HAND) {

            ItemStack originalItemInMainHand = player.getInventory().getItemInMainHand().clone();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand().clone();
            itemInMainHand.setAmount(1);

            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (ItemStones.allStones.contains(player.getInventory().getItemInMainHand()) && player.getInventory().getHeldItemSlot() == 8) {
                    ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());
                    if (activePlayer != null) {
                        event.setCancelled(true);
                        activePlayer.toggleActive();
                    }
                }
            } else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (itemInMainHand.isSimilar(CraftItemManager.VOODOO_DOLL)) {
                    event.setCancelled(true);
                    new Boss(player, originalItemInMainHand);
                }
            }
        }
    }
}





















