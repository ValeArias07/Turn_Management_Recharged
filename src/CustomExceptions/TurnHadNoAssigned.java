package CustomExceptions;

public class TurnHadNoAssigned extends Exception{
	
	public TurnHadNoAssigned(String codeTurn) {
		super("### The turn " + codeTurn+ " had never an user assigned yet ###");
	}

}
