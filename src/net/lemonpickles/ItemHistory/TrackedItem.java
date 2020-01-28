package net.lemonpickles.ItemHistory;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import sun.awt.image.ImageWatched;

import java.util.*;

@SerializableAs("TrackedItem")
public class TrackedItem implements ConfigurationSerializable {//just contains info about an item
    private String eventName;//event that caused this
    private OfflinePlayer offlinePlayer;
    private Block block;
    private Integer itemEntityId;//entity id for item on ground
    private LinkedItemStack linkedItemStack;
    TrackedItem(String eventName,OfflinePlayer offlinePlayer,Block block,Integer itemEntityId,ItemStack itemStack){
        this.eventName = eventName;
        this.offlinePlayer = offlinePlayer;
        this.block = block;
        this.itemEntityId = itemEntityId;
        this.linkedItemStack = new LinkedItemStack(itemStack);
    }
    TrackedItem(String eventName,OfflinePlayer offlinePlayer,Block block,Integer itemEntityId,LinkedItemStack linkedItemStack){
        this.eventName = eventName;
        this.offlinePlayer = offlinePlayer;
        this.block = block;
        this.itemEntityId = itemEntityId;
        this.linkedItemStack = linkedItemStack;
    }
    TrackedItem(String eventName){
        this.eventName = eventName;
        this.offlinePlayer = null;
        this.block = null;
        this.itemEntityId = null;
        this.linkedItemStack = null;
    }
    String getEventName(){return eventName;}
    Block getBlock(){return block;}
    Integer getItemEntityId(){return itemEntityId;}
    LinkedItemStack getLinkedItemStack(){return linkedItemStack;}
    OfflinePlayer getOfflinePlayer(){return offlinePlayer;}

    @Override
    public Map<String,Object> serialize(){
        Map<String,Object> result = new LinkedHashMap<>();
        if(eventName!=null)result.put("eventName",eventName);
        if(offlinePlayer!=null)result.put("offlinePlayer",offlinePlayer.getUniqueId());
        if(block!=null)result.put("location",block.getLocation());
        if(itemEntityId!=null)result.put("itemEntityId",itemEntityId);
        if(linkedItemStack!=null)result.put("linkedItemStack",linkedItemStack);
        return result;
    }
    public static TrackedItem deserialize(Map<String,Object> value){
        Object eventObject = value.get("eventName");
        String eventName = null;
        if(eventObject!=null)eventName = eventObject.toString();

        Object offlinePlayerObject = value.get("offlinePlayer");
        OfflinePlayer offlinePlayer = null;
        if(offlinePlayerObject!=null)offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(offlinePlayerObject.toString()));

        Block block = null;
        if(value.containsKey("location")){
            Object raw = value.get("location");
            if(raw instanceof Location){
                block = ((Location)raw).getBlock();
            }
        }

        Object itemObject = value.get("itemEntityId");
        Integer itemEntityId = null;
        if(itemObject!=null)itemEntityId = Integer.parseInt(itemObject.toString());

        LinkedItemStack linkedItemStack = null;
        if(value.containsKey("linkedItemStack")){
            Object raw = value.get("linkedItemStack");
            if(raw instanceof LinkedItemStack){
                linkedItemStack = (LinkedItemStack)raw;
            }
        }
        return new TrackedItem(eventName,offlinePlayer,block,itemEntityId,linkedItemStack);
    }
}
