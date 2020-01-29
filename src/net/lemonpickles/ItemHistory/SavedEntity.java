package net.lemonpickles.ItemHistory;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@SerializableAs("SavedEntity")
public class SavedEntity implements ConfigurationSerializable {
    private UUID entityId;
    private EntityType entityType;
    private int ticksLived;
    private Location location;
    SavedEntity(Entity entity){
        this.entityId = entity.getUniqueId();
        this.entityType = entity.getType();
        this.ticksLived = entity.getTicksLived();
        this.location = entity.getLocation();
    }
    SavedEntity(UUID entityId,EntityType entityType,int ticksLived,Location location){
        this.entityId = entityId;
        this.entityType = entityType;
        this.ticksLived = ticksLived;
        this.location = location;
    }
    UUID getUniqueId(){return entityId;}
    EntityType getEntityType(){return entityType;}
    int getTicksLived(){return ticksLived;}
    Location getLocation(){return location;}

    public Map<String,Object> serialize(){
        Map<String,Object> value = new LinkedHashMap<>();
        value.put("entityId",entityId.toString());
        value.put("entityType",entityType.toString());
        value.put("ticksLived",ticksLived);
        value.put("location",location);
        return value;
    }
    public static SavedEntity deserialize(Map<String,Object> value){
        UUID entityId;
        if(value.containsKey("entityId"))entityId = UUID.fromString(value.get("entityId").toString().toUpperCase());
        else return null;

        EntityType entityType;
        if(value.containsKey("entityType"))entityType = EntityType.valueOf(value.get("entityType").toString().toUpperCase());
        else return null;

        int ticksLived;
        if(value.containsKey("ticksLived"))ticksLived = Integer.parseInt(value.get("ticksLived").toString());
        else return null;

        Location location;
        if(value.containsKey("location")&&value.get("location") instanceof Location)location = (Location)value.get("location");
        else return null;

        return new SavedEntity(entityId,entityType,ticksLived,location);
    }
}
