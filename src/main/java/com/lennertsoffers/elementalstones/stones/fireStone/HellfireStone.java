package com.lennertsoffers.elementalstones.stones.fireStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireBall;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireBlast;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireFlamethrower;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireWall;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
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

    private static final Map<LivingEntity, Long> damagedEntityCoolDownMove7 = new HashMap<>();
    private static final Map<LivingEntity, Long> damagedEntityCoolDownMove8 = new HashMap<>();

    private static void spawnSmallFlameOrb(Location location, Vector direction, Random random, World world) {
        for (int j = 0; j < 2; j++) {
            Location flameLocation = location.clone().add(direction);
            flameLocation.add(random.nextGaussian() / 20, random.nextGaussian() / 20, random.nextGaussian() / 20);
            world.spawnParticle(Particle.FLAME, flameLocation, 0, 0, 0, 0);
        }
    }

    // PASSIVE
    // Friendly Fire
    public static void passive(EntityDamageEvent event) {
        if (event.getCause().equals(EntityDamageEvent.DamageCause.FIRE) || event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {
            event.setCancelled(true);
        }
    }

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

    // MOVE 8
    // Dragons Breath
    // -> Creates a ring of fire around the player damaging entities
    // -> Player shoots continuously fireballs at the location he aims
    // -> This move has to charge for 3 seconds
    public static Runnable move8(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            Location location = player.getLocation().add(0, 3, 0);
            Vector direction = location.getDirection().setY(0);
            location.add(direction);

            Fireball fireball = (Fireball) world.spawnEntity(location, EntityType.FIREBALL);
            int speedAmplifier = 1;

            new BukkitRunnable() {
                int angle = 0;

                @Override
                public void run() {
                    Location fromLocation = fireball.getLocation();
                    Location toLocation = getFireballLocation();

                    double toX = (toLocation.getX() - fromLocation.getX()) / speedAmplifier;
                    double toZ = (toLocation.getZ() - fromLocation.getZ()) / speedAmplifier;

                    fireball.setVelocity(new Vector(toX, 0, toZ));

                    angle += 1;
                    if (angle > 360) {
                        angle = 0;
                    }
                }

                private Location getFireballLocation() {
                    Location location = player.getLocation().add(0, 3, 0);
                    Vector direction = location.getDirection().setY(0);
                    location.add(direction.multiply(3));
                    return location;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }
}




























