package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.FireworkTools;
import com.lennertsoffers.elementalstones.moves.fireMoves.explosion.TripleThreat;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.LinkedList;

public class FireFireworks extends BukkitRunnable {

    private final ActivePlayer activePlayer;
    private final Player player;
    private final World world;
    private int amountOfTicks = 0;
    private final LinkedList<Firework> fireworks = new LinkedList<>();

    public FireFireworks(ActivePlayer activePlayer) {
        this.activePlayer = activePlayer;
        this.player = activePlayer.getPlayer();
        this.world = player.getWorld();

        getFireworkLocations().forEach(location -> {
            Firework firework = FireworkTools.setRandomMeta(((Firework) this.world.spawnEntity(location, EntityType.FIREWORK)), 127, null, 3, 3, 0, 1);
            firework.setShotAtAngle(true);
            this.fireworks.add(firework);
        });
    }

    @Override
    public void run() {
        LinkedList<Location> fireworkLocations = getFireworkLocations();
        for (int i = 0; i < fireworkLocations.size(); i++) {
            this.fireworks.get(i).teleport(fireworkLocations.get(i));
        }

        this.amountOfTicks++;
        if (this.amountOfTicks > 400) {
            this.shootFireworks();
            this.activePlayer.getMoveController().getMoves().forEach(move -> {
                if (move instanceof TripleThreat) {
                    move.setCooldown();
                }
            });
        }
    }

    public void shootFireworks() {
        this.cancel();

        Vector playerDirection = this.player.getLocation().getDirection().multiply(0.7);
        this.fireworks.get(0).setVelocity(playerDirection.clone().rotateAroundY(-Math.PI / 15));
        this.fireworks.get(1).setVelocity(playerDirection);
        this.fireworks.get(2).setVelocity(playerDirection.clone().rotateAroundY(Math.PI / 15));

        new BukkitRunnable() {
            @Override
            public void run() {
                fireworks.forEach(Firework::detonate);
            }
        }.runTaskLater(StaticVariables.plugin, 60L);
    }

    private LinkedList<Location> getFireworkLocations() {
        LinkedList<Location> fireworkLocations = new LinkedList<>();

        Location playerLocation = this.player.getLocation().add(0, 1.2, 0);
        Vector playerDirection = playerLocation.getDirection().setY(0);
        Vector playerDirectionPerpendicular = playerDirection.clone().rotateAroundY(Math.PI / 2).multiply(0.2);
        playerLocation.add(playerDirection).add(playerDirectionPerpendicular.clone().multiply(-1));

        fireworkLocations.add(playerLocation.clone());
        fireworkLocations.add(playerLocation.clone().add(playerDirectionPerpendicular));
        fireworkLocations.add(playerLocation.clone().add(playerDirectionPerpendicular.clone().multiply(2)));

        return fireworkLocations;
    }
}
