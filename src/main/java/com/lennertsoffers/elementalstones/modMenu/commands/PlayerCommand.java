package com.lennertsoffers.elementalstones.modMenu.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCommand extends com.lennertsoffers.elementalstones.modMenu.commands.Command implements CommandExecutor {
    private Player player;
    public String[] args;

    public PlayerCommand(String command) {
        super(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (this.getCommand().equalsIgnoreCase(label)) {
            if (sender instanceof Player) {
                if (sender.isOp()) {
                    this.player = (Player) sender;
                    this.args = args;

                    return this.execute();
                }
            }
        }

        return false;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String[] getArgs() {
        return this.args;
    }

    abstract boolean execute();
}
