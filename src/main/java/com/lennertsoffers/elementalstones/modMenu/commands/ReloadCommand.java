package com.lennertsoffers.elementalstones.modMenu.commands;

import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReloadCommand extends OperatorCommand {

    public ReloadCommand(String command) {
        super(command);
    }

    @Override
    boolean execute() {
        ActivePlayer.clearActivePlayers();

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer("Server is reloading");
        }

        Bukkit.reload();
        return true;    }
}
