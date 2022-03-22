package com.lennertsoffers.elementalstones.consumables.effects;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class BottleOfLightningEffect implements ConsumableEffect {

    @Override
    public void playEffect(Player player) {
        World world = player.getWorld();
        player.sendMessage(ChatColor.YELLOW + "Set weather to thunder");

        world.setStorm(true);
        world.setThundering(true);
        world.setThunderDuration(3000);
    }
}
