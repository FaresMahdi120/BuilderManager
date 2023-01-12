package me.developer.faremahdi.Configuration;

import lombok.Getter;

import me.developer.faremahdi.BuilderManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {
    private final BuilderManager plugin;

    @Getter
    public FileConfiguration config;

    @Getter
    public FileConfiguration languageConfig;


    @Getter
    public FileConfiguration itemConfig;
    @Getter
    public FileConfiguration guiConfig;
    //private final File configFile;
    private final File languageFile;
    private final File itemFile;
    private final File guiFile;

    public ConfigManager(BuilderManager plugin) {
        this.plugin = plugin;
        //this.configFile = new File(plugin.getDataFolder(), "config.yml");
        this.languageFile = new File(plugin.getDataFolder(), "language.yml");
        this.itemFile = new File(plugin.getDataFolder(), "itemconfig.yml");
        this.guiFile = new File(plugin.getDataFolder(), "Gui.yml");

        loadConfigurations();
    }

    public void loadConfigurations() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        /*if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }*/

        if (!languageFile.exists()) {
            plugin.saveResource("language.yml", false);
        }

        if (!itemFile.exists()) {
            plugin.saveResource("itemconfig.yml", false);
        }
        if (!guiFile.exists()) {
            plugin.saveResource("Gui.yml", false);
        }
        /*plugin.getLogger().info("Loading config.yml...");
        config = YamlConfiguration.loadConfiguration(configFile);*/

        plugin.getLogger().info("Loading language.yml...");
        languageConfig = YamlConfiguration.loadConfiguration(languageFile);

        plugin.getLogger().info("Loading itemsconfig.yml");
        itemConfig = YamlConfiguration.loadConfiguration(itemFile);

        plugin.getLogger().info("Loading Gui.yml");
        guiConfig = YamlConfiguration.loadConfiguration(guiFile);
    }

    /*public void saveConfig() {
        try {
            getConfig().save(configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "An error occurred whilst saving the configuration file", e);
        }
    }*/


    /*public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }*/

    public void reloadLanguageConfig() {
        languageConfig = YamlConfiguration.loadConfiguration(languageFile);
    }

    public void reloadItemsConfig() {
        itemConfig = YamlConfiguration.loadConfiguration(itemFile);
    }
    public void reloadGuiConfig() {
        guiConfig = YamlConfiguration.loadConfiguration(guiFile);
    }
}
