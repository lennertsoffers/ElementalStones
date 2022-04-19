package com.lennertsoffers.elementalstones.modMenu.commands;

import com.lennertsoffers.elementalstones.customClasses.annotations.CommandExecutor;
import com.lennertsoffers.elementalstones.customClasses.models.gameplay.Boss;

@CommandExecutor
public class SpawnBossCommand extends PlayerCommand {

    public SpawnBossCommand() {
        super("spawnBoss");
    }

    @Override
    boolean execute() {
        new Boss(this.getPlayer().getLocation());
        return true;
    }
}
