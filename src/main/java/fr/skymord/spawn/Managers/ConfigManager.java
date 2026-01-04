package fr.skymord.spawn.Managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class ConfigManager {

    private final JavaPlugin plugin;
    private FileConfiguration config;
    private File configFile;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "config.yml");
    }

    public void loadConfig() {
        if (!configFile.exists()) {
            plugin.saveDefaultConfig();
        }

        config = YamlConfiguration.loadConfiguration(configFile);

        InputStream defaultStream = plugin.getResource("config.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(defaultStream)
            );
            config.setDefaults(defaultConfig);
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Unable to save config.yml", e);
        }
    }

    public FileConfiguration getConfig() {
        if (config == null) {
            loadConfig();
        }
        return config;
    }

    public String getString(String path, String defaultValue) {
        return getConfig().getString(path, defaultValue);
    }

    public int getInt(String path, int defaultValue) {
        return getConfig().getInt(path, defaultValue);
    }

    public boolean getBoolean(String path, boolean defaultValue) {
        return getConfig().getBoolean(path, defaultValue);
    }

    public double getDouble(String path, double defaultValue) {
        return getConfig().getDouble(path, defaultValue);
    }

    public void set(String path, Object value) {
        getConfig().set(path, value);
        saveConfig();
    }

    public boolean contains(String path) {
        return getConfig().contains(path);
    }

    public void createBackup() {
        File backupFile = new File(plugin.getDataFolder(), "config.yml.backup");
        try {
            if (configFile.exists()) {
                java.nio.file.Files.copy(
                        configFile.toPath(),
                        backupFile.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                );
                plugin.getLogger().info("Configuration backup created successfully");
            }
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, "Unable to create a backup of config.yml", e);
        }
    }
}