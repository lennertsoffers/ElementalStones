package com.lennertsoffers.elementalstones.customClasses;

import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ActivePlayer {
    private final Player player;
    private boolean active;
    private static final ArrayList<ActivePlayer> activePlayers = new ArrayList<>();

    // EarthStone
    private List<FallingBlock> move8FallingBlocks;
    private int move8Stage;

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

    public void setActive(boolean active) {
        this.active = active;
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

    public static ArrayList<ActivePlayer> getActivePlayers() {
        return activePlayers;
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
}
