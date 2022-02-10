package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.LinkedList;

public class FireHellfireStorm extends BukkitRunnable {

    private int amountOfTicks = 0;

    private final Player player;
    private final World world;
    private final long resetTime;

    public static LinkedList<Fireball> fireballs = new LinkedList<>();

    public FireHellfireStorm(Player player) {
        this.player = player;
        this.world = player.getWorld();
        this.resetTime = this.world.getTime();
        this.world.setTime(18000);
    }

    @Override
    public void run() {
        for (int i = 0; i < 40; i++) {
            this.strikeLightning();
        }

        for (int i = 0; i < 4; i++) {
            this.spawnFireball();
        }

        this.amountOfTicks += 5;
        if (this.amountOfTicks > 200) {
            this.cancel();
            this.world.setTime(this.resetTime);
        }
    }

    private void strikeLightning() {
        Block block = this.getTargetBlock();
        this.world.strikeLightning(block.getLocation());
    }

    private void spawnFireball() {
        Block block = this.getTargetBlock();
        Location endLocation = block.getLocation();
        Location startLocation = new Location(
                this.world,
                endLocation.getBlockX() + StaticVariables.random.nextGaussian() * 5,
                endLocation.getBlockY() + StaticVariables.random.nextGaussian() * 5 + 100,
                endLocation.getBlockZ() + StaticVariables.random.nextGaussian() * 5
        );

        Vector direction = new Vector(endLocation.getX() - startLocation.getX(), endLocation.getY() - startLocation.getY(), endLocation.getZ() - startLocation.getZ());

        Fireball fireball = (Fireball) this.world.spawnEntity(startLocation, EntityType.FIREBALL);
        fireball.setDirection(direction);
        fireballs.add(fireball);
    }

    private Block getTargetBlock() {
        Location center = this.player.getLocation();
        int centerX = center.getBlockX();
        int centerZ = center.getBlockZ();

        int x = (int) (StaticVariables.random.nextGaussian() * 30);
        int z = (int) (StaticVariables.random.nextGaussian() * 30);
        if (x < 10 && x >= 0 && z < 10 && z >= 0) {
            x += 10;
            z += 10;
        } else if (x < 10 && x >= 0 && z > -10 && z < 0) {
            x += 10;
            z -= 10;
        } else if (x > -10 && x < 0 && z < 10 && z >= 0) {
            x -= 10;
            z += 10;
        } else if (x > -10 && x < 0 && z > -10 && z < 0) {
            x -= 10;
            z -= 10;
        }

        x += centerX;
        z += centerZ;

        return this.world.getHighestBlockAt(x, z);
    }
}
