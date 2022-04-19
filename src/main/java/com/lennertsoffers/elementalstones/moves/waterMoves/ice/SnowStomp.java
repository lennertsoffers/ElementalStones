package com.lennertsoffers.elementalstones.moves.waterMoves.ice;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SnowStomp extends Move {

    /**
     * <b>MOVE 6: Snow Stomp</b>
     * <p>
     *     Turns a circle of ground into powder snow<br>
     *     The height of the transformed blocks is 4<br>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     */
    public SnowStomp(ActivePlayer activePlayer) {
        super(activePlayer, "Snow Stomp", "water_stone", "ice_stone", 6);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();
        World world = player.getWorld();
        Block targetBlock = player.getTargetBlockExact(20);

        if (targetBlock != null) {
            Location playerLocation = targetBlock.getLocation();

            ArrayList<Location> snowBlockLocations = new ArrayList<>();
            snowBlockLocations.add(playerLocation.clone().add(-1, 0, 2));
            snowBlockLocations.add(playerLocation.clone().add(0, 0, 2));
            snowBlockLocations.add(playerLocation.clone().add(1, 0, 2));
            snowBlockLocations.add(playerLocation.clone().add(-1, 0, -2));
            snowBlockLocations.add(playerLocation.clone().add(0, 0, -2));
            snowBlockLocations.add(playerLocation.clone().add(1, 0, -2));
            snowBlockLocations.add(playerLocation.clone().add(2, 0, -1));
            snowBlockLocations.add(playerLocation.clone().add(2, 0, 0));
            snowBlockLocations.add(playerLocation.clone().add(2, 0, 1));
            snowBlockLocations.add(playerLocation.clone().add(-2, 0, -1));
            snowBlockLocations.add(playerLocation.clone().add(-2, 0, 0));
            snowBlockLocations.add(playerLocation.clone().add(-2, 0, 1));

            playerLocation.add(-1, 0, -1);

            for (int i = 1; i <= 9; i++) {
                snowBlockLocations.add(playerLocation.clone());
                if (i % 3 == 0) {
                    playerLocation.add(-3, 0, 1);
                }
                playerLocation.add(1, 0, 0);
            }
            for (Location location : snowBlockLocations) {
                Location startLocation = world.getHighestBlockAt(location).getLocation();
                Location particleLocation = startLocation.clone().add(0, 1, 0);
                for (int i = 0; i < 5; i++) {
                    double x = particleLocation.getX() + StaticVariables.random.nextGaussian() / 10;
                    double y = particleLocation.getY() + StaticVariables.random.nextDouble() / 10;
                    double z = particleLocation.getZ() + StaticVariables.random.nextGaussian() / 10;
                    world.spawnParticle(Particle.BLOCK_CRACK, x, y, z, 0, Material.POWDER_SNOW.createBlockData());
                }

                for (int i = 0; i > -4; i--) {
                    Block block = world.getBlockAt(startLocation.clone().add(0, i, 0));
                    this.getActivePlayer().addLocationMaterialMapping(block.getLocation(), block.getType());
                    block.setType(Material.POWDER_SNOW);
                }
            }

            this.setCooldown();
        }
    }
}
