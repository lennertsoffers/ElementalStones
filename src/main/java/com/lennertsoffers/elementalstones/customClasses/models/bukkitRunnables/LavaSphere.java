package com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.CheckLocationTools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class LavaSphere extends BukkitRunnable {
    private int amountOfTicks = 0;
    private final Player player;
    private final ActivePlayer activePlayer;
    private final World world;
    private final List<Block> lavaBlocks = new ArrayList<>();

    public LavaSphere(ActivePlayer activePlayer) {
        this.activePlayer = activePlayer;
        this.player = activePlayer.getPlayer();
        this.world = player.getWorld();
    }

    @Override
    public void run() {
        Location centerLocation = this.player.getLocation().add(0, -2, 0);

        this.clearBlocks();

        CheckLocationTools.getSphere5Locations(centerLocation).forEach(location -> {
            Block block = this.world.getBlockAt(location);

            if (block.getType() == Material.AIR) {
                block.setType(Material.LAVA);
                this.lavaBlocks.add(block);
                this.activePlayer.getLavaLocations().add(block.getLocation());
            }
        });

        if (this.amountOfTicks > 1200) {
            this.cancel();
            this.player.setAllowFlight(false);
            this.player.setFlying(false);
            this.player.setVelocity(new Vector(0, 1, 0));

            new BukkitRunnable() {
                @Override
                public void run() {
                    clearBlocks();
                }
            }.runTaskLater(StaticVariables.plugin, 40L);
        }
        this.amountOfTicks++;
    }

    private void clearBlocks() {
        this.lavaBlocks.forEach(block -> block.setType(Material.AIR));
        this.lavaBlocks.clear();
    }
}
