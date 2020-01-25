package net.lemonpickles.ItemHistory;

import net.lemonpickles.util.Node;
import org.bukkit.command.*;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

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
            if(args.length==2){
                plugin.trackedItemTree.addNode(new Node<>(new TrackedItem(args[1])));
                sender.sendMessage("Added "+args[1]+" as a new trackedItem");
            }
            else sender.sendMessage("Not enough arguments");
            return true;
        }else if(args[0].equalsIgnoreCase("get")){
            if(args.length==2){
                List<Node<TrackedItem>> nodes = plugin.trackedItemTree.getByName(args[1]);
                if(nodes.size()==0)sender.sendMessage("Could not find trackedItem with name "+args[1]);
                else for(Node<TrackedItem> node:nodes)sender.sendMessage(node.getData().getName());
            }else sender.sendMessage("Not enough arguments");
            return true;
        }else if(args[0].equalsIgnoreCase("getChildren")){
            if(args.length==2){
                List<Node<TrackedItem>> nodes = plugin.trackedItemTree.getByName(args[1]);
                if(nodes.size()==0)sender.sendMessage("Could not find trackedItem with name "+args[1]);
                else{
                    sender.sendMessage("Children of "+args[1]);
                    for(Node<TrackedItem> node:nodes)for(Node<TrackedItem> childNode:node.getChildren())sender.sendMessage(childNode.getData().getName());
                }
            }else if(args.length==3){
                List<Node<TrackedItem>> nodes = plugin.trackedItemTree.getByName(args[1]);
                if(nodes.size()==0)sender.sendMessage("Could not find trackedItem with name "+args[1]);
                else{
                    sender.sendMessage("Children of "+args[1]);
                    for(Node<TrackedItem> node:nodes)for(Node<TrackedItem> child:node.getChildren(Integer.parseInt(args[2])))sender.sendMessage(child.getData().getName());
                }
            }else sender.sendMessage("Not enough arguments");
            return true;
        }else if(args[0].equalsIgnoreCase("getParents")){
            if(args.length==2){
                List<Node<TrackedItem>> nodes = plugin.trackedItemTree.getByName(args[1]);
                if(nodes.size()==0)sender.sendMessage("Could not find trackedItem with name "+args[1]);
                else{
                    sender.sendMessage("Parents of "+args[1]);
                    for(Node<TrackedItem> node:nodes)for(Node<TrackedItem> child:node.getChildren())sender.sendMessage(child.getData().getName());//todo parents
                }
            }else if(args.length==3){
                List<Node<TrackedItem>> nodes = plugin.trackedItemTree.getByName(args[1]);
                if(nodes.size()==0)sender.sendMessage("Could not find trackedItem with name "+args[1]);
                else{
                    sender.sendMessage("Parents of "+args[1]);
                    for(Node<TrackedItem> node:nodes)for(Node<TrackedItem> child:node.getChildren(Integer.parseInt(args[2])))sender.sendMessage(child.getData().getName());//todo parents
                }
            }else sender.sendMessage("Not enough arguments");
            return true;
        }else if(args[0].equalsIgnoreCase("list")){
            sender.sendMessage("Listing all nodes in tree");
            for(Node<TrackedItem> node:plugin.trackedItemTree.getAllNodes()){
                sender.sendMessage(node.getData().getName());
            }
            return true;
        }else if(args[0].equalsIgnoreCase("addAsChild")){
            if(args.length==3){
                List<Node<TrackedItem>> nodes = plugin.trackedItemTree.getByName(args[1]);
                if(nodes.size()==0)sender.sendMessage("Could not find trackedItem with name "+args[1]);
                else{
                    Node<TrackedItem> node = new Node<>(new TrackedItem(args[2]),nodes);
                    plugin.trackedItemTree.addNode(node);
                    StringBuilder parents = new StringBuilder();
                    for(Node<TrackedItem> parent:node.getParents()) parents.append(parent.getData().getName());
                    sender.sendMessage("Added "+args[2]+" as new trackedItem with parents "+parents);
                }
            }else sender.sendMessage("Not enough arguments");
            return true;
        }
        sender.sendMessage("Unknown argument");
        return true;
    }
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args){
        List<String> completions = new ArrayList<>();
        if(args.length==1){
            for(String string:new String[]{"add","get","getChildren","getParents","list","addAsChild"})if(checkCompletions(string,args[0]))completions.add(string);
            return completions;
        }else if(args.length==2){
            if(args[0].equalsIgnoreCase("add"))return completions;
            else if(args[0].equalsIgnoreCase("get")||args[0].equalsIgnoreCase("getChildren")||args[0].equalsIgnoreCase("getParents")||args[0].equalsIgnoreCase("addAsChild"))for(Node<TrackedItem> node:plugin.trackedItemTree.getAllNodes())if(checkCompletions(node.getData().getName(),args[1]))completions.add(node.getData().getName());//long boi
            return completions;
        }else if(args.length==3) {
            if (args[0].equalsIgnoreCase("getAsChild")) return completions;
        }
        return completions;
    }
}
