package exceptions;

public class LadderPresentException extends Exception {
	private final static String message = "Ladder Already Present at this Location";
	public LadderPresentException() {
		super(message);
	}
}
