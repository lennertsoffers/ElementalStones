package com.lennertsoffers.elementalstones.modMenu.commands;

import com.lennertsoffers.elementalstones.customClasses.annotations.CommandExecutor;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandExecutor
public class ReloadCommand extends OperatorCommand {

    public ReloadCommand() {
        super("r");
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
