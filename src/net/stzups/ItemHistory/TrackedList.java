package net.stzups.ItemHistory;

import net.stzups.util.Node;
import net.stzups.util.Graph;

import java.util.*;

public class TrackedList extends Graph<TrackedItem> {
    private ItemHistory plugin;
    TrackedList(ItemHistory plugin){
        this.plugin = plugin;
    }
    List<Node<TrackedItem>> getByName(String name){
        List<Node<TrackedItem>> hits = new ArrayList<>();
        for(Node<TrackedItem> node:getAllNodes()){
            if(node.getData().getEventName().equalsIgnoreCase(name)){
                hits.add(node);
            }
        }
        return hits;
    }
}
