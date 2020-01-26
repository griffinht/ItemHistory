package net.lemonpickles.ItemHistory;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ItemHistory extends JavaPlugin {
    public Logger logger;
    TrackedList trackedItemTree;
    TreeList treeList;
    public void onEnable(){
        long start = System.nanoTime();
        logger = Bukkit.getLogger();
        new ItemEvent(this);
        new ItemCmd(this);
        treeList = new TreeList(this);
        trackedItemTree = new TrackedList(this);
        logger.info("Enabled ItemHistory in "+((System.nanoTime()-start)/1000000)+"ms");
    }
    public void onDisable(){
        long start = System.nanoTime();
        treeList.save();
        logger.info("Disabled ItemHistory in "+((System.nanoTime()-start)/1000000)+"ms");
    }
}
