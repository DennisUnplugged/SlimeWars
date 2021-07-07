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

package net.unpluggemc.slimewars;

import net.unpluggemc.slimewars.arena.Arena;
import net.unpluggemc.slimewars.manager.GameManager;
import net.unpluggemc.slimewars.utils.PluginCommand;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public final class SlimeWars extends JavaPlugin {

    private GameManager gameManager;

    @Override
    public void onEnable() {
        this.gameManager = new GameManager(this);

        saveDefaultConfig();

        registerEvents();
        registerCommands();
    }

    public GameManager getGameManager() {
        return this.gameManager;
    }

    @Override
    public void onDisable() {
        gameManager.getArenaManager().loop(Arena::unload);
    }


    public void registerEvents() {
        String packageName = getClass().getPackage().getName();

        for (Class<?> clazz : new Reflections(packageName + ".listeners").getSubTypesOf(Listener.class)) {
            try {
                Listener l = (Listener) clazz.
                        getDeclaredConstructor(this.getClass()).newInstance(this);
                Bukkit.getPluginManager().registerEvents(l, this);
            } catch (Exception x) {
                x.printStackTrace();
            }


        }
    }
    public void registerCommands() {
        String packageName = getClass().getPackage().getName();

        for (Class<? extends PluginCommand> clazz : new Reflections(packageName + ".commands").getSubTypesOf(PluginCommand.class)) {
            try {
                PluginCommand pluginCommand = clazz.getDeclaredConstructor(this.getClass()).newInstance(this);
                getCommand(pluginCommand.getCommandInfo().name())
                        .setExecutor(pluginCommand);
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}
