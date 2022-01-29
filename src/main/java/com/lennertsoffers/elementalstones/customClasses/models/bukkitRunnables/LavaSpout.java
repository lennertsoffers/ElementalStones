package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LavaSpout extends BukkitRunnable {
    private int amountOfTicks = 0;
    private int height = 0;
    private int heightAddition;
    private Material material;
    private final Location middlePoint;
    private final Player player;
    private final World world;
    private final List<Location> placedLavaLocations = new ArrayList<>();

    public LavaSpout(Player player, Location middlePoint) {
        this.player = player;
        this.world = player.getWorld();
        this.middlePoint = getLowestHeight(middlePoint);

        this.heightAddition = 1;
        this.material = Material.LAVA;
    }

    @Override
    public void run() {
        Location center = this.middlePoint.clone();

        for (int i = 0; i < height + 1; i++) {
            placeLayer(center, this.material);
            center.add(0, 1, 0);
        }

        amountOfTicks++;
        height += heightAddition;
        if (this.amountOfTicks == 20) {
            heightAddition = 0;
        } else if (this.amountOfTicks == 60) {
            this.heightAddition = -1;
            this.material = Material.AIR;
        } else if (this.amountOfTicks == 80) {
            this.cancel();
            placedLavaLocations.clear();
        }
    }

    private void placeLayer(Location center, Material material) {
        getLocations(center).forEach(location -> {
            Block block = world.getBlockAt(location);

            Material blockMaterial = block.getType();
            if (blockMaterial == Material.AIR || placedLavaLocations.contains(location)) {
                block.setType(Material.AIR);
                block.setType(material);
                placedLavaLocations.add(location);
            }
        });
    }

    private Location getLowestHeight(Location center) {
        int lowestHeight = middlePoint.getBlockY() + 1;

        for (Location loc : getLocations(center)) {
            Location location = CheckLocationTools.getClosestAirBlockLocation(loc);

            if (location != null) {
                int height = location.getBlockY();

                if (height < lowestHeight) {
                    lowestHeight = height;
                }
            }
        }

        middlePoint.setY(lowestHeight);
        return middlePoint;
    }

    private List<Location> getLocations(Location center) {
        return Arrays.asList(
                center.clone(),
                center.clone().add(0, 0, 1),
                center.clone().add(0, 0, -1),
                center.clone().add(1, 0, 0),
                center.clone().add(-1, 0, 0)
        );
    }
}
