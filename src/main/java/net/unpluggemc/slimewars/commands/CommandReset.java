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
import net.unpluggemc.slimewars.utils.Colors;
import net.unpluggemc.slimewars.utils.CommandInfo;
import net.unpluggemc.slimewars.utils.PluginCommand;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "resetMap", permission = "slimewars.reset", requiresPlayer = false)
public class CommandReset extends PluginCommand {

    private final SlimeWars plugin;

    public CommandReset(SlimeWars plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length != 1) {
            return;
        }

        String name = args[0];

        if (name.isEmpty()) {
            return;
        }

        plugin.getArenaManager().getArenaByName(name).resetMap();

        sender.sendMessage(Colors.colorize("&2Map " + name + " was reset!"));
    }
}
