/*
 * Copyright (c) 2021.
 * This class was coded by DennisUnplugged. Do not distribute or modify. Do not copy and use for any purpose.
 */

package net.unpluggemc.slimewars.listeners;

import net.unpluggemc.slimewars.Slimewars;
import net.unpluggemc.slimewars.manager.GameManager;
import net.unpluggemc.slimewars.manager.GameState;
import net.unpluggemc.slimewars.manager.PlayerState;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathEventListener implements Listener {

    private Slimewars plugin;
    private GameManager gameManager;

    public PlayerDeathEventListener(Slimewars plugin, GameManager gameManager) {
        this.plugin = plugin;
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.getDrops().clear();
        Bukkit.getScheduler().runTaskLater(plugin, () -> e.getEntity().spigot().respawn(), 2    );

    }
}
