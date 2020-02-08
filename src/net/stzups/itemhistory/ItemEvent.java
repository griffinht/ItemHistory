package net.stzups.itemhistory;

import net.stzups.util.Node;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

class ItemEvent implements Listener {
    private ItemHistory plugin;
    private HashSet<ItemStack> stackMap = new HashSet<>();
    ItemEvent(ItemHistory plugin){
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        /*List<Node<TrackedItem>> parents = new ArrayList<>();
        for(Node<TrackedItem> node:plugin.trackedItemTree.getLeaves()){
            TrackedItem nodeTrackedItem = node.getData();
            if(nodeTrackedItem.getOfflinePlayer().equals(event.getPlayer())&&event.getItemInHand().equals(nodeTrackedItem.getLinkedItemStack().getParent()))parents.add(node);
        }
        ItemStack itemStack = event.getItemInHand().clone();
        itemStack.setAmount(1);
        TrackedItem trackedItem = new TrackedItem(event.getEventName(),event.getPlayer(),event.getBlock(),null,itemStack);
        plugin.trackedItemTree.addNode(new Node<>(trackedItem,parents));*/
    }
    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event){
        Node<TrackedItem> trackedItemNode = null;
        for(Node<TrackedItem> node:plugin.trackedItemTree.getLeaves()){
            TrackedItem nodeTrackedItem = node.getData();
            //System.out.println(((LinkedItemStack)nodeTrackedItem.getEventInfo().get("linkedItemStack")).getParent());
            //System.out.println(event.getItem().getItemStack());
            if(nodeTrackedItem.getEventInfo().containsKey("savedEntity")&&((SavedEntity)nodeTrackedItem.getEventInfo().get("savedEntity")).getUniqueId()==event.getItem().getUniqueId())trackedItemNode = node;
            //System.out.println((trackedItemNode==null));
            //System.out.println();
        }
        System.out.println(stackMap.size());
        stackMap.add(event.getItem().getItemStack());
        System.out.println(stackMap.size());
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("savedEntity",new SavedEntity(event.getEntity()));
        map.put("linkedItemStack",new LinkedItemStack(event.getItem().getItemStack()));
        plugin.trackedItemTree.addNode(new Node<>(new TrackedItem(event.getEventName(),event.getEntity().getLocation(),map),trackedItemNode));
        System.out.println("pickup "+event.getItem().getItemStack().getType()+" with hashcode "+event.getItem().getItemStack().hashCode());
    }
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event){
        Node<TrackedItem> trackedItemNode = null;
        for(Node<TrackedItem> node:plugin.trackedItemTree.getLeaves()){
            TrackedItem nodeTrackedItem = node.getData();
            //System.out.println(((SavedEntity)nodeTrackedItem.getEventInfo().get("savedEntity")).getUniqueId());
            //System.out.println(event.getEntity().getUniqueId());
            if(nodeTrackedItem.getEventInfo().containsKey("savedEntity")&&((SavedEntity)nodeTrackedItem.getEventInfo().get("savedEntity")).getUniqueId().equals(event.getPlayer().getUniqueId()))trackedItemNode = node;
            //System.out.println((trackedItemNode==null));
            //System.out.println();
        }
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("savedEntity",new SavedEntity(event.getPlayer()));
        map.put("linkedItemStack",new LinkedItemStack(event.getItemDrop().getItemStack()));
        plugin.trackedItemTree.addNode(new Node<>(new TrackedItem(event.getEventName(),event.getPlayer().getLocation(),map),trackedItemNode));
        System.out.println("dropped "+event.getItemDrop().getItemStack().getType()+" with hashcode "+event.getItemDrop().getItemStack().hashCode());
        System.out.println(stackMap.size());
        stackMap.add(event.getItemDrop().getItemStack());
        System.out.println(stackMap.size());
    }
    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event){
        Node<TrackedItem> trackedItemNode = null;
        for(Node<TrackedItem> node:plugin.trackedItemTree.getLeaves()){
            TrackedItem nodeTrackedItem = node.getData();
            //System.out.println(((LinkedItemStack)nodeTrackedItem.getEventInfo().get("linkedItemStack")).getParent());
            //System.out.println(event.getEntity().getItemStack());
            if(nodeTrackedItem.getEventInfo().containsKey("linkedItemStack")&&((LinkedItemStack)nodeTrackedItem.getEventInfo().get("linkedItemStack")).getParent().equals(event.getEntity().getItemStack()))trackedItemNode = node;
            //System.out.println((trackedItemNode==null));
            //System.out.println();
        }
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("savedEntity",new SavedEntity(event.getEntity()));
        map.put("linkedItemStack",new LinkedItemStack(event.getEntity().getItemStack()));
        plugin.trackedItemTree.addNode(new Node<>(new TrackedItem(event.getEventName(),event.getLocation(),map),trackedItemNode));
        System.out.println("spawned "+event.getEntity().getItemStack().getType()+" with hashcode "+event.getEntity().getItemStack().hashCode());
        System.out.println(stackMap.size());
        stackMap.add(event.getEntity().getItemStack());
        System.out.println(stackMap.size());
    }
}
