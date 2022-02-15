package com.lennertsoffers.elementalstones.moves.airMoves.agility;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class FlyingKnives extends Move {

    public FlyingKnives(ActivePlayer activePlayer) {
        super(activePlayer, "Flying Knives", "air_stone", "agility_stone", 5);
    }

    @Override
    public void useMove() {
        if (this.getActivePlayer().getMove5Arrows().isEmpty()) {
            World world = this.getPlayer().getWorld();

            getArrowLocations().forEach(arrowLocation -> {
                Arrow arrow = (Arrow) world.spawnEntity(arrowLocation, EntityType.ARROW);
                arrow.setGlowing(true);
                this.getActivePlayer().getMove5Arrows().add(arrow);
                arrow.setBounce(true);
            });

            new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {
                    List<Location> toLocations = getArrowLocations();

                    for (int i = 0; i < getActivePlayer().getMove5Arrows().size(); i++) {
                        Arrow arrow = getActivePlayer().getMove5Arrows().get(i);

                        if (arrow.isDead()) {
                            getActivePlayer().getMove5Arrows().remove(arrow);
                        } else {
                            Location toLocation = toLocations.get(i);
                            Location fromLocation = arrow.getLocation();

                            double differenceX = toLocation.getX() - fromLocation.getX();
                            double differenceY = toLocation.getY() - fromLocation.getY();
                            double differenceZ = toLocation.getZ() - fromLocation.getZ();

                            Vector velocity = new Vector(differenceX, differenceY, differenceZ);

                            arrow.setVelocity(velocity.multiply(0.5));
                        }

                        if (amountOfTicks > 2400 || getActivePlayer().getMove5Arrows().size() == 0) {
                            this.cancel();
                            getActivePlayer().getMove5Arrows().clear();
                        }
                        amountOfTicks++;
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else {
            Arrow arrow = this.getActivePlayer().getMove5Arrows().removeLast();
            Location location = this.getPlayer().getLocation();
            arrow.setVelocity(location.getDirection().multiply(3));

            if (this.getActivePlayer().getMove5Arrows().isEmpty()) {
                this.setCooldown();
            }
        }
    }

    private List<Location> getArrowLocations() {
        Location location = this.getPlayer().getLocation();
        Vector direction = location.getDirection();

        return Arrays.asList(
                location.clone().add(0, 1, 0).add(direction.clone().rotateAroundY(90)).add(direction.clone().multiply(1.3)),
                location.clone().add(0, 1, 0).add(direction.clone().rotateAroundY(-90)).add(direction.clone().multiply(1.3)),
                location.clone().add(0, 2, 0).add(direction.clone().rotateAroundY(90)).add(direction.clone().multiply(1.3)),
                location.clone().add(0, 2, 0).add(direction.clone().rotateAroundY(-90)).add(direction.clone().multiply(1.3)),
                location.clone().add(0, 2.5, 0).add(direction.clone().multiply(1.3))
        );
    }
}
