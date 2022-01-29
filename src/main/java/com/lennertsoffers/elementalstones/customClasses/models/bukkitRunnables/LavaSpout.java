package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LavaSpout extends BukkitRunnable {
    private int amountOfTicks = 0;
    private int height = 0;
    private int heightAddition;
    private final Location middlePoint;
    private final ActivePlayer activePlayer;
    private final Player player;
    private final World world;
    private final List<Location> placedLavaLocations = new ArrayList<>();

    public LavaSpout(ActivePlayer activePlayer, Location middlePoint) {
        this.activePlayer = activePlayer;
        this.player = activePlayer.getPlayer();
        this.world = player.getWorld();
        this.middlePoint = getLowestHeight(middlePoint);

        this.heightAddition = 1;
    }

    @Override
    public void run() {
        Location center = this.middlePoint.clone();

        for (int i = 0; i < height + 1; i++) {
            placeLayer(Material.LAVA, true, getLocations(center));
            placeLayer(Material.LAVA, false, getLocationsAround(center));
            center.add(0, 1, 0);
        }
        placeLayer(Material.AIR, true, getLocations(center));


        amountOfTicks++;
        height += heightAddition;
        if (this.amountOfTicks == 20) {
            heightAddition = 0;
        } else if (this.amountOfTicks == 60) {
            this.heightAddition = -1;
        } else if (this.amountOfTicks == 82) {
            this.cancel();
            placedLavaLocations.clear();
        }
    }

    private void placeLayer(Material material, boolean innerLayer, List<Location> locations) {
        Vector velocity = new Vector(StaticVariables.random.nextGaussian() / 5, 1, StaticVariables.random.nextGaussian() / 5);

        locations.forEach(location -> {
            Block block = world.getBlockAt(location);

            Material blockMaterial = block.getType();
            if (blockMaterial == Material.AIR || placedLavaLocations.contains(location)) {
                block.setType(Material.AIR);
                placedLavaLocations.add(location);

                if (innerLayer) {
                    block.setType(material);
                    NearbyEntityTools.damageNearbyEntities(this.player, location, 0, 1, 1, 1, velocity);
                }
            }
        });
    }

    private Location getLowestHeight(Location center) {
        int lowestHeight = center.getBlockY() + 1;

        for (Location loc : getLocations(center)) {
            Location location = CheckLocationTools.getClosestAirBlockLocation(loc);

            if (location != null) {
                int height = location.getBlockY();

                if (height < lowestHeight) {
                    lowestHeight = height;
                }
            }
        }

        center.setY(lowestHeight);
        return center;
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

    private List<Location> getLocationsAround(Location center) {
        return Arrays.asList(
                center.clone().add(2, 0, 0),
                center.clone().add(-2, 0, 0),
                center.clone().add(0, 0, 2),
                center.clone().add(0, 0, -2),
                center.clone().add(1, 0, 1),
                center.clone().add(-1, 0, 1),
                center.clone().add(1, 0, -1),
                center.clone().add(-1, 0, -1)
        );
    }
}
