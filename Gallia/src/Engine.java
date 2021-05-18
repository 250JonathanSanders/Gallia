/*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
Name: Engine.java
Author: Jonathan Sanders
Date: 17.05.21
Purpose: Game engine that processes
input and manages output

-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
////////////IMPORTS////////////
import java.util.Scanner;

public class Engine {
	//Public variables regarding character inventory
	public static String strName = "", strInput = "", strOutput = "", strStory = "";
	public static int intHP = 0;
	public static boolean blSword = false, blPotion = false, blTorch = false, blBook = false, blFinished = false, blThrowaway = false;
	
	//Public variables regarding character strLocation
	public static int intX = 1, intY = 0;
	public static String strLocation = "Kronwell", strRoom = "Your house", strDesc = "You're in your house. It's quite empty. Not even a bed. You sleep on the floor. Head south to go to the town center.";
	
	//Map
	public static String[][][] Kronwell = 	{{{"wall","1a"},{"Your house", "You're in your house. It's quite empty. Not even a bed. You sleep on the floor. Head South to go to the town center.", "Nothing"},{"wall", "1c"}},
											{{"Storage shack","You are in an almost empty shack. There is a sword on the floor. You can go back East.","You are in an empty shack."},{"Town center","This is the town center. To the north is your house. To the east and west are two buildings. To the south is a path into the Forest."} , {"Shop","You are in an empty, abandoned shop. No one knows how long it's been here for. You can exit out west."}},
											{{"wall","3a"},{"Path","path1"},{"wall","3c"}}};
	public static String[][][] Forest = {{{"wall","1a"},{"wall","1a"},{"wall","1a"},{"wall","1a"},{"Path","path2"},{"wall","1c"}},
										{{"wall", "2a"},{"wall", "2a"},{"wall", "2a"},{"wall", "2a"},{"Forest","You are in the forest. It is dark and you are surrounded by trees. You can go east, or exit the forest by heading North,"},{"Forest","You are still in the forest, but you reach a corner. You can go west, or south."}},
										{{"wall", "2a"},{"Monster","Monster Fight"},{"Forest","You are still in the forest. You can go East or West."},{"Forest","You reach a corner of the forest. You can head West or South."},{"wall", "eee"},{"Forest", "You're still in the forest. You can go North, east, or South."},{"Monster","Monster Fight"}},
										{{"wall", "ee"},{"wall", "ee"},{"wall", "ee"},{"Forest","You reach a corner. You can go north or east."},{"Forest","You're at an intersection. You can go east, south, or west."},{"Forest","You reach a corner. You can go west or north."},{"wall", "ee"}},
										{{"Potion","It's an empty corner of the woods... Or so you thought. There's a potion on the ground.","It's an empty corner of the woods."},{"Forest","You're in a path through the woods. You can go west or east."},{"Forest", "You reach a corner. You can go west or south."},{"wall","eee"},{"Forest","You're still in the forest. You can go north or south."},{"wall","ee"},{"Torch","You're in a corner of the woods with a torch on the ground. You can go south.","It's an empty corner of the woods. You can go south."}},
										{{"wall","eee"},{"wall","eee"},{"Forest","You're at a fork in the road. You can head north, east, or south."}, {"Forest","You're still in the woods. You can head east or west."},{"Forest","You reach an intersection. You can head north, east, south, or west."},{"Forest","You are still in the woods. You can head east or west."},{"Forest","You're at a corner in the woods. You can go north or west."}},
										{{"wall","eee"},{"Forest","You're in the woods, at a corner. You can head east or south."},{"Forest","You're in the corner of the woods. You can go north or west."},{"wall","eee"},{"Forest","You're in an empty corner of the woods. You can go back north."},{"wall","eee"},{"wall","eee"}}};
	public static void main(String[] args){
	
		
		
		System.out.println("Welcome, adventurer! Would you like to start a new adventure, or load one in?");
		//Determine whether it's a new game or loading a game
		do {
			strInput = Input();
			
			if (strInput.toLowerCase().contains("new")){
				blThrowaway = true;
				NewGame();
			} else if(strInput.toLowerCase().contains("load")){
				blThrowaway = true;
				System.out.print("loading...");
			} else{
				System.out.println("Invalid selection. Try again. (HINT: Say \"New\" or \"Load\")");
			}
		} while (!blThrowaway);
		
	}
	
	//Method for input
	public static String Input() {
		//Scanner object
		Scanner scrInput = new Scanner(System.in);
		
		System.out.print("\n> ");
		//This is redundant, but it's for an extra space under the > [input]
		strInput = scrInput.nextLine();
		System.out.print("\n");
		return(strInput);
	}
	
	//Method for new game
	public static void NewGame(){
		strStory += "What is your name, adventurer?\n";
		System.out.print(strStory);
		
		strName = Input();
		strStory += "> " + strName + "\n";
		
		strOutput = "Alright, " + strName + "... That's an interesting name, by the way. Regardless, your story begins as such...\nYears ago, life was peaceful on the land of Gallia. However, one day the evil forces\ndecided to make themselves known. They had revived the orcs down at Kilburgh,\nreactivated Mt. Anbus, and destroyed the city of Brimmore from within.\nYour journey begins with you in Kronwell, your home town, on your way to save the world.\n";
		strStory += strOutput + "\n";
		
		System.out.println(strOutput);
		Game();
	}
	
	//Method for the actual rest of the game
	public static void Game(){
		do{
			//Print environment
			strOutput = "============\nCoordinates: (" + intX + ", " + intY + ")\nstrLocation: " + strLocation + "\nstrRoom: " + strRoom + "\nstrDesc: " + strDesc + "\n============\n";
			strStory += "\n" + strOutput;
			
			System.out.println(strOutput);
			//Input
			strInput = Input();
			strStory += "\n> " + strInput;
			
			//File saving
			
			
			//Inspecting
			
			
			//Using
			
			
			//Attack
			
			
			//Take
			
			
			//Moving
			if(strInput.toLowerCase().equals("n") || strInput.toLowerCase().contains("north")){
				//Determine strLocation
				if(strLocation.equals("Kronwell")){
					if(intY-1>=0) {//Can't walk through borders
						//can't walk into walls
						if (Kronwell[intY - 1][intX][0].equals("wall") || intY - 1 < 0) {
							System.out.println("You can't go that way!");
						} else {
							intY--;
						}
					} else{
						System.out.println("You can't go that way!");
					}
				} else if(strLocation.equals("Forest")) {
					if(intY-1>=0) {
						if (Forest[intY - 1][intX][0].equals("wall") || intY - 1 < 0) {
							System.out.println("You can't go that way!");
						} else {
							intY--;
						}
					} else{
						System.out.println("You can't go that way!");
					}
				}
			} else if(strInput.toLowerCase().equals("s") || strInput.toLowerCase().contains("south")){
				if(strLocation.equals("Kronwell")){
					if(intY+1<=6) {
						if (Kronwell[intY + 1][intX][0].equals("wall") || intY + 1 > 6) {
							System.out.println("You can't go that way!");
						} else {
							intY++;
						}
					} else{
						System.out.println("You can't go that way!");
					}
				} else if(strLocation.equals("Forest")) {
					if (intY+1<=6) {
						if (Forest[intY + 1][intX][0].equals("wall") || intY + 1 > 6) {
							System.out.println("You can't go that way!");
						} else {
							intY++;
						}
					} else{
						System.out.println("You can't go that way!");
					}
				}
			} else if(strInput.toLowerCase().equals("e") || strInput.toLowerCase().contains("east")){
				if(strLocation.equals("Kronwell")){
					if (intX+1<=6) {
						if (Kronwell[intY][intX + 1][0].equals("wall") || intX + 1 > 6) {
							System.out.println("You can't go that way!");
						} else {
							intX++;
						}
					} else{
						System.out.println("You can't go that way!");
					}
				} else if(strLocation.equals("Forest")) {
					if(intX+1<=6) {
						if (Forest[intY][intX + 1][0].equals("wall") || intX + 1 > 6) {
							System.out.println("You can't go that way!");
						} else {
							intX++;
						}
					} else{
						System.out.println("You can't go that way!");
					}
				}
			} else if(strInput.toLowerCase().equals("w") || strInput.toLowerCase().contains("west")){
				if(strLocation.equals("Kronwell")){
					if (intX-1 >= 0) {
						if (Kronwell[intY][intX - 1][0].equals("wall") || intX - 1 < 0) {
							System.out.println("You can't go that way!");
						} else {
							intX--;
						}
					} else {
						System.out.println("You can't go that way!");
					}
				} else if(strLocation.equals("Forest")) {
					if (intX-1>=0) {
						if (Forest[intY][intX - 1][0].equals("wall") || intX - 1 < 0) {
							System.out.println("You can't go that way!");
						} else {
							intX--;
						}
					} else {
						System.out.println("You can't go that way!");
					}
				}
			} else if(strInput.equals("strLocation")){
				System.out.print("");
			} else if(strInput.toLowerCase().equals("log")){
				System.out.println(strStory);
			} else {
				System.out.println("Invalid selection.");
			}
			
			if(strLocation.equals("Kronwell")){
				strDesc = Kronwell[intY][intX][1];
				strRoom = Kronwell[intY][intX][0];
				if (strRoom.equals("Path")){
					strLocation = "Forest";
					intY = 1;
					intX = 4;
					strRoom = Forest[intY][intX][0];
					strDesc = Forest[intY][intX][1];
				}
			} else if(strLocation.equals("Forest")){
				strRoom = Forest[intY][intX][0];
				strDesc = Forest[intY][intX][1];
				if (strRoom.equals("Path")){
					strLocation = "Kronwell";
					intY = 1;
					intX = 1;
					strRoom = Kronwell[intY][intX][0];
					strDesc = Kronwell[intY][intX][1];
				}
			}
		}while(true);
	}
}
