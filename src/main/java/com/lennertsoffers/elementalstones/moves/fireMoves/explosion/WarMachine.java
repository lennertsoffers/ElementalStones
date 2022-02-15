package com.lennertsoffers.elementalstones.moves.fireMoves.explosion;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.GrenadeWarMachineBig;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class WarMachine extends Move {

    public WarMachine(ActivePlayer activePlayer) {
        super(activePlayer, "War Machine", "fire_stone", "explosion_stone", 8);
    }

    @Override
    public void useMove() {
        ActivePlayer activePlayer = this.getActivePlayer();
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        int offset = 20;

        if (activePlayer.hasWarMachineGrenades()) {
            activePlayer.useWarMachineGrenade();
            GrenadeWarMachineBig grenadeWarMachineBig = new GrenadeWarMachineBig(player, 17, Particle.TOTEM, null);
            grenadeWarMachineBig.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else {
            activePlayer.fillWarMachineGrenades();

            new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {
                    Location startLocation = player.getLocation();
                    Vector direction = startLocation.getDirection().setY(0);
                    startLocation.add(direction.clone().multiply(1.5)).add(0, 2, 0).add(direction.clone().rotateAroundY(-Math.PI / 1.5));

                    world.spawnParticle(Particle.TOTEM, startLocation.getX() + StaticVariables.random.nextGaussian() / offset, startLocation.getY() + StaticVariables.random.nextGaussian() / offset, startLocation.getZ() + StaticVariables.random.nextGaussian() / offset, 0);

                    amountOfTicks++;
                    if (!activePlayer.hasWarMachineGrenades() || amountOfTicks > 600) {
                        this.cancel();
                        activePlayer.setWarMachineGrenades(0);
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        }
    }
}
