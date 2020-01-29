package net.lemonpickles.util;

import java.util.ArrayList;
import java.util.List;

public class Graph<T> {//directed acyclic graph with multiple roots
    private List<Node<T>> roots = new ArrayList<>();
    private List<Node<T>> leaves = new ArrayList<>();
    public List<Node<T>> getRoots(){return roots;}
    public List<Node<T>> getLeaves(){return leaves;}
    public List<Node<T>> getAllNodes(){
        if(roots.isEmpty())return new ArrayList<>();
        List<Node<T>> nodes = roots.get(0).getChildren(roots,new ArrayList<>(),-1);//loop through all roots with an empty list for nodes already found, because no nodes have been found yet
        nodes.addAll(roots);
        return nodes;
    }
    public void addNode(Node<T> node){
        leaves.add(node);
        for(Node<T> parent:node.getParents()){//for each parent node add this node as their child
            parent.addChild(node);
            leaves.remove(parent);//node with child is not a leaf
        }
        if(!node.hasParents())roots.add(node);
    }
    public void addNodes(List<Node<T>> nodes){
        for(Node<T> node:nodes){
            addNode(node);
        }
    }
}
