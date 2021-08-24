package com.lennertsoffers.elementalstones.eventHandlers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.lennertsoffers.elementalstones.ElementalStones;
import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.items.ItemStones;
import com.lennertsoffers.elementalstones.stones.earthStone.EarthbendingStone;
import com.lennertsoffers.elementalstones.stones.windStone.AgilityStone;
import net.minecraft.server.PacketPlayOutWorldParticles;
import net.minecraft.server.ParticleType;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class ClickEvent implements Listener {

    protected final ElementalStones plugin;

    public ClickEvent(ElementalStones plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) throws InvocationTargetException {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (ItemStones.allStones.contains(player.getInventory().getItemInMainHand())) {
                ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());
                if (activePlayer != null) {
                    if (activePlayer.isActive()) {
                        activePlayer.setActive(false);
                        player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You left move mode!");
                    } else {
                        activePlayer.setActive(true);
                        player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You are in move mode!");
                    }
                }
            }
        }

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
//            Location location = player.getLocation().add(player.getLocation().getDirection()).add(0, 1, 0);
//            ProtocolManager manager = ProtocolLibrary.getProtocolManager();
//            PacketContainer packet = manager.createPacket(PacketType.Play.Server.WORLD_PARTICLES);
//            packet.getIntegers().write(0, 77);
////            packet.getIntegers().write(1, 10);
//            packet.getDoubles().write(0, location.getX());
//            packet.getDoubles().write(1, location.getY());
//            packet.getDoubles().write(2, location.getZ());
//            packet.getFloat().write(0, 0F);
//            packet.getFloat().write(1, 0F);
//            packet.getFloat().write(2, 0F);
//            packet.getFloat().write(3, 0F);
//            packet.getBooleans().write(0, false);
//            try {
//                manager.sendServerPacket(player, packet);
//            } catch (InvocationTargetException exception) {
//                System.out.println(exception.getMessage());
//            }
        }
    }
}





















