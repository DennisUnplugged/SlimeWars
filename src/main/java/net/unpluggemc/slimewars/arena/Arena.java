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

package net.unpluggemc.slimewars.arena;

import net.unpluggemc.slimewars.SlimeWars;
import net.unpluggemc.slimewars.state.ArenaState;
import net.unpluggemc.slimewars.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Arena {

    private final SlimeWars plugin;
    private final LocalGameMap activeMap;
    private final File map;
    private Location spawn;
    private final String name;
    private Set<UUID> players;
    YamlConfiguration config;
    private World activeWorld;

    private ArenaState state = ArenaState.WAITING_FOR_PLAYERS;

    public Arena(SlimeWars plugin, File map, String name) {
        this.name = name;
        this.plugin = plugin;
        this.map = map;


        try {
            loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.activeMap = new LocalGameMap(map, name);

        activeWorld = activeMap.getWorld();
        spawn = new Location(activeWorld, config.getInt("spawn.x"), config.getInt("spawn.y"), config.getInt("spawn.z"));
        players = new HashSet<>();
    }

    public void registerPlayer(Player p) {
        if (!ArenaState.canPlayersJoin(state)) return;
        if (players.contains(p.getUniqueId())) return;
        Objects.requireNonNull(p.getUniqueId(), "Player must have a unique id!");

        players.add(p.getUniqueId());
        p.teleport(activeWorld.getSpawnLocation());
    }

    public void changeArenaState(ArenaState newState) {
        if (newState == this.state) return;

        this.state = newState;

        switch (state) {
            case WAITING_FOR_PLAYERS:
                activeWorld.setPVP(false);

                break;
            case STARTING:

                break;
            case ACTIVE:

                break;
            case END_OF_GAME:

                break;
            case MAP_RESET:
                resetMap();
                break;
        }
    }

    private void loadConfig() throws IOException {

        File dataFolder = plugin.getDataFolder();
        File gameMapsFolder = new File(dataFolder, "gameMaps");
        File worldFolder = new File(gameMapsFolder, name);
        File configFile = new File(worldFolder, "config.yml");

        if (!configFile.exists()) {
            plugin.getLogger().info("Configuration file for arena " + name + " does not exist! Creating one...");

            boolean success = configFile.createNewFile();

            plugin.getLogger().info(success ? "Configuration file for arena " + name + " created!"
                    : "Couldn't create configuration file for arena " + name + "!");

            config = YamlConfiguration.loadConfiguration(configFile);

            config.set("spawn.x", 0);
            config.set("spawn.y", 0);
            config.set("spawn.z", 0);
            config.set("name", name);

            config.save(configFile);

            plugin.getLogger().info("Config file for arena " + name + " set up and configured with default settings!");

            return;
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        plugin.getLogger().info("Config file for arena " + name + " loaded!");
    }


    public void resetMap() {
        //TODO: Delete the file instance of the active map -- Reset the code instance of the map (by loading a new one)
        //DELETE PROCESS
        activeMap.restoreFromSource();
        //NEW WORLD FORMATION
        this.activeWorld = activeMap.getWorld();
        spawn = new Location(activeWorld, config.getInt("spawn.x"), config.getInt("spawn.y"), config.getInt("spawn.z"));
        players = new HashSet<>();
    }

    public void unload() {
        activeMap.unload();
    }

    //Getters
    public Location getSpawnLocation() {
        return this.spawn;
    }
    public String getName() {
        return this.name;
    }
    public World getActiveWorld() {
        return this.activeWorld;
    }

    //Additional methods
    public void safeRemovePlayer(Player p) {
        if (!players.contains(p.getUniqueId())) return;
        players.remove(p.getUniqueId());
    }

    public boolean isRegistered(Player p) {
        return players.contains(p.getUniqueId());
    }

    public UUID[] getPlayers() {
        return (UUID[]) players.toArray();
    }
}
