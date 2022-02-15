package com.lennertsoffers.elementalstones.moves.fireMoves.basic;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.moves.Move;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AQuickSnack extends Move {

    public AQuickSnack(ActivePlayer activePlayer) {
        super(activePlayer, "A-Quick-Snack", "fire_stone", "default", 1);
    }

    @Override
    public void useMove() {
        Player player = this.getPlayer();
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

        this.setCooldown();
    }
}
