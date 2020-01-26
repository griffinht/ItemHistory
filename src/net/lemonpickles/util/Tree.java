package net.lemonpickles.util;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> {
    private List<Node<T>> roots;
    protected Tree(List<Node<T>> roots){this.roots = roots;}//Tree class can be initialized with or without existing roots
    protected Tree(){roots = new ArrayList<>();}
    public List<Node<T>> getRoots(){return roots;}
    public List<Node<T>> getAllNodes(){
        List<Node<T>> nodes = roots.get(0).getChildren(roots,new ArrayList<>(),-1);//loop through all roots with an empty list for nodes already found, because no nodes have been found yet
        nodes.addAll(roots);
        return nodes;
    }
    public void addNode(Node<T> node){
        for(Node<T> parent:node.getParents()){//for each parent node add this node as their child
            parent.addChild(node);
        }
        if(!node.hasParent())roots.add(node);
    }
    public void addNodes(List<Node<T>> nodes){
        for(Node<T> node:nodes){
            addNode(node);
        }
    }
}
