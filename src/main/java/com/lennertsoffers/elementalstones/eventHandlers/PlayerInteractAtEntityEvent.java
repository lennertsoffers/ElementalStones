package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.consumables.ConsumableHandler;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.ShamanTradeItem;
import com.lennertsoffers.elementalstones.customClasses.models.ShamanVillager;
import com.lennertsoffers.elementalstones.customClasses.tools.ItemTools;
import org.bukkit.entity.Entity;
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
        Entity entity = event.getRightClicked();

        ConsumableHandler.bundleOfHerbs(player, entity);

        if (activePlayer != null) {
            if (entity instanceof Villager) {
                Villager villager = (Villager) event.getRightClicked();

                if (villager.getProfession() == Villager.Profession.FLETCHER) {
                    ShamanVillager shamanVillager = new ShamanVillager(villager, false);
                    ItemStack clickItemStackType = ItemTools.getSingleFromStack(player.getInventory().getItemInMainHand());

                    for (ShamanTradeItem shamanTradeItem : ShamanTradeItem.getShamanXpItems()) {
                        if (clickItemStackType.isSimilar(shamanTradeItem.getItem())) {
                            activePlayer.setCloseTradingInventories(true);

                            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

                            if (shamanVillager.acceptItem(clickItemStackType)) {
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
