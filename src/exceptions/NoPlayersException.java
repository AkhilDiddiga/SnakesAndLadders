package exceptions;

public class NoPlayersException extends Exception{
	private final static String message = "No Players in the game";
	public NoPlayersException() {
		super(message);
	}
}
