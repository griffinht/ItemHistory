package net.lemonpickles.ItemHistory;

import net.lemonpickles.util.Node;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

class ItemEvent implements Listener {
    private ItemHistory plugin;
    ItemEvent(ItemHistory plugin){
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        List<Node<TrackedItem>> parents = new ArrayList<>();
        for(Node<TrackedItem> node:plugin.trackedItemTree.getLeaves()){
            TrackedItem nodeTrackedItem = node.getData();
            if(nodeTrackedItem.getOfflinePlayer().equals(event.getPlayer())&&event.getItemInHand().equals(nodeTrackedItem.getLinkedItemStack().getParent()))parents.add(node);
        }
        ItemStack itemStack = event.getItemInHand().clone();
        itemStack.setAmount(1);
        TrackedItem trackedItem = new TrackedItem(event.getEventName(),event.getPlayer(),event.getBlock(),null,itemStack);
        plugin.trackedItemTree.addNode(new Node<>(trackedItem,parents));
    }
    @EventHandler
    public void onBlockDispense(BlockDispenseEvent event){
        List<Node<TrackedItem>> parents = new ArrayList<>();
        for(Node<TrackedItem> node:plugin.trackedItemTree.getLeaves()){
            TrackedItem nodeTrackedItem = node.getData();
            if(nodeTrackedItem.getBlock()!=null&&nodeTrackedItem.getBlock().equals(event.getBlock()))parents.add(node);
        }
        TrackedItem trackedItem = new TrackedItem(event.getEventName(),null,event.getBlock(),null,event.getItem());
        plugin.trackedItemTree.addNode(new Node<>(trackedItem, parents));
    }
}
