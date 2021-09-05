package com.lennertsoffers.elementalstones.stones.fireStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.SetBlockTools;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LavaStone {

    // PASSIVE
    // Passive1: Lava Walker
    public static void passive1(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        Location location = player.getLocation().add(0, -1, 0);
        ArrayList<Location> locationGroup = new ArrayList<>();
        if (CheckLocationTools.lavaAroundPlayer(location)) {
            location.add(2, 0, 2);
            Location startLocation = location.clone();
            for (int i = 1; i <= 25; i++) {
                if (!activePlayer.isInLavaBlockLocations(location)) {
                    locationGroup.add(location.clone());
                    if (
                            !(startLocation.getX() == location.getX() && startLocation.getZ() == location.getZ()) &&
                                    !(startLocation.getX() == location.getX() && startLocation.getZ() - 4 == location.getZ()) &&
                                    !(startLocation.getX() - 4 == location.getX() && startLocation.getZ() == location.getZ()) &&
                                    !(startLocation.getX() - 4 == location.getX() && startLocation.getZ() - 4 == location.getZ())
                    ) {
                        world.getBlockAt(location).setType(Material.BASALT);
                    }
                }
                location.add(-1, 0, 0);
                if (i % 5 == 0) {
                    location.add(5, 0, -1);
                }
            }
        }
        activePlayer.setRemoveBasald(new BukkitRunnable() {
            int blocksRemoved = 1;
            @Override
            public void run() {
                if (locationGroup.size() >= 1) {
                    int index = StaticVariables.random.nextInt(Math.abs(locationGroup.size()));
                    world.getBlockAt(locationGroup.get(index)).setType(Material.LAVA);
                    locationGroup.remove(index);
                }
                if (blocksRemoved >= 25) {
                    this.cancel();
                }
                blocksRemoved++;
            }
        });
    }

    // Passive 2: No damage on magma
    public static void passive2(ActivePlayer activePlayer, EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR) {
            event.setCancelled(true);
        }
    }


    // MOVE 4
    // Reverse Logic
    // -> The player heals over time while standing on magma blocks
    public static void move4(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        new BukkitRunnable() {
            int amountOfTicks = 0;
            @Override
            public void run() {
                Location location = player.getLocation().add(0, 1, 0);
                for (int i = 0; i < 2; i++) {
                    player.getWorld().spawnParticle(Particle.REDSTONE, location.getX() + StaticVariables.random.nextGaussian() / 3, location.getY() + StaticVariables.random.nextGaussian() / 3, location.getZ() + StaticVariables.random.nextGaussian() / 3, 0, 0, 0, 0, new Particle.DustOptions(Color.RED, 1));
                }
                if (amountOfTicks >= 199) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        new BukkitRunnable() {
            int amountOfSecs = 0;
            @Override
            public void run() {
                Location location = player.getLocation();
                if (world.getBlockAt(location.add(0, -1, 0)).getType() == Material.MAGMA_BLOCK) {
                    player.setHealth(player.getHealth() + 3);
                }
                if (amountOfSecs >= 9) {
                    this.cancel();
                }
                amountOfSecs++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 20L);
    }


    // MOVE 5
    // Lava Wave
    // -> Creates a wave of lava in the looking direction
    public static void move5(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        Location playerLocation = player.getLocation();
        float yaw = Math.abs(playerLocation.getYaw());
        Map<Character, Material> characterMaterialMap = new HashMap<>();
        characterMaterialMap.put('A', Material.AIR);
        characterMaterialMap.put('L', Material.LAVA);
        ArrayList<Material> overrideBlocks = new ArrayList<>();
        overrideBlocks.add(Material.LAVA);
        String[] clearAllLavaString = {
                "AAAAAAAAAAAAA",
                "AAAAAAAAAAAAA",
                "AAAAAAAAAAAAA",
                "AAAAAAAAAAAAA",
                "AAAAAAAAAAAAA",
                "AAAAAAAAAAAAA",
                "AAAAAA*AAAAAA",
                "AAAAAAAAAAAAA",
                "AAAAAAAAAAAAA",
                "AAAAAAAAAAAAA",
                "AAAAAAAAAAAAA",
                "AAAAAAAAAAAAA",
                "AAAAAAAAAAAAA"
        };
        BukkitRunnable clearLava = new BukkitRunnable() {
            @Override
            public void run() {
                SetBlockTools.setBlocks(playerLocation, clearAllLavaString, characterMaterialMap, true, overrideBlocks, Material.AIR);
                SetBlockTools.setBlocks(playerLocation.add(0, 1, 0), clearAllLavaString, characterMaterialMap, true, overrideBlocks, Material.AIR);
            }
        };
        if ((yaw >= 0 && yaw < 25) || (yaw >= 335 && yaw <= 360)) {
            playerLocation.add(0, 0, 7);
            String[] stringListBottom = {
                    "AAAAAA",
                    "AALAAA",
                    "ALLLAA",
                    "ALLLA*",
                    "ALLLAA",
                    "AALAAA",
                    "AAAAAA"
            };
            String[] stringListTop = {
                    "AAAAA",
                    "ALAAA",
                    "ALLA*",
                    "ALAAA",
                    "AAAAA",
            };
            new BukkitRunnable() {
                int lengthOfWave = 1;
                @Override
                public void run() {
                    SetBlockTools.setBlocks(playerLocation, stringListBottom, characterMaterialMap, true, overrideBlocks);
                    SetBlockTools.setBlocks(playerLocation.clone().add(0, 1, 0), stringListTop, characterMaterialMap, true, overrideBlocks);
                    if (lengthOfWave % 2 == 0) {
                        playerLocation.add(0, 0, 1);
                    }
                    if (lengthOfWave > 31) {
                        clearLava.runTaskLater(StaticVariables.plugin, 1L);
                        this.cancel();
                    }
                    lengthOfWave++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else if (yaw >= 25 && yaw < 65) {
            playerLocation.add(-2, 0, 2);
            String[] stringListBottom = {
                    "AAAAAA",
                    "ALAAAA",
                    "ALLAAA",
                    "ALLLAA",
                    "A*LLLA",
                    "AAAAAA"
            };
            String[] stringListTop = {
                    "AAAAA",
                    "ALAAA",
                    "AALAA",
                    "A*ALA",
                    "AAAAA"
            };
            new BukkitRunnable() {
                int lengthOfWave = 1;
                @Override
                public void run() {
                    SetBlockTools.setBlocks(playerLocation, stringListBottom, characterMaterialMap, true, overrideBlocks, Material.AIR);
                    SetBlockTools.setBlocks(playerLocation.clone().add(0, 1, 0), stringListTop, characterMaterialMap, true, overrideBlocks, Material.AIR);
                    if (lengthOfWave % 2 == 0) {
                        playerLocation.add(-1, 0, 1);
                    }
                    if (lengthOfWave > 31) {
                        clearLava.runTaskLater(StaticVariables.plugin, 1L);
                        this.cancel();
                    }
                    lengthOfWave++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else if (yaw >= 65 && yaw < 115) {
            playerLocation.add(-1, 0, 0);
            String[] stringListBottom = {
                    "AAAAAAA",
                    "AALLLAA",
                    "ALLLLLA",
                    "AALLLAA",
                    "AAAAAAA",
                    "AAA*AAA",
            };
            String[] stringListTop = {
                    "AAAAAAA",
                    "AAALAAA",
                    "AALLLAA",
                    "AAAAAAA",
                    "AAAAAAA",
                    "AAA*AAA",
            };
            new BukkitRunnable() {
                int lengthOfWave = 1;
                @Override
                public void run() {
                    SetBlockTools.setBlocks(playerLocation, stringListBottom, characterMaterialMap, true, overrideBlocks);
                    SetBlockTools.setBlocks(playerLocation.clone().add(0, 1, 0), stringListTop, characterMaterialMap, true, overrideBlocks);
                    if (lengthOfWave % 2 == 0) {
                        playerLocation.add(-1, 0, 0);
                    }
                    if (lengthOfWave > 31) {
                        clearLava.runTaskLater(StaticVariables.plugin, 1L);
                        this.cancel();
                    }
                    lengthOfWave++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else if (yaw >= 115 && yaw < 155) {
            playerLocation.add(-2, 0, -2);
            String[] stringListBottom = {
                    "AAAAAA",
                    "AAAALA",
                    "AAALLA",
                    "AALLLA",
                    "ALLL*A",
                    "AAAAAA"
            };
            String[] stringListTop = {
                    "AAAAA",
                    "AAALA",
                    "AALAA",
                    "ALA*A",
                    "AAAAA"
            };
            new BukkitRunnable() {
                int lengthOfWave = 1;
                @Override
                public void run() {
                    SetBlockTools.setBlocks(playerLocation, stringListBottom, characterMaterialMap, true, overrideBlocks, Material.AIR);
                    SetBlockTools.setBlocks(playerLocation.clone().add(0, 1, 0), stringListTop, characterMaterialMap, true, overrideBlocks, Material.AIR);
                    if (lengthOfWave % 2 == 0) {
                        playerLocation.add(-1, 0, -1);
                    }
                    if (lengthOfWave > 31) {
                        clearLava.runTaskLater(StaticVariables.plugin, 1L);
                        this.cancel();
                    }
                    lengthOfWave++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else if (yaw >= 155 && yaw < 205) {
            playerLocation.add(0, 0, -1);
            String[] stringListBottom = {
                    "AAAAAA",
                    "AALAAA",
                    "ALLLAA",
                    "ALLLA*",
                    "ALLLAA",
                    "AALAAA",
                    "AAAAAA"
            };
            String[] stringListTop = {
                    "AAAAAA",
                    "AALAAA",
                    "ALLAA*",
                    "AALAAA",
                    "AAAAAA",
            };
            new BukkitRunnable() {
                int lengthOfWave = 1;
                @Override
                public void run() {
                    SetBlockTools.setBlocks(playerLocation, stringListBottom, characterMaterialMap, true, overrideBlocks);
                    SetBlockTools.setBlocks(playerLocation.clone().add(0, 1, 0), stringListTop, characterMaterialMap, true, overrideBlocks);
                    if (lengthOfWave % 2 == 0) {
                        playerLocation.add(0, 0, -1);
                    }
                    if (lengthOfWave > 31) {
                        clearLava.runTaskLater(StaticVariables.plugin, 1L);
                        this.cancel();
                    }
                    lengthOfWave++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else if (yaw >= 205 && yaw < 245) {
            playerLocation.add(2, 0, -2);
            String[] stringListBottom = {
                    "AAAAAA",
                    "ALLL*A",
                    "AALLLA",
                    "AAALLA",
                    "AAAALA",
                    "AAAAAA"
            };
            String[] stringListTop = {
                    "AAAAA",
                    "ALA*A",
                    "AALAA",
                    "AAALA",
                    "AAAAA"
            };
            new BukkitRunnable() {
                int lengthOfWave = 1;
                @Override
                public void run() {
                    SetBlockTools.setBlocks(playerLocation, stringListBottom, characterMaterialMap, true, overrideBlocks, Material.AIR);
                    SetBlockTools.setBlocks(playerLocation.clone().add(0, 1, 0), stringListTop, characterMaterialMap, true, overrideBlocks, Material.AIR);
                    if (lengthOfWave % 2 == 0) {
                        playerLocation.add(1, 0, -1);
                    }
                    if (lengthOfWave > 31) {
                        clearLava.runTaskLater(StaticVariables.plugin, 1L);
                        this.cancel();
                    }
                    lengthOfWave++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else if (yaw >= 245 && yaw < 295) {
            playerLocation.add(7, 0, 0);
            String[] stringListBottom = {
                    "AAAAAAA",
                    "AALLLAA",
                    "ALLLLLA",
                    "AALLLAA",
                    "AAAAAAA",
                    "AAA*AAA",
            };
            String[] stringListTop = {
                    "AAAAAAA",
                    "AALLLAA",
                    "AAALAAA",
                    "AAAAAAA",
                    "AAA*AAA",
            };
            new BukkitRunnable() {
                int lengthOfWave = 1;
                @Override
                public void run() {
                    SetBlockTools.setBlocks(playerLocation, stringListBottom, characterMaterialMap, true, overrideBlocks);
                    SetBlockTools.setBlocks(playerLocation.clone().add(0, 1, 0), stringListTop, characterMaterialMap, true, overrideBlocks);
                    if (lengthOfWave % 2 == 0) {
                        playerLocation.add(1, 0, 0);
                    }
                    if (lengthOfWave > 31) {
                        clearLava.runTaskLater(StaticVariables.plugin, 1L);
                        this.cancel();
                    }
                    lengthOfWave++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else {
            playerLocation.add(2, 0, 2);
            String[] stringListBottom = {
                    "AAAAAA",
                    "A*LLLA",
                    "ALLLAA",
                    "ALLAAA",
                    "ALAAAA",
                    "AAAAAA"
            };
            String[] stringListTop = {
                    "AAAAA",
                    "A*ALA",
                    "AALAA",
                    "ALAAA",
                    "AAAAA"
            };
            new BukkitRunnable() {
                int lengthOfWave = 1;
                @Override
                public void run() {
                    SetBlockTools.setBlocks(playerLocation, stringListBottom, characterMaterialMap, true, overrideBlocks, Material.AIR);
                    SetBlockTools.setBlocks(playerLocation.clone().add(0, 1, 0), stringListTop, characterMaterialMap, true, overrideBlocks, Material.AIR);
                    if (lengthOfWave % 2 == 0) {
                        playerLocation.add(1, 0, 1);
                    }
                    if (lengthOfWave > 31) {
                        clearLava.runTaskLater(StaticVariables.plugin, 1L);
                        this.cancel();
                    }
                    lengthOfWave++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        }


    }

    // MOVE 6
    // Rift
    // -> Creates a gap in the earth in the direction of the player filled with lava


    // MOVE 7
    // Lava Burst
    // -> The blocks where the player is looking at burst open creating an intense flow of lava
    public static void move7(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        Location midpoint = Objects.requireNonNull(player.getTargetBlockExact(25)).getLocation();

        Material materialCenter = world.getBlockAt(midpoint).getType();
        Material materialUp = world.getBlockAt(midpoint.clone().add(1, 0, 0)).getType();
        Material materialDown = world.getBlockAt(midpoint.clone().add(-1, 0, 0)).getType();
        Material materialLeft = world.getBlockAt(midpoint.clone().add(0, 0, -1)).getType();
        Material materialRight = world.getBlockAt(midpoint.clone().add(1, 0, 0)).getType();

        world.getBlockAt(midpoint).setType(Material.AIR);
        world.getBlockAt(midpoint.clone().add(1, 0, 0)).setType(Material.AIR);
        world.getBlockAt(midpoint.clone().add(-1, 0, 0)).setType(Material.AIR);
        world.getBlockAt(midpoint.clone().add(0, 0, -1)).setType(Material.AIR);
        world.getBlockAt(midpoint.clone().add(0, 0, 1)).setType(Material.AIR);

        ArrayList<Location> lavaLocations = new ArrayList<>();
        String[] lavaBeamCrossSection = {
                "AAAAA",
                "AALAA",
                "AL*LA",
                "AALAA",
                "AAAAA",
        };
        String[] lavaRemoveCrossSection = {
                "AAAAAAA",
                "AAAAAAA",
                "AAAAAAA",
                "AAA*AAA",
                "AAAAAAA",
                "AAAAAAA",
                "AAAAAAA"
        };
        Map<Character, Material> characterMaterialMap = new HashMap<>();
        characterMaterialMap.put('A', Material.AIR);
        characterMaterialMap.put('L', Material.LAVA);
        characterMaterialMap.put('S', Material.STONE);
        new BukkitRunnable() {
            int amountOfTicks = 0;
            @Override
            public void run() {
                for (Location location : lavaLocations) {
                    for (Entity entity : world.getNearbyEntities(location, 0, 0, 0)) {
                        if (entity instanceof LivingEntity) {
                            LivingEntity livingEntity = (LivingEntity) entity;
//                            if (!(livingEntity == player)) {
                                livingEntity.setVelocity(new Vector(0, 2, 0));
                                livingEntity.setFireTicks(100);
//                            }
                        }
                    }
                }
                if (amountOfTicks > 70) {
                    this.cancel();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        new BukkitRunnable() {
            int height = 0;
            @Override
            public void run() {
                Location variableMidpoint = midpoint.clone();
                for (int i = 0; i < height; i++) {
                    for (Location location : SetBlockTools.setBlocks(variableMidpoint, lavaBeamCrossSection, characterMaterialMap, true, Material.LAVA, activePlayer)) {
                        if (!lavaLocations.contains(location)) {
                            lavaLocations.add(location);
                        }
                    }
                    variableMidpoint.add(0, 1, 0);
                }
                if (height > 20) {
                    new BukkitRunnable() {
                        int amountOfTicks = 0;
                        @Override
                        public void run() {
                            final ArrayList<Material> overrideBlocks = new ArrayList<>();
                            overrideBlocks.add(Material.LAVA);
                            Location variableMidpoint = midpoint.clone();
                            for (int i = 0; i < 22; i++) {
                                SetBlockTools.setBlocks(variableMidpoint, lavaBeamCrossSection, characterMaterialMap, true, overrideBlocks, Material.LAVA, activePlayer);
                                variableMidpoint.add(0, 1, 0);
                            }
                            if (amountOfTicks > 50) {
                                new BukkitRunnable() {
                                    int height = 21;
                                    @Override
                                    public void run() {
                                        final ArrayList<Material> overrideBlocks = new ArrayList<>();
                                        overrideBlocks.add(Material.LAVA);
                                        Location variableMidpoint = midpoint.clone();
                                        for (int i = 0; i < height; i++) {
                                            SetBlockTools.setBlocks(variableMidpoint, lavaBeamCrossSection, characterMaterialMap, true, overrideBlocks, Material.LAVA, activePlayer);
                                            variableMidpoint.add(0, 1, 0);
                                        }
                                        for (int i = height; i <= 21; i++) {
                                            SetBlockTools.setBlocks(variableMidpoint, lavaRemoveCrossSection, characterMaterialMap, true, overrideBlocks, Material.AIR, activePlayer);
                                            variableMidpoint.add(0, 1, 0);
                                        }
                                        if (height <= 0) {
                                            new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    world.getBlockAt(midpoint).setType(materialCenter);
                                                    world.getBlockAt(midpoint.clone().add(1, 0, 0)).setType(materialUp);
                                                    world.getBlockAt(midpoint.clone().add(-1, 0, 0)).setType(materialDown);
                                                    world.getBlockAt(midpoint.clone().add(0, 0, -1)).setType(materialLeft);
                                                    world.getBlockAt(midpoint.clone().add(0, 0, 1)).setType(materialRight);
                                                }
                                            }.runTaskLater(StaticVariables.plugin, 10L);
                                            this.cancel();
                                        }
                                        height--;
                                    }
                                }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
                                this.cancel();
                            }
                            amountOfTicks++;
                        }
                    }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
                    this.cancel();
                }
                height++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }

    // MOVE 8
    // Eruption

}
