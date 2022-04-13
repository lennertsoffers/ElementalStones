package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerRespawnEvent implements Listener {

    @EventHandler
    public void onPlayerRespawn(org.bukkit.event.player.PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());
        if (activePlayer != null) {
            activePlayer.respawnPlayer();
        }

    }
}
