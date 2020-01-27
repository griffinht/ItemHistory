package net.lemonpickles.ItemHistory;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.*;

@SerializableAs("TrackedItem")
public class TrackedItem implements ConfigurationSerializable {//just contains info about an item
    private String name;
    TrackedItem(String name){this.name = name;}
    String getName(){return name;}

    @Override
    public Map<String,Object> serialize(){
        Map<String,Object> value = new HashMap<>();
        value.put("name",name);
        return value;
    }
    public static TrackedItem deserialize(Map<String,Object> value){
        return new TrackedItem((String)value.get("name"));
    }
}
