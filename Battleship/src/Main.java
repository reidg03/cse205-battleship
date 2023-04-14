import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		//setup code
		Scanner scnr = new Scanner(System.in);
		GUI.setup();
		
		//intro
		String[] introMessage = {
			"Welcome to battleship! Make sure you can see the entire box on the screen.",
			"Player 1, enter anything to begin placing your ships.",
		};
		GUI.fullscreenMessage(introMessage);
		
		//player 1 places ships
		String[] placingInstructions = {
			"Place your 5 ships using the controls below.",
			"P represents the current ship you are placing.",
			"B represents placed ships.",
			"",
			"Enter W,A,S,D to move ship.",
			"Enter R to rotate ship.",
			"Enter P to place ship."
		};
		scnr.nextLine();
		Board p1ships = new Board();
		GUI.leftBoard = p1ships;
		GUI.clearMessages();
		int removeInstructions = GUI.addImg(placingInstructions, 33, 7);
		p1ships.placeBoat(0, 0, 4, "v", scnr);
		p1ships.placeBoat(0, 0, 3, "v", scnr);
		p1ships.placeBoat(0, 0, 2, "v", scnr);
		p1ships.placeBoat(0, 0, 1, "v", scnr);
		
		//player 2 places ships
		String[] pass = {
			"Your turn has ended.",
			"Pass the device to the other player.",
			"Press any button to continue."
		};
		GUI.fullscreenMessage(pass);
		scnr.nextLine();
		Board p2ships = new Board();
		GUI.leftBoard = p2ships;
		GUI.clearMessages();
		p2ships.placeBoat(0, 0, 4, "v", scnr);
		p2ships.placeBoat(0, 0, 3, "v", scnr);
		p2ships.placeBoat(0, 0, 2, "v", scnr);
		p2ships.placeBoat(0, 0, 1, "v", scnr);
		
		//start actual game
		GUI.removeAt(removeInstructions); //remove placing instructions
		
		Board p1attacks = new Board();
		Board p2attacks = new Board();
		
		//game cycle until a player wins
		GUI.addImg("Enemy Board:", 10, 4);
		GUI.addImg("Your Board:", 78, 4);
		GUI.addImg("Enter coords to attack:", 4, GUI.getHeight()-6);
		GUI.addImg("(Ex. \"A1\", \"b2\", \"f7\")", 4, GUI.getHeight()-5);
		
		String whichPlayer = "Current turn: Player 1";
		int whereToRemove = GUI.addImg("SHOULD NEVER APPEAR", 50,3);
		
		while (!GUI.won) {	
			whichPlayer = "Current turn: Player 1";	
			GUI.removeAt(whereToRemove);
			whereToRemove = GUI.addImg(whichPlayer, GUI.getWidth()/2-11, 2);
			GUI.addImg(whichPlayer, 4, 2);
			
			GUI.clearMessages();
			
			GUI.fullscreenMessage(pass);
			scnr.nextLine();
			
			GUI.rightBoard = p1ships;	
			GUI.leftBoard = p1attacks;
			GUI.clearMessages();
			GUI.bottomMessage("");
			p1attacks.attack(p2ships, scnr);
			////////////////////////////////////////////////////////////////
			whichPlayer = "Current turn: Player 2";
			GUI.removeAt(whereToRemove);
			whereToRemove = GUI.addImg(whichPlayer, GUI.getWidth()/2-11, 2);
			GUI.addImg(whichPlayer, 4, 2);
			
			GUI.fullscreenMessage(pass);
			scnr.nextLine();
			
			GUI.rightBoard = p2ships;	
			GUI.leftBoard = p2attacks;
			GUI.clearMessages();
			GUI.bottomMessage("");
			p2attacks.attack(p1ships, scnr);
		}
		GUI.fullscreenMessage("SHOULD NEVER APPEAR. END OF MAIN");
		
	}
	
	
}
