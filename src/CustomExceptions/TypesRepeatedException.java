package CustomExceptions;

public class TypesRepeatedException extends Exception{

	public TypesRepeatedException(String type, double duration) {
		super("Sorry, the type "+ type + " is already exist and it duration is " + duration);
	}
}
