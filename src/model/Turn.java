package model;

import java.util.ArrayList;

public class Turn {

	private char letter;
	private String number;
	private boolean state;
	private TypeTurn typeT;
	private LocalDateSystem localDateT;
	
	
	public Turn(char letter_, String number_,String type, double duration,int year, int month, int dayOfMonth, int hour, int minute, int seconds) {
		letter=letter_;
		number=number_;
		state=false;
		typeT= new TypeTurn(type,duration);
		localDateT= new LocalDateSystem(year, month, dayOfMonth, hour, minute, seconds);
		
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
	public char getLetter() {
		return letter;
	}
	public String getNumber() {
		return number;
	}
	
	public boolean getState() {
		return state;
	}
	
	public String getComplete() {
		return letter+number;
	}		
	
	public double getDuration() {
		return typeT.getDuration();
	}
	
	public String getType() {
		return typeT.getType();
	}
	
	public LocalDateSystem getDateOfCreation() {
		return localDateT;
	}
	
	public String getDateValue() {
		return localDateT.getCompleteDate()+" "+localDateT.getCompleteHour();
	/////localDateT.getYear()+"/"+localDateT.getMonth()+"/"+localDateT.getDay()+"  "+localDateT.getHour()+":"+localDateT.getMinutes()+":"+localDateT.getSeconds();
	}
	public String toString() {
		return "Turn: "+letter+number+" type"+" "+typeT.getType()+" with a duration: "+typeT.getDuration()+" \nTurn Date:"+"["+getDateValue()+"]";
	}


}
