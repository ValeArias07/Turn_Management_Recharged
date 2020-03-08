package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import CustomExceptions.DocumentExistException;
import CustomExceptions.NoTurnYetException;
import CustomExceptions.NotFoundException;
import CustomExceptions.ObligatoryFieldsException;

public class AdminTest {
	private Admin adminTest1;
	private Admin adminTest2;
	private Admin adminTest3;
	private Admin adminTest4;
	public void setUp1() throws NumberFormatException, DocumentExistException, ObligatoryFieldsException, NotFoundException {
		adminTest1= new Admin();
		adminTest1.addUser(TypeDocuments.ACARD.getType(),"1000255081", "Luisa", "Lopez", "3215463728", "Calle Circunvalar");
	}
	public void setUp2() throws DocumentExistException, ObligatoryFieldsException {
		adminTest2=new Admin();
	}
	
	public void setUp3() throws DocumentExistException, ObligatoryFieldsException {
		adminTest3=new Admin();
		adminTest3.addUser(TypeDocuments.FOREIGNCARD.getType(), "AC718203", "Rebeca", "Lopez", "3263857171", "Avenida 1 Oeste");
		adminTest3.addUser(TypeDocuments.ACARD.getType(), "10255081", "Valentina", "Arias", "3263285717", "Calle Circunvalar");
		
		adminTest3.addUser(TypeDocuments.ACARD.getType(), "1234556", "Alisson", "Rodriguez", "3163285717", "calle 1 #1234");
		adminTest3.addUser(TypeDocuments.ACARD.getType(), "904008195", "Violeta", "Mendez", "3152812413", "Crystal Avenue");
	}
	
	public void setUp4() throws NotFoundException, DocumentExistException, ObligatoryFieldsException {
		adminTest4= new Admin();
		adminTest4.addUser(TypeDocuments.FOREIGNCARD.getType(), "A1239012398", "Luis", "Lopez", "798123910", "Av. 2 Oeste");
		adminTest4.addUser(TypeDocuments.ACARD.getType(), "B1299923123", "Andres", "Arias", "71293920", "Clle Circunvalar");
		
		adminTest4.registTurn("A1239012398");
		adminTest4.registTurn("B1299923123");
	}

	
	@Test
	public void testAddUser() throws NumberFormatException, DocumentExistException, ObligatoryFieldsException, NotFoundException {
		
	//////This method proof if the method addUser works successfully when the ArrayList of users is EMPTY and the user is NEW
		setUp1();
		try {
			adminTest1.addUser(TypeDocuments.ACARD.getType(), "23456091", "Valentina", "Arias Parra", "3263285717", "Calle 1 Oeste");
		}
		catch(DocumentExistException d) {
		fail("There is a error with the search of repeated person");	
		}
		catch( ObligatoryFieldsException o) {
			fail("There is a error comparing the correct type of fields");	
		}
		
		
	//////This method proof is the method addUser works successfully when the ArrayList of users HAVE USERS and the user is NEW/////
		
		try {
			adminTest1.addUser(TypeDocuments.CIVILREGIST.getType(), "124214123", "Alejandra", "Hurtado", "32857174567", "Calle circunvalar");
		}
		catch(ObligatoryFieldsException o) {
			fail("That exception is throw in a bad way");
		}
		
		
	//////This part of the method proof if the method is throwing the DocumentExistException when the number of Document is REPEATED (The user is already registered)
		try{
			adminTest1.addUser(TypeDocuments.CIVILREGIST.getType(), "23456091", "Alejandro", "Hurtado", "6128370123", "Calle del Sol #34-12");
			fail("DocumentExistException has not been throw");
		}
		catch(DocumentExistException d) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testRegistTurn() throws NumberFormatException, DocumentExistException, ObligatoryFieldsException, NotFoundException, NoTurnYetException {
		
		setUp1();
	//////This test shows what happen when it SEARCH a user that DOESNT EXIST,and also, shows what happen when the ArrayList usersData is EMPTY
		
		try {
		adminTest1.registTurn("718218218");
		fail("The method has search a inexist user");
		}
		catch(NotFoundException no) {
		}
		
	//////This test proof is the method SEARCH a user and shoe if it have an ACTIVE TURN and if it has one, it show a message and show the ACTUAL TURN
		
		String turnMessage="Sorry, the user have an active turn yet and it is A00";
		String numDoc="1000255081";
		String methodMessage="";
		methodMessage=adminTest1.registTurn(numDoc);
		try {
		methodMessage=adminTest1.registTurn(numDoc);
		assertEquals("The method is giving the incorrect turn", methodMessage, turnMessage);
		}
		catch(NotFoundException n) {
			fail("There is a error searching the document number");
		}
		
		setUp2();
	//////This test proof if the method is giving the CORRECT TURN when it is ASSIGNED to a NEW user and it star in A00
		
		try {
			adminTest2.addUser(TypeDocuments.CIVILREGIST.getType(), "AC9910234", "Alejandro", "Hurtado", "32857174567", "Calle circunvalar");
			methodMessage=adminTest2.registTurn("AC9910234");
			assertEquals("The method is giving the incorrect turn", methodMessage,"A00");
		}
		catch(NotFoundException n) {
			fail("There is a error searching the document number");
		}
		
	//////This test proof if the method is GIVING the correct consecutive turn when it ATTEND it 
		try {
			String turnToAttend=adminTest2.showTurn();
			if(!(turnToAttend.equalsIgnoreCase("A00"))) {
				fail("The program is not atent turns in order");
			}
		}
		catch(NoTurnYetException n) {
		fail("THe program is giving a incorrect exception");
		}
		
		setUp3();
	//////This test proof if the method is giving the correct turn after the turn D99 (D99 to E00)
		adminTest3.addTurn('D', 99, 0);
		adminTest3.attentTurn(1);
		String turn=adminTest3.registTurn("10255081");
		if(!(turn.equalsIgnoreCase("E00"))){
			fail("The method is not ordering the turns in a consecutive way" + turn );
		}
		

		
	//////This test proof if the method is giving the correct turn after the turn Z99 (Z99 to A00)
		adminTest3.addTurn('Z', 99, 2);
		if(!(adminTest3.registTurn("904008195").equalsIgnoreCase("A00"))) {
				fail("The method is not ordering the turns in a consecutive way");
		}
	}
	
	@Test
	public void testAttentTurn() throws DocumentExistException, ObligatoryFieldsException, NoTurnYetException, NotFoundException {
		setUp4();

	/////This test proof if the method give the correct turn to attend
		if(!(adminTest4.showTurn().equalsIgnoreCase("A00"))){
			fail("The method is not showing the correct Turn" + adminTest4.showTurn());
		}
		
	/////This test proof if the method is attended right the next turn
		adminTest4.attentTurn(1);
		String turn=adminTest4.showTurn();
		if(!(turn.equalsIgnoreCase("A01"))) {
			fail("The method is not attent in consecutive order" + turn);
		}
		
	}
}
