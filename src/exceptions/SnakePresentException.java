package exceptions;

public class SnakePresentException extends Exception {
	// this exception is thrown when a snake head is already present at the location where add ladder start is called
	private final static String message = "Snake Already Present at this Location";
	public SnakePresentException() {
		super(message);
	}
}
