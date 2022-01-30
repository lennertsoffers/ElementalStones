package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.items.ItemStones;
import com.lennertsoffers.elementalstones.stones.earthStone.LavaStone;
import com.lennertsoffers.elementalstones.stones.waterStone.WaterbendingStone;
import com.lennertsoffers.elementalstones.stones.windStone.AirbendingStone;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class PlayerMoveEvent implements Listener {

    @EventHandler
    public void onPlayerMove(org.bukkit.event.player.PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());
        if (activePlayer == null) {
            return;
        }
        if (
                !Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.airbendingStones) ||
                !Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.agilityStones)
        ) {
            if (((Entity) player).isOnGround()) {
                activePlayer.enableDoubleJump();
                if (activePlayer.doCriticalOnGround()) {
                    activePlayer.setCriticalOnGround(false);
                    AirbendingStone.move4Slash(player, true);
                }
            }
        }

        Vector movingDirection = Objects.requireNonNull(Objects.requireNonNull(event.getTo()).clone()).add(event.getFrom().clone().multiply(-1)).toVector();
        activePlayer.setMovingDirection(movingDirection.multiply(3));

        WaterbendingStone.passive2(activePlayer);


//        HellfireStone.move4(Objects.requireNonNull(ActivePlayer.getActivePlayer(event.getPlayer().getUniqueId())), event);
        LavaStone.passive1(activePlayer);
    }
}
