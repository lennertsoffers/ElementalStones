package com.lennertsoffers.elementalstones.modMenu;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ActivePlayer.clearActivePlayers();
        if (label.equalsIgnoreCase("r")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.setWalkSpeed(0.2f);
                player.kickPlayer("Server is reloading");
            }
            Bukkit.reload();
            return true;
        }


        return false;
    }
}
