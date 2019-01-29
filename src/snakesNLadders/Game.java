package snakesNLadders;

import java.util.InputMismatchException;
import java.util.Scanner;

import exceptions.*;

public class Game {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int players=0,turns;
		System.out.println("Enter the number of Players:");
		while(players==0) {
			try {
				players = sc.nextInt();
				if(players ==0) {
					System.out.println("Please Enter a number(>0):");
				}
			}catch(InputMismatchException e) {
				System.out.println("Not a valid Input enter again");
				sc.nextLine();
			}
		}
		
		SnakesAndLadders board = new SnakesAndLadders();
		board.initSnakesNLadders();
		board.createPlayers(players);
		try {
			board.StartGame();
		} catch (NoPlayersException e) {
			System.out.println(e.getMessage());
		}
		while(true) {
			try {
				System.out.println("Enter No of turn to play(enter 0 to exit):");
				turns  = sc.nextInt();
				if(turns== 0)break;
				if(board.playNTurns(turns))break;
			}catch(InputMismatchException e) {
				System.out.println("Not a valid Input enter again");
				sc.nextLine();
			}	
		}
	}

}
