package model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UserTest {
	
	private User user;

	public void setUp1() {
		user= new User(TypeDocuments.ACARD.getType(),"1000255081","Valentina","Arias","3263285717","Calle Circunvalar");
	}
	@Test
	public void testUser() {
		setUp1();
		String typeDoc=TypeDocuments.ACARD.getType();
		String name="Valentina";
		String lastName="Arias";
		String numDoc="1000255081";
		String phone="3263285717";
		String address="Calle Circunvalar";
		
		assertEquals("The getter is not working", typeDoc, user.getTypeDoc());
		assertEquals("The getter is not working", name, user.getName());
		assertEquals("The getter is not working", lastName, user.getLastName());
		assertEquals("The getter is not working", numDoc, user.getNumDoc());
		assertEquals("The getter is not working", phone, user.getPhone());
		assertEquals("The getter is not working", address, user.getAddress());	
	}
	
	public void testsetTurn() {
		setUp1();
		char letter='A';
		String number="00";
		
		assertEquals("The getter is not working", letter, user.getTurnLetter());
		assertEquals("The getter is not working", number, user.getTurnNumber());	
	}
	
	public void testisAssisted() {
		setUp1();
		String option=User.AS;
		user.isAssisted(option);
		if(!(user.wasAssisted().equalsIgnoreCase(User.AS))) {
			fail("The state is not working");
		}
	}
}
