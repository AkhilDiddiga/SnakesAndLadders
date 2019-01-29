package snakesNLadders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import exceptions.*;


public class SnakesAndLadders {
	private class PlayerPriority implements Comparable<PlayerPriority>{
		private Player player;
		private int priority;
		public PlayerPriority(Player p, int priority) {
			this.player = p;
			this.priority= priority;
		}
		@Override
		public int compareTo(PlayerPriority o) {
			return o.priority - this.priority;
		}
		
		@Override
		public String toString() {
			return player!=null?player.toString():super.toString();
		}
	}
	
	final int WINNING_SCORE = 100;  //marks the end of the game 
	private HashMap<Integer,Integer> snakes;
	private HashMap<Integer,Integer> ladders;
	private ArrayList<PlayerPriority> playSequence;
	private ArrayList <Player> players;
	private Random r;
	private int playerCount;
	private PlayerEncounterObjectListener listener;
	
	//creates board and initializes with given number of players
	public SnakesAndLadders() {
		this.snakes = new HashMap<Integer, Integer>();
		this.ladders = new HashMap<Integer, Integer>();
		this.playSequence = new ArrayList<PlayerPriority>();
		this.players = new ArrayList<Player>();
		r = new Random();
		playerCount= 1;
	}
	
	//set this to do custom event when any object is encountered by the player
	public void setPlayerEncounterObjectListener(PlayerEncounterObjectListener listener) {
		this.listener = listener;
	}
	
	//adds a snake at the given location
	//throws exception if there is a ladder already starts at that location
	public void addSnake(int head, int tail) throws LadderPresentException,OutOfBoundsException {
		if(ladders.containsKey(head))throw new LadderPresentException();
		if(head<1||head>100 || tail<1 || tail>100)throw new OutOfBoundsException();
		snakes.put(head, tail);
	}
	
	//removes a snake at given location if present
	public void removeSnake(int head) {
		snakes.remove(head);
	}
	
	//removes all snakes
	public void clearAllSnakes() {
		snakes.clear();
	}
	
	//adds a ladder at the given location
	//throws exception if there is a snake's head is present at that location
	public void addLadder(int start, int end) throws SnakePresentException,OutOfBoundsException{
		if(snakes.containsKey(start))throw new SnakePresentException();
		if(start<1||start>100 || end<1 || end>100)throw new OutOfBoundsException();
		ladders.put(start, end);
	}
	
	//removes a ladder starting at given location if present
	public void removeLadder(int start) {
		ladders.remove(start);
	}
	
	//removes all ladders
	public void clearAllLadders() {
		ladders.clear();
	}
	
	//initializes the board with creating snakes and ladders
	//Override this to create your custom locations of snakes and ladders
	public void initSnakesNLadders() {
		//creating Snakes;	
		try {
			addSnake(31,14);
			addSnake(37, 17);
			addSnake(73,53);
			addSnake(78, 39);
			addSnake(92,35);
			addSnake(99, 7);
		} catch(LadderPresentException e) {
			System.out.println(e.getMessage());
		}catch(OutOfBoundsException o) {
			System.out.println(o.getMessage());
		}
		//creating ladders
		try {
			addLadder(5, 25);
			addLadder(10,29);
			addLadder(22, 41);
			addLadder(28,55);
			addLadder(44, 95);
			addLadder(70,89);
		}catch(SnakePresentException e) {
			System.out.println(e.getMessage());
		}catch(OutOfBoundsException o) {
			System.out.println(o.getMessage());
		}
		
	}
	
	//creates the player list
	public void createPlayers(int NoPlayers) {
		for(int i = 0; i<NoPlayers ;i++) {
			players.add(new Player(playerCount++));
		}
	}
	
	//removes player at given index from the game
	public void removePlayerAt(int index) {
		Player p = players.get(index);
		players.remove(index);
		int i;
		for(i=0;i<playSequence.size();i++) {
			if(playSequence.get(i).player == p) {
				break;
			}
		}
		if(i<playSequence.size()) {
			playSequence.remove(i);
		}
	}
	
	//removes player given the player name
	public void removePlayer(String playerName) {
		for(int i=0;i<players.size();i++) {
			if(players.get(i).toString().equals(playerName)) {
				removePlayerAt(i);
				break;
			}
		}
	}
	
	//removes all players
	public void removeAllPlayer() {
		players.clear();
		playSequence.clear();
	}
	
	//playerNo starts from 0
	public Player getPlayer(int playerNo) {
		if(players.size()>playerNo) {
			return players.get(playerNo);
		}
		return null;
	}
	
	//make each player roll once and makes the play sequence
	public void StartGame() throws NoPlayersException{
		if(players.size()==0)throw new NoPlayersException();
		System.out.println(String.format("The game started with %d players", players.size()));
		playSequence.clear();
		int roll;
		for(Player p: players) {
			roll = rollDice();
			playSequence.add(new PlayerPriority(p, roll));
			System.out.println(String.format("player %s rolls die scoring %d", p, roll));
		}
		Collections.sort(playSequence);
		System.out.println(String.format("player %s gets to start\n", playSequence.get(0)));
	}
	
	// simulates dice roll using random function
	private int rollDice() {
		return r.nextInt(6)+1; // nextInt(6) gives number in range [0,6) so add 1 to make [1,6]  
	}
	
	//roles dice once and updates the payer position
	private boolean rollDiceAndUpdatePlayerValue(Player p) {
		int roll = rollDice();
		System.out.println(String.format("player %s rolls %d", p, roll));
		int currPlayerPos = p.getCurrPositon();
		currPlayerPos+= roll;
		if(currPlayerPos>WINNING_SCORE) {
			currPlayerPos-=roll;
		}else {
			while(true) {
				p.setCurrPosition(currPlayerPos);
				if(currPlayerPos == WINNING_SCORE) {
					return true;
				}
				else if(snakes.containsKey(currPlayerPos)) {
					currPlayerPos = snakes.get(currPlayerPos); 
					if(listener!=null)listener.playerEncounterSnake(p);
				}
				else if(ladders.containsKey(currPlayerPos)) {
					currPlayerPos = ladders.get(currPlayerPos);
					if(listener!=null)listener.playerEncounterLadder(p);
				}
				else {
					break;
				}		
			}
		}
		return false;
	}
	
	//plays N turns and returns true if the one of the player won the game else false
	public boolean playNTurns(int turns) {
		boolean isGameFinished = false;
		while(turns>0 && !isGameFinished) {
			turns--;
			for(PlayerPriority p: playSequence) {
				if(rollDiceAndUpdatePlayerValue(p.player)) {
					System.out.println(String.format("player %s won the game", p));
					isGameFinished= true;
					break;
				}
			}
			System.out.println("");
		}
		printCurrentPositions();
		return isGameFinished;
	}
	
	//prints current positions of the players
	public void printCurrentPositions() {
		System.out.println("The positions after the sequence of rolls above");
		for(Player p: players) {
			System.out.println(String.format("player %s - %d", p, p.getCurrPositon()));
		}
		System.out.println("");
	}
	

}
