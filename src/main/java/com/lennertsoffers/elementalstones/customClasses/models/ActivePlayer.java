package com.lennertsoffers.elementalstones.customClasses.models;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.Comet;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireBall;
import com.lennertsoffers.elementalstones.customClasses.models.bukkitRunnables.FireFireworks;
import com.lennertsoffers.elementalstones.items.ItemStones;
import com.lennertsoffers.elementalstones.passives.PassiveHandler;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;

public class ActivePlayer {
    private final Player player;
    private boolean active;
    private Vector movingDirection;
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

    // Consumables
    private final LinkedList<Player> spectatorTargets = new LinkedList<>();
    private boolean requestedNewSpectatorTarget = false;

    public ActivePlayer(Player player) {
        this.player = player;
        this.active = false;
        activePlayers.add(this);
        this.moveController = new MoveController(this);
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean isActive() {
        return this.active;
    }

    public void toggleActive() {
        if (this.active) {
            this.setInactive();
        } else {
            this.setActive();
        }
    }

    private void setActive() {
        // Set player to active
        this.active = true;

        // Select correct moves
        this.moveController.loadMoves();

        // Initialize passives
        PassiveHandler.iceBoots(this);
        initAgilityStonePassive();

        // Inform player of his/her state
        this.player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You are in move mode!");
    }

    public void setInactive() {
        this.active = false;
        this.resetWorld();
        this.player.setAllowFlight(false);
        this.moveController.clearScoreBoard();
        clearMoves();

        PassiveHandler.iceBoots(this);
        this.player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You left move mode!");
    }

    private void initAgilityStonePassive() {
        if (
                !Collections.disjoint(Arrays.asList(this.player.getInventory().getContents()), ItemStones.agilityStones) ||
                !Collections.disjoint(Arrays.asList(this.player.getInventory().getContents()), ItemStones.airbendingStones)
        ) {
            player.setAllowFlight(true);
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
        return this.iceSpear == null;
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
        return this.possibleTarget == null;
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

    public boolean hasSpectatorTargets() {
        return !this.spectatorTargets.isEmpty();
    }

    public LinkedList<Player> getSpectatorTargets() {
        return this.spectatorTargets;
    }

    public void clearSpectatorTargets() {
        this.spectatorTargets.clear();
        this.requestedNewSpectatorTarget = false;
    }

    public Player getNewSpectatorTarget() {
        this.requestedNewSpectatorTarget = false;
        return this.spectatorTargets.pop();
    }

    public boolean hasRequestedNewSpectatorTarget() {
        return this.requestedNewSpectatorTarget;
    }

    public void requestNewSpectatorTarget() {
        this.requestedNewSpectatorTarget = true;
    }
}
