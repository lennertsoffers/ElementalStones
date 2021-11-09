package com.lennertsoffers.elementalstones.customClasses;

import com.lennertsoffers.elementalstones.items.CraftItemManager;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ShamanVillager {

    private final Villager villager;
    private final ArrayList<MerchantRecipe> trades = new ArrayList<>();

    public static HashSet<ShamanVillager> shamanVillagers = new HashSet<>();
    private static final HashMap<String, ArrayList<ItemStack>> shamanIngredients = new HashMap<>();

    public ShamanVillager(Villager villager) {
        this.villager = villager;
        villager.setCustomName("Shaman");
        initShamanIngredients();

        new BukkitRunnable() {
            @Override
            public void run() {
                generateTrades();
            }
        }.runTaskTimer(StaticVariables.plugin, 0L, 24000L);

        shamanVillagers.add(this);
    }

    public void generateTrades() {
        trades.clear();
        for (int i = 0; i < 5; i++) {
            trades.add(generateTrade());
        }
        villager.setRecipes(trades);
    }

    private MerchantRecipe generateTrade() {
        MerchantRecipe merchantRecipe;
        List<ItemStack> ingredients = new ArrayList<>();
        if (this.villager.getVillagerLevel() == 1) {
            if (StaticVariables.random.nextInt(20) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.RARE_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("rareIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("rareIngredients").size())));
            } else if (StaticVariables.random.nextInt(8) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.UNCOMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("uncommonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("uncommonIngredients").size())));
            } else {
                merchantRecipe = new MerchantRecipe(CraftItemManager.COMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("commonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("commonIngredients").size())));
            }
        } else if (this.villager.getVillagerLevel() == 2) {
            if (StaticVariables.random.nextInt(30) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.ULTRA_RARE_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("ultraRareIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("ultraRareIngredients").size())));
            } else if (StaticVariables.random.nextInt(15) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.RARE_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("rareIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("rareIngredients").size())));
            } else if (StaticVariables.random.nextInt(6) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.UNCOMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("uncommonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("uncommonIngredients").size())));
            } else {
                merchantRecipe = new MerchantRecipe(CraftItemManager.COMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("commonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("commonIngredients").size())));
            }
        } else if (this.villager.getVillagerLevel() == 3) {
            if (StaticVariables.random.nextInt(30) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.LEGENDARY_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("legendaryIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("legendaryIngredients").size())));
            } else if (StaticVariables.random.nextInt(20) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.ULTRA_RARE_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("ultraRareIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("ultraRareIngredients").size())));
            } else if (StaticVariables.random.nextInt(10) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.RARE_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("rareIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("rareIngredients").size())));
            } else if (StaticVariables.random.nextInt(4) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.UNCOMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("uncommonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("uncommonIngredients").size())));
            } else {
                merchantRecipe = new MerchantRecipe(CraftItemManager.COMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("commonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("commonIngredients").size())));
            }
        } else if (this.villager.getVillagerLevel() == 4) {
            if (StaticVariables.random.nextInt(15) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.LEGENDARY_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("legendaryIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("legendaryIngredients").size())));
            } else if (StaticVariables.random.nextInt(10) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.ULTRA_RARE_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("ultraRareIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("ultraRareIngredients").size())));
            } else if (StaticVariables.random.nextInt(4) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.RARE_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("rareIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("rareIngredients").size())));
            } else if (StaticVariables.random.nextInt(3) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.UNCOMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("uncommonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("uncommonIngredients").size())));
            } else {
                merchantRecipe = new MerchantRecipe(CraftItemManager.COMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("commonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("commonIngredients").size())));
            }
        } else {
            if (StaticVariables.random.nextInt(8) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.LEGENDARY_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("legendaryIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("legendaryIngredients").size())));
            } else if (StaticVariables.random.nextInt(6) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.ULTRA_RARE_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("ultraRareIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("ultraRareIngredients").size())));
            } else if (StaticVariables.random.nextInt(3) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.RARE_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("rareIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("rareIngredients").size())));
            } else if (StaticVariables.random.nextInt(2) == 0) {
                merchantRecipe = new MerchantRecipe(CraftItemManager.UNCOMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("uncommonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("uncommonIngredients").size())));
            } else {
                merchantRecipe = new MerchantRecipe(CraftItemManager.COMMON_SHARD, 0, StaticVariables.random.nextInt(3) + 1, true);
                ingredients.add(shamanIngredients.get("commonIngredients").get(StaticVariables.random.nextInt(shamanIngredients.get("commonIngredients").size())));
            }
        }
        System.out.println(ingredients);

        merchantRecipe.setIngredients(ingredients);
        return merchantRecipe;
    }

    private void initShamanIngredients() {
        ArrayList<ItemStack> commonIngredientsPool = new ArrayList<>();
        commonIngredientsPool.add(new ItemStack(Material.NETHER_WART, 64));
        commonIngredientsPool.add(new ItemStack(Material.REDSTONE, 64));
        commonIngredientsPool.add(new ItemStack(Material.GUNPOWDER, 64));
        commonIngredientsPool.add(new ItemStack(Material.SUGAR, 64));
        commonIngredientsPool.add(new ItemStack(Material.BLAZE_POWDER, 64));
        commonIngredientsPool.add(new ItemStack(Material.ROTTEN_FLESH, 64));
        commonIngredientsPool.add(new ItemStack(Material.NAME_TAG, 2));
        commonIngredientsPool.add(new ItemStack(Material.WHITE_WOOL, 30));
        commonIngredientsPool.add(new ItemStack(Material.PURPUR_PILLAR, 10));
        commonIngredientsPool.add(new ItemStack(Material.PACKED_ICE, 64));
        commonIngredientsPool.add(new ItemStack(Material.OBSIDIAN, 10));

        ArrayList<ItemStack> uncommonIngredientsPool = new ArrayList<>();
        uncommonIngredientsPool.add(new ItemStack(Material.GLOWSTONE_DUST, 64));
        uncommonIngredientsPool.add(new ItemStack(Material.SPIDER_EYE, 20));
        uncommonIngredientsPool.add(new ItemStack(Material.PUFFERFISH, 20));
        uncommonIngredientsPool.add(new ItemStack(Material.PHANTOM_MEMBRANE, 20));
        uncommonIngredientsPool.add(new ItemStack(Material.ENDER_EYE, 20));
        uncommonIngredientsPool.add(new ItemStack(Material.EXPERIENCE_BOTTLE, 10));
        uncommonIngredientsPool.add(new ItemStack(Material.DIAMOND_HORSE_ARMOR, 1));commonIngredientsPool.add(new ItemStack(Material.WHITE_WOOL, 30));
        uncommonIngredientsPool.add(new ItemStack(Material.CHORUS_FLOWER, 10));commonIngredientsPool.add(new ItemStack(Material.WHITE_WOOL, 30));
        uncommonIngredientsPool.add(new ItemStack(Material.BLUE_ICE, 10));commonIngredientsPool.add(new ItemStack(Material.WHITE_WOOL, 30));

        ArrayList<ItemStack> rareIngredientsPool = new ArrayList<>();
        rareIngredientsPool.add(new ItemStack(Material.FERMENTED_SPIDER_EYE, 20));
        rareIngredientsPool.add(new ItemStack(Material.GOLDEN_CARROT, 20));
        rareIngredientsPool.add(new ItemStack(Material.GLISTERING_MELON_SLICE, 20));
        rareIngredientsPool.add(new ItemStack(Material.RABBIT_FOOT, 20));
        rareIngredientsPool.add(new ItemStack(Material.AMETHYST_SHARD, 64));
        rareIngredientsPool.add(new ItemStack(Material.COBWEB, 64));
        rareIngredientsPool.add(new ItemStack(Material.DRAGON_HEAD, 1));
        rareIngredientsPool.add(new ItemStack(Material.TOTEM_OF_UNDYING, 1));
        rareIngredientsPool.add(new ItemStack(Material.MUSIC_DISC_11, 1));

        ArrayList<ItemStack> ultraRareIngredientsPool = new ArrayList<>();
        ultraRareIngredientsPool.add(new ItemStack(Material.DRAGON_BREATH, 10));
        ultraRareIngredientsPool.add(new ItemStack(Material.GHAST_TEAR, 20));
        ultraRareIngredientsPool.add(new ItemStack(Material.TURTLE_HELMET, 1));
        ultraRareIngredientsPool.add(new ItemStack(Material.TURTLE_EGG, 20));
        ultraRareIngredientsPool.add(new ItemStack(Material.AXOLOTL_BUCKET, 1));
        ultraRareIngredientsPool.add(new ItemStack(Material.SPYGLASS, 10));
        ultraRareIngredientsPool.add(new ItemStack(Material.SHULKER_BOX, 1));
        ultraRareIngredientsPool.add(new ItemStack(Material.SKELETON_SKULL, 1));
        ultraRareIngredientsPool.add(new ItemStack(Material.ZOMBIE_HEAD, 1));
        ultraRareIngredientsPool.add(new ItemStack(Material.CREEPER_HEAD, 1));
        ultraRareIngredientsPool.add(new ItemStack(Material.WITHER_SKELETON_SKULL, 1));

        ArrayList<ItemStack> legendaryIngredientsPool = new ArrayList<>();
        legendaryIngredientsPool.add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1));
        legendaryIngredientsPool.add(new ItemStack(Material.NETHERITE_INGOT, 4));
        legendaryIngredientsPool.add(new ItemStack(Material.NETHER_STAR, 1));
        legendaryIngredientsPool.add(new ItemStack(Material.HEART_OF_THE_SEA, 2));
        legendaryIngredientsPool.add(new ItemStack(Material.DIAMOND_BLOCK, 2));
        legendaryIngredientsPool.add(new ItemStack(Material.DRAGON_EGG, 1));
        legendaryIngredientsPool.add(new ItemStack(Material.MUSIC_DISC_PIGSTEP, 1));
        legendaryIngredientsPool.add(new ItemStack(Material.ENDER_CHEST, 64));

        shamanIngredients.put("commonIngredients", commonIngredientsPool);
        shamanIngredients.put("uncommonIngredients", uncommonIngredientsPool);
        shamanIngredients.put("rareIngredients", rareIngredientsPool);
        shamanIngredients.put("ultraRareIngredients", ultraRareIngredientsPool);
        shamanIngredients.put("legendaryIngredients", legendaryIngredientsPool);
    }


    public static boolean isShamanVillager(UUID uuid) {
        for (ShamanVillager shamanVillager : shamanVillagers) {
            if (shamanVillager.villager.getUniqueId() == uuid) {
                return true;
            }
        }
        return false;
    }

    public static void deadShamanVillager(UUID uuid) {
        shamanVillagers.removeIf(shamanVillager -> shamanVillager.villager.getUniqueId() == uuid);
    }
}
