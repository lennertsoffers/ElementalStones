package com.lennertsoffers.elementalstones.consumables.effects;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import com.lennertsoffers.elementalstones.customClasses.models.PalantirSpectatorHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class PalantirEffect implements ConsumableEffect {

    @Override
    public void playEffect(Player player) {
        ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());

        if (activePlayer != null) {
            PalantirSpectatorHandler palantirSpectatorHandler = activePlayer.getPalantirSpectatorHandler();

            // Switch trough players in map
            if (palantirSpectatorHandler.hasSpectatorTargets()) {
                player.setGameMode(GameMode.SPECTATOR);

                palantirSpectatorHandler.requestNewSpectatorTarget();

                palantirSpectatorHandler.setBossBar(Bukkit.createBossBar("Spectating Time Left", BarColor.YELLOW, BarStyle.SEGMENTED_20));
                palantirSpectatorHandler.getBossBar().setProgress(1.0);
                palantirSpectatorHandler.getBossBar().addPlayer(player);

                new BukkitRunnable() {
                    int amountOfTicks = 1200;

                    @Override
                    public void run() {
                        palantirSpectatorHandler.getBossBar().setProgress(amountOfTicks / 1200F);

                        if (palantirSpectatorHandler.hasRequestedNewSpectatorTarget()) {
                            Player spectatorTarget = palantirSpectatorHandler.getNewSpectatorTarget();
                            player.setSpectatorTarget(spectatorTarget);
                            player.sendMessage(ChatColor.YELLOW + "Switched to " + player.getName());
                        }

                        amountOfTicks--;
                        if (amountOfTicks <= 1) {
                            this.cancel();
                            palantirSpectatorHandler.endEffect();
                        }
                    }
                }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            }

            // Create new set of online players
            else {
                palantirSpectatorHandler.setRespawnLocation(player.getLocation());
                palantirSpectatorHandler.setGameMode(player.getGameMode());

                List<Player> onlinePlayers = StaticVariables.plugin.getServer().getOnlinePlayers().stream().filter(onlinePlayer -> {
                    GameMode gameMode = onlinePlayer.getGameMode();
                    return gameMode == GameMode.ADVENTURE || gameMode == GameMode.CREATIVE || gameMode == GameMode.SURVIVAL;
                }).collect(Collectors.toList());

                onlinePlayers.remove(player);

                // Only possible to create this map if there are players online
                if (!onlinePlayers.isEmpty()) {
                    palantirSpectatorHandler.getSpectatorTargets().addAll(onlinePlayers);
                    Collections.shuffle(palantirSpectatorHandler.getSpectatorTargets());
                    this.playEffect(player);
                }

                // Send message if no one is online
                else {
                    player.sendMessage(ChatColor.YELLOW + "There are no players online");
                }
            }
        }
    }
}
