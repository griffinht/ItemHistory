package net.lemonpickles.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tree<T> {
    private List<Node<T>> roots;
    protected Tree(List<Node<T>> roots){
        this.roots = roots;
    }
    protected Tree(){roots = new ArrayList<>();}
    public List<Node<T>> getRoots(){return roots;}
    public List<Node<T>> getAllNodes(){
        List<Node<T>> nodes = loop(new ArrayList<>(),roots,0);
        nodes.addAll(roots);
        return nodes;
    }
    private List<Node<T>> loop(List<Node<T>> nodes, List<Node<T>> roots, int i){
        if(roots.size()>0) {
            Node<T> root = roots.get(i);
            List<Node<T>> children = root.getChildren(Collections.singletonList(root), nodes, -1);
            i++;
            if (i<roots.size()) loop(nodes, roots, i);
            return children;
        }else return new ArrayList<>();
    }
    public void addNode(Node<T> node){
        for(Node<T> parent:node.getParents()){
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
