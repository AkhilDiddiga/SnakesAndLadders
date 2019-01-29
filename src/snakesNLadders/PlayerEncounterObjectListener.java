package snakesNLadders;

// this listener is invoked when any player encounters a snake or a ladder
public interface PlayerEncounterObjectListener {
	void playerEncounterSnake(Player P);
	void playerEncounterLadder(Player P);
}
