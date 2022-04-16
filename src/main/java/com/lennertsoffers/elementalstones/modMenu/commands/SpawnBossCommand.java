package com.lennertsoffers.elementalstones.modMenu.commands;

import com.lennertsoffers.elementalstones.customClasses.models.gameplay.Boss;

public class SpawnBossCommand extends PlayerCommand {

    public SpawnBossCommand(String command) {
        super(command);
    }

    @Override
    boolean execute() {
        new Boss(this.getPlayer().getLocation());
        return true;
    }
}
