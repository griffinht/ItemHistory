package net.lemonpickles.ItemHistory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
    public void onInventoryMoveItemEvent(InventoryMoveItemEvent e){
        System.out.println(e.getItem().getAmount()+" "+e.getItem().getType()+": "+e.getSource().getHolder()+"->"+e.getDestination().getHolder());
    }
    @EventHandler
    public void onInventoryPickupItemEvent(InventoryPickupItemEvent e){
        System.out.println(e.getItem().getItemStack().getAmount()+" "+e.getItem().getItemStack().getType()+": somewhere to ->"+e.getInventory().getHolder());
    }
    @EventHandler
    public void onInventoryInteractEvent(InventoryInteractEvent e){
        System.out.println(e.getResult()+", "+e.getInventory().getHolder());
    }
    @EventHandler
    public void onItemSpawnEvent(ItemSpawnEvent e){
        System.out.println(e.getEntity().getItemStack().getAmount()+" "+e.getEntity().getItemStack().getType()+": dropped from somewhere");
    }
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e){
        System.out.println(e.getCurrentItem().getAmount()+" "+e.getCurrentItem().getType()+" "+e.getAction()+" in "+e.getClickedInventory());
    }
}
