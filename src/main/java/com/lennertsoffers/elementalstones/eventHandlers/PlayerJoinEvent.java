package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.ElementalStones;
import com.lennertsoffers.elementalstones.customClasses.annotations.Event;
import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.passives.PassiveHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Event
public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ActivePlayer activePlayer = new ActivePlayer(player);
        activePlayer.respawnPlayer();

        PassiveHandler.deepBreath(activePlayer);

        String resourcePackUrl = ElementalStones.configuration.getString("resource_pack");
        if (resourcePackUrl != null) {
            player.setResourcePack(resourcePackUrl);
        }
    }
}
