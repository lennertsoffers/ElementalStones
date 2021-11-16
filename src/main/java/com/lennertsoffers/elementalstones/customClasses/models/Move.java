package com.lennertsoffers.elementalstones.customClasses.models;

public class Move {

    private Runnable move;
    private int cooldown;
    private long blockedUntil;

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
        if (this.move != null) {
            if (this.blockedUntil < System.currentTimeMillis()) {
                this.move.run();
//                this.blockedUntil = System.currentTimeMillis() + cooldown;
                return true;
            }
        }
        return false;
    }

    public void block() {
        this.blockedUntil = System.currentTimeMillis() + cooldown;
    }

    public void clearMove() {
        this.move = null;
    }
}
