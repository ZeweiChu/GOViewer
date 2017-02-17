package org.geneontology;

import java.util.ArrayList;
import java.util.List;

public class Term {
	public Term(){
		this.is_a = new ArrayList<String>();
		this.part_of = new ArrayList<String>();
		this.source="";
		this.id="";
		this.name="";
		this.namespace="";
		this.annotations = new ArrayList<Annotation>();
	}
	public String source;
	public String id;
	public String name;
	public String namespace;
	public List<String> is_a;
	public List<String> part_of;
	public List<Annotation> annotations;
}
