package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.ShamanTradeItem;
import com.lennertsoffers.elementalstones.customClasses.models.ShamanVillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.*;

public class PlayerInteractAtEntityEvent implements Listener {

    @EventHandler
    public void onPlayerInteractAtEntity(org.bukkit.event.player.PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());

        if (activePlayer != null) {
            if (event.getRightClicked() instanceof Villager) {
                Villager villager = (Villager) event.getRightClicked();
                if (villager.getProfession() == Villager.Profession.FLETCHER) {
                    if (ShamanVillager.isShamanVillager(villager)) {
                        ShamanVillager shamanVillager = ShamanVillager.getShamanVillager(villager.getUniqueId());
                        if (shamanVillager != null) {
                            ItemStack clickItemStackType = player.getInventory().getItemInMainHand().clone();
                            clickItemStackType.setAmount(1);
                            for (ShamanTradeItem shamanTradeItem : ShamanTradeItem.getShamanXpItems()) {
                                if (clickItemStackType.isSimilar(shamanTradeItem.getItem())) {
                                    activePlayer.setCloseTradingInventories(true);
                                    ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
                                    ItemStack itemToGive = itemInMainHand.clone();
                                    itemToGive.setAmount(1);
                                    if (shamanVillager.acceptItem(itemToGive)) {
                                        itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);
                                        player.getInventory().setItemInMainHand(itemInMainHand);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
