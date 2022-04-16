package com.lennertsoffers.elementalstones.modMenu.commands;

public class LowerHungerCommand extends PlayerCommand {

    public LowerHungerCommand(String command) {
        super(command);
    }

    @Override
    boolean execute() {
        this.getPlayer().setFoodLevel(1);
        return true;
    }
}
