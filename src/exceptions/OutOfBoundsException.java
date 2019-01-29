package exceptions;

public class OutOfBoundsException extends Exception{
	// this exception is thrown when one of the inputs is out of bounds based on the criterion
	private final static String message = "Input out of Bounds";
	public OutOfBoundsException() {
		super(message);
	}
}
