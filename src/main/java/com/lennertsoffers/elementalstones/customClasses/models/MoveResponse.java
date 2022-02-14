package com.lennertsoffers.elementalstones.customClasses.models;

public class MoveResponse {

    private final Runnable runnable;
    private final boolean setCooldown;

    public MoveResponse(Runnable runnable, boolean setCooldown) {
        this.runnable = runnable;
        this.setCooldown = setCooldown;
    }

    public Runnable getRunnable() {
        return this.runnable;
    }

    public boolean getSetCooldown() {
        return this.setCooldown;
    }
}
