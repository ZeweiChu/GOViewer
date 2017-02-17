package org.geneontology;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

public class GeneOntology {
	public static void main(String[] args) {
		
		List<Node> nodelist = new ArrayList<Node>();
		//start reading the obo files
		try(BufferedReader br = new BufferedReader(new FileReader("resources/go.obo.txt"))) {
		    //Tree tree = new Tree(null);
			String line = br.readLine();
		    String key = "";
		    String value = "";
		    
		    while (line != null) {
		    	
		    	if (line.contains("[Term]")){
		    		StringBuilder sb = new StringBuilder();
		    		Term term = new Term();
		    		sb.append(line);
		    		sb.append(System.lineSeparator());
		    		line=br.readLine();
		    		while (line != null){
		    			if (line.contains("[Term]") || line.contains("[Typedef]")) break;
		    			sb.append(line);
				        sb.append(System.lineSeparator());
				        
				        if (line.contains(":")){
			    			key = line.substring(0, line.indexOf(':'));
//			    			System.out.println(key);
					        line = line.substring(line.indexOf(":")+1);
//					        System.out.println(line);
					        while (line.charAt(0) == ' ') line = line.substring(1);
//					        System.out.println(line);
					        
//					        System.out.println(value);
//					        System.out.println(line);
					        
					        if (key.equals("id")){
					        	term.id = line.trim();
//					        	System.out.println(value);
					        } else if (key.equals("name")){
					        	term.name = line.trim();
					        } else if (key.equals("namespace")){
					        	term.namespace = line.trim();
					        } else if (key.equals("is_a")){
					        	if (line.indexOf(" ") != -1){
							        value = line.substring(0, line.indexOf(" "));
						        } else {
						        	value = line;
						        }
					        	term.is_a.add(value.trim());
//					        	System.out.println(value);
					        } else if (key.equals("relationship")) {
					        	if (line.indexOf(" ") != -1){
							        value = line.substring(0, line.indexOf(" "));
							        line = line.substring(line.indexOf(" ")+1);
						        } else {
						        	value = line;
						        }
					        	if (value.equals("part_of")) {
						        	value = line.substring(0, line.indexOf(" "));
	//					        	System.out.println(value);
						        	term.part_of.add(value.trim());
					        	}
					        }
				        }				        
				    	line = br.readLine();
		    		}
		    		term.source = sb.toString();
		    		nodelist.add(new Node(term));
		    	} else {
		    		line = br.readLine();
		    	}
		    }
		    //String everything = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		List<Annotation> annotationlist = new ArrayList<Annotation>();
		
		try(BufferedReader br = new BufferedReader(new FileReader("resources/gene_association.ecocyc"))) {
			String line = br.readLine();
		    line = line.trim();
		    while (line != null) {
		    		Annotation annotation = new Annotation();
		    		annotation.source = line;
		    		annotation.items = Arrays.asList(line.split(" |\t|\n"));
		    		for (int i = 0; i < annotation.items.size(); ++i){
		    			annotation.items.set(i, annotation.items.get(i).trim());
		    		}
		    		annotationlist.add(annotation);
		    		line=br.readLine();
		    		
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
//		for (Node n: nodelist){
//			System.out.println(n.data.source);
//			System.out.println(n.data.id);
//			System.out.println(n.data.is_a);
//			System.out.println(n.data.part_of);
//		}
//		Tree t = buildTree(nodelist);
//		
//		
//		for (Node n: t.roots){
//			System.out.println(n.data.source);
//		}
		

		
		//startQueryServer(nodelist);
		
		
		//start the query gui
		GUI gui = new GUI(nodelist, annotationlist);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(500,400);
		gui.setTitle("Gene Ontology");
		gui.setVisible(true);
	}
	
	public static void startQueryServer(List<Node> nodelist){
		Scanner sc = new Scanner(System.in);
		
		sc.close();
	}
	
	public static Tree buildTree(List<Node> nodelist){
		for (Node n: nodelist){
			for (Node pn: nodelist){
				if (n.data.is_a.contains(pn.data.id)){
//					System.out.println(n.data.is_a);
//					System.out.println(pn.data.id);
					pn.appendChild(n);
					n.appendParent(pn);
				}
				if (n.data.part_of.contains(pn.data.id)){
					pn.appendChild(n);
					n.appendParent(pn);
				}
			}
		}
		
		Tree tree = new Tree();
		
		for (Node n: nodelist){
//			System.out.println(n.parents.size());
			if (n.parents.size() == 0){
//				System.out.println(n.data.source);
				tree.roots.add(n);
			}
		}
		
		return tree;
	}
	

}
