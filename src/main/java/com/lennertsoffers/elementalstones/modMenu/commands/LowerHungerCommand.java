package com.lennertsoffers.elementalstones.modMenu.commands;

import com.lennertsoffers.elementalstones.customClasses.annotations.CommandExecutor;

@CommandExecutor
public class LowerHungerCommand extends PlayerCommand {

    public LowerHungerCommand() {
        super("lowerHunger");
    }

    @Override
    boolean execute() {
        this.getPlayer().setFoodLevel(1);
        return true;
    }
}
