package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TurnTest {
	private Turn turn;
	
	public void setUp1() {
		turn =new Turn('A',"00");
	}
	@Test
	public void testTurn() {
		char letter='A';
		String number="00";
		
		assertEquals( "Getter Letter is not working",letter, turn.getLetter());
		assertEquals( "Getter Number is not working",number, turn.getNumber());
		
	}
}
