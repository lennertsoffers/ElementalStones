package com.lennertsoffers.elementalstones.moves.earthMoves.basic;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class Pushback extends Move {

    /**
     * <b>MOVE 3: Pushback</b>
     * <p>
     *     Launches a wall that pushes away entities
     *     <ul>
     *         <li><b>Range:</b> 40</li>
     *         <li><b>Knockback:</b> 1.5</li>
     *         <li><b>Knockup:</b> 0.2</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     */
    public Pushback(ActivePlayer activePlayer) {
        super(activePlayer, "Pushback", "earth_stone", "default", 3);
    }

    @Override
    public void useMove() {
        World world = this.getPlayer().getWorld();
        Location location = this.getPlayer().getLocation().add(this.getPlayer().getLocation().getDirection().multiply(2));
        Vector direction = location.getDirection().setY(0);
        Vector directionLeft = direction.clone().rotateAroundY(90);
        Vector directionRight = direction.clone().rotateAroundY(-90);
        Material material = world.getBlockAt(this.getPlayer().getLocation().add(0, -1, 0)).getType();

        if (!CheckLocationTools.isSolidBlock(material)) {
            material = Material.STONE;
        }
        Material finalMaterial = material;
        ArrayList<Location> toChangeLocations = new ArrayList<>();

        new BukkitRunnable() {
            int amountOfTicks = 0;
            @Override
            public void run() {

                for (Location l : toChangeLocations) {
                    world.getBlockAt(l).setType(Material.AIR);
                }
                toChangeLocations.clear();

                ArrayList<Location> setBlockLocations = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    Location locationLeft = CheckLocationTools.getClosestAirBlockLocation(location.clone().add(directionLeft));
                    Location locationMiddle  = CheckLocationTools.getClosestAirBlockLocation(location);
                    Location locationRight = CheckLocationTools.getClosestAirBlockLocation(location.clone().add(directionRight));

                    if (locationLeft != null) {
                        locationLeft.add(0, i, 0);
                        setBlockLocations.add(locationLeft);
                    }
                    if (locationMiddle != null) {
                        locationMiddle.add(0, i, 0);
                        setBlockLocations.add(locationMiddle);
                    }
                    if (locationRight != null) {
                        locationRight.add(0, i, 0);
                        setBlockLocations.add(locationRight);
                    }
                }

                for (Location l : setBlockLocations) {
                    world.getBlockAt(l).setType(finalMaterial);
                    toChangeLocations.add(l);
                    for (Entity entity : world.getNearbyEntities(l, 1, 1, 1)) {
                        if (entity != getPlayer()) {
                            entity.setVelocity(direction.clone().multiply(1.5).setY(0.2));
                        }
                    }
                }

                location.add(direction);

                if (amountOfTicks > 40) {
                    this.cancel();
                    for (Location l : toChangeLocations) {
                        world.getBlockAt(l).setType(Material.AIR);
                    }
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

        this.setCooldown();
    }
}
