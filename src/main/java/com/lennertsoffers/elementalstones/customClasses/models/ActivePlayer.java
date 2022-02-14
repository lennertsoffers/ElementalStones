package com.lennertsoffers.elementalstones.customClasses.models;

import com.lennertsoffers.elementalstones.ElementalStones;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.Comet;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireBall;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireFireworks;
import com.lennertsoffers.elementalstones.items.ItemStones;
import com.lennertsoffers.elementalstones.stones.fireStone.ExplosionStone;
import com.lennertsoffers.elementalstones.stones.earthStone.EarthStone;
import com.lennertsoffers.elementalstones.stones.earthStone.EarthbendingStone;
import com.lennertsoffers.elementalstones.stones.fireStone.FireStone;
import com.lennertsoffers.elementalstones.stones.fireStone.HellfireStone;
import com.lennertsoffers.elementalstones.stones.earthStone.LavaStone;
import com.lennertsoffers.elementalstones.stones.waterStone.IceStone;
import com.lennertsoffers.elementalstones.stones.waterStone.WaterStone;
import com.lennertsoffers.elementalstones.stones.waterStone.WaterbendingStone;
import com.lennertsoffers.elementalstones.stones.windStone.AgilityStone;
import com.lennertsoffers.elementalstones.stones.windStone.AirStone;
import com.lennertsoffers.elementalstones.stones.windStone.AirbendingStone;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;

public class ActivePlayer {
    private final Player player;
    private boolean active;
    private Vector movingDirection;
    private final ArrayList<ItemStack> hotbarContents = new ArrayList<>();
    private static final ArrayList<ActivePlayer> activePlayers = new ArrayList<>();
    private final Map<Location, Material> resetMapping = new HashMap<>();
    private final MoveController moveController;
    private boolean movesEnabled = true;

    // Earth Stone
    private final List<FallingBlock> move6FallingBlocks = new ArrayList<>();
    private final List<FallingBlock> move6LaunchedFallingBlocks = new ArrayList<>();
    private Comet comet;
    private boolean move8active = false;
    private List<FallingBlock> move8FallingBlocks = new ArrayList<>();
    private final ArrayList<Location> lavaLocations = new ArrayList<>();
    private final List<Block> currentPlatform = new ArrayList<>();
    private final List<Block> allPlatformBlocks = new ArrayList<>();


    // Fire Stone
    private FireBall fireBall;
    private long fireWallsEndTime = -1;
    private int fireWallsAmount = 0;
    private BukkitTask slowTask = null;
    private final ArrayList<Location> wallLocations = new ArrayList<>();
    private FireFireworks fireFireworks = null;
    private int warMachineGrenades = 0;

    // Water Stone
    private int remainingIceShards = 10;
    private boolean doublePassive1 = false;
    private BukkitTask iceSpear;

    // Air Stone
    private boolean canDoubleJump = true;
    private final LinkedList<Arrow> move5Arrows = new LinkedList<>();
    private boolean criticalOnGround = false;
    private long chargingStart = -1;
    private int move7LaunchState = 0;
    private boolean inAirBoost = false;
    private boolean windCloak = false;
    private Entity possibleTarget = null;
    private Entity target = null;
    private BukkitTask levitatingTask = null;
    private Location move8from = null;
    private Location move8to = null;

    // Shaman Trading
    private boolean closeTradingInventories = false;

    public ActivePlayer(Player player) {
        this.player = player;
        this.active = false;
        activePlayers.add(this);
        this.moveController = new MoveController(player);
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean isActive() {
        return this.active;
    }

    public void toggleActive() {
        if (this.active) {
            this.active = false;
//            Inventory inventory = player.getInventory();
//            for (int i = 0; i < 8; i++) {
//                inventory.setItem(i, hotbarContents.get(i));
//                hotbarContents.clear();
//            }
            this.resetWorld();
            player.setAllowFlight(false);

            clearMoves();

            IceStone.passive1(this);
            this.player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You left move mode!");

        } else {
            // Set player to active
            this.active = true;

            // Switch contents of hotbar with moves
            Inventory inventory = player.getInventory();
            for (int i = 0; i < 8; i++) {
                hotbarContents.add(inventory.getItem(i));
                inventory.setItem(i, null);
            }

            // Select correct moves
            loadMoves();

            // Select correct cooldowns
            String currentStoneName = Objects.requireNonNull(Objects.requireNonNull(inventory.getItem(8)).getItemMeta()).getDisplayName();
            if (currentStoneName.contains("Water Stone")) {
                loadCooldowns(1, 3, "water_stone", "default");
            } else if (currentStoneName.contains("Ice")) {
                loadCooldowns(1, 3, "water_stone", "default");
                loadCooldowns(4, 8, "water_stone", "ice_stone");

            } else if (currentStoneName.contains("Waterbending")) {
                loadCooldowns(1, 3, "water_stone", "default");
                loadCooldowns(4, 8, "water_stone", "waterbending_stone");
            } else if (currentStoneName.contains("Fire Stone")) {
                loadCooldowns(1, 3, "fire_stone", "default");
            } else if (currentStoneName.contains("Hellfire")) {
                loadCooldowns(1, 3, "fire_stone", "default");
                loadCooldowns(4, 8, "fire_stone", "hellfire_stone");
            } else if (currentStoneName.contains("Explosion")) {
                loadCooldowns(1, 3, "fire_stone", "default");
                loadCooldowns(4, 8, "fire_stone", "explosion_stone");
            } else if (currentStoneName.contains("Air Stone")) {
                loadCooldowns(1, 3, "air_stone", "default");
            } else if (currentStoneName.contains("Airbending")) {
                loadCooldowns(1, 3, "air_stone", "default");
                loadCooldowns(4, 8, "air_stone", "airbending_stone");
            } else if (currentStoneName.contains("Agility")) {
                loadCooldowns(1, 3, "air_stone", "default");
                loadCooldowns(4, 8, "air_stone", "agility_stone");
            } else if (currentStoneName.contains("Earth Stone")) {
                loadCooldowns(1, 3, "earth_stone", "default");
            } else if (currentStoneName.contains("Earthbending")) {
                loadCooldowns(1, 3, "earth_stone", "default");
                loadCooldowns(4, 8, "earth_stone", "earthbending_stone");
            } else if (currentStoneName.contains("Lava")) {
                loadCooldowns(1, 3, "earth_stone", "default");
                loadCooldowns(4, 8, "earth_stone", "lava_stone");
            }


            // Initialize passives
            IceStone.passive1(this);
            initAgilityStonePassive();

            // Inform player of his/her state
            this.player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You are in move mode!");
        }
    }

    private void initAgilityStonePassive() {
        if (
                !Collections.disjoint(Arrays.asList(this.player.getInventory().getContents()), ItemStones.agilityStones) ||
                !Collections.disjoint(Arrays.asList(this.player.getInventory().getContents()), ItemStones.airbendingStones)
        ) {
            player.setAllowFlight(true);
        }
    }

    private void loadCooldowns(int startMove, int endMove, String stone, String path) {
        for (int i = startMove; i <= endMove; i++) {
            int cooldown = ElementalStones.configuration.getBoolean("disable_cooldowns") ? 0 : ElementalStones.configuration.getInt(stone + "." + path + ".move" + i);
            switch (i) {
                case 1:
                   moveController.getMove1().setCooldown(cooldown);
                case 2:
                    moveController.getMove2().setCooldown(cooldown);
                case 3:
                    moveController.getMove3().setCooldown(cooldown);
                case 4:
                    moveController.getMove4().setCooldown(cooldown);
                case 5:
                    moveController.getMove5().setCooldown(cooldown);
                case 6:
                    moveController.getMove6().setCooldown(cooldown);
                case 7:
                    moveController.getMove7().setCooldown(cooldown);
                case 8:
                    moveController.getMove8().setCooldown(cooldown);
            }
        }
    }

    private void loadMoves() {
        this.moveController.clearMoves();
        ItemStack activeStone = player.getInventory().getItem(8);

        if (activeStone != null) {
            if (activeStone.isSimilar(ItemStones.waterStone0)) {
                moveController.getMove1().setMove(WaterStone.move1(this));
            } else if (activeStone.isSimilar(ItemStones.waterStone1)) {
                moveController.getMove1().setMove(WaterStone.move1(this));
                moveController.getMove2().setMove(WaterStone.move2(this));
            } else if (activeStone.isSimilar(ItemStones.waterStone2)) {
                moveController.getMove1().setMove(WaterStone.move1(this));
                moveController.getMove2().setMove(WaterStone.move2(this));
                moveController.getMove3().setMove(WaterStone.move3(this));
            } else if (activeStone.isSimilar(ItemStones.waterStoneBending0)) {
                moveController.getMove1().setMove(WaterStone.move1(this));
                moveController.getMove2().setMove(WaterStone.move2(this));
                moveController.getMove3().setMove(WaterStone.move3(this));
                moveController.getMove4().setMove(WaterbendingStone.move4(this));
            } else if (activeStone.isSimilar(ItemStones.waterStoneBending1)) {
                moveController.getMove1().setMove(WaterStone.move1(this));
                moveController.getMove2().setMove(WaterStone.move2(this));
                moveController.getMove3().setMove(WaterStone.move3(this));
                moveController.getMove4().setMove(WaterbendingStone.move4(this));
                moveController.getMove5().setMove(WaterbendingStone.move5(this));
            } else if (activeStone.isSimilar(ItemStones.waterStoneBending2)) {
                moveController.getMove1().setMove(WaterStone.move1(this));
                moveController.getMove2().setMove(WaterStone.move2(this));
                moveController.getMove3().setMove(WaterStone.move3(this));
                moveController.getMove4().setMove(WaterbendingStone.move4(this));
                moveController.getMove5().setMove(WaterbendingStone.move5(this));
                moveController.getMove6().setMove(WaterbendingStone.move6(this));
            } else if (activeStone.isSimilar(ItemStones.waterStoneBending3)) {
                moveController.getMove1().setMove(WaterStone.move1(this));
                moveController.getMove2().setMove(WaterStone.move2(this));
                moveController.getMove3().setMove(WaterStone.move3(this));
                moveController.getMove4().setMove(WaterbendingStone.move4(this));
                moveController.getMove5().setMove(WaterbendingStone.move5(this));
                moveController.getMove6().setMove(WaterbendingStone.move6(this));
                moveController.getMove7().setMove(WaterbendingStone.move7(this));
            } else if (activeStone.isSimilar(ItemStones.waterStoneBending4)) {
                moveController.getMove1().setMove(WaterStone.move1(this));
                moveController.getMove2().setMove(WaterStone.move2(this));
                moveController.getMove3().setMove(WaterStone.move3(this));
                moveController.getMove4().setMove(WaterbendingStone.move4(this));
                moveController.getMove5().setMove(WaterbendingStone.move5(this));
                moveController.getMove6().setMove(WaterbendingStone.move6(this));
                moveController.getMove7().setMove(WaterbendingStone.move7(this));
                moveController.getMove8().setMove(WaterbendingStone.move8(this));
            } else if (activeStone.isSimilar(ItemStones.waterStoneIce0)) {
                moveController.getMove1().setMove(WaterStone.move1(this));
                moveController.getMove2().setMove(WaterStone.move2(this));
                moveController.getMove3().setMove(WaterStone.move3(this));
                moveController.getMove4().setMove(IceStone.move4(this));
            } else if (activeStone.isSimilar(ItemStones.waterStoneIce1)) {
                moveController.getMove1().setMove(WaterStone.move1(this));
                moveController.getMove2().setMove(WaterStone.move2(this));
                moveController.getMove3().setMove(WaterStone.move3(this));
                moveController.getMove4().setMove(IceStone.move4(this));
                moveController.getMove5().setMove(IceStone.move5(this));
            } else if (activeStone.isSimilar(ItemStones.waterStoneIce2)) {
                moveController.getMove1().setMove(WaterStone.move1(this));
                moveController.getMove2().setMove(WaterStone.move2(this));
                moveController.getMove3().setMove(WaterStone.move3(this));
                moveController.getMove4().setMove(IceStone.move4(this));
                moveController.getMove5().setMove(IceStone.move5(this));
                moveController.getMove6().setMove(IceStone.move6(this));
            } else if (activeStone.isSimilar(ItemStones.waterStoneIce3)) {
                moveController.getMove1().setMove(WaterStone.move1(this));
                moveController.getMove2().setMove(WaterStone.move2(this));
                moveController.getMove3().setMove(WaterStone.move3(this));
                moveController.getMove4().setMove(IceStone.move4(this));
                moveController.getMove5().setMove(IceStone.move5(this));
                moveController.getMove6().setMove(IceStone.move6(this));
                moveController.getMove7().setMove(IceStone.move7(this));
            } else if (activeStone.isSimilar(ItemStones.waterStoneIce4)) {
                moveController.getMove1().setMove(WaterStone.move1(this));
                moveController.getMove2().setMove(WaterStone.move2(this));
                moveController.getMove3().setMove(WaterStone.move3(this));
                moveController.getMove4().setMove(IceStone.move4(this));
                moveController.getMove5().setMove(IceStone.move5(this));
                moveController.getMove6().setMove(IceStone.move6(this));
                moveController.getMove7().setMove(IceStone.move7(this));
                moveController.getMove8().setMove(IceStone.move8(this));
            } else if (activeStone.isSimilar(ItemStones.fireStone0)) {
                moveController.getMove1().setMove(FireStone.move1(this));
            } else if (activeStone.isSimilar(ItemStones.fireStone1)) {
                moveController.getMove1().setMove(FireStone.move1(this));
                moveController.getMove2().setMove(FireStone.move2(this));
            } else if (activeStone.isSimilar(ItemStones.fireStone2)) {
                moveController.getMove1().setMove(FireStone.move1(this));
                moveController.getMove2().setMove(FireStone.move2(this));
                moveController.getMove3().setMove(FireStone.move3(this));
            } else if (activeStone.isSimilar(ItemStones.fireStoneHellFire0)) {
                moveController.getMove1().setMove(FireStone.move1(this));
                moveController.getMove2().setMove(FireStone.move2(this));
                moveController.getMove3().setMove(FireStone.move3(this));
                moveController.getMove4().setMove(HellfireStone.move4(this));
            } else if (activeStone.isSimilar(ItemStones.fireStoneHellFire1)) {
                moveController.getMove1().setMove(FireStone.move1(this));
                moveController.getMove2().setMove(FireStone.move2(this));
                moveController.getMove3().setMove(FireStone.move3(this));
                moveController.getMove4().setMove(HellfireStone.move4(this));
                moveController.getMove5().setMove(HellfireStone.move5(this));
            } else if (activeStone.isSimilar(ItemStones.fireStoneHellFire2)) {
                moveController.getMove1().setMove(FireStone.move1(this));
                moveController.getMove2().setMove(FireStone.move2(this));
                moveController.getMove3().setMove(FireStone.move3(this));
                moveController.getMove4().setMove(HellfireStone.move4(this));
                moveController.getMove5().setMove(HellfireStone.move5(this));
                moveController.getMove6().setMove(HellfireStone.move6(this));
            } else if (activeStone.isSimilar(ItemStones.fireStoneHellFire3)) {
                moveController.getMove1().setMove(FireStone.move1(this));
                moveController.getMove2().setMove(FireStone.move2(this));
                moveController.getMove3().setMove(FireStone.move3(this));
                moveController.getMove4().setMove(HellfireStone.move4(this));
                moveController.getMove5().setMove(HellfireStone.move5(this));
                moveController.getMove6().setMove(HellfireStone.move6(this));
                moveController.getMove7().setMove(HellfireStone.move7(this));
            } else if (activeStone.isSimilar(ItemStones.fireStoneHellFire4)) {
                moveController.getMove1().setMove(FireStone.move1(this));
                moveController.getMove2().setMove(FireStone.move2(this));
                moveController.getMove3().setMove(FireStone.move3(this));
                moveController.getMove4().setMove(HellfireStone.move4(this));
                moveController.getMove5().setMove(HellfireStone.move5(this));
                moveController.getMove6().setMove(HellfireStone.move6(this));
                moveController.getMove7().setMove(HellfireStone.move7(this));
                moveController.getMove8().setMove(HellfireStone.move8(this));
            } else if (activeStone.isSimilar(ItemStones.fireStoneExplosion0)) {
                moveController.getMove1().setMove(FireStone.move1(this));
                moveController.getMove2().setMove(FireStone.move2(this));
                moveController.getMove3().setMove(FireStone.move3(this));
                moveController.getMove4().setMove(ExplosionStone.move4(this));
            } else if (activeStone.isSimilar(ItemStones.fireStoneExplosion1)) {
                moveController.getMove1().setMove(FireStone.move1(this));
                moveController.getMove2().setMove(FireStone.move2(this));
                moveController.getMove3().setMove(FireStone.move3(this));
                moveController.getMove4().setMove(ExplosionStone.move4(this));
                moveController.getMove5().setMove(ExplosionStone.move5(this));
            } else if (activeStone.isSimilar(ItemStones.fireStoneExplosion2)) {
                moveController.getMove1().setMove(FireStone.move1(this));
                moveController.getMove2().setMove(FireStone.move2(this));
                moveController.getMove3().setMove(FireStone.move3(this));
                moveController.getMove4().setMove(ExplosionStone.move4(this));
                moveController.getMove5().setMove(ExplosionStone.move5(this));
                moveController.getMove6().setMove(ExplosionStone.move6(this));
            } else if (activeStone.isSimilar(ItemStones.fireStoneExplosion3)) {
                moveController.getMove1().setMove(FireStone.move1(this));
                moveController.getMove2().setMove(FireStone.move2(this));
                moveController.getMove3().setMove(FireStone.move3(this));
                moveController.getMove4().setMove(ExplosionStone.move4(this));
                moveController.getMove5().setMove(ExplosionStone.move5(this));
                moveController.getMove6().setMove(ExplosionStone.move6(this));
                moveController.getMove7().setMove(ExplosionStone.move7(this));
            } else if (activeStone.isSimilar(ItemStones.fireStoneExplosion4)) {
                moveController.getMove1().setMove(FireStone.move1(this));
                moveController.getMove2().setMove(FireStone.move2(this));
                moveController.getMove3().setMove(FireStone.move3(this));
                moveController.getMove4().setMove(ExplosionStone.move4(this));
                moveController.getMove5().setMove(ExplosionStone.move5(this));
                moveController.getMove6().setMove(ExplosionStone.move6(this));
                moveController.getMove7().setMove(ExplosionStone.move7(this));
                moveController.getMove8().setMove(ExplosionStone.move8(this));
            } else if (activeStone.isSimilar(ItemStones.airStone0)) {
                moveController.getMove1().setMove(AirStone.move1(this));
            } else if (activeStone.isSimilar(ItemStones.airStone1)) {
                moveController.getMove1().setMove(AirStone.move1(this));
                moveController.getMove2().setMove(AirStone.move2(this));
            } else if (activeStone.isSimilar(ItemStones.airStone2)) {
                moveController.getMove1().setMove(AirStone.move1(this));
                moveController.getMove2().setMove(AirStone.move2(this));
                moveController.getMove3().setMove(AirStone.move3(this));
            } else if (activeStone.isSimilar(ItemStones.airStoneAgility0)) {
                moveController.getMove1().setMove(AirStone.move1(this));
                moveController.getMove2().setMove(AirStone.move2(this));
                moveController.getMove3().setMove(AirStone.move3(this));
                moveController.getMove4().setMove(AgilityStone.move4(this));
            } else if (activeStone.isSimilar(ItemStones.airStoneAgility1)) {
                moveController.getMove1().setMove(AirStone.move1(this));
                moveController.getMove2().setMove(AirStone.move2(this));
                moveController.getMove3().setMove(AirStone.move3(this));
                moveController.getMove4().setMove(AgilityStone.move4(this));
                moveController.getMove5().setMove(AgilityStone.move5(this));
            } else if (activeStone.isSimilar(ItemStones.airStoneAgility2)) {
                moveController.getMove1().setMove(AirStone.move1(this));
                moveController.getMove2().setMove(AirStone.move2(this));
                moveController.getMove3().setMove(AirStone.move3(this));
                moveController.getMove4().setMove(AgilityStone.move4(this));
                moveController.getMove5().setMove(AgilityStone.move5(this));
                moveController.getMove6().setMove(AgilityStone.move6(this));
            } else if (activeStone.isSimilar(ItemStones.airStoneAgility3)) {
                moveController.getMove1().setMove(AirStone.move1(this));
                moveController.getMove2().setMove(AirStone.move2(this));
                moveController.getMove3().setMove(AirStone.move3(this));
                moveController.getMove4().setMove(AgilityStone.move4(this));
                moveController.getMove5().setMove(AgilityStone.move5(this));
                moveController.getMove6().setMove(AgilityStone.move6(this));
                moveController.getMove7().setMove(AgilityStone.move7(this));
            } else if (activeStone.isSimilar(ItemStones.airStoneAgility4)) {
                moveController.getMove1().setMove(AirStone.move1(this));
                moveController.getMove2().setMove(AirStone.move2(this));
                moveController.getMove3().setMove(AirStone.move3(this));
                moveController.getMove4().setMove(AgilityStone.move4(this));
                moveController.getMove5().setMove(AgilityStone.move5(this));
                moveController.getMove6().setMove(AgilityStone.move6(this));
                moveController.getMove7().setMove(AgilityStone.move7(this));
                moveController.getMove8().setMove(AgilityStone.move8(this));
            } else if (activeStone.isSimilar(ItemStones.airStoneBending0)) {
                moveController.getMove1().setMove(AirStone.move1(this));
                moveController.getMove2().setMove(AirStone.move2(this));
                moveController.getMove3().setMove(AirStone.move3(this));
                moveController.getMove4().setMove(AirbendingStone.move4(this));
            } else if (activeStone.isSimilar(ItemStones.airStoneBending1)) {
                moveController.getMove1().setMove(AirStone.move1(this));
                moveController.getMove2().setMove(AirStone.move2(this));
                moveController.getMove3().setMove(AirStone.move3(this));
                moveController.getMove4().setMove(AirbendingStone.move4(this));
                moveController.getMove5().setMove(AirbendingStone.move5(this));
            } else if (activeStone.isSimilar(ItemStones.airStoneBending2)) {
                moveController.getMove1().setMove(AirStone.move1(this));
                moveController.getMove2().setMove(AirStone.move2(this));
                moveController.getMove3().setMove(AirStone.move3(this));
                moveController.getMove4().setMove(AirbendingStone.move4(this));
                moveController.getMove5().setMove(AirbendingStone.move5(this));
                moveController.getMove6().setMove(AirbendingStone.move6(this));
            } else if (activeStone.isSimilar(ItemStones.airStoneBending3)) {
                moveController.getMove1().setMove(AirStone.move1(this));
                moveController.getMove2().setMove(AirStone.move2(this));
                moveController.getMove3().setMove(AirStone.move3(this));
                moveController.getMove4().setMove(AirbendingStone.move4(this));
                moveController.getMove5().setMove(AirbendingStone.move5(this));
                moveController.getMove6().setMove(AirbendingStone.move6(this));
                moveController.getMove7().setMove(AirbendingStone.move7(this));
            } else if (activeStone.isSimilar(ItemStones.airStoneBending4)) {
                moveController.getMove1().setMove(AirStone.move1(this));
                moveController.getMove2().setMove(AirStone.move2(this));
                moveController.getMove3().setMove(AirStone.move3(this));
                moveController.getMove4().setMove(AirbendingStone.move4(this));
                moveController.getMove5().setMove(AirbendingStone.move5(this));
                moveController.getMove6().setMove(AirbendingStone.move6(this));
                moveController.getMove7().setMove(AirbendingStone.move7(this));
                moveController.getMove8().setMove(AirbendingStone.move8(this));
            } else if (activeStone.isSimilar(ItemStones.earthStone0)) {
                moveController.getMove1().setMove(EarthStone.move2(this));
            } else if (activeStone.isSimilar(ItemStones.earthStone1)) {
                moveController.getMove1().setMove(EarthStone.move1(this));
                moveController.getMove2().setMove(EarthStone.move2(this));
            } else if (activeStone.isSimilar(ItemStones.earthStone2)) {
                moveController.getMove1().setMove(EarthStone.move1(this));
                moveController.getMove2().setMove(EarthStone.move2(this));
                moveController.getMove3().setMove(EarthStone.move3(this));
            } else if (activeStone.isSimilar(ItemStones.earthStoneBending0)) {
                moveController.getMove1().setMove(EarthStone.move1(this));
                moveController.getMove2().setMove(EarthStone.move2(this));
                moveController.getMove3().setMove(EarthStone.move3(this));
                moveController.getMove4().setMove(EarthbendingStone.move4(this));
            } else if (activeStone.isSimilar(ItemStones.earthStoneBending1)) {
                moveController.getMove1().setMove(EarthStone.move1(this));
                moveController.getMove2().setMove(EarthStone.move2(this));
                moveController.getMove3().setMove(EarthStone.move3(this));
                moveController.getMove4().setMove(EarthbendingStone.move4(this));
                moveController.getMove5().setMove(EarthbendingStone.move5(this));
            } else if (activeStone.isSimilar(ItemStones.earthStoneBending2)) {
                moveController.getMove1().setMove(EarthStone.move1(this));
                moveController.getMove2().setMove(EarthStone.move2(this));
                moveController.getMove3().setMove(EarthStone.move3(this));
                moveController.getMove4().setMove(EarthbendingStone.move4(this));
                moveController.getMove5().setMove(EarthbendingStone.move5(this));
                moveController.getMove6().setMove(EarthbendingStone.move6(this));
            } else if (activeStone.isSimilar(ItemStones.earthStoneBending3)) {
                moveController.getMove1().setMove(EarthStone.move1(this));
                moveController.getMove2().setMove(EarthStone.move2(this));
                moveController.getMove3().setMove(EarthStone.move3(this));
                moveController.getMove4().setMove(EarthbendingStone.move4(this));
                moveController.getMove5().setMove(EarthbendingStone.move5(this));
                moveController.getMove6().setMove(EarthbendingStone.move6(this));
                moveController.getMove7().setMove(EarthbendingStone.move7(this));
            } else if (activeStone.isSimilar(ItemStones.earthStoneBending4)) {
                moveController.getMove1().setMove(EarthStone.move1(this));
                moveController.getMove2().setMove(EarthStone.move2(this));
                moveController.getMove3().setMove(EarthStone.move3(this));
                moveController.getMove4().setMove(EarthbendingStone.move4(this));
                moveController.getMove5().setMove(EarthbendingStone.move5(this));
                moveController.getMove6().setMove(EarthbendingStone.move6(this));
                moveController.getMove7().setMove(EarthbendingStone.move7(this));
                moveController.getMove8().setMove(EarthbendingStone.move8(this));
            } else if (activeStone.isSimilar(ItemStones.earthStoneLava0)) {
                moveController.getMove1().setMove(EarthStone.move1(this));
                moveController.getMove2().setMove(EarthStone.move2(this));
                moveController.getMove3().setMove(EarthStone.move3(this));
                moveController.getMove4().setMove(LavaStone.move4(this));
            } else if (activeStone.isSimilar(ItemStones.earthStoneLava1)) {
                moveController.getMove1().setMove(EarthStone.move1(this));
                moveController.getMove2().setMove(EarthStone.move2(this));
                moveController.getMove3().setMove(EarthStone.move3(this));
                moveController.getMove4().setMove(LavaStone.move4(this));
                moveController.getMove5().setMove(LavaStone.move5(this));
            } else if (activeStone.isSimilar(ItemStones.earthStoneLava2)) {
                moveController.getMove1().setMove(EarthStone.move1(this));
                moveController.getMove2().setMove(EarthStone.move2(this));
                moveController.getMove3().setMove(EarthStone.move3(this));
                moveController.getMove4().setMove(LavaStone.move4(this));
                moveController.getMove5().setMove(LavaStone.move5(this));
                moveController.getMove6().setMove(LavaStone.move6(this));
            } else if (activeStone.isSimilar(ItemStones.earthStoneLava3)) {
                moveController.getMove1().setMove(EarthStone.move1(this));
                moveController.getMove2().setMove(EarthStone.move2(this));
                moveController.getMove3().setMove(EarthStone.move3(this));
                moveController.getMove4().setMove(LavaStone.move4(this));
                moveController.getMove5().setMove(LavaStone.move5(this));
                moveController.getMove6().setMove(LavaStone.move6(this));
                moveController.getMove7().setMove(LavaStone.move7(this));
            } else if (activeStone.isSimilar(ItemStones.earthStoneLava4)) {
                moveController.getMove1().setMove(EarthStone.move1(this));
                moveController.getMove2().setMove(EarthStone.move2(this));
                moveController.getMove3().setMove(EarthStone.move3(this));
                moveController.getMove4().setMove(LavaStone.move4(this));
                moveController.getMove5().setMove(LavaStone.move5(this));
                moveController.getMove6().setMove(LavaStone.move6(this));
                moveController.getMove7().setMove(LavaStone.move7(this));
                moveController.getMove8().setMove(LavaStone.move8(this));
            }
        }
    }

    private void clearMoves() {
        this.moveController.clearMoves();

        List<FallingBlock> allFallingBlocks = new ArrayList<>();
        allFallingBlocks.addAll(this.getMove6FallingBlocks());
        allFallingBlocks.addAll(this.getMove6LaunchedFallingBlocks());

        for (FallingBlock fallingBlock : allFallingBlocks) {
            fallingBlock.remove();
        }

        this.getMove6LaunchedFallingBlocks().clear();
        this.getMove6FallingBlocks().clear();
    }

    public void resetWorld() {
        this.resetMapping.forEach(((location, material) -> this.player.getWorld().getBlockAt(location).setType(material)));
    }


    public void setMovesEnabled(boolean movesEnabled) {
        this.movesEnabled = movesEnabled;
    }

    public boolean areMovesEnabled() {
        return this.movesEnabled;
    }

    public List<FallingBlock> getMove6FallingBlocks() {
        return this.move6FallingBlocks;
    }

    public List<FallingBlock> getMove6LaunchedFallingBlocks() {
        return this.move6LaunchedFallingBlocks;
    }

    public List<FallingBlock> getCometFallingBlocks() {
        return this.move6FallingBlocks;
    }

    public Comet getComet() {
        return this.comet;
    }

    public void setComet(Comet comet) {
        this.comet = comet;
    }

    public void setMove8active(boolean move8active) {
        this.move8active = move8active;
    }

    public boolean isMove8active() {
        return this.move8active;
    }

    public List<FallingBlock> getMove8FallingBlocks() {
        return this.move8FallingBlocks;
    }

    public void setMove8FallingBlocks(List<FallingBlock> fallingBlocks) {
        this.move8FallingBlocks = fallingBlocks;
    }

    public void clearMove8FallingBlocks() {
        this.move8FallingBlocks.clear();
    }


    public FireBall getFireBall() {
        return this.fireBall;
    }

    public void setFireBall(FireBall fireBall) {
        this.fireBall = fireBall;
    }

    public ArrayList<Location> getWallLocations() {
        return this.wallLocations;
    }

    public long getFireWallsEndTime() {
        return this.fireWallsEndTime;
    }

    public void placeWall() {
        if (this.fireWallsEndTime == -1) {
            this.fireWallsEndTime = System.currentTimeMillis() + 15000;

            this.slowTask = new BukkitRunnable() {
                @Override
                public void run() {
                    wallLocations.forEach(location -> player.getWorld().getNearbyEntities(location, 0.1, 1, 0.1).forEach(entity -> entity.setVelocity(new Vector(0, 0, 0))));
                }
            }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
        }

        this.fireWallsAmount++;
    }

    public boolean canPlaceWall() {
        return this.fireWallsAmount < 5;
    }

    public void endFireWalls() {
        this.slowTask.cancel();
        this.slowTask = null;
        this.fireWallsAmount = 0;
        this.fireWallsEndTime = -1;
        this.wallLocations.clear();
    }

    public FireFireworks getFireFireworks() {
        return this.fireFireworks;
    }

    public void setFireFireworks(FireFireworks fireFireworks) {
        this.fireFireworks = fireFireworks;
    }

    public boolean hasWarMachineGrenades() {
        return this.warMachineGrenades > 0;
    }

    public void fillWarMachineGrenades() {
        this.warMachineGrenades = 3;
    }

    public void useWarMachineGrenade() {
        this.warMachineGrenades--;
    }

    public void setWarMachineGrenades(int amount) {
        this.warMachineGrenades = amount;
    }


    public List<Location> getLavaLocations() {
        return this.lavaLocations;
    }

    public List<Block> getCurrentPlatform() {
        return this.currentPlatform;
    }

    public List<Block> getAllPlatformBlocks() {
        return this.allPlatformBlocks;
    }


    public void addLocationMaterialMapping(Location location, Material material) {
        if (!(this.resetMapping.containsKey(location))) {
            this.resetMapping.put(location, material);
        }
    }

    public static ArrayList<ActivePlayer> getActivePlayers() {
        return activePlayers;
    }

    public int getRemainingIceShards() {
        return this.remainingIceShards;
    }

    public boolean useIceShard() {
        if (this.remainingIceShards >= 1) {
            this.remainingIceShards--;
            long ticksOfDelay = (10 - this.remainingIceShards) * 40L;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (remainingIceShards < 10) {
                        remainingIceShards++;
                    }
                }
            }.runTaskLater(StaticVariables.plugin, ticksOfDelay);
            return true;
        }
        return false;
    }

    public boolean hasIceSpear() {
        return this.iceSpear != null;
    }

    public void setIceSpear(BukkitTask bukkitTask) {
        this.iceSpear = bukkitTask;
    }

    public void clearIceSpear() {
        this.iceSpear.cancel();
        this.iceSpear = null;
    }

    public boolean isDoublePassive1() {
        return this.doublePassive1;
    }

    public void setDoublePassive1(boolean doublePassive1) {
        this.doublePassive1 = doublePassive1;
    }

    public void disableDoubleJump() {
        this.canDoubleJump = false;
    }

    public void enableDoubleJump() {
        this.canDoubleJump = true;
    }

    public boolean canDoubleJump() {
        return this.canDoubleJump;
    }

    public LinkedList<Arrow> getMove5Arrows() {
        return this.move5Arrows;
    }

    public boolean doCriticalOnGround() {
        return this.criticalOnGround;
    }

    public void setCriticalOnGround(boolean criticalOnGround) {
        this.criticalOnGround = criticalOnGround;
    }

    public Vector getMovingDirection() {
        return this.movingDirection;
    }

    public void setMovingDirection(Vector movingDirection) {
        this.movingDirection = movingDirection;
    }

    public void setChargingStart() {
        this.chargingStart = System.currentTimeMillis();
    }

    public double getCharge() {
        if ((int) this.chargingStart != -1) {
            return (double) System.currentTimeMillis() - this.chargingStart;
        } else {
            return -1;
        }
    }

    public void resetCharge() {
        this.chargingStart = -1;
    }

    public int getMove7LaunchState() {
        return this.move7LaunchState;
    }

    public void setMove7LaunchState(int move7LaunchState) {
        this.move7LaunchState = move7LaunchState;
    }

    public boolean isInAirBoost() {
        return this.inAirBoost;
    }

    private void removeInAirBoost() {
        this.inAirBoost = false;
    }

    public void activateAirBoost() {
        this.inAirBoost = true;
        new BukkitRunnable() {
            @Override
            public void run() {
                removeInAirBoost();
            }
        }.runTaskLater(StaticVariables.plugin, 1200);
    }

    public void setWindCloak(boolean windCloak) {
        this.windCloak = windCloak;
    }

    public boolean hasWindCloak() {
        return this.windCloak;
    }

    public void setPossibleTarget(Entity entity) {
        this.possibleTarget = entity;
        new BukkitRunnable() {
            @Override
            public void run() {
                possibleTarget.setGlowing(false);
                possibleTarget = null;
            }
        }.runTaskLater(StaticVariables.plugin, 60L);
    }

    public boolean hasPossibleTarget() {
        return this.possibleTarget != null;
    }

    public Entity getPossibleTarget() {
        return this.possibleTarget;
    }

    public void setLevitatingTask(BukkitTask bukkitTask) {
        this.levitatingTask = bukkitTask;
    }

    public void stopLevitatingTask() {
        this.levitatingTask.cancel();
        this.levitatingTask = null;
    }

    public boolean isNotLevitatingTarget() {
        return this.levitatingTask == null;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public Entity getTarget() {
        return this.target;
    }

    public void clearTarget() {
        this.target = null;
    }

    public void setMove8from(Location move8from) {
        this.move8from = move8from;
    }

    public void setMove8to(Location move8to) {
        this.move8to = move8to;
    }

    public Location getMove8from() {
        return this.move8from;
    }

    public Location getMove8to() {
        return this.move8to;
    }

    public static ActivePlayer getActivePlayer(UUID uuid) {
        for (ActivePlayer activePlayer : activePlayers) {
            if (activePlayer.getPlayer().getUniqueId() == uuid) {
                return activePlayer;
            }
        }
        return null;
    }

    public static void removeActivePlayer(UUID uuid) {
        activePlayers.removeIf(activePlayer -> activePlayer.getPlayer().getUniqueId() == uuid);
    }

    public static void clearActivePlayers() {
        activePlayers.clear();
    }

    public MoveController getMoveController() {
        return this.moveController;
    }

    public boolean closeTradingInventories() {
        return this.closeTradingInventories;
    }

    public void setCloseTradingInventories(boolean closeTradingInventories) {
        this.closeTradingInventories = closeTradingInventories;
    }
}
