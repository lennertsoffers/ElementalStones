package com.lennertsoffers.elementalstones.moves.waterMoves.waterbending;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Bubblebeam extends Move {

    public Bubblebeam(ActivePlayer activePlayer) {
        super(activePlayer, "Bubblebeam", "water_stone", "waterbending_stone", 4);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();

        // Creation of beam
        new BukkitRunnable() {
            int amountOfTicks = 0;

            @Override
            public void run() {

                Location location = player.getLocation().add(0, 1, 0).add(player.getLocation().getDirection());

                for (int i = 0; i < amountOfTicks; i++) {
                    spawnParticles(location);
                    NearbyEntityTools.damageNearbyEntities(player, location, 1, 0.5, 0.5, 0.5);
                    location.add(player.getLocation().getDirection().multiply(0.4));
                }

                if (amountOfTicks > 30) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

        // Static beam
        new BukkitRunnable() {
            int amountOfTicks = 0;

            @Override
            public void run() {

                Location location = player.getLocation().add(0, 1, 0).add(player.getLocation().getDirection());

                for (int i = 0; i < 30; i++) {
                    spawnParticles(location);
                    NearbyEntityTools.damageNearbyEntities(player, location, 1, 0.5, 0.5, 0.5);
                    location.add(player.getLocation().getDirection().multiply(0.4));
                }

                if (amountOfTicks > 30) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 30L, 1L);

        // Removing beam
        new BukkitRunnable() {
            int amountOfTicks = 0;

            @Override
            public void run() {

                Location location = player.getLocation().add(0, 1, 0).add(player.getLocation().getDirection());
                location.add(player.getLocation().getDirection().multiply(0.4 * 30));
                for (int i = amountOfTicks; i < 30; i++) {
                    spawnParticles(location);
                    NearbyEntityTools.damageNearbyEntities(player, location, 1, 0.5, 0.5, 0.5);
                    location.add(player.getLocation().getDirection().multiply(-0.4));
                }

                if (amountOfTicks > 30) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 60L, 1L);

        this.setCooldown();
    }

    private void spawnParticles(Location location) {
        World world = location.getWorld();

        if (world != null) {
            for (int j = 0; j < 10; j++) {
                double locationX = location.getX() + StaticVariables.random.nextGaussian() / 10;
                double locationY = location.getY() + StaticVariables.random.nextGaussian() / 10;
                double locationZ = location.getZ() + StaticVariables.random.nextGaussian() / 10;
                world.spawnParticle(Particle.WATER_BUBBLE, locationX, locationY, locationZ, 0);
            }
        }
    }
}
