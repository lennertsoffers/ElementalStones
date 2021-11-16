package com.lennertsoffers.elementalstones.stones.earthStone;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class DefenseStone extends EarthStone {

    private static ItemStack addEnchantments(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
            itemMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 5, true);
            itemMeta.addEnchant(Enchantment.PROTECTION_FALL, 5, true);
            itemMeta.addEnchant(Enchantment.PROTECTION_FIRE, 5, true);
            itemMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 5, true);
            itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
            item.setItemMeta(itemMeta);
        }
        return item;
    }

    private static void buildPerpendicularWallX(Location location, World world) {
        location.add(0, -1, 3);
        for (int i = 0; i < 15; i++) {
            if (i % 5 == 0) {
                location.add(0, 1, -5);
            }
            world.getBlockAt(location).setType(Material.STONE);
            location.add(0, 0, 1);
        }
    }

    private static void buildPerpendicularWallZ(Location location, World world) {
        location.add(3, -1, 0);
        for (int i = 0; i < 15; i++) {
            if (i % 5 == 0) {
                location.add(-5, 1, 0);
            }
            world.getBlockAt(location).setType(Material.STONE);
            location.add(1, 0, 0);
        }
    }

    private static void buildDiagonalWallX(Location location, World world) {
        location.add(3, -1, 3);
        for (int i = 0; i < 15; i++) {
            if (i % 5 == 0) {
                location.add(-5, 1, -5);
            }
            world.getBlockAt(location).setType(Material.STONE);
            location.add(1, 0, 1);
        }
    }

    private static void buildDiagonalWallZ(Location location, World world) {
        location.add(3, -1, -3);
        for (int i = 0; i < 15; i++) {
            if (i % 5 == 0) {
                location.add(-5, 1, 5);
            }
            world.getBlockAt(location).setType(Material.STONE);
            location.add(1, 0, -1);
        }
    }

    // PASSIVE
    // Stone Resistance
    // -> Player gets the resistance effect when he is holding the stone
    private static void passive() {
        StaticVariables.scheduler.scheduleSyncRepeatingTask(StaticVariables.plugin, () -> {
            for (ActivePlayer activePlayer : ActivePlayer.getActivePlayers()) {
                Player player = activePlayer.getPlayer();
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 220, 1, true, true, true));
            }
        }, 0L, 200L);
    }

    // MOVE 4
    // Smoke Screen
    // -> Following up to flying rock
    // -> Explodes the flying block into a big smoke which makes it impossible to see trough
    public static Runnable move4(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            FallingBlock move4Block = activePlayer.getFallingBlock();
            if (move4Block == null) {
                return;
            }
            World world = player.getWorld();
            Location location = move4Block.getLocation();
            move4Block.remove();
            new BukkitRunnable() {
                int counter = 0;

                @Override
                public void run() {
                    world.spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, location, 800, counter / 2.0, counter / 2.0, counter / 2.0, 0.1);
                    counter++;
                    if (counter >= 20) {
                        this.cancel();
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 5L);
        };
    }

    // MOVE 5
    // Better Gear
    // -> Swaps your current armor for the best possible armor in the game
    // -> After 60s, your normal gear will return
    public static Runnable move5(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            ItemStack helmet;
            ItemStack boots;
            ItemStack chestplate;
            ItemStack leggings;

            helmet = player.getInventory().getHelmet();
            chestplate = player.getInventory().getChestplate();
            leggings = player.getInventory().getLeggings();
            boots = player.getInventory().getBoots();

            ItemStack betterHelmet = DefenseStone.addEnchantments(new ItemStack(Material.NETHERITE_HELMET));
            ItemStack betterChestplate = DefenseStone.addEnchantments(new ItemStack(Material.NETHERITE_CHESTPLATE));
            ItemStack betterLeggings = DefenseStone.addEnchantments(new ItemStack(Material.NETHERITE_LEGGINGS));
            ItemStack betterBoots = DefenseStone.addEnchantments(new ItemStack(Material.NETHERITE_BOOTS));

            player.getInventory().setHelmet(betterHelmet);
            player.getInventory().setChestplate(betterChestplate);
            player.getInventory().setLeggings(betterLeggings);
            player.getInventory().setBoots(betterBoots);

            StaticVariables.scheduler.scheduleSyncDelayedTask(StaticVariables.plugin, () -> {
                player.getInventory().setHelmet(helmet);
                player.getInventory().setChestplate(chestplate);
                player.getInventory().setLeggings(leggings);
                player.getInventory().setBoots(boots);
            }, 1200L);
        };
    }

    // MOVE 6
    // Wall
    // -> Place a stone wall a few blocks before the player to block attacks
    public static Runnable move6(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = player.getLocation();
            float yaw = Math.abs(location.getYaw());
            if ((yaw >= 0 && yaw < 25) || (yaw >= 335 && yaw <= 360)) {
                buildPerpendicularWallZ(location, player.getWorld());
            } else if (yaw >= 25 && yaw < 65) {
                buildDiagonalWallX(location, player.getWorld());
            } else if (yaw >= 65 && yaw < 115) {
                buildPerpendicularWallX(location, player.getWorld());
            } else if (yaw >= 115 && yaw < 155) {
                buildDiagonalWallZ(location, player.getWorld());
            } else if (yaw >= 155 && yaw < 205) {
                buildPerpendicularWallZ(location, player.getWorld());
            } else if (yaw >= 205 && yaw < 245) {
                buildDiagonalWallX(location, player.getWorld());
            } else if (yaw >= 245 && yaw < 295) {
                buildPerpendicularWallX(location, player.getWorld());
            } else {
                buildDiagonalWallZ(location, player.getWorld());
            }
        };
    }

    // MOVE 7
    // Shockwave
    // -> Creates a shockwave around the player knocking up all entities and giving them the slowness effect
    public static Runnable move7(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = player.getLocation();
            List<Entity> entities = player.getNearbyEntities(3, 0, 3);
            int playerX = location.getBlockX() - 3;
            int playerY = location.getBlockY();
            int playerZ = location.getBlockZ() - 3;

            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (!((i == 0 && j == 0) || (i == 0 && j == 6) || (i == 6 && j == 0) || (i == 6 && j == 6))) {
                        ItemStack stack;
                        if (player.getWorld().getBlockAt(player.getLocation().add(0, -1, 0)).getType().equals(Material.AIR)) {
                            stack = new ItemStack(Material.STONE);
                        } else {
                            stack = new ItemStack(player.getWorld().getBlockAt(player.getLocation().add(0, -1, 0)).getType());
                        }
                        player.getWorld().spawnParticle(Particle.ITEM_CRACK, playerX + i, playerY + 0.5, playerZ + j, 50, 1, 0, 1, 0.1, stack);
                        for (Entity entity : entities) {
                            try {
                                LivingEntity livingEntity = (LivingEntity) entity;
                                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 140, 10));
                            } finally {
                                entity.setVelocity(new Vector(0, 0.5, 0));
                            }
                        }
                    }
                }
            }
        };
    }

    // MOVE 8
    // Last Chance
    // -> When something should have killed the player, he gets a last chance by not getting the damage
    // -> All entities in close range are knocked back and the player is protected by a stone bunker
    public static Runnable move8(ActivePlayer activePlayer) {
        return () -> {
            Player player = activePlayer.getPlayer();
            Location location = player.getLocation();
            World world = player.getWorld();
            double playerX = location.getBlockX();
            double playerY = location.getBlockY();
            double playerZ = location.getBlockZ();
            for (Entity entity : player.getNearbyEntities(4, 4, 4)) {
                double entityX = entity.getLocation().getBlockX();
                double entityZ = entity.getLocation().getBlockZ();
                if (entityX == playerX) {
                    playerX = entityX + 0.001;
                }
                if (entityZ == playerZ) {
                    playerZ = entityZ + 0.001;
                }
                entity.setVelocity(new Vector((double) 1 / (entityX - playerX) * 2, 0.5, (double) 1 / (entityZ - playerZ) * 2));
            }
            int playerXint = (int) playerX;
            int playerYint = (int) playerY;
            int playerZint = (int) playerZ;
            if (world.getBlockAt(location.add(0, -1, 0)).getType() != Material.AIR) {
                StaticVariables.scheduler.scheduleSyncDelayedTask(StaticVariables.plugin, () -> {
                    location.add(0, 1, 0);
                    for (int i = 0; i < 3; i++) {
                        world.getBlockAt(playerXint + 2, playerYint + i, playerZint - 1).setType(Material.STONE);
                        world.getBlockAt(playerXint + 2, playerYint + i, playerZint).setType(Material.STONE);
                        world.getBlockAt(playerXint + 2, playerYint + i, playerZint + 1).setType(Material.STONE);
                        world.getBlockAt(playerXint - 1, playerYint + i, playerZint + 2).setType(Material.STONE);
                        world.getBlockAt(playerXint, playerYint + i, playerZint + 2).setType(Material.STONE);
                        world.getBlockAt(playerXint + 1, playerYint + i, playerZint + 2).setType(Material.STONE);
                        world.getBlockAt(playerXint - 2, playerYint + i, playerZint - 1).setType(Material.STONE);
                        world.getBlockAt(playerXint - 2, playerYint + i, playerZint).setType(Material.STONE);
                        world.getBlockAt(playerXint - 2, playerYint + i, playerZint + 1).setType(Material.STONE);
                        world.getBlockAt(playerXint - 1, playerYint + i, playerZint - 2).setType(Material.STONE);
                        world.getBlockAt(playerXint, playerYint + i, playerZint - 2).setType(Material.STONE);
                        world.getBlockAt(playerXint + 1, playerYint + i, playerZint - 2).setType(Material.STONE);

                        world.getBlockAt(playerXint + 1, playerYint + i, playerZint + 1).setType(Material.AIR);
                        world.getBlockAt(playerXint + 1, playerYint + i, playerZint).setType(Material.AIR);
                        world.getBlockAt(playerXint + 1, playerYint + i, playerZint - 1).setType(Material.AIR);
                        world.getBlockAt(playerXint, playerYint + i, playerZint + 1).setType(Material.AIR);
                        world.getBlockAt(playerXint, playerYint + i, playerZint).setType(Material.AIR);
                        world.getBlockAt(playerXint, playerYint + i, playerZint - 1).setType(Material.AIR);
                        world.getBlockAt(playerXint - 1, playerYint + i, playerZint + 1).setType(Material.AIR);
                        world.getBlockAt(playerXint - 1, playerYint + i, playerZint).setType(Material.AIR);
                        world.getBlockAt(playerXint - 1, playerYint + i, playerZint - 1).setType(Material.AIR);
                    }
                    world.getBlockAt(playerXint + 1, playerYint + 3, playerZint + 1).setType(Material.STONE);
                    world.getBlockAt(playerXint + 1, playerYint + 3, playerZint).setType(Material.STONE);
                    world.getBlockAt(playerXint + 1, playerYint + 3, playerZint - 1).setType(Material.STONE);
                    world.getBlockAt(playerXint, playerYint + 3, playerZint + 1).setType(Material.STONE);
                    world.getBlockAt(playerXint, playerYint + 3, playerZint).setType(Material.STONE);
                    world.getBlockAt(playerXint, playerYint + 3, playerZint - 1).setType(Material.STONE);
                    world.getBlockAt(playerXint - 1, playerYint + 3, playerZint + 1).setType(Material.STONE);
                    world.getBlockAt(playerXint - 1, playerYint + 3, playerZint).setType(Material.STONE);
                    world.getBlockAt(playerXint - 1, playerYint + 3, playerZint - 1).setType(Material.STONE);
                }, 5L);
            }
        };
    }
}
