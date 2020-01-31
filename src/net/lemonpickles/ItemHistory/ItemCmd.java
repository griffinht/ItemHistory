package net.lemonpickles.ItemHistory;

import net.lemonpickles.util.Node;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
            if(args.length==2){
                plugin.trackedItemTree.addNode(new Node<>(new TrackedItem(args[1],null,null)));
                sender.sendMessage("Added "+args[1]+" as a new trackedItem");
            }
            else sender.sendMessage("Not enough arguments");
            return true;
        }else if(args[0].equalsIgnoreCase("get")){
            if(args.length==2){
                List<Node<TrackedItem>> nodes = plugin.trackedItemTree.getByName(args[1]);
                if(nodes.size()==0)sender.sendMessage("Could not find trackedItem with name "+args[1]);
                else for(Node<TrackedItem> node:nodes)sender.sendMessage(node.getData().getEventName());
            }else sender.sendMessage("Not enough arguments");
            return true;
        }else if(args[0].equalsIgnoreCase("getChildren")){
            if(args.length==2){
                List<Node<TrackedItem>> nodes = plugin.trackedItemTree.getByName(args[1]);
                if(nodes.size()==0)sender.sendMessage("Could not find trackedItem with name "+args[1]);
                else{
                    sender.sendMessage("Children of "+args[1]);
                    for(Node<TrackedItem> node:nodes)for(Node<TrackedItem> childNode:node.getChildren())sender.sendMessage(childNode.getData().getEventName());
                }
            }else if(args.length==3){
                List<Node<TrackedItem>> nodes = plugin.trackedItemTree.getByName(args[1]);
                if(nodes.size()==0)sender.sendMessage("Could not find trackedItem with name "+args[1]);
                else{
                    sender.sendMessage("Children of "+args[1]);
                    for(Node<TrackedItem> node:nodes)for(Node<TrackedItem> child:node.getChildren(Integer.parseInt(args[2])))sender.sendMessage(child.getData().getEventName());
                }
            }else sender.sendMessage("Not enough arguments");
            return true;
        }else if(args[0].equalsIgnoreCase("getParents")){
            if(args.length==2){
                List<Node<TrackedItem>> nodes = plugin.trackedItemTree.getByName(args[1]);
                if(nodes.size()==0)sender.sendMessage("Could not find trackedItem with name "+args[1]);
                else{
                    sender.sendMessage("Parents of "+args[1]);
                    for(Node<TrackedItem> node:nodes)for(Node<TrackedItem> child:node.getChildren())sender.sendMessage(child.getData().getEventName());//todo parents
                }
            }else if(args.length==3){
                List<Node<TrackedItem>> nodes = plugin.trackedItemTree.getByName(args[1]);
                if(nodes.size()==0)sender.sendMessage("Could not find trackedItem with name "+args[1]);
                else{
                    sender.sendMessage("Parents of "+args[1]);
                    for(Node<TrackedItem> node:nodes)for(Node<TrackedItem> child:node.getChildren(Integer.parseInt(args[2])))sender.sendMessage(child.getData().getEventName());//todo parents
                }
            }else sender.sendMessage("Not enough arguments");
            return true;
        }else if(args[0].equalsIgnoreCase("list")){
            sender.sendMessage("Listing all nodes in tree");
            for(Node<TrackedItem> node:plugin.trackedItemTree.getAllNodes()){
                sender.sendMessage(node.getData().getEventName());
            }
            return true;
        }else if(args[0].equalsIgnoreCase("addAsChild")){
            if(args.length==3){
                List<Node<TrackedItem>> nodes = plugin.trackedItemTree.getByName(args[1]);
                if(nodes.size()==0)sender.sendMessage("Could not find trackedItem with name "+args[1]);
                else{
                    Node<TrackedItem> node = new Node<>(new TrackedItem(args[2],null,null),nodes);
                    plugin.trackedItemTree.addNode(node);
                    StringBuilder parents = new StringBuilder();
                    for(Node<TrackedItem> parent:node.getParents()) parents.append(parent.getData().getEventName());
                    sender.sendMessage("Added "+args[2]+" as new trackedItem with parents "+parents);
                }
            }else sender.sendMessage("Not enough arguments");
            return true;
        }else if(args[0].equalsIgnoreCase("roots")){
            sender.sendMessage("Listing all roots");
            for(Node<TrackedItem> node:plugin.trackedItemTree.getRoots())sender.sendMessage(node.getData().getEventName());
            return true;
        }else if(args[0].equalsIgnoreCase("leaves")){
            sender.sendMessage("Listing all leaves");
            for(Node<TrackedItem> node:plugin.trackedItemTree.getLeaves())sender.sendMessage(node.getData().getEventName());
            return true;
        } else if (args[0].equalsIgnoreCase("go")) {
            sender.sendMessage("Going...");
            Map<String,Object> stringObjectMap = new HashMap<>();
            sender.sendMessage("Gone");
            return true;
        } else if (args[0].equalsIgnoreCase("map")){
            sender.sendMessage("Mapping");
            for(Node<TrackedItem> root:plugin.trackedItemTree.getRoots()){
                StringBuilder stringBuilder = new StringBuilder("map: ");
                for(Node<TrackedItem> child:root.getChildren(-1)){
                    stringBuilder.append(child.getData().getEventName()).append(", ").append(new SimpleDateFormat("HH:mm:ss").format(new Date(child.getData().getTime()))).append("-> ");
                }
                sender.sendMessage(stringBuilder.toString());
            }
            return true;
        }
        sender.sendMessage("Unknown argument");
        return true;
    }
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args){
        List<String> completions = new ArrayList<>();
        if(args.length==1){
            for(String string:new String[]{"add","get","getChildren","getParents","list","addAsChild","roots","leaves","go","map"})if(checkCompletions(string,args[0]))completions.add(string);
            return completions;
        }else if(args.length==2){
            if(args[0].equalsIgnoreCase("add"))return completions;
            else if(args[0].equalsIgnoreCase("get")||args[0].equalsIgnoreCase("getChildren")||args[0].equalsIgnoreCase("getParents")||args[0].equalsIgnoreCase("addAsChild"))for(Node<TrackedItem> node:plugin.trackedItemTree.getAllNodes())if(checkCompletions(node.getData().getEventName(),args[1]))completions.add(node.getData().getEventName());//long boi
            return completions;
        }else if(args.length==3) {
            if (args[0].equalsIgnoreCase("getAsChild")) return completions;
        }
        return completions;
    }
}
