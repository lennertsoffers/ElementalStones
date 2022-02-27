package com.lennertsoffers.elementalstones.moves.airMoves.agility;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.MathTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ChargeJump extends Move {

    /**
     * <b>MOVE 7: Charge Jump</b>
     * <p>
     *     Charges until next activation (max 10 seconds)<br>
     *     The longer you charge, the higher you will jump<br>
     *     The player gets slowness when charging<br>
     * </p>
     *
     * @param activePlayer the activeplayer executing the move
     */
    public ChargeJump(ActivePlayer activePlayer) {
        super(activePlayer, "Charge Jump", "air_stone", "agility_stone", 7);
    }
    
    @Override
    public void useMove() {
        World world = this.getPlayer().getWorld();

        if ((int) this.getActivePlayer().getCharge() == -1) {
            this.getActivePlayer().setMove7LaunchState(1);
            this.getActivePlayer().setChargingStart();
            this.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000, 3, true, false, false));
            int nominator = 10;

            new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {
                    Location location = getPlayer().getLocation().add(0, 1, 0);
                    Location particleLocation = MathTools.locationOnCircle(location.clone().add(0, StaticVariables.random.nextGaussian() / 2, 0), 2, StaticVariables.random.nextInt(180), world);
                    world.spawnParticle(Particle.CLOUD, particleLocation, 0, (location.getX() - particleLocation.getX()) / nominator, (location.getY() - particleLocation.getY()) / nominator, (location.getZ() - particleLocation.getZ()) / nominator);

                    if (amountOfTicks >= 200 || getActivePlayer().getMove7LaunchState() == 2) {
                        this.cancel();
                        if (getActivePlayer().getMove7LaunchState() != 2) {
                            useMove();
                        }
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        } else {
            int nominator = 2000;
            if (this.getActivePlayer().isInAirBoost()) {
                nominator = 1000;
            }
            this.getActivePlayer().setMove7LaunchState(2);
            double velocityY = this.getActivePlayer().getCharge() / nominator;
            this.getActivePlayer().resetCharge();
            this.getPlayer().removePotionEffect(PotionEffectType.SLOW);
            this.getPlayer().setVelocity(new Vector(0, velocityY, 0));
            this.setCooldown();

            new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {
                    world.spawnParticle(Particle.CLOUD, getPlayer().getLocation(), 0);

                    amountOfTicks++;
                    if (amountOfTicks > velocityY * 10) {
                        this.cancel();
                    }
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        }
    }
}
