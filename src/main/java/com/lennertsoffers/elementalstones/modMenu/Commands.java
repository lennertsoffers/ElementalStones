package com.lennertsoffers.elementalstones.modMenu;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("r")) {
            Bukkit.reload();
            return true;
        }
        return false;
    }
}
