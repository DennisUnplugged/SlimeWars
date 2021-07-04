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

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ArenaManager {

    private SlimeWars plugin;
    private final Set<Arena> arenas = new HashSet<>();

    public ArenaManager(SlimeWars plugin) {
        //plugin folder, gameMaps
        this.plugin = plugin;

        loadArenas();
    }

    public void registerArena(Arena a) {
        if (a == null) return;
        arenas.add(a);
    }

    public Set<Arena> getArenas() {
        return arenas;
    }

    public Arena getArenaByName(String name) {

        for (Arena a : arenas) {
            if (a.getName().equals(name)) {
                return a;
            }
        }

        return null;
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
            File f  = new File(gameMapsFolder, s);

            Arena a = new Arena(plugin, f.getParentFile(), f.getName());
            arenas.add(a);

        }

    }

}
