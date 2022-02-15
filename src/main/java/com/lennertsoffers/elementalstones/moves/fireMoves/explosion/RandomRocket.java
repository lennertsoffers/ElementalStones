package com.lennertsoffers.elementalstones.moves.fireMoves.explosion;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.Effect;
import com.lennertsoffers.elementalstones.customClasses.tools.FireworkTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashSet;

public class RandomRocket extends Move {

    public static HashSet<Firework> randomRocketFireworks = new HashSet<>();

    public RandomRocket(ActivePlayer activePlayer) {
        super(activePlayer, "Random Rocket", "fire_stone", "explosion_stone", 7);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();
        World world = player.getWorld();
        Location location = player.getLocation();
        Vector direction = location.getDirection();
        location.add(direction).add(0, 1.4, 0);

        Firework firework = FireworkTools.setRandomMeta((Firework) world.spawnEntity(location, EntityType.FIREWORK), 127, FireworkEffect.Type.BALL, 3, 3, 0, 1);
        firework.setShotAtAngle(true);
        firework.setVelocity(direction);
        randomRocketFireworks.add(firework);

        new BukkitRunnable() {
            @Override
            public void run() {
                firework.detonate();
            }
        }.runTaskLater(StaticVariables.plugin, 20L);
    }

    public static void effect(Location location) {
        World world = location.getWorld();

        if (world != null) {
            int totalChance = Arrays.stream(Effect.values()).mapToInt(Effect::getChance).sum();
            int random = StaticVariables.random.nextInt(totalChance);

            int bottomBoundary = 0;

            for (int i = 0; i < Effect.values().length; i++) {
                int topBoundary = bottomBoundary + Effect.values()[i].getChance();

                if (random >= bottomBoundary && random < topBoundary) {
                    Effect.values()[i].playEffect(location);
                }

                bottomBoundary = topBoundary;
            }
        }
    }
}
