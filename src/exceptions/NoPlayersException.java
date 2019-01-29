package exceptions;

public class NoPlayersException extends Exception{
	// this exception is thrown when there are no players to start the game
	private final static String message = "No Players in the game";
	public NoPlayersException() {
		super(message);
	}
}
