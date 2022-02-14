package com.lennertsoffers.elementalstones.customClasses.models;

public class Move {

    private Runnable move;
    private int cooldown;
    private long blockedUntil = 0;

    public Runnable getMove() {
        return move;
    }

    public void setMove(Runnable move) {
        this.move = move;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public boolean activateMove() {
        System.out.println("isNull: " + (this.isNull() ? "true" : "false"));
        System.out.println("isOnCooldown: " + (this.isOnCooldown() ? "true" : "false"));
        if (!this.isNull()) {
            if (!this.isOnCooldown()) {
                this.move.run();
                this.block();
                System.out.println("ActivateMove: true");
                return true;
            }
        }

        System.out.println("ActivateMove: false");
        return false;
    }

    public void block() {
        this.blockedUntil = System.currentTimeMillis() + cooldown;
    }

    public void clearMove() {
        this.move = null;
    }

    public boolean isNull() {
        return this.move == null;
    }

    public boolean isOnCooldown() {
        return this.blockedUntil > System.currentTimeMillis();
    }

    public long secondsUntilActive() {
        long miliseconds = this.blockedUntil - System.currentTimeMillis();
        miliseconds = miliseconds < 0 ? 0 : miliseconds;
        miliseconds /= 1000;
        return miliseconds;
    }
}
