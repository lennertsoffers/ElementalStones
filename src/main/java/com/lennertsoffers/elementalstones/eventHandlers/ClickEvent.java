package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.ElementalStones;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.Boss;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.WarHornRaid;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.raid.RaidSpawnWaveEvent;
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
        World world = player.getWorld();
        Location location = player.getLocation();

        if (event.getHand() == EquipmentSlot.HAND) {

            ItemStack originalItemInMainHand = player.getInventory().getItemInMainHand().clone();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand().clone();
            itemInMainHand.setAmount(1);

            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                // Voodoo Doll
                if (itemInMainHand.isSimilar(CraftItemManager.VOODOO_DOLL)) {
                    event.setCancelled(true);
                    new Boss(player, originalItemInMainHand);
                }

                if (ItemStones.allStones.contains(player.getInventory().getItemInMainHand()) && player.getInventory().getHeldItemSlot() == 8) {
                    ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());
                    if (activePlayer != null) {
                        event.setCancelled(true);
                        activePlayer.toggleActive();
                    }
                }
            }
        }
    }
}





















