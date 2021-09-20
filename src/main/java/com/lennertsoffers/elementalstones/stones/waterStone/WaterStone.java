package com.lennertsoffers.elementalstones.stones.waterStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.tools.SetBlockTools;
import com.lennertsoffers.elementalstones.customClasses.tools.StringListTools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WaterStone {

    // MOVE 1
    // Ocean splitter
    // -> Splits the water in front of the player

    // MOVE 2
    // Water Arms
    // -> Create two arms of water next to the player
    // -> Only further use in combination with other moves
    public static void move2(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        int amountOfArms;
        if (activePlayer.hasWaterArms()) {
            amountOfArms = 1;
            activePlayer.setWaterArmStage(activePlayer.getWaterArmStage() - 1);
            activePlayer.clearWaterArms();
            if (activePlayer.getWaterArmStage() == 0) {
                activePlayer.setWaterArmStage(0);
                placeWaterArms(activePlayer, new String[] {
                        "AAAAAAAAAAA",
                        "AAAAAAAAAAA",
                        "AAAAAAAAAAA",
                        "AAAAAAAAAAA",
                        "AAAAAAAAAAA",
                        "AAAAA*AAAAA",
                        "AAAAAAAAAAA",
                        "AAAAAAAAAAA",
                        "AAAAAAAAAAA",
                        "AAAAAAAAAAA",
                        "AAAAAAAAAAA"}
                        );
                return;
            }
        } else {
            amountOfArms = 2;
            activePlayer.setWaterArmStage(2);
        }
        activePlayer.setWaterArms(new BukkitRunnable() {
            @Override
            public void run() {
                String[] armsForm0;
                String[] armsForm1;
                String[] armsForm2;
                if (amountOfArms == 2) {
                    armsForm0 = new String[] {
                            "AAAAAAAAA",
                            "AAAAAAAAA",
                            "AABAAABAA",
                            "AABAAABAA",
                            "AABA*ABAA",
                            "AABAAABAA",
                            "AAAAAAAAA",
                            "AAAAAAAAA"
                    };
                    armsForm1 = new String[] {
                            "AAAAAAAAAAAAA",
                            "AAAAAAAAAAAAA",
                            "AAABAAABAAAAA",
                            "AAABAAABAAAAA",
                            "AAAABA*ABAAAA",
                            "AAAABAAABAAAA",
                            "AAAAAAAAAAAAA",
                            "AAAAAAAAAAAAA"
                    };
                    armsForm2 = new String[] {
                            "AAAAAAAAAAAAA",
                            "AAAAAAAAAAAAA",
                            "AAAAABAAAAAAA",
                            "AAAAAABAAAAAA",
                            "AAAAAAABAAAAA",
                            "AABAAAAABAAAA",
                            "AAABAA*AAAAAA",
                            "AAAABAAAAAAAA",
                            "AAAAABAAAAAAA",
                            "AAAAAAAAAAAAA",
                            "AAAAAAAAAAAAA"
                    };
                } else {
                    armsForm0 = new String[] {
                            "AAAAAAAAA",
                            "AAAAAAAAA",
                            "AAAAAABAA",
                            "AAAAAABAA",
                            "AAAA*ABAA",
                            "AAAAAABAA",
                            "AAAAAAAAA",
                            "AAAAAAAAA"
                    };
                    armsForm1 = new String[] {
                            "AAAAAAAAAAAAA",
                            "AAAAAAAAAAAAA",
                            "AAAAAAABAAAAA",
                            "AAAAAAABAAAAA",
                            "AAAAAA*ABAAAA",
                            "AAAAAAAABAAAA",
                            "AAAAAAAAAAAAA",
                            "AAAAAAAAAAAAA"
                    };
                    armsForm2 = new String[] {
                            "AAAAAAAAAAAAA",
                            "AAAAAAAAAAAAA",
                            "AAAAABAAAAAAA",
                            "AAAAAABAAAAAA",
                            "AAAAAAABAAAAA",
                            "AAAAAAAABAAAA",
                            "AAAAAA*AAAAAA",
                            "AAAAAAAAAAAAA",
                            "AAAAAAAAAAAAA",
                            "AAAAAAAAAAAAA",
                            "AAAAAAAAAAAAA"
                    };
                }
                Location location = player.getLocation();
                float yaw = Math.abs(location.getYaw());
                double startVal = 11.25;
                if (yaw >= (startVal * 31) || yaw < (startVal)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorY(StringListTools.rotate(armsForm0)));
                } else if (yaw >= (startVal) && yaw < (startVal * 3)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorY(StringListTools.rotate(armsForm1)));
                } else if (yaw >= (startVal * 3) && yaw < (startVal * 5)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorY(StringListTools.rotate(armsForm2)));
                } else if (yaw >= (startVal * 5) && yaw < (startVal * 7)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorY(armsForm1));
                } else if (yaw >= (startVal * 7) && yaw < (startVal * 9)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorY(armsForm0));
                } else if (yaw >= (startVal * 9) && yaw < (startVal * 11)) {
                    placeWaterArms(activePlayer, armsForm1);
                } else if (yaw >= (startVal * 11) && yaw < (startVal * 13)) {
                    placeWaterArms(activePlayer, armsForm2);
                } else if (yaw >= (startVal * 13) && yaw < (startVal * 15)) {
                    placeWaterArms(activePlayer, StringListTools.rotate(armsForm1));
                } else if (yaw >= (startVal * 15) && yaw < (startVal * 17)) {
                    placeWaterArms(activePlayer, StringListTools.rotate(armsForm0));
                } else if (yaw >= (startVal * 17) && yaw < (startVal * 19)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorX(StringListTools.rotate(armsForm1)));
                } else if (yaw >= (startVal * 19) && yaw < (startVal * 21)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorX(StringListTools.rotate(armsForm2)));
                } else if (yaw >= (startVal * 21) && yaw < (startVal * 23)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorX(armsForm1));
                } else if (yaw >= (startVal * 23) && yaw < (startVal * 25)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorX(armsForm0));
                } else if (yaw >= (startVal * 25) && yaw < (startVal * 27)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorX(StringListTools.mirrorY(armsForm1)));
                } else if (yaw >= (startVal * 27) && yaw < (startVal * 29)) {
                    placeWaterArms(activePlayer, StringListTools.mirrorX(StringListTools.mirrorY(armsForm2)));
                } else if (yaw >= (startVal * 29) && yaw < (startVal * 31)) {
                    placeWaterArms(activePlayer, StringListTools.rotate(StringListTools.mirrorX(StringListTools.mirrorY(armsForm1))));
                }
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L));
    }

    // place water arms in world
    private static void placeWaterArms(ActivePlayer activePlayer, String[] armsForm) {
        Player player = activePlayer.getPlayer();
        if (player.getVelocity().length() < 0.5) {
            Location location = player.getLocation().add(0, 1, 0);
            String[] airLayer = {
                    "AAAAAAAAAAA",
                    "AAAAAAAAAAA",
                    "AAAAAAAAAAA",
                    "AAAAAAAAAAA",
                    "AAAAAAAAAAA",
                    "AAAAA*AAAAA",
                    "AAAAAAAAAAA",
                    "AAAAAAAAAAA",
                    "AAAAAAAAAAA",
                    "AAAAAAAAAAA",
                    "AAAAAAAAAAA"
            };
            Map<Character, Material> characterMaterialMap = new HashMap<>();
            characterMaterialMap.put('A', Material.AIR);
            characterMaterialMap.put('B', Material.WATER);

            SetBlockTools.setBlocks(location.clone().add(0, -1, 0), airLayer, characterMaterialMap, true, Material.AIR, activePlayer, activePlayer.getOverrideLocations());
            SetBlockTools.setBlocks(location, airLayer, characterMaterialMap, true, Material.AIR, activePlayer, activePlayer.getOverrideLocations());
            SetBlockTools.setBlocks(location.clone().add(0, 1, 0), armsForm, characterMaterialMap, true, Material.AIR, activePlayer, activePlayer.getOverrideLocations());
            SetBlockTools.setBlocks(location.clone().add(0, 2, 0), airLayer, characterMaterialMap, true, Material.AIR, activePlayer, activePlayer.getOverrideLocations());
            SetBlockTools.setBlocks(location.clone().add(0, 3, 0), airLayer, characterMaterialMap, true, Material.AIR, activePlayer, activePlayer.getOverrideLocations());
        } else {
            for (Location location : activePlayer.getOverrideLocations()) {
                location.getBlock().setType(Material.AIR);
            }
        }
    }

    // MOVE 3
    // Water Spear
    // -> Throw one of your water arms that damages entities on impact
    // -> Creates splash damage
    public static void move3(ActivePlayer activePlayer) {
        if (activePlayer.hasWaterArms()) {
            move2(activePlayer);
            Player player = activePlayer.getPlayer();
            ArrayList<Location> spearLocations = new ArrayList<>();
            Vector initialDirection = player.getLocation().getDirection();
            Location startLocation = player.getLocation().add(initialDirection.rotateAroundY(180)).add(0, 2, 0);
            Vector direction = player.getLocation().getDirection().multiply(0.9);
            new BukkitRunnable() {
                final Location location = startLocation.clone();
                int distance = 0;

                @Override
                public void run() {
                    Location currentLocation = location.clone();
                    for (Entity entity : player.getWorld().getNearbyEntities(currentLocation, 1, 1, 1)) {
                        if (entity instanceof LivingEntity) {
                            LivingEntity livingEntity = (LivingEntity) entity;
                            if (livingEntity != player) {
                                livingEntity.damage(5);
                            }
                        }
                    }
                    spearLocations.add(currentLocation);
                    for (Location location : spearLocations) {
                        if (placeWaterBlock(location, false)) {
                            this.cancel();
                            new BukkitRunnable() {
                                final ArrayList<Location> waterLocationsToRemove = spearLocations;

                                @Override
                                public void run() {
                                    if (waterLocationsToRemove.size() > 0) {
                                        placeWaterBlock(waterLocationsToRemove.get(0), true);
                                        waterLocationsToRemove.remove(0);
                                    } else {
                                        this.cancel();
                                    }
                                }
                            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
                        }
                    }
                    location.add(direction);
                    if (spearLocations.size() > 6) {
                        placeWaterBlock(spearLocations.get(0), true);
                        spearLocations.remove(0);
                    }
                    if (distance > 70) {
                        this.cancel();
                        for (Location location : spearLocations) {
                            location.getBlock().setType(Material.AIR);
                        }
                    }
                    distance++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        }
    }

    // place blocks if not already water
    private static boolean placeWaterBlock(Location location, boolean remove) {
        Location locationTop = location.clone().add(0, 1, 0);
        Location locationBottom = location.clone().add(0, -1, 0);
        if (remove) {
            if (location.getBlock().getType() == Material.WATER) {
                location.getBlock().setType(Material.AIR);
            }
        } else {
            if (location.getBlock().getType() == Material.AIR) {
                location.getBlock().setType(Material.WATER);
            } else {
                if (location.getBlock().getType() != Material.WATER || (locationBottom.getBlock().getType() != Material.WATER && locationBottom.getBlock().getType() != Material.AIR)) {
                    return true;
                }
            }
        }
        if (locationTop.getBlock().getType() == Material.AIR) {
            locationTop.getBlock().setType(Material.AIR);
        }
        if (locationBottom.getBlock().getType() == Material.AIR || locationBottom.getBlock().getType() == Material.WATER) {
            locationBottom.getBlock().setType(Material.AIR);
        }
        return false;
    }
}

























