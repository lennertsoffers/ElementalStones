package com.lennertsoffers.elementalstones.customClasses.models.gameplay;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class PalantirSpectatorHandler {

    private final ArrayList<Player> spectatorTargets = new ArrayList<>();
    private int spectatorIndex = 0;
    private boolean requestedNewSpectatorTarget = false;
    private Location respawnLocation;
    private BossBar bossBar;
    private GameMode gameMode;
    private Player spectatorTarget;
    private BukkitTask playerSwitch;

    private final Player player;

    public PalantirSpectatorHandler(Player player) {
        this.player = player;
    }

    public void setPlayerSwitch(BukkitTask playerSwitch) {
        this.playerSwitch = playerSwitch;
    }

    public boolean hasSpectatorTargets() {
        return !this.spectatorTargets.isEmpty();
    }

    public boolean hasSpectatorTarget() {
        return this.spectatorTarget != null;
    }

    public ArrayList<Player> getSpectatorTargets() {
        return this.spectatorTargets;
    }

    public void clearSpectatorTargets() {
        this.spectatorTargets.clear();
        this.requestedNewSpectatorTarget = false;
        this.spectatorTarget = null;
    }

    public Player getNewSpectatorTarget() {
        this.requestedNewSpectatorTarget = false;

        Player player = this.spectatorTargets.get(this.spectatorIndex);
        this.spectatorTarget = player;

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

    public boolean teleportToPlayer() {
        if (this.hasSpectatorTarget()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    respawnLocation = spectatorTarget.getLocation();
                    endEffect();
                }
            }.runTask(StaticVariables.plugin);

            return true;
        }

        return false;
    }

    public void endEffect() {
        this.playerSwitch.cancel();
        this.playerSwitch = null;

        this.bossBar.setProgress(0);
        this.bossBar.setVisible(false);
        this.bossBar.removeAll();
        this.bossBar.removePlayer(this.player);

        this.clearSpectatorTargets();

        this.player.setGameMode(this.gameMode);
        this.player.teleport(this.respawnLocation);
    }
}
