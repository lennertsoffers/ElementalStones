package com.lennertsoffers.elementalstones.moves.waterMoves.ice;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
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

public class IceShards extends Move {

    /**
     * <b>MOVE 4: Ice Shards</b>
     * <p>
     *     You can throw ice shards damaging entities on impact<br>
     *     It stops when colliding with a block<br>
     *     <ul>
     *         <li><b>Damage:</b> 2</li>
     *         <li><b>Ammo:</b> 10</li>
     *         <li><b>Freeze:</b> +2s</li>
     *         <li><b>PotionEffect:</b> Slowness (duration: 2s, amplifier: 2)</li>
     *     </ul>
     * </p>
     * @param activePlayer the activeplayer executing the move
     */
    public IceShards(ActivePlayer activePlayer) {
        super(activePlayer, "Ice Shards", "water_stone", "ice_stone", 4);
    }

    @Override
    public void useMove() {
        ActivePlayer activePlayer = this.getActivePlayer();
        Player player = activePlayer.getPlayer();
        World world = player.getWorld();

        if (activePlayer.useIceShard()) {
            Location location = player.getLocation().add(0, 1.5, 0);
            Vector direction = location.getDirection();
            List<PotionEffect> potionEffects = new ArrayList<>();
            potionEffects.add(new PotionEffect(PotionEffectType.SLOW, 40, 2, true, true, true));

            new BukkitRunnable() {
                int amountOfTicks = 1;
                final Location midpoint = location.add(direction);

                @Override
                public void run() {
                    if (
                            world.getBlockAt(midpoint).getType().isSolid() ||
                            NearbyEntityTools.damageNearbyEntities(player, midpoint, 2, 0.3, 0.3, 0.3, direction.clone().setY(0.3), potionEffects, livingEntity -> livingEntity.setFreezeTicks(Math.min(livingEntity.getFreezeTicks() + 40, livingEntity.getMaxFreezeTicks())))
                    ) {
                        this.cancel();
                        impact(midpoint);
                    }

                    for (int i = 0; i < 60; i++) {
                        Particle.DustOptions dustOptions;
                        if (StaticVariables.random.nextBoolean()) {
                            dustOptions = new Particle.DustOptions(Color.fromRGB(0, 165, 255), 0.3f);
                        } else {
                            dustOptions = new Particle.DustOptions(Color.WHITE, 0.3f);
                        }

                        world.spawnParticle(Particle.REDSTONE, midpoint.clone().add(StaticVariables.random.nextGaussian() / 20, StaticVariables.random.nextGaussian() / 20, StaticVariables.random.nextGaussian() / 20), 0, direction.getX(), direction.getY(), direction.getZ(), dustOptions);
                        if (i % 2 == 0) {
                            midpoint.add(direction.getX() / 30, direction.getY() / 30, direction.getZ() / 30);
                        }
                    }
                    if (amountOfTicks > 10) {
                        this.cancel();
                    }
                    amountOfTicks++;
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        }
    }

    private void impact(Location impactLocation) {
        World world = impactLocation.getWorld();

        if (world != null) {
            for (int i = 0; i < 5; i++) {
                ItemStack stack;
                if (StaticVariables.random.nextBoolean()) {
                    stack = new ItemStack(Material.ICE);
                } else {
                    stack = new ItemStack(Material.SNOW_BLOCK);
                }
                world.spawnParticle(Particle.ITEM_CRACK, impactLocation.clone().add(StaticVariables.random.nextGaussian() / 10, StaticVariables.random.nextGaussian() / 10, StaticVariables.random.nextGaussian() / 10), 0, 0, 0, 0, stack);
            }
        }
    }
}
