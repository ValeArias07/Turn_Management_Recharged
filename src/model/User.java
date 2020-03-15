package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Comparable <User>, Serializable{
	
	private String name;
	private String lastName;
	private String numDoc;
	private String phone;
	private String address;
	private String typeDoc;
	private boolean assistence;
	
	private ArrayList <Turn> turns;

	public User(String ptypeDoc, String pnumDoc, String pname, String pLastName, String pPhone, String pAddress) {
		typeDoc=ptypeDoc;
		name=pname;
		lastName=pLastName;
		numDoc=pnumDoc;
		phone=pPhone;
		address=pAddress;
		turns= new ArrayList<Turn>();
	}
	
	public void setFirst(char letter, String number,String type, double duration,int year, int month, int dayOfMonth, int hour, int minute, int seconds) {
		turns.add(new Turn(letter,number,type,duration,year,  month,  dayOfMonth, hour, minute, seconds));
	}
	
	/**
	 * This method search an Specific turn in the ArrayList Turns
	 * @param codeTurn is the turn that will be search
	 * @return m is the message that shows all the information related of the turn
	 */
	
	public String searchSpecificTurn(String codeTurn) {
		String m="-";
		Turn[] t=selectionSearch();
		
		for (int i = 0; i < t.length; i++) {
			if(t[i].getComplete().equals(codeTurn)) {
				m="The person "+name+" "+lastName+" with the ID "+ numDoc+ " had the turn "+codeTurn+" with the state "+turns.get(i).getState();
			}
		}
		return m;
	}
	
	/**
	 * This method just sort the turns using the selection method
	 * @return t is a Array with the turns sorted
	 */
	public Turn[] selectionSearch() {
		Turn[] t=new Turn[turns.size()];
		for (int i = 0; i < t.length; i++) {
			t[i]=turns.get(i);
		}
		for (int i=t.length-1; i>0;i--) {
			int maxValue = 0;
			for (int j=0; j<i; j++) {
				if (Integer.parseInt(t[j + 1].getNumber()) > Integer.parseInt(t[maxValue].getNumber())) {
					maxValue = j + 1;
				}
			}
			Turn value = t[maxValue];
			t[maxValue] = t[i];
			t[i] = value;
		}
		return t;
	}
	
	/**
	 * This method show the active turn in the arrayList Turns
	 * @return
	 */
	public String getActiveTurn() {
		String turnActive="-";
		if(turns.get(turns.size()-1).getState()==false) {
			turnActive=turns.get(turns.size()-1).getComplete();
		}
		return turnActive;
	}
	
	public Turn getLastTurn() {
		return turns.get(turns.size()-1);
	}
	
	public double getDurationLastTurn() {
		return turns.get(turns.size()-1).getDuration();
	}
	
	/**
	 * This method gives all the turns that had the person
	 * @return turnsAssigned is a message with all the information related the turns of the person
	 */
	public String[] getAllTurns() {
		String[] turnsAssigned= new String[turns.size()];
		for (int i = 0; i < turns.size(); i++) {
			turnsAssigned[i]="Code " +turns.get(i).getComplete() +"State: "+turns.get(i).getState();
		}
		return turnsAssigned;
	}
	
	/**
	 * This method show the number of the Turn
	 * @return number is the number of the active turn
	 */
	public int getTurnNumber() {
		int number=-1;
		if(turns.get(turns.size()-1).getState()==false) {
			number=Integer.parseInt(turns.get(turns.size()-1).getNumber());
		}
		return number;
	}
	
	/**
	 * This method gives the letter of the active turn 
	 * @return letter is the letter of the active turn
	 */
	public char getTurnLetter() {
		char letter='-';
		if(turns.get(turns.size()-1).getState()==false) {
				letter=turns.get(turns.size()-1).getLetter();
		}
		return letter;
	}
	
	/**
	 * This method add a new Turn to the ArrayList Turns
	 * @param letter is the letter that form the Turn
	 * @param number is the number that form the Turn
	 * @param type is the type of turn choose
	 * @param duration is the duration of the type of turn choose
	 * @param year is the year where the turn was assigned
	 * @param month is the month where the turn was assigned
	 * @param dayOfMonth is the day where the turn was assigned
	 * @param hour is the hour where the turn was assigned
	 * @param minute is the minute where the turn was assigned
	 * @param seconds is the seconds where the turn was assigned
	 */
	public void setTurns(char letter, String number, String type, double duration,int year, int month, int dayOfMonth, int hour, int minute, int seconds) {
		if(Integer.parseInt(number)<9) {
			number="0"+number;
			turns.add(new Turn(letter,number,type, duration, year,  month,  dayOfMonth, hour, minute, seconds));
		}
		else
		{
			turns.add(new Turn(letter,number,type,duration,year,  month,  dayOfMonth, hour, minute, seconds));
		}
	}
	
	/**
	 * This method just set the state of ATTENTED or NOT ATTENTED
	 * @param option if the option is false it means that the turn was not attented and if its true it means it was
	 */
	public void setState(boolean option) {
			assistence=option;
			turns.get(turns.size()-1).setState(true);
					/**for (int i = 0; i < turns.size() &&found==false; i++) {
					if(turns.get(i).getState()==false) {
						turns.get(i).setState(option);
						found=true;
					}
					*/
		}
	/**
	 * This method just sort the array turns using the bubble algorithm
	 * @return t[] is the array sorted by number of Turn
	 */
	public Turn[] bubbleSort() {
		Turn[] t= new Turn [turns.size()];
		for (int i = 0; i < turns.size(); i++) {
			t[i]=turns.get(i);
		}
		
		for(int i = 0; i < t.length - 1; i++){
            for(int j = 0; j < t.length - 1; j++) {
                if (Integer.parseInt(t[j].getNumber()) < Integer.parseInt(t[j+1].getNumber())){  	
                    Turn tmp = t[j+1];
                    t[j+1] = t[j];
                    t[j] = tmp;
                }
            }
		}
		return t;
	}
	
	/**
	 * This method show all the states of the turns that has a person
	 * @return m[] is a message with all the information about the turn
	 */
	public String[] allStates() {
		String[] m=new String[turns.size()];
		Turn[] t=bubbleSort();
		for (int i = 0; i < t.length; i++) {
			m[i]="Code: "+t[i].getComplete() +" Status: "+ t[i].getState();
		}
		return m;
	}
	/**
	 * This method shows if the User has been attended or not
	 * @return state is the state of the turn. If is false, it means that the turn has not benn attended
	 */
////////////////////////////////////////////////////////////////////////////////
	public boolean isAttent() {
		boolean state=true;
			if(turns.get(turns.size()-1).getState()==false) {
				state=false;
		}
		return state;
	}
//////////////////////////////////////////////////////////////////////////////// 
	
	
	public String getTypeDoc() {
		return typeDoc;
	}
	
	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	public String getNumDoc() {
		return numDoc;
	}
	
	public String getTypeAndDuration() {
		String m="";
		m=turns.get(turns.size()-1).getType()+" with duration "+ turns.get(turns.size()-1).getDuration();	
		return m;
	}
	
	public String getTurnInfo() {
		return turns.get(turns.size()-1).toString();
	}
	
	public String toString() {
		return "--User information--\n" + "Name:" +" "+ name+"\n"+"Last Name :" +" "+lastName+"\n"+ "Type of Document:" +" "+typeDoc+ "\n" +"Id number:" +" "+numDoc+"\n"+"Phone:" +" "+phone+"\n"+"Address:" +" "+address+"\n"+"_________________________"+"\n";
	}

	@Override
	public int compareTo(User u2) {
		int comp;
		if(numDoc.compareTo(u2.getNumDoc())<0) {
			comp=-1;
		}else if(numDoc.compareTo(u2.getNumDoc())>0) {
			comp=1;
		}else {
			comp=0;
		}
		return comp;
	}

}
