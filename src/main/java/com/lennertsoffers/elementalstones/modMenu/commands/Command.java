package com.lennertsoffers.elementalstones.modMenu.commands;

import org.bukkit.command.CommandExecutor;

public abstract class Command implements CommandExecutor {
    private final String command;

    public Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return this.command;
    }
}
