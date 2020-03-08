package model;

public class Turn {
	private char letter;
	private String number;
	private String type;
	private String state;
	
	public static String AS="ASSITED";
	public static String NOAS="NOT ASSISTED";
	public static String NOTD="NOT DEFINEAD";
	
	public Turn(char letter_, String number_) {
		letter=letter_;
		number=number_;
		state=NOTD;
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

	
	
}
