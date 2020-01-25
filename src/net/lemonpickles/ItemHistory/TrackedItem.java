package net.lemonpickles.ItemHistory;

import org.bukkit.configuration.serialization.SerializableAs;

import java.util.*;

@SerializableAs("TrackedItem")
public class TrackedItem {//just contains info about an item
    private String name;
    public Map<String,Object> serialize(){
        Map<String,Object> result = new HashMap<>();
        result.put("name",name);
        return result;
    }
    public static TrackedItem deserialize(Map<String,Object> args){
        String name = "";
        if(args.containsKey("name"))name = args.get("name").toString();
        return new TrackedItem(name);
    }
    TrackedItem(String name){
        this.name = name;
    }
    String getName(){return name;}
}
