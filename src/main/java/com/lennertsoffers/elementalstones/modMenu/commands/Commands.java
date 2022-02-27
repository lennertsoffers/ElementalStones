package com.lennertsoffers.elementalstones.modMenu.commands;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.Boss;
import com.lennertsoffers.elementalstones.items.CraftItemManager;
import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("r")) {
            if (sender.isOp()) {
                ActivePlayer.clearActivePlayers();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.kickPlayer("Server is reloading");
                }
                Bukkit.reload();
                return true;
            }
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
        } else if (label.equalsIgnoreCase("giveItem")) {
            if (sender instanceof Player) {
                if (sender.isOp()) {
                    if (args.length == 0) {
                        Player player = (Player) sender;
                        ItemStack itemStack = CraftItemManager.FINN_SOUP.clone();
                        itemStack.setAmount(64);
                        player.getInventory().addItem(itemStack);
                        return true;
                    }
                }
            }
        } else if (label.equalsIgnoreCase("giveShards")) {
            if (sender instanceof Player) {
                if (sender.isOp()) {
                    if (args.length == 0) {
                        Player player = (Player) sender;
                        ItemStack itemStack = CraftItemManager.COMMON_SHARD.clone();
                        itemStack.setAmount(64);
                        player.getInventory().addItem(itemStack);
                        return true;
                    }
                }
            }
        } else if (label.equalsIgnoreCase("shamanMajor")) {
            if (sender instanceof Player) {
                if (sender.isOp()) {
                    if (args.length == 0) {
                        Player player = (Player) sender;
                        new Boss(player.getLocation());
                        return true;
                    }
                }
            }
        } else if (label.equalsIgnoreCase("saveDefaultConfig")) {
            if (sender instanceof Player) {
                if (sender.isOp()) {
                    if (args.length == 0) {
                        StaticVariables.plugin.saveDefaultConfig();

                        return true;
                    }
                }
            }
        } else if (label.equalsIgnoreCase("spawnCows")) {
            if (sender instanceof Player) {
                if (sender.isOp()) {
                    Player player = (Player) sender;
                    World world = player.getWorld();
                    Location location = player.getLocation().add(0, 1, 0);

                    for (int i = 0; i < 100; i++) {
                        world.spawnEntity(location, EntityType.COW);
                    }

                    return true;
                }
            }
        } else if (label.equalsIgnoreCase("customModelData")) {
            if (sender.isOp()) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();

                    if (itemMeta != null) {
                        player.sendMessage(String.valueOf(itemMeta.getCustomModelData()));
                    }

                    return true;
                }
            }
        } else if (label.equalsIgnoreCase("checkShards")) {
            if (sender.isOp()) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    Inventory inventory = Bukkit.createInventory(player, 18);
                    int slot = 0;
                    for (ItemStack itemStack : Arrays.asList(CraftItemManager.COMMON_SHARD, CraftItemManager.UNCOMMON_SHARD, CraftItemManager.RARE_SHARD, CraftItemManager.ULTRA_RARE_SHARD, CraftItemManager.LEGENDARY_SHARD)) {
                        inventory.setItem(slot, itemStack);
                        slot++;
                    }
                    player.openInventory(inventory);

                    return true;
                }
            }
        } else if (label.equalsIgnoreCase("checkSpells")) {
            if (sender.isOp()) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    Inventory inventory = Bukkit.createInventory(player, 18);
                    int slot = 0;
                    for (ItemStack itemStack : Arrays.asList(CraftItemManager.AGILITY_SPELL, CraftItemManager.AIRBENDING_SPELL, CraftItemManager.HELLFIRE_SPELL, CraftItemManager.EXPLOSION_SPELL, CraftItemManager.WATERBENDING_SPELL, CraftItemManager.ICE_SPELL, CraftItemManager.EARTHBENDING_SPELL, CraftItemManager.LAVA_SPELL)) {
                        inventory.setItem(slot, itemStack);
                        slot++;
                    }
                    player.openInventory(inventory);

                    return true;
                }
            }
        } else if (label.equalsIgnoreCase("checkItems1")) {
            if (sender.isOp()) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    List<ItemStack> itemStacks = Arrays.asList(
                            CraftItemManager.BABY_ZOMBIE_HIDE,
                            CraftItemManager.INSECT,
                            CraftItemManager.BAT,
                            CraftItemManager.THYME,
                            CraftItemManager.OREGANO,
                            CraftItemManager.DILL,
                            CraftItemManager.ROSEMARY,
                            CraftItemManager.GOLDEN_FEATHER,
                            CraftItemManager.DEAD_FLOWER,
                            CraftItemManager.TWIG,
                            CraftItemManager.SOUL_OF_EVOKER,
                            CraftItemManager.BLOOD_OF_WANDERING_TRADER,
                            CraftItemManager.STINGER,
                            CraftItemManager.VOODOO_DOLL,
                            CraftItemManager.WAR_HORN,
                            CraftItemManager.ANTIDOTE,
                            CraftItemManager.PALANTIR
                    );

                    Inventory inventory = Bukkit.createInventory(player, 18);
                    for (int i = 0; i < itemStacks.size() - 1; i++) {
                        inventory.setItem(i, itemStacks.get(i));
                    }

                    player.openInventory(inventory);

                    return true;
                }
            }
        } else if (label.equalsIgnoreCase("checkItems2")) {
            if (sender.isOp()) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    List<ItemStack> itemStacks = Arrays.asList(
                            CraftItemManager.HOGLIN_TUSK,
                            CraftItemManager.FINN,
                            CraftItemManager.SHIP_IN_BOTTLE,
                            CraftItemManager.BLOOD_AND_QUIL,
                            CraftItemManager.BUNDLE_OF_HERBS,
                            CraftItemManager.CARNIVOROUS_PLANT,
                            CraftItemManager.SCENTED_CANDLE,
                            CraftItemManager.POISONOUS_DART,
                            CraftItemManager.BROOM,
                            CraftItemManager.ROTTEN_APPLE,
                            CraftItemManager.POISONED_APPLE,
                            CraftItemManager.MYSTERY_POTION,
                            CraftItemManager.GINGERBREAD_MAN,
                            CraftItemManager.FINN_SOUP
                    );

                    Inventory inventory = Bukkit.createInventory(player, 18);
                    int slot = 0;
                    for (ItemStack itemStack : itemStacks) {
                        inventory.setItem(slot, itemStack);
                        slot++;
                    }
                    player.openInventory(inventory);

                    return true;
                }
            }
        }
        return false;
    }
}
