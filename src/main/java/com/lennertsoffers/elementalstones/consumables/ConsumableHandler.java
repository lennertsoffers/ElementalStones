package com.lennertsoffers.elementalstones.consumables;

import com.lennertsoffers.elementalstones.consumables.effects.*;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.Boss;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.WarHornRaid;
import com.lennertsoffers.elementalstones.customClasses.tools.ItemTools;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.raid.RaidSpawnWaveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ConsumableHandler {
    public static void shipInBottle(Player player) {
        if (canPlayEffect(player, CraftItemManager.SHIP_IN_BOTTLE, true)) {
            ConsumableEffect effect = new ShipInBottleEffect();
            effect.playEffect(player);
        }
    }

    public static void finnSoup(Player player) {
        if (canPlayEffect(player, CraftItemManager.FINN_SOUP, false)) {
            ConsumableEffect effect = new FinnSoupEffect();
            effect.playEffect(player);
        }
    }

    public static void warHorn(Player player) {
        if (canPlayEffect(player, CraftItemManager.WAR_HORN, true)) {
            World world = player.getWorld();
            Location location = player.getLocation();

            world.playSound(location, Sound.EVENT_RAID_HORN, 1, 0);

            WarHornRaid raid = new WarHornRaid(location, world, player);
            raid.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            new RaidSpawnWaveEvent(raid, world, raid.getCaptain(), raid.getRaiders());
        }
    }

    public static void rottenApple(Player player) {
        if (canPlayEffect(player, CraftItemManager.ROTTEN_APPLE, false)) {
            ConsumableEffect effect = new RottenAppleEffect();
            effect.playEffect(player);
        }
    }

    public static void poisonedApple(Player player) {
        if (canPlayEffect(player, CraftItemManager.POISONED_APPLE, false)) {
            ConsumableEffect effect = new PoisonedAppleEffect();
            effect.playEffect(player);
        }
    }

    public static void mysteryPotion(Player player) {
        if (canPlayEffect(player, CraftItemManager.MYSTERY_POTION, false)) {
            ConsumableEffect effect = new MysteryPotionEffect();
            effect.playEffect(player);
        }
    }

    public static void antidote(Player player) {
        if (canPlayEffect(player, CraftItemManager.ANTIDOTE, true)) {
            ConsumableEffect effect = new AntidoteEffect();
            effect.playEffect(player);
        }
    }

    public static void gingerbreadMan(Player player) {
        if (canPlayEffect(player, CraftItemManager.GINGERBREAD_MAN, false)) {
            ConsumableEffect effect = new GingerbreadManEffect();
            effect.playEffect(player);
        }
    }

    public static void bottleOfLightning(Player player) {
        if (canPlayEffect(player, CraftItemManager.BOTTLE_OF_LIGHTNING, true)) {
            ConsumableEffect effect = new BottleOfLightningEffect();
            effect.playEffect(player);
        }
    }

    public static void voodooDoll(Player player) {
        if (canPlayEffect(player, CraftItemManager.VOODOO_DOLL, true)) {
            new Boss(player);
        }
    }

    public static void palantir(Player player) {
        if (canPlayEffect(player, CraftItemManager.PALANTIR, true)) {
            ConsumableEffect effect = new PalantirEffect();
            effect.playEffect(player);
        }
    }

    public static void carnivorousPlant(Player player) {
        if (canPlayEffect(player, CraftItemManager.CARNIVOROUS_PLANT, true)) {
            ConsumableEffect effect = new CarnivorousPlantEffect();
            effect.playEffect(player);
        }
    }

    public static boolean canPlayEffect(Player player, ItemStack itemStack, boolean lowerItemInMainHand) {
        PlayerInventory inventory = player.getInventory();

        ItemStack itemInMainHand = inventory.getItemInMainHand();
        ItemStack singleItemInMainHand = ItemTools.getSingleFromStack(itemInMainHand);

        if (singleItemInMainHand.isSimilar(itemStack)) {
            if (lowerItemInMainHand) {
                inventory.setItemInMainHand(ItemTools.getLoweredItemStack(itemInMainHand));
            }

            return true;
        }

        return false;
    }

    // TODO - only spectate survival players                        ?
    // TODO - Respawn on activation place                           ?
    // TODO - When shifting after no players in array, respawn      ?

    // TODO - Fix mystery potion particles
    // TODO - Broom: Spawn cats following u
    // TODO - Poisonous dart: Arsenal of tipped arrows
    // TODO - Bundle of herbs: Ride every entity
    // TODO - Check spell drops
    // TODO - Bosses more health
    // TODO - Fix items of consumables and resource pack
    // TODO - Make stones stay in inventory after death

    // java.lang.IllegalArgumentException: bound must be positive
    //        at java.util.Random.nextInt(Random.java:322) ~[?:?]
    //        at com.lennertsoffers.elementalstones.eventHandlers.EntityDeathEvent.onEntityDeath(EntityDeathEvent.java:76) ~[elementalstones-1.0.1.jar:?]
    //        at com.destroystokyo.paper.event.executor.asm.generated.GeneratedEventExecutor5.execute(Unknown Source) ~[?:?]
    //        at org.bukkit.plugin.EventExecutor.lambda$create$1(EventExecutor.java:75) ~[paper-api-1.18.1-R0.1-SNAPSHOT.jar:?]
}
