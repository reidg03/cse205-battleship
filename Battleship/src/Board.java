import java.util.Scanner;
//creates a board where ships can be added and coordinates can be attacked
//almost inherits image class but not really
public class Board {

	private String[] boardTemplate = {
			"-----------------------",
			"|   1 2 3 4 5 6 7 8 9 |",
			"| a . . . . . . . . . |",
			"| b . . . . . . . . . |",
			"| c . . . . . . . . . |",
			"| d . . . . . . . . . |",
			"| e . . . . . . . . . |",
			"| f . . . . . . . . . |",
			"| g . . . . . . . . . |",
			"| h . . . . . . . . . |",
			"| i . . . . . . . . . |",
			"-----------------------"
	};
	private Image myBoard = new Image(23, 12);
	private int numHits = 0;
	
	public Board() {
		myBoard.addImg(boardTemplate, 0, 0);
	}
	
	//attack a board
	public void attack(Board ships, Scanner scnr) {
		boolean done = false;
		String input = "";
		int x = 0;
		int y = 0;
		String[] winMsg = {
				"            You win!",
				"",
				"Game over. Restart to play again."
		};
		while (!done) {
			//get input coords to attack and check if they are valid
			input = scnr.nextLine(); 
			if (input.length() != 2) {
				GUI.bottomMessage("Invalid input! Must be a letter a-i followed by a number 1-9.");
				continue;
			}
			input = input.toUpperCase();
			y = input.charAt(0) - 65;
			x = input.charAt(1) - 49;
			if (!(0<=y && y<=8 && 0<=x && x<=8)) {
				GUI.bottomMessage("Invalid input! Must be a letter a-i followed by a number 1-9.");
				continue;
			}
			
			String attackedChar = ships.charAt(x, y);
			switch (attackedChar) {
				case ".": 
					//miss
					placeImgAt("O", x, y);
					ships.placeImgAt("O", x, y);
					GUI.bottomMessage("Miss! Enter anything to continue.");
					break;
				case "O":
					//already attacked here
					GUI.bottomMessage("Already attacked here! Enter the coords to attack (Ex. \"A1\", \"b2\", \"f7\")");
					continue;
				case "X":
					GUI.bottomMessage("Already hit here! Enter the coords to attack (Ex. \"A1\", \"b2\", \"f7\")");
					continue;
				case "B":
					//hit
					placeImgAt("X", x, y);
					ships.placeImgAt("X", x, y);
					ships.numHits++;
					//CHECK FOR WIN
					if (ships.numHits>9) {GUI.fullscreenMessage(winMsg); System.exit(0);}
					GUI.bottomMessage("Hit! Enter anything to continue.");
					break;
				default:
					GUI.bottomMessage("Invalid input! Enter the coords to attack.");
					break;
			}
			done = true;
		}
		scnr.nextLine();
	}
	
	//return char at coords, but uses board coords
	public String charAt(int x, int y) {
		x*=2;
		x+=4;
		y+=2;
		return myBoard.charAt(x, y);
	}
	
	//place image at board coords
	public void placeImgAt(String[] img, int x, int y) {
		x*=2;
		x+=4;
		y+=2;
		myBoard.addImg(img, x, y);
	}
	public void placeImgAt(String img, int x, int y) {
		x*=2;
		x+=4;
		y+=2;
		myBoard.addImg(img, x, y);
	}
	
	//return string image of board
	public String toString() {
		return myBoard.getImage();
	}
	
	//place a boat on the board
	public void placeBoat(int x1, int y1, int length, String rot, Scanner scnr) {
		GUI.clearMessages();
		
		int x = x1;
		int y = y1;
		String input = "";
		boolean inLoop = true;
		placeImgAt(boatImage(length, rot), x, y);
		GUI.print();
		while (inLoop){
			input = scnr.nextLine();
			input.toLowerCase();
			switch (input) {
			case "`":
				//to do nothing
				break;
			case "w":
				if (onBoard(x,y-1,length,rot)) {
					if (myBoard.getImageListSize()>1) myBoard.removeLast();
					y--;
					placeImgAt(boatImage(length, rot), x, y);
					GUI.bottomMessage("");
				} else {
					GUI.bottomMessage("Can't go further!");
				}
				break;
			case "a":
				if (onBoard(x-1,y,length,rot)) {
					if (myBoard.getImageListSize()>1) myBoard.removeLast();
					x--;
					placeImgAt(boatImage(length, rot), x, y);
					GUI.bottomMessage("");
				} else {
					GUI.bottomMessage("Can't go further!");
				}
				
				break;
			case "s":
				if (onBoard(x,y+1,length,rot)) {
					if (myBoard.getImageListSize()>1) myBoard.removeLast();
					y++;
					placeImgAt(boatImage(length, rot), x, y);
					GUI.bottomMessage("");
				} else {
					GUI.bottomMessage("Can't go further!");
				}
				break;
			case "d":
				if (onBoard(x+1,y,length,rot)) {
					if (myBoard.getImageListSize()>1) myBoard.removeLast();
					x++;
					placeImgAt(boatImage(length, rot), x, y);
					GUI.bottomMessage("");
				} else {
					GUI.bottomMessage("Can't go further!");
				}
				break;
			case "r":
				rot = (rot.equals("v"))? "h" : "v";
				if  (onBoard(x,y,length,rot)) {
					if (myBoard.getImageListSize()>1) myBoard.removeLast();
					placeImgAt(boatImage(length, rot), x, y);
					GUI.bottomMessage("");
				} else {
					rot = (rot.equals("v"))? "h" : "v";
					GUI.bottomMessage("Can't rotate here!");
				}
				break;
			case "p":
				if (canPlace(x, y, length, rot)) {
					placeImgAt(boatImage(length, rot, "B"), x, y);
					inLoop=false;
				} else {
					GUI.bottomMessage("Can't place on an already existing boat!");
				}
				break;
				
			default:
				GUI.bottomMessage("Invalid input!");
			}
		}
	}
	
	//returns if a ship with these attributes would be fully on the board
	private boolean onBoard(int x, int y, int length, String rot) {
		boolean can = true;
		if (rot.equals("v")) {
			if (!(0<=y && y+length-1<=8)) return false;
			if (!(0<=x && x<=8)) return false;
		} else {
			if (!(0<=x && x+length-1<=8)) return false;
			if (!(0<=y && y<=8)) return false;
		}
		return can;
	}
	//returns if a ship can be placed at this location on the board
	//checks if fully on board and not overlapping other ships
	private boolean canPlace(int x, int y, int length, String rot) {
		boolean can = true;
		if (!onBoard(x,y,length,rot)) can = false;
		myBoard.removeLast();
		if (rot.equals("v")) {
			for (int i=0;i<length;i++) {
				if (charAt(x,y+i).equals("B")) {
					can = false;
				}
			}
		} else {
			for (int i=0;i<length;i++) {
				if (charAt(x+i,y).equals("B")) {
					can = false;
				}
			}
		}
		placeImgAt(boatImage(length, rot, "P"), x, y);
		return can;
	}
	
	//create string image of boat of given length and rotation
	private String[] boatImage(int length, String rot) {
		String[] s1 = null;
		if (rot.equals("v")) {
			s1 = new String[length];
			for (int i=0;i<length;i++)
				s1[i]="P";
		} else {
			s1 = new String[1];
			s1[0]="";
			for (int i=0;i<length-1;i++)
				s1[0]+="P-";
			s1[0]+="P";
		}
		return s1;
	}
	private String[] boatImage(int length, String rot, String symbol) {
		String[] s1 = null;
		if (rot.equals("v")) {
			s1 = new String[length];
			for (int i=0;i<length;i++)
				s1[i]=symbol;
		} else {
			s1 = new String[1];
			s1[0]="";
			for (int i=0;i<length-1;i++)
				s1[0]+=symbol + "-";
			s1[0]+=symbol;
		}
		return s1;
	}
	
}
