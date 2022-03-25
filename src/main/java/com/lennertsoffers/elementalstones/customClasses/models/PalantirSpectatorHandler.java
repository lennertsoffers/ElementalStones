package com.lennertsoffers.elementalstones.customClasses.models;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public class PalantirSpectatorHandler {

    private final LinkedList<Player> spectatorTargets = new LinkedList<>();
    private boolean requestedNewSpectatorTarget = false;
    private Location respawnLocation;
    private BossBar bossBar;
    private GameMode gameMode;

    private final Player player;

    public PalantirSpectatorHandler(Player player) {
        this.player = player;
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
        if (this.hasSpectatorTargets()) {
            this.requestedNewSpectatorTarget = true;
            System.out.println("contin");
            System.out.println("contin");
        } else {
            System.out.println("end");
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

    public void endEffect() {
        this.bossBar.removeAll();
        this.bossBar.removePlayer(this.player);
        this.clearSpectatorTargets();
        this.player.setGameMode(this.gameMode);
        this.player.teleport(this.respawnLocation);
    }
}
