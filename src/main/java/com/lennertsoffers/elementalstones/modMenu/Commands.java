package com.lennertsoffers.elementalstones.modMenu;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("r")) {
            ActivePlayer.clearActivePlayers();
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.kickPlayer("Server is reloading");
            }
            Bukkit.reload();
            return true;
        } else if (label.equalsIgnoreCase("giveStone")) {
            if (sender.isOp()) {
                Player player = Bukkit.getPlayer(args[0]);
                if (player == null) {
                    if (sender instanceof Player) {
                        player = (Player) sender;
                    }
                }
                if (player != null) {
                    ArrayList<ItemStack> allStones = ItemStones.allStones;
                    for (ItemStack itemStack : allStones) {
                        if (Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName().contains(args[args.length - 1])) {
                            if (player.getInventory().firstEmpty() != -1) {
                                player.getInventory().addItem(itemStack);
                            } else {
                                player.getWorld().dropItemNaturally(player.getLocation().add(player.getLocation().getDirection()), itemStack);
                            }
                            break;
                        }
                    }
                }
            }
            return true;
        } else if (label.equalsIgnoreCase("stoneInventory")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (sender.isOp()) {
                    if (args.length != 0) {
                        if (args[0].matches("water|fire|air|earth|magic")) {
                            ArrayList<ItemStack> selectedStones;
                            switch (args[0]) {
                                case "water":
                                    selectedStones = (ArrayList<ItemStack>) ItemStones.waterStones.clone();
                                    break;
                                case "fire":
                                    selectedStones = (ArrayList<ItemStack>) ItemStones.fireStones.clone();
                                    break;
                                case "air":
                                    selectedStones = (ArrayList<ItemStack>) ItemStones.airStones.clone();
                                    break;
                                default:
                                    selectedStones = (ArrayList<ItemStack>) ItemStones.earthStones.clone();
                            }
                            Inventory inventory = Bukkit.createInventory(player, 18, args[0]);
                            int slot = 0;
                            for (ItemStack itemStack : selectedStones) {
                                inventory.setItem(slot, itemStack);
                                slot++;
                            }
                            player.openInventory(inventory);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
