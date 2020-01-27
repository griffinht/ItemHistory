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
    private ItemStack itemStack;
    TrackedItem(String eventName,OfflinePlayer offlinePlayer,Block block,Integer itemEntityId,ItemStack itemStack){
        this.eventName = eventName;
        this.offlinePlayer = offlinePlayer;
        this.block = block;
        this.itemEntityId = itemEntityId;
        this.itemStack = itemStack;
    }
    TrackedItem(String eventName){
        this.eventName = eventName;
        this.offlinePlayer = null;
        this.block = null;
        this.itemEntityId = null;
        this.itemStack = null;
    }
    String getEventName(){return eventName;}
    Block getBlock(){return block;}
    Integer getItemEntityId(){return itemEntityId;}
    ItemStack itemStack(){return itemStack;}

    @Override
    public Map<String,Object> serialize(){
        Map<String,Object> value = new LinkedHashMap<>();
        if(eventName!=null)value.put("eventName",eventName);
        if(offlinePlayer!=null)value.put("offlinePlayer",offlinePlayer.getUniqueId());
        if(block!=null)value.put("location",block.getLocation());
        if(itemEntityId!=null)value.put("itemEntityId",itemEntityId);
        if(itemStack!=null)value.put("itemStack",itemStack);
        return value;
    }
    public static TrackedItem deserialize(Map<String,Object> value){
        System.out.println(value.keySet());
        System.out.println(value.values());
        System.out.println(value.get("itemStack"));
        for(String string:value.keySet()){
            if(string.equals("itemStack"))System.out.println(value.get(string));
        }
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

        ItemStack itemStack = null;
        if(value.containsKey("itemStack")){
            Object raw = value.get("itemStack");
            if(raw instanceof ItemStack){
                itemStack = (ItemStack)raw;
            }
        }
        return new TrackedItem(eventName,offlinePlayer,block,itemEntityId,itemStack);
    }
}
