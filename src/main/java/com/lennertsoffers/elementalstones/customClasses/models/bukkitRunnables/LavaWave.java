package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.SetBlockTools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LavaWave extends BukkitRunnable {

    private final String[][] lavaWavePerpendicular = {
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

    private final String[][] lavaWaveDiagonal = {
            {
                    "AAAAAA",
                    "ALAAAA",
                    "ALLAAA",
                    "ALLLAA",
                    "A*LLLA",
                    "AAAAAA"
            },
            {
                    "AAAAA",
                    "ALAAA",
                    "AALAA",
                    "A*ALA",
                    "AAAAA"
            }
    };

    private int lengthOfWave = 1;
    private final Location location;
    private final ActivePlayer activePlayer;
    private final Map<Character, Material> characterMaterialMap = new HashMap<>();
    private final ArrayList<Material> overrideMaterials = new ArrayList<>();
    private final String[][] finalStringList;

    public LavaWave(ActivePlayer activePlayer, boolean perpendicular) {
        characterMaterialMap.put('A', Material.AIR);
        characterMaterialMap.put('L', Material.LAVA);
        overrideMaterials.add(Material.LAVA);

        this.activePlayer = activePlayer;
        location = activePlayer.getPlayer().getLocation();

        if (perpendicular) {
            finalStringList = lavaWavePerpendicular;
        } else {
            finalStringList = lavaWaveDiagonal;
        }
    }

    @Override
    public void run() {
        location.add(-2, 0, 2);

        SetBlockTools.setBlocksInWorld(activePlayer, location, finalStringList, characterMaterialMap, true, overrideMaterials, null, Material.AIR, 4, new Vector(0, 0, 0), new ArrayList<>());

        if (lengthOfWave % 2 == 0) {
            location.add(-1, 0, 1);
        }
        if (lengthOfWave > 31) {
            // Clear the lava
            this.cancel();
        }
        lengthOfWave++;
    }
}
