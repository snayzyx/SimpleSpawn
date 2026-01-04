package fr.skymord.spawn.Managers;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import fr.skymord.spawn.Managers.ConfigManager;

import java.util.HashMap;
import java.util.UUID;

public class SpawnManager {

    private final JavaPlugin plugin;
    private final ConfigManager configManager;
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final HashMap<UUID, Location> pendingTeleports = new HashMap<>();

    public SpawnManager(JavaPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    public Location getSpawnLocation() {
        String worldName = configManager.getString("spawn.world", null);
        if (worldName == null) {
            return null;
        }

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            return null;
        }

        return new Location(
                world,
                configManager.getDouble("spawn.x", 0.5),
                configManager.getDouble("spawn.y", 64.0),
                configManager.getDouble("spawn.z", 0.5),
                (float) configManager.getDouble("spawn.yaw", 0.0),
                (float) configManager.getDouble("spawn.pitch", 0.0)
        );
    }

    public void setSpawnLocation(Location location) {
        configManager.set("spawn.world", location.getWorld().getName());
        configManager.set("spawn.x", location.getX());
        configManager.set("spawn.y", location.getY());
        configManager.set("spawn.z", location.getZ());
        configManager.set("spawn.yaw", location.getYaw());
        configManager.set("spawn.pitch", location.getPitch());
    }

    public boolean isSpawnConfigured() {
        return configManager.getString("spawn.world", null) != null;
    }

    public void teleportToSpawn(Player player, TeleportCallback callback) {
        Location spawnLoc = getSpawnLocation();
        if (spawnLoc == null) {
            callback.onFail(TeleportFailReason.SPAWN_NOT_CONFIGURED);
            return;
        }

        if (spawnLoc.getWorld() == null) {
            callback.onFail(TeleportFailReason.WORLD_NOT_FOUND);
            return;
        }

        if (hasCooldown(player)) {
            long timeLeft = getCooldownTimeLeft(player);
            callback.onFail(TeleportFailReason.COOLDOWN_ACTIVE, timeLeft);
            return;
        }

        int delay = configManager.getInt("teleportation.delay", 3);

        if (delay <= 0) {
            player.teleport(spawnLoc);
            playSound(player);
            setCooldown(player);
            callback.onSuccess();
            return;
        }

        pendingTeleports.put(player.getUniqueId(), player.getLocation());
        playCountdownSound(player);

        new BukkitRunnable() {
            int countdown = delay;

            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    pendingTeleports.remove(player.getUniqueId());
                    return;
                }

                if (configManager.getBoolean("teleportation.cancel-on-move", true)) {
                    Location originalLoc = pendingTeleports.get(player.getUniqueId());
                    if (originalLoc != null && player.getLocation().distance(originalLoc) > 0.5) {
                        callback.onFail(TeleportFailReason.MOVED);
                        cancel();
                        pendingTeleports.remove(player.getUniqueId());
                        return;
                    }
                }

                if (countdown <= 0) {
                    player.teleport(spawnLoc);
                    playSound(player);
                    setCooldown(player);
                    pendingTeleports.remove(player.getUniqueId());
                    callback.onSuccess();
                    cancel();
                } else {
                    callback.onCountdown(countdown);
                    playCountdownSound(player);
                    countdown--;
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public boolean hasCooldown(Player player) {
        if (!cooldowns.containsKey(player.getUniqueId())) {
            return false;
        }

        long timeLeft = cooldowns.get(player.getUniqueId()) - System.currentTimeMillis();
        if (timeLeft <= 0) {
            cooldowns.remove(player.getUniqueId());
            return false;
        }

        return true;
    }

    public long getCooldownTimeLeft(Player player) {
        if (!cooldowns.containsKey(player.getUniqueId())) {
            return 0;
        }
        return (cooldowns.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000;
    }

    private void setCooldown(Player player) {
        int cooldownSeconds = configManager.getInt("teleportation.cooldown", 10);
        if (cooldownSeconds > 0) {
            cooldowns.put(player.getUniqueId(), System.currentTimeMillis() + (cooldownSeconds * 1000L));
        }
    }

    private void playSound(Player player) {
        if (configManager.getBoolean("teleportation.play-sound", true)) {
            String soundName = configManager.getString("teleportation.sound-type", "ENTITY_ENDERMAN_TELEPORT");
            try {
                Sound sound = Sound.valueOf(soundName);
                player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
            } catch (Exception e) {
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                plugin.getLogger().warning("Invalid sound in config, using default sound");
            }
        }
    }

    private void playCountdownSound(Player player) {
        if (configManager.getBoolean("teleportation.play-countdown-sound", true)) {
            String soundName = configManager.getString("teleportation.countdown-sound-type", "BLOCK_NOTE_BLOCK_PLING");
            try {
                Sound sound = Sound.valueOf(soundName);
                player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
            } catch (Exception e) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                plugin.getLogger().warning("Invalid countdown sound in config, using default sound");
            }
        }
    }

    public interface TeleportCallback {
        void onSuccess();
        void onFail(TeleportFailReason reason);
        void onFail(TeleportFailReason reason, long timeLeft);
        void onCountdown(int secondsLeft);
    }

    public enum TeleportFailReason {
        SPAWN_NOT_CONFIGURED,
        WORLD_NOT_FOUND,
        COOLDOWN_ACTIVE,
        MOVED
    }
}