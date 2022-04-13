package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.models.mechanics.ActivePlayer;
import com.lennertsoffers.elementalstones.items.ItemStones;
import com.lennertsoffers.elementalstones.moves.airMoves.airbending.AirSlash;
import com.lennertsoffers.elementalstones.passives.PassiveHandler;
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

        // AirStones
        if (
                !Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.airbendingStones) ||
                !Collections.disjoint(Arrays.asList(player.getInventory().getContents()), ItemStones.agilityStones)
        ) {
            if (((Entity) player).isOnGround()) {
                activePlayer.enableDoubleJump();
                if (activePlayer.doCriticalOnGround()) {
                    activePlayer.setCriticalOnGround(false);
                    AirSlash.slashEffect(player, true);
                }
            }
        }

        Vector movingDirection = Objects.requireNonNull(Objects.requireNonNull(event.getTo()).clone()).add(event.getFrom().clone().multiply(-1)).toVector();
        activePlayer.setMovingDirection(movingDirection.multiply(3));

        PassiveHandler.waterWalker(activePlayer);
        PassiveHandler.slippery(player);
        PassiveHandler.lavaWalker(activePlayer);
    }
}
