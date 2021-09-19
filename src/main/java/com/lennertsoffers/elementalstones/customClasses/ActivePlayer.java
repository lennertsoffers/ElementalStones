package com.lennertsoffers.elementalstones.customClasses;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ActivePlayer {
    private final Player player;
    private boolean active;
    private ArrayList<Location> overrideLocations = new ArrayList<>();
    private static final ArrayList<ActivePlayer> activePlayers = new ArrayList<>();
    private final Map<Location, Material> resetMapping = new HashMap<>();


    // Earth Stone
    private FallingBlock fallingBlock;
    private List<FallingBlock> move8FallingBlocks;
    private int move8Stage;

    // Fire Stone
    private long hellfireStoneMove4TimeRemaining = -1;
    private BukkitRunnable floatingFire;
    private Location floatingFireLocation;
    private BukkitRunnable removeBasald;
    private ArrayList<Location> lavaBlockLocations = new ArrayList<>();
    private boolean lavaStoneMove8Active = false;

    // Water Stone
    private int remainingIceShards = 10;

    public ActivePlayer(Player player) {
        this.player = player;
        this.active = false;
        this.move8Stage = 0;
        activePlayers.add(this);
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
            this.resetMapping.forEach(((location, material) -> this.player.getWorld().getBlockAt(location).setType(material)));
            this.player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You left move mode!");

        } else {
            this.active = true;
            this.player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You are in move mode!");
        }
    }

    public void addOverrideLocation(Location location) {
        if (!this.overrideLocations.contains(location)) {
            this.overrideLocations.add(location.getBlock().getLocation());
        }
    }

    public void clearOverrideLocations() {
        this.overrideLocations.clear();
    }

    public ArrayList<Location> getOverrideLocations() {
        return this.overrideLocations;
    }

    public FallingBlock getFallingBlock() {
        return this.fallingBlock;
    }

    private void setFallingBlockValue(FallingBlock fallingBlock) {
        this.fallingBlock = fallingBlock;
    }

    public void setFallingBlock(FallingBlock fallingBlock) {
        setFallingBlockValue(fallingBlock);
        StaticVariables.scheduler.scheduleSyncDelayedTask(StaticVariables.plugin, () -> setFallingBlockValue(null), 100L);
    }

    public List<FallingBlock> getMove8FallingBlocks() {
        return this.move8FallingBlocks;
    }

    public void setMove8FallingBlocks(List<FallingBlock> fallingBlocks) {
        this.move8FallingBlocks = fallingBlocks;
    }

    public int getMove8Stage() {
        return this.move8Stage;
    }

    public void increaseMove8Stage() {
        this.move8Stage++;
    }

    public void setMove8Stage(int newValue) {
        this.move8Stage = newValue;
    }

    public BukkitRunnable getFloatingFire() {
        return this.floatingFire;
    }

    public void setFloatingFire(BukkitRunnable bukkitRunnable) {
        this.floatingFire = bukkitRunnable;
        this.floatingFire.runTaskTimer(StaticVariables.plugin, 0L, 1L);
    }

    public void cancelFloatingFire() {
        this.floatingFire.cancel();
        this.floatingFire = null;
    }

    public Location getFloatingFireLocation() {
        return this.floatingFireLocation;
    }

    public void setFloatingFireLocation(Location location) {
        this.floatingFireLocation = location.add(0, 1, 0);
    }

    public boolean hasHellfireStoneMove4TimeRemaining() {
        if (this.hellfireStoneMove4TimeRemaining != -1) {
            if (this.hellfireStoneMove4TimeRemaining > System.currentTimeMillis()) {
                return true;
            } else {
                this.hellfireStoneMove4TimeRemaining = -1;
                return false;
            }
        }
        return false;
    }

    public void setHellfireStoneMove4TimeRemaining() {
        this.hellfireStoneMove4TimeRemaining = System.currentTimeMillis() + (10 * 1000);
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 140, 2, false, false, false));
    }

    public void setRemoveBasald(BukkitRunnable removeBasaldRunnable) {
        if (this.removeBasald != null) {
            this.removeBasald.runTaskTimer(StaticVariables.plugin, 60L, 5L);
        }
        this.removeBasald = removeBasaldRunnable;
    }

    public void addLavaBlockLocation(Location lavaBlockLocation) {
        this.lavaBlockLocations.add(lavaBlockLocation);
    }

    public void removeLavaBlockLocation(Location lavaBlockLocation) {
        this.lavaBlockLocations.remove(lavaBlockLocation);
    }

    public boolean isInLavaBlockLocations(Location location) {
        for (Location lavaBlockLocation : this.lavaBlockLocations) {
            if (
                    (lavaBlockLocation.getBlockX() == location.getBlockX()) &&
                            (lavaBlockLocation.getBlockY() == location.getBlockY()) &&
                            (lavaBlockLocation.getBlockZ() == location.getBlockZ())
            ) {
                return true;
            }
        }
        return false;
    }

    public boolean isLavaStoneMove8Active() {
        return this.lavaStoneMove8Active;
    }

    public void setLavaStoneMove8Active(boolean lavaStoneMove8Active) {
        this.lavaStoneMove8Active = lavaStoneMove8Active;
    }

    public void addLocationMaterialMapping(Location location, Material material) {
        if (!(this.resetMapping.containsKey(location))) {
            this.resetMapping.put(location, material);
        }
    }

    public void mergeLocationMaterialMapping(Map<Location, Material> locationMaterialMap) {
        locationMaterialMap.forEach((this.resetMapping::putIfAbsent));
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
}
