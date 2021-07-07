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

package net.unpluggemc.slimewars.manager;

import net.unpluggemc.slimewars.SlimeWars;
import net.unpluggemc.slimewars.arena.Arena;
import net.unpluggemc.slimewars.arena.EmptyChunkGenerator;
import net.unpluggemc.slimewars.utils.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ArenaManager {

    private final SlimeWars plugin;
    private final Set<Arena> arenas = new HashSet<>();

    public ArenaManager(SlimeWars plugin) {
        //plugin folder, gameMaps
        this.plugin = plugin;

        loadArenas();
    }


    private boolean createMapSampleWorld(String name) {
        if (Bukkit.getWorld(name) != null) return false;
        WorldCreator creator = new WorldCreator(name);

        creator.generator(new EmptyChunkGenerator());

        World world = creator.createWorld();

        if (world == null) return false;

        world.getBlockAt(0, 0, 0).setType(Material.GLASS);
        world.getBlockAt(0, 64, 0).setType(Material.GLASS);
        world.getBlockAt(0, 128, 0).setType(Material.GLASS);
        world.getBlockAt(0, 255, 0).setType(Material.GLASS);

        return true;
    }

    public void createSampleArena(String name) throws IOException {
        boolean success = createMapSampleWorld(name);
        if (!success) return;

        File worldFolder = Bukkit.getWorld(name).getWorldFolder();
        File dataFolder = plugin.getDataFolder();
        File gameMapsFolder = new File(dataFolder, "gameMaps");
        File newWorldFolder = new File(gameMapsFolder, name);

        if (Bukkit.getWorld(name) != null) Bukkit.unloadWorld(Bukkit.getWorld(name), true);
        else return;

        FileUtils.copy(worldFolder, newWorldFolder);

        FileUtils.deleteDir(worldFolder);

        Arena arena = new Arena(plugin, newWorldFolder.getParentFile(), name);
        registerArena(arena);

    }



    public void unloadArena(String name) {
        Arena a = getArenaByName(name);
        if (a == null) return;

        a.unload();
        arenas.remove(a);
    }

    public void unloadArena(Arena arena) {
        if (arena == null) return;

        arena.unload();
        arenas.remove(arena);
    }

    private void loadArenas() {
        File dataFolder = plugin.getDataFolder();

        File gameMapsFolder = new File(dataFolder, "gameMaps");

        if (!gameMapsFolder.exists()) {
            gameMapsFolder.mkdirs();
        }

        String[] maps = gameMapsFolder.list();

        if (maps == null) return;

        for (String s : maps) {
            File f = new File(gameMapsFolder, s);

            Arena a = new Arena(plugin, f.getParentFile(), f.getName());
            arenas.add(a);

        }

    }

    public void registerArena(Arena a) {
        if (a == null) return;
        arenas.add(a);
    }

    public void loop(Consumer<? super Arena> action) {
        arenas.forEach(action);
    }

    public void loop(Predicate<? super Arena> filter, Consumer<? super Arena> action) {
        arenas.stream().filter(filter).forEach(action);
    }

    public Arena getArenaByName(String name) {

        for (Arena a : arenas) {
            if (a.getName().equals(name)) {
                return a;
            }
        }

        return null;
    }

    public int amountOfArenas() {
        return arenas.size();
    }
}
