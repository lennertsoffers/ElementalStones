package com.lennertsoffers.elementalstones.moves.waterMoves.ice;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.tools.SetBlockTools;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeepFreeze extends Move {

    public DeepFreeze(ActivePlayer activePlayer) {
        super(activePlayer, "Deep Freeze", "water_stone", "ice_stone", 7);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();
        Location location = player.getLocation().add(0, 1.5, 0).add(player.getLocation().getDirection());
        World world = player.getWorld();
        new BukkitRunnable() {
            int amountOfTicks = 0;
            final Location currentLocation = location;

            @Override
            public void run() {
                Vector playerDirection = player.getLocation().getDirection();
                for (int i = 0; i < 10; i++) {
                    ItemStack stack;
                    if (StaticVariables.random.nextBoolean()) {
                        stack = new ItemStack(Material.ICE);
                    } else {
                        stack = new ItemStack(Material.SNOW_BLOCK);
                    }
                    world.spawnParticle(Particle.ITEM_CRACK, location.clone().add(StaticVariables.random.nextGaussian() / 10, StaticVariables.random.nextGaussian() / 10, StaticVariables.random.nextGaussian() / 10), 0, 0, 0, 0, 0, stack);
                    if (!world.getNearbyEntities(location, 0.5, 0.5, 0.5).isEmpty()) {
                        for (Entity entity : world.getNearbyEntities(location, 0.5, 0.5, 0.5)) {
                            if (entity != null) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity livingEntity = (LivingEntity) entity;
                                    if (livingEntity != player) {
                                        freezeEffect(livingEntity);
                                        this.cancel();
                                    }
                                }
                            }
                        }
                    }
                }

                if (amountOfTicks > 800) {
                    this.cancel();
                }
                amountOfTicks++;
                currentLocation.add(playerDirection.multiply(0.1));
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);

        this.setCooldown();
    }

    private void freezeEffect(LivingEntity target) {
        ActivePlayer activePlayer = this.getActivePlayer();

        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 3, false, false, false));
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 600, 3, false, false, false));
        target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 3, false, false, false));

        Map<Character, Material> characterMaterialMap = new HashMap<>();
        characterMaterialMap.put('I', Material.ICE);
        characterMaterialMap.put('P', Material.PACKED_ICE);
        characterMaterialMap.put('B', Material.BLUE_ICE);
        characterMaterialMap.put('S', Material.POWDER_SNOW);

        Location startLocation = target.getLocation();
        String[][] form1 = {
                {
                        "?PBP?",
                        "BIIIB",
                        "BI*IP",
                        "PPPPI",
                        "?IBB?"
                },
                {
                        "??P??",
                        "?PPI?",
                        "BPSPB",
                        "?PPP?",
                        "??B??"
                },
                {
                        "?????",
                        "??B??",
                        "?ISB?",
                        "??P??",
                        "?????"
                }
        };

        String[][] form2 = {
                {
                        "??I??",
                        "?PBI?",
                        "PP*PB",
                        "?BIP?",
                        "??P??"
                },
                {
                        "?????",
                        "??P??",
                        "?ISB?",
                        "??P??",
                        "?????"
                },
                {
                        "?????",
                        "?????",
                        "??I??",
                        "?????",
                        "?????"
                }
        };

        target.setFreezeTicks(target.getMaxFreezeTicks());

        new BukkitRunnable() {
            int amountOfTicks = 0;

            @Override
            public void run() {
                if (amountOfTicks < 300) {
                    SetBlockTools.setBlocksInWorld(activePlayer, startLocation, form1, characterMaterialMap, true, new ArrayList<>(), new ArrayList<>(), Material.POWDER_SNOW, -1, null, null);
                } else if (amountOfTicks == 300) {
                    activePlayer.resetWorld();
                    SetBlockTools.setBlocksInWorld(activePlayer, startLocation, form2, characterMaterialMap, true, new ArrayList<>(), new ArrayList<>(), Material.POWDER_SNOW, -1, null, null);
                } else if (amountOfTicks < 600) {
                    SetBlockTools.setBlocksInWorld(activePlayer, startLocation, form2, characterMaterialMap, true, new ArrayList<>(), new ArrayList<>(), Material.POWDER_SNOW, -1, null, null);
                } else {
                    this.cancel();
                    activePlayer.resetWorld();
                }
                amountOfTicks++;
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }
}
