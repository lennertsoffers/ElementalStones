package com.lennertsoffers.elementalstones.consumables.effects;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BroomEffect implements ConsumableEffect {

    @Override
    public void playEffect(Player player) {
        World world = player.getWorld();
        Location location = player.getLocation();
        PotionEffect levitation = new PotionEffect(PotionEffectType.LEVITATION, 12000, 1, true, true, true);

        for (int i = 0; i < 20; i++) {
            double locationX = location.getX() + StaticVariables.random.nextGaussian() * 10;
            double locationZ = location.getZ() + StaticVariables.random.nextGaussian() * 10;

            Location spawnLocation = CheckLocationTools.getClosestAirBlockLocation(new Location(world, locationX, location.getY(), locationZ));

            if (spawnLocation != null) {
                Cat cat = (Cat) world.spawnEntity(spawnLocation, EntityType.CAT, true);
                cat.setOwner(player);

                if (StaticVariables.random.nextBoolean()) {
                    cat.addPotionEffect(levitation);
                }
            }
        }
    }
}
