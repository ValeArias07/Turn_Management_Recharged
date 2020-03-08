package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RandomData {
	private String[] names;
	private String[] lastNames;
	private String[] idNumber;
	private String[] address;
	private String[] phone;
	
	public static String PATH_NAMES="G:/Eclipse/TurnManagementRecharged/data/Names.txt";
	public static String PATH_LASTNAMES="G:/Eclipse/TurnManagementRecharged/data/LastNames.txt";
	public static String PATH_ADRESS="G:/Eclipse/TurnManagementRecharged/data/Address.txt";
	public static String PATH_ID="G:/Eclipse/TurnManagementRecharged/data/ID.txt";
	public static String PATH_PHONE="G:/Eclipse/TurnManagementRecharged/data/Phones.txt";
	
	public RandomData() throws IOException{
		setNames();
		setLastNames();
		setIdNumber();
		setAddress();
		setPhone();
	}

	public String getNames(int index) {
		return names[index];
	}

	public String getLastNames(int index) {
		return lastNames[index];
	}

	public String getIdNumber(int index) {
		return idNumber[index];
	}

	public String getAddress(int index) {
		return address[index];
	}
	public String getPhone(int index) {
		return phone[index];
	}
	
	public int getNamesLength() {
	return names.length;	
	}
	public int getLastNamesLength() {
	return lastNames.length;		
	}
	public int getPhoneLength() {
	return phone.length;
	}
	public int getNAddressLength() {
	return address.length;
	}
	public int getId() {
	return idNumber.length;		
	}
	public void setNames() throws IOException{
		String name="";
		BufferedReader brN= new BufferedReader(new FileReader(PATH_NAMES));
		String line="";
		while(line!=null) {
			line=brN.readLine();
			if(line!=null) {
			name+=line+";";;
			}
		}
		brN.close();
		names=name.split(";");
		}	
	
	public void setLastNames() throws IOException{
		BufferedReader brLN= new BufferedReader(new FileReader(PATH_LASTNAMES));
		String lastName1="";
		String line="";
		while(line!=null) {
			line=brLN.readLine();
			if(line!=null) {
				lastName1+=line+";";
			}
		}
		brLN.close();
		lastNames=lastName1.split(";");
		
	}

	public void setIdNumber() throws IOException{
		BufferedReader brI= new BufferedReader(new FileReader(PATH_ID));
		String lineNumber="";
		String line="";
		
		while(line!=null) {
			line=brI.readLine();
			if(line!=null) 
				lineNumber+=brI.readLine()+";";
		}
		 brI.close();
		idNumber=lineNumber.split(";");
	}

	public void setAddress() throws IOException {
		BufferedReader brA= new BufferedReader(new FileReader(PATH_ADRESS));
		String lineAddress="";
		String line="";
		while(line!=null) {
			line=brA.readLine();
			if(line!=null) 
				lineAddress+=line+";";
	}
		brA.close();
	address=lineAddress.split(";");	
}
	public void setPhone() throws IOException{
		BufferedReader brA= new BufferedReader(new FileReader(PATH_PHONE));
		String linePhone="";
		String line="";
		while(line!=null) {
			line=brA.readLine();
			if(line!=null) 
				linePhone+=line+";";
	}
		phone=linePhone.split(";");
	}
	public String getRandomTypes() {
		String doc="";
		int types=0;
			types=(int)(Math.random()*5+1);
			if(types==1)
			doc=TypeDocuments.ACARD.getType();
			else if(types==2)
				doc=TypeDocuments.CIVILREGIST.getType();
			else if(types==3)
				doc=TypeDocuments.FOREIGNCARD.getType();
			else if(types==4)
				doc=TypeDocuments.ICARD.getType();
			else if(types==5)
				doc=TypeDocuments.PASSPORT.getType();
		return doc;
	}
		
	}
