package com.lennertsoffers.elementalstones.customClasses.models;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.items.ItemStones;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Boss {

    private Monster monster;
    private String name;
    private ItemStack reward;

    public static ArrayList<Boss> bosses = new ArrayList<>();

    public Boss(Player player) {
        if (spawnBoss(player.getLocation())) {
            bosses.add(this);
            this.monster.setTarget(player);
        }
    }

    public Boss(Location location) {
        if (spawnBoss(location)) {
            bosses.add(this);
        }
    }

    private boolean spawnBoss(Location location) {
        World world = location.getWorld();
        if (world != null) {
            world.setTime(18000);
            double attack;
            double health;
            double range;
            double speed;
            int bossIndex = StaticVariables.random.nextInt(4);
            if (bossIndex == 0) {
                this.monster = (Monster) world.spawnEntity(location, EntityType.HUSK);
                this.reward = ItemStones.waterStone0;
                this.name = "Husk Boss";
                Husk husk = (Husk) monster;
                husk.setAdult();
                attack = 10;
                health = 100;
                range = 100;
                speed = 0.8;
            } else if (bossIndex == 1) {
                this.monster = (Monster) world.spawnEntity(location, EntityType.ZOMBIE);
                this.reward = ItemStones.fireStone0;
                this.name = "Zombie Boss";
                Zombie zombie = (Zombie) monster;
                zombie.setAdult();
                attack = 12;
                health = 400;
                range = 40;
                speed = 0.5;
            } else if (bossIndex == 2) {
                this.monster = (Monster) world.spawnEntity(location, EntityType.SPIDER);
                this.reward = ItemStones.earthStone0;
                this.name = "Spider Boss";
                attack = 40;
                health = 100;
                range = 50;
                speed = 0.3;
            } else {
                this.monster = (Monster) world.spawnEntity(location, EntityType.ILLUSIONER);
                this.reward = ItemStones.airStone0;
                this.name = "Illusioner Boss";
                attack = 10;
                health = 1000;
                range = 200;
                speed = 0.5;
            }

            monster.setCustomName(this.name);

            HashMap<AttributeInstance, Double> attributeInstanceMap = new HashMap<>();
            attributeInstanceMap.put(monster.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE), attack);
            attributeInstanceMap.put(monster.getAttribute(Attribute.GENERIC_MAX_HEALTH), health);
            attributeInstanceMap.put(monster.getAttribute(Attribute.GENERIC_FOLLOW_RANGE), range);
            attributeInstanceMap.put(monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED), speed);

            attributeInstanceMap.forEach((key, value) -> {
                if (key != null) {
                    key.setBaseValue(value);
                }
            });

            return true;
        }
        return false;
    }

    public static void killedBoss(Boss boss) {
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + boss.name + " was killed!");
        Monster monster = boss.monster;
        monster.getWorld().dropItemNaturally(monster.getLocation(), boss.reward);
    }

    public static Boss getBoss(UUID uuid) {
        for (Boss boss : bosses) {
            if (boss.monster.getUniqueId() == uuid) {
                return boss;
            }
        }
        return null;
    }
}
