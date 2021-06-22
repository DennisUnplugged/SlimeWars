/*
 * Copyright (c) 2021.
 * This class was coded by DennisUnplugged. Do not distribute or modify. Do not copy and use for any purpose.
 */

package net.unpluggemc.slimewars.manager;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;

public class BlockManager {

    private GameManager gameManager;
    private Set<Material> allowedToBreak = new HashSet<>();

    public BlockManager(GameManager gameManager) {
        this.gameManager = gameManager;

        //Default List
        allowedToBreak.add(Material.SLIME_BLOCK);
    }

    public boolean canBreak(Block b) {
        return allowedToBreak.contains(b.getType());
    }
    public boolean canBreak(Material m) {
        return allowedToBreak.contains(m);
    }

    public void allowToBreak(Block b) {
        allowedToBreak.add(b.getType());
    }
    public void allowToBreak(Material m) {
        allowedToBreak.add(m);
    }

    public void disallowToBreak(Block b) {
        if (!allowedToBreak.contains(b.getType())) {
            allowedToBreak.add(b.getType());
        }
    }

    public void disallowToBreak(Material m) {
        if (!allowedToBreak.contains(m)) {
            allowedToBreak.add(m);
        }
    }


}
