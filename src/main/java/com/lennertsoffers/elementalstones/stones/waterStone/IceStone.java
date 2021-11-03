package com.lennertsoffers.elementalstones.stones.waterStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.customClasses.tools.SetBlockTools;
import com.lennertsoffers.elementalstones.items.ItemStones;
import io.netty.handler.codec.http.multipart.Attribute;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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


    // MOVE 4
    // Ice Shards
    // -> You can throw ice shards damaging entities on impact
    // -> A player can have up to 10 shards

    // Move
    public static void move4(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        if (activePlayer.useIceShard()) {
            Location location = player.getLocation().add(0, 1.5, 0);
            Vector direction = location.getDirection();
            new BukkitRunnable() {
                int amountOfTicks = 1;
                Location midpoint = location.add(direction);
                @Override
                public void run() {
                    for (int i = 0; i < 60; i++) {
                        Particle.DustOptions dustOptions;
                        if (StaticVariables.random.nextBoolean()) {
                            dustOptions = new Particle.DustOptions(Color.fromRGB(0, 165, 255), 0.3f);
                        } else {
                            dustOptions = new Particle.DustOptions(Color.WHITE, 0.3f);
                        }
                        if (world.getBlockAt(midpoint).getType().isSolid()) {
                            this.cancel();
                            move4Impact(midpoint);
                        }
                        for (Entity entity : world.getNearbyEntities(midpoint, 0.3, 0.3, 0.3)) {
                            if (entity != null) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingEntity = (LivingEntity) entity;
                                    if (livingEntity != player) {
                                        livingEntity.damage(1);
                                        livingEntity.setVelocity(direction.setY(0.3));
                                        this.cancel();
                                        move4Impact(midpoint);
                                    }
                                }
                            }
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
    }

    // particles on impact
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
    public static void move5(ActivePlayer activePlayer) {
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
            new BukkitRunnable() {
                int amountOfTicks = 0;
                @Override
                public void run() {
                    move5spearAnimation(spearLocation, direction);
                    Location checkLocation = spearLocation.clone().add(player.getLocation().getDirection().multiply(3));
                    if (world.getBlockAt(checkLocation).getType().isSolid()) {
                        this.cancel();
                        move5Impact(checkLocation);
                    }
                    for (Entity entity : world.getNearbyEntities(checkLocation, direction.getX() * 1.5, direction.getY() * 1.5, direction.getZ() * 1.5)) {
                        if (entity != null) {
                            if (entity instanceof LivingEntity) {
                                LivingEntity livingEntity = (LivingEntity) entity;
                                if (livingEntity != player) {
                                    livingEntity.setFreezeTicks(100);
                                    livingEntity.damage(5);
                                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 2, false, true, true));
                                    move5Impact(checkLocation);
                                }
                            }
                        }
                    }
                    spearLocation.add(direction);
                    if (amountOfTicks > 200) {
                        this.cancel();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        }
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
    public static void move6(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        Block targetBlock = player.getTargetBlockExact(20);
        if (targetBlock == null) {
            return;
        }
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
            Block block = world.getHighestBlockAt(location);
            Block block1 = world.getBlockAt(block.getLocation().add(0, -1, 0));
            Block block2 = world.getBlockAt(block.getLocation().add(0, -2, 0));
            Block block3 = world.getBlockAt(block.getLocation().add(0, -3, 0));
            activePlayer.addLocationMaterialMapping(block.getLocation(), block.getType());
            activePlayer.addLocationMaterialMapping(block1.getLocation(), block1.getType());
            activePlayer.addLocationMaterialMapping(block2.getLocation(), block1.getType());
            activePlayer.addLocationMaterialMapping(block3.getLocation(), block1.getType());
            block.setType(Material.POWDER_SNOW);
            block1.setType(Material.POWDER_SNOW);
            block2.setType(Material.POWDER_SNOW);
            block3.setType(Material.POWDER_SNOW);
        }
    }

    // MOVE 7
    // Deep Freeze
    // -> Throws an ice bal that penetrates trough walls
    // -> If an entity is hit by this ball, it will be unable to move and see

    // move
    public static void move7(ActivePlayer activePlayer) {
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
    }

    // freeze effect
    private static void freezeEffect(LivingEntity target, ActivePlayer activePlayer) {
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 100, false, false, false));
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 100, false, false, false));
        target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 10, false, false, false));
        Location startLocation = target.getLocation();
        String[] form1Layer0 = {
                "?PBP?",
                "BIIIB",
                "BI*IP",
                "PPPPI",
                "?IBB?"
        };
        String[] form1Layer1 = {
                "??P??",
                "?PPI?",
                "BP*PB",
                "?PPP?",
                "??B??"
        };
        String[] form1Layer2 = {
                "?B?",
                "I*B",
                "?P?"
        };
        String[] form2Layer0 = {
                "??I??",
                "?PBI?",
                "PP*PB",
                "?BIP?",
                "??P??"
        };
        String[] form2Layer1 = {
                "?P?",
                "I*B",
                "?P?"
        };
        String[] form2Layer2 = {
                "*"
        };
        Map<Character, Material> characterMaterialMap = new HashMap<>();
        characterMaterialMap.put('I', Material.ICE);
        characterMaterialMap.put('P', Material.PACKED_ICE);
        characterMaterialMap.put('B', Material.BLUE_ICE);

        SetBlockTools.setBlocks(startLocation, form1Layer0, characterMaterialMap, true, Material.POWDER_SNOW, activePlayer);
        SetBlockTools.setBlocks(startLocation.clone().add(0, 1, 0), form1Layer1, characterMaterialMap, true, Material.PACKED_ICE, activePlayer);
        SetBlockTools.setBlocks(startLocation.clone().add(0, 2, 0), form1Layer2, characterMaterialMap, true, Material.PACKED_ICE, activePlayer);
        target.setFreezeTicks(600);
        new BukkitRunnable() {
            @Override
            public void run() {
                activePlayer.resetWorld();
                SetBlockTools.setBlocks(startLocation, form2Layer0, characterMaterialMap, true, Material.POWDER_SNOW, activePlayer);
                SetBlockTools.setBlocks(startLocation.clone().add(0, 1, 0), form2Layer1, characterMaterialMap, true, Material.PACKED_ICE, activePlayer);
                SetBlockTools.setBlocks(startLocation.clone().add(0, 2, 0), form2Layer2, characterMaterialMap, true, Material.PACKED_ICE, activePlayer);
            }
        }.runTaskLater(StaticVariables.plugin, 300L);
        new BukkitRunnable() {
            @Override
            public void run() {
                activePlayer.resetWorld();
            }
        }.runTaskLater(StaticVariables.plugin, 600L);
    }


    // MOVE 8
    // Ice Beam
    // -> Shoots a beam of ice freezing your targets
    public static void move8(ActivePlayer activePlayer) {
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
                                            if (amountOfTicks > 200) {
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
    }

}