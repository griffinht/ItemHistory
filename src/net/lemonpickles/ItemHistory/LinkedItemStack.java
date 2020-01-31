package net.lemonpickles.ItemHistory;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@SerializableAs("LinkedItemStack")
public class LinkedItemStack implements ConfigurationSerializable{
    private ItemStack parent;
    private List<ItemStack> children;
    private Integer index;
    LinkedItemStack(ItemStack parent,List<ItemStack> children,Integer index){
        this.parent = parent;
        this.children = children;
        this.index = index;
    }
    LinkedItemStack(ItemStack parent,ItemStack child,Integer index){
        this.parent = parent;
        this.children = Collections.singletonList(child);
        this.index = index;
    }
    LinkedItemStack(ItemStack parent){
        this.parent = parent;
        this.children = new ArrayList<>();
    }
    ItemStack getParent(){return parent;}
    List<ItemStack> getChildren(){return children;}

    @Override
    public Map<String,Object> serialize(){
        Map<String,Object> result = new LinkedHashMap<>();
        result.put("parent",parent);
        result.put("children",children);
        result.put("index",index);
        return result;
    }
    public static LinkedItemStack deserialize(Map<String,Object> value){
        ItemStack parent = null;
        if(value.containsKey("parent")){
            Object raw = value.get("parent");
            if(raw instanceof ItemStack){
                parent = (ItemStack)raw;
            }
        }
        List<ItemStack> children = new ArrayList<>();
        if(value.containsKey("children")){
            Object rawList = value.get("children");
            if(rawList instanceof List<?>){
                for(Object raw:(List<?>)rawList) {
                    if (raw instanceof ItemStack) {
                        children.add((ItemStack) raw);
                    }
                }
            }
        }
        Integer index = null;
        if(value.containsKey("index"))index = Integer.parseInt(value.get("index").toString());
        return new LinkedItemStack(parent,children,index);
    }
}
