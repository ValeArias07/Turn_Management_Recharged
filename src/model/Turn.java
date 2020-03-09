package model;

import java.util.ArrayList;

public class Turn implements Comparable <Turn>{
	private String type;
	private double duration;
	private char letter;
	private String number;
	private String state;

	public static String AS="ASSITED";
	public static String NOAS="NOT ASSISTED";
	public static String NOTD="NOT ATTENTED YET";
	
	public Turn(char letter_, String number_,String typep, double durationp) {
		letter=letter_;
		number=number_;
		state=NOTD;
		type=typep;
		duration=durationp;
	}
	
	public Turn(String name, double durationp) {
		type=name;
		duration=durationp;
	}
	
	public String getType() {
		return type;
	}
	
	public double getDuration() {
		return duration;
	}

	public char getLetter() {
		return letter;
	}
	public String getNumber() {
		return number;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setState(String state) {
		this.state = state;
	}
	public String getState() {
		return state;
	}
	
	public String getComplete() {
		return letter+number;
	}	

	
	public void setType(int option) {
		
	}
	
	public void addType(String name, double duration) {
		
	}
	
	
	public int compareTo(Turn o) {
		int comp;
		if(Double.compare(duration, o.getDuration())>0) {
			comp=1;
		}else if(Double.compare(duration, o.getDuration())<0) {
			comp=-1;
		}else {
			comp=0;
		}
		return comp;
	}
}
