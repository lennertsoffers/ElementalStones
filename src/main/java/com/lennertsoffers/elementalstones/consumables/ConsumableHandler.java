package com.lennertsoffers.elementalstones.consumables;

import com.lennertsoffers.elementalstones.consumables.effects.ShipInBottleEffect;
import com.lennertsoffers.elementalstones.customClasses.tools.ItemTools;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import org.bukkit.entity.Player;

public class ConsumableHandler {
    public static void shipInBottle(Player player) {
        if (ItemTools.getSingleFromStack(player.getInventory().getItemInMainHand()) == CraftItemManager.SHIP_IN_BOTTLE) {
            ShipInBottleEffect shipInBottleEffect = new ShipInBottleEffect();
            shipInBottleEffect.playEffect(player);
        }
    }

    public static void finnSoup() {

    }

    public static void warHorn() {

    }

    public static void rottenApple() {

    }

    public static void poisonedApple() {

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
