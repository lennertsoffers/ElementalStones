package com.lennertsoffers.elementalstones.consumables.effects;

import com.lennertsoffers.elementalstones.customClasses.StaticVariables;
import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class PalantirEffect implements ConsumableEffect {

    @Override
    public void playEffect(Player player) {
        ActivePlayer activePlayer = ActivePlayer.getActivePlayer(player.getUniqueId());

        if (activePlayer != null) {
            // Switch trough players in map
            if (activePlayer.hasSpectatorTargets()) {
                player.setGameMode(GameMode.SPECTATOR);

                activePlayer.requestNewSpectatorTarget();

                BossBar bossBar = Bukkit.createBossBar("Spectating Time Left", BarColor.YELLOW, BarStyle.SEGMENTED_20);
                bossBar.setProgress(1.0);
                bossBar.addPlayer(player);

                new BukkitRunnable() {
                    int amountOfTicks = 1200;

                    @Override
                    public void run() {
                        bossBar.setProgress(amountOfTicks / 1200F);
                        amountOfTicks--;
                        if (amountOfTicks <= 1) {
                            bossBar.removeAll();
                            this.cancel();
                        }

                        if (activePlayer.hasRequestedNewSpectatorTarget()) {
                            player.setSpectatorTarget(activePlayer.getNewSpectatorTarget());
                        }

                        amountOfTicks++;
                        if (amountOfTicks <= 1) {
                            this.cancel();
                            activePlayer.clearSpectatorTargets();
                            player.setGameMode(GameMode.SURVIVAL);
                        }
                    }
                }.runTaskTimer(StaticVariables.plugin, 0L, 1L);
            }

            // Create new set of online players
            else {
                Collection<? extends Player> onlinePlayers = StaticVariables.plugin.getServer().getOnlinePlayers();
                onlinePlayers.remove(player);

                // Only possible to create this map if there are players online
                if (!onlinePlayers.isEmpty()) {
                    activePlayer.getSpectatorTargets().addAll(onlinePlayers);
                    Collections.shuffle(activePlayer.getSpectatorTargets());
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
