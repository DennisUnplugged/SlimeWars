/*
 * Copyright (c) 2021.
 * This class was coded by DennisUnplugged. Do not distribute or modify. Do not copy and use for any purpose.
 */

package net.unpluggemc.slimewars;

import net.unpluggemc.slimewars.utils.PluginCommand;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public final class SlimeWars extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        registerEvents();
        registerCommands();


    }

    @Override
    public void onDisable() {

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
                Objects.requireNonNull(getCommand(pluginCommand.getCommandInfo().name())).setExecutor(pluginCommand);
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}
