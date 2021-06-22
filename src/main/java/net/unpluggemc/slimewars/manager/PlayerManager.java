/*
 * Copyright (c) 2021.
 * This class was coded by DennisUnplugged. Do not distribute or modify. Do not copy and use for any purpose.
 */

package net.unpluggemc.slimewars.manager;

import net.unpluggemc.slimewars.Slimewars;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {
    private HashMap<UUID, PlayerState> playerStates;

    private GameManager gameManager;
    private Slimewars plugin;

    public PlayerManager(GameManager gameManager, Slimewars plugin) {
        this.gameManager = gameManager;
        this.plugin = plugin;
        this.playerStates = new HashMap<>();

    }

    public void registerPlayer(Player p) {
        changePlayerState(p, PlayerState.LOBBY);
    }

    public PlayerState getPlayerState(Player p) {
        if (playerStates.containsKey(p.getUniqueId())) {
            return playerStates.get(p.getUniqueId());
        }

        return null;
    }

    public void changePlayerState(Player p, PlayerState newState) {
        if (playerStates.containsKey(p.getUniqueId())) {
            playerStates.replace(p.getUniqueId(), newState);
        } else {
            playerStates.put(p.getUniqueId(), newState);
        }

        switch (newState) {
            case LOBBY:
                p.getInventory().clear();
                p.setHealth(p.getMaxHealth());
                p.removePotionEffect(PotionEffectType.WEAKNESS);
                p.setGameMode(GameMode.ADVENTURE);
                p.getInventory().setArmorContents(null);
                p.setSaturation(20);
                p.teleport(p.getWorld().getSpawnLocation());
                break;
            case PLAYING:
                p.setGameMode(GameMode.SURVIVAL);
                break;
            case HELL:
                p.sendTitle("§cYou are now in hell!", "", 20, 40, 20);
                break;
            case DEAD:
                p.setGameMode(GameMode.SPECTATOR);
                p.sendTitle("§cYOU GOT FUCKING DEMOLISHED!", "§7Go back to the §8LOBBY§7 kid", 20, 40, 20);
                p.sendMessage("§8(§7!§8) §7Switched to §2DEAD §7player state");

                if (Bukkit.getOnlinePlayers().stream().filter((ps) -> ps.getGameMode() == GameMode.SURVIVAL).count() == 1) {
                    Bukkit.getOnlinePlayers().stream().filter((ps) -> ps.getGameMode() == GameMode.SURVIVAL).forEach((ps) -> {
                        ps.sendMessage("§5YOU WON THE GAME!");
                        ps.sendTitle("§6§lVICTORY", "§7" + ps.getName() + " won the game!");
                        gameManager.changeGameState(GameState.END_OF_GAME);
                    });
                }


                break;
        }
    }

    public void giveKits() {
        Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.ADVENTURE).forEach(this::giveKit);
    }

    public void giveKit(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);

        p.getInventory().addItem(new ItemStack(Material.DIAMOND_AXE));
        p.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));
        p.getInventory().setArmorContents(new ItemStack[]
                {
                        new ItemStack(Material.IRON_BOOTS),
                        new ItemStack(Material.IRON_LEGGINGS),
                        new ItemStack(Material.IRON_CHESTPLATE),
                        new ItemStack(Material.IRON_HELMET),

                });

    }
}
