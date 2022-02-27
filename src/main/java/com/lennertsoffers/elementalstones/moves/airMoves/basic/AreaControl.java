package com.lennertsoffers.elementalstones.moves.airMoves.basic;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class AreaControl extends Move {

    /**
     * <b>MOVE 2: A(i)rea Control</b>
     * <p>
     *     Blast away every living entity in close range<br>
     *     <ul>
     *         <li><b>Range: </b> 7</li>
     *         <li><b>Knockback:</b> more the further away from the player</li>
     *         <li><b>Knockup: </b> 0.2</li>
     *     </ul>
     * </p>
     * @param activePlayer the activeplayer executing the move
     */
    public AreaControl(ActivePlayer activePlayer) {
        super(activePlayer, "A(i)rea Control", "air_stone", "default", 2);
    }
    
    @Override
    public void useMove() {
        World world = this.getPlayer().getWorld();
        Location location = this.getPlayer().getLocation().add(0, 1, 0);
        for (int i = 0; i < 360; i++) {
            Location particleLocation = MathTools.locationOnCircle(location, 4, i, world);
            Vector direction = particleLocation.add(-location.getX(), 0, -location.getZ()).toVector().setY(0);
            for (int j = 0; j < 3; j++) {
                world.spawnParticle(Particle.SNOWFLAKE, location.clone().add(StaticVariables.random.nextGaussian() / 4, StaticVariables.random.nextGaussian() / 4, StaticVariables.random.nextGaussian() / 4), 0, direction.getX() / 5, 0, direction.getZ() / 5);
            }
        }
        if (!world.getNearbyEntities(location, 7, 2, 7).isEmpty()) {
            for (Entity entity : world.getNearbyEntities(location, 7, 2, 7)) {
                if (entity != null) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        if (livingEntity != this.getPlayer()) {
                            Vector direction = livingEntity.getLocation().add(-location.getX(), 0, -location.getZ()).toVector().setY(0.2);
                            livingEntity.setVelocity(direction);
                        }
                    }
                }
            }
        }

        this.setCooldown();
    }
}
