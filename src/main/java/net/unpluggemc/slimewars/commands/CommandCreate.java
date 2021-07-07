/*
 * Copyright (c) 2021 DennisUnplugged
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.unpluggemc.slimewars.commands;

import net.unpluggemc.slimewars.SlimeWars;
import net.unpluggemc.slimewars.utils.CommandInfo;
import net.unpluggemc.slimewars.utils.PluginCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

@CommandInfo(name = "createArena", permission = "slimewars.createArena", requiresPlayer = false)
public class CommandCreate extends PluginCommand {

    private final SlimeWars plugin;

    public CommandCreate(SlimeWars plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        try {
            plugin.getGameManager().getArenaManager().createSampleArena(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
