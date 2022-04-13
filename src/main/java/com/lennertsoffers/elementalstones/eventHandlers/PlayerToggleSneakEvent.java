package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.PalantirSpectatorHandler;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerToggleSneakEvent implements Listener {

    @EventHandler
    public void onPlayerToggleSneak(org.bukkit.event.player.PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.SPECTATOR) {
            ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());

            if (activePlayer != null) {
                PalantirSpectatorHandler palantirSpectatorHandler = activePlayer.getPalantirSpectatorHandler();

                if (palantirSpectatorHandler.hasSpectatorTargets()) {
                    event.setCancelled(true);
                    palantirSpectatorHandler.requestNewSpectatorTarget();
                }
            }
        }
    }
}
