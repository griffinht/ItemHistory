package net.lemonpickles.ItemHistory;

import java.util.List;
import java.util.UUID;

public class TrackedItem {
    private String name;
    private UUID uuid;
    private List<UUID> children;
    private List<UUID> parents;
    TrackedItem(List<UUID> parents, String name){
        this.uuid = UUID.randomUUID();
        this.parents = parents;
        this.name = name;
    }
    UUID getUuid(){return uuid;}
    List<UUID> getParents(){return parents;}
    List<UUID> getChildren(){return children;}
    String getName(){return name;}
    void addChild(UUID child){children.add(child);}
    void addChildren(List<UUID> children){this.children.addAll(children);}
    boolean hasChild(){return children.size()>0;}
    boolean hasParent(){return parents.size()>0;}
}
