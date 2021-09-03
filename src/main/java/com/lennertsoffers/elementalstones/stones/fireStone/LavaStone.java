package com.lennertsoffers.elementalstones.stones.fireStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import com.lennertsoffers.elementalstones.customClasses.tools.SetBlockTools;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

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


    // MOVE 7


    // MOVE 8
}
