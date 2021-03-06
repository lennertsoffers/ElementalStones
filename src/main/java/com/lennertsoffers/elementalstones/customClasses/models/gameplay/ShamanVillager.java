package com.lennertsoffers.elementalstones.customClasses.models.gameplay;

import com.lennertsoffers.elementalstones.ElementalStones;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Villager;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ShamanVillager{

    private boolean firstTradeGeneration = true;

    private final Villager villager;
    private final ArrayList<MerchantRecipe> trades = new ArrayList<>();

    private static final HashMap<String, ArrayList<ItemStack>> shamanIngredients = new HashMap<>();

    public ShamanVillager(Villager villager, boolean firstConversion) {
        this.villager = villager;

        if (firstConversion) {
            this.villager.setCustomName("Shaman");
            this.villager.setVillagerExperience(villager.getVillagerExperience() + 1);
            this.generateTrades();
        }
    }

    public void generateTrades() {
        this.trades.clear();
        for (int i = 0; i < 5; i++) {
            this.trades.add(generateTrade());
        }

        if (this.firstTradeGeneration) {
            this.firstTradeGeneration = false;
            new BukkitRunnable() {
                @Override
                public void run() {
                    villager.setRecipes(trades);
                }
            }.runTaskLater(StaticVariables.plugin, 10L);
        } else {
            this.villager.setRecipes(this.trades);
        }
    }

    public boolean acceptItem(ItemStack item) {
        if (villager.getVillagerLevel() < 5) {
            for (ShamanTradeItem shamanTradeItem : ShamanTradeItem.getShamanXpItems()) {
                if (shamanTradeItem.getItem().isSimilar(item)) {
                    int xp = shamanTradeItem.getXpValue();

                    int oldXp = this.villager.getVillagerExperience();
                    this.villager.setVillagerExperience(villager.getVillagerExperience() + xp);
                    int newXp = this.villager.getVillagerExperience();

                    if (
                            (oldXp < 10 && newXp >= 10) ||
                            (oldXp < 70 && newXp >= 70) ||
                            (oldXp < 150 && newXp >= 150) ||
                            (oldXp < 250 && newXp >= 250)
                    ) {
                        villagerLevelUp();
                    }

                    return true;
                }
            }
        }
        return false;
    }

    private void villagerLevelUp() {
        World world = villager.getWorld();

        for (int i = 0; i < 30; i++) {
            Location particleLocation = villager.getLocation().add(0, 1.5, 0);
            double x = particleLocation.getX() + StaticVariables.random.nextGaussian() / 6;
            double y = particleLocation.getY() + StaticVariables.random.nextGaussian() / 6;
            double z = particleLocation.getZ() + StaticVariables.random.nextGaussian() / 6;
            world.spawnParticle(Particle.VILLAGER_HAPPY, x, y, z, 0);
        }

        generateTrades();
        this.villager.setVillagerLevel(this.villager.getVillagerLevel() + 1);

        if (this.villager.getVillagerLevel() == 5) {
            int random = StaticVariables.random.nextInt(ElementalStones.configuration.getInt("drop_chance.spell"));

            if (random == 0) {
                Location location = this.villager.getLocation();
                world.dropItem(location, CraftItemManager.spells.get(StaticVariables.random.nextInt(CraftItemManager.spells.size())));
            }
        }
    }

    private MerchantRecipe generateTrade() {
        MerchantRecipe merchantRecipe;
        List<ItemStack> ingredients = new ArrayList<>();
        if (this.villager.getVillagerLevel() == 1) {
            if (StaticVariables.random.nextInt(20) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.RARE_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("rareIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("rareIngredients").size())));
            } else if (StaticVariables.random.nextInt(8) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.UNCOMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("uncommonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("uncommonIngredients").size())));
            } else {
                merchantRecipe = new MerchantRecipe(CraftItemManager.COMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("commonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("commonIngredients").size())));
            }
        } else if (this.villager.getVillagerLevel() == 2) {
            if (StaticVariables.random.nextInt(30) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.ULTRA_RARE_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("ultraRareIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("ultraRareIngredients").size())));
            } else if (StaticVariables.random.nextInt(15) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.RARE_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("rareIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("rareIngredients").size())));
            } else if (StaticVariables.random.nextInt(6) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.UNCOMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("uncommonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("uncommonIngredients").size())));
            } else {
                merchantRecipe = new MerchantRecipe(CraftItemManager.COMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("commonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("commonIngredients").size())));
            }
        } else if (this.villager.getVillagerLevel() == 3) {
            if (StaticVariables.random.nextInt(30) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.LEGENDARY_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("legendaryIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("legendaryIngredients").size())));
            } else if (StaticVariables.random.nextInt(20) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.ULTRA_RARE_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("ultraRareIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("ultraRareIngredients").size())));
            } else if (StaticVariables.random.nextInt(10) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.RARE_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("rareIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("rareIngredients").size())));
            } else if (StaticVariables.random.nextInt(4) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.UNCOMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("uncommonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("uncommonIngredients").size())));
            } else {
                merchantRecipe = new MerchantRecipe(CraftItemManager.COMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("commonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("commonIngredients").size())));
            }
        } else if (this.villager.getVillagerLevel() == 4) {
            if (StaticVariables.random.nextInt(15) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.LEGENDARY_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("legendaryIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("legendaryIngredients").size())));
            } else if (StaticVariables.random.nextInt(10) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.ULTRA_RARE_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("ultraRareIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("ultraRareIngredients").size())));
            } else if (StaticVariables.random.nextInt(4) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.RARE_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("rareIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("rareIngredients").size())));
            } else if (StaticVariables.random.nextInt(3) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.UNCOMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("uncommonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("uncommonIngredients").size())));
            } else {
                merchantRecipe = new MerchantRecipe(CraftItemManager.COMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("commonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("commonIngredients").size())));
            }
        } else {
            if (StaticVariables.random.nextInt(8) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.LEGENDARY_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("legendaryIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("legendaryIngredients").size())));
            } else if (StaticVariables.random.nextInt(6) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.ULTRA_RARE_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("ultraRareIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("ultraRareIngredients").size())));
            } else if (StaticVariables.random.nextInt(3) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.RARE_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("rareIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("rareIngredients").size())));
            } else if (StaticVariables.random.nextInt(2) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.UNCOMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("uncommonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("uncommonIngredients").size())));
            } else {
                merchantRecipe = new MerchantRecipe(CraftItemManager.COMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, false);
                ingredients.add(shamanIngredients.get("commonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("commonIngredients").size())));
            }
        }
        merchantRecipe.setIngredients(ingredients);
        return merchantRecipe;
    }

    public static void initShamanIngredients() {
        ArrayList<ItemStack> commonIngredientsPool = new ArrayList<>();
        commonIngredientsPool.add(new ItemStack(Material.NETHER_WART, 64));
        commonIngredientsPool.add(new ItemStack(Material.REDSTONE, 64));
        commonIngredientsPool.add(new ItemStack(Material.GUNPOWDER, 64));
        commonIngredientsPool.add(new ItemStack(Material.SPIDER_EYE, 64));
        commonIngredientsPool.add(new ItemStack(Material.SUGAR, 64));
        commonIngredientsPool.add(new ItemStack(Material.BLAZE_POWDER, 64));
        commonIngredientsPool.add(new ItemStack(Material.ROTTEN_FLESH, 64));
        commonIngredientsPool.add(new ItemStack(Material.NAME_TAG, 1));
        commonIngredientsPool.add(new ItemStack(Material.WHITE_WOOL, 64));
        commonIngredientsPool.add(new ItemStack(Material.PURPUR_PILLAR, 32));
        commonIngredientsPool.add(new ItemStack(Material.PACKED_ICE, 64));
        commonIngredientsPool.add(new ItemStack(Material.OBSIDIAN, 10));
        commonIngredientsPool.add(new ItemStack(Material.BRICK, 64));

        ArrayList<ItemStack> uncommonIngredientsPool = new ArrayList<>();
        uncommonIngredientsPool.add(new ItemStack(Material.GLOWSTONE_DUST, 64));
        uncommonIngredientsPool.add(new ItemStack(Material.PUFFERFISH, 64));
        uncommonIngredientsPool.add(new ItemStack(Material.PHANTOM_MEMBRANE, 64));
        uncommonIngredientsPool.add(new ItemStack(Material.ENDER_EYE, 20));
        uncommonIngredientsPool.add(new ItemStack(Material.EXPERIENCE_BOTTLE, 25));
        uncommonIngredientsPool.add(new ItemStack(Material.DIAMOND_HORSE_ARMOR, 1));
        uncommonIngredientsPool.add(new ItemStack(Material.CHORUS_FLOWER, 32));
        uncommonIngredientsPool.add(new ItemStack(Material.BLUE_ICE, 64));
        uncommonIngredientsPool.add(new ItemStack(Material.SCUTE, 2));
        uncommonIngredientsPool.add(new ItemStack(Material.FERMENTED_SPIDER_EYE, 64));
        uncommonIngredientsPool.add(new ItemStack(Material.GLISTERING_MELON_SLICE, 32));
        uncommonIngredientsPool.add(new ItemStack(Material.AMETHYST_SHARD, 32));
        uncommonIngredientsPool.add(new ItemStack(Material.BEE_NEST, 1));
        uncommonIngredientsPool.add(new ItemStack(Material.CHAINMAIL_HELMET, 1));

        ArrayList<ItemStack> rareIngredientsPool = new ArrayList<>();
        rareIngredientsPool.add(new ItemStack(Material.GOLDEN_CARROT, 64));
        rareIngredientsPool.add(new ItemStack(Material.RABBIT_FOOT, 20));
        rareIngredientsPool.add(new ItemStack(Material.COBWEB, 64));
        rareIngredientsPool.add(new ItemStack(Material.DRAGON_HEAD, 1));
        rareIngredientsPool.add(new ItemStack(Material.TOTEM_OF_UNDYING, 1));
        rareIngredientsPool.add(new ItemStack(Material.MUSIC_DISC_11, 1));
        rareIngredientsPool.add(new ItemStack(Material.SHULKER_SHELL, 4));
        rareIngredientsPool.add(new ItemStack(Material.AXOLOTL_BUCKET, 1));
        rareIngredientsPool.add(new ItemStack(Material.TURTLE_HELMET, 1));

        ArrayList<ItemStack> ultraRareIngredientsPool = new ArrayList<>();
        ultraRareIngredientsPool.add(new ItemStack(Material.DRAGON_BREATH, 10));
        ultraRareIngredientsPool.add(new ItemStack(Material.GHAST_TEAR, 20));
        ultraRareIngredientsPool.add(new ItemStack(Material.TURTLE_EGG, 64));
        ultraRareIngredientsPool.add(new ItemStack(Material.SCUTE, 20));
        ultraRareIngredientsPool.add(new ItemStack(Material.SKELETON_SKULL, 1));
        ultraRareIngredientsPool.add(new ItemStack(Material.ZOMBIE_HEAD, 1));
        ultraRareIngredientsPool.add(new ItemStack(Material.WITHER_SKELETON_SKULL, 3));
        ultraRareIngredientsPool.add(new ItemStack(Material.SPONGE, 10));
        ultraRareIngredientsPool.add(new ItemStack(Material.ELYTRA, 1));
        ultraRareIngredientsPool.add(new ItemStack(Material.CLOCK, 64));
        ultraRareIngredientsPool.add(new ItemStack(Material.WITHER_ROSE, 1));

        ArrayList<ItemStack> legendaryIngredientsPool = new ArrayList<>();
        legendaryIngredientsPool.add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1));
        legendaryIngredientsPool.add(new ItemStack(Material.NETHERITE_BLOCK, 1));
        legendaryIngredientsPool.add(new ItemStack(Material.NETHER_STAR, 2));
        legendaryIngredientsPool.add(new ItemStack(Material.TRIDENT, 1));
        legendaryIngredientsPool.add(new ItemStack(Material.DIAMOND_BLOCK, 20));
        legendaryIngredientsPool.add(new ItemStack(Material.DRAGON_EGG, 1));
        legendaryIngredientsPool.add(new ItemStack(Material.MUSIC_DISC_PIGSTEP, 1));
        legendaryIngredientsPool.add(new ItemStack(Material.ENDER_CHEST, 64));
        legendaryIngredientsPool.add(new ItemStack(Material.EMERALD_ORE, 20));
        legendaryIngredientsPool.add(new ItemStack(Material.CONDUIT, 1));

        shamanIngredients.put("commonIngredients", commonIngredientsPool);
        shamanIngredients.put("uncommonIngredients", uncommonIngredientsPool);
        shamanIngredients.put("rareIngredients", rareIngredientsPool);
        shamanIngredients.put("ultraRareIngredients", ultraRareIngredientsPool);
        shamanIngredients.put("legendaryIngredients", legendaryIngredientsPool);
    }
}
