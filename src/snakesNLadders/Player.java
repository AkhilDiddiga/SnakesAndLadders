package snakesNLadders;

public class Player {
	private int playerNo; 
	private String playerName; //custom player name
	private int currentPos; // current position of the player
	private PlayerPositionChangeListener listener; // this listener is called when player position is updated
	
	public Player(int i) {
		this.playerNo = i;
		this.currentPos = 1;
	}
	
	public void setPlayerPositionChangeListener(PlayerPositionChangeListener changeListener) {
		this.listener = changeListener;
	}
	
	public int getCurrPositon() {
		return this.currentPos;
	}
	
	public void setCurrPosition(int newPos) {
		int oldPos = currentPos;
		currentPos = newPos;
		if(listener!=null) {
			listener.onPlayerPositionChanged(oldPos, newPos);
		}
	}
	
	public void setPlayerName(String name) {
		playerName = name;
	}
	
	@Override
	public String toString() {
		return playerName!=null?playerName:String.format("%d", playerNo+1);
	}
}
