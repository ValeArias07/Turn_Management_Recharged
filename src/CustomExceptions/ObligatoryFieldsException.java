package CustomExceptions;

public class ObligatoryFieldsException extends Exception{

	public ObligatoryFieldsException() {
		super("You must type the information of this fields: Type of Document, "
				+ "\n number of document, names and last names");
	}
}
