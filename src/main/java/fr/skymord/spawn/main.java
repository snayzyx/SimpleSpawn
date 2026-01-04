package fr.skymord.spawn;

import fr.skymord.spawn.Listeners.PlayerJoinListener;
import fr.skymord.spawn.Managers.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;
import fr.skymord.spawn.Managers.SpawnManager;
import fr.skymord.spawn.Commands.SpawnCommand;

public final class main extends JavaPlugin {
    private ConfigManager configManager;
    private SpawnManager spawnManager;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        configManager.loadConfig();

        spawnManager = new SpawnManager(this, configManager);

        this.getCommand("spawn").setExecutor(new SpawnCommand(this, configManager, spawnManager));

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(configManager, spawnManager), this);

        getLogger().info("Spawn Plugin enabled successfully!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Spawn Plugin disabled!");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public SpawnManager getSpawnManager() {
        return spawnManager;
    }
}
