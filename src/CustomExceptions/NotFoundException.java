package CustomExceptions;

public class NotFoundException extends Exception {
	
	public NotFoundException(String numDoc) {
		super("The user " + numDoc + " doesnt found");
	}

}
