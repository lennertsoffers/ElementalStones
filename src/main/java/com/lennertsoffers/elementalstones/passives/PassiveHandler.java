package com.lennertsoffers.elementalstones.passives;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.FireworkTools;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import com.lennertsoffers.elementalstones.items.ItemStones;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PassiveHandler {


    // EARTHBENDING STONE
    /**
     * <b>Shockwave</b>
     * <p>
     *     Creates a shockwave around the player when he falls<br>
     *     Activates when the player should normally get fall damage (no fall damage is taken)<br>
     *     This wave knocks up entities<br>
     *     The size and the knockup height depends on the fall distance of the player<br>
     *     <ul>
     *         <li><b>PotionEffect:</b> Slowness (duration: 2s, amplifier: 1)</li>
     *     </ul>
     * </p>
     *
     *
     * @param activePlayer the activeplayer for whom this move is executed
     * @param event the EntityDamageEvent from the event listener
     */
    public static void shockwave(ActivePlayer activePlayer, EntityDamageEvent event) {
        Player player = activePlayer.getPlayer();

        if (!Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.earthBendingStones)) {
            World world = player.getWorld();
            float fallDistance;
            if (player.getFallDistance() < 20) {
                fallDistance = player.getFallDistance();
            } else {
                fallDistance = 20;
            }
            List<PotionEffect> potionEffects = new ArrayList<>();
            potionEffects.add(new PotionEffect(PotionEffectType.SLOW, 40, 1, false, false, false));
            Vector velocity = new Vector(0, 0.2, 0);

            event.setCancelled(true);

            List<Block> rings = new ArrayList<>();
            new BukkitRunnable() {
                int range = 2;

                @Override
                public void run() {
                    for (int i = 0; i < 360; i += 1) {
                        Location locationOnCircle = CheckLocationTools.getClosestAirBlockLocation(MathTools.locationOnCircle(player.getLocation(), range, i, world));

                        if (locationOnCircle != null) {
                            locationOnCircle.add(0, -1, 0);

                            Block block = world.getBlockAt(locationOnCircle);

                            if (rings.stream().noneMatch(b -> b.getX() == block.getX() && b.getZ() == block.getZ())) {
                                rings.add(block);

                                BlockData blockData = block.getBlockData();
                                block.setType(Material.AIR);

                                FallingBlock fallingBlock = world.spawnFallingBlock(block.getLocation().add(0.5, 0, 0.5), blockData);
                                fallingBlock.setDropItem(true);
                                fallingBlock.setVelocity(velocity);

                                double amountY = (fallDistance - range - 2) / 10;
                                if (amountY < 0.4) {
                                    amountY = 0.4;
                                }
                                NearbyEntityTools.damageNearbyEntities(player, block.getLocation(), 0, 1, 2, 1, new Vector(0, amountY, 0), potionEffects);
                            }
                        }
                    }

                    range++;
                    if (range > fallDistance - 2) {
                        this.cancel();
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        }
    }


    // LAVA STONE
    /**
     * <b>Lava Walker</b>
     * <p>
     *     Frost walker for lava<br>
     *     It generates basalt beneath the player and it gets removed after a short period of time<br>
     *     The platform under the player won't be removed unlike the real frost walker so the player can stand still on lava<br>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     */
    public static void lavaWalker(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();

        if (player.getWorld().getEnvironment() == World.Environment.NETHER) {
            if (!Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.lavaStones)) {
                World world = player.getWorld();
                Location center = player.getLocation().getBlock().getLocation().add(0, -1, 0);

                if (CheckLocationTools.lavaAroundPlayer(center)) {
                    activePlayer.getCurrentPlatform().clear();

                    getLocationsAround(center).forEach(location -> {
                        if (!activePlayer.getLavaLocations().contains(location)) {
                            Block block = world.getBlockAt(location);

                            if (block.getType() == Material.LAVA || block.getType() == Material.BASALT) {
                                block.setType(Material.BASALT);
                                activePlayer.getAllPlatformBlocks().add(block);
                                activePlayer.getCurrentPlatform().add(block);
                            }
                        }
                    });

                    if (activePlayer.getAllPlatformBlocks().size() > 21) {
                        List<Block> blocksToRemove = new ArrayList<>(activePlayer.getAllPlatformBlocks());
                        activePlayer.getAllPlatformBlocks().clear();

                        new BukkitRunnable() {
                            @Override
                            public void run() {

                                int bound = blocksToRemove.size();
                                int index = StaticVariables.random.nextInt(bound);

                                Block blockToRemove = blocksToRemove.get(index);
                                if (activePlayer.getCurrentPlatform().contains(blockToRemove)) {
                                    activePlayer.getAllPlatformBlocks().add(blockToRemove);
                                } else {
                                    blockToRemove.setType(Material.LAVA);
                                }
                                blocksToRemove.remove(index);

                                if (blocksToRemove.size() <= 0) {
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(StaticVariables.plugin, 0L, 4L);
                    }
                } else if (world.getBlockAt(center).getType() != Material.LAVA && world.getBlockAt(center).getType() != Material.BASALT) {
                    activePlayer.getAllPlatformBlocks().forEach(block -> block.setType(Material.LAVA));
                }
            }
        }
    }

    /**
     * <b>Magma Master</b>
     * <p>
     *     Players holding the lava stone are invincible for lava, fire and hot floor damage<br>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     * @param event the EntityDamageEvent that is triggered
     * @see com.lennertsoffers.elementalstones.eventHandlers.EntityDamageEvent
     */
    public static void magmaMaster(ActivePlayer activePlayer, EntityDamageEvent event) {
        if (!Collections.disjoint(Arrays.asList(activePlayer.getPlayer().getInventory().getContents()), ItemStones.lavaStones)) {
            if (
                    event.getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.LAVA ||
                            event.getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.FIRE_TICK ||
                            event.getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.FIRE ||
                            event.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR
            ) {
                event.setCancelled(true);
            }
        }
    }


    // HELLFIRE STONE
    /**
     * <b>Friendly Fire</b>
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
    public static void friendlyFire(EntityDamageEvent event, Player player) {
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


    // EXPLOSION STONE
    /**
     * <b>Explosion Resistance</b>
     * <p>
     *     The player is completely immune for explosions<br>
     * </p>
     *
     * @param event the entityDamageEvent that triggers this passive
     * @param player the player that is hurt by the explosion
     */
    public static void explosionResistance(EntityDamageEvent event, Player player) {
        if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            if (!Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.explosionStones)) {
                event.setCancelled(true);
            }
        }
    }

    /**
     * <b>Cute Creepers</b>
     * <p>
     *     If a creeper explodes close to a player with the explosion stone, this explosion will be cancelled<br>
     *     A firework explosion will trigger instead<br>
     * </p>
     *
     * @param event the entityExplosionEvent that triggers this passive
     */
    public static void cuteCreepers(EntityExplodeEvent event) {
        Location location = event.getLocation();
        World world = location.getWorld();

        if (event.getEntityType() == EntityType.CREEPER) {
            if (world != null) {
                if (
                        world.getNearbyEntities(location, 10, 10, 10, entity -> entity instanceof Player).stream().anyMatch(entity -> {
                            Player player = (Player) entity;
                            return !Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.explosionStones);
                        })
                ) {
                    event.setCancelled(true);

                    Firework firework = FireworkTools.setRandomMeta((Firework) world.spawnEntity(location.add(0, 1.5, 0), EntityType.FIREWORK), 0, null, 3, 3, -1, -1);
                    firework.detonate();
                }
            }
        }
    }


    // WATERBENDING STONE
    /**
     * <b>Deep Breath</b>
     * <p>
     *     When the player is in water, he gets the water breathing effect<br>
     *     <ul>
     *         <li><b>PotionEffect:</b> Water Breathing (duration: 10s, amplifier: 1)</li>
     *     </ul>
     * </p>
     *
     * @param activePlayer the activePlayer going into the water
     */
    public static void deepBreath(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.waterBendingStones)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 210, 1, false, false, false));
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 200L);
    }

    /**
     * <b>Water Walker</b>
     * <p>
     *     The player always moves forward in water at a normal speed<br>
     *     You can cancel the movement by crouching<br>
     *     Your speed can be doubled when executing move2<br>
     * </p>
     *
     * @param activePlayer the activeplayer moving through the water
     */
    public static void waterWalker(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        if (player.getLocation().getBlock().getType() == Material.WATER) {
            if (!Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.waterBendingStones)) {
                if (player.getPose() != Pose.SNEAKING) {
                    if (!activePlayer.isDoublePassive1()) {
                        player.setVelocity(player.getLocation().getDirection().multiply(0.3));
                    } else {
                        player.setVelocity(player.getLocation().getDirection());
                    }
                }
            }
        }
    }


    // ICE STONE
    /**
     * <b>Ice Boots</b>
     * <p>
     *     The player gets ice boots with the frost walker effect<br>
     *     If the player already has boots, they get upgraded with frost walker<br>
     *     When the player leaves move mode, the boots will disappear or the enchantment will be removed<br>
     * </p>
     *
     * @param activePlayer the activeplayer going in move mode
     */
    public static void iceBoots(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        if (
                player.getInventory().contains(ItemStones.waterStoneIce0) ||
                        player.getInventory().contains(ItemStones.waterStoneIce1) ||
                        player.getInventory().contains(ItemStones.waterStoneIce2) ||
                        player.getInventory().contains(ItemStones.waterStoneIce3) ||
                        player.getInventory().contains(ItemStones.waterStoneIce4)
        ) {
            // Leather boots
            ItemStack leatherBoots = new ItemStack(Material.LEATHER_BOOTS, 1);
            leatherBoots.addEnchantment(Enchantment.FROST_WALKER, 2);
            leatherBoots.addEnchantment(Enchantment.BINDING_CURSE, 1);
            ItemMeta leatherBootsMeta = leatherBoots.getItemMeta();
            if (leatherBootsMeta != null) {
                leatherBootsMeta.setDisplayName(ChatColor.BLUE + "Ice Boots");
                leatherBootsMeta.setUnbreakable(true);
                leatherBootsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                leatherBootsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                leatherBootsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            }
            leatherBoots.setItemMeta(leatherBootsMeta);

            if (player.getInventory().getBoots() != null) {
                // Player has boots
                ItemStack boots = player.getInventory().getBoots();
                if (!boots.containsEnchantment(Enchantment.FROST_WALKER)) {
                    // Don't have frost walker
                    boots.addEnchantment(Enchantment.FROST_WALKER, 2);
                    boots.addEnchantment(Enchantment.BINDING_CURSE, 1);
                    ItemMeta bootsMeta = boots.getItemMeta();
                    if (bootsMeta != null) {
                        if (bootsMeta.getDisplayName().isEmpty()) {
                            bootsMeta.setDisplayName(WordUtils.capitalizeFully(boots.getType().name().toLowerCase().replace("_", " ")));
                        }
                        bootsMeta.setDisplayName(ChatColor.BLUE + "Upgraded " + bootsMeta.getDisplayName());
                    }
                    boots.setItemMeta(bootsMeta);
                } else {
                    // Already have frost walker
                    ItemMeta bootsMeta = boots.getItemMeta();
                    if (bootsMeta != null) {
                        if (bootsMeta.getDisplayName().startsWith(ChatColor.BLUE + "Upgraded ")) {
                            // Upgraded boots -> Remove enchantment
                            bootsMeta.setDisplayName(ChatColor.WHITE + bootsMeta.getDisplayName().replace(ChatColor.BLUE + "Upgraded ", ""));
                            boots.setItemMeta(bootsMeta);
                            boots.removeEnchantment(Enchantment.FROST_WALKER);
                            boots.removeEnchantment(Enchantment.BINDING_CURSE);
                        } else if (bootsMeta.getDisplayName().contains(ChatColor.BLUE + "Ice Boots")) {
                            // New boots -> Remove boots
                            player.getInventory().setBoots(null);
                        }
                    }
                }
            } else {
                // Player doesn't have boots
                player.getInventory().setBoots(leatherBoots);
            }
        }
    }

    /**
     * <b>Slippery</b>
     * <p>
     *     If the player walks on ice, he gets the speed potion effect<br>
     *     <ul>
     *         <li><b>PotionEffect:</b> Speed (duration: 5, amplifier: 3)</li>
     *     </ul>
     * </p>
     *
     * @param player the player who walking on ice
     */
    public static void slippery(Player player) {
        if (!Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.iceStones)) {
            Material material = player.getLocation().add(0, -1, 0).getBlock().getType();
            if (material == Material.ICE || material == Material.PACKED_ICE || material == Material.BLUE_ICE || material == Material.FROSTED_ICE) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 3, true, true, true));
            }
        }
    }


    // AIR STONE
    /**
     * <b>Feather Falling</b>
     * <p>
     *     The player won't take any fall damage<br>
     * </p>
     *
     * @param event this move is triggered when the player would normally take fall damage
     */
    public static void featherFalling(ActivePlayer activePlayer, EntityDamageEvent event) {
        Player player = activePlayer.getPlayer();
        if (
                !Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.airbendingStones) ||
                        !Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.agilityStones)
        ) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
            }
        }
    }

    /**
     * <b>Double Jump</b>
     * <p>
     *     The player can jump a second time after jumping in the air with space bar<br>
     *     If the player is standing still he get launched up<br>
     *     Otherwise he gets launched in the moving direction<br>
     *     For the AirbendingStone the velocity and height is is multiplied with 0.6<br>
     * </p>
     *
     * @param event this move is triggered when the player would normally toggle flight mode
     */
    public static void doubleJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());

        if (activePlayer != null) {
            boolean airbendingDoubleJump = false;
            boolean agilityDoubleJump = false;

            if (!Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.airbendingStones)) {
                airbendingDoubleJump = true;
            } else if (!Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.agilityStones)) {
                agilityDoubleJump = true;
            }

            if (airbendingDoubleJump || agilityDoubleJump) {
                event.setCancelled(true);

                if (activePlayer.canDoubleJump()) {
                    Vector launchDirection;
                    launchDirection = activePlayer.getMovingDirection();
                    launchDirection.setY(1);

                    if (airbendingDoubleJump) {
                        launchDirection.multiply(0.6);
                    }

                    if (player.getVelocity().getY() < 0) {
                        player.setVelocity(player.getVelocity().setY(0));
                    }
                    player.setVelocity(player.getVelocity().add(launchDirection));
                    activePlayer.disableDoubleJump();

                    Location location = player.getLocation();
                    World world = player.getWorld();
                    Vector inverseLaunchDirection = launchDirection.clone().multiply(-0.5);
                    for (int i = 0; i < 40; i++) {
                        double x = location.getX() + StaticVariables.random.nextGaussian() / 10;
                        double y = location.getY() + StaticVariables.random.nextGaussian() / 10;
                        double z = location.getZ() + StaticVariables.random.nextGaussian() / 10;
                        world.spawnParticle(Particle.CLOUD, x, y, z, 0, inverseLaunchDirection.getX(), inverseLaunchDirection.getY(), inverseLaunchDirection.getZ());
                    }
                }
            }
        }
    }



    // HELPERS
    /**
     * <b>Generates a list of locations in a circle beneath the player</b>
     * @param center the center location of the circle
     * @return a list of locations in a circle around the player
     */
    public static List<Location> getLocationsAround(Location center) {
        return Arrays.asList(
                center.clone(),
                center.clone().add(1, 0, 0),
                center.clone().add(-1, 0, 0),
                center.clone().add(0, 0, 1),
                center.clone().add(0, 0, -1),
                center.clone().add(1, 0, 1),
                center.clone().add(-1, 0, 1),
                center.clone().add(1, 0, -1),
                center.clone().add(-1, 0, -1),

                center.clone().add(2, 0, 0),
                center.clone().add(2, 0, 1),
                center.clone().add(2, 0, -1),
                center.clone().add(-2, 0, 0),
                center.clone().add(-2, 0, 1),
                center.clone().add(-2, 0, -1),
                center.clone().add(0, 0, 2),
                center.clone().add(1, 0, 2),
                center.clone().add(-1, 0, 2),
                center.clone().add(0, 0, -2),
                center.clone().add(1, 0, -2),
                center.clone().add(-1, 0, -2)
        );
    }
}
