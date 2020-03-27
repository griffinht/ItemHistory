package net.stzups.itemhistory;

import net.stzups.util.FileManagement;
import net.stzups.util.Node;
import org.bukkit.configuration.MemorySection;

import java.util.*;

public class TreeList extends FileManagement {
    private ItemHistory plugin;

    TreeList(ItemHistory plugin) {
        super(plugin, "nodes.yml");
        this.plugin = plugin;
        if (config.get("nodes") == null) config.createSection("nodes");
        super.save();
    }

    public void save() {
        long start = System.nanoTime();
        List<Node<TrackedItem>> nodes = plugin.trackedItemTree.getAllNodes();
        config.set("nodes", new HashMap<>());
        for (Node<TrackedItem> node : nodes) {
            Map<String, Object> value = new HashMap<>();
            value.put("trackedItem", node.getData());
            List<Integer> parents = new ArrayList<>();
            for (Node<TrackedItem> parent : node.getParents()) {
                parents.add(parent.hashCode());
            }
            value.put("parents", parents);
            config.set("nodes." + node.hashCode(), value);
        }
        super.save();
        plugin.logger.info("Saved " + nodes.size() + " nodes to " + file.getName() + " in " + ((System.nanoTime() - start) / 1000000) + "ms");
    }

    public void load() {
        long start = System.nanoTime();
        super.load();
        Map<Integer, Node<TrackedItem>> integerNodeMap = new HashMap<>();
        Map<Integer, List<Integer>> hashCodeIntegerToParentsIntegerList = new HashMap<>();
        for (Map.Entry<String, Object> entry1 : config.getValues(false).entrySet()) {
            if (entry1.getValue() instanceof MemorySection) {
                MemorySection memorySection1 = (MemorySection) entry1.getValue();
                for (Map.Entry<String, Object> entry2 : memorySection1.getValues(false).entrySet()) {
                    if (entry2.getValue() instanceof MemorySection) {
                        MemorySection memorySection2 = (MemorySection) entry2.getValue();
                        Object object = memorySection2.get("trackedItem");
                        if (object instanceof TrackedItem) {
                            TrackedItem trackedItem = (TrackedItem) object;
                            List<Integer> parents = memorySection2.getIntegerList("parents");
                            int hashCode = Integer.parseInt(memorySection2.getName());
                            integerNodeMap.put(hashCode, new Node<>(trackedItem));
                            hashCodeIntegerToParentsIntegerList.put(hashCode, parents);
                        }
                    }
                }
            }
        }
        System.out.println(hashCodeIntegerToParentsIntegerList);
        for (Map.Entry<Integer, List<Integer>> entry : hashCodeIntegerToParentsIntegerList.entrySet()) {
            Node<TrackedItem> trackedItemNode = integerNodeMap.get(entry.getKey());
            for (Integer parent : entry.getValue()) {
                trackedItemNode.addParent(integerNodeMap.get(parent));
            }
        }
        plugin.trackedItemTree.addNodes(new ArrayList<>(integerNodeMap.values()));//tells parents their children and adds roots
        plugin.logger.info("Loaded " + integerNodeMap.size() + " nodes and built tree with " + plugin.trackedItemTree.getRoots().size() + " roots in " + ((System.nanoTime() - start) / 1000000) + "ms");
    }
}
