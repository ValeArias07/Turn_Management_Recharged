package CustomExceptions;

public class DocumentExistException extends Exception{
	
	public DocumentExistException(String numDoc) {
	super("The document " + numDoc + " is already registered in our system");
	}
}
