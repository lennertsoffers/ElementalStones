package com.lennertsoffers.elementalstones.stones.fireStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.*;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Stream;

public class HellfireStone extends FireStone {


    // PASSIVE


    /**
     * <b>PASSIVE: Friendly Fire</b>
     * <p>
     *     The player will not take any fire or lighting damage<br>
     *     If the player is on fire, he will get the strength effect<br>
     *     If the player is struck by lightning, he will get a large set of effects<br>
     *     <ul>
     *         <li><b>Fire damage:</b>
     *             <ul>
     *                 <li>Strength (duration: 1min, amplifier: 3)</li>
     *             </ul>
     *         </li>
     *         <li><b>Lightning:</b>
     *             <ul>
     *                 <li>Speed (duration: 1min, amplifier: 3)</li>
     *                 <li>Strength (duration: 1min, amplifier: 3)</li>
     *                 <li>Jump Boost (duration: 1min, amplifier: 3)</li>
     *                 <li>Saturation (duration: 1min, amplifier: 3)</li>
     *                 <li>Healing (duration: 1min, amplifier: 3)</li>
     *                 <li>Resistance (duration: 1min, amplifier: 3)</li>
     *             </ul>
     *         </li>
     *     </ul>
     * </p>
     *
     * @param event the entityDamageEvent triggering this passive
     * @param player the player that gets damage
     */
    public static void passive(EntityDamageEvent event, Player player) {
        if (!Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.hellfireStones)) {
            if (event.getCause().equals(EntityDamageEvent.DamageCause.FIRE) || event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {
                event.setCancelled(true);

                if (player.getFireTicks() > 0) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 3, true, true, true));
                }
            } else if (event.getCause().equals(EntityDamageEvent.DamageCause.LIGHTNING)) {
                event.setCancelled(true);

                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 3, true, true, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1200, 3, true, true, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1200, 3, true, true, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 1200, 3, true, true, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1200, 3, true, true, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1200, 3, true, true, true));
            }
        }
    }


    // MOVES


    /**
     * <b>MOVE 4: Fire Track</b>
     * <p>
     *     The player leaves a track of fire behind him/her<br>
     *     The speed of the player is drastically improved<br>
     *     <ul>
     *         <li><b>Duration:</b> 30s</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move4(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 3, true, true, true));
            LinkedList<HashMap<String, Object>> fireBlockInfo = new LinkedList<>();

            new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {
                    Location location = player.getLocation();
                    Block block = location.getBlock();
                    Material blockMaterial = block.getType();

                    location.add(0, -1, 0);
                    Block walkingBlock = location.getBlock();
                    Material walkingMaterial = walkingBlock.getType();

                    if (
                            walkingMaterial != Material.AIR &&
                            walkingMaterial != Material.LAVA &&
                            walkingMaterial != Material.WATER &&
                            walkingMaterial.isSolid() &&
                            (blockMaterial == Material.AIR || CheckLocationTools.isFoliage(blockMaterial))
                    ) {
                        HashMap<String, Object> fireBlockInfoMap = new HashMap<>();
                        fireBlockInfoMap.put("block", block);
                        fireBlockInfoMap.put("material", blockMaterial);
                        fireBlockInfo.add(fireBlockInfoMap);

                        block.setType(Material.FIRE);

                        if (fireBlockInfo.size() > 20) {
                            HashMap<String, Object> removeFireBlockInfoMap = fireBlockInfo.pop();
                            Block fireBlock = (Block) removeFireBlockInfoMap.get("block");
                            Material fireBlockMaterial = (Material) removeFireBlockInfoMap.get("material");

                            fireBlock.setType(fireBlockMaterial);
                        }
                    }

                    amountOfTicks++;
                    if (amountOfTicks > 600) {
                        this.cancel();
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }

    /**
     * <b>MOVE 5: Ring Of Fire</b>
     * <p>
     *     A wave of fire is created around the player<br>
     *     It damages entities and pushes them back<br>
     *     Sets entities on fire for a moment<br>
     *     <ul>
     *         <li><b>Damage:</b> 3</li>
     *         <li><b>Range:</b> 10</li>
     *         <li><b>Knockback:</b> 3</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     */
    public static Runnable move5(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();

            FireBlast fireBlast = new FireBlast(player, world);
            fireBlast.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }

    /**
     * <b>MOVE 6: Fire Shields</b>
     * <p>
     *     The player can place 5 walls of fire<br>
     *     These walls will protect the player from entities or other moves<br>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     * @see FireWall
     */
    public static Runnable move6(ActivePlayer activePlayer) {
        return () -> {
            if (activePlayer.canPlaceWall()) {
                Player player = activePlayer.getPlayer();
                Location feetLocation = player.getLocation();
                Vector direction = feetLocation.getDirection().setY(0);

                Location wallBottomLeft = feetLocation.clone().add(direction.clone().multiply(4));
                direction.rotateAroundY(Math.PI / 2);
                wallBottomLeft.add(direction.clone().multiply(-1));

                FireWall fireWall = new FireWall(activePlayer, wallBottomLeft, direction);
                fireWall.runTaskTimer(StaticVariables.plugin, 0L, 10L);
            }
        };
    }

    /**
     * <b>MOVE 7: Fire Beam</b>
     * <p>
     *     Shoot an array of destructing fire out of your hands<br>
     *     Sets entities and the ground on fire<br>
     *     <ul>
     *         <li><b>Damage:</b> 2</li>
     *         <li><b>Range:</b> 7</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     * @see FireFlamethrower
     */
    public static Runnable move7(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();

            FireFlamethrower fireFlamethrower = new FireFlamethrower(player, world);
            fireFlamethrower.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }

    /**
     * <b>ULTIMATE: Hellfire</b>
     * <p>
     *     Creates a storm of lightning and fireballs around the player<br>
     *     These fireballs will not break blocks<br>
     *     <ul>
     *         <li><b>Range:</b> 30</li>
     *         <li><b>Duration:</b> 10s</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     * @see FireHellfireStorm
     */
    public static Runnable move8(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            FireHellfireStorm fireHellfireStorm = new FireHellfireStorm(player);
            fireHellfireStorm.runTaskTimer(StaticVariables.plugin, 0L, 5L);
        };
    }
}




























