package me.developer.faremahdi.Listener;

import lombok.Getter;
import me.developer.faremahdi.BuilderManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public class OnInventoryClick implements Listener {

    @Getter
    private final BuilderManager plugin;
    public OnInventoryClick(BuilderManager plugin) {
        this.plugin = plugin;
    }

    @org.bukkit.event.EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        String title = e.getView().getTitle();
        Set<String> entries = getPlugin().getGuiConfig().getKeys(false);
        for (String entry : entries){
            if (title.equals(ChatColor.translateAlternateColorCodes('&',
                    getPlugin().getGuiConfig().getString(entry + ".Title")))){
                List<String> actions = getActions(e.getCurrentItem(), title, entry);
                processAction(actions, (Player) e.getWhoClicked());
                if (getPlugin().getGuiConfig().getBoolean(entry + ".Cancel_Click")){
                    e.setCancelled(true);
                }
            }
        }

    }
    public List<String> getActions(ItemStack itemStack, String inventoryTitle, String entry){
        Set<String> entries = getPlugin().getGuiConfig().getKeys(false);
            Set<String> subNodes = getPlugin().getGuiConfig().getConfigurationSection(entry + ".items").getKeys(false);
            String itemName = itemStack.getItemMeta().getDisplayName();
            for (String node : subNodes){
                if (ChatColor.translateAlternateColorCodes('&', getPlugin().getGuiConfig().getString(entry + ".items." + node + ".Name")).equalsIgnoreCase(itemName)){
                    return getPlugin().getGuiConfig().getStringList(entry + ".items." + node + ".on_click");
                }
            }
            if (getPlugin().getGuiConfig().contains(entry + ".filler_item.on_click")){
                if (ChatColor.translateAlternateColorCodes('&', getPlugin().getGuiConfig().getString(entry + ".filler_item.Name")).equalsIgnoreCase(itemName)){
                    return getPlugin().getGuiConfig().getStringList(entry + ".filler_item.on_click");
                }
            }
        return null;
    }
    public void processAction(List<String> actions, Player player){
        for (String action : actions) {
            if (action.equalsIgnoreCase("CLOSE")){
                player.closeInventory();
            }else if (action.contains("CONSOLE:")){
                String newAction = action.replaceAll("CONSOLE:", "");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), newAction);
            }else if (action.contains("PLAYER:")){
                String newAction = action.replaceAll("PLAYER:", "");
                player.performCommand(newAction);
            }else if (action.contains("OPEN:")) {
                String newAction = action.replaceAll("OPEN:", "");
                player.performCommand("openInv " + newAction);
            }else if (action.contains("MESSAGE:")) {
                String newAction = action.replaceAll("MESSAGE:", "");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', newAction));
            }
        }
    }

}
