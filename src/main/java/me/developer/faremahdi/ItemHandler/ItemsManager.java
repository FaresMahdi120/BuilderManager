package me.developer.faremahdi.ItemHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemsManager extends ItemStack{

    private final Material material;
    private final String name;
    private final int Amount;
    private final boolean glow;
    private final List<String> lore;


    public ItemsManager(Material material, String name, int Amount, boolean glow, List<String> lore){
        this.material = material;
        this.Amount = Amount;
        this.name = name;
        this.glow = glow;
        this.lore = lore;
    }
    public ItemStack createItem(){
        ItemStack itemStack = new ItemStack(this.material, this.Amount);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.name));
        /*if (this.glow){
            itemStack.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemStack.addEnchantment(Enchantment.LUCK, 1);
        }*/
        if (!(meta.hasLore())){
            List<String> coloredLore = new ArrayList<>();
            for (String line: this.lore) {
                coloredLore.add(ChatColor.translateAlternateColorCodes('&', line));
            }
            meta.setLore(coloredLore);
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }




}
