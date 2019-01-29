package exceptions;

public class SnakePresentException extends Exception {
	private final static String message = "Snake Already Present at this Location";
	public SnakePresentException() {
		super(message);
	}
}
