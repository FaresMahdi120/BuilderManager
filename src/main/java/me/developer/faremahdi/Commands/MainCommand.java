package me.developer.faremahdi.Commands;

import me.developer.faremahdi.GuiHandler.GuiBuilder;
import me.developer.faremahdi.ItemHandler.Items.Builder;
import me.developer.faremahdi.Utils.MessageHandler;
import me.developer.faremahdi.BuilderManager;
import me.developer.faremahdi.Utils.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MainCommand implements TabExecutor {
    private final BuilderManager plugin;
    MessageHandler msg;
    public MainCommand(BuilderManager plugin){
        this.plugin = plugin;

    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            msg = new MessageHandler(plugin, player);
            if (!player.hasPermission(PermissionManager.itembuilder_main_command.name())){
                msg.NoPermission(PermissionManager.itembuilder_main_command.name());
                return false;
            }
            if (command.getAliases().contains(command.getName())) {
                if (args.length == 1) {
                    switch (args[0]) {
                        case "giveItem":
                        case "openMenu":
                            msg.sendHelpMessage();
                            break;
                    }
                } else if (args.length > 1) {
                    switch (args[0]) {
                        case "giveItem":
                            if (!player.hasPermission(PermissionManager.itembuilder_give_item.name())) {
                                msg.NoPermission(PermissionManager.itembuilder_give_item.name());
                                return false;
                            }
                            String name = plugin.getItemsConfig().getString(args[1] + ".Name");
                            int amount = plugin.getItemsConfig().getInt(args[1] + ".Amount");
                            String material = plugin.getItemsConfig().getString(args[1] + ".Material");
                            boolean glow = plugin.getItemsConfig().getBoolean(args[1] + ".glow");
                            List<String> rawLore = plugin.getItemsConfig().getStringList(args[1] + ".lore");
                            Builder item = new Builder(Material.valueOf(material), name, amount, glow, rawLore);
                            if (args.length == 3) {
                                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[2]))) {
                                    Player newPlayer = Bukkit.getPlayer(args[2]);
                                    if (!player.hasPermission(PermissionManager.itembuilder_give_item_others.name())) {
                                        msg.NoPermission(PermissionManager.itembuilder_give_item_others.name());
                                        return false;
                                    }
                                    if (!plugin.getItemsConfig().contains(args[1])) {
                                        msg.sendNotFound("item");
                                        return false;
                                    }
                                    assert newPlayer != null;
                                    newPlayer.getInventory().addItem(item.createItem());
                                    msg.sendGivenItem("given", "found", player, name);
                                    msg.sendGivenItem("obtained", "found", newPlayer, name);
                                } else {
                                    msg.sendOpenMenu("given", "notfound", player, name);
                                }
                            } else if (args.length == 2) {
                                if (plugin.getItemsConfig().contains(args[1])) {
                                    player.getInventory().addItem(item.createItem());
                                    msg.sendGivenItem("obtained", "found", player, name);
                                } else {
                                    msg.sendNotFound("item");
                                }
                            }
                            break;
                        case "openMenu":
                            if (!player.hasPermission(PermissionManager.itembuilder_open_menu.name())) {
                                msg.NoPermission(PermissionManager.itembuilder_open_menu.name());
                                return false;
                            }
                            if (plugin.configManager.getGuiConfig().contains(args[1])) {
                                GuiBuilder inventory = new GuiBuilder(
                                        plugin.configManager.getGuiConfig().getString(args[1] + ".Title"),
                                        plugin.configManager.getGuiConfig().getInt(args[1] + ".Size"),
                                        player
                                );
                                if (args.length == 3) {
                                    if (!player.hasPermission(PermissionManager.itembuilder_open_menu_othes.name())) {
                                        msg.NoPermission(PermissionManager.itembuilder_open_menu_othes.name());
                                        return false;
                                    }
                                    if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[2]))) {
                                        msg.sendOpenMenu("openedFor", "notfound", player, plugin.configManager.getGuiConfig().getString(args[1] + ".Title"));
                                        return false;
                                    }
                                    Player newPlayer = Bukkit.getPlayer(args[2]);
                                    inventory.openInventory(args[1], inventory.getInventory(), newPlayer, plugin);
                                    msg.sendOpenMenu("openedFor", "found", newPlayer, plugin.configManager.getGuiConfig().getString(args[1] + ".Title"));
                                    msg.sendOpenMenu("open", "found", newPlayer, plugin.configManager.getGuiConfig().getString(args[1] + ".Title"));
                                } else if (args.length == 2) {
                                    inventory.openInventory(args[1], inventory.getInventory(), player, plugin);
                                    msg.sendOpenMenu("open", "found", player, plugin.configManager.getGuiConfig().getString(args[1] + ".Title"));
                                }
                            }
                            break;
                        case "reload":
                            if (!player.hasPermission(PermissionManager.itembuilder_reload.name())) {
                                msg.NoPermission(PermissionManager.itembuilder_reload.name());
                                return false;
                            }
                            plugin.configManager.reloadLanguageConfig();
                            plugin.configManager.reloadItemsConfig();
                            plugin.configManager.reloadGuiConfig();
                            msg.sendReload();
                            break;
                        case "list":
                            if (!player.hasPermission(PermissionManager.itembuilder_list.name())) {
                                msg.NoPermission(PermissionManager.itembuilder_list.name());
                            }
                            switch (args[1]) {
                                case "items":
                                    int index = 0;
                                    Set<String> itemList = plugin.getItemsConfig().getKeys(false);
                                    msg.getStringUtils().SendMessage(player, "&c-----&dItems&c----");
                                    for (String line : itemList) {
                                        index++;
                                        msg.getStringUtils().SendMessage(player, "&b#" + index + "&c- " + line);
                                    }
                                    break;
                                case "menus":
                                    Set<String> menuList = plugin.getGuiConfig().getKeys(false);
                                    msg.getStringUtils().SendMessage(player, "&c-----&dMenus&c----");
                                    for (String line : menuList) {
                                        index++;
                                        msg.getStringUtils().SendMessage(player, "&b#" + index + "&c- " + line);
                                    }
                                    break;
                            }
                            break;
                        case "help":
                            msg.sendHelpMessage();
                            break;

                    }
                }

            }

        }
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getAliases().contains(command.getName())){
            if (args.length == 1){
                List<String> list = new ArrayList<>();
                list.add("giveItem");
                list.add("openMenu");
                list.add("reload");
                list.add("list");
                list.add("help");
                return list;
            }else if (args.length == 2){
                switch (args[0]) {
                    case "giveItem":
                        return new ArrayList<>(plugin.getItemsConfig().getKeys(false));
                    case "openMenu":
                        return new ArrayList<>(plugin.configManager.getGuiConfig().getKeys(false));
                    case "list":
                        List<String> list = new ArrayList<>();
                        list.add("items");
                        list.add("menus");
                        return list;
                }
            }
        }
        return null;
    }

}
