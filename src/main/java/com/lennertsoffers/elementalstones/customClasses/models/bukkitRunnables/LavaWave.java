package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.SetBlockTools;
import com.lennertsoffers.elementalstones.customClasses.tools.StringListTools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LavaWave extends BukkitRunnable {

    private int lengthOfWave = 1;
    private final Location location;
    private final ActivePlayer activePlayer;
    private final Map<Character, Material> characterMaterialMap = new HashMap<>();
    private final ArrayList<Material> overrideMaterials = new ArrayList<>();
    private String[][] stringList;
    private final Vector direction;

    public LavaWave(ActivePlayer activePlayer, boolean perpendicular, boolean var0, boolean var1) {
        characterMaterialMap.put('A', Material.AIR);
        characterMaterialMap.put('L', Material.LAVA);
        overrideMaterials.add(Material.LAVA);

        this.activePlayer = activePlayer;
        location = activePlayer.getPlayer().getLocation().add(0, -3, 0);

        if (perpendicular) {
            stringList = new String[][]{
                    {
                        "AAAAAAA",
                        "AAAAAAA",
                        "AAAAAAA",
                        "AAAAAAA",
                        "AAAAAAA",
                        "AAA*AAA",
                    },
                    {
                        "AAAAAAA",
                        "AALLLAA",
                        "ALLLLLA",
                        "AALLLAA",
                        "AAAAAAA",
                        "AAA*AAA",
                    },
                    {
                        "AAAAAAA",
                        "AAALAAA",
                        "AALLLAA",
                        "AAAAAAA",
                        "AAAAAAA",
                        "AAA*AAA",
                    }
            };

            if (var0) {
                if (var1) {
                    this.direction = new Vector(0, 0, 1);
                    StringListTools.mirrorY(StringListTools.rotate(stringList));
                } else {
                    this.direction = new Vector(1, 0, 0);
                    StringListTools.mirrorX(stringList);
                }
            } else {
                if (var1) {
                    this.direction = new Vector(-1, 0, 0);
                } else {
                    this.direction = new Vector(0, 0, -1);
                    StringListTools.rotate(stringList);
                }
            }
        } else {
            stringList = new String[][]{
                    {
                        "AAAAAA",
                        "AAAAAA",
                        "AAAAAA",
                        "AAAAAA",
                        "A*AAAA",
                        "AAAAAA",
                    },
                    {
                        "AAAAAA",
                        "ALAAAA",
                        "ALLAAA",
                        "ALLLAA",
                        "A*LLLA",
                        "AAAAAA"
                    },
                    {
                        "AAAAAA",
                        "AAAAAA",
                        "ALAAAA",
                        "AALAAA",
                        "A*ALAA",
                        "AAAAAA"
                    }
            };

            if (var0) {
                if (var1) {
                    this.location.add(1, 0, 1);
                    this.direction = new Vector(1, 0, 1);
                    StringListTools.mirrorY(StringListTools.rotate(stringList));
                } else {
                    this.location.add(-1, 0, 1);
                    this.direction = new Vector(-1, 0, 1);
                }
            } else {
                if (var1) {
                    this.location.add(-1, 0, -1);
                    this.direction = new Vector(-1, 0, -1);
                    StringListTools.mirrorX(StringListTools.rotate(stringList));
                } else {
                    this.location.add(1, 0, -1);
                    this.direction = new Vector(1, 0, -1);
                    StringListTools.rotate(stringList);
                }
            }
        }
    }

    @Override
    public void run() {
        SetBlockTools.setBlocksInWorld(activePlayer, location, stringList, characterMaterialMap, true, overrideMaterials, new ArrayList<>(), Material.AIR, 4, new Vector(0, 0, 0), new ArrayList<>());

        if (lengthOfWave % 2 == 0) {
            location.add(direction);
        }
        if (lengthOfWave > 100) {
            this.stringList = new String[][] {
                {
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
                },
                {
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
                },
                {
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
                }
            };

            SetBlockTools.setBlocksInWorld(activePlayer, location, stringList, characterMaterialMap, true, overrideMaterials, new ArrayList<>(), Material.AIR, -1, null, new ArrayList<>());

            this.cancel();
        }
        lengthOfWave++;
    }
}
