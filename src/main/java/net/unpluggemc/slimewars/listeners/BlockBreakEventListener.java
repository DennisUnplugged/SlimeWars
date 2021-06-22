/*
 * Copyright (c) 2021.
 * This class was coded by DennisUnplugged. Do not distribute or modify. Do not copy and use for any purpose.
 */

package net.unpluggemc.slimewars.listeners;

import net.unpluggemc.slimewars.Slimewars;
import net.unpluggemc.slimewars.manager.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakEventListener implements Listener {

    private Slimewars plugin;
    private GameManager gameManager;

    public BlockBreakEventListener(Slimewars plugin, GameManager gameManager) {
        this.plugin = plugin;
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getPlayer().isOp()) return;

        if (!gameManager.getBlockManager().canBreak(e.getBlock())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "You cannot break this block!");
            return;
        }

        if (e.getBlock().getType() == Material.SLIME_BLOCK) {
            if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.GOLDEN_SHOVEL) {
                e.getBlock().getDrops().clear();
                e.getPlayer().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.EGG));
            }
        }
    }
}
