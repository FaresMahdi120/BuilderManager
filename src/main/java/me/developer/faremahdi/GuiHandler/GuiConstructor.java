package me.developer.faremahdi.GuiHandler;

import lombok.Getter;
import me.developer.faremahdi.BuilderManager;
import me.developer.faremahdi.ItemHandler.Items.Builder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
public abstract class GuiConstructor {
    private final String name;
    private final int size;
    private final Player player;
    public GuiConstructor(String name, int size, Player player){
        this.name = name;
        this.size = size;
        this.player = player;
    }
    public Inventory getInventory(){
        return Bukkit.createInventory(getPlayer(), getSize(), ChatColor.translateAlternateColorCodes('&', getName()));
    }
    public void openInventory(String path, Inventory inventory, Player player, BuilderManager plugin){
        player.openInventory(inventory);
        List<Integer> slots = plugin.getGuiConfig().getIntegerList(path + ".filler_item.slots");
        if (!slots.isEmpty()){
            for (int i : slots) {
                inventory.setItem(i, menuItems(path, plugin));
            }
        }
        Set<String> entries = plugin.getGuiConfig().getConfigurationSection(path + ".items").getKeys(false);
        for (String entry : entries) {
            int slot = plugin.getGuiConfig().getInt(path + ".items." + entry + ".slot");
            String material = plugin.getGuiConfig().getString(path + ".items." + entry + ".Material");
            int amount = plugin.getGuiConfig().getInt(path + ".items." + entry + ".Amount");
            String name = plugin.getGuiConfig().getString(path + ".items." + entry + ".Name");
            List<String> lore = plugin.getGuiConfig().getStringList(path + ".items." + entry + ".lore");
            Builder item = new Builder(Material.getMaterial(material), name, amount, false, lore);
            inventory.setItem(slot, item.createItem());
            player.getInventory().addItem(item.createItem());
        }
    }
    public ItemStack menuItems(String path, BuilderManager plugin) {
        String material = plugin.getGuiConfig().getString(path + ".filler_item.Material");
        int amount = plugin.getGuiConfig().getInt(path + ".filler_item.Amount");
        String name = plugin.getGuiConfig().getString(path + ".filler_item.Name");
        return new Builder(Material.getMaterial(material), name, amount, false, new ArrayList<>()).createItem();
    }

}
