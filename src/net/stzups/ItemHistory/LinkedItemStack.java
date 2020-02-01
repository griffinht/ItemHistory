package net.stzups.ItemHistory;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@SerializableAs("LinkedItemStack")
public class LinkedItemStack implements ConfigurationSerializable{
    private ItemStack parent;
    private List<ItemStack> children;
    LinkedItemStack(ItemStack parent, List<ItemStack> children){
        this.parent = parent;
        this.children = children;
    }
    LinkedItemStack(ItemStack parent,ItemStack child){
        this.parent = parent;
        this.children = Collections.singletonList(child);
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
        return result;
    }
    public static LinkedItemStack deserialize(Map<String,Object> value){
        ItemStack parent;
        if(value.containsKey("parent")&&value.get("parent") instanceof ItemStack)parent = (ItemStack)value.get("parent");
        else return null;
        List<ItemStack> children = new ArrayList<>();
        if(value.containsKey("children")&&value.get("children")instanceof List<?>){
            for(Object raw:(List<?>)value.get("children")) {
                if (raw instanceof ItemStack)children.add((ItemStack)raw);
            }
        }
        return new LinkedItemStack(parent,children);
    }
}
