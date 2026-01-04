package fr.skymord.spawn.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import fr.skymord.spawn.Managers.ConfigManager;
import fr.skymord.spawn.Managers.SpawnManager;

public class PlayerJoinListener implements Listener {

    private final ConfigManager configManager;
    private final SpawnManager spawnManager;

    public PlayerJoinListener(ConfigManager configManager, SpawnManager spawnManager) {
        this.configManager = configManager;
        this.spawnManager = spawnManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore() && configManager.getBoolean("teleportation.teleport-on-first-join", true)) {
            teleportPlayer(player);
            return;
        }
        if (configManager.getBoolean("teleportation.teleport-on-join", false)) {
            teleportPlayer(player);
        }
    }

    private void teleportPlayer(Player player) {
        if (!spawnManager.isSpawnConfigured()) {
            return;
        }

        spawnManager.teleportToSpawn(player, new SpawnManager.TeleportCallback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFail(SpawnManager.TeleportFailReason reason) {
            }

            @Override
            public void onFail(SpawnManager.TeleportFailReason reason, long timeLeft) {
            }

            @Override
            public void onCountdown(int secondsLeft) {
            }
        });
    }
}