package net.lemonpickles.ItemHistory;

import net.lemonpickles.util.Node;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

class ItemEvent implements Listener {
    private ItemHistory plugin;
    ItemEvent(ItemHistory plugin){
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler
    public void onBlockDispense(BlockDispenseEvent event){

    }
}
