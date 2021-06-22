/*
 * Copyright (c) 2021.
 * This class was coded by DennisUnplugged. Do not distribute or modify. Do not copy and use for any purpose.
 */

package net.unpluggemc.slimewars.listeners;

import net.unpluggemc.slimewars.Slimewars;
import net.unpluggemc.slimewars.manager.GameManager;
import net.unpluggemc.slimewars.manager.PlayerState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnEventListener implements Listener {

    private Slimewars plugin;
    private GameManager gameManager;

    public PlayerRespawnEventListener(Slimewars plugin, GameManager gameManager) {
        this.plugin = plugin;
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        gameManager.getPlayerManager().changePlayerState(e.getPlayer(), PlayerState.DEAD);
    }
}
