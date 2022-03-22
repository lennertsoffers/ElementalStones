package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.consumables.ConsumableHandler;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerConsumeEvent implements Listener {

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();

        if (itemStack.isSimilar(CraftItemManager.FINN_SOUP)) {
            ConsumableHandler.finnSoup(player);
        } else if (itemStack.isSimilar(CraftItemManager.ROTTEN_APPLE)) {
            ConsumableHandler.rottenApple(player);
        } else if (itemStack.isSimilar(CraftItemManager.POISONED_APPLE)) {
            ConsumableHandler.poisonedApple(player);
        } else if (itemStack.isSimilar(CraftItemManager.MYSTERY_POTION)) {
            ConsumableHandler.mysteryPotion(player);
        } else if (itemStack.isSimilar(CraftItemManager.GINGERBREAD_MAN)) {
            ConsumableHandler.gingerbreadMan(player);
        }
    }
}
