package com.lennertsoffers.elementalstones.stones.fireStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireBall;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireBlast;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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
     * <b>MOVE 4: Fire Pokes</b>
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
            HashMap<Block, Material> fireBlocksTypes = new HashMap<>();

            new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {
                    Location location = player.getLocation();
                    Block block = location.getBlock();
                    Material material = block.getType();

                    if (
                            material != Material.AIR &&
                            material != Material.LAVA &&
                            material != Material.WATER &&
                            !CheckLocationTools.isFoliage(location)
                    ) {
                        fireBlocksTypes.put(block, material);
                        block.setType(Material.FIRE);

                        List<Object> fireBlocks = Arrays.asList(fireBlocksTypes.keySet().toArray());
                        if (fireBlocks.size() > 20) {
                            Block fireBlock = (Block) fireBlocks.get(0);
                            Material fireBlockMaterial = fireBlocksTypes.get(fireBlock);

                            fireBlock.setType(fireBlockMaterial);
                            fireBlocksTypes.remove(fireBlock);
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

    // MOVE 5
    // Fire Blast
    // -> Follow up to floating fire
    // -> Shoots fire ball in the looking direction
    public static Runnable move5(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();

            FireBlast fireBlast = new FireBlast(player, world);
            fireBlast.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }

    // MOVE 6
    // Into the Underworld
    // -> Follow up to floating fire
    // -> Instant nether teleportation
    public static Runnable move6(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();

            // Mark teleportation location in dimension
            if (player.isSneaking()) {

            }
        };
    }

    // MOVE 7
    // Fire Beam
    // Shoot an array of destructing fire out of your hands
    public static Runnable move7(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            new BukkitRunnable() {
                double stageOfBeam = 0.1;
                final Random random = new Random();

                @Override
                public void run() {
                    final Location location = player.getEyeLocation().add(0, -0.6, 0);
                    final Vector direction = location.getDirection();
                    Location variableLocation = location.clone();
                    for (double i = 0.1; i <= stageOfBeam; i += 0.1) {
                        NearbyEntityTools.damageNearbyEntities(player, location, 1, 0.5, 0.5, 0.5, livingEntity -> livingEntity.setFireTicks(livingEntity.getFireTicks() + 40));                        spawnSmallFlameOrb(variableLocation, direction, random, world);
                        variableLocation.add(direction.getX() / 40 * i, direction.getY() / 40 * i, direction.getZ() / 40 * i);
                    }
                    if (stageOfBeam >= 7) {
                        this.cancel();
                    }
                    stageOfBeam += 0.35;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            new BukkitRunnable() {
                int tickCount = 0;
                final Random random = new Random();

                @Override
                public void run() {
                    final Location location = player.getEyeLocation();
                    final Vector direction = location.getDirection();
                    location.add(0, -0.6, 0);
                    for (double i = 0.1; i < 7; i += 0.1) {
                        NearbyEntityTools.damageNearbyEntities(player, location, 1, 0.5, 0.5, 0.5, livingEntity -> livingEntity.setFireTicks(livingEntity.getFireTicks() + 40));                        spawnSmallFlameOrb(location, direction, random, world);
                        location.add(direction.getX() / 40 * i, direction.getY() / 40 * i, direction.getZ() / 40 * i);
                    }
                    if (tickCount > 100) {
                        this.cancel();
                    }
                    tickCount++;
                }
            }.runTaskTimer(StaticVariables.plugin, 20L, 1L);
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
            new BukkitRunnable() {
                int tickCount = 0;
                final Random random = new Random();

                @Override
                public void run() {
                    Location location = player.getLocation().add(0, 1, 0);
                    Location particleLocation = location.clone();
                    double addX = random.nextDouble();
                    double addY = random.nextDouble();
                    double addZ = random.nextDouble();
                    if (random.nextBoolean()) {
                        particleLocation.add(addX, 0, 0);
                    } else {
                        particleLocation.add(-addX, 0, 0);
                    }

                    if (random.nextBoolean()) {
                        particleLocation.add(0, addY, 0);
                    } else {
                        particleLocation.add(0, -addY, 0);
                    }

                    if (random.nextBoolean()) {
                        particleLocation.add(0, 0, addZ);
                    } else {
                        particleLocation.add(0, 0, 0 - addZ);
                    }
                    Vector particleDirection = location.toVector().subtract(particleLocation.toVector());
                    world.spawnParticle(Particle.FLAME, particleLocation, 0, particleDirection.getX() / 100, particleDirection.getY() / 100, particleDirection.getZ() / 100, 4);
                    if (tickCount > 58) {
                        this.cancel();
                    }
                    tickCount++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            new BukkitRunnable() {
                int radius = 20;
                final Random random = new Random();

                @Override
                public void run() {
                    Location location = player.getLocation();
                    if (radius % 2 == 0) {
                        Entity fireball = world.spawnEntity(location.clone().add(0, 3, 0), EntityType.FIREBALL);
                        fireball.setVelocity(location.getDirection().setY(-0.3));
                    }
                    for (int i = 0; i < 360; i++) {
                        Location particleLocation = MathTools.locationOnCircle(location, (double) radius / 10, i, world);
                        particleLocation.add(random.nextGaussian() / 2, random.nextGaussian() / 2, random.nextGaussian() / 2);
                        Vector particleDirection = MathTools.directionOfVector(location, particleLocation);
                        particleDirection.setX(particleDirection.getX() / 150);
                        particleDirection.setZ(particleDirection.getZ() / 150);
                        Location newParticleLocation = particleLocation.add(particleDirection).getBlock().getLocation();
                        world.spawnParticle(Particle.FLAME, particleLocation, 0, particleDirection.getX(), 0, particleDirection.getZ(), 70);
                        Stream.concat(world.getNearbyEntities(newParticleLocation, 0.5, 0.5, 0.5).stream(), world.getNearbyEntities(particleLocation, 0.5, 0.5, 0.5).stream()).forEach(entity -> {
                            if (entity != null) {
                                if (entity != player) {
                                    if (entity instanceof LivingEntity) {
                                        LivingEntity livingEntity = (LivingEntity) entity;
                                        if (HellfireStone.damagedEntityCoolDownMove8.containsKey(livingEntity)) {
                                            if (HellfireStone.damagedEntityCoolDownMove8.get(livingEntity) < System.currentTimeMillis()) {
                                                livingEntity.setFireTicks(400);
                                                livingEntity.damage(5);
                                                HellfireStone.damagedEntityCoolDownMove8.put(livingEntity, System.currentTimeMillis() + (10 * 1000));
                                            }
                                        } else {
                                            livingEntity.setFireTicks(400);
                                            livingEntity.damage(5);
                                            HellfireStone.damagedEntityCoolDownMove8.put(livingEntity, System.currentTimeMillis() + (10 * 1000));
                                        }

                                    }
                                }
                            }
                        });
                    }
                    if (radius >= 100) {
                        this.cancel();
                    }
                    radius += 1;
                }
            }.runTaskTimer(StaticVariables.plugin, 60L, 1L);
        };
    }
}




























