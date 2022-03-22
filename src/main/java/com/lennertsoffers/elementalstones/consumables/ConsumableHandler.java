package com.lennertsoffers.elementalstones.consumables;

import com.lennertsoffers.elementalstones.consumables.effects.*;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.WarHornRaid;
import com.lennertsoffers.elementalstones.customClasses.tools.ItemTools;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.raid.RaidSpawnWaveEvent;

public class ConsumableHandler {
    public static void shipInBottle(Player player) {
        if (ItemTools.getSingleFromStack(player.getInventory().getItemInMainHand()).isSimilar(CraftItemManager.SHIP_IN_BOTTLE)) {
            ConsumableEffect effect = new ShipInBottleEffect();
            effect.playEffect(player);
        }
    }

    public static void finnSoup(Player player) {
        if (ItemTools.getSingleFromStack(player.getInventory().getItemInMainHand()).isSimilar(CraftItemManager.FINN_SOUP)) {
            ConsumableEffect effect = new FinnSoupEffect();
            effect.playEffect(player);
        }
    }

    public static void warHorn(Player player) {
        if (ItemTools.getSingleFromStack(player.getInventory().getItemInMainHand()).isSimilar(CraftItemManager.WAR_HORN)) {
            World world = player.getWorld();
            Location location = player.getLocation();

            world.playSound(location, Sound.EVENT_RAID_HORN, 1, 0);

            WarHornRaid raid = new WarHornRaid(location, world, player);
            raid.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            new RaidSpawnWaveEvent(raid, world, raid.getCaptain(), raid.getRaiders());
        }
    }

    public static void rottenApple(Player player) {
        ConsumableEffect effect = new RottenAppleEffect();
        effect.playEffect(player);
    }

    public static void poisonedApple(Player player) {
        ConsumableEffect effect = new PoisonedAppleEffect();
        effect.playEffect(player);
    }

    public static void mysteryPotion() {

    }

    public static void antidote() {

    }

    public static void gingerbreadMan() {

    }

    public static void bottleOfLightning() {

    }
}
