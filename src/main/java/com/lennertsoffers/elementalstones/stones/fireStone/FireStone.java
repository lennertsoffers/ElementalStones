package com.lennertsoffers.elementalstones.stones.fireStone;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class FireStone {

    // MOVE 1
    // A-Quick-Snack
    // -> Turns every rawww stack of food to the cooked version of it
    public static void move1(ActivePlayer activePlayer) {
        Player player = activePlayer.getPlayer();
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null) {
                Material material = itemStack.getType();
                if (material == Material.BEEF) {
                    itemStack.setType(Material.COOKED_BEEF);
                } else if (material == Material.PORKCHOP) {
                    itemStack.setType(Material.COOKED_PORKCHOP);
                } else if (material == Material.SALMON) {
                    itemStack.setType(Material.COOKED_SALMON);
                } else if (material == Material.MUTTON) {
                    itemStack.setType(Material.COOKED_MUTTON);
                } else if (material == Material.POTATO) {
                    itemStack.setType(Material.BAKED_POTATO);
                } else if (material == Material.CHICKEN) {
                    itemStack.setType(Material.COOKED_CHICKEN);
                } else if (material == Material.COD) {
                    itemStack.setType(Material.COOKED_COD);
                } else if (material == Material.RABBIT) {
                    itemStack.setType(Material.COOKED_RABBIT);
                }
            }
        }
    }

    // MOVE 2
    // Floating Fire
    // -> You can summon a fireball and hold it right in front of you
    public static void move2(ActivePlayer activePlayer) {

    }

    // MOVE 3
    // Fire-fly
    // -> You get trusted in the looking direction of the player
    public static void move3(ActivePlayer activePlayer) {

    }
}
