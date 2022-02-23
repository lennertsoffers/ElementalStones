package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.passives.PassiveHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ActivePlayer activePlayer = new ActivePlayer(player);
        PassiveHandler.deepBreath(activePlayer);

        player.setResourcePack("https://www.dropbox.com/s/tjxjp8sgrzjytmc/ElementalStones.zip?dl=1");
    }
}
