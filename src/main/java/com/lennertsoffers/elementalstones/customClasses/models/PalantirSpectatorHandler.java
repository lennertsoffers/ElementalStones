package com.lennertsoffers.elementalstones.customClasses.models;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedList;

public class PalantirSpectatorHandler {

    private final ArrayList<Player> spectatorTargets = new ArrayList<>();
    private int spectatorIndex = 0;
    private boolean requestedNewSpectatorTarget = false;
    private Location respawnLocation;
    private BossBar bossBar;
    private GameMode gameMode;
    private Player spectatorTraget;

    private final Player player;

    public PalantirSpectatorHandler(Player player) {
        this.player = player;
    }

    public boolean hasSpectatorTargets() {
        return !this.spectatorTargets.isEmpty();
    }

    public ArrayList<Player> getSpectatorTargets() {
        return this.spectatorTargets;
    }

    public void clearSpectatorTargets() {
        this.spectatorTargets.clear();
        this.requestedNewSpectatorTarget = false;
    }

    public Player getNewSpectatorTarget() {
        this.requestedNewSpectatorTarget = false;

        Player player = this.spectatorTargets.get(this.spectatorIndex);
        this.spectatorTraget = player;

        this.spectatorIndex++;
        if (this.spectatorIndex >= this.spectatorTargets.size()) {
            this.spectatorIndex = 0;
        }

        return player;
    }

    public boolean hasRequestedNewSpectatorTarget() {
        return this.requestedNewSpectatorTarget;
    }

    public void requestNewSpectatorTarget() {
        if (this.hasSpectatorTargets()) {
            this.requestedNewSpectatorTarget = true;
        } else {
            this.endEffect();
        }
    }

    public void setRespawnLocation(Location location) {
        this.respawnLocation = location;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void setBossBar(BossBar bossBar) {
        this.bossBar = bossBar;
    }

    public BossBar getBossBar() {
        return this.bossBar;
    }

    public void teleportToPlayer() {
        this.respawnLocation = this.spectatorTraget.getLocation();
        this.endEffect();
    }

    public void endEffect() {
        this.bossBar.setProgress(0);
        this.bossBar.setVisible(false);
        this.bossBar.removeAll();
        this.bossBar.removePlayer(this.player);
        this.clearSpectatorTargets();
        this.player.setGameMode(this.gameMode);
        this.player.teleport(this.respawnLocation);
    }
}
