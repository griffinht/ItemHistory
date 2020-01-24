package net.lemonpickles.ItemHistory;

import java.util.*;

public class TrackedList {
    ItemHistory plugin;
    Map<UUID,TrackedItem> trackedItemMap = new HashMap<>();
    List<UUID> roots;
    TrackedList(ItemHistory plugin){
        this.plugin = plugin;
    }
    Map<UUID,TrackedItem> getChildren(UUID uuid){
        Map<UUID,TrackedItem> children = new HashMap<>();
        List<UUID> tracked = new ArrayList<>();
        TrackedItem trackedItem = trackedItemMap.get(uuid);
        UUID startUuid = trackedItem.getUuid();
        Iterator<UUID> iterator = trackedItem.getChildren().iterator();
        while(iterator.hasNext()){
            iterator.next();
            System.out.println("Looping through "+trackedItem.getUuid()+"'s "+trackedItem.getChildren().size()+" children");
            for(UUID childUUID:trackedItem.getChildren()){
                if(!tracked.contains(childUUID)){
                    if(trackedItemMap.get(childUUID).hasChild())trackedItem = trackedItemMap.get(childUUID);
                    else tracked.add(childUUID);
                }else{
                    if(trackedItem.getUuid().equals(startUuid))break;
                }
            }
        }
        System.out.println("done, returning "+children.keySet());
        return children;
    }
    void addTrackedItem(TrackedItem trackedItem){
        trackedItemMap.put(trackedItem.getUuid(),trackedItem);
        if(!trackedItem.hasParent())roots.add(trackedItem.getUuid());
    }
}
