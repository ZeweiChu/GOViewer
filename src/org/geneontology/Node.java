package org.geneontology;

import java.util.List;
import java.util.ArrayList;

public class Node {
	public Term data;
    public List<Node> parents;
    public List<Node> children;

    public Node(){
    	this.data = new Term();
    	this.parents = new ArrayList<Node>();
    	this.children = new ArrayList<Node>();
    }
    
    public Node(Term data) {
    	this.data = data;
    	this.parents = new ArrayList<Node>();
        this.children = new ArrayList<Node>();
    }
    
    public Node appendChild(Node child){
    	this.children.add(child);
    	return this;
    }
    
    public Node appendChildren(List<Node> children){
    	this.children.addAll(children);
    	return this;
    }
    
    public Node appendParent(Node parent){
    	this.parents.add(parent);
    	return this;
    }
}