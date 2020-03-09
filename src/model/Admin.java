package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import CustomExceptions.DocumentExistException;
import CustomExceptions.NoTurnYetException;
import CustomExceptions.NotFoundException;
import CustomExceptions.ObligatoryFieldsException;
import CustomExceptions.TurnHadNoAssigned;
import CustomExceptions.TypesRepeatedException;


public class Admin {
	
	private ArrayList <User> usersData;
	private ArrayList <User> usersWithTurns;
	private ArrayList <Turn> typeOfTurns;
	private RandomData random;
	
	
	public static String PATH_REPORT_SPECIFIC_PERSON="G:/Eclipse/TurnManagementRecharged/data/ReportSpecificPerson.txt";
	public static String PATH_REPORT_SPECIFIC_TURN="G:/Eclipse/TurnManagementRecharged/data/ReportSpecificTurn.txt";

	
	public Admin() throws IOException {
		usersData=new ArrayList<User>();
		usersWithTurns= new ArrayList<User>();
		random= new RandomData(); 
		typeOfTurns=new ArrayList <Turn>();
///////
	}
	
	public String addTypeOfTurn(String type, double duration) throws TypesRepeatedException {
		String m="Turn type created sucessful";
		double durationT=nameRepeated(type);
		if(durationT==-1) {
		typeOfTurns.add(new Turn(type, duration));
		}else {
			throw new TypesRepeatedException(type, durationT);
		}
		return m;
	}
	
	public String showOrderTypes() {
		Collections.sort(typeOfTurns);
		String m="";
		for (int i = 0; i < typeOfTurns.size(); i++) {
			m+=(i+1)+". "+typeOfTurns.get(i).getType()+" with duration "+ typeOfTurns.get(i).getDuration()+"\n";
		}
		if(m.equals("")) 
			m="No types yet";
		return m;
	}
	
	public int chooseTypeTurn (int opt, int type) {
		Collections.sort(typeOfTurns);
		int choise=0;
		if(opt==2) {
			return choise=(int)(Math.random()*typeOfTurns.size());
		}
		return type;
	}
	
	
	public String generateUsers(int numP, int numGen) throws IOException, DocumentExistException, ObligatoryFieldsException{
		String m="People generated Successfully";
		for (int i = 0; i < numP; i++) {
			String typeDoc=random.getRandomTypes();
			String id=random.getIdNumber(i) + numGen;
			String name=random.getNames((int)(Math.random()*random.getNamesLength()));
			String lastName=random.getLastNames((int)(Math.random()*random.getLastNamesLength()));
			String phone=random.getPhone((int)(Math.random()*random.getPhoneLength()));
			String address=random.getAddress((int)(Math.random()*random.getNAddressLength()));
			addUser(typeDoc, id, name, lastName, phone , address);
			}
		return m;
	}
	
//////////////////////////////////////////////////////////Principal Methods//////////////////////////////////////////////////////////
	/**This method add an User in the ArrayList User of the class
	 * <b>post<b/>:The ArrayList has to be initialized and the user has not be in the ArrayList before
	 * <b>pre<b/>:The ArrayList will have an  new User 
	 * @param typeDoc is the String that represent the user's type of the document. It just can be the constants of the Class TypeDocuments
	 * @param numDoc is the String that represent the number of the User's Document numDoc!=0 and numDoc!=" "
	 * @param name is the String that represents the user's name name!=" " 
	 * @param lastName is the String that represents the user's last name lastName!=" "
	 * @param phone is the String that represents the user's phone number phone!=0
	 * @param adress is the String that represent the user's address
	 */
	public void addUser(String typeDoc, String numDoc, String name, String lastName, String phone,String address)  throws DocumentExistException, ObligatoryFieldsException {
			searchUserRepeated(numDoc);
				User u=new User(typeDoc, numDoc, name, lastName, phone, address);
				usersData.add(u);
	}
	
	/**
	 * This method assign a turn for a User in the Arraylist 
	 * <b>pre<b/>: The user has to be in the ArrayList and it doesn't have a turn before
	 * <b>post<b/>: The user will have assigned a consecutive turn
	 * @param numDoc is the String that represent the User's number of Document numDoc!=0 and numDoc!=" "
	 * @return turn is the String that represent the User's turn that was assigned 
	 * @throws NotFoundException it is throwing when the user is not registered in the ArrayList
	 */
	public String registTurn(String numDoc, int opt,int type) throws NotFoundException{
	Collections.sort(typeOfTurns);
	int numUbication=0; 
	char letter;
	String turn="";
	User userInUse;
	int ubicationRandom=-1;
	if(numDoc.equals("0")) {
		ubicationRandom=(int)(Math.random()*usersData.size());
		numDoc=usersData.get(ubicationRandom).getNumDoc();
	}
	if(oneTurnOnce(numDoc).equalsIgnoreCase("none")) {
		int typeTurn=chooseTypeTurn(opt, type);
		userInUse=searchUserInData(numDoc,ubicationRandom);
			if(usersWithTurns.size()>0) {
				letter=usersWithTurns.get(usersWithTurns.size()-1).getTurnLetter();
				numUbication=(usersWithTurns.get(usersWithTurns.size()-1).getTurnNumber())+1;
				
					if(numUbication>99) {
						numUbication=0;
						turn=addTurn(searchLetter(letter), numUbication, userInUse, typeTurn);
						}
						else {
							turn=addTurn(letter, numUbication,userInUse, typeTurn);
							numUbication++;
							}
					}
					else {
						usersWithTurns.add(userInUse);
						userInUse.setFirst('A', "0",typeOfTurns.get(typeTurn-1).getType(),typeOfTurns.get(typeTurn-1).getDuration());
						turn="A00";
					}
		turn="The person " + userInUse.getName()+" "+userInUse.getLastName()+ " with the ID "+ userInUse.getNumDoc() + " has the turn " + turn + " of the type " + userInUse.getTypeAndDuration()+"\n";
	}
	return turn;
	}
	
	
	/**
	 * This method attend a turn in order to the first created until the last one 
	 * @param option it represent the two possible option, option==ATTENTED or option==NOT ATTENDED
	 * @throws NoTurnYetException it is throw when there is not more turns to attend
	 */
	public void attentTurn(int option) throws NoTurnYetException {
	String turn="";
	try {
		 turn=showTurn();
	}
	finally {
		for(int i=0; i<usersWithTurns.size(); i++) {
			if(usersWithTurns.get(i).getActiveTurn().equalsIgnoreCase(turn)) {
				if(option==1) 
					usersWithTurns.get(i).setState(Turn.AS);
				else 
					usersWithTurns.get(i).setState(Turn.NOAS);
			}
		}
	}
}
	
//////////////////////////////////////////////////////////Auxiliary Methods//////////////////////////////////////////////////////////
	public String generateReportSpecificPerson(String numDoc, int option) throws NotFoundException, IOException {
		User userInUse=searchUserWithTurn(numDoc);
		String m="Report Saved Successful ";
		String[] all= userInUse.allStates();
		if(option==1) {
			m="The turns of the Person " +userInUse.getName()+" "+userInUse.getLastName()+" "+userInUse.getNumDoc()+" are:";
			for (int i = 0; i < userInUse.allStates().length; i++) {
				m+="\n"+all[i];
			}
		}else {
			BufferedWriter bw= new BufferedWriter(new FileWriter(PATH_REPORT_SPECIFIC_PERSON));
			bw.write("The turns of the Person " +userInUse.getName()+" "+userInUse.getLastName()+" "+userInUse.getNumDoc()+" are:\n");
			for (int i = 0; i < userInUse.allStates().length; i++) {
				bw.write(all[i]+"\n");
				bw.flush();
			}
			bw.close();
		}
		return m;
	}
	
	public String generateReportSpecificTurn(String codeTurn, int option) throws IOException, TurnHadNoAssigned {
		String m="Report Saved";
		if(option==1) {
			m=searchTurnsInUser(codeTurn);
		}else {
			BufferedWriter bw= new BufferedWriter(new FileWriter(PATH_REPORT_SPECIFIC_TURN));
			try {
				bw.write(searchTurnsInUser(codeTurn));
			}
			catch(TurnHadNoAssigned tn) {
				bw.write(tn.getMessage());
			}
			finally{
				bw.close();
			}
		}
		return m;
	}
	
	
	/**
	 * This auxiliary method assign the turn using the letter of the /anterior/ turn and a number superior of the last 
	 * @param letter is the char that represent the order of the turn. 
	 * @param numUbication is the integer that represent the order of the turn numUbication<100 and numUbication>0
	 * @param positionUser is the position where the user is in the ArrayList of usersData positionUser<usersData.size()
	 * @return turn is the turn assigned to the User
	 */
	public String addTurn(char letter, int numUbication, User userInUse, int typeTurn) {
		Collections.sort(typeOfTurns);
		Turn turnType=typeOfTurns.get(typeTurn-1);
		usersWithTurns.add(userInUse);
		userInUse.setTurns(letter,String.valueOf(numUbication),turnType.getState(),turnType.getDuration());
		String turn=(numUbication>9)?(letter+String.valueOf(numUbication)):(letter+"0"+String.valueOf(numUbication));
		return turn;
	}
	
	/*
	 * 
	 */
	public String oneTurnOnce(String numDoc) {
		boolean found=false;
		String turn="none";
		if(usersWithTurns.size()>0) {
		for(int i=0; i<usersWithTurns.size() && found==false;i++) {
			if(numDoc.equals(usersWithTurns.get(i).getNumDoc())) {
				if(usersWithTurns.get(i).isAttent().equals(Turn.NOTD))
				turn=usersWithTurns.get(i).showTurn();
				}
			}
		}
		return turn;
	}
	
	/**
	 * This auxiliar method search a letter in the Array Letters to assign it in the user's turn
	 * @param letter it could be the letter of the last turn created 
	 * @return letter it represent the letter that will have the new turn
	 */
	public char searchLetter(char letter) {
	char[] letters= {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	boolean found=false;
		for(int j=0;j<letters.length && found==false ;j++) {
			if(letter==letters[j]) {
				if(j==letters.length-1) {
					letter=letters[0];
					found=true;
				}
					else
					{
					letter=letters[j+1];
					found=true;	
					}	
			}
		}
		return letter;
	}
	
	/**
	 * This auxiliary method have the function of search in the ArrayList usersData if the user's document number is not repeated
	 * @param numDoc it represent the document number of the new User
	 * @throws DocumentExistException it is throw if some user in the ArrayList have the same number of the users new
	 */
	
	public void searchUserRepeated(String numDoc) throws DocumentExistException {
		for(int i=0;i<usersData.size(); i++) {
			if(usersData.get(i).getNumDoc().equalsIgnoreCase(numDoc)) {
				throw new DocumentExistException(numDoc);
			}
		}
	}
	
/**
 * This auxiliary method have the function of show the turn to attend in a ascendent order
 * @return turn represent the turn to attend
 * @throws NotFoundException 
 * @throws NoTurnYetException it is throw if there is not turn to attend
 */
	public User searchUserInData(String numDoc,int ubicationRandom) throws NotFoundException {
		int initial=0, finalP=usersData.size()-1, medium;
		User userFound=null;
		boolean found=false;
		if(ubicationRandom==-1) {
		while(initial<=finalP && found==false){
			medium=(initial+finalP)/2;
			
			if(Long.parseLong(usersData.get(medium).getNumDoc()) > Long.parseLong(numDoc)) {
				finalP=medium-1;
			}else if(Long.parseLong(usersData.get(medium).getNumDoc()) < Long.parseLong(numDoc)){
				initial=medium+1;
			}
			else {
				userFound=usersData.get(medium);
				found=true;
			}
		}
		if(found==false)
			throw new NotFoundException(numDoc);
		}else {
			userFound=usersData.get(ubicationRandom);
		}
		return userFound;
	}
	
	public User searchUserWithTurn(String numDoc) throws NotFoundException {
		int initial=0, medium;
		User userFound=null;
		boolean found=false;
		if(usersWithTurns.size()>=0) {
			int finalP=usersWithTurns.size()-1;
			while(initial<=finalP && found==false){
				medium=(initial+finalP)/2;
				
				if(Long.parseLong(usersWithTurns.get(medium).getNumDoc()) > Long.parseLong(numDoc)) {
					finalP=medium-1;
				}else if(Long.parseLong(usersWithTurns.get(medium).getNumDoc()) < Long.parseLong(numDoc)){
					initial=medium+1;
				}
				else {
					userFound=usersWithTurns.get(medium);
					found=true;
				}
			}
			if(found==false)
				throw new NotFoundException(numDoc);
			}
		return userFound;
	}
	
	public String searchTurnsInUser(String codeTurn) throws TurnHadNoAssigned {
		String m="";
		for (int i = 0; i < usersWithTurns.size(); i++) {
			if(!(usersWithTurns.get(i).searchSpecificTurn(codeTurn).equals("-")))
			m+=usersWithTurns.get(i).searchSpecificTurn(codeTurn);
		}
		if(m.equals(""))
			throw new TurnHadNoAssigned(codeTurn);
		return m;
	}
	
	public String showPeople() {
		String m="";
		for (int i = 0; i < usersData.size(); i++) {
			m+=usersData.get(i).toString();
		}
		return m;
	}
	public String showTurn () throws NoTurnYetException{
		boolean found=false;
		String turn="";
			
			for(int i=0; i<usersWithTurns.size() && found==false;i++) {
				if(usersWithTurns.get(i).isAttent().equals(Turn.NOTD)){
					turn=usersWithTurns.get(i).showTurn();
					found=true;
				}
			}
			if(found==false)
				throw new NoTurnYetException();
		return turn;
	}
	//////////////////////////TURN METHODS//////////////////////
	
	/**
	public String addTypeTurns(String type, double duration) throws TypesRepeatedException {
		String sucess="Turn created sucessfull";
		Turn pointer=null;
		double durationT=nameRepeated(type);
		if(durationT==-1) {
		if(firstTurn==null) {
			firstTurn=new Turn(type,duration);
		}else {
			pointer=firstTurn;
			while(pointer.getNextTurn()!=null) {
				pointer=pointer.getNextTurn();
			}
			pointer=new Turn(type,duration);
		}
		}else {
			throw new TypesRepeatedException(type, durationT);
		}
		return sucess;
	}
	*/
	public double nameRepeated(String type) {
		double duration=-1;
		boolean found=false;
		if(typeOfTurns.size()>0) {
		for (int i = 0; i < typeOfTurns.size() && found==false; i++) {
			if(typeOfTurns.get(i).getType().equals(type)) {
				duration=typeOfTurns.get(i).getDuration();
				found=true;
			}
		}
	}
	return duration;
}
}
