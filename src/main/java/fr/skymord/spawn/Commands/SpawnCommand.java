package fr.skymord.spawn.Commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import fr.skymord.spawn.Managers.ConfigManager;
import fr.skymord.spawn.Managers.SpawnManager;

import java.util.ArrayList;
import java.util.List;

public class SpawnCommand implements CommandExecutor, TabCompleter {

    private final JavaPlugin plugin;
    private final ConfigManager configManager;
    private final SpawnManager spawnManager;

    public SpawnCommand(JavaPlugin plugin, ConfigManager configManager, SpawnManager spawnManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.spawnManager = spawnManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(colorize(configManager.getString("messages.must-be-player", "&cThis command must be executed by a player!")));
                return true;
            }

            Player player = (Player) sender;

            if (!player.hasPermission("spawn.use")) {
                player.sendMessage(colorize(configManager.getString("messages.no-permission", "&cYou don't have permission to use this command!")));
                return true;
            }

            teleportToSpawn(player);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "set":
                return handleSet(sender);

            case "reload":
                return handleReload(sender);

            default:
                sender.sendMessage(colorize(configManager.getString("messages.invalid-usage", "&cUsage: /spawn [set|reload]")));
                return true;
        }
    }

    private void teleportToSpawn(Player player) {
        spawnManager.teleportToSpawn(player, new SpawnManager.TeleportCallback() {
            @Override
            public void onSuccess() {
                player.sendMessage(colorize(configManager.getString("messages.teleport-success", "&aTeleported to spawn successfully!")));
            }

            @Override
            public void onFail(SpawnManager.TeleportFailReason reason) {
                switch (reason) {
                    case SPAWN_NOT_CONFIGURED:
                        player.sendMessage(colorize(configManager.getString("messages.spawn-not-configured", "&cSpawn is not configured! Contact an administrator.")));
                        break;
                    case WORLD_NOT_FOUND:
                        player.sendMessage(colorize(configManager.getString("messages.world-not-found", "&cSpawn world not found!")));
                        break;
                    case MOVED:
                        player.sendMessage(colorize(configManager.getString("messages.teleport-cancelled", "&cTeleportation cancelled because you moved!")));
                        break;
                }
            }

            @Override
            public void onFail(SpawnManager.TeleportFailReason reason, long timeLeft) {
                if (reason == SpawnManager.TeleportFailReason.COOLDOWN_ACTIVE) {
                    String message = configManager.getString("messages.cooldown-active", "&cYou must wait &6{time} &cseconds before teleporting again!")
                            .replace("{time}", String.valueOf(timeLeft));
                    player.sendMessage(colorize(message));
                }
            }

            @Override
            public void onCountdown(int secondsLeft) {
                String message = configManager.getString("messages.teleport-countdown", "&eYou will be teleported in &6{time} &eseconds...")
                        .replace("{time}", String.valueOf(secondsLeft));
                player.sendMessage(colorize(message));
            }
        });
    }

    private boolean handleSet(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(colorize(configManager.getString("messages.must-be-player", "&cThis command must be executed by a player!")));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("spawn.set")) {
            player.sendMessage(colorize(configManager.getString("messages.no-permission", "&cYou don't have permission to use this command!")));
            return true;
        }

        Location location = player.getLocation();
        if (configManager.getBoolean("settings.round-coordinates", true)) {
            if (configManager.getBoolean("settings.round-to-block-center", true)) {
                location = new Location(
                        location.getWorld(),
                        Math.floor(location.getX()) + 0.5,
                        Math.floor(location.getY()),
                        Math.floor(location.getZ()) + 0.5,
                        location.getYaw(),
                        location.getPitch()
                );
            } else {
                location = new Location(
                        location.getWorld(),
                        Math.round(location.getX()),
                        Math.round(location.getY()),
                        Math.round(location.getZ()),
                        location.getYaw(),
                        location.getPitch()
                );
            }
        }

        spawnManager.setSpawnLocation(location);

        player.sendMessage(colorize(configManager.getString("messages.spawn-set", "&aSpawn set at your current location!")));

        if (configManager.getBoolean("settings.show-coordinates", true)) {
            player.sendMessage(ChatColor.GRAY + "World: " + location.getWorld().getName());
            player.sendMessage(ChatColor.GRAY + "Position: " +
                    (int) location.getX() + ", " +
                    (int) location.getY() + ", " +
                    (int) location.getZ());
        }

        plugin.getLogger().info(player.getName() + " set the spawn");
        return true;
    }

    private boolean handleReload(CommandSender sender) {
        if (!sender.hasPermission("spawn.reload")) {
            sender.sendMessage(colorize(configManager.getString("messages.no-permission", "&cYou don't have permission to use this command!")));
            return true;
        }

        try {
            if (configManager.getBoolean("settings.auto-backup", true)) {
                configManager.createBackup();
            }

            configManager.reloadConfig();

            sender.sendMessage(colorize(configManager.getString("messages.config-reloaded", "&aConfiguration reloaded successfully!")));

            if (configManager.getBoolean("settings.auto-backup", true)) {
                sender.sendMessage(colorize(configManager.getString("messages.backup-created", "&7A backup was automatically created.")));
            }

            plugin.getLogger().info(sender.getName() + " reloaded the configuration");

        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "✗ Error during reload!");
            plugin.getLogger().severe("Error during reload: " + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }

    private String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            if (sender.hasPermission("spawn.set")) {
                completions.add("set");
            }

            if (sender.hasPermission("spawn.reload")) {
                completions.add("reload");
            }

            String input = args[0].toLowerCase();
            completions.removeIf(s -> !s.startsWith(input));
        }

        return completions;
    }
}