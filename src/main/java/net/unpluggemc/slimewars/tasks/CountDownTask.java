/*
 * Copyright (c) 2021.
 * This class was coded by DennisUnplugged. Do not distribute or modify. Do not copy and use for any purpose.
 */

package net.unpluggemc.slimewars.tasks;

import net.unpluggemc.slimewars.manager.GameManager;
import net.unpluggemc.slimewars.manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class CountDownTask extends BukkitRunnable {

    private GameManager gameManager;

    public CountDownTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private int timeLeft = 15;

    @Override
    public void run() {
        Bukkit.broadcastMessage("§e§lGame Starting in §c" + timeLeft + " §e§lseconds!");
        if (timeLeft == 0) {
            cancel();
            gameManager.changeGameState(GameState.ACTIVE_GAME);
            return;
        }

        if (timeLeft <= 10) {
            Bukkit.getOnlinePlayers().stream().forEach((p) -> p.sendTitle("§6" + timeLeft, "", 0, 20, 0));
        }

        timeLeft--;

    }
}
