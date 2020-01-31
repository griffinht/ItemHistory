package net.stzups.ItemHistory;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.*;

@SerializableAs("TrackedItem")
public class TrackedItem implements ConfigurationSerializable {//just contains info about an item
    private String eventName;//event that caused this
    private Location location;//location where the event happened
    private long time;
    private Map<String,Object> eventInfo;
    TrackedItem(String eventName, Location location, long time, Map<String,Object> eventInfo){
        this.eventName = eventName;
        this.location = location;
        this.time = time;
        this.eventInfo = eventInfo;
    }
    TrackedItem(String eventName, Location location, Map<String,Object> eventInfo){
        this.eventName = eventName;
        this.location = location;
        this.time = System.currentTimeMillis();
        this.eventInfo = eventInfo;
    }
    String getEventName(){return eventName;}
    Location getLocation(){return location;}
    long getTime(){return time;}
    Map<String,Object> getEventInfo(){return eventInfo;}

    @Override
    public Map<String,Object> serialize(){
        Map<String,Object> result = new LinkedHashMap<>();
        result.put("eventName",eventName);
        result.put("location",location);
        result.put("time",time);
        result.put("eventInfo",eventInfo);
        return result;
    }
    public static TrackedItem deserialize(Map<String,Object> value){ ;
        String eventName = null;
        if(value.containsKey("eventName"))eventName = value.get("eventName").toString();

        Location location = null;
        if(value.containsKey("location")){
            Object raw = value.get("location");
            if(raw instanceof Location){
                location = (Location)raw;
            }
        }

        long time;
        if(value.containsKey("time"))time = Long.parseLong(value.get("time").toString());
        else return null;
        Map<String,Object> eventInfo = new LinkedHashMap<>();
        if (value.containsKey("eventInfo")) {
            Object raw = value.get("eventInfo");
            if(raw instanceof Map<?,?>){
                for(Map.Entry<?,?> entry:((Map<?,?>)raw).entrySet()){
                    eventInfo.put(entry.getKey().toString(),entry.getValue());
                }
            }
        }
        TrackedItem trackedItem = new TrackedItem(eventName,location,time,eventInfo);
        System.out.println("desiedasr "+trackedItem);
        return trackedItem;
    }
}
