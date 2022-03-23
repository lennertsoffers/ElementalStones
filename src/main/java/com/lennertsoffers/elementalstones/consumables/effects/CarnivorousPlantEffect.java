package com.lennertsoffers.elementalstones.consumables.effects;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.Boss;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CarnivorousPlantEffect implements ConsumableEffect {

    @Override
    public void playEffect(Player player) {
        World world = player.getWorld();
        Location location = CheckLocationTools.getClosestAirBlockLocation(player.getLocation().add(new Vector(StaticVariables.random.nextInt(10) + 5, 0 ,StaticVariables.random.nextInt(10) + 5)));

        if (location != null) {
            if (StaticVariables.random.nextInt(10) == 0) {
                world.spawnEntity(location, this.randomEntityType(), true);
            } else {
                new Boss(player);
            }
        } else {
            player.sendMessage(ChatColor.RED + "Could not spawn an entity because there are no valid spawn locations!");
        }
    }

    private EntityType randomEntityType() {
        int pick = StaticVariables.random.nextInt(EntityType.values().length);
        return EntityType.values()[pick];
    }
}
