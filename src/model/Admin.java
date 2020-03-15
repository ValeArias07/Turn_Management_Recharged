package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import CustomExceptions.DocumentExistException;
import CustomExceptions.NoTurnYetException;
import CustomExceptions.NotFoundException;
import CustomExceptions.ObligatoryFieldsException;
import CustomExceptions.TurnHadNoAssigned;
import CustomExceptions.TypesNotCreatedException;
import CustomExceptions.TypesRepeatedException;

public class Admin implements Serializable{
	
	private ArrayList <User> usersData;
	private ArrayList <User> usersWithTurns;
	private ArrayList <TypeTurn> typeOfTurns;
	private ArrayList <String> randomIdsTaken;
	private LocalDateSystem dateSystem;
	private LocalDateSystem computerInternDate;
	private RandomData random;
	private int numId;
	
	public static Double TURNS_REST=0.15;
	public static String PATH_REPORT_SPECIFIC_PERSON="G:/Eclipse/TurnManagementRecharged/data/ReportSpecificPerson.txt";
	public static String PATH_REPORT_SPECIFIC_TURN="G:/Eclipse/TurnManagementRecharged/data/ReportSpecificTurn.txt";

	public static String SAVE_ROOT="G:/Eclipse/TurnManagementRecharged/data/savedData.turn";
	
	
	public Admin() throws IOException {
		random= new RandomData(); 
		usersData=new ArrayList<User>();
		usersWithTurns= new ArrayList<User>();
		typeOfTurns=new ArrayList <TypeTurn>();
		randomIdsTaken=new ArrayList <String>();
		computerInternDate=new LocalDateSystem();
		numId=0;
		dateSystem=null;
	}
	
	////////////////////////////////////////////////DATE METHODS////////////////////////////////////////////////
	
	/**
	 * This method update the System's date using the differences of a old date and the actual
	 */
	public void updateDateSystem() {
		LocalDateTime localSystem2=LocalDateTime.now();
		Duration duration= Duration.between(computerInternDate.getDate(),localSystem2);
		long secondsPlus=duration.getSeconds();
		dateSystem=dateSystem.updateTime(secondsPlus);
	}
	
	/**
	 * This method update the date class of the System
	 */
	public void setDate() {
		dateSystem=new LocalDateSystem();
	}
	
	/**
	 *This method update the intern date class of the System
	 */
	public void setNewComputerDate() {
		computerInternDate=new LocalDateSystem();
	}
	
	/**
	 * This method set the updated date to the actual date
	 * @param dateSystemNew
	 */
	public void setDateUpdated(LocalDateSystem dateSystemNew) {
		dateSystem=dateSystemNew;
	}
	
	/**
	 * This method set a date with the information that is giving by the user
	 * @param year is the year where the user want to set the date in the system
	 * @param month is the month where the user want to set the date in the system
	 * @param dayOfMonth is the day where the user want to set the date in the system
	 * @param hour is the hour where the user want to set the date in the system
	 * @param minute is the minute where the user want to set the date in the system
	 * @param seconds is the second where the user want to set the date in the system
	 */
	public void setDate(int year, int month, int dayOfMonth, int hour, int minute, int seconds) {
		dateSystem=new LocalDateSystem(year, month, dayOfMonth, hour, minute, seconds);
	}
	
	/**
	 * This method update the System's date with information giving by the user
	 * @param yearN is the year where the user want to set the date in the system
	 * @param monthN is the month where the user want to set the date in the system
	 * @param dayOfMonthN is the day where the user want to set the date in the system
	 * @param hourN is the hour where the user want to set the date in the system
	 * @param minuteN is the minute where the user want to set the date in the system
	 * @param secondsN is the second where the user want to set the date in the system
	 * @return the date updated
	 */
	public String updateDate(int yearN, int monthN, int dayOfMonthN, int hourN, int minuteN, int secondsN) {
		LocalDateSystem dateSystemNew=dateSystem.updateDate(yearN, monthN, dayOfMonthN, hourN, minuteN, secondsN);
		setDateUpdated(dateSystemNew);
		return dateSystem.getCompleteDate();
	}
	
	/**
	 * This method show the System's date and  time
	 * @return the actual date and time
	 */
	public String getCompleteDate() {
		return dateSystem.getCompleteDate()+" "+dateSystem.getCompleteHour();
	}
	
	////////////////////////////////////////////////TURNS METHODS////////////////////////////////////////////////
	
	/**
	 * This turn add a Type of Turn to the ArrayList TypeTurn
	 * @param type is the name of the turn
 	 * @param duration is the duration of the turn
	 * @return m shows if the type was added or not
 	 * @throws TypesRepeatedException this exception is thrown if the name of the turn is repeated 
	 */
	public String addTypeOfTurn(String type, double duration) throws TypesRepeatedException {
		String m="Turn type created sucessful";
		double durationT=nameRepeated(type);
		if(durationT==-1) {
		typeOfTurns.add(new TypeTurn(type, duration));
		}else {
			throw new TypesRepeatedException(type, durationT);
		}
		return m;
	}
	
	/**
	 * This method show in order the Types of Turns of the Arraylist typeOfTurns
	 * @return m shows all the types of turns in order to it duration
	 * @throws TypesNotCreatedException this exception is thrown if the types has not be created
	 */
	public String showOrderTypes() throws TypesNotCreatedException {
		Collections.sort(typeOfTurns);
		String m="";
		if(!typeOfTurns.isEmpty()) {
		for (int i = 0; i < typeOfTurns.size(); i++) {
			m+=(i+1)+". "+typeOfTurns.get(i).getType()+" with duration "+ typeOfTurns.get(i).getDuration()+"\n";
		}
		}else {
			throw new TypesNotCreatedException();
		}
		return m;
	}
	
	/**
	 * This method is created to choose a specific type of turn in the ArrayList typeOfTurns.
	 * @param opt it represent if the user choose a specific option of turn or it was taken random
	 * @param type represent the type choosen  the ArrayList
	 * @return type is the ubication of the turn type
	 * @throws TypesNotCreatedException
	 */
	public int chooseTypeTurn (int opt, int type) throws TypesNotCreatedException {
		Collections.sort(typeOfTurns);
		
		if(!typeOfTurns.isEmpty()){
		if(opt==2) {
			return (int)(Math.random()*typeOfTurns.size());
		}
		}else {
			throw new TypesNotCreatedException();
		}
		return type;
	}
	
	/**This method check if the user has created at least one type of turn
	 * @return voidType shows if at least one type of Turn was created
	 */
	public boolean noTypesYet() {
		boolean voidType=false;
		if(typeOfTurns==null) {
			voidType=true;
		}
		return voidType;
	}
	
	/**
	 * This method guarantees that the random number created to choose a user never repeat that number again
	 * @return numDoc is the number of Identification of the user selected
	 */
	public String selectRandomUser() {
		Collections.sort(randomIdsTaken);
		boolean repeated=false, exit=false;
		String numDoc="invalid";
		int ubicationRandom=-1;
		if(randomIdsTaken.size()>0) {
		while(!exit) {
			int initial=0, finalP=randomIdsTaken.size()-1, medium;
			ubicationRandom=(int)(Math.random()*usersData.size());
			
			while(initial<=finalP && repeated==false) {
				medium=(initial+finalP)/2;
				
				if(Integer.parseInt(randomIdsTaken.get(medium))<ubicationRandom) {
					initial=medium+1;
				}else if(Integer.parseInt(randomIdsTaken.get(medium))>ubicationRandom) {
					finalP=medium-1;
				}else {
					repeated=true;
				}
			}
			
			exit=(repeated==false)?true:false;
			}
		if(exit==true) {
			numDoc=usersData.get(ubicationRandom).getNumDoc();
			randomIdsTaken.add(String.valueOf(ubicationRandom));
		}	
			}else{
					numDoc=usersData.get((int)(Math.random()*usersData.size())).getNumDoc();
				}
		return numDoc;
	}
	
	public boolean checkState(User userInUse) {
		boolean locked=false;
		if(userInUse.getfaults()>=2) {
			locked=true;
		}
		return locked;
	}
	
	/**
	 * This method assign a turn for a User in the Arraylist 
	 * <b>pre<b/>: The user has to be in the ArrayList and it doesn't have a turn before
	 * <b>post<b/>: The user will have assigned a consecutive turn
	 * @param numDoc is the String that represent the User's number of Document numDoc!=0 and numDoc!=" "
	 * @return turn is the String that represent the User's turn that was assigned 
	 * @throws NotFoundException it is throwing when the user is not registered in the ArrayList
	 * @throws TypesNotCreatedException 
	 */
	public String registTurn(String numDoc, int opt,int type) throws NotFoundException, TypesNotCreatedException{
	Collections.sort(typeOfTurns);
	boolean stateUser=false;
	int year=dateSystem.getYear(), month=dateSystem.getMonth(), dayOfMonth=dateSystem.getDay(), hour=dateSystem.getHour(), minute=dateSystem.getMinutes(),seconds=dateSystem.getSeconds();	int numUbication=0; 
	char letter;
	String turn="";
	User userInUse;
	int ubicationRandom=-1;

	if(numDoc.equals("0")) {
		numDoc=selectRandomUser();
	}
	if(oneTurnOnce(numDoc).equalsIgnoreCase("none")) {
		int typeTurn=chooseTypeTurn(opt, type);
		userInUse=searchUserInData(numDoc,ubicationRandom);
		stateUser=checkState(userInUse);
		if(!stateUser) {
			if(usersWithTurns.size()>0) {
				letter=usersWithTurns.get(usersWithTurns.size()-1).getTurnLetter();
				numUbication=(usersWithTurns.get(usersWithTurns.size()-1).getTurnNumber())+1;
				
					if(numUbication>99) {
						numUbication=0;
						turn=addTurn(searchLetter(letter), numUbication, userInUse, typeTurn,year,  month,  dayOfMonth, hour, minute, seconds);
						}
						else {
							turn=addTurn(letter, numUbication,userInUse, typeTurn,year,  month,  dayOfMonth, hour, minute, seconds);
							numUbication++;
							}
					}
					else {
						usersWithTurns.add(userInUse);
						userInUse.setFirst('A', "00",typeOfTurns.get(typeTurn).getType(),typeOfTurns.get(typeTurn).getDuration(),year,  month,  dayOfMonth, hour, minute, seconds);
					}
		turn="User " + userInUse.getName()+" "+userInUse.getLastName()+ " ID "+ userInUse.getNumDoc() + "\nhas assigned the " + userInUse.getTurnInfo();
	
		}else {
			turn="Sorry, the user is locked till ";
		}
	return turn;
	}
	
	public int ubicationOldTurn() {
		int ubication=-1;
		boolean found=false;
		for (int i = 0; i < usersWithTurns.size() && found==false; i++) {
			if(usersWithTurns.get(i).getLastTurn().getState()==false){
				ubication=i;
				found=true;
			}
		}
		return ubication;
	}
	
	public String AttendTillNow() {
		String m="Turns Attended :\n";
		if(!usersWithTurns.isEmpty()) {
		int firstTurnCreated=ubicationOldTurn();//Takes the older turn
		LocalDateTime localDateOld=usersWithTurns.get(firstTurnCreated).getLastTurn().getDateOfCreation().getDate();//takes the time of the oldest Turn
		Duration diferences = Duration.between(localDateOld, dateSystem.getDate());//Make the difference of time
		double timeToAttend=diferences.getSeconds()/60;
		double timePassed=0; 
		boolean presenceUser=false;
		int randomPresence=1;
		for (int i = firstTurnCreated; timePassed<=timeToAttend; i++) {
			randomPresence=(int)(Math.random()*6+1);
			presenceUser=(randomPresence%2==0)?true:false;
			timePassed+=usersWithTurns.get(i).getDurationLastTurn() + TURNS_REST;
			usersWithTurns.get(i).setState(presenceUser);
			m+= "\n" + (i+1)+". "+usersWithTurns.get(i).getTurnInfo();
		}
		}else {
			m="Not turns registered yet";
		}
		return m;
	}
	
	/**
	 * This auxiliary method assign the turn using the letter of the /anterior/ turn and a number superior of the last 
	 * @param letter is the char that represent the order of the turn. 
	 * @param numUbication is the integer that represent the order of the turn numUbication<100 and numUbication>0
	 * @param userInUse is the user that will has assign the turn
	 * @param typeTurn is the type of Turn that the user will have
	 * @param year is the year where the turn was assigned
	 * @param month is the month where the turn was assigned
	 * @param dayOfMonth  is the day of month where the turn was assigned
	 * @param hour is the hour where the turn was assigned
	 * @param minute is the minute where the turn was assigned
	 * @param seconds is the second where the turn was assigned
	 * @return turn is message that show the turn assigned to the User and also show information related to the turn
	 */
	public String addTurn(char letter, int numUbication, User userInUse, int typeTurn,int year, int month, int dayOfMonth, int hour, int minute, int seconds) {
		Collections.sort(typeOfTurns);
		TypeTurn turnType=typeOfTurns.get(typeTurn);
		usersWithTurns.add(userInUse);
		userInUse.setTurns(letter,String.valueOf(numUbication),turnType.getType(),turnType.getDuration(),year,  month,  dayOfMonth, hour, minute, seconds);
		String turn=(numUbication>9)?(letter+String.valueOf(numUbication)):(letter+"0"+String.valueOf(numUbication));
		return turn;
	}
	
	
	/**
	 * This method attend a turn in order to the first created until the last one 
	 * @param option it represent the two possible option/ When option=1, it means that was attend and when the option=2, it means that the option was no attend
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
					usersWithTurns.get(i).setState(true);
				else 
					usersWithTurns.get(i).setState(false);
			}
		}
	}
}
	
	/**
	 * This method warn if the user have an active turn before to assing other new
	 * @param numDoc is the ID of the user that will be search
	 * @return turn is the turn that have the person
	 */
	
	public String oneTurnOnce(String numDoc) {
		boolean found=false;
		String turn="none";
		if(usersWithTurns.size()>0) {
		for(int i=0; i<usersWithTurns.size() && found==false;i++) {
			if(numDoc.equals(usersWithTurns.get(i).getNumDoc())) {
				if(usersWithTurns.get(i).isAttent()==false)
				turn=usersWithTurns.get(i).getActiveTurn();
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
	 * This method check that two types of TUrn does not have the same name
	 * @param type is the name of theTurn type 
	 * @return duration show if the name is repeated or not
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
	
	/**
	 * This method show the active turn to attend
	 * @return turn is the active turn in the moment
	 * @throws NoTurnYetException
	 */
	public String showTurn () throws NoTurnYetException{
		boolean found=false;
		String turn="";
			
			for(int i=0; i<usersWithTurns.size() && found==false;i++) {
				if(usersWithTurns.get(i).isAttent()==false){
					turn=usersWithTurns.get(i).getActiveTurn();
					found=true;
				}
			}
			if(found==false)
				throw new NoTurnYetException();
		return turn;
	}
	
	////////////////////////////////////////////////USER METHODS////////////////////////////////////////////////
	
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
				usersData.add(new User(typeDoc, numDoc, name, lastName, phone, address));
	}
	
	/**
	 * This method generate a n amount of users with random information.
	 * @param numP This is the amount of users to create
	 * @param numGen This is the times of this method is used. This int is used for generate new Ids.
	 * @return m is the message that show if the users was created
	 * @throws IOException 
	 * @throws DocumentExistException This exception is thrown if two persons have the same ID's
	 * @throws ObligatoryFieldsException This exception is thrown if the fields are empty
	 */
	public String generateUsers(int numP, int numGen) throws IOException, DocumentExistException, ObligatoryFieldsException{
		String m="People generated Successfully";
		int numIds=0;
		for (int i = 0; i < numP; i++) {
			if(numIds>random.getId()-5) {
				numIds=0;
				numId++;
			}
			String typeDoc=random.getRandomTypes();
			String id= numGen+numId+random.getIdNumber(numIds)+numId;
			String name=random.getNames((int)(Math.random()*random.getNamesLength()));
			String lastName=random.getLastNames((int)(Math.random()*random.getLastNamesLength()));
			String phone=random.getPhone((int)(Math.random()*random.getPhoneLength()));
			String address=random.getAddress((int)(Math.random()*random.getNAddressLength()));
			addUser(typeDoc, id, name, lastName, phone , address);
			numIds++;
			}
		return m;
	}

	/**
	 * This method generate a report with all the turns that have a specific person
	 * @param numDoc is the ID of the person that will be searched and reported all its turns
	 * @param option determines if the report will be written in a text archive or printed in the screen
	 * @return m is the message that warn if the report was written successful or is the report, in case that the user wants a to print it in the screen
	 * @throws NotFoundException this exception is thrown if the ID does not registered in the System
	 * @throws IOException
	 */
	
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
	
	/**
	 * This method generates a report with all the people that has ever had a specific turn
	 * @param codeTurn is the turn to search and generate the report 
	 * @param option determines if the report will be written in a text archive or printed in the screen
	 * @return  m is the message that warn if the report was written successful or is the report, in case that the user wants a to print it in the screen
	 * @throws IOException
	 * @throws TurnHadNoAssigned This exception is thrown if the turn has never assigned
	 */
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
	 * This method search a user in the Data using the binary algorithm
	 * @param numDoc is the ID of the person to search
	 * @param ubicationRandom if the user was created random, this int represent it ubication
	 * @return userFound is the user founded
	 * @throws NotFoundException this exception is thrown when the user doesnt exist
	 */
	public User searchUserInData(String numDoc,int ubicationRandom) throws NotFoundException {
		Collections.sort(usersData);
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
	
	/**
	 * This method search a user that have an turn using the binary search
	 * @param numDoc is the document of the user to search
	 * @return userFound is the object User founded
	 * @throws NotFoundException this exception is thrown when the user doesnt exist
	 */
	public User searchUserWithTurn(String numDoc) throws NotFoundException {
		Collections.sort(usersWithTurns);
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
	
	/**
	 * This method search which person have an specific turn
	 * @param codeTurn is the turn that will be search
	 * @return m is the message that shows who have the specific turn
	 * @throws TurnHadNoAssigned This exception is thrown if the turn has never assigned
	 */
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
	
	/**This method show all the users registered in the system
	 * <b> pre <b/>: The ArrayList usersData must be have initialized
	 * @return m is the String that show all the users in the system
	 */
	public String showPeople() {
		String m="";
		Comparator <User> comparatorName= new ComparatorByName();
		Collections.sort(usersData, comparatorName);
		
		for (int i = 0; i < usersData.size(); i++) {
			m+=usersData.get(i).toString();
		}
		return m;
	}
	/**
	 * This method saves the information of the class using the interface Serializable
	 * @throws IOException its thrown when the text archive is not found
	 */
	public void saveInformation() throws IOException { 
		FileOutputStream adminFOut = new FileOutputStream(SAVE_ROOT);
		ObjectOutputStream adminObjOut = new ObjectOutputStream(adminFOut);
		adminObjOut.writeObject(usersData);
		adminObjOut.writeObject(usersWithTurns);
		adminObjOut.writeObject(randomIdsTaken);
		adminObjOut.writeObject(random);
		adminObjOut.writeObject(dateSystem);
		adminObjOut.writeObject(typeOfTurns);
		adminObjOut.close();
	}
	
	/**
	 * This method loads the information saved using the interface Serializable
	 * @throws IOException its thrown when the text archive is not found
	 * @throws ClassNotFoundException is thrown when the class is does not found in the package
	 */
	@SuppressWarnings("unchecked")
	public void loadInformation() throws IOException, ClassNotFoundException {
		FileInputStream adminFIn = new FileInputStream(SAVE_ROOT);
		ObjectInputStream adminObjIn= new ObjectInputStream(adminFIn);
		usersData = (ArrayList<User>) adminObjIn.readObject();
		usersWithTurns =(ArrayList<User>) adminObjIn.readObject();
		randomIdsTaken =(ArrayList<String>) adminObjIn.readObject();
		random =(RandomData) adminObjIn.readObject();
		dateSystem=(LocalDateSystem) adminObjIn.readObject();
		typeOfTurns=(ArrayList<TypeTurn>) adminObjIn.readObject();
		
		adminObjIn.close();
	}
	
}
