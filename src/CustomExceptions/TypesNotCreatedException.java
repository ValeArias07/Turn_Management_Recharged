package CustomExceptions;

public class TypesNotCreatedException extends Exception{
	public static String[] EXAMPLE_NAME={"Feed","Clean","Wash","Order","Pay","Claim"};;
	public static double EXAMPLE_DURATION=Math.random()*5;
	
	public TypesNotCreatedException() {
	super("Sorry, you need to create at least one type of turn.");
	}
	
	public String getExampleType() {
		String message=( "\n For Example, you need create type of turns like: "
		+ "\n The type "+EXAMPLE_NAME[(int)(Math.random()*EXAMPLE_NAME.length-1)]+" with a duration of "+ EXAMPLE_DURATION+"\n");
		return message;
	}
	
}
