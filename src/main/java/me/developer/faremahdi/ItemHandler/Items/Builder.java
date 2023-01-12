package me.developer.faremahdi.ItemHandler.Items;

import me.developer.faremahdi.ItemHandler.ItemsManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Builder extends ItemsManager {

    public Builder(Material material, String name, int Amount, boolean glow, List<String> lore) {
        super(material, name, Amount, glow, lore);
    }

    @Override
    public ItemStack createItem() {
        return super.createItem();
    }
}
