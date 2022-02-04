package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FireFlamethrower extends BukkitRunnable {

    private final Player player;
    private final World world;
    private double amountOfTicks = -1.0;

    public FireFlamethrower(Player player, World world) {
        this.player = player;
        this.world = world;
    }

    @Override
    public void run() {
        Location startLocation = this.player.getLocation().add(0, 1, 0);
        Vector direction = startLocation.getDirection();
        startLocation.add(direction);
        double distance = this.calculateDistance();

        for (double i = 0.2; i < distance; i += 0.2) {
            Location center = startLocation.clone().add(direction.clone().multiply(i));

            spawnParticleSphere(center);

            Block block = center.getBlock();
            Block blockAbove = block.getRelative(BlockFace.UP);
            Material material = block.getType();
            Material materialAbove = blockAbove.getType();
            if (material != Material.AIR &&
                !CheckLocationTools.isFoliage(material) &&
                material != Material.WATER &&
                material != Material.LAVA &&
                (materialAbove == Material.AIR || CheckLocationTools.isFoliage(materialAbove))
            ) {
                blockAbove.setType(Material.FIRE);
            }


            if (Math.round(amountOfTicks * 100) % 5 == 0) {
                NearbyEntityTools.damageNearbyEntities(this.player, center, 2, 0.2, 0.2, 0.2, livingEntity -> livingEntity.setFireTicks(livingEntity.getFireTicks() + 40));
            }
        }

        this.amountOfTicks += 0.01;
        if (this.amountOfTicks >= 1) {
            this.cancel();
        }
    }

    private void spawnParticleSphere(Location center) {
        int offset = 10;
        int slowness = 10;

        Vector direction = this.player.getLocation().getDirection();
        double directionX = direction.getX() / slowness;
        double directionY = direction.getY() / slowness;
        double directionZ = direction.getZ() / slowness;

        for (int i = 0; i < 20; i++) {
            double locationX = center.getX() + StaticVariables.random.nextGaussian() / offset;
            double locationY = center.getY() + StaticVariables.random.nextGaussian() / offset;
            double locationZ = center.getZ() + StaticVariables.random.nextGaussian() / offset;
            this.world.spawnParticle(Particle.FLAME, locationX, locationY, locationZ, 0, directionX, directionY, directionZ);
        }
    }

    private double calculateDistance() {
        return 6 * Math.cos((Math.PI / 2) * Math.pow(this.amountOfTicks, 2));
    }

}
