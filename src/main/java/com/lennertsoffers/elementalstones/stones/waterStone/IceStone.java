package com.lennertsoffers.elementalstones.stones.waterStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import com.lennertsoffers.elementalstones.customClasses.tools.SetBlockTools;
import com.lennertsoffers.elementalstones.items.ItemStones;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class IceStone extends WaterStone {

    // PASSIVE
    public static void passive1(ActivePlayer activePlayer) {
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
     * <b>MOVE 4: Ice Shards</b>
     * <p>
     *     You can throw ice shards damaging entities on impact<br>
     *     It stops when colliding with a block<br>
     *     <ul>
     *         <li><b>Damage:</b> 2</li>
     *         <li><b>Ammo:</b> 10</li>
     *         <li><b>Freeze:</b> +2s</li>
     *         <li><b>PotionEffect:</b> Slowness (duration: 2s, amplifier: 2)</li>
     *     </ul>
     * </p>
     * @param activePlayer the activeplayer executing the move
     * @return a BukkitRunnable that can be executed as move
     * @see IceStone#move4Impact(Location)
     */
    public static Runnable move4(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();

            if (activePlayer.useIceShard()) {
                Location location = player.getLocation().add(0, 1.5, 0);
                Vector direction = location.getDirection();
                List<PotionEffect> potionEffects = new ArrayList<>();
                potionEffects.add(new PotionEffect(PotionEffectType.SLOW, 40, 2, true, true, true));

                new BukkitRunnable() {
                    int amountOfTicks = 1;
                    final Location midpoint = location.add(direction);

                    @Override
                    public void run() {
                        if (
                                world.getBlockAt(midpoint).getType().isSolid() ||
                                NearbyEntityTools.damageNearbyEntities(player, midpoint, 2, 0.3, 0.3, 0.3, direction.clone().setY(0.3), potionEffects, livingEntity -> livingEntity.setFreezeTicks(Math.min(livingEntity.getFreezeTicks() + 40, livingEntity.getMaxFreezeTicks())))
                        ) {
                            this.cancel();
                            move4Impact(midpoint);
                        }

                        for (int i = 0; i < 60; i++) {
                            Particle.DustOptions dustOptions;
                            if (StaticVariables.random.nextBoolean()) {
                                dustOptions = new Particle.DustOptions(Color.fromRGB(0, 165, 255), 0.3f);
                            } else {
                                dustOptions = new Particle.DustOptions(Color.WHITE, 0.3f);
                            }

                            world.spawnParticle(Particle.REDSTONE, midpoint.clone().add(StaticVariables.random.nextGaussian() / 20, StaticVariables.random.nextGaussian() / 20, StaticVariables.random.nextGaussian() / 20), 0, direction.getX(), direction.getY(), direction.getZ(), dustOptions);
                            if (i % 2 == 0) {
                                midpoint.add(direction.getX() / 30, direction.getY() / 30, direction.getZ() / 30);
                            }
                        }
                        if (amountOfTicks > 10) {
                            this.cancel();
                        }
                        amountOfTicks++;
                    }
                }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            }
        };
    }

    /**
     * <b>Plays the particle effect for ice shards</b>
     * <p>
     *     This is played when an ice shard collides with and entity or block<br>
     * </p>
     *
     * @param impactLocation the location where the particle effect must play
     */
    private static void move4Impact(Location impactLocation) {
        for (int i = 0; i < 5; i++) {
            ItemStack stack;
            if (StaticVariables.random.nextBoolean()) {
                stack = new ItemStack(Material.ICE);
            } else {
                stack = new ItemStack(Material.SNOW_BLOCK);
            }
            Objects.requireNonNull(impactLocation.getWorld()).spawnParticle(Particle.ITEM_CRACK, impactLocation.clone().add(StaticVariables.random.nextGaussian() / 10, StaticVariables.random.nextGaussian() / 10, StaticVariables.random.nextGaussian() / 10), 0, 0, 0, 0, stack);
        }
    }

    // MOVE 5
    // Ice Spear
    // -> Throw a spear of ice at your enemy

    // move
    public static Runnable move5(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            World world = player.getWorld();
            if (!activePlayer.hasIceSpear()) {
                activePlayer.setIceSpear(new BukkitRunnable() {
                    @Override
                    public void run() {
                        Location spearLocation = player.getLocation().add(0, 1.5, 0).add(player.getLocation().getDirection().rotateAroundY(90).multiply(1.5)).add(player.getLocation().getDirection().multiply(-0.7));
                        move5spearAnimation(spearLocation, player.getLocation().getDirection());
                    }
                }.runTaskTimer(StaticVariables.plugin, 0L, 1L));
            } else {
                Vector direction = player.getLocation().getDirection();
                activePlayer.clearIceSpear();
                Location spearLocation = player.getLocation().add(0, 1.5, 0).add(player.getLocation().getDirection().rotateAroundY(90).multiply(1.5)).add(player.getLocation().getDirection().multiply(-0.7));
                List<PotionEffect> potionEffects = new ArrayList<>();
                potionEffects.add(new PotionEffect(PotionEffectType.SLOW, 60, 2, false, true, true));

                new BukkitRunnable() {
                    int amountOfTicks = 0;

                    @Override
                    public void run() {
                        move5spearAnimation(spearLocation, direction);
                        Location checkLocation = spearLocation.clone().add(player.getLocation().getDirection().multiply(3));
                        if (
                                world.getBlockAt(checkLocation).getType().isSolid() ||
                                NearbyEntityTools.damageNearbyEntities(player, checkLocation, 5, 0.5, 0.5, 0.5, direction, potionEffects, livingEntity -> livingEntity.setFreezeTicks(Math.min(100, livingEntity.getMaxFreezeTicks()))) ||
                                amountOfTicks > 200
                        ) {
                            this.cancel();
                            move5Impact(checkLocation);
                        }

                        spearLocation.add(direction);
                        amountOfTicks++;
                    }
                }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            }
        };
    }

    // impact animation
    private static void move5Impact(Location location) {
        World world = location.getWorld();
        for (int i = 0; i < 400; i++) {
            ItemStack stack;
            if (StaticVariables.random.nextBoolean()) {
                stack = new ItemStack(Material.ICE);
            } else {
                stack = new ItemStack(Material.SNOW_BLOCK);
            }
            Objects.requireNonNull(world).spawnParticle(Particle.ITEM_CRACK, location.clone().add(StaticVariables.random.nextGaussian() / 3, StaticVariables.random.nextGaussian() / 3, StaticVariables.random.nextGaussian() / 3), 0, 0, 0, 0, stack);

        }
    }

    // spear animation
    public static void move5spearAnimation(Location spearLocation, Vector direction) {
        for (int i = 0; i < 30; i++) {
            Particle.DustOptions dustOptions;
            if (StaticVariables.random.nextBoolean()) {
                dustOptions = new Particle.DustOptions(Color.fromRGB(0, 165, 255), 1f);
            } else {
                dustOptions = new Particle.DustOptions(Color.WHITE, 1f);
            }
            Objects.requireNonNull(spearLocation.getWorld()).spawnParticle(Particle.REDSTONE, spearLocation.clone().add(direction.clone().multiply(0.1 * i)), 0, dustOptions);
        }
    }

    // MOVE 6
    // Snow Stomp
    // -> Turns a pad of ground into powder snow trapping entities standing on it
    public static Runnable move6(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
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
                        activePlayer.addLocationMaterialMapping(block.getLocation(), block.getType());
                        block.setType(Material.POWDER_SNOW);
                    }
                }
            }
        };
    }

    // MOVE 7
    // Deep Freeze
    // -> Throws an ice bal that penetrates trough walls
    // -> If an entity is hit by this ball, it will be unable to move and see

    // move
    public static Runnable move7(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = player.getLocation().add(0, 1.5, 0).add(player.getLocation().getDirection());
            World world = player.getWorld();
            new BukkitRunnable() {
                int amountOfTicks = 0;
                final Location currentLocation = location;

                @Override
                public void run() {
                    Vector playerDirection = player.getLocation().getDirection();
                    for (int i = 0; i < 10; i++) {
                        ItemStack stack;
                        if (StaticVariables.random.nextBoolean()) {
                            stack = new ItemStack(Material.ICE);
                        } else {
                            stack = new ItemStack(Material.SNOW_BLOCK);
                        }
                        world.spawnParticle(Particle.ITEM_CRACK, location.clone().add(StaticVariables.random.nextGaussian() / 10, StaticVariables.random.nextGaussian() / 10, StaticVariables.random.nextGaussian() / 10), 0, 0, 0, 0, 0, stack);
                        if (!world.getNearbyEntities(location, 0.5, 0.5, 0.5).isEmpty()) {
                            for (Entity entity : world.getNearbyEntities(location, 0.5, 0.5, 0.5)) {
                                if (entity != null) {
                                    if (entity instanceof LivingEntity) {
                                        LivingEntity livingEntity = (LivingEntity) entity;
                                        if (livingEntity != player) {
                                            freezeEffect(livingEntity, activePlayer);
                                            this.cancel();
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (amountOfTicks > 800) {
                        this.cancel();
                    }
                    amountOfTicks++;
                    currentLocation.add(playerDirection.multiply(0.1));
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }

    // freeze effect
    private static void freezeEffect(LivingEntity target, ActivePlayer activePlayer) {
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 100, false, false, false));
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 600, 100, false, false, false));
        target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 10, false, false, false));

        Map<Character, Material> characterMaterialMap = new HashMap<>();
        characterMaterialMap.put('I', Material.ICE);
        characterMaterialMap.put('P', Material.PACKED_ICE);
        characterMaterialMap.put('B', Material.BLUE_ICE);
        characterMaterialMap.put('S', Material.POWDER_SNOW);

        Location startLocation = target.getLocation();
        String[][] form1 = {
                {
                        "?PBP?",
                        "BIIIB",
                        "BI*IP",
                        "PPPPI",
                        "?IBB?"
                },
                {
                        "??P??",
                        "?PPI?",
                        "BPSPB",
                        "?PPP?",
                        "??B??"
                },
                {
                        "?????",
                        "??B??",
                        "?ISB?",
                        "??P??",
                        "?????"
                }
        };

        String[][] form2 = {
                {
                        "??I??",
                        "?PBI?",
                        "PP*PB",
                        "?BIP?",
                        "??P??"
                },
                {
                        "?????",
                        "??P??",
                        "?ISB?",
                        "??P??",
                        "?????"
                },
                {
                        "?????",
                        "?????",
                        "??I??",
                        "?????",
                        "?????"
                }
        };

        target.setFreezeTicks(target.getMaxFreezeTicks());

        new BukkitRunnable() {
            int amountOfTicks = 0;

            @Override
            public void run() {
                if (amountOfTicks < 300) {
                    SetBlockTools.setBlocksInWorld(activePlayer, startLocation, form1, characterMaterialMap, true, new ArrayList<>(), new ArrayList<>(), Material.POWDER_SNOW, -1, null, null);
                } else if (amountOfTicks == 300) {
                    activePlayer.resetWorld();
                    SetBlockTools.setBlocksInWorld(activePlayer, startLocation, form2, characterMaterialMap, true, new ArrayList<>(), new ArrayList<>(), Material.POWDER_SNOW, -1, null, null);
                } else if (amountOfTicks < 600) {
                    SetBlockTools.setBlocksInWorld(activePlayer, startLocation, form2, characterMaterialMap, true, new ArrayList<>(), new ArrayList<>(), Material.POWDER_SNOW, -1, null, null);
                } else {
                    this.cancel();
                    activePlayer.resetWorld();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }


    // MOVE 8
    // Ice Beam
    // -> Shoots a beam of ice freezing your targets
    public static Runnable move8(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location playerLocation = player.getLocation();
            World world = player.getWorld();
            Vector direction = playerLocation.getDirection();
            Vector perpendicularDirection = direction.clone().rotateAroundY(90);

            new BukkitRunnable() {
                double angle = 1;
                double angle2 = 180;
                Location particleLocation0;
                Location particleLocation1;
                Location particleLocation2;
                int amountOfTicks;

                @Override
                public void run() {
                    angle += 0.3;
                    angle2 += 0.3;
                    particleLocation0 = playerLocation.clone().add(direction.clone().multiply(2)).add(0, 1.5, 0).add(direction.clone().multiply(0.5 * amountOfTicks));
                    particleLocation1 = playerLocation.clone().add(direction.clone().multiply(2)).add(0, 1.5, 0).add(perpendicularDirection.clone().rotateAroundAxis(direction, angle).multiply(0.5)).add(direction.clone().multiply(0.5 * amountOfTicks));
                    particleLocation2 = playerLocation.clone().add(direction.clone().multiply(2)).add(0, 1.5, 0).add(perpendicularDirection.clone().rotateAroundAxis(direction, angle2).multiply(0.5)).add(direction.clone().multiply(0.5 * amountOfTicks));
                    for (int i = 0; i < 5; i++) {
                        double x1 = particleLocation1.getX() + StaticVariables.random.nextGaussian() / 20;
                        double y1 = particleLocation1.getY() + StaticVariables.random.nextGaussian() / 20;
                        double z1 = particleLocation1.getZ() + StaticVariables.random.nextGaussian() / 20;
                        double x2 = particleLocation2.getX() + StaticVariables.random.nextGaussian() / 20;
                        double y2 = particleLocation2.getY() + StaticVariables.random.nextGaussian() / 20;
                        double z2 = particleLocation2.getZ() + StaticVariables.random.nextGaussian() / 20;
                        world.spawnParticle(Particle.REDSTONE, x1, y1, z1, 0, new Particle.DustOptions(Color.fromRGB(0, 165, 255), 2f));
                        world.spawnParticle(Particle.REDSTONE, x2, y2, z2, 0, new Particle.DustOptions(Color.WHITE, 2f));
                    }

                    if (!world.getNearbyEntities(particleLocation0, 0.5, 0.5, 0.5).isEmpty()) {
                        for (Entity entity : world.getNearbyEntities(particleLocation0, 0.5, 0.5, 0.5)) {
                            if (entity != null) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingEntity = (LivingEntity) entity;
                                    if (livingEntity != player) {
                                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 10, false, true, false));
                                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 10, false, true, false));
                                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 100, false, true, false));
                                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 100, false, true, false));
                                        livingEntity.setFreezeTicks(livingEntity.getMaxFreezeTicks());
                                        livingEntity.damage(5);
                                        this.cancel();
                                        new BukkitRunnable() {
                                            int amountOfTicks = 0;

                                            @Override
                                            public void run() {
                                                Location targetLocation = livingEntity.getLocation();
                                                Location snowballTargetLocation = targetLocation.clone().add(0, 1.5, 0);
                                                if (amountOfTicks % 10 == 0) {
                                                    Location snowBallLocation = MathTools.locationOnCircle(snowballTargetLocation, 6, Math.abs(livingEntity.getLocation().getYaw()), world);
                                                    snowBallLocation.setY(snowBallLocation.getY() + 1);
                                                    Vector velocity = new Vector(snowballTargetLocation.getX() - snowBallLocation.getX(), snowballTargetLocation.getY() - snowBallLocation.getY(), snowballTargetLocation.getZ() - snowBallLocation.getZ());
                                                    Entity entity = world.spawnEntity(snowBallLocation, EntityType.SNOWBALL);
                                                    entity.setVelocity(velocity.multiply(0.2));
                                                }
                                                livingEntity.setFreezeTicks(livingEntity.getMaxFreezeTicks());
                                                for (int i = 0; i < 50; i++) {
                                                    ItemStack stack;
                                                    if (StaticVariables.random.nextBoolean()) {
                                                        stack = new ItemStack(Material.ICE);
                                                    } else {
                                                        stack = new ItemStack(Material.SNOW_BLOCK);
                                                    }
                                                    world.spawnParticle(Particle.ITEM_CRACK, targetLocation.clone().add(StaticVariables.random.nextGaussian() / 3, StaticVariables.random.nextGaussian() / 3, StaticVariables.random.nextGaussian() / 3), 0, 0, 0, 0, 0, stack);
                                                }
                                                if (amountOfTicks > 200 || livingEntity.isDead()) {
                                                    this.cancel();
                                                }
                                                amountOfTicks++;
                                            }
                                        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
                                    }
                                }
                            }
                        }
                    }
                    if (amountOfTicks > 200) {
                        this.cancel();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        };
    }
}