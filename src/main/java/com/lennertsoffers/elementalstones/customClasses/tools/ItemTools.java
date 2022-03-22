package com.lennertsoffers.elementalstones.customClasses.tools;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemTools {

    public static ItemStack getSingleFromStack(ItemStack itemStack) {
        ItemStack singleStack = itemStack.clone();
        singleStack.setAmount(1);

        return singleStack;
    }

    public static ArrayList<ItemStack> getSingleListFromStackList(ItemStack[] itemStacks) {
        ArrayList<ItemStack> singleItemCraftingMatrix = new ArrayList<>();

        for (ItemStack itemStack : itemStacks) {
            if (itemStack != null) {
                singleItemCraftingMatrix.add(ItemTools.getSingleFromStack(itemStack));
            }
        }

        return singleItemCraftingMatrix;
    }

    public static ItemStack getLoweredItemStack(ItemStack itemStack) {
        itemStack.setAmount(itemStack.getAmount() - 1);
        return itemStack;
    }
}
