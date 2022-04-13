package com.lennertsoffers.elementalstones.moves;

import com.lennertsoffers.elementalstones.ElementalStones;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import org.bukkit.entity.Player;

public abstract class Move {

    private final ActivePlayer activePlayer;
    private final Player player;
    private long cooldown = 0;
    private final int maxCooldown;
    private final String name;
    private final String stone;
    private final String path;
    private final int moveIndex;

    public Move(ActivePlayer activePlayer, String name, String stone, String path, int moveIndex) {
        this.activePlayer = activePlayer;
        this.player = activePlayer.getPlayer();
        this.name = name;
        this.stone = stone;
        this.path = path;
        this.moveIndex = moveIndex;
        this.maxCooldown = ElementalStones.configuration.getBoolean("disable_cooldowns") ? 0 : ElementalStones.configuration.getInt(this.getFullName());
    }

    abstract public void useMove();

    public void setCooldown() {
        this.cooldown = System.currentTimeMillis() + this.maxCooldown;
    }

    public int getSecondsUntilActive() {
        return (int) ((this.cooldown - System.currentTimeMillis()) / 1000f);
    }

    public boolean isOnCooldown() {
        return System.currentTimeMillis() < this.cooldown;
    }

    public String getName() {
        return this.name;
    }

    public String getFullName() {
        return stone + "." + path + ".move" + moveIndex;
    }

    public String getStone() {
        return this.stone;
    }

    public Player getPlayer() {
        return this.player;
    }

    public ActivePlayer getActivePlayer() {
        return this.activePlayer;
    }
}
