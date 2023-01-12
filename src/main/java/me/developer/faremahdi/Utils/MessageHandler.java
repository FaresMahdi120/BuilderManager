package me.developer.faremahdi.Utils;

import lombok.Getter;
import me.developer.faremahdi.BuilderManager;
import org.bukkit.entity.Player;

import java.util.List;
@Getter
public class MessageHandler {
    StringUtils stringUtils;
    Player player;
    String prefix;
    BuilderManager plugin;
    public MessageHandler(BuilderManager plugin, Player player) {
        this.plugin = plugin;
        this.stringUtils = new StringUtils(plugin);
        this.player = player;
        this.prefix = getPlugin().getLanguageConfiguration().getString("General.Prefix");
    }

    public void sendHelpMessage(){
        List<String> help = getPlugin().getLanguageConfiguration().getStringList("General.Help");
        for (String line : help){
            stringUtils.SendMessage(player, line);
        }
    }
    public void sendGivenItem(String key, String stats, Player otherPlayer, String itemName){
        String rawMessage;
        switch (key){
            case "given":
                switch (stats){
                    case "notfound":
                        rawMessage = getPlugin().getLanguageConfiguration().getString("Admin.Player_Not_Found");
                        assert rawMessage != null;
                        String notFoundMessage = rawMessage.replaceAll("<Prefix>", this.prefix).replaceAll("<StatePrefix>", stringUtils.failedPrefix());
                        stringUtils.SendMessage(player, notFoundMessage);
                        break;
                    case "found":
                        rawMessage = getPlugin().getLanguageConfiguration().getString("Admin.Given_an_item");
                        assert rawMessage != null;
                        String foundMessage = rawMessage.replaceAll("<Prefix>", this.prefix).replaceAll("<ItemGiven>", itemName).replaceAll("<StatePrefix>", stringUtils.successPrefix()).replaceAll("<PlayerGiven>", otherPlayer.getName());
                        stringUtils.SendMessage(player, foundMessage);
                        break;
                }
                break;
            case "obtained":
                rawMessage = getPlugin().getLanguageConfiguration().getString("General.Got_an_item");
                assert rawMessage != null;
                String Message = rawMessage.replaceAll("<Prefix>", this.prefix).replaceAll("<item>", itemName).replaceAll("<StatePrefix>", stringUtils.successPrefix());
                stringUtils.SendMessage(otherPlayer, Message);
                break;
        }
    }
    public void sendOpenMenu(String key, String stats, Player otherPlayer, String menuName){
        String rawMessage;
        switch (key){
            case "openedFor":
                switch (stats){
                    case "notfound":
                        rawMessage = getPlugin().getLanguageConfiguration().getString("Admin.Player_Not_Found");
                        assert rawMessage != null;
                        String notFoundMessage = rawMessage.replaceAll("<Prefix>", prefix).replaceAll("<StatePrefix>", stringUtils.failedPrefix());
                        stringUtils.SendMessage(player, notFoundMessage);
                        break;
                    case "found":
                        rawMessage = getPlugin().getLanguageConfiguration().getString("Admin.Open_Menu_To_Player");
                        assert rawMessage != null;
                        String foundMessage = rawMessage.replaceAll("<Prefix>", prefix).replaceAll("<Open_a_menu>", menuName).replaceAll("<StatePrefix>", stringUtils.successPrefix()).replaceAll("<PlayerOpenedMenu>", otherPlayer.getName());
                        stringUtils.SendMessage(player, foundMessage);
                        break;
                }
                break;
            case "open":
                rawMessage = getPlugin().getLanguageConfiguration().getString("General.Open_a_menu");
                assert rawMessage != null;
                String Message = rawMessage.replaceAll("<Prefix>", prefix).replaceAll("<MenuName>", menuName).replaceAll("<StatePrefix>", stringUtils.successPrefix());
                stringUtils.SendMessage(otherPlayer, Message);
                break;
        }
    }
    public void sendNotFound(String key){
        String rawMessage;
        switch (key){
            case "item":
                rawMessage = getPlugin().getLanguageConfiguration().getString("General.Item_Does_Not_Exist");
                assert rawMessage != null;
                String itemMessage = rawMessage.replaceAll("<Prefix>", prefix).replaceAll("<StatePrefix>", stringUtils.failedPrefix());
                stringUtils.SendMessage(player, itemMessage);
                break;
            case "menu":
                rawMessage = getPlugin().getLanguageConfiguration().getString("General.Menu_Does_Not_Exist");
                assert rawMessage != null;
                String Message = rawMessage.replaceAll("<Prefix>", prefix).replaceAll("<StatePrefix>", stringUtils.failedPrefix());
                stringUtils.SendMessage(player, Message);
                break;
        }
    }
    public void NoPermission(String permission){
        String rawMessage = getPlugin().getLanguageConfiguration().getString("General.no_permission");
        assert rawMessage != null;
        String itemMessage = rawMessage.replaceAll("<Prefix>", prefix).replaceAll("<StatePrefix>", stringUtils.failedPrefix()).replaceAll("<permission>", permission);
        stringUtils.SendMessage(player, itemMessage);
    }
    public void sendReload(){
        String rawMessage = getPlugin().getLanguageConfiguration().getString("Admin.Reload_Config");
        assert rawMessage != null;
        String Message = rawMessage.replaceAll("<Prefix>", prefix).replaceAll("<StatePrefix>", stringUtils.successPrefix());
        stringUtils.SendMessage(player, Message);
    }


}
