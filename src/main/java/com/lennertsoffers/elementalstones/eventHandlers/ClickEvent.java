package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.ElementalStones;
import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.items.ItemStones;
import net.minecraft.server.PacketPlayOutWorldParticles;
import net.minecraft.server.Particles;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


import java.lang.reflect.InvocationTargetException;

public class ClickEvent implements Listener {

//    protected final ElementalStones plugin;
//
//    public ClickEvent(ElementalStones plugin) {
//        this.plugin = plugin;
//    }

//    @EventHandler
//    public void onClick(PlayerInteractEvent event) throws InvocationTargetException {
//        Player player = event.getPlayer();
//        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
//            if (ItemStones.allStones.contains(player.getInventory().getItemInMainHand())) {
//                ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());
//                if (activePlayer != null) {
//                    if (activePlayer.isActive()) {
//                        activePlayer.setActive(false);
//                        player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You left move mode!");
//                    } else {
//                        activePlayer.setActive(true);
//                        player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You are in move mode!");
//                    }
//                }
//            }
//        }
//
//        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
//            Location location = player.getLocation().add(player.getLocation().getDirection()).add(0, 1, 0);
//            ProtocolManager manager = ProtocolLibrary.getProtocolManager();
//            PacketContainer packet = manager.createPacket(PacketType.Play.Server.WORLD_PARTICLES);
//            packet.getModifier().writeDefaults();
//            packet.getParticles().write(0, EnumWrappers.Particle.FLAME);
//            packet.getFloat().write(0, (float) location.getX());
//            packet.getFloat().write(1, (float) location.getY());
//            packet.getFloat().write(2, (float) location.getZ());
////            packet.getFloat().write(0, 0F);
////            packet.getFloat().write(1, 0F);
////            packet.getFloat().write(2, 0F);
////            packet.getFloat().write(3, 0F);
////            packet.getBooleans().write(0, false);
//            Bukkit.getOnlinePlayers().fo
//            try {
//                manager.sendServerPacket(player, packet);
//            } catch (InvocationTargetException exception) {
//                System.out.println(exception.getMessage());
//            }
//            (player, new PacketPlayOutWorldParticles(Particles.FLAME, false, location.getX(), location.getY(), location.getZ(), 0, 0, 0, 0, 0));
//        }
//    }
}





















