package me.developer.faremahdi;

import me.developer.faremahdi.Commands.MainCommand;
import me.developer.faremahdi.Configuration.ConfigManager;
import me.developer.faremahdi.Listener.OnInventoryClick;
import me.developer.faremahdi.Utils.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public final class BuilderManager extends JavaPlugin {
    StringUtils messagesManager = new StringUtils(this);
    public ConfigManager configManager = new ConfigManager(this);
    MainCommand cmd = new MainCommand(this);

    @Override
    public void onEnable() {
        messagesManager.loading_Message();
        getCommand("ItemBuilder").setTabCompleter(cmd);
        getCommand("ItemBuilder").setExecutor(cmd);
        OnInventoryClick onInventoryClick = new OnInventoryClick(this);
        this.getServer().getPluginManager().registerEvents(onInventoryClick, this);
    }

    @Override
    public void onDisable() {
        messagesManager.unloading_Message();
    }

    /*public FileConfiguration getConfig() {
        return this.configManager.getConfig();
    }*/
    public FileConfiguration getItemsConfig(){
        return this.configManager.getItemConfig();
    }

    public FileConfiguration getGuiConfig(){
        return this.configManager.getGuiConfig();
    }

    public FileConfiguration getLanguageConfiguration(){
        return this.configManager.getLanguageConfig();
    }
}
