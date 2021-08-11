package com.lennertsoffers.elementalstones.customClasses;

import org.bukkit.entity.Player;

import java.util.ArrayList;

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
}
