package ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import model.Admin;
import model.LocalDateSystem;
import model.TypeDocuments;
import CustomExceptions.DocumentExistException;
import CustomExceptions.NoTurnYetException;
import CustomExceptions.NotFoundException;
import CustomExceptions.ObligatoryFieldsException;
import CustomExceptions.OnceTurnException;
import CustomExceptions.TurnHadNoAssigned;
import CustomExceptions.TypesNotCreatedException;
import CustomExceptions.TypesRepeatedException;


public class Main {
	
	public Scanner lectorL;
	public	Scanner lectorN;
	public Admin admin;
	public boolean usersGenerated;
	public int numberOfGenerated;
	
	public Main() throws IOException, ClassNotFoundException {
		lectorN= new Scanner(System.in);
		lectorL= new Scanner(System.in);
		
		admin= new Admin();
		usersGenerated=false;
		numberOfGenerated=0;
	}
	
	public static void main (String args[]) throws IOException, DocumentExistException, ObligatoryFieldsException, NotFoundException, TypesNotCreatedException, ClassNotFoundException {
		Main main= new Main();
		main.menu();
		
	}
	
	public void menu() throws IOException, DocumentExistException, ObligatoryFieldsException, NotFoundException, TypesNotCreatedException, ClassNotFoundException {
		System.out.println("Welcome the the Turn Management. Enjoy the program");
		System.out.println("Please, select the option for the system of Date: \n1. Set Date automatically \n2. Set Dat manually");
		int option=lectorN.nextInt();
		updateDate(option,0);
		boolean exit=false;
			while(!exit) {
				admin.setNewComputerDate();
				admin.updateDateSystem();
				System.out.println("---Current Date and Time---");
				System.out.println("    "+admin.getCompleteDate());
				System.out.println("---------------------------");
				System.out.println("Please, choose an option:"
						+ "\n 1. Update the date"
						+ "\n 2. Add a new type of Turn"
						+ "\n 3. Add a person"
						+ "\n 4. Generate users"
						+ "\n 5. Asign a turn"
						+ "\n 6. Attent all the turns till now"
						+ "\n ---REPORTS----"
						+ "\n 7. Generate a report with all the turns of a specific Person (solved)"
						+ "\n 8. Generate a report with all the persons that had a specific Turn (solved)"
						+ "\n 9. Show users in System"
						+ "\n 10. Exit");
				
			int choice=lectorN.nextInt();
			switch(choice) {
				case(1):
					updateDate(2,1);
					break;
				case(2):
					addTypeTurn();
					break;
				case(3):
					addUser();
					break;
				case(4):
					generateUsers();
					break;
				case(5):
					assignTurn();
					break;
				case(6):
					attentTurnsTillNow();
					break;
				case(7):
					generateReportSpecificPerson();
					break;
				case(8):
					generateReportSpecificTurn();
					break;
				case(9):
					System.out.println(admin.showPeople());
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
		System.out.println("Write the amount of types to create");
		int numT=lectorN.nextInt();
		for (int i = 0; i < numT; i++) {
			System.out.println("Write the name of the Turn");
			String name=lectorL.nextLine();	
			System.out.println("Write the duration of the Turn");
			double duration=lectorN.nextDouble();
			try {
			System.out.println(admin.addTypeOfTurn(name,duration));
			}
			catch(TypesRepeatedException tr){
				System.out.println(tr.getMessage());
				}
		}
	}
	
	public void generateUsers() throws IOException, DocumentExistException, ObligatoryFieldsException {

		System.out.println("Write the amount of people to generate");
		int numPersons=lectorN.nextInt();
		long timeInitial= System.currentTimeMillis();
		long finalTime=System.currentTimeMillis()-timeInitial;
		System.out.println(admin.generateUsers(numPersons,numberOfGenerated));
		System.out.println("Time of Ejecution :" + finalTime);
		setGeneratedUsers(true);
		numberOfGenerated++;
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
				long timeInitial= System.currentTimeMillis();
				admin.addUser(typeDoc, numDoc, names, lastNames, phone, address);
				sucess=true;
				long finalTime=System.currentTimeMillis()-timeInitial;
				System.out.println("Time of Ejecution :" + finalTime);
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
	
	public void assignTurn() throws NotFoundException, IOException, DocumentExistException, ObligatoryFieldsException, TypesNotCreatedException {
		
		boolean sucessful=false;
		boolean typesExist=false;

		while(!sucessful) {
			String numDoc="0";
			int optCType=0;
			System.out.println("Choose one option: \n1.Choose type of Turn  \n2.Random type");
			int optType=lectorN.nextInt();
			
		while(!typesExist) {
			if(optType==1) {
				try {
					System.out.println("Choose the types\n" + admin.showOrderTypes());
					optCType=lectorN.nextInt();
					typesExist=true;
				}
				catch(TypesNotCreatedException tnc) {
					System.out.println(tnc.getMessage()+tnc.getExampleType());
					addTypeTurn();
				}
			}else {
				try {
					admin.showOrderTypes();
					typesExist=true;
				}
				catch(TypesNotCreatedException tnc) {
					System.out.println(tnc.getMessage()+tnc.getExampleType());
					addTypeTurn();
				}
			}
		}
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
			long timeInitial= System.currentTimeMillis();
			try {
			System.out.println("###############\n" + admin.registTurn(numDoc,optType,optCType)+"\n###############");
			}
			catch(OnceTurnException ot) {
				ot.getMessage();
			}
			finally {
			sucessful=true;
			long finalTime=System.currentTimeMillis()-timeInitial;
			System.out.println("Time of Ejecution :" + finalTime);
				}
			}
			catch(NotFoundException nf) {
				System.out.println(nf.getMessage());
				}
			}
		}
	
	public void attentTurnsTillNow() {
		System.out.println(admin.AttendTillNow());
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
		long timeInitial= System.currentTimeMillis();
			System.out.println(admin.generateReportSpecificPerson(numDoc, option));
		long finalTime=System.currentTimeMillis()-timeInitial;
		System.out.println("Time of Ejecution :" + finalTime);
		}
		catch(NotFoundException nf) {
			System.out.println(nf.getMessage());
		}
	}
	
	public void generateReportSpecificTurn() throws IOException {
		boolean good=false;
		int option=0;
		System.out.println("Write the code of the Turn");
		String codeTurn=lectorL.nextLine();
		while(!good) {
			System.out.println("1. Read the report in Screen \n2. Save the report in a text file ");
			option=lectorN.nextInt();
			good=(option==1 || option==2)?true:false;
			}
		try {
		long timeInitial= System.currentTimeMillis();
			System.out.println(admin.generateReportSpecificTurn(codeTurn, option));
		long finalTime=System.currentTimeMillis()-timeInitial;
		System.out.println("Time of Ejecution :" + finalTime);
		}
		catch(TurnHadNoAssigned t) {
			System.out.println(t.getMessage());
		}
	}
	
	
	public void saveOrLoadInformation() throws IOException,ClassNotFoundException {
		System.out.println("Write \n1. Save Information \n2. Load Information");
		int opt=lectorN.nextInt();
		if(opt==1) {
		admin.saveInformation();
		System.out.println("Information saved succesfuly in the root "+admin.SAVE_ROOT);
		}else if(opt==2) {
			admin.loadInformation();
			System.out.println("Information loaded succesfuly from the root "+admin.SAVE_ROOT);
		}
	}
	
	public void updateDate(int option, int type){
		if(option==1) {
			admin.setDate();
		}else {
		System.out.println("Remember, you can only change the date of one superior. \nPlease, write the date YEAR/MONTH/DAY: ");
		String date=lectorL.nextLine();
		
		int year=Integer.parseInt(date.split("/")[0]);
		int month=Integer.parseInt(date.split("/")[1]);
		int day=Integer.parseInt(date.split("/")[2]);
		
		System.out.println("Now, write the hour with the format HOUR:MINUTE:SECOND");
		String time=lectorL.nextLine();
		int hour=Integer.parseInt(time.split(":")[0]);
		int minutes=Integer.parseInt(time.split(":")[1]);
		int seconds=Integer.parseInt(time.split(":")[2]);
		
		if(type==0) {
			admin.setDate(year, month, day, hour, minutes, seconds);
			}else{
				System.out.println(admin.updateDate(year, month, day, hour, minutes, seconds));
			}
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
	
	public void save() {
        File file = new File(Admin.SAVE_ROOT);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            FileOutputStream adminFOut = new FileOutputStream(file);
            ObjectOutputStream adminObjOut = new ObjectOutputStream(adminFOut);
            adminObjOut.writeObject(Admin.SAVE_ROOT);
            adminObjOut.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void load() throws ClassNotFoundException {
        File file = new File(Admin.SAVE_ROOT);
        if(file.exists()) {
            try {
                FileInputStream adminFIn = new FileInputStream(Admin.SAVE_ROOT);
                ObjectInputStream adminObjIn;
                adminObjIn = new ObjectInputStream(adminFIn);
                admin = (Admin)adminObjIn.readObject();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

	
	public void setGeneratedUsers(boolean state){
		usersGenerated=state;
	}

	
	}
