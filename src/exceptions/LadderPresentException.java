package exceptions;

public class LadderPresentException extends Exception {
	// this exception is thrown when a ladder is already present at the location where add snake is called
	private final static String message = "Ladder Already Present at this Location";
	public LadderPresentException() {
		super(message);
	}
}
