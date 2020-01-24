package net.lemonpickles.ItemHistory;

import org.bukkit.command.*;

import java.util.ArrayList;
import java.util.List;

public class ItemCmd implements CommandExecutor, TabCompleter {
    private ItemHistory plugin;
    ItemCmd(ItemHistory plugin){
        this.plugin = plugin;
        PluginCommand pluginCommand = plugin.getCommand("itemhistory");
        assert pluginCommand != null;
        pluginCommand.setExecutor(this);
        pluginCommand.setTabCompleter(this);
    }
    boolean checkCompletions(String a, String arg){
        if(a.length()<arg.length()){return false;}
        return a.substring(0,arg.length()).equalsIgnoreCase(arg);
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(args.length==0){sender.sendMessage("No argument");return true;}
        if(args[0].equalsIgnoreCase("add")){
            if(args.length==2)plugin.trackedList.addTrackedItem(new TrackedItem(null,args[1]));
            else sender.sendMessage("Not enough arguments");
            return true;
        }else if(args[0].equalsIgnoreCase("get")){
            if(args.length==2){
                for(TrackedItem trackedItem:plugin.trackedList.trackedItemMap.values()){
                    if(trackedItem.getName().equalsIgnoreCase(args[1])){
                        sender.sendMessage(trackedItem.getUuid()+": "+trackedItem);
                        break;
                    }
                }
                sender.sendMessage("Could not find trackedItem with name "+args[1]);
            }else sender.sendMessage("Not enough arguments");
            return true;
        }else if(args[0].equalsIgnoreCase("getchildren")){
            if(args.length==2){
                for(TrackedItem trackedItem:plugin.trackedList.trackedItemMap.values()){
                    if(trackedItem.getName().equalsIgnoreCase(args[1])){
                        sender.sendMessage(plugin.trackedList.getChildren(trackedItem.getUuid()).toString());
                        break;
                    }
                }
                sender.sendMessage("Could not find trackedItem with name "+args[1]);
            }else sender.sendMessage("Not enough arguments");
            return true;
        }else if(args[0].equalsIgnoreCase("getparents")){
            if(args.length==2){
                for(TrackedItem trackedItem:plugin.trackedList.trackedItemMap.values()){
                    if(trackedItem.getName().equalsIgnoreCase(args[1])){
                        sender.sendMessage(plugin.trackedList.getChildren(trackedItem.getUuid()).toString());//todo parents
                        break;
                    }
                }
                sender.sendMessage("Could not find trackedItem with name "+args[1]);
            }else sender.sendMessage("Not enough arguments");
            return true;
        }
        sender.sendMessage("Unknown argument");
        return true;
    }
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args){
        List<String> completions = new ArrayList<>();

        return null;
    }
}
