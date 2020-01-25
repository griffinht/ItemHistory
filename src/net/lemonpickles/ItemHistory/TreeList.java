package net.lemonpickles.ItemHistory;

import net.lemonpickles.util.FileMgmt;
import net.lemonpickles.util.Node;
import org.bukkit.configuration.MemorySection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TreeList extends FileMgmt {
    TreeList(ItemHistory plugin){
        super(plugin,"itemtree.yml");
        load();
    }
    public void save(){

    }
    public void load(){
        Map<UUID, Node<TrackedItem>> nodeMap = new HashMap<>();
        for(Map.Entry<String,Object> entry1:config.getValues(false).entrySet()){
            if(entry1.getValue() instanceof MemorySection){
                MemorySection memorySection1 = (MemorySection)entry1;
                for(Map.Entry<String,Object> entry2:memorySection1.getValues(false).entrySet()){
                    if(entry2 instanceof MemorySection){
                        MemorySection memorySection2 = (MemorySection)entry2.getValue();
                        String uuidStr = memorySection2.getString("uuid");
                        UUID uuid;
                        if(uuidStr!=null)uuid = UUID.fromString(uuidStr);
                        else{
                            plugin.logger.warning("Could not find UUID in config");
                            break;
                        }

                    }
                }
            }
        }
    }
}