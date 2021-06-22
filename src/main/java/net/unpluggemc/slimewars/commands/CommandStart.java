/*
 * Copyright (c) 2021.
 * This class was coded by DennisUnplugged. Do not distribute or modify. Do not copy and use for any purpose.
 */

package net.unpluggemc.slimewars.commands;

import net.unpluggemc.slimewars.Slimewars;
import net.unpluggemc.slimewars.manager.GameManager;
import net.unpluggemc.slimewars.manager.GameState;
import net.unpluggemc.slimewars.utils.CommandInfo;
import net.unpluggemc.slimewars.utils.PluginCommand;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "start", permission = "slimewars.start", requiresPlayer = false)
public class CommandStart extends PluginCommand {

    private Slimewars plugin;
    private GameManager manager;

    public CommandStart(Slimewars plugin, GameManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        manager.changeGameState(GameState.STARTING);

    }

}
