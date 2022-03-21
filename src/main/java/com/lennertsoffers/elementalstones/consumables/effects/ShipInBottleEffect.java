package com.lennertsoffers.elementalstones.consumables.effects;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.OffsetParticleLocation;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ShipInBottleEffect implements ConsumableEffect{
    @Override
    public void playEffect(Player player) {
        World world = player.getWorld();
        Location playerLocation = player.getLocation().add(0, 2, 0);
        Vector direction = playerLocation.getDirection().setY(0);
        Location shipwreck = world.locateNearestStructure(playerLocation, StructureType.SHIPWRECK, 10000, true);

        playerLocation.add(direction);
        direction.multiply(0.1);

        if (shipwreck != null) {
            player.sendMessage(ChatColor.YELLOW + "Shipwreck at:");
            player.sendMessage(ChatColor.YELLOW + "X: " + shipwreck.getBlockX());
            player.sendMessage(ChatColor.YELLOW + "Y: " + shipwreck.getBlockY());
            player.sendMessage(ChatColor.YELLOW + "Z: " + shipwreck.getBlockZ());

            new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        OffsetParticleLocation ops = new OffsetParticleLocation(playerLocation, 10);
                        world.spawnParticle(Particle.TOTEM, ops.getX(), ops.getY(), ops.getZ(), 0);
                    }

                    playerLocation.add(shipwreck);

                    amountOfTicks++;
                    if (amountOfTicks > 20) {
                        this.cancel();
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else {
            player.sendMessage(ChatColor.YELLOW + "No unexplored shipwrecks in 10000 blocks radius!");
        }
    }
}
