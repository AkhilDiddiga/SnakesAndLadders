package exceptions;

public class OutOfBoundsException extends Exception{
	private final static String message = "Input out of Bounds";
	public OutOfBoundsException() {
		super(message);
	}
}
