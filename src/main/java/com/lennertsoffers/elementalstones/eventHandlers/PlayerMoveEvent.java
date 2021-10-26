package com.lennertsoffers.elementalstones.eventHandlers;

import com.lennertsoffers.elementalstones.customClasses.ActivePlayer;
import com.lennertsoffers.elementalstones.items.ItemStones;
import com.lennertsoffers.elementalstones.stones.waterStone.WaterbendingStone;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import java.util.Objects;

public class PlayerMoveEvent implements Listener {

    @EventHandler
    public void onPlayerMove(org.bukkit.event.player.PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());
        if (activePlayer == null) {
            return;
        }
        if (player.getInventory().contains(ItemStones.airStoneAgility0) ||
                player.getInventory().contains(ItemStones.airStoneAgility1) ||
                player.getInventory().contains(ItemStones.airStoneAgility2) ||
                player.getInventory().contains(ItemStones.airStoneAgility3) ||
                player.getInventory().contains(ItemStones.airStoneAgility4)) {
            if (player.isOnGround()) {
                activePlayer.enableDoubleJump();
            }
        }
        Vector movingDirection = Objects.requireNonNull(Objects.requireNonNull(event.getTo()).clone()).add(event.getFrom().clone().multiply(-1)).toVector();
        activePlayer.setMovingDirection(movingDirection.multiply(3));

        WaterbendingStone.passive2(activePlayer);


//        HellfireStone.move4(Objects.requireNonNull(ActivePlayer.getActivePlayer(event.getPlayer().getUniqueId())), event);
//        LavaStone.passive(activePlayer);
    }
}
