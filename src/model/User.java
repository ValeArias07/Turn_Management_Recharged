package model;

import java.util.ArrayList;

public class User {
	
	private String name;
	private String lastName;
	private String numDoc;
	private String phone;
	private String address;
	private String typeDoc;
	
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
	
	public String searchSpecificTurn(String codeTurn) {
		String m="-";
		for (int i = 0; i < turns.size(); i++) {
			if(turns.get(i).getComplete().equals(codeTurn)) {
				m="The person "+name+" "+lastName+" with the ID "+ numDoc+ " had the turn "+codeTurn+" with the state "+turns.get(i).getState();
			}
		}
		return m;
	}
	
	public String getActiveTurn() {
		String turnActive="";
		boolean found=false;
		for (int i = 0; i < turns.size(); i++) {
			if(turns.get(i).getState().equals(Turn.NOTD) && found==false){
				turnActive=turns.get(i).getComplete();
				found=true;
			}
		}
		return turnActive;
	}
	
	public String[] getAllTurns() {
		String[] turnsAssigned= new String[turns.size()];
		for (int i = 0; i < turns.size(); i++) {
			turnsAssigned[i]="Code " +turns.get(i).getComplete() +"State: "+turns.get(i).getState();
		}
		return turnsAssigned;
	}
	
	public int getTurnNumber() {
		boolean found=false;
		int number=-1;
		for (int i = 0; i < turns.size() &&found==false; i++) {
			if(turns.get(i).getState().equals(Turn.NOTD)) {
				number=Integer.parseInt(turns.get(i).getNumber());
				found=true;
			}
		}
		return number;
	}
	
	public char getTurnLetter() {
		boolean found=false;
		char letter='-';
		for (int i = 0; i < turns.size() &&found==false; i++) {
			if(turns.get(i).getState().equals(Turn.NOTD)) {
				letter=turns.get(i).getLetter();
				found=true;
			}
		}
		return letter;
	}

	public void setTurns(char letter, String number) {
		if(Integer.parseInt(number)<9) {
			number="0"+number;
			turns.add(new Turn(letter,number));
		}
		else
		{
			turns.add(new Turn(letter,number));
		}
	}
	public void setFirst(char letter, String number) {
		turns.add(new Turn(letter,number));
	}
	
	public void setState(String option) {
		boolean found=false;
		for (int i = 0; i < turns.size() &&found==false; i++) {
			if(turns.get(i).getState().equals(Turn.NOTD)) {
				turns.get(i).setState(option);
				found=true;
			}
		}
	}
	
	public String[] allStates() {
		String[] m=new String[turns.size()];
		for (int i = 0; i < turns.size(); i++) {
			m[i]="Code: "+turns.get(i).getComplete() +" Status: "+ turns.get(i).getState();
		}
		return m;
	}
	public String isAttent() {
		boolean found=false;
		String m="";
		for (int i = 0; i < turns.size() &&found==false; i++) {
			if(turns.get(i).getState().equals(Turn.NOTD)) {
				m=Turn.NOTD;
				found=true;
			}
		}
		return m;
	}
	
	public String showTurn() {
		boolean found=false;
		String t="";
		for (int i = 0; i < turns.size() &&found==false; i++) {
			if(turns.get(i).getState().equals(Turn.NOTD)) {
				t=turns.get(i).getComplete();
				found=true;
			}
		}
		return t;
	}
	
	public String toString() {
		return "--User information--\n" + "Name:" +" "+ name+"\n"+"Last Name :" +" "+lastName+"\n"+ "Type of Document:" +" "+typeDoc+ "\n" +"Id number:" +" "+numDoc+"\n"+"Phone:" +" "+phone+"\n"+"Address:" +" "+address+"\n"+"_________________________"+"\n";
	}

}
