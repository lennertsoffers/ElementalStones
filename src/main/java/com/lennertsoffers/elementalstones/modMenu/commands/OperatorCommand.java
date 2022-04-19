package com.lennertsoffers.elementalstones.modMenu.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class OperatorCommand extends com.lennertsoffers.elementalstones.modMenu.commands.Command {

    public OperatorCommand(String command) {
        super(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (this.getCommand().equalsIgnoreCase(label)) {
            if (sender.isOp()) {
                return this.execute();
            }
        }

        return false;
    }

    abstract boolean execute();
}
