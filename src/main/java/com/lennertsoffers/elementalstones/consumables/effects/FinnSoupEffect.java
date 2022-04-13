package com.lennertsoffers.elementalstones.consumables.effects;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.utils.OffsetParticleLocation;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class FinnSoupEffect implements ConsumableEffect {

    @Override
    public void playEffect(Player player) {
        World world = player.getWorld();
        Location location = player.getLocation().add(0, 1, 0);

        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 12000, 3, true, true, true));

        new BukkitRunnable() {
            int amountOfTicks = 0;

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    OffsetParticleLocation opl = new OffsetParticleLocation(location, 25 + amountOfTicks);
                    world.spawnParticle(Particle.ELECTRIC_SPARK, opl.getLocation(), 0);
                }

                amountOfTicks++;
                if (amountOfTicks > 10) {
                    this.cancel();
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }
}
