package ui;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import model.Admin;
import model.LocalDate;
import model.LocalHour;
import model.TypeDocuments;
import CustomExceptions.DocumentExistException;
import CustomExceptions.NoTurnYetException;
import CustomExceptions.NotFoundException;
import CustomExceptions.ObligatoryFieldsException;

public class Main {
	
	public Scanner lectorL;
	public	Scanner lectorN;
	public Admin admin;
	public boolean usersGenerated;
	
	public Main() throws IOException {
		lectorN= new Scanner(System.in);
		lectorL= new Scanner(System.in);
		admin= new Admin();
		usersGenerated=false;
	}
	
	public static void main (String args[]) throws IOException, DocumentExistException, ObligatoryFieldsException, NotFoundException {
		Main main= new Main();
		main.menu();
	}
	
	public void menu() throws IOException, DocumentExistException, ObligatoryFieldsException, NotFoundException {
		
		System.out.println("Welcome the the Turn Management. Enjoy the program");
		boolean exit=false;
		boolean usersGenerated=false;
		setTypeTurns();
			while(!exit) {
				System.out.println("---Current Date and Time---");
				dateAndHour();
				System.out.println("---------------------------");
				System.out.println("Please, choose an option:"
						+ "\n 1. Add a new type of Turn"
						+ "\n 2. Add a person (solved)"
						+ "\n 3. Generate users (solved)"
						+ "\n 4. Asign a turn"
						+ "\n 5. Attent all the turns till now"
						+ "\n 6. Generate turns for a defineted time"
						+ "\n ---REPORTS----"
						+ "\n 7. Generate a report with all the turns of a specific Person"
						+ "\n 8. Generate a report with all the persons that had a specific Turn"
						+ "\n 9. Save information"
						+ "\n 10. Exit");
				
			int choice=lectorN.nextInt();
			switch(choice) {
				case(1):
					addTypeTurn();
					break;
				case(2):
					addUser();
					break;
				case(3):
					generateUsers();
					
					break;
				case(4):
					assignTurn();
					break;
				case(5):
					attentTurnsTillNow();
					break;
				case(6):
					generateTurnsWithTime();
					break;
				case(7):
					generateReportSpecificPerson();
					break;
				case(8):
					generateReportSpecificTurn();
					break;
				case(9):
					saveInformation();
					break;
				case(10):
					exit=true;
					break;
				default:
					System.out.println("Write a correct option.");
				}
			}
		}
	
	
	public void addTypeTurn() {
		
	}
	public void generateUsers() throws IOException, DocumentExistException, ObligatoryFieldsException {
		System.out.println("Write the amount of people to generate");
		int numPersons=lectorN.nextInt();
		long timeInitial= System.currentTimeMillis();
		admin.generateUsers(numPersons);
		System.out.println(admin.showPeople());
		long finalTime=System.currentTimeMillis()-timeInitial;
		System.out.println("Time of Ejecution :" + finalTime);
		setGeneratedUsers(true);
	}
	public void addUser() {
		boolean sucess=false;
		while(!sucess) {
		String typeDoc=defineTypeDoc();
		
		System.out.println("Write the person's complete name");
		String names=lectorL.nextLine();
		
		System.out.println("Write the person's last name");
		String lastNames=lectorL.nextLine();
		
		System.out.println("Write the person's document number");
		String numDoc=lectorL.nextLine();
		
		System.out.println("Write the person's phone");
		String phone=lectorL.nextLine();
		
		System.out.println("Write the person's address");
		String address=lectorL.nextLine();


		try {
			if(numDoc.equalsIgnoreCase(" ") || names.equalsIgnoreCase(" ") || lastNames.equalsIgnoreCase(" ")) {
				throw new ObligatoryFieldsException();	
			}
			else {
				admin.addUser(typeDoc, numDoc, names, lastNames, phone, address);
				sucess=true;
			}
		}
		catch(ObligatoryFieldsException o) {
			System.out.println(o.getMessage());
			}
		catch(DocumentExistException d) {
			System.out.println(d.getMessage());
			}
		}
	}
	public void assignTurn() throws NotFoundException, IOException, DocumentExistException, ObligatoryFieldsException {
		boolean sucessful=false;
		while(!sucessful) {
			String numDoc="0";
			System.out.println("Choose the type of turn");
			System.out.println("1. Assign turn a specific person \n2. Assign turn random");
			int option=lectorN.nextInt();
			if(option==1) {
				System.out.println("Write the number of Document");
				numDoc=lectorL.nextLine();
			}else {
				if(usersGenerated!=true) {
					System.out.println("Ok, you haven't generate the users yet. Lets generate them");
					generateUsers();
				}
			}
			try {
			System.out.println(admin.registTurn(numDoc));
			sucessful=true;
			}
			catch(NotFoundException nf) {
				System.out.println(nf.getMessage());
				}
		}
	}
	public void attentTurnsTillNow() {
		
	}
	public void generateTurnsWithTime() {
		
	}
	public void generateReportSpecificPerson() throws NotFoundException, IOException {
		boolean good=false;
		int option=0;
		System.out.println("Write the document of the Person");
		String numDoc=lectorL.nextLine();
		while(!good) {
		System.out.println("1. Read the report in Screen \n2. Save the report in a text file ");
		option=lectorN.nextInt();
		good=(option==1 || option==2)?true:false;
		}
		try{
		System.out.println(admin.generateReportSpecificPerson(numDoc, option));
		}
		catch(NotFoundException nf) {
			System.out.println(nf.getMessage());
		}
	}
	public void generateReportSpecificTurn() {
		
	}
	public void saveInformation() {
		
	}

	//////COMPLETEMENT/////// 
	
	public void dateAndHour() {
		Date current= new Date();
		LocalDate currentDate= new LocalDate((current.getYear() +1900),(current.getMonth()+1),(current.getDay()+1));
		LocalHour currentHour= new LocalHour((current.getHours()+2),current.getMinutes(),current.getSeconds());
		System.out.println("    " + currentDate.getCompleteDate() + " " +currentHour.getCompleteHour());
		
	}
	
	public void setTypeTurns() {
	System.out.println("Write the number of types of turns that you want to create");
	int numOptions=lectorN.nextInt();
	String[] typeTurns= new String[numOptions];
	
	for (int i = 0; i < typeTurns.length; i++) {
		System.out.println("Write the names of the types");
		typeTurns[i]=lectorL.nextLine();
	}
	double[] duration= new  double[numOptions];
	for (int i = 0; i < typeTurns.length; i++) {
		System.out.println("write the time for the Turn " + typeTurns[i]);
		double time=lectorN.nextDouble();
	}
	}
	
	public String defineTypeDoc() {
		boolean correctOption=false;
		int option=0;
		String typeDoc="";
		while(!correctOption) {
		System.out.println("Select the type of Document"
				+ "\n1."+ TypeDocuments.ACARD.getType()
				+ "\n2."+ TypeDocuments.ICARD.getType()
				+ "\n3."+ TypeDocuments.CIVILREGIST.getType()
				+ "\n4."+ TypeDocuments.PASSPORT.getType()
				+ "\n5."+ TypeDocuments.FOREIGNCARD.getType());
		option=lectorN.nextInt();
		if(option==1 || option==2 || option==3 || option==4 || option==5)
			correctOption=true;
		}
	
		switch(option){
			case(1):
				typeDoc=(TypeDocuments.ACARD.getType());
				break;
			case(2):
				typeDoc=(TypeDocuments.ICARD.getType());
				break;
			case(3):
				typeDoc=(TypeDocuments.CIVILREGIST.getType());
				break;
			case(4):
				typeDoc=(TypeDocuments.PASSPORT.getType());
				break;
			case(5):
				typeDoc=(TypeDocuments.FOREIGNCARD.getType());
			break;
		}
	return typeDoc;	
	}
	public void setGeneratedUsers(boolean state){
		usersGenerated=state;
	}

	}
