/*
 * Copyright (c) 2021.
 * This class was coded by DennisUnplugged. Do not distribute or modify. Do not copy and use for any purpose.
 */

package net.unpluggemc.slimewars.manager;

import net.unpluggemc.slimewars.Slimewars;
import net.unpluggemc.slimewars.tasks.CountDownTask;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class GameManager {

    private final Slimewars plugin;
    private GameState gameState = GameState.WAITING_FOR_PLAYERS;
    private BlockManager blockManager;
    private PlayerManager playerManager;

    public GameManager(Slimewars plugin) {
        this.plugin = plugin;
        this.blockManager = new BlockManager(this);
        this.playerManager = new PlayerManager(this, plugin);
    }

    public void changeGameState(GameState newState) {
        if (this.gameState == GameState.ACTIVE_GAME && newState == GameState.STARTING) return;
        if (this.gameState == GameState.ACTIVE_GAME && newState == GameState.ACTIVE_GAME) return;

        this.gameState = newState;

        switch (gameState) {
            case WAITING_FOR_PLAYERS:
                Bukkit.broadcastMessage("§8(§7!§8) §7Switched to §2WAITING_FOR_PLAYERS §7game state");
                for (World w : Bukkit.getWorlds()) {
                    w.setPVP(false);
                }
                break;
            case STARTING:
                Bukkit.broadcastMessage("§8(§7!§8) §7Switched to §2STARTING §7game state");
                playerManager.giveKits();
                new CountDownTask(this).runTaskTimer(plugin, 0, 20);
                break;
            case ACTIVE_GAME:
                Bukkit.broadcastMessage("§8(§7!§8) §7Switched to §2ACTIVE §7game state");
                for (World w : Bukkit.getWorlds()) {
                    w.setPVP(true);
                }

                for (Player ps : Bukkit.getOnlinePlayers()) {
                    playerManager.changePlayerState(ps, PlayerState.PLAYING);
                }

                break;
            case END_OF_GAME:
                Bukkit.broadcastMessage("§8(§7!§8) §7Switched to §2END_OF_GAME §7game state");

                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    Bukkit.getOnlinePlayers().forEach((p) -> p.kickPlayer("§4Game Ended!"));
                    changeGameState(GameState.RESET_SERVER);
                }, 20 * 20);

                break;
            case RESET_SERVER:

                Bukkit.getServer().reload();
                break;
        }
    }

    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }
    public GameState getGameState() {return this.gameState;}
    public BlockManager getBlockManager() {return this.blockManager;}
    public void cleanUp() {

    }

}
