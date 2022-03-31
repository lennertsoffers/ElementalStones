package com.lennertsoffers.elementalstones.consumables.effects;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class PoisonousDartEffect implements ConsumableEffect {

    private final ArrayList<Location> spawnLocations = new ArrayList<>();

    @Override
    public void playEffect(Player player) {
        World world = player.getWorld();
        PotionType potionType = this.randomPotionType();

        new BukkitRunnable() {
            int amountOfTicks = 0;

            @Override
            public void run() {
                Location center = player.getLocation();
                Vector direction = center.getDirection();
                Vector directionNoY = direction.clone().setY(0);

                spawnLocations.add(center.clone().add(directionNoY.clone().rotateAroundY(Math.toRadians(-90))).add(directionNoY).add(0, 2.5, 0));
                spawnLocations.add(center.clone().add(directionNoY.clone().rotateAroundY(Math.toRadians(90))).add(directionNoY).add(0, 2.5, 0));

                spawnLocations.forEach(spawnLocation -> {
                    Arrow arrow = world.spawnArrow(spawnLocation, direction, 0.8F, 0);
                    arrow.setBasePotionData(new PotionData(potionType));
                });

                spawnLocations.clear();

                amountOfTicks++;
                if (amountOfTicks > 20) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }

    private PotionType randomPotionType() {
        int pick = StaticVariables.random.nextInt(PotionType.values().length);
        return PotionType.values()[pick];
    }
}
