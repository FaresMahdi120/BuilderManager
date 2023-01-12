package me.developer.faremahdi.GuiHandler;

import me.developer.faremahdi.BuilderManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GuiBuilder extends GuiConstructor {
    public GuiBuilder(String name, int size, Player player) {
        super(name, size, player);
    }

    @Override
    public Inventory getInventory() {
        return super.getInventory();
    }

    @Override
    public void openInventory(String path, Inventory inventory, Player player, BuilderManager plugin) {
        super.openInventory(path, inventory, player, plugin);
    }

}
