/*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
Name: Engine.java
Author: Jonathan Sanders
Date: 17.05.21
Purpose: Game engine that processes
input and manages output
-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
////////////IMPORTS////////////
import java.util.Scanner;
import java.io.*;

public class Engine {
	//Public variables regarding character inventory
	public static String strName = "", strInput = "", strOutput = "", strStory = "";
	public static int intHP = 100, intLow = 0, intHigh = 0, intPotion = 0, intDamage = 0;
	public static boolean blSword = false, blPotion = false, blTorch = false, blFinished = false, blThrowaway = false, blInCombat = false;
	
	//Public variables for monsters
	public static int intMonHP = 50, intMonLow = 0, intMonHigh = 0, intMon2HP = 50, intMon2Low = 0, intMon2High = 0, intMon3HP = 50, intMon3Low = 0, intMon3High = 0;
	public static boolean blMon1Dead = false, blMon2Dead = false, blMon3Dead = false;
	
	//Public variables regarding character strLocation
	public static int intX = 1, intY = 0;
	public static String strLocation = "Kronwell", strRoom = "Your house", strDesc = "You're in your house. It's quite empty. Not even a bed. You sleep on the floor. Head south to go to the town center.";
	
	//Map
	//{Room name, description, secondary description} is the area layout
	public static String[][][] Kronwell = 	{{{"wall"},{"Your house", "You're in your house. It's quite empty. Not even a bed. You sleep on the floor. Head South to go to the town center.", "Nothing"},{"wall"}},
			{{"Storage shack","You are in an almost empty shack. There is a sword on the floor. You can go back East.","You are in an empty shack."},{"Town center","This is the town center. To the north is your house. To the east and west are two buildings. To the south is a path into the Forest."} , {"Shop","You are in an empty, abandoned shop. No one knows how long it's been here for. You can exit out west."}},
			{{"wall"},{"Path","path1"},{"wall"}}};
	public static String[][][] Forest = {{{"wall"},{"wall"},{"wall"},{"wall"},{"Path","path2"},{"wall"}},
			{{"wall"},{"wall"},{"wall"},{"wall"},{"Forest","You are in the forest. It is dark and you are surrounded by trees. You can go east, or exit the forest by heading north,"},{"Forest","You are still in the forest, but you reach a corner. You can go west, or south."},{"wall"}},
			{{"wall"},{"Forest","You arrive at a corner of the woods. You can go back east."},{"Forest","You are still in the forest. You can go east or west."},{"Forest","You reach a corner of the forest. You can head west or south."},{"wall"},{"Monster1","You come across a monster with " + intMonHP + " HP! You can go north, but you can't go south until you beat this monster.", "You come across an empty clearing. You can go north or south."},{"wall"}},
			{{"wall"},{"wall"},{"wall"},{"Forest","You reach a corner. You can go north or east."},{"Forest","You're at an intersection. You can go east, south, or west."},{"Forest","You reach a corner. You can go west or north."},{"wall"}},
			{{"Potion","It's an empty corner of the woods... Or so you thought. There's a potion on the ground.","It's an empty corner of the woods.",},{"Monster2","You come across a monster with " + intMon2HP + " HP! You can go east, but you can't go west until you beat this monster.", "You come across an empty clearing. You can go east or west."},{"Forest", "You reach a corner. You can go west or south."},{"wall"},{"Forest","You're still in the forest. You can go north or south."},{"wall"},{"Torch","You're in a corner of the woods with a torch on the ground. You can go south.","It's an empty corner of the woods. You can go south."}},
			{{"wall"},{"wall"},{"Forest","You're at a fork in the road. You can head north, east, or south."}, {"Forest","You're still in the woods. You can head east or west."},{"Forest","You reach an intersection. You can head north, east, south, or west."},{"Forest","You are still in the woods. You can head east or west."},{"Forest","You're at a corner in the woods. You can go north or west."}},
			{{"wall"},{"Monster3","You come across a monster with " + intMon3HP + " HP! You can go east, but you can't go south until you beat this monster.", "You come across an empty clearing. You can go east or south."},{"Forest","You're in the corner of the woods. You can go north or west."},{"wall"},{"Forest","You're in an empty corner of the woods. You can go back north."},{"wall"},{"wall"}},
			{{"wall"},{"Path","path3"},{"wall"},{"wall"},{"wall"},{"wall"},}};
	
	
	public static void main(String[] args){
		System.out.print("Welcome, adventurer! Would you like to start a new adventure, or load one in?");
		
		//Determine whether it's a new game or loading a game
		while (!blThrowaway) {
			strInput = Input();
			
			if (strInput.toLowerCase().contains("new")){
				blThrowaway = true;
				NewGame();//new game method
				
			} else if(strInput.toLowerCase().contains("load")){
				blThrowaway = true;
				System.out.print("Which adventurer would you like to load?\n");
				do{
					blThrowaway = false;
					strInput = Input();
					if (!(new File("Saves\\" + strInput + ".txt").exists())){
						System.out.print("Invalid adventurer name!\n"); //Check for the filename. This is caps sensitive
					} else{
						blThrowaway = true;
						SaveLoad.Load(strInput);
					}
				} while(!blThrowaway);
				Game();//send to game with loaded file
			} else{
				System.out.print("Invalid selection. Try again. (HINT: Say \"New\" or \"Load\")\n");
			}
		}
	}
	
	//Method for input
	public static String Input() {
		//Scanner object
		Scanner scrInput = new Scanner(System.in);
		
		System.out.print("\n\n> ");
		strInput = scrInput.nextLine();
		//This is redundant, but it's for an extra space under the > [input]
		System.out.print("\n");
		return(strInput);
	}
	
	//Method for new game
	public static void NewGame(){
		//Generate random sword damage and potion values for character.
		intLow = (int) ((11) * Math.random());
		intHigh = (int) (( 25 - 11 + 1) * Math.random() + 11);
		intPotion = (int) ((50 - 10 + 1) * Math.random() + 10);
		
		strStory += "What is your name, adventurer?";
		System.out.print(strStory);
		
		strName = Input();
		strStory += "> " + strName + "\n"; //Store on strStory. strStory will be used for the "log" command
		
		strOutput = "Alright, " + strName + "... That's an interesting name, by the way. Regardless, your story begins as such...\nYears ago, life was peaceful on the land of Gallia. However, one day the evil forces\ndecided to make themselves known. They had reactivated Mt. Anbus, and destroyed the city of Brimmore from within.\nYour journey begins with you in Kronwell, your home town, on your way to save the world.\n";
		strStory += strOutput + "\n\n";
		strOutput += "\n\n";
		System.out.print(strOutput);
		
		strOutput = "You're in your house. It's quite empty. Not even a bed. You sleep on the floor. Head South to go to the town center.\n";
		strStory += strOutput + "\n";
		Game(); //Send to game with new values
	}
	
	//Method for monster fight
	public static void MonsterFight(String Monster){
		//Determine which monster the player is fighting
		if (Monster.equals("Monster1")) {
			//player does damage
			intDamage = (int) ((intHigh - intLow + 1) * Math.random() + intLow);
			intMonHP = intMonHP - intDamage;
			if (intMonHP < 0){
				intMonHP = 0;
			}
			strOutput = "You have done " + intDamage + " points of damage! The monster now has " + intMonHP + " HP!\n";
			
			//Check if monster is dead
			if (intMonHP == 0){
				strOutput += "You have defeated the monster!\n";
				blInCombat = false;
				blMon1Dead = true;
				strDesc = Forest[intY][intX][2];
			} else {
				//Monster does damage
				intDamage = (int) ((20 - 2 + 1) * Math.random() + 2);
				intHP = intHP - intDamage;
				if (intHP < 0) {
					intHP = 0;
				}
				strOutput += "You have received " + intDamage + " points of damage! You have " + intHP + " HP!";
			}
		} else if (Monster.equals("Monster2")) {
			//player does damage
			intDamage = (int) ((intHigh - intLow + 1) * Math.random() + intLow);
			intMon2HP = intMon2HP - intDamage;
			if (intMon2HP < 0){
				intMon2HP = 0;
			}
			strOutput = "You have done " + intDamage + " points of damage! The monster now has " + intMon2HP + " HP!\n";
			
			//Check if monster is dead
			if (intMon2HP == 0){
				strOutput += "You have defeated the monster!\n";
				blInCombat = false;
				blMon2Dead = true;
				strDesc = Forest[intY][intX][2];
			} else {
				//Monster does damage
				intDamage = (int) ((20 - 2 + 1) * Math.random() + 2);
				intHP = intHP - intDamage;
				if (intHP < 0) {
					intHP = 0;
				}
				strOutput += "You have received " + intDamage + " points of damage! You have " + intHP + " HP!";
			}
		} else if (Monster.equals("Monster3")) {
			//player does damage
			intDamage = (int) ((intHigh - intLow + 1) * Math.random() + intLow);
			intMon3HP = intMon3HP - intDamage;
			if (intMon3HP < 0){
				intMon3HP = 0;
			}
			strOutput = "You have done " + intDamage + " points of damage! The monster now has " + intMon3HP + " HP!\n";
			
			//Check if monster is dead
			if (intMon3HP == 0){
				strOutput += "You have defeated the monster!\n";
				blInCombat = false;
				blMon3Dead = true;
				strDesc = Forest[intY][intX][2];
			} else {
				//Monster does damage
				intDamage = (int) ((20 - 2 + 1) * Math.random() + 2);
				intHP = intHP - intDamage;
				if (intHP < 0) {
					intHP = 0;
				}
				strOutput += "You have received " + intDamage + " points of damage! You have " + intHP + " HP!";
			}
		}
		//Make death possible
		if (intHP == 0){
			strOutput += "You have died!";
			blFinished = true;
		}
		
	}
	
	
	//Method for the actual rest of the game
	public static void Game(){
		do{
			//Print output and store it for loading
			strStory += "\n\n" + strOutput;
			System.out.print(strOutput);
			
			//Input
			strInput = Input();
			strStory += "\n\n> " + strInput;
			
			//File saving/loading
			if (strInput.toLowerCase().contains("save")){
				SaveLoad.Save();//save method using encryption
			} else if(strInput.toLowerCase().contains("load")){
				blThrowaway = true;
				System.out.print("Which adventurer would you like to load?\n");
				//loading just like in the main method
				do{
					blThrowaway = false;
					strInput = Input();
					if (!(new File("Saves\\" + strInput + ".txt").exists())){
						System.out.print("Invalid adventurer name!\n");
					} else{
						blThrowaway = true;
						SaveLoad.Load(strInput);
					}
				} while(!blThrowaway);
			}
			
			//Inspecting
			else if(strInput.toLowerCase().contains("inspect")){
				//Determine what's being inspected
				if (strInput.toLowerCase().contains("sword")){
					if (blSword){//If user has a sword
						strOutput = "You inspect your sword. Its damage ranges between " + intLow + " and " + intHigh + " points of damage.";
						continue;
					} else{
						strOutput = "You don't have a sword!";
						continue;
					}
				} else if(strInput.toLowerCase().contains("potion")){
					if (blPotion) { //If user has a potion
						strOutput = "You inspect your potion, and determine that it will give you " + intPotion + " health points in a tricky situation.";
						continue;
					} else{
						strOutput = "You don't have a potion!";
						continue;
					}
				} else if(strInput.toLowerCase().contains("torch")){
					if (blTorch){ //If user has a torch
						strOutput = "You inspect your torch. This thing seems handy.";
						continue;
					} else{
						strOutput = "You don't have a torch!";
						continue;
					}
				} else{ //If the user types in any random item like "inspect cxnjktydrsf"
					strOutput = "You don't have that item!";
					continue;
				}
			}
			
			//Using items
			else if(strInput.toLowerCase().contains("use")){
				//Determines item
				if (strInput.toLowerCase().contains("sword")){
					if (blSword){
						if (blInCombat){ //Check if in combat
							//Determine which monster is engaged
							if (strRoom.equals("Monster1")){
								MonsterFight("Monster1");
							} else if(strRoom.equals("Monster2")){
								MonsterFight("Monster2");
							} else if(strRoom.equals("Monster3")){
								MonsterFight("Monster3");
							}
							//If user is not dead
							if (intHP <0) {
								strOutput += "What will you do now?";
							}
							continue;
						} else {
							strOutput = "You are not in combat!";
							continue;
						}
					} else{ //Won't attack or be attacked
						strOutput = "You do not have a sword!";
						continue;
					}
				} else if(strInput.toLowerCase().contains("potion")){
					if (blPotion){
						blPotion = false;
						if(intHP + intPotion > 100){
							intHP = 100; //Makes sure the health cap is 100
						} else{
							intHP += intPotion;
						}
						strOutput = "Your HP has been restored to " + intHP + " HP!";
						continue;
					}
					else {
						strOutput = "You do not have a potion!";
						continue;
					}
				} else if(strInput.toLowerCase().contains("torch")){
					if (blTorch){
						strOutput = "You light up your torch.";
						continue;
					} else{
						strOutput = "You do not have a torch!";
						continue;
					}
				} else{
					strOutput = "You do not have that item!"; //Once again, just in case they type in "use esanktjswaeui8"
					continue;
				}
			} else if(strInput.toLowerCase().contains("attack")){ //Repeat of "use sword"
				if (blSword){
					if (blInCombat){ //Check if in combat
						//Determine which monster is engaged
						if (strRoom.equals("Monster1")){
							MonsterFight("Monster1");
						} else if(strRoom.equals("Monster2")){
							MonsterFight("Monster2");
						} else if(strRoom.equals("Monster3")){
							MonsterFight("Monster3");
						}
						//If user is not dead
						if (intHP <0) {
							strOutput += "What will you do now?";
						}
						continue;
					} else {
						strOutput = "You are not in combat!";
						continue;
					}
				} else{
					strOutput = "You do not have a sword!";
					continue;
				}
			}
			
			
			
			//Picking up items
			else if(strInput.toLowerCase().contains("take") || strInput.toLowerCase().contains("pick up") || strInput.toLowerCase().contains("pickup")){ //multiple options
				//Determine what's being picked up
				if(strInput.toLowerCase().contains("sword")){
					if(strRoom.equals("Storage shack")){ //If the user is in the room with the sword
						if(!blSword){
							strOutput = "You pick up the sword.";
							blSword = true;
							continue;
						} else{
							strOutput = "You already picked up the sword!";
							continue;
						}
					} else{
						strOutput = "There is nothing you can take!";
						continue;
					}
				} else if(strInput.toLowerCase().contains("potion")){
					if(strRoom.equals("Potion")){
						if(!blPotion){
							strOutput = "You pick up the potion.";
							blPotion = true;
							continue;
						} else{
							strOutput = "You already picked up the potion!";
							continue;
						}
					} else{
						strOutput = "There is nothing you can take!";
						continue;
					}
				} else if(strInput.toLowerCase().contains("torch")){
					if(strRoom.equals("Torch")){
						if(!blTorch){
							strOutput = "You pick up the torch.";
							blTorch = true;
							continue;
						} else{
							strOutput = "You already picked up the torch!";
							continue;
						}
					} else{
						strOutput = "There is nothing you can take!";
						continue;
					}
				} else{
					strOutput = "There is nothing you can take!";
					continue;
				}
			}
			
			//Moving
			else if(strInput.toLowerCase().equals("n") || strInput.toLowerCase().contains("north")){
				//Determine Location
				if(strLocation.equals("Kronwell")){
					if(intY-1>=0) {//Check for any potential obstacles when travelling north
						if (Kronwell[intY - 1][intX][0].equals("wall") || intY - 1 < 0) {
							strOutput = "You can't go that way!";
							continue;
						} else {
							intY--;
						}
					} else{
						strOutput = "You can't go that way!";
						continue;
					}
				} else if(strLocation.equals("Forest")) {
					if(intY-1>=0) { //Check for any potential obstacles when travelling north
						if (Forest[intY - 1][intX][0].equals("wall") || intY - 1 < 0) {
							strOutput = "You can't go that way!";
							continue;
						} else {
							intY--;
						}
					} else{
						strOutput = "You can't go that way!";
						continue;
					}
				} //repeat of moving function, just south
			} else if(strInput.toLowerCase().equals("s") || strInput.toLowerCase().contains("south")){
				if(strLocation.equals("Kronwell")){
					if(intY+1<=7) {
						if (Kronwell[intY + 1][intX][0].equals("wall") || intY + 1 > 7) {
							strOutput = "You can't go that way!";
							continue;
						} else {
							intY++;
						}
					} else{
						strOutput = "You can't go that way!";
						continue;
					}
				} else if(strLocation.equals("Forest")) {
					if (intY+1<=7) {
						if (Forest[intY + 1][intX][0].equals("wall") || intY + 1 > 7 || (strRoom.equals("Monster1") && !blMon1Dead)) {
							strOutput = "You can't go that way!";
							continue;
						} else {
							intY++;
						}
					} else{
						strOutput = "You can't go that way!";
						continue;
					}
				}//repeat of moving function, just east
			} else if(strInput.toLowerCase().equals("e") || strInput.toLowerCase().contains("east")){
				if(strLocation.equals("Kronwell")){
					if (intX+1<=2) {
						if (Kronwell[intY][intX + 1][0].equals("wall") || intX + 1 > 2) {
							strOutput = "You can't go that way!";
							continue;
						} else {
							intX++;
						}
					} else{
						strOutput = "You can't go that way!";
						continue;
					}
				} else if(strLocation.equals("Forest")) {
					if(intX+1<=6) {
						if (Forest[intY][intX + 1][0].equals("wall") || intX + 1 > 6) {
							strOutput = "You can't go that way!";
							continue;
						} else {
							intX++;
						}
					} else{
						strOutput = "You can't go that way!";
						continue;
					}
				}//repeat of moving function, just west
			} else if(strInput.toLowerCase().equals("w") || strInput.toLowerCase().contains("west")){
				if(strLocation.equals("Kronwell")){
					if (intX-1 >= 0) {
						if (Kronwell[intY][intX - 1][0].equals("wall") || intX - 1 < 0) {
							strOutput = "You can't go that way!";
							continue;
						} else {
							intX--;
						}
					} else {
						strOutput = "You can't go that way!";
						continue;
					}
				} else if(strLocation.equals("Forest")) {
					if (intX-1>=0) {
						if (Forest[intY][intX - 1][0].equals("wall") || intX - 1 < 0) {
							strOutput = "You can't go that way!";
							continue;
						} else {
							intX--;
						}
					} else {
						strOutput = "You can't go that way!";
						continue;
					}
				}
			}
			//Look around just prints the description
			else if(strInput.toLowerCase().contains("look around")){
				strOutput = strDesc;
			} else if(strInput.toLowerCase().equals("log")){ //This is the story so far
				System.out.println(strStory);
			} else { //if all else fails
				strOutput = "Invalid selection.";
				continue;
			}
			//loop isn't broken up to this point when the user moves so this will run, determining the description of the area.
			if(strLocation.equals("Kronwell")){
				strDesc = Kronwell[intY][intX][1];
				strRoom = Kronwell[intY][intX][0];
				//Check for Kronwell specific events
				if (strRoom.equals("Storage shack") && blSword){ //if user has the sword
					strDesc = Kronwell[intY][intX][2];
				}
				strOutput = strDesc;
				if (strDesc.equals("path1")){ //Path from kronwell to forest
					strLocation = "Forest";
					intY = 1;
					intX = 4;
					strRoom = Forest[intY][intX][0];
					strDesc = Forest[intY][intX][1];
					strOutput = strDesc;
				}
			} else if(strLocation.equals("Forest")){
				strRoom = Forest[intY][intX][0];
				strDesc = Forest[intY][intX][1];
				//Check for Forest specific events
				if (strRoom.equals("Potion") && blPotion) { //If user has potion
					strDesc = Forest[intY][intX][2];
				} else if (strRoom.equals("Torch") && blTorch){
					strDesc = Forest[intY][intX][2];
				} else if (strRoom.equals("Monster1")) { //If user has beat the monster at this room, display the description without the monster
					if (!blInCombat && !blMon1Dead) {
						blInCombat = true;
						strDesc = Forest[intY][intX][2];
					} else if(blMon1Dead){
						strDesc = Forest[intY][intX][2];
					}
				} else if (strRoom.equals("Monster2")) {
					if (!blInCombat && !blMon2Dead) {
						blInCombat = true;
						strDesc = Forest[intY][intX][1];
					} else if(blMon2Dead){
						strDesc = Forest[intY][intX][2];
					}
				} else if (strRoom.equals("Monster3")) {
					if (!blInCombat && !blMon3Dead) {
						blInCombat = true;
						strDesc = Forest[intY][intX][2];
					} else if(blMon3Dead){
						strDesc = Forest[intY][intX][2];
					}
				}
				strOutput = strDesc;
				if (blInCombat && (strRoom.equals("Monster1") || strRoom.equals("Monster2") || strRoom.equals("Monster3"))){ //if the user meets any monster
					strOutput = "You come across a monster with ";
					//determine which monster
					if (strRoom.equals("Monster1")){
						strOutput += intMonHP + " HP!";
					} else if (strRoom.equals("Monster2")){
						strOutput += intMon2HP + " HP!";
					} else if(strRoom.equals("Monster3")){
						strOutput += intMon3HP + " HP!";
					}
					strOutput += "\nYou have " + intHP + " HP! What would you like to do?";
				} else if (strDesc.equals("path2")){ //Path from forest to Kronwell
					strLocation = "Kronwell";
					intY = 1;
					intX = 1;
					strRoom = Kronwell[intY][intX][0];
					strDesc = Kronwell[intY][intX][1];
					strOutput = strDesc;
				} else if(strDesc.equals("path3")){ //Path out through the mountain
					if (blTorch){
						blFinished = true;
					} else{ //Can't proceed without a torch
						strDesc = "It's too dark to continue this way. Find a torch, maybe?";
						intY = 6;
						intX = 1;
						strRoom = Forest[intY][intX][0];
						strOutput = strDesc;
					}
				}
			}
		}while(!blFinished);
		
		//If player is dead
		if (intHP == 0){
			System.out.println(strOutput);
		} else { //If player is not dead
			strOutput = "\nYou proceed through the exit of the forest, into the entrance of Mt. Anbus.\nNo problems arise, and as you exit, there it is. The eye of evil. Floating \nominously above the ruins of Brimmore. It's purple, with its glow a blinding gold.\nYou walk up to it, and stab it. The world is free.";
			System.out.println(strOutput);
			try{
				Thread.sleep(5000);
			} catch(Exception e){}
			strOutput = "\n\nNow you can go back to working your nine to five job.";
			System.out.println(strOutput);
		}
	}
}