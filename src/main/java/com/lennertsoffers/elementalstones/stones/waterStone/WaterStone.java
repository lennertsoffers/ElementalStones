package com.lennertsoffers.elementalstones.stones.waterStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.LinkedList;

public class WaterStone {


    // MOVES


    /**
     * <b>MOVE 1: Splash</b>
     * <p>
     *     Splashes around some water<br>
     *     The higher the level of the player, the more damage it does<br>
     *     <ul>
     *         <li><b>Damage:</b> 2^(level / 30)</li>
     *     </ul>
     * </p>
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move1(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            player.setVelocity(new Vector(0, 0.2, 0));
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (
                            int i = 0;
                            i < 360; i++) {
                        Location centerLocation = MathTools.locationOnCircle(player.getLocation(), 3, i, world);
                        NearbyEntityTools.damageNearbyEntities(player, centerLocation, Math.pow(2, player.getLevel() / 30f), 1, 1, 1);

                        for (int j = 0; j < 10; j++) {
                            double locationX = centerLocation.getX() + StaticVariables.random.nextGaussian() / 2;
                            double locationY = centerLocation.getY() + StaticVariables.random.nextGaussian() / 10;
                            double locationZ = centerLocation.getZ() + StaticVariables.random.nextGaussian() / 2;
                            world.spawnParticle(Particle.BUBBLE_POP, locationX, locationY, locationZ, 0);
                        }
                    }
                }
            }.runTaskLater(StaticVariables.plugin, 5L);
        };
    }

    /**
     * <b>MOVE 2: Dolphin Dive</b>
     * <p>
     *     The player gets the dolphins grace effect for 1 minute<br>
     *     If the player has the waterbending stone, its passive effect gets doubled<br>
     *     <ul>
     *         <li><b>PotionEffect:</b> Dolphin Grace (duration: 1min, amplifier 3)</li>
     *     </ul>
     * </p>
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move2(ActivePlayer activePlayer) {
        return () -> {
            activePlayer.setDoublePassive1(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    activePlayer.setDoublePassive1(false);
                }
            }.runTaskLater(StaticVariables.plugin, 1200L);
            Player player = activePlayer.getPlayer();
            player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 1200, 3, true, true, true));
        };
    }

    /**
     * <b>MOVE 3: Water Bullet</b>
     * <p>
     *     Shoots a bullet of water damaging and penetrating entities<br>
     *     <ul>
     *         <li><b>Damage:</b> 4</li>
     *         <li><b>Knockback:</b> 1</li>
     *     </ul>
     * </p>
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move3(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            Location location = player.getLocation().add(0, 1, 0);
            Vector vector = location.getDirection().multiply(0.5);
            location.add(vector.clone().multiply(5));

            LinkedList<Block> waterBlocks = new LinkedList<>();

            new BukkitRunnable() {
                int amountOfTicks = 1;

                @Override
                public void run() {
                    if (waterBlocks.size() > 3) {
                        waterBlocks.pop().setType(Material.AIR);
                    }

                    Location blockLocation = location.clone().add(vector.clone().multiply(amountOfTicks));
                    Block block = world.getBlockAt(blockLocation);
                    NearbyEntityTools.damageNearbyEntities(player, blockLocation, 5, 1, 1, 1, vector);

                    Material material = block.getType();
                    if (material == Material.AIR || material == Material.FIRE || material == Material.WATER || CheckLocationTools.isFoliage(material)) {
                        block.setType(Material.WATER);
                        waterBlocks.add(block);
                    } else {
                        this.cancel();

                        for (Block waterBlock : waterBlocks) {
                            waterBlock.setType(Material.AIR);
                        }
                        waterBlocks.clear();
                    }

                    location.add(vector);

                    amountOfTicks++;
                    if (amountOfTicks > 100) {
                        this.cancel();

                        for (Block waterBlock : waterBlocks) {
                            waterBlock.setType(Material.AIR);
                        }
                        waterBlocks.clear();
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }
}

























