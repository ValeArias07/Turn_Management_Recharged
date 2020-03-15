package model;

import java.io.Serializable;

public class TypeTurn implements Comparable <TypeTurn>, Serializable{
	private String type;
	private double duration;
	
	public TypeTurn(String name, double durationp) {
		type=name;
		duration=durationp;
	}
	
	public String getType() {
		return type;
	}
	
	public double getDuration() {
		return duration;
	}
	
	public int compareTo(TypeTurn o) {
		int comp;
		if(Double.compare(getDuration(), o.getDuration())>0) {
			comp=1;
		}else if(Double.compare(getDuration(), o.getDuration())<0) {
			comp=-1;
		}else {
			comp=0;
		}
		return comp;
	}
}
