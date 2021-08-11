package com.lennertsoffers.elementalstones.customClasses;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class ActivePlayer {
    private final Player player;
    private boolean active;
    private static final ArrayList<ActivePlayer> activePlayers = new ArrayList<>();

    public ActivePlayer(Player player) {
        this.player = player;
        this.active = false;
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
