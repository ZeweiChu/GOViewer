package org.geneontology;

import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GUI extends JFrame{
	JLabel label;
	JTextField in_tf;
	TextArea out_ta;
	JButton queryButton, parentsButton, childrenButton, currentButton, searchGeneButton;
	List<Node> nodelist;
	static Node currnode;
	List<Annotation> annotationlist;
	
	public GUI(List<Node> nodelist, List<Annotation> annotationlist){
		setLayout(new FlowLayout());
		
		label = new JLabel("Gene Code");
		add(label);
		
		in_tf = new JTextField(10);
		add(in_tf);
		
		
		queryButton = new JButton("Query");
		add(queryButton);
		
		parentsButton = new JButton("Parents");
		add(parentsButton);
		
		childrenButton = new JButton("Children");
		add(childrenButton);
		
		currentButton = new JButton("Current");
		add(currentButton);
		
		searchGeneButton = new JButton("Search Gene ID");
		add(searchGeneButton);
		
		QueryButtonPushedEvent queryButtonPushedEvent = new QueryButtonPushedEvent();
		queryButton.addActionListener(queryButtonPushedEvent);
		in_tf.addActionListener(queryButtonPushedEvent);
		
		CurrentButtonPushedEvent currentButtonPushedEvent = new CurrentButtonPushedEvent();
		currentButton.addActionListener(currentButtonPushedEvent);
	
		ParentsButtonPushedEvent parentsButtonPushedEvent = new ParentsButtonPushedEvent();
		parentsButton.addActionListener(parentsButtonPushedEvent);
	
		ChildrenButtonPushedEvent childrenButtonPushedEvent = new ChildrenButtonPushedEvent();
		childrenButton.addActionListener(childrenButtonPushedEvent);
	
		SearchGeneButtonPushedEvent searchGeneButtonPushedEvent = new SearchGeneButtonPushedEvent();
		searchGeneButton.addActionListener(searchGeneButtonPushedEvent);
		
		out_ta = new TextArea();
//		out_ta.setWrapStyleWord(true);
//        out_ta.setLineWrap(true);
        add(out_ta);

        out_ta.setText( "testing: " );
		
		this.nodelist = nodelist;
		this.annotationlist = annotationlist;
		GUI.currnode = nodelist.get(0);
	}
	
	public class QueryButtonPushedEvent implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String input = in_tf.getText().trim();
			System.out.println(input);
			if (input.equals("ids")){
				for (Node n: nodelist){
					System.out.println(n.data.id);
				}
			} else {
				for (Node n: nodelist){
					if (n.data.id.equals(input)){
						GUI.currnode = n;
						break;
					}
					if (n.data.name.equals(input)){
						GUI.currnode = n;
						break;
					}
				}
			}
			if (GUI.currnode.data.annotations.size() == 0){
				for (Annotation annotation: annotationlist){	
//					System.out.println(annotation.source);
//					System.out.println(annotation.items);
					if (annotation.items.contains(GUI.currnode.data.id)){
						GUI.currnode.data.annotations.add(annotation);
					}
				}
			}
			StringBuilder sb = new StringBuilder();
			sb.append(GUI.currnode.data.source);
			sb.append(System.lineSeparator());
			for (Annotation annotation: GUI.currnode.data.annotations){
				sb.append(annotation.source);
				sb.append(System.lineSeparator());
			}
			out_ta.setText(sb.toString());
		}
	}
	public class CurrentButtonPushedEvent implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			StringBuilder sb = new StringBuilder();
			sb.append(GUI.currnode.data.source);
			sb.append(System.lineSeparator());
			for (Annotation annotation: GUI.currnode.data.annotations){
				sb.append(annotation.source);
				sb.append(System.lineSeparator());
			}
			out_ta.setText(sb.toString());
		
		}
	}
	
	public class ParentsButtonPushedEvent implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("parents button pushed");
			System.out.println(nodelist.size());
			System.out.println(currnode.data.source);
			
			StringBuilder sb = new StringBuilder();
			if (currnode.parents.size() == 0){

				System.out.println(currnode.data.is_a);
				for (Node n: nodelist){
					if (currnode.data.is_a.contains(n.data.id)){
						System.out.println(n.data.id);
						currnode.parents.add(n);
					}
					if (currnode.data.part_of.contains(n.data.id)){
						System.out.println(n.data.id);
						currnode.parents.add(n);
					}
				}
			} 
			
			for (Node n: currnode.parents){
				sb.append(n.data.id);
				sb.append(System.lineSeparator());
			}		
			
			System.out.println(sb.toString());
			
			out_ta.setText(sb.toString());
			System.out.println("parents button pushed");
		}
	}
	public class ChildrenButtonPushedEvent implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("children button pushed");
			StringBuilder sb = new StringBuilder();
			if (currnode.children.size() == 0){
				for (Node n: nodelist){
					if (n.data.is_a.contains(currnode.data.id)){
						currnode.children.add(n);
					}
					if (n.data.part_of.contains(currnode.data.id)){
						currnode.children.add(n);
					}
				}
			} 
			
			for (Node n: currnode.children){
				sb.append(n.data.id);
				sb.append(System.lineSeparator());
			}		
			
			out_ta.setText(sb.toString());
			System.out.println("children button pushed");
		}
	}
	
	public class SearchGeneButtonPushedEvent implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String input = in_tf.getText().trim();
			System.out.println(input);
			for (Node n: nodelist){
				if (n.data.id.equals(input)){
					GUI.currnode = n;
					break;
				}
				if (n.data.name.equals(input)){
					GUI.currnode = n;
					break;
				}
			}
			if (GUI.currnode.data.annotations.size() == 0){
				for (Annotation annotation: annotationlist){	
					if (annotation.items.contains(GUI.currnode.data.id)){
						GUI.currnode.data.annotations.add(annotation);
					}
				}
			}
			StringBuilder sb = new StringBuilder();
			for (Annotation annotation: GUI.currnode.data.annotations){
				sb.append(annotation.items.get(1));
				sb.append(System.lineSeparator());
			}
			out_ta.setText(sb.toString());
		}
	}
}
