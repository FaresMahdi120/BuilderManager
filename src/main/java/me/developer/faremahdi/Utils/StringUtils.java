package me.developer.faremahdi.Utils;

import lombok.Getter;
import me.developer.faremahdi.BuilderManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
@Getter
public class StringUtils {
    private final BuilderManager plugin;
    public StringUtils(BuilderManager plugin){
        this.plugin = plugin;
    }
    public static String Colorize(String Message){
        return ChatColor.translateAlternateColorCodes('&', Message);
    }
    public void SendMessage(Player player, String message){
        player.sendMessage(Colorize(message));
    }
    public String successPrefix(){
        return this.plugin.getLanguageConfiguration().getString("Admin.Success_Prefix");
    }
    public String failedPrefix(){
        return this.plugin.getLanguageConfiguration().getString("Admin.ERROR_Prefix");
    }
    public String infoPrefix(){
        return this.plugin.getLanguageConfiguration().getString("Admin.Info_Prefix");
    }
    public void loading_Message(){
        System.out.println("Starting up " + this.plugin.getDataFolder().getName() + " version: " + this.plugin.getDescription().getVersion());
        System.out.println("Made by " + this.plugin.getDescription().getAuthors().get(0) + " please report bugs at: FaresMahdi120#5730");
    }
    public void unloading_Message(){
        System.out.println("Shutting down "  + this.plugin.getDataFolder().getName() + " version: " + this.plugin.getDescription().getVersion());
        System.out.println("Made by " + this.plugin.getDescription().getAuthors().get(0) + " please report bugs at: FaresMahdi120#5730");
    }
}
