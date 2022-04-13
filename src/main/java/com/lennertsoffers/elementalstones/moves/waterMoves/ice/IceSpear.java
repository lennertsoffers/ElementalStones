package com.lennertsoffers.elementalstones.moves.waterMoves.ice;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.NearbyEntityTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IceSpear extends Move {

    /**
     * <b>MOVE 5: Ice Spear</b>
     * <p>
     *     Throw a spear of ice at your enemy<br>
     *     This spear will give the enemy the freeze effect and slow him down for a brief moment<br>
     *     <ul>
     *         <li><b>Damage:</b> 5</li>
     *         <li><b>Freeze:</b> 5s</li>
     *         <li><b>PotionEffect:</b> Slowness (duration: 3s, amplifier: 2)</li>
     *     </ul>
     * </p>
     * @param activePlayer the activeplayer executing the move
     */
    public IceSpear(ActivePlayer activePlayer) {
        super(activePlayer, "Ice Spear", "water_stone", "ice_stone", 5);
    }

    @Override
    public void useMove() {
        ActivePlayer activePlayer = this.getActivePlayer();
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();
        if (activePlayer.hasIceSpear()) {
            activePlayer.setIceSpear(new BukkitRunnable() {
                @Override
                public void run() {
                    Location spearLocation = player.getLocation().add(0, 1.5, 0).add(player.getLocation().getDirection().rotateAroundY(90).multiply(1.5)).add(player.getLocation().getDirection().multiply(-0.7));
                    animation(spearLocation, player.getLocation().getDirection());
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L));
        } else {
            Vector direction = player.getLocation().getDirection();
            activePlayer.clearIceSpear();
            Location spearLocation = player.getLocation().add(0, 1.5, 0).add(player.getLocation().getDirection().rotateAroundY(90).multiply(1.5)).add(player.getLocation().getDirection().multiply(-0.7));
            List<PotionEffect> potionEffects = new ArrayList<>();
            potionEffects.add(new PotionEffect(PotionEffectType.SLOW, 60, 2, false, true, true));

            new BukkitRunnable() {
                int amountOfTicks = 0;

                @Override
                public void run() {
                    animation(spearLocation, direction);
                    Location checkLocation = spearLocation.clone().add(player.getLocation().getDirection().multiply(3));
                    if (
                            world.getBlockAt(checkLocation).getType().isSolid() ||
                                    NearbyEntityTools.damageNearbyEntities(player, checkLocation, 5, 0.5, 0.5, 0.5, direction, potionEffects, livingEntity -> livingEntity.setFreezeTicks(Math.min(100, livingEntity.getMaxFreezeTicks()))) ||
                                    amountOfTicks > 200
                    ) {
                        this.cancel();
                        impact(checkLocation);
                    }

                    spearLocation.add(direction);
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

            this.setCooldown();
        }
    }

    private void animation(Location spearLocation, Vector direction) {
        for (int i = 0; i < 30; i++) {
            Particle.DustOptions dustOptions;
            if (StaticVariables.random.nextBoolean()) {
                dustOptions = new Particle.DustOptions(Color.fromRGB(0, 165, 255), 1f);
            } else {
                dustOptions = new Particle.DustOptions(Color.WHITE, 1f);
            }
            Objects.requireNonNull(spearLocation.getWorld()).spawnParticle(Particle.REDSTONE, spearLocation.clone().add(direction.clone().multiply(0.1 * i)), 0, dustOptions);
        }
    }

    private void impact(Location location) {
        World world = location.getWorld();

        if (world != null) {
            for (int i = 0; i < 400; i++) {
                ItemStack stack;
                if (StaticVariables.random.nextBoolean()) {
                    stack = new ItemStack(Material.ICE);
                } else {
                    stack = new ItemStack(Material.SNOW_BLOCK);
                }
                world.spawnParticle(Particle.ITEM_CRACK, location.clone().add(StaticVariables.random.nextGaussian() / 3, StaticVariables.random.nextGaussian() / 3, StaticVariables.random.nextGaussian() / 3), 0, 0, 0, 0, stack);
            }
        }
    }
}
