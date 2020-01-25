package net.lemonpickles.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node<T>{
    private T data;
    private List<Node<T>> parents;
    private List<Node<T>> children = new ArrayList<>();
    public Node(){}
    public Node(T data){
        this.data = data;
        parents = new ArrayList<>();
    }
    public Node(T data, List<Node<T>> parents){
        this.data = data;
        this.parents = parents;
    }
    public Node(T data, List<Node<T>> parents, List<Node<T>> children){
        this.parents = parents;
        if(children==null)children = new ArrayList<>();
        this.children = children;
        this.data = data;
    }

    public T getData(){return data;}
    public List<Node<T>> getChildren(){return children;}
    public List<Node<T>> getChildren(int level){return getChildren(new ArrayList<>(Collections.singletonList(this)),new ArrayList<>(),level);}
    public List<Node<T>> getChildren(List<Node<T>> find, List<Node<T>> found, int level){
        if(level!=0){
            for(Node<T> node:find){
                if(node.hasChild()) {
                    for (Node<T> child : node.getChildren()) {
                        if (!found.contains(child)) {
                            found.add(child);
                        }
                    }
                    getChildren(node.getChildren(),found,level--);
                }
            }
        }
        return found;
    }
    public boolean hasChild(){return children.size()>0;}
    void addChild(Node<T> node){children.add(node);}
    void addChildren(List<Node<T>> nodes){children.addAll(nodes);}
    public List<Node<T>> getParents(){return parents;}
    public boolean hasParent(){return parents.size()>0;}
    void addParent(Node<T> node){parents.add(node);}
    void addParent(List<Node<T>> nodes){parents.addAll(nodes);}
}
