package CustomExceptions;

public class NoTurnYetException extends Exception{

	public NoTurnYetException() {
		super("There is not turn registered yet.");
	}
}
